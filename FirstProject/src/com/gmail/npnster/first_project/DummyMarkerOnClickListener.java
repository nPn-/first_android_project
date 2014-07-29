package com.gmail.npnster.first_project;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class DummyMarkerOnClickListener implements GoogleMap.OnMarkerClickListener {

	@Override
	public boolean onMarkerClick(Marker marker) {
        //In order for the showInfoWindow() method to bring the marker to front
        marker.setTitle("whatever");

        marker.showInfoWindow();
		return true;
	}

}
