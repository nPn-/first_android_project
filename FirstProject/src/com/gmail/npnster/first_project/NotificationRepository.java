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

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class NotificationRepository {
	
	@Inject Bus mBus;
	@Inject @ForApplication Context mContext;
	
	public NotificationRepository() {
		Injector.getInstance().inject(this);
		mBus.register(this);
	}

	@Subscribe
	public void onNewMicropostEvent(NewMicropostEvent event) {
		getUserIconAndThenNotifyUser(event.getIntent());
	}
	
	 private void getUserIconAndThenNotifyUser(final Intent intent) {
		    String gravatarUrl = intent.getStringExtra("gravatar_url");
		    System.out.println(String.format("loading bitmap from %s and then notify", gravatarUrl));
		    Picasso.with(mContext).load(gravatarUrl).into(new Target() {

		    	@Override
		    	public void onBitmapFailed(Drawable arg0) {
		    		System.out.println("failed to load bit map for notification");
		    		Bitmap failIcon = BitmapFactory.decodeResource(mContext.getResources(),
		                   R.drawable.ic_launcher);
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

		    Intent resultIntent = new Intent(mContext, HomeActivity.class);
		    TaskStackBuilder stackBuilder = TaskStackBuilder.create(mContext);
		    stackBuilder.addNextIntent(resultIntent);
		    PendingIntent resultPendingIntent =
		            stackBuilder.getPendingIntent(
		                0,
		                PendingIntent.FLAG_UPDATE_CURRENT
		            );
	        NotificationCompat.Builder mBuilder =
	                new NotificationCompat.Builder(mContext)
	        		.setContentIntent(resultPendingIntent)
	                .setSmallIcon(R.drawable.ic_launcher)
	                .setLargeIcon(userIcon)
	                .setContentTitle(name)
	                .setTicker(content)
	                .setPriority(2)
	                .setSound(alarmSound)
	                .setVibrate(vibrate)
	                .setContentText(content);

	            NotificationManager mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
	            mNotificationManager.notify(1, mBuilder.build());
		
	}
}
