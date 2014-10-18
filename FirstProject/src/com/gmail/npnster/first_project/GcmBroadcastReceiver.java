package com.gmail.npnster.first_project;

import javax.inject.Inject;

import com.gmail.npnster.first_project.api_params.UpdateLocationRequest;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.squareup.otto.Bus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class GcmBroadcastReceiver extends BroadcastReceiver {
	
	@Inject Bus mBus;
//	UpdateLocationRequest updateLocationRequest = new UpdateLocationRequest();
	
	public GcmBroadcastReceiver() {
		
	}
	

	@Override
	public void onReceive(Context context, Intent intent) {
		Injector.getInstance().inject(this);
		Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        String messageType = gcm.getMessageType(intent);
        System.out.println("inside GCM receiver");

        if (!extras.isEmpty()) {
        	if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
        		System.out.println(extras.toString());
        		if (extras.getString("message") != null && extras.getString("message").equals("update_locations")) {
        			System.out.println("got location update request from gcm");
        			Intent serviceIntent = new Intent(context, LocationMonitorService.class);
        			serviceIntent.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.LOCATION_UPDATE_REQUEST_RECEIVED");
        			context.startService(serviceIntent);
        			//mBus.post(updateLocationRequest);
				}
        		if (extras.getString("message") != null && extras.getString("message").equals("micropost_created")) {
        			System.out.println("got micropost update from gcm");
        			mBus.post(new NewMicropostEvent(intent));
//        			Intent serviceIntent = new Intent(context, LocationMonitorService.class);
//        			serviceIntent.putExtras(intent);
//        			serviceIntent.putExtra("gravatar_url",intent.getStringExtra("gravatar_url"));
//        			serviceIntent.putExtra("user_name",intent.getStringExtra("user_name"));
//        			serviceIntent.putExtra("content",intent.getStringExtra("content"));
//        			serviceIntent.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MICROPOST_UPDATE_RECEIVED");
//        			context.startService(serviceIntent);

        		}
        	}
        	
        }

	}

}
