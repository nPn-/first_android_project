package com.gmail.npnster.first_project;

import java.util.ArrayList;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.text.format.DateUtils;

import com.gmail.npnster.first_project.api_params.GetMapMarkersRequest;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestRequest;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.Marker;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MapPresenter {

	private MapView mMapView;
	private Boolean isMapReady = false;
	private MapMarkers mMapMarkers;
	private Context mContext;
	private Bus mBus;
	private Intent requestMarkers;
	private int centerOnModeIndex = 0;
	private int centerOnPersonIndex = 0;
	private boolean mMarkersAreDirty = false;
	private GoogleMap.CancelableCallback mExpandMapCallback;

	public MapPresenter(Context context,  MapMarkers mapMarkers) {
        System.out.println("Constructing MapPresenter");
		mMapMarkers = mapMarkers;
		mContext = context;
		mBus = BusProvider.getInstance();
//		mBus.register(this);
		requestMarkers = new Intent(context, LocationMonitorService.class);
		requestMarkers.addCategory("REQUEST_MARKERS");
		setExpandMapCallback();
		

	}

	@Subscribe
	public void onGetMapMarkersRequestCompleted(GetMapMarkersResponse event) {
		System.out
				.println("inside map activity - onGetMapMarkersRequestCompleted");
		// GetUserProfileResponse params = event.getParams();
		if (event != null && event.getRawResponse() != null) {
			System.out
					.println(String
							.format("get marker request completed with a status of %d, isSuccessful = %b",
									event.getRawResponse().getStatus(),
									event.isSuccessful()));
		}
		if (event != null && event.isSuccessful()) {
			// mMapView.clearMap();
			if (!mMarkersAreDirty && mMapMarkers.hasSameUserList(event.getMarkers())) {
				mMarkersAreDirty = false;
				System.out.println(String.format("updating %d markers",
						mMapMarkers.size()));

				mMapMarkers.updateLocationInfo(event.getMarkers(),
						centerOnModeIndex);
				for (MapMarker m : mMapMarkers.toArrayList()) {
					GoogleMapMarkerParameters parameters = m
							.getGoogleMapMarkerParameters();
					mMapView.updateMarker(parameters);
				}

			} else {
				System.out.println("replacing markers");
				mMapMarkers.clear();
				// ArrayList<com.google.android.gms.maps.model.Marker>
				// markerList =
				// new
				// ArrayList<com.google.android.gms.maps.model.Marker>();
				// markerList.get(0).

				for (Marker m : event.getMarkers()) {
					mMapMarkers.add(new MapMarker(mContext, m));
					// it takes time to construct the new marker (download a bit
					// map)
					// so loading to the mapView is defered until the maker is
					// ready
					// maker is loaded on the map when we get a MakerReadyEvent
					// from the model

				}
				mMapView.setupCenterOnSpinner(mMapMarkers,centerOnPersonIndex);
				mMapView.setIntialCenterOnImage(mMapMarkers.get(centerOnPersonIndex));
			}
			isMapReady = true;
			recenterMap();

			System.out.println("finished processing returned markers");
		}

	}

	public void recenterMap() {
		if (!isMapReady) return;
		MapMarker mapMarker = mMapMarkers.get(centerOnPersonIndex);
		switch (centerOnModeIndex) {
		case 0:
			if (mapMarker != null) {
				LatLng latLng = new LatLng(mapMarker.getLatitude(),
						mapMarker.getLongitude());
				mMapView.centerMapAt(latLng);
			}
			break;
		case 1:
			LatLng newMapCenter = mMapMarkers.getCenterOfMarkers();
			if (newMapCenter != null)
//				mMapView.centerMapAt(newMapCenter);
				System.out.println("recentering on group");
			 	recenterAndExpandMapViewIfNeeded(newMapCenter);
//			 	mMapView.centerMapAt(newMapCenter);
			break;
		}
		if (mapMarker != null)  mMapView.bringMarkerForUserIdToFront(mapMarker.getUserId());

	}

	private void recenterAndExpandMapViewIfNeeded(LatLng newMapCenter) {
		mMapView.centerMapAt(newMapCenter,mExpandMapCallback);
//		Projection currentProjection = mMapView.getCurrentMapProjection();
//		MapViewPortRegion mapViewPortRegion = new MapViewPortRegion(currentProjection);
//		LatLngBounds requiredBounds = mMapMarkers.getLatLngBounds();
//		LatLngBounds newBounds = mapViewPortRegion.getNextBounds(requiredBounds, 100, 100);
//		mMapView.setMapBounds(newBounds);

	}

	@Subscribe
	public void onMarkerReadyEvent(MarkerReadyEvent event) {
		MapMarker mapMarker = event.getMapMarker();
		GoogleMapMarkerParameters markerParameters = new GoogleMapMarkerParameters(
				mapMarker.getUserId()).setBitmap(mapMarker.getBitmap())
				.setLatLng(mapMarker.getLatLng()).setTitle(mapMarker.getName())
				.setCircleRadius(mapMarker.getAccuracy());
		if (mapMarker.getUserId().equals(
				mMapMarkers.get(centerOnPersonIndex).getUserId())) {
			markerParameters.setIsCenterOn(true);
		}
		// System.out.println(String.format("marker for %s , fix time = %s",
		// mapMarker.getName(),new Date(mapMarker.getLocationFixTime())));
		mMapView.addMarkerToMap(markerParameters);

	}

	public void centerOnPersonSelected(int position) {
		centerOnPersonIndex = position;
		recenterMap();
		MapMarker mapMarker = mMapMarkers.get(position);
		if (mapMarker != null) {
//			LatLng latLng = new LatLng(mapMarker.getLatitude(),
//					mapMarker.getLongitude());
//			mMapView.centerMapAt(latLng);
			mMapView.setCenterOnButtonImage(mapMarker.getBitmap());

		}

	}

	public void refreshMap() {
		mContext.startService(requestMarkers);
		mBus.post(new PushLocationsUpdateRequestRequest());
		// mBus.post(new GetMapMarkersRequest());

	}

	public void nextButtonClicked() {
		int nextPosition = (centerOnPersonIndex + 1) % mMapMarkers.size();
		mMapView.setCenterOnSpinnerSelection(nextPosition);
		centerOnPersonSelected(nextPosition);
	}

	public void previousButtonClicked() {
		int previousPosition;
		int n = centerOnPersonIndex - 1;
		if (n >= 0) {
			previousPosition = n;
		} else {
			previousPosition = 3 + n % 3;
		}
		mMapView.setCenterOnSpinnerSelection(previousPosition);
		centerOnPersonSelected(previousPosition);
	}

	public void centerOnModeSelected(int position) {
		centerOnModeIndex = position;
		recenterMap();

	}

	public void markerClicked(com.google.android.gms.maps.model.Marker marker) {
		System.out.println(String.format("marker id %s was clicked",
				marker.getId()));
		String userId = mMapView.getUserIdForMarker(marker);
		System.out.println(String.format("marker is for user id = %s", userId));
		;
		MapMarker mapMarker = mMapMarkers.findByUserId(userId);
		String infoWindowData = mapMarker.getInfoWindowData();
		if (mapMarker != null) {
			mMapView.centerMapAt(mapMarker.getLatLng());
			mMapView.bringMarkerForUserIdToFront(userId);
			mMapView.setUserInfoWindow(userId, infoWindowData);
		}

	}

	public void centerOnImageclicked() {
		mMapView.clickOnMarkerForUserId(mMapMarkers.get(centerOnPersonIndex)
				.getUserId());

	}
	
	public void setExpandMapCallback() {
		mExpandMapCallback = new GoogleMap.CancelableCallback() {

			@Override
			public void onCancel() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onFinish() {
				System.out.println("map is now centered on the group");
				LatLngBounds mapBounds = mMapView.getCurrentMapBounds();
				boolean areAllMarkersContained = mMapMarkers.areAllMarkersContained(mapBounds);
				if (areAllMarkersContained) {
					System.out.println("map size is OK");
				} else {
					System.out.println("map needs to be expanded");
					LatLngBounds markerBounds = mMapMarkers.getLatLngBounds();
					mMapView.setMapBounds(markerBounds);
				}
				
			}
			
		};
			
		
		
	}
	
	public void setMapView(MapView mapView) {
		mMapView = mapView;
		mapView.setmMapPresenter(this);
	}
	
	public void reinitMapView() {
		mMapView.setupCustomActionBar();
		mMapView.setupCenterOnSpinner(mMapMarkers, centerOnPersonIndex);
		mMapView.setIntialCenterOnImage(mMapMarkers.get(centerOnPersonIndex));
		mMapView.setCenterOnMode(centerOnModeIndex);
//		mMapView.clearMap();
//		mMarkersAreDirty = true;
		
	}
	



}
