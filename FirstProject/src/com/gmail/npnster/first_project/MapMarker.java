package com.gmail.npnster.first_project;

import javax.inject.Inject;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.format.DateUtils;

import com.gmail.npnster.first_project.AFirstDaggerModule;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.RailsMarker;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.otto.Bus;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import dagger.ObjectGraph;

public class MapMarker implements Target {
	
	
	private Bus mBus;
	

	private RailsMarker mMarker;
	
	@Inject
	GoogleMapMarkerParameters googleMapMarkerParameters;


	private com.google.android.gms.maps.model.Marker mGoogleMapMarker;
	private Bitmap mBitmap;
	
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
	
	public void setGravatarUrl(String gravatarUrl) {
		mMarker.setGravatarUrl(gravatarUrl);
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

	public Float getAccuracy() {
		return mMarker.getAccuracy();
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

	public MapMarker(Context context, RailsMarker marker) {
		System.out.println("building marker");
		mMarker = marker;
		mBitmap = null;
		mBus = BusProvider.getInstance();
		ObjectGraph objectGraph = MyApp.getObjectGraph();
		objectGraph.inject(this);
		Picasso.with(context).load(marker.getGravatarUrl()).into(this);
	}
	
	public MapMarker() {
		ObjectGraph objectGraph = MyApp.getObjectGraph();
		objectGraph.inject(this);
	}


	@Override
	public void onBitmapFailed(Drawable arg0) {
		System.out.println("failed to load bit map for gravatar");
		mBus.post(new MarkerReadyEvent(this));
		
	}

	@Override
	public void onBitmapLoaded(Bitmap bitmap, LoadedFrom loadedFrom) {
		System.out.println(String.format("loaded bit map from gravatar url = %s, for userid = %s",getGravatarUrl(), getUserId()));
		System.out.println("bit map was loaded from");
//		System.out.println(loadedFrom.toString());
		mBitmap = bitmap;
		mBus.post(new MarkerReadyEvent(this));
	    
	}
	
	@Override
	public void onPrepareLoad(Drawable arg0) {
		System.out.println("inside MapMarker onPrepareLoad");
		
	}

	public GoogleMapMarkerParameters getGoogleMapMarkerParameters() {
		googleMapMarkerParameters.setUserId(getUserId());
		googleMapMarkerParameters.setLatLng(getLatLng());
		Float radius = hasAccuracy() ? getAccuracy() : 0.0f ;  
		googleMapMarkerParameters.setCircleRadius(radius);
		System.out.println(String.format("rad = %f",googleMapMarkerParameters.getCircleRadius() ));
		return googleMapMarkerParameters;
	}

	public String getInfoWindowData() {
		String infoWindowData = new String();
		infoWindowData = DateUtils.getRelativeTimeSpanString(getLocationFixTime()).toString();
		System.out.println(String.format("hasAccuracy = %b, accuracy = %f", hasAccuracy(), getAccuracy()));
		if (hasAccuracy()) infoWindowData = infoWindowData + String.format("\nAccuracy: %4.0f feet",getAccuracy()*3.28);
		if (hasSpeed()) infoWindowData = infoWindowData +    String.format("\nSpeed: %3.0f mph",getSpeed()*2.237);
		if (hasBearing()) infoWindowData = infoWindowData +  String.format("\nBearing: %3.0f degrees",getBearing());
		System.out.println(String.format("infoWindow in MapMarker  = %s", infoWindowData));
		return infoWindowData;
	}
	
	protected RailsMarker getMarker() {
		return mMarker;
	}

	protected void setMarker(RailsMarker mMarker) {
		this.mMarker = mMarker;
	}
	
	public Bus getBus() {
		return mBus;
	}

	public void setBus(Bus mBus) {
		this.mBus = mBus;
	}



}
