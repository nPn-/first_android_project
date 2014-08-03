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
import android.content.Context;
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
	
	private MapMarkers mapMarkerList;
	private MapView mapView;
	private MapPresenter mapPresenter;
	
	private Context context;
	
	
	private GoogleMap map; 


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

		System.out.println("resuming map activity");
		System.out.println("posting get markers request to the bus");

		mBus.post(new PushLocationsUpdateRequestRequest());
		mBus.post(new GetMapMarkersRequest());
		mapPresenter.refreshMap();
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
		System.out.println("pausing map activity");
		getBus().unregister(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
	    context = getApplicationContext();
		mapMarkerList = new MapMarkers();
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		ActionBar actionBar = getActionBar();
		actionBar.setCustomView(R.layout.map_actionbar_view);
		actionBarView = actionBar.getCustomView();
		mapView = new MapView(context,map,actionBarView);
		mapPresenter = new MapPresenter(context,mapView,mapMarkerList);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
				| ActionBar.DISPLAY_SHOW_HOME);


	}


 

}
