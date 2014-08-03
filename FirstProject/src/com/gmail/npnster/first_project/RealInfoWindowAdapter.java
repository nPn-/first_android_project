package com.gmail.npnster.first_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class RealInfoWindowAdapter implements InfoWindowAdapter {

	private View infoWindow = null;
	private LayoutInflater inflater = null;

	public RealInfoWindowAdapter(Context context) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getInfoWindow(Marker marker) {

		return null;
	}

	@Override
	public View getInfoContents(Marker marker) {
		if (infoWindow == null) {
			infoWindow = inflater.inflate(R.layout.real_info_window, null);
		}

		TextView textView = (TextView) infoWindow.findViewById(R.id.title);

		textView.setText(marker.getTitle());
		textView = (TextView) infoWindow.findViewById(R.id.snippet);
		textView.setText(marker.getSnippet());

		return (infoWindow);

	}
}
