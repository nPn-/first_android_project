package com.gmail.npnster.first_project;
import java.util.ArrayList;
import java.util.List;

import com.gmail.npnster.first_project.MapMarker;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.RailsMarker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.squareup.otto.Bus;


public class MapMarkers {
	
	private ArrayList<MapMarker> markers;
	private Bus mBus;
	
	public MapMarker get(int index) {
		MapMarker marker = null;
		if (index >= 0 && index < size()) marker = markers.get(index);
		return marker;
	}


	public ArrayList<MapMarker> toArrayList() {
		return markers;
	}


	public MapMarkers() {
		markers = new ArrayList<MapMarker>();
		mBus = BusProvider.getInstance();
	}


	public boolean add(MapMarker object) {
		return markers.add(object);
	}

	public void clear() {
		markers.clear();
	}
	
	public ArrayList<String> getUserList() {
		ArrayList<String> userList = new ArrayList<String>();
		for (MapMarker m : markers) {
			userList.add(m.getUserId());
		}
		return userList;		
	}
	
	public Boolean hasSameUserList(ArrayList<RailsMarker> newMarkers) {
	    ArrayList<String> newMarkersUserIds = new ArrayList<String>();
	    for (RailsMarker m : newMarkers ) {
	    	newMarkersUserIds.add(m.getUserId());
	    }
		return this.getUserList().equals(newMarkersUserIds);
	}

	public void updateLocationInfo(List<RailsMarker> markers, int centerOnMarkerIndex) {
		int i = 0;
		for (RailsMarker m : markers) {
			MapMarker marker = get(i);
			marker.setLatitude(m.getLatitude());
			marker.setLongitude(m.getLongitude());
			marker.setAltitude(m.getAltitude());
			marker.setAccuracy(m.getAccuracy());
			marker.setBearing(m.getBearing());
			marker.setSpeed(m.getSpeed());
			marker.setHasAccuracy(m.hasAccuracy());
			marker.setHasAltitude(m.hasAltitude());
			marker.setHasBearing(m.hasBearing());
			marker.setHasSpeed(m.hasSpeed());
			marker.setLocationFixTime(m.getLocationFixTime());
			i++;
						
		}
		
	}


	public int size() {
		return markers.size();
	}


	public MapMarker findByUserId(String userId) {
		MapMarker found = null;
		for (MapMarker m : toArrayList()) {
			if (m.getUserId().equals(userId)) {
				found = m;
			}
		}
		return found;
	}
	
	
	public LatLng getCenterOfMarkers() {
		LatLngBounds.Builder builder = LatLngBounds.builder();
		LatLng center = null;
		for (MapMarker m : toArrayList()) {
			builder = builder.include(m.getLatLng());
		}
		try {
			center = builder.build().getCenter();
		} catch (IllegalStateException e) {
			System.out.println(String.format("caught an illegalstate exception message =  %s,  returning null as marker center", e.getMessage()));
			
		}
		return center;
	}


	public LatLngBounds getLatLngBounds() {
		Double minLat =  9999.00;
		Double maxLat = -9999.00;
		Double minLng =  9999.00;
		Double maxLng = -9999.00;
		
		for (MapMarker m : toArrayList()) {
			if (m.getLatitude() > maxLat) maxLat = m.getLatitude(); 
			if (m.getLatitude() < minLat) minLat = m.getLatitude(); 
			if (m.getLongitude() > maxLng) maxLng = m.getLongitude(); 
			if (m.getLongitude() < minLng) minLng = m.getLongitude(); 
		}	
		return new LatLngBounds(new LatLng(minLat,minLng), new LatLng(maxLat,maxLng));
	}


	public boolean areAllMarkersContained(LatLngBounds mapBounds) {
		boolean areAllContained = true;
		for (MapMarker m : toArrayList()) {
			if (!mapBounds.contains(m.getLatLng())) areAllContained = false;
		}
		return areAllContained;
	}

}
