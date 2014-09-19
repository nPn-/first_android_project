package com.gmail.npnster.first_project;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.gmail.npnster.first_project.api_params.GetMapMarkersRequest;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestRequest;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

import dagger.ObjectGraph;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;


public class LocationMonitorService extends Service implements Target {
	
	@Inject DeviceLocationClient deviceLocationClient;
	@Inject AlarmManager alarmManager;
    @Inject PendingIntent gcmKeepAlivePendingIntent;
	@Inject GetMarkerRequestTimer markerRequestTimer;
	@Inject PushRequestTimer pushRequestTimer;
	@Inject Bus mBus;
	Intent mIntent;

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("creating the LocationMonitorService");
		injectMe();
		mBus.register(this);
		// should move the alarmManger to the app class, no reason it should be down here in this service
		// but leave it for now since this is the only reason/example i have of a scoped dagger module (at this point)
		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, 4*60*1000, gcmKeepAlivePendingIntent);
		
		
	}
	
	void injectMe() {
		Injector.getInstance().injectWith(this,  new LocationMonitorServiceModule(this) );
	}
	
	AlarmManager getAlarmManager() {
		return alarmManager;
	}
	
	PendingIntent getGcmKeepAlivePendingIntent() {
		return gcmKeepAlivePendingIntent;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("inside service making request for location updates");
		mIntent = intent;
		if (intent != null) {
			if (intent.hasCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_RESUMED")) {
				startRequestMarkerUpdates();	
				startLocationPushRequests();
			} else if (intent.hasCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_PAUSED")) {
				endRequestMarkerUpdates();
				endLocationPushRequests();
			} else if (intent.hasCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MICROPOST_UPDATE_RECEIVED")) {
				getUserIconAndThenNotifyUser();
			} else if (intent.hasCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.LOCATION_UPDATE_REQUEST_RECEIVED")) {
				deviceLocationClient.requestLLocationUpdates();
			}
		}

		return START_STICKY;
	}

	
	 private void getUserIconAndThenNotifyUser() {
		    String gravatarUrl = mIntent.getStringExtra("gravatar_url");
		    System.out.println(String.format("loading bitmap from %s and then notify", gravatarUrl));
		    Picasso.with(this).load(gravatarUrl).into(this);
		    
	 }
	
	 private void notifyUserOfMicropostUpdate( Bitmap userIcon) {
		    System.out.println("building notification");
		    Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		    long[] vibrate = { 500, 500, 500, 500 };
		    String content = mIntent.getStringExtra("content");
		    String name = mIntent.getStringExtra("user_name");

		    Intent resultIntent = new Intent(this, HomeActivity.class);
		    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		    stackBuilder.addNextIntent(resultIntent);
		    PendingIntent resultPendingIntent =
		            stackBuilder.getPendingIntent(
		                0,
		                PendingIntent.FLAG_UPDATE_CURRENT
		            );
	        NotificationCompat.Builder mBuilder =
	                new NotificationCompat.Builder(this)
	        		.setContentIntent(resultPendingIntent)
	                .setSmallIcon(R.drawable.ic_launcher)
	                .setLargeIcon(userIcon)
	                .setContentTitle(name)
	                .setTicker(content)
	                .setPriority(2)
	                .setSound(alarmSound)
	                .setVibrate(vibrate)
	                .setContentText(content);

	            NotificationManager mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
	            mNotificationManager.notify(1, mBuilder.build());
		
	}

	void startRequestMarkerUpdates() {
		System.out.println("starting cycle of request for markers from server");
		mBus.post(new GetMapMarkersRequest());
		markerRequestTimer.cancel();
		markerRequestTimer.start();		
	}
	
	 void endRequestMarkerUpdates() {
		System.out.println("canceling request for new updates");	
		markerRequestTimer.cancel();
	}
	
	 void startLocationPushRequests() {
		System.out.println("starting cycle of push requests for remote device locations");
		mBus.post(new PushLocationsUpdateRequestRequest());
		mBus.post(new GetMapMarkersRequest());
		pushRequestTimer.cancel();
		pushRequestTimer.start();		
	}
	
	 void endLocationPushRequests() {
		System.out.println("canceling renewal of push request for remote device locations");	
		pushRequestTimer.cancel();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	 static class GetMarkerRequestTimer extends CountDownTimer {
		
		@Inject Bus mBus; 

		 
		public GetMarkerRequestTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			Injector.getInstance().inject(this);
		}
		
		@Override
		public void onTick(long millisUntilFinished) {
			System.out.println("requesting new markers from server");
			mBus.post(new GetMapMarkersRequest());
			
		}
		
		@Override
		public void onFinish() {
			System.out.println("time out reached ending request for markers from the server");	
			
		}
		
	}
	
	 static class PushRequestTimer extends CountDownTimer {

		@Inject Bus mBus;
		 
		public PushRequestTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			Injector.getInstance().inject(this);
		}

		@Override
		public void onTick(long millisUntilFinished) {
			System.out.println("sending request for gcm push notifications to ask for updated locations");
			mBus.post(new PushLocationsUpdateRequestRequest());
			
		}

		@Override
		public void onFinish() {
			System.out.println("time out reached ending push notifcation renewal ending - for now");	
			
		}
		
	}

	@Override
	public void onBitmapFailed(Drawable arg0) {
		System.out.println("failed to load bit map for notification");
		Bitmap failIcon = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.ic_launcher);
		notifyUserOfMicropostUpdate(failIcon);
	}

	@Override
	public void onBitmapLoaded(Bitmap userIcon, LoadedFrom arg1) {
		System.out.println("bit map for notification loaded");
		notifyUserOfMicropostUpdate(userIcon);
		
	}

	@Override
	public void onPrepareLoad(Drawable arg0) {
		// TODO Auto-generated method stub
		
	}

	
}
