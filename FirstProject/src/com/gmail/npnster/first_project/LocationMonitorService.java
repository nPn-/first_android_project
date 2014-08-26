package com.gmail.npnster.first_project;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import com.gmail.npnster.first_project.api_params.GetMapMarkersRequest;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestRequest;
import com.squareup.otto.Bus;

import dagger.ObjectGraph;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.CountDownTimer;


public class LocationMonitorService extends Service {
	
	@Inject DeviceLocationClient deviceLocationClient;
//	@Inject @NamedProvider("gcmKeepAliveIntent") Intent gcmKeepAliveIntent;
//	Intent gcmKeepAliveIntent;
	@Inject AlarmManager alarmManager;
    @Inject PendingIntent gcmKeepAlivePendingIntent;
	@Inject GetMarkerRequestTimer markerRequestTimer;
	@Inject PushRequestTimer pushRequestTimer;
	@Inject Bus mBus;
//	ObjectGraph mExtendedGraph;

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("creating the LocationMonitorService");
//		gcmKeepAliveIntent = new Intent("com.gmail.npnster.first_project.gcmKeepAlive");
		injectMe();
		mBus.register(this);
//		markerRequestTimer = new GetMarkerRequestTimer(10*60000,10000);
//		pushRequestTimer = new PushRequestTimer(10*60000,60000);
//		deviceLocationClient = new DeviceLocationClient(this);
//		gcmKeepAlivePendingIntent = PendingIntent.getBroadcast(this, 0, gcmKeepAliveIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//		alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
		
		// should move the alarmManger to the app class, no reason it should be down here in this service
		// but leave it for now since this is the only reason/example i have of a scoped dagger module (at this point)
		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, 4*60*1000, gcmKeepAlivePendingIntent);
		
//		System.out.println(String.format("service am = %s", alarmManager));
//		System.out.println("leaving onCreate the LocationMonitorService");
		
		
	}
	
	void injectMe() {
//		ObjectGraph objectGraph = Injector.getInstance().getObjectGraph();
//		System.out.println(String.format("currentGraph = %s", objectGraph));
//		ObjectGraph extendedGraph = objectGraph.plus(getModules().toArray());
//		System.out.println(String.format("extendedGraph = %s", extendedGraph));
//		extendedGraph.inject(this);
		Injector.getInstance().injectWith(this,  new LocationMonitorServiceModule(this) );
	}
	
	AlarmManager getAlarmManager() {
		return alarmManager;
	}
	
	PendingIntent getGcmKeepAlivePendingIntent() {
		return gcmKeepAlivePendingIntent;
	}
	
//	  protected List<Object> getModules() {
//		    return Arrays.<Object>asList(new LocationMonitorServiceModule(this,gcmKeepAliveIntent));
//		  }

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("inside service making request for location updates");
		if (intent != null) {
			if (intent.hasCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_RESUMED")) {
				startRequestMarkerUpdates();	
				startLocationPushRequests();
			} else if (intent.hasCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_PAUSED")) {
				endRequestMarkerUpdates();
				endLocationPushRequests();
			} else if (intent.hasCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.LOCATION_UPDATE_REQUEST_RECEIVED")) {
				deviceLocationClient.requestLLocationUpdates();
			}
		}

		return START_STICKY;
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
			// TODO Auto-generated constructor stub
		}
		
		@Override
		public void onTick(long millisUntilFinished) {
			System.out.println("requesting new markers from server");
			mBus.post(new GetMapMarkersRequest());
			
		}
		
		@Override
		public void onFinish() {
			System.out.println("time out reached ending request for markers from the server");	
//			System.out.println("renewing request for markers from the server");	
//			start();
			
		}
		
	}
	
	 static class PushRequestTimer extends CountDownTimer {

		@Inject Bus mBus;
		 
		public PushRequestTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			Injector.getInstance().inject(this);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTick(long millisUntilFinished) {
			System.out.println("sending request for gcm push notifications to ask for updated locations");
			mBus.post(new PushLocationsUpdateRequestRequest());
			
		}

		@Override
		public void onFinish() {
			System.out.println("time out reached ending push notifcation renewal ending - for now");	
//			System.out.println("renewing request for markers from the server");	
//			start();
			
		}
		
	}

	
}
