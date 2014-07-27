package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.GetMapMarkersRequest;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestRequest;
import com.squareup.otto.Bus;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.CountDownTimer;


public class LocationMonitorService extends Service {
	
	private DeviceLocationClient deviceLocationClient;
	private AlarmManager alarmManager;
	private Intent gcmKeepAliveIntent;
    private PendingIntent gcmKeepAlivePendingIntent;
	private Bus mBus;
	private GetMarkerRequestTimer markerRequestTimer;

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("creating the LocationMonitorService");
		mBus = BusProvider.getInstance();
		mBus.register(this);
		markerRequestTimer = new GetMarkerRequestTimer(60000,10000);
		deviceLocationClient = new DeviceLocationClient(this);
		gcmKeepAliveIntent = new Intent("com.gmail.npnster.first_project.gcmKeepAlive");
		gcmKeepAlivePendingIntent = PendingIntent.getBroadcast(this, 0, gcmKeepAliveIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
		alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, 4*60*1000, gcmKeepAlivePendingIntent);
		
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("inside service making request for location updates");
		deviceLocationClient.requestLLocationUpdates();
		if (intent.hasCategory("REQUEST_MARKERS")) {
			System.out.println("inside service .. requesting new map markers");
			requestMarkerUpdates();
		}
		return START_STICKY;
	}

	private void requestMarkerUpdates() {
		markerRequestTimer.start();
		
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	
	class GetMarkerRequestTimer extends CountDownTimer {

		public GetMarkerRequestTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTick(long millisUntilFinished) {
			System.out.println("requesting new markers from server");
			mBus.post(new GetMapMarkersRequest());
			
		}

		@Override
		public void onFinish() {
			System.out.println("done requesting new markers from server");	
			
		}
		
	}

	
}
