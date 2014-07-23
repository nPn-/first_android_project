package com.gmail.npnster.first_project;

import java.util.ArrayList;
import java.util.Date;

import com.gmail.npnster.first_project.api_params.GetMapMarkersRequest;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.Marker;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

import android.app.Activity;
import android.app.ActionBar;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.os.Build;

public class MapActivity extends Activity {

	private Bus mBus;
	private GoogleMap map;
	private MapMarkers mapMarkerList;
	private CenterOnSpinnerAdapter centerOnSpinnerAdapter;
	private View actionBarView;

	private Bus getBus() {
		if (mBus == null) {
			mBus = BusProvider.getInstance();
		}
		return mBus;
	}

	public void setBus(Bus bus) {
		mBus = bus;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getBus().register(this);
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		System.out.println("posting get markers request to the bus");

		mBus.post(new PushLocationsUpdateRequestRequest());
		mBus.post(new GetMapMarkersRequest());
		// android.app.ActionBar actionBar = getActionBar();
		// View actionBarView = actionBar.getCustomView();
		// Spinner spinner = (Spinner) actionBarView.findViewById(R.id.spinner);
		// centerOnSpinnerAdapter = new CenterOnSpinnerAdapter(this,
		// mapMarkerList.toArrayList());
		// System.out.println(centerOnSpinnerAdapter.getCount());

		// mBus.post(new GetUsersRequest());
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getBus().unregister(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		setupCustomActionBar();
		mapMarkerList = new MapMarkers();

		// if (savedInstanceState == null) {
		// getSupportFragmentManager().beginTransaction()
		// .add(R.id.container, new PlaceholderFragment()).commit();
		// }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Subscribe
	public void onGetMapMarkersRequestCompleted(GetMapMarkersResponse event) {
		System.out
				.println("inside map activity - onGetMapMarkersRequestCompleted");

		// GetUserProfileResponse params = event.getParams();
		System.out.println(event.getMarkers().size());
		mapMarkerList.clear();
		// ArrayList<com.google.android.gms.maps.model.Marker> markerList = new
		// ArrayList<com.google.android.gms.maps.model.Marker>();
		// markerList.get(0).
		map.clear();
		for (Marker m : event.getMarkers()) {
			LatLng pos = new LatLng(m.getLatitude(), m.getLongitude());
			System.out.println(m.getLatitude());
			System.out.println(m.getLongitude());
			System.out.println(new Date(m.getLocationFixTime()));

			mapMarkerList.add(new MapMarker(this, m, map));

			map.setMyLocationEnabled(true);
		}
		android.app.ActionBar actionBar = getActionBar();
		// View actionBarView = actionBar.getCustomView();
		Spinner spinner = (Spinner) actionBarView.findViewById(R.id.spinner);
		centerOnSpinnerAdapter = new CenterOnSpinnerAdapter(this,
				mapMarkerList.toArrayList());
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

			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
		System.out.println("finished processing returned markers");

	}

	void setupCustomActionBar() {
		ActionBar actionBar = getActionBar();

		// add the custom view to the action bar
		actionBar.setCustomView(R.layout.map_actionbar_view);
		actionBarView = actionBar.getCustomView();

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME);

		Integer[] imageArray = new Integer[] { R.drawable.ic_action_person,
				R.drawable.ic_action_group };
		ImageArrayAdapter imageArrayAdapter = new ImageArrayAdapter(this,
				imageArray);
		Spinner centerOnMode = (Spinner) actionBarView
				.findViewById(R.id.center_on_mode_spinner);
		centerOnMode.setAdapter(imageArrayAdapter);
		centerOnMode
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent,
							View view, int position, long id) {
						System.out.println(String.format(
								"center on Mode  = %d, id = %d", position, id));
					}

					@Override
					public void onNothingSelected(AdapterView<?> parent) {
					}
				});

		ImageButton refresh = (ImageButton) actionBarView
				.findViewById(R.id.refresh);
		refresh.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("refresh button clicked");

			}
		});

		ImageButton centerOn = (ImageButton) actionBarView
				.findViewById(R.id.center_on);
		centerOn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("recenter button clicked");

			}
		});

		ImageButton nextPerson = (ImageButton) actionBarView
				.findViewById(R.id.next);
		nextPerson.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("next button clicked");

			}
		});

		ImageButton previousPerson = (ImageButton) actionBarView
				.findViewById(R.id.previous);
		previousPerson.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				System.out.println("pervious button clicked");

			}
		});

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	// public static class PlaceholderFragment extends Fragment {
	//
	// public PlaceholderFragment() {
	// }
	//
	// @Override
	// public View onCreateView(LayoutInflater inflater, ViewGroup container,
	// Bundle savedInstanceState) {
	// View rootView = inflater.inflate(R.layout.fragment_map, container,
	// false);
	// return rootView;
	// }
	// }

}
