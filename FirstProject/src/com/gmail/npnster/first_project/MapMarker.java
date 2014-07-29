package com.gmail.npnster.first_project;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.Marker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class MapMarker implements Target {
	
	
	private Bus mBus;
	private Marker mMarker;
	private com.google.android.gms.maps.model.Marker mGoogleMapMarker;
	
	public com.google.android.gms.maps.model.Marker getGoogleMapMarker() {
		return mGoogleMapMarker;
	}

	public void setGoogleMapMarker(
			com.google.android.gms.maps.model.Marker googleMapMarker) {
		mGoogleMapMarker = googleMapMarker;
	}

	public void setAccuracy(Float accuracy) {
		mMarker.setAccuracy(accuracy);
	}

	public void setHasAccuracy(Boolean hasAccuracy) {
		mMarker.setHasAccuracy(hasAccuracy);
	}

	public void setHasAltitude(Boolean hasAltitude) {
		mMarker.setHasAltitude(hasAltitude);
	}

	public void setHasBearing(Boolean hasBearing) {
		mMarker.setHasBearing(hasBearing);
	}

	public void setHasSpeed(Boolean hasSpeed) {
		mMarker.setHasSpeed(hasSpeed);
	}

	public void setLatitude(Double latitude) {
		mMarker.setLatitude(latitude);
	}

	public void setLongitude(Double longitude) {
		mMarker.setLongitude(longitude);
	}

	public void setAltitude(Double altitude) {
		mMarker.setAltitude(altitude);
	}

	public void setBearing(Float bearing) {
		mMarker.setBearing(bearing);
	}

	public void setSpeed(Float speed) {
		mMarker.setSpeed(speed);
	}

	public void setLocationFixTime(Long locationFixTime) {
		mMarker.setLocationFixTime(locationFixTime);
	}

	public Boolean hasAccuracy() {
		return mMarker.hasAccuracy();
	}

	public Boolean hasAltitude() {
		return mMarker.hasAltitude();
	}

	public Boolean hasBearing() {
		return mMarker.hasBearing();
	}

	public Boolean hasSpeed() {
		return mMarker.hasSpeed();
	}

	public Double getAltitude() {
		return mMarker.getAltitude();
	}

	public Float getBearing() {
		return mMarker.getBearing();
	}

	public Float getSpeed() {
		return mMarker.getSpeed();
	}

	public Long getLocationFixTime() {
		return mMarker.getLocationFixTime();
	}

	public Double getLatitude() {
		return mMarker.getLatitude();
	}

	public Double getLongitude() {
		return mMarker.getLongitude();
	}
	
	public LatLng getLatLng() {
		return new LatLng(mMarker.getLatitude(), mMarker.getLongitude());
	}



	private Bitmap mBitmap;
		

	public String getName() {

		return mMarker.getName();
	}

	public String getGravatarUrl() {
		return mMarker.getGravatarUrl();
	}
	
	public Bitmap getBitmap() {
		return mBitmap;
	}
	
	public String getUserId() {
		return mMarker.getUserId();
	}



	public MapMarker(Context context, Marker marker) {
		System.out.println("building marker");
		mMarker = marker;
		mBitmap = null;
		mBus = BusProvider.getInstance();
//		mBus.register(this);
		Picasso.with(context).load(marker.getGravatarUrl()).into(this);
	}


	@Override
	public void onBitmapFailed(Drawable arg0) {
		// TODO Auto-generated method stub    
		System.out.println("failed to load bit map for gravatar");
		mBus.post(new MarkerReadyEvent(this));
		
	}

	@Override
	public void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
		// TODO Auto-generated method stub
		System.out.println(String.format("loaded bit map from gravatar url = %s, for userid = %s",getGravatarUrl(), getUserId()));
		System.out.println("bit map was loaded from");
		System.out.println(loadedFrom.toString());
		mBitmap = bitmap;
		mBus.post(new MarkerReadyEvent(this));
	    
	}
	
	@Override
	public void onPrepareLoad(Drawable arg0) {
		// TODO Auto-generated method stub
		System.out.println("inside MapMarker onPrepareLoad");
		
	}

	public GoogleMapMarkerParameters getGoogleMapMarkerParameters() {
		return new GoogleMapMarkerParameters(getUserId()).setLatLng(getLatLng());
	}

}
