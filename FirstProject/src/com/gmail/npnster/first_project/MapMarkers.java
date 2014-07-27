package com.gmail.npnster.first_project;
import java.util.ArrayList;
import java.util.List;

import com.gmail.npnster.first_project.MapMarker;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.Marker;
import com.squareup.otto.Bus;


public class MapMarkers {
	
	private ArrayList<MapMarker> markers;
	private Bus mBus;
	
	public MapMarker get(int index) {
		return markers.get(index);
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
	
	public Boolean hasSameUserList(ArrayList<Marker> newMarkers) {
	    ArrayList<String> newMarkersUserIds = new ArrayList<String>();
	    for (Marker m : newMarkers ) {
	    	newMarkersUserIds.add(m.getUserId());
	    }
		return this.getUserList().equals(newMarkersUserIds);
	}


	public void updateLocationInfo(List<Marker> markers, int centerOnMarkerIndex) {
		int i = 0;
		MapMarker lastMarkerToDraw = null;
		for (Marker m : markers) {
			MapMarker marker = get(i);
			if (i == centerOnMarkerIndex) lastMarkerToDraw = marker;
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
			if ( i != centerOnMarkerIndex) mBus.post(new MarkerReadyEvent(marker));
						
		}
		mBus.post(new MarkerReadyEvent(lastMarkerToDraw));
		
	}


	public int size() {
		return markers.size();
	}


}
