package com.gmail.npnster.first_project;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class LocationMonitorService extends Service {
	
	private DeviceLocationClient deviceLocationClient;
	private GcmKeepAlive gcmKeepAlive;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("creating the LocationMonitorService");
		deviceLocationClient = new DeviceLocationClient(this);
		gcmKeepAlive = new GcmKeepAlive(this);
		gcmKeepAlive.broadcastIntents();
		gcmKeepAlive.start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		System.out.println("inside service making request for location updates");
		deviceLocationClient.requestLLocationUpdates();
		gcmKeepAlive.broadcastIntents();
		gcmKeepAlive.start();
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
