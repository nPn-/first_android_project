package com.gmail.npnster.first_project;

import javax.inject.Inject;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.gmail.npnster.first_project.api_params.FollowResponse;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.UnfollowResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class NotificationRepository {

	@Inject
	Bus mBus;
	@Inject
	@ForApplication
	Context mContext;
	private boolean mPermissionNotificationsEnabled = true;

	public NotificationRepository() {
		Injector.getInstance().inject(this);
		mBus.register(this);
	}

	@Subscribe
	public void onNewMicropostEvent(NewMicropostEvent event) {
		getUserIconAndThenNotifyUser(event.getIntent());
	}

	@Subscribe
	public void onUserDetailActivityStateChangeEvent(UserDetailActivityStateChangeEvent event) {
		UserDetailActivityStateChangeEvent.State newState = event.getState();
		switch (newState) {
		case RESUMED:
			mPermissionNotificationsEnabled = false;
			break;
		case PAUSED:
			mPermissionNotificationsEnabled = true;
			break;
		}
		System.out.println(String.format("user detail notifications enabled = %b", mPermissionNotificationsEnabled));

	}

	private void getUserIconAndThenNotifyUser(final Intent intent) {
		String gravatarUrl = intent.getStringExtra("gravatar_url");
		System.out.println(String.format("loading bitmap from %s and then notify", gravatarUrl));
		Picasso.with(mContext).load(gravatarUrl).into(new Target() {

			@Override
			public void onBitmapFailed(Drawable arg0) {
				System.out.println("failed to load bit map for notification");
				Bitmap failIcon = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher);
				notifyUserOfMicropostUpdate(intent, failIcon);
			}

			@Override
			public void onBitmapLoaded(Bitmap userIcon, LoadedFrom arg1) {
				System.out.println("bit map for notification loaded");
				notifyUserOfMicropostUpdate(intent, userIcon);

			}

			@Override
			public void onPrepareLoad(Drawable arg0) {
				// TODO Auto-generated method stub

			}

		});

	}

	private void notifyUserOfMicropostUpdate(Intent intent, Bitmap userIcon) {
		System.out.println("building notification");
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		long[] vibrate = { 500, 500, 500, 500 };
		String content = intent.getStringExtra("content");
		String name = intent.getStringExtra("user_name");
		String userIdString = intent.getStringExtra("user_id");
		int userId = Integer.parseInt(userIdString);
		System.out.println(String.format("user id = %d", userId));

		Intent resultIntent = new Intent(mContext, HomeActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext).setContentIntent(resultPendingIntent)
				.setSmallIcon(R.drawable.ic_launcher).setLargeIcon(userIcon).setContentTitle(name).setTicker(content)
				.setPriority(2).setSound(alarmSound).setVibrate(vibrate).setContentText(content);

		NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(userId, mBuilder.build());

	}

	public void setPermissionsNotificationsEnabled(boolean enable) {
		mPermissionNotificationsEnabled = enable;
	}

	@Subscribe
	public void onRevokePermissionResponse(RevokeFollowerPermissionResponse event) {
		if (mPermissionNotificationsEnabled && !event.isSuccessful()) {
			System.out.println(String.format("notify = revoke %s permission failed", event.getRequestEvent().getPermission()));
			notifyUserUpdateFailed(event.getRequestEvent().getFollowerId(),event.getRequestEvent().getFollowerName());

		}
	}

	private void notifyUserUpdateFailed(String followerId, String followerName) {
		System.out.println("building notification for update failure");
		int followerIdInt = Integer.parseInt(followerId);
		Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		long[] vibrate = { 500, 500, 500, 500 };
		Intent resultIntent = new Intent(mContext, UserDetailActivity.class);
		resultIntent.putExtra("user_id",followerIdInt);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
		stackBuilder.addParentStack(UserDetailActivity.class);
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(followerIdInt, PendingIntent.FLAG_UPDATE_CURRENT);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mContext).setContentIntent(resultPendingIntent)
				.setSmallIcon(R.drawable.ic_launcher).setContentTitle(followerName).setTicker("user detail update failed")
				.setPriority(2).setSound(alarmSound).setVibrate(vibrate).setContentText("user detail update update failed");
		
		NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(followerIdInt, mBuilder.build());
		
	}

	@Subscribe
	public void onGrantPermissionResponse(GrantFollowerPermissionResponse event) {
		if (mPermissionNotificationsEnabled && !event.isSuccessful()) {
			System.out.println(String.format("notify = grant %s permission failed", event.getRequestEvent().getPermission()));
			notifyUserUpdateFailed(event.getRequestEvent().getFollowerId(),event.getRequestEvent().getFollowerName());
		}
	}
	
	@Subscribe
	public void onFollowResponse(FollowResponse event) {
		if (mPermissionNotificationsEnabled && !event.isSuccessful()) {
			System.out.println(String.format("notify = follow action for %s failed", event.getRequestEvent().getUserName()));
			notifyUserUpdateFailed(event.getRequestEvent().getUserId(),event.getRequestEvent().getUserName());
		}
	}
	
	@Subscribe
	public void onUnfollowResponse(UnfollowResponse event) {
		if (mPermissionNotificationsEnabled && !event.isSuccessful()) {
			System.out.println(String.format("notify = unfollow action for %s failed", event.getRequestEvent().getUserName()));
			notifyUserUpdateFailed(event.getRequestEvent().getUserId(),event.getRequestEvent().getUserName());
		}
	}

}
