package com.gmail.npnster.first_project.api_params;

import android.location.Location;

public class PostLocationRequest extends TokenBasedRequest {
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
	private String gcmRegKey;
	
	public PostLocationRequest(String gcmRegKey, Location location) {
		provider = location.getProvider();
		latitude = location.getLatitude();
		longitude = location.getLongitude();
		accuracy = location.getAccuracy();
		altitude = location.getAltitude();
		bearing = location.getBearing();
		speed = location.getSpeed();
		time = location.getTime();
		has_accuracy = location.hasAccuracy();
		has_altitude = location.hasAltitude();
		has_bearing = location.hasBearing();
		has_speed = location.hasSpeed();
		this.gcmRegKey = gcmRegKey;
		
	}
	
	public String getGcmRegKey() {
		return gcmRegKey;
	}
	
	

}
