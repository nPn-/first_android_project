package com.gmail.npnster.first_project;

public class MarkerReadyEvent {
	
	private MapMarker mMapMarker;

	public MarkerReadyEvent(MapMarker mapMarker) {
		mMapMarker = mapMarker;
	}

	public MapMarker getMapMarker() {
		return mMapMarker;
	}
	

}
