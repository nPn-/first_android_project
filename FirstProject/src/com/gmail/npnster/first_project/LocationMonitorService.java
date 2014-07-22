package com.gmail.npnster.first_project;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


public class LocationMonitorService extends Service {
	
	private DeviceLocationClient deviceLocationClient;
	private AlarmManager alarmManager;
	private Intent gcmKeepAliveIntent;
    private PendingIntent gcmKeepAlivePendingIntent;

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("creating the LocationMonitorService");
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
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	
}
