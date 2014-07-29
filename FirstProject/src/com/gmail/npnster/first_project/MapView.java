package com.gmail.npnster.first_project;

import android.app.ActionBar;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.internal.widget.ActionBarView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class MapView {

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
	private GoogleMap.OnMarkerClickListener dummyMarkerOnClickListener ;

	public MapView(Context context, GoogleMap map, View actionBarView) {
		mMap = map;
		mActionBarView = actionBarView;
		mContext = context;
		mMarkers = new GoogleMapMarkerList();
		dummyInfoWindowAdapter = new DummyInfoWindowAdapter(context);
		setupCustomActionBar();
		
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
		GoogleMapMarker googleMapMarker = new GoogleMapMarker(gMarker, parameters.getUserId());
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
				R.drawable.ic_action_group };
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

	public void setupCenterOnSpinner(MapMarkers mapMarkers) {
		spinner = (Spinner) mActionBarView.findViewById(R.id.spinner);
		centerOnSpinnerAdapter = new CenterOnSpinnerAdapter(mContext,
				mapMarkers.toArrayList());
		spinner.setAdapter(centerOnSpinnerAdapter);
		System.out.println(String.format(
				"number items in spinner adapter = %d",
				centerOnSpinnerAdapter.getCount()));
		// centerOnSpinnerAdapter.notifyDataSetChanged();
		
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

	public void centerMapAt(LatLng latLng) {
		mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
//		mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
	}

//	public int getCenterOnPersonSelectedPosition() {
//		return centerOnPersonSelectedPosition;
//	}
//
//	public int getCenterOnModePosition() {
//		return centerOnModePosition;
//	}

	public void setCenterOnSpinnerSelection(int position) {
		spinner.setSelection(position);

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
		if (marker != null) marker.getMarker().setPosition(parameters.getLatLng());
		
	}

}
