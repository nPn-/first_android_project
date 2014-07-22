package com.gmail.npnster.first_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class GcmKeepAliveBroadcastReceiver extends BroadcastReceiver {

	private GcmKeepAlive gcmKeepAlive;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		System.out.println("inside gcm keep alive receiver");
		gcmKeepAlive = new GcmKeepAlive(context);
		gcmKeepAlive.broadcastIntents();
		
	}

}
