package com.gmail.npnster.first_project;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.gmail.npnster.first_project.api_params.GetMapMarkersRequest;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestRequest;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.Marker;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MapPresenter {

	private MapView mMapView;
	private MapMarkers mMapMarkers;
	private Context mContext;
	private Bus mBus;
	private Intent requestMarkers;
	private int centerOnModeIndex;
	private int centerOnPersonIndex;

	public MapPresenter(Context context, MapView mapView, MapMarkers mapMarkers) {
		mMapView = mapView;
		mMapMarkers = mapMarkers;
		mContext = context;
		mBus = BusProvider.getInstance();
		mBus.register(this);
		mapView.setmMapPresenter(this);
		requestMarkers = new Intent(context, LocationMonitorService.class);
		requestMarkers.addCategory("REQUEST_MARKERS");

	}

	@Subscribe
	public void onGetMapMarkersRequestCompleted(GetMapMarkersResponse event) {
		System.out
				.println("inside map activity - onGetMapMarkersRequestCompleted");
		// GetUserProfileResponse params = event.getParams();
		System.out.println(event.getMarkers().size());
		mMapView.clearMap();
		if (mMapMarkers.hasSameUserList(event.getMarkers())) {
			System.out.println("updating markers");

			mMapMarkers.updateLocationInfo(event.getMarkers(),centerOnModeIndex);
		} else {
			System.out.println("replacing markers");
			mMapMarkers.clear();
			// ArrayList<com.google.android.gms.maps.model.Marker> markerList =
			// new
			// ArrayList<com.google.android.gms.maps.model.Marker>();
			// markerList.get(0).
			
			for (Marker m : event.getMarkers()) {
				LatLng pos = new LatLng(m.getLatitude(), m.getLongitude());
				System.out.println(m.getLatitude());
				System.out.println(m.getLongitude());
				System.out.println(new Date(m.getLocationFixTime()));

				mMapMarkers.add(new MapMarker(mContext, m));

			}
			mMapView.setupCenterOnSpinner(mMapMarkers);
		}
		centerOnPersonSelected(centerOnPersonIndex);
		System.out.println("finished processing returned markers");

	}

	@Subscribe
	public void onMarkerReadyEvent(MarkerReadyEvent event) {
		MapMarker mapMarker = event.getMapMarker();
		com.google.android.gms.maps.model.Marker googleMapMarker;
		googleMapMarker = mMapView.addMarkerToMap(mapMarker);
		mapMarker.setGoogleMapMarker(googleMapMarker);
		
		

	}

	public void centerOnPersonSelected(int position) {
		centerOnPersonIndex = position;
		MapMarker mapMarker = mMapMarkers.get(position);
		LatLng latLng = new LatLng(mapMarker.getLatitude(),
				mapMarker.getLongitude());
		mMapView.centerMapAt(latLng);

	}

	public void refreshMap() {
		mContext.startService(requestMarkers);
		// mBus.post(new PushLocationsUpdateRequestRequest());
		// mBus.post(new GetMapMarkersRequest());

	}

	public void nextButtonClicked() {
		int nextPosition = ( centerOnPersonIndex + 1 ) % mMapMarkers.size();
		mMapView.setCenterOnSpinnerSelection(nextPosition);
		centerOnPersonSelected(nextPosition);
	}

	public void previousButtonClicked() {
		int previousPosition;
		int n = centerOnPersonIndex - 1;
		if (n >= 0) {
			previousPosition = n ;
		} else {
			previousPosition = 3 + n % 3;
		}
		mMapView.setCenterOnSpinnerSelection(previousPosition);
		centerOnPersonSelected(previousPosition);
	}

	public void centerOnModeSelected(int position) {
		centerOnModeIndex = position;
		
	}

}
