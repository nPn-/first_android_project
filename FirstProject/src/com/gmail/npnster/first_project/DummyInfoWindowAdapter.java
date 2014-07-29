package com.gmail.npnster.first_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class DummyInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private Context context = null;

    public DummyInfoWindowAdapter(Context context) {
        this.context = context;
    }



    // Hack to prevent info window from displaying: use a 0dp/0dp frame
    @Override
    public View getInfoWindow(Marker marker) {
		LayoutInflater inflater = (LayoutInflater) context
		        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.dummy_info_window, null);
        return v;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
