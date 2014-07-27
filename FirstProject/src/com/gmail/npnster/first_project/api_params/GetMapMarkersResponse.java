package com.gmail.npnster.first_project.api_params;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetMapMarkersResponse extends BaseResponse {
	@Expose
	private ArrayList<Marker> markers = new ArrayList<Marker>();

	public ArrayList<Marker> getMarkers() {
	return markers;
	}

	public void setMarkers(ArrayList<Marker> markers) {
	this.markers = markers;
	}
	
	public class Marker {

		@Expose
		private String name;
		@SerializedName("user_id")
		@Expose
		private String userId;
		@SerializedName("marker_id")
		@Expose
		private String markerId;
		@SerializedName("device_id")
		@Expose
		private String deviceId;
		@SerializedName("location_id")
		@Expose
		private String locationId;
		@SerializedName("gravatar_url")
		@Expose
		private String gravatarUrl;
		@SerializedName("location_provider")
		@Expose
		private String locationProvider;
		@SerializedName("has_accuracy")
		@Expose
		private Boolean hasAccuracy;
		@SerializedName("has_altitude")
		@Expose
		private Boolean hasAltitude;
		@SerializedName("has_bearing")
		@Expose
		private Boolean hasBearing;
		@SerializedName("has_speed")
		@Expose
		private Boolean hasSpeed;
		@Expose
		private Double latitude;
		@Expose
		private Double longitude;
		@Expose
		private Double altitude;
		@Expose
		private Float bearing;
		@Expose
		private Float speed;
		@Expose
		private Float accuracy;
		@SerializedName("location_fix_time")
		@Expose
		private Long locationFixTime;
		@SerializedName("marker_created_time")
		@Expose
		private Long markerCreatedTime;
		@SerializedName("marker_updated_time")
		@Expose
		private Long markerUpdatedTime;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getMarkerId() {
			return markerId;
		}

		public void setMarkerId(String markerId) {
			this.markerId = markerId;
		}

		public String getDeviceId() {
			return deviceId;
		}
		
		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}
		
		public String getLocationId() {
			return locationId;
		}

		public void setLocationId(String locationId) {
			this.locationId = locationId;
		}

		public String getGravatarUrl() {
			return gravatarUrl;
		}
		
		public void setGravatarUrl(String gravatarUrl) {
			this.gravatarUrl = gravatarUrl;
		}
		
		public String getLocationProvider() {
			return locationProvider;
		}

		public void setlocationProvider(String locationProvider) {
			this.locationProvider = locationProvider;
		}

		public Boolean hasAccuracy() {
			return hasAccuracy;
		}

		public void setHasAccuracy(Boolean hasAccuracy) {
			this.hasAccuracy = hasAccuracy;
		}

		public Boolean hasAltitude() {
			return hasAltitude;
		}

		public void setHasAltitude(Boolean hasAltitude) {
			this.hasAltitude = hasAltitude;
		}

		public Boolean hasBearing() {
			return hasBearing;
		}

		public void setHasBearing(Boolean hasBearing) {
			this.hasBearing = hasBearing;
		}

		public Boolean hasSpeed() {
			return hasSpeed;
		}

		public void setHasSpeed(Boolean hasSpeed) {
			this.hasSpeed = hasSpeed;
		}

		public Double getLatitude() {
			return latitude;
		}

		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}

		public Double getLongitude() {
			return longitude;
		}

		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}

		public Double getAltitude() {
			return altitude;
		}

		public void setAltitude(Double altitude) {
			this.altitude = altitude;
		}

		public Float getBearing() {
			return bearing;
		}

		public void setBearing(Float bearing) {
			this.bearing = bearing;
		}

		public Float getSpeed() {
			return speed;
		}
		
		public void setSpeed(Float speed) {
			this.speed = speed;
		}
		
		public Float getAccuracy() {
			return accuracy;
		}

		public void setAccuracy(Float accuracy) {
			this.accuracy = accuracy;
		}

		public Long getLocationFixTime() {
			return locationFixTime;
		}

		public void setLocationFixTime(Long locationFixTime) {
			this.locationFixTime = locationFixTime;
		}

		public Long getMarkerCreatedTime() {
			return markerCreatedTime;
		}

		public void setMarkerCreatedTime(Long markerCreatedTime) {
			this.markerCreatedTime = markerCreatedTime;
		}

		public Long getMarkerUpdatedTime() {
			return markerUpdatedTime;
		}

		public void setMarkerUpdatedTime(Long markerUpdatedTime) {
			this.markerUpdatedTime = markerUpdatedTime;
		}

	}

}
