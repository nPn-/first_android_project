package com.gmail.npnster.first_project;

import javax.inject.Inject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ActionMode;
import android.view.MenuItem;

import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.RailsMarker;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.GoogleMap;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class MapPresenter {

	private MapView mMapView;
	private Boolean isMapReady = false;
	private MapMarkers mMapMarkers;
	private Context mContext;
	private int centerOnModeIndex = 0;
	private int centerOnPersonIndex = 0;
	private GoogleMap.CancelableCallback mExpandMapCallback;
	@Inject Bus mBus;

	public MapPresenter(Context context,MapMarkers mapMarkers) {
		System.out.println("Constructing MapPresenter");
		Injector.getInstance().inject(this);
		mMapMarkers = mapMarkers;
		mContext = context;
		setExpandMapCallback();

	}

	@Subscribe
	public void onGetMapMarkersRequestCompleted(GetMapMarkersResponse event) {
		System.out
				.println("inside map activity - onGetMapMarkersRequestCompleted");
		if (event != null && event.getRawResponse() != null) {
			System.out
					.println(String
							.format("get marker request completed with a status of %d, isSuccessful = %b",
									event.getRawResponse().getStatus(),
									event.isSuccessful()));
		}
		if (event != null && event.isSuccessful()) {
			if (mMapMarkers.hasSameUserList(event.getMarkers())) {
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
				String currentCenterOnUser = null; 
				if (mMapMarkers.get(centerOnPersonIndex) != null ) {
				    currentCenterOnUser = mMapMarkers.get(centerOnPersonIndex).getUserId();
				}
				mMapMarkers.clear();
				mMapView.clearMap();

				for (RailsMarker m : event.getMarkers()) {
					mMapMarkers.add(new MapMarker(mContext, m));
					System.out.println(String.format("requesting profile for user name = %s,  id = %s", m.getName(), m.getUserId()));
					mBus.post(new GetUserProfileRequest(m.getUserId()));

				}
				int newIndexOfCurrentCenterOnUser = mMapMarkers.getIndexOfUserId(currentCenterOnUser);
				if (newIndexOfCurrentCenterOnUser >= 0) {
					setCenterOnPosition(newIndexOfCurrentCenterOnUser);
				} else {
					 setCenterOnPosition(0);
				}
				mMapView.setupCenterOnSpinner(mMapMarkers, centerOnPersonIndex);
				mMapView.setIntialCenterOnImage(mMapMarkers
						.get(centerOnPersonIndex));
			}
			isMapReady = true;
			recenterMap();
			System.out.println("finished processing returned markers");
		}

	}

	public void recenterMap() {
		if (!isMapReady)
			return;
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
				System.out.println("recentering on group");
			recenterAndExpandMapViewIfNeeded(newMapCenter);
			break;
		}
		if (mapMarker != null)
			mMapView.bringMarkerForUserIdToFront(mapMarker.getUserId());

	}

	private void recenterAndExpandMapViewIfNeeded(LatLng newMapCenter) {
		mMapView.centerMapAt(newMapCenter, mExpandMapCallback);

	}

	@Subscribe
	public void onMarkerReadyEvent(MarkerReadyEvent event) {
		MapMarker mapMarker = event.getMapMarker();
		GoogleMapMarkerParameters markerParameters = new GoogleMapMarkerParameters();
		markerParameters.setUserId(mapMarker.getUserId())
				.setBitmap(mapMarker.getBitmap())
				.setLatLng(mapMarker.getLatLng()).setTitle(mapMarker.getName())
				.setCircleRadius(mapMarker.getAccuracy());
		if (mapMarker.getUserId().equals(
				mMapMarkers.get(centerOnPersonIndex).getUserId())) {
			markerParameters.setIsCenterOn(true);
		}
		mMapView.addMarkerToMap(markerParameters);

	}

	public void centerOnPersonSelected(int position) {
		centerOnPersonIndex = position;
		recenterMap();
		MapMarker mapMarker = mMapMarkers.get(position);
		if (mapMarker != null) {
			mMapView.setCenterOnButtonImage(mapMarker.getBitmap());
		}

	}

	public void refreshMap() {
		// no longer needed map is refresehd while fragment is active - remove
		// calls to this method
		System.out
				.println("inside map presenter method refreshMap - this no longer does anything please remove calls");

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
		System.out.println(String.format("marker is for user id = %s,", userId));
		;
		MapMarker mapMarker = mMapMarkers.findByUserId(userId);
		String infoWindowData = mapMarker.getInfoWindowData();
		if (mapMarker != null) {
			mMapView.centerMapAt(mapMarker.getLatLng());
			mMapView.bringMarkerForUserIdToFront(userId);
			mMapView.setUserInfoWindow(userId, infoWindowData);
			System.out.println(String.format("user phone number = %s", mapMarker.getPhoneNumber()));
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

			}

			@Override
			public void onFinish() {
				System.out.println("map is now centered on the group");
				LatLngBounds mapBounds = mMapView.getCurrentMapBounds();
				boolean areAllMarkersContained = mMapMarkers
						.areAllMarkersContained(mapBounds);
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

	}

	public int getGenterOnPosition() {  
		return centerOnPersonIndex;
	}

	public int getGenterOnMode() {
		return centerOnModeIndex;
	}

	public void setCenterOnPosition(int position) {
		centerOnPersonIndex = position;
		mMapView.setCenterOnSpinnerSelection(position);
	}

	public void setCenterOnMode(int mode) {
		centerOnModeIndex = mode;
		mMapView.setCenterOnMode(mode);
	}
	
	@Subscribe public void onGetUserProfileRequestCompleted(GetUserProfileResponse event) {
		System.out.println(String.format("got an updated users profile, name = %s, id = %s, phone number = %s", event.getName(), event.getId(), event.getPhoneNumber()));
		MapMarker marker = mMapMarkers.findByUserId(event.getId().toString());
		if (marker != null) marker.setPhoneNumber(event.getPhoneNumber());
		
	}

	public void centerOnImageLongClicked() {
		System.out.println("inside presenter center on image long clikcked");
		mMapView.startActionMode();
		
	}


	public void callPerson() {
		String phoneNumber = mMapMarkers.get(centerOnPersonIndex).getPhoneNumber();
		String uriString = String.format("tel:%s",phoneNumber);
		System.out.println(String.format("call person with phone number = %s", phoneNumber));
		Intent phoneCallIntent = new Intent(Intent.ACTION_CALL);
		phoneCallIntent.setData(Uri.parse(uriString));
		mContext.startActivity(phoneCallIntent);
		
		
	}

	public void messagePerson() {
		String phoneNumber = mMapMarkers.get(centerOnPersonIndex).getPhoneNumber();
		String uriString = String.format("sms:%s",phoneNumber);
		System.out.println(String.format("message person with phone number = %s", mMapMarkers.get(centerOnPersonIndex).getPhoneNumber()));
		Intent txtMessageIntent = new Intent(Intent.ACTION_VIEW);
		txtMessageIntent.setData(Uri.parse(uriString));
		mContext.startActivity(txtMessageIntent);
		
	}

	public void mapLongClicked() {
		mMapView.startActionMode();
		
	}

	public void centerOnPersonLongClicked(int position, long id) {
		System.out.println("map view spinner item long clicked");
		setCenterOnPosition(position);
		mMapView.startActionMode();
		
	}

}
