package com.gmail.npnster.first_project;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

public class GoogleMapMarkerParameters {
	private LatLng mLatLng;
	private Bitmap mBitmap;
	private String mUserId;
	private String mTitle;
	private Boolean mIsCenterOn ;
	
	public GoogleMapMarkerParameters(String userId) {
		mUserId = userId;
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
	
	public String getUserId() {
		return mUserId;
	}
	
	public GoogleMapMarkerParameters setIsCenterOn(Boolean isCenterOn) {
		mIsCenterOn = isCenterOn;
		return this;
	}
	
	public Boolean isCenterOn() {
		return mIsCenterOn;
	}
	
	
	

}
