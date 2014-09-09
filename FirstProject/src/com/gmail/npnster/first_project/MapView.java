package com.gmail.npnster.first_project;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.view.ActionMode.Callback;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class MapView implements OnMarkerClickListener, OnMapLongClickListener, android.view.ActionMode.Callback {

	private GoogleMap mMap;
	private View mActionBarView;
	private MapPresenter mMapPresenter;
	private Activity mActivity;
	private CenterOnSpinnerAdapter centerOnSpinnerAdapter;
	private Spinner spinner;
	private GoogleMapMarkerList mMarkers;
	private DummyInfoWindowAdapter dummyInfoWindowAdapter;
	private RealInfoWindowAdapter realInfoWindowAdapter;
	private GoogleMap.OnMarkerClickListener dummyMarkerOnClickListener ;

	public MapView(Activity activity, GoogleMap map, View actionBarView) {
		mMap = map;
		mActionBarView = actionBarView;
		mActivity = activity;
		mMarkers = new GoogleMapMarkerList();
		dummyInfoWindowAdapter = new DummyInfoWindowAdapter(mActivity);
		realInfoWindowAdapter = new RealInfoWindowAdapter(mActivity);
		setupCustomActionBar();
		attachMapListeners();
		
	}
	
	public void setActivity (Activity activity) {
		mActivity = activity;
		  
	}
	
	public void attachMapListeners() {
		System.out.println("attaching map listeners");
		mMap.setOnMarkerClickListener(this);
		mMap.setOnMapLongClickListener(null);
		mMap.setOnMapLongClickListener(this);
	}

	public void setmMapPresenter(MapPresenter mapPresenter) {
		mMapPresenter = mapPresenter;
	}

	public void clearMap() {
		mMap.clear();
		mMarkers.clear();
	}

	public GoogleMap getMap() {
		return mMap;
	}

	public void addMarkerToMap(GoogleMapMarkerParameters parameters) {
		Bitmap bitmap = parameters.getBitmap();
		BitmapDescriptor bitmapDescriptor;
		System.out.println(String.format("rad = %f",parameters.getCircleRadius() ));
		if (bitmap == null) {
			bitmapDescriptor = BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
		} else {
			bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
		}
		
		com.google.android.gms.maps.model.Marker gMarker = mMap
				.addMarker(new MarkerOptions()
				.position(parameters.getLatLng())
				.title(parameters.getTitle())
				.icon(bitmapDescriptor));
		com.google.android.gms.maps.model.Circle gCircle = mMap
				.addCircle(new CircleOptions()
						.center(parameters.getLatLng())
						.fillColor(0x200000ff)
						.strokeColor(Color.BLUE)
						.strokeWidth(3.0f)
						.radius(parameters.getCircleRadius()));
		GoogleMapMarker googleMapMarker = new GoogleMapMarker( parameters.getUserId(), gMarker, gCircle);
		// add any other parameters not covered by the constructor here
		googleMapMarker.setIsCenterOn(parameters.isCenterOn());
		mMarkers.add(googleMapMarker);
		for (GoogleMapMarker m : mMarkers.getMarkerList() ) {
			if (m.isCenterOn()) {
				bringMarkerToFront(m);
			}
			
		}
		
		return ;

	}
	
	void bringMarkerForUserIdToFront(String userId) {
		for (GoogleMapMarker m : mMarkers.getMarkerList() ) {
			if (m.getUserId().equalsIgnoreCase(userId)) {
				bringMarkerToFront(m);
			}
		}
	}
	
	void bringMarkerToFront(GoogleMapMarker m) {
		// hack to get center user to be shown
		mMap.setInfoWindowAdapter(dummyInfoWindowAdapter);
		m.getMarker().showInfoWindow();
		mMap.setInfoWindowAdapter(null);
	}

	void setupCustomActionBar() {

		Integer[] imageArray = new Integer[] { R.drawable.ic_action_person,
				R.drawable.ic_action_group, R.drawable.ic_action_place };
		ImageArrayAdapter imageArrayAdapter = new ImageArrayAdapter(mActivity,
				imageArray);
		Spinner centerOnMode = (Spinner) mActionBarView
				.findViewById(R.id.center_on_mode_spinner);
		centerOnMode.setAdapter(imageArrayAdapter);
		centerOnMode
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						System.out.println(String.format(
								"center on Mode  = %d, id = %d", position, id));
						mMapPresenter.centerOnModeSelected(position);
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

//		ImageButton refresh = (ImageButton) mActionBarView
//				.findViewById(R.id.refresh);
//		refresh.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				System.out.println("refresh button clicked");
//				mMapPresenter.refreshMap();
//
//			}
//		});

		ImageButton centerOn = (ImageButton) mActionBarView
				.findViewById(R.id.center_on);
		centerOn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("recenter button clicked");
				mMapPresenter.centerOnImageclicked();

			}
		});
		centerOn.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				System.out.println("recenter button long clicked");
				mMapPresenter.centerOnImageLongClicked();
				return true;
				
			}
		});

		ImageButton nextPerson = (ImageButton) mActionBarView
				.findViewById(R.id.next);
		nextPerson.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("next button clicked");
				mMapPresenter.nextButtonClicked();

			}
		});

		ImageButton previousPerson = (ImageButton) mActionBarView
				.findViewById(R.id.previous);
		previousPerson.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("pervious button clicked");
				mMapPresenter.previousButtonClicked();

			}
		});
		
		

	}  

	public void setupCenterOnSpinner(MapMarkers mapMarkers,int initialPosition) {
		spinner = (Spinner) mActionBarView.findViewById(R.id.spinner);
		centerOnSpinnerAdapter = new CenterOnSpinnerAdapter(mActivity,
				mapMarkers.toArrayList());
		spinner.setAdapter(centerOnSpinnerAdapter);
		System.out.println(String.format(
				"number items in spinner adapter = %d",
				centerOnSpinnerAdapter.getCount()));
		spinner.setSelection(initialPosition);
		
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(String.format("person number = %d, id = %d",
						position, id));
				mMapPresenter.centerOnPersonSelected(position);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		
		// the following is currently not supported for spinners
//		spinner.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//
//			@Override
//			public boolean onItemLongClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				System.out.println("mapView item long clicked");
//				mMapPresenter.centerOnPersonLongClicked(position, id);
//				return false;
//			}
//		});
	}

	
	public void centerMapAt(LatLng latlng, LatLngBounds bounds) {
		int padding = 100; 
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, padding);
			getMap().animateCamera(cameraUpdate);		
	}
	
	public void centerMapAt(LatLng latLng) {
		mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
	}


	public void setCenterOnSpinnerSelection(int position) {
		spinner.setSelection(position);

	}
	
	public void setCenterOnMode(int position) {
		Spinner centerOnModeSpinner = (Spinner) mActionBarView.findViewById(R.id.center_on_mode_spinner);
		centerOnModeSpinner.setSelection(position);
	}
	
	public void setCenterOnButtonImage(Bitmap bitmap) {
		ImageButton imageButton = (ImageButton) mActionBarView.findViewById(R.id.center_on);
		imageButton.setImageBitmap(bitmap);
		
	}

	public void setIntialCenterOnImage(MapMarker mapMarker) {
		if (mapMarker != null) {
			String gavatarUrl = mapMarker.getGravatarUrl();
			if (gavatarUrl != null) {
				Picasso.with(mActivity).load(gavatarUrl).into((ImageButton) mActionBarView.findViewById(R.id.center_on));
			}
		}
		
	}

	public void updateMarker(GoogleMapMarkerParameters parameters) {
		String userId = parameters.getUserId();
		GoogleMapMarker marker = mMarkers.findMarkerForUserId(userId);
		if (marker != null) { 
			marker.getMarker().setPosition(parameters.getLatLng());
			marker.getCircle().setRadius(parameters.getCircleRadius());
			marker.getCircle().setCenter(parameters.getLatLng());
		}
		
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		mMapPresenter.markerClicked(marker);
		return true;
	}

	public String getUserIdForMarker(
			com.google.android.gms.maps.model.Marker marker) {
		String found = null;
		for (GoogleMapMarker m : mMarkers.getMarkerList() ) {
			
			if ( m.getMarker().getId().equals(marker.getId()) ) {
				found = m.getUserId();
			}
			
		}
		return found;
	}

	public void setUserInfoWindow(String userId, String string) {
		System.out.println(String.format("info window = %s",string));
		com.google.android.gms.maps.model.Marker marker = findMarkerForUserId(userId);
		System.out.println(String.format("marker = %s",marker));

		if (marker != null) {
			System.out.println(String.format("setting title to %s",string));
		    mMap.setInfoWindowAdapter(realInfoWindowAdapter);
			marker.setSnippet(string);
			marker.showInfoWindow();
			
		}
		
	}

	private Marker findMarkerForUserId(String userId) {
		Marker found = null;
		for (GoogleMapMarker m : mMarkers.getMarkerList() ) {
			
			if ( m.getUserId().equals(userId) ) {
				found = m.getMarker();
			}
			
		}
		return found;
		
	}

	public void clickOnMarkerForUserId(String userId) {
		Marker marker = findMarkerForUserId(userId);
		if (marker != null) mMapPresenter.markerClicked(marker);
		
	}

	public LatLngBounds getCurrentMapBounds() {
		return getMap().getProjection().getVisibleRegion().latLngBounds;
	}

	public void setMapBounds(LatLngBounds newBounds) {
		int padding = 50; 
		System.out.println(String.format("newBounds = %s", newBounds));
		CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(newBounds, padding);
			getMap().animateCamera(cameraUpdate);
		
		
	}

	public Projection getCurrentMapProjection() {
		Projection currentProjection = null;
		if (mMap != null) { 
			currentProjection = mMap.getProjection();
		}
		return currentProjection;
	}

	public void centerMapAt(LatLng newMapCenter,
			CancelableCallback mExpandMapCallback) {
		mMap.animateCamera(CameraUpdateFactory.newLatLng(newMapCenter), mExpandMapCallback);
		
	}

	public void setActionBarView(View actionBarView) {
		mActionBarView = actionBarView;
		
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		System.out.println("in onCreateActionMode");
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.map_context_menu, menu);
		return true;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		System.out.println("in onPrepareActionMode");
		return false;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		switch (item.getItemId()) {
			case R.id.call_person : 
				mMapPresenter.callPerson();
				mode.finish();
				return true;
		case R.id.message_person :
				mMapPresenter.messagePerson();
				mode.finish();
				return true;
		default :
				return false;
		}
	}

	@Override
	public void onDestroyActionMode(ActionMode mode) {
		// TODO Auto-generated method stub
		
	}

	public void startActionMode() {     
		System.out.println("mapView starting action mode");
		mActivity.startActionMode(this);
		
	}

	@Override
	public void onMapLongClick(LatLng point) {
		System.out.println("map long clicked");
		mMapPresenter.mapLongClicked();
		
	}



}
