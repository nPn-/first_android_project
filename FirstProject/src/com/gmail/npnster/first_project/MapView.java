package com.gmail.npnster.first_project;

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

public class MapView {

	private GoogleMap mMap;
	private View mActionBarView;
	private MapPresenter mMapPresenter;
	private Context mContext;
	private CenterOnSpinnerAdapter centerOnSpinnerAdapter;
//	private int centerOnPersonSelectedPosition = 0;
//	private int centerOnModePosition = 0;
	private Spinner spinner;

	public MapView(Context context, GoogleMap map, View actionBarView) {
		mMap = map;
		mActionBarView = actionBarView;
		mContext = context;
		setupCustomActionBar();
	}

	public void setmMapPresenter(MapPresenter mapPresenter) {
		mMapPresenter = mapPresenter;
	}

	public void clearMap() {
		mMap.clear();
	}

	public GoogleMap getMap() {
		return mMap;
	}

	public com.google.android.gms.maps.model.Marker addMarkerToMap(MapMarker mapMarker) {
		Bitmap bitmap = mapMarker.getBitmap();
		BitmapDescriptor bitmapDescriptor;
		if (bitmap == null) {
			bitmapDescriptor = BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
		} else {
			bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(bitmap);
		}
		com.google.android.gms.maps.model.Marker googleMapMarker = mMap
				.addMarker(new MarkerOptions()
						.position(
								new LatLng(mapMarker.getLatitude(), mapMarker
										.getLongitude()))
						.title(mapMarker.getName()).icon(bitmapDescriptor));
		return googleMapMarker;

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

}
