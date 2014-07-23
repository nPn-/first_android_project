package com.gmail.npnster.first_project;
import java.util.ArrayList;

import com.gmail.npnster.first_project.MapMarker;


public class MapMarkers {
	
	private ArrayList<MapMarker> markers;
	
	public ArrayList<MapMarker> toArrayList() {
		return markers;
	}


	public MapMarkers() {
		markers = new ArrayList<MapMarker>();
	}


	public boolean add(MapMarker object) {
		return markers.add(object);
	}

	public void clear() {
		markers.clear();
	}


}
