package com.gmail.npnster.first_project;

import javax.inject.Inject;

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

public class DeviceLocationClient implements
		GooglePlayServicesClient.ConnectionCallbacks,
		GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	@Inject
	@ForApplication
	Context context;
	private LocationClient locationClient;
	private boolean isConnected;
	private LocationRequest request;
	@Inject
	Bus mBus;
	@Inject
	PersistData mPersistData;

	@Override
	public void onLocationChanged(Location location) {
		System.out.println("got a location update");
		if (location != null ) {
			PatchLocationRequest patchLocationRequest = new PatchLocationRequest(
					mPersistData.getGcmRegId(), location);
			mBus.post(patchLocationRequest);
		}
	}

	public DeviceLocationClient() {

		Injector.getInstance().inject(this);
		System.out.println("connecting to google play services");
		locationClient = new LocationClient(context, this, this);
		isConnected = false;
		locationClient.connect();

	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {
		System.out.println("connection to google play services FAILED!");
		isConnected = false;

	}

	@Override
	public void onConnected(Bundle connectionHint) {
		System.out.println("google play servies connected");
		isConnected = true;
		// locationClient.requestLocationUpdates(request, this);
		mBus.register(this);
		request = new LocationRequest();
		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
		request.setExpirationDuration(60 * 1000);
		request.setFastestInterval(5 * 1000);
		request.setInterval(10 * 1000);
		isConnected = true;
		requestLLocationUpdates();

	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		System.out
				.println("google play services got disconnected - reconnecting");
		isConnected = false;
		mBus.unregister(this);
		locationClient.connect();

	}

	
	// I don't think this is used anymore
   
//	@Subscribe
//	public void onUpdateLocationRequest(UpdateLocationRequest event) {
//		System.out.println("got the location request");
//		request = new LocationRequest();
//		request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//		request.setExpirationDuration(60 * 1000);
//		request.setFastestInterval(5 * 1000);
//		request.setInterval(10 * 1000);
//		locationClient.removeLocationUpdates(this);
//		locationClient.requestLocationUpdates(request, this);
//	}

	public void requestLLocationUpdates() {
		System.out.println("got the location request from the service");

		if (isConnected) {
			System.out.println("processing request");
			System.out.println("sending latest location");
			Location lastLocation = locationClient.getLastLocation();
			if (lastLocation != null) {
				PatchLocationRequest patchLocationRequest = new PatchLocationRequest(
						mPersistData.getGcmRegId(), lastLocation);
				mBus.post(patchLocationRequest);
			}
			request = new LocationRequest();
			request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
			request.setExpirationDuration(60 * 1000);
			request.setFastestInterval(5 * 1000);
			request.setInterval(10 * 1000);
			System.out.println("requesting updates");
			locationClient.removeLocationUpdates(this);
			locationClient.requestLocationUpdates(request, this);
		} else {
			if (locationClient.isConnecting()) {
				System.out.println("google play services is connecting");
			} else {
				System.out
						.println("attempting to connect to google play services");
				locationClient.connect();
			}

		}
	}

}
