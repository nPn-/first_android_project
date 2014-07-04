package com.gmail.npnster.first_project;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;

import com.gmail.npnster.first_project.api_params.PatchLocationRequest;
import com.gmail.npnster.first_project.api_params.PostLocationRequest;
import com.gmail.npnster.first_project.api_params.UpdateLocationRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationListener;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class DeviceLocationClient implements GooglePlayServicesClient.ConnectionCallbacks,
                                             GooglePlayServicesClient.OnConnectionFailedListener,
                                             LocationListener
                                             {
	
	private Context context;
	private LocationClient locationClient;
	private boolean isConnected ;
	private LocationRequest request;
	private Bus mBus;
	
	@Override
	public void onLocationChanged(Location location) {
		System.out.println("got a location update");
		PatchLocationRequest patchLocationRequest = new PatchLocationRequest(MyApp.getGcmRegId(),location);
		mBus.post(patchLocationRequest);	
	}

	public DeviceLocationClient(Context context) {
		this.context = context;
		mBus = MyApp.getBus();
		locationClient = new LocationClient(context, this, this);
		locationClient.connect();
		isConnected = false;
		request = new LocationRequest();
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		request.setExpirationDuration(60 *1000);  
		request.setFastestInterval(5 *1000);
		request.setInterval(10 * 1000);
		
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		isConnected = true;
		//locationClient.requestLocationUpdates(request, this);
		mBus.register(this);
		
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		mBus.unregister(this);
		
	}
	
	@Subscribe
	public void onUpdateLocationRequest(UpdateLocationRequest event) {	
		System.out.println("got the location request");
		request = new LocationRequest();
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		request.setExpirationDuration(60 *1000);  
		request.setFastestInterval(5 *1000);
		request.setInterval(10 * 1000);
		locationClient.requestLocationUpdates(request, this);
	}

}
