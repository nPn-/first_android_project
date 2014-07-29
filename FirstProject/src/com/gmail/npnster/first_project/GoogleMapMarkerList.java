package com.gmail.npnster.first_project;

import java.util.ArrayList;

public class GoogleMapMarkerList {
	private ArrayList<GoogleMapMarker> mMapMarkers ;
	

	public int size() {
		return mMapMarkers.size();
	}

	public GoogleMapMarkerList() {
		mMapMarkers = new ArrayList<GoogleMapMarker>();
	}

	public void add(GoogleMapMarker marker) {
		mMapMarkers.add(marker);
	}

	public GoogleMapMarker findMarkerForUserId(String userId) {
		GoogleMapMarker found = null;
		for (GoogleMapMarker m : mMapMarkers) {
			if (m.getUserId().equals(userId)) found = m;
		}
		return found;
	}
	
    public ArrayList<GoogleMapMarker> getMarkerList() {
    	return mMapMarkers;
    }
    
	public void clear() {
		mMapMarkers.clear();
	}

	

}
