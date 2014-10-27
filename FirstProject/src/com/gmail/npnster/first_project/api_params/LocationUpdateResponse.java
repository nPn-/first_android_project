package com.gmail.npnster.first_project.api_params;

import android.location.Location;

public class LocationUpdateResponse<T> extends BaseResponse<T> {
	
	private String provider;
	private double latitude;
	private double longitude;
	private float accuracy;
	private double altitude;
	private float bearing;
	private float speed;
	private long time;
	private boolean has_accuracy;
	private boolean has_altitude;
	private boolean has_bearing;
	private boolean has_speed;	
	
	public Location getLocation() {
		Location location = new Location(provider);
		location.setLatitude(latitude);
		location.setLongitude(longitude);		
		location.setTime(time);		
		if (has_accuracy) location.setAccuracy(accuracy);
		if (has_altitude) location.setAltitude(altitude);
		if (has_bearing) location.setBearing(bearing);
		if (has_speed) location.setSpeed(speed);
		return location;
	}

}
