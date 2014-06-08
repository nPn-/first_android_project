package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.UpdateLocationRequest;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.squareup.otto.Bus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class GcmBroadcastReceiver extends BroadcastReceiver {
	
	private Bus mBus;
	UpdateLocationRequest updateLocationRequest = new UpdateLocationRequest();
	
	public GcmBroadcastReceiver() {
		mBus = MyApp.getBus();
		
	}
	

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(context);
        String messageType = gcm.getMessageType(intent);
        System.out.println("inside GCM receiver");

        if (!extras.isEmpty()) {
        	if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
        		System.out.println(extras.toString());
        		mBus.post(updateLocationRequest);
        		      		
        	}
        	
        }

	}

}
