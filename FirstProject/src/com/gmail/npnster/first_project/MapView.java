package com.gmail.npnster.first_project;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.internal.widget.ActionBarView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.gmail.npnster.first_project.R.color;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.CancelableCallback;
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

public class MapView implements OnMarkerClickListener {

	private GoogleMap mMap;
	private View mActionBarView;
	private MapPresenter mMapPresenter;
	private Context mContext;
	private CenterOnSpinnerAdapter centerOnSpinnerAdapter;
//	private int centerOnPersonSelectedPosition = 0;
//	private int centerOnModePosition = 0;
	private Spinner spinner;
	private GoogleMapMarkerList mMarkers;
	private DummyInfoWindowAdapter dummyInfoWindowAdapter;
	private RealInfoWindowAdapter realInfoWindowAdapter;
	private GoogleMap.OnMarkerClickListener dummyMarkerOnClickListener ;

	public MapView(Context context, GoogleMap map, View actionBarView) {
		mMap = map;
		mActionBarView = actionBarView;
		mContext = context;
		mMarkers = new GoogleMapMarkerList();
		dummyInfoWindowAdapter = new DummyInfoWindowAdapter(context);
		realInfoWindowAdapter = new RealInfoWindowAdapter(context);
		setupCustomActionBar();
		mMap.setOnMarkerClickListener(this);
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
		// seems to really slow down the map
		mMap.setInfoWindowAdapter(dummyInfoWindowAdapter);
		m.getMarker().showInfoWindow();
		mMap.setInfoWindowAdapter(null);
	}

	void setupCustomActionBar() {

		Integer[] imageArray = new Integer[] { R.drawable.ic_action_person,
				R.drawable.ic_action_group, R.drawable.ic_action_place };
		ImageArrayAdapter imageArrayAdapter = new ImageArrayAdapter(mContext,
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
//						centerOnModePosition = position;
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

		ImageButton refresh = (ImageButton) mActionBarView
				.findViewById(R.id.refresh);
		refresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("refresh button clicked");
				mMapPresenter.refreshMap();

			}
		});

		ImageButton centerOn = (ImageButton) mActionBarView
				.findViewById(R.id.center_on);
		centerOn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("recenter button clicked");
				mMapPresenter.centerOnImageclicked();

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
		centerOnSpinnerAdapter = new CenterOnSpinnerAdapter(mContext,
				mapMarkers.toArrayList());
		spinner.setAdapter(centerOnSpinnerAdapter);
		System.out.println(String.format(
				"number items in spinner adapter = %d",
				centerOnSpinnerAdapter.getCount()));
		// centerOnSpinnerAdapter.notifyDataSetChanged();
		spinner.setSelection(initialPosition);
		
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(String.format("person number = %d, id = %d",
						position, id));
//				centerOnPersonSelectedPosition = position;
				mMapPresenter.centerOnPersonSelected(position);

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
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
				Picasso.with(mContext).load(gavatarUrl).into((ImageButton) mActionBarView.findViewById(R.id.center_on));
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



}
