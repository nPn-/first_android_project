package com.gmail.npnster.first_project;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class GoogleMapMarkerParameters {
	private LatLng mLatLng;
	private Bitmap mBitmap;
	private String mUserId;
	private String mTitle;
	private Boolean mIsCenterOn ;
	private Float mRadius;
	
	public GoogleMapMarkerParameters(String userId) {
		mUserId = userId;
		mIsCenterOn = false;
		
	}
	
	public GoogleMapMarkerParameters() {
		mIsCenterOn = false;
	}
 	
	public LatLng getLatLng() {
		return mLatLng;
	}
	public GoogleMapMarkerParameters setLatLng(LatLng latLng) {
		mLatLng = latLng;
		return this;
	}

	public Bitmap getBitmap() {
		return mBitmap;
	}

	public GoogleMapMarkerParameters setBitmap(Bitmap bitmap) {
		mBitmap = bitmap;
		return this;
	}

	public String getTitle() {
		return mTitle;
	}
	
	public GoogleMapMarkerParameters setTitle(String title) {
		mTitle = title;
		return this;
	}
	
	public Float getCircleRadius() {
		return mRadius;
	}
	
	public GoogleMapMarkerParameters setCircleRadius(Float radius) {
		mRadius = radius;
		return this;
	}
	
	public String getUserId() {
		return mUserId;
	}
	
	public GoogleMapMarkerParameters setIsCenterOn(Boolean isCenterOn) {
		mIsCenterOn = isCenterOn;
		return this;
	}
	
	public GoogleMapMarkerParameters setUserId(String userId) {
		mUserId = userId;
		return this;
	}
	
	public Boolean isCenterOn() {
		return mIsCenterOn;
	}
	
	
	

}
