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
	UpdateLocationRequest updateLocationRequest = new UpdateLocationRequest();
	
	public GcmBroadcastReceiver() {
		Injector.getInstance().inject(this);
		
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
        		Intent serviceIntent = new Intent(context, LocationMonitorService.class);
        		serviceIntent.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.LOCATION_UPDATE_REQUEST_RECEIVED");
        		context.startService(serviceIntent);
        		//mBus.post(updateLocationRequest);
        		      		
        	}
        	
        }

	}

}
