package com.gmail.npnster.first_project;

public class GoogleMapMarker {
	private com.google.android.gms.maps.model.Marker mMarker;
	private String mUserId;
	private Boolean mIsCenterOn;
	
	GoogleMapMarker (com.google.android.gms.maps.model.Marker marker, String userId) {
		mMarker = marker;
		mUserId = userId;
	    mIsCenterOn = false;
	}
	
	public com.google.android.gms.maps.model.Marker getMarker() {
		return mMarker;
	}
	public void setMarker(com.google.android.gms.maps.model.Marker marker) {
		mMarker = marker;
	}
	public String getUserId() {
		return mUserId;
	}
	public void setUserId(String userId) {
		mUserId = userId;
	}
	
	public Boolean isCenterOn() {
		return mIsCenterOn;
	}
	
	public void setIsCenterOn(Boolean isCenterOn) {
		mIsCenterOn = isCenterOn;
	}
	

}
