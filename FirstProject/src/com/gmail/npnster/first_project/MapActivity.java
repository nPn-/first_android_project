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

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.os.Build;

public class MapActivity extends ActionBarActivity {
	
	private Bus mBus;
	private GoogleMap map;
	private ArrayList<MapMarker> mapMarkerList;
	
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
			 map = ((MapFragment) getFragmentManager()
		                .findFragmentById(R.id.map)).getMap();
			 System.out.println("posting get markers request to the bus");
			 mapMarkerList = new ArrayList<MapMarker>();
			 mBus.post(new PushLocationsUpdateRequestRequest());
			 mBus.post(new GetMapMarkersRequest());
		//	 mBus.post(new GetUsersRequest());  
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
		
		 
//		if (savedInstanceState == null) {
//			getSupportFragmentManager().beginTransaction()
//					.add(R.id.container, new PlaceholderFragment()).commit();
//		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.map, menu);
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
		System.out.println("inside map activity - onGetMapMarkersRequestCompleted");
		
		//GetUserProfileResponse params = event.getParams();
		System.out.println(event.getMarkers().size());
		mapMarkerList.clear();
//		ArrayList<com.google.android.gms.maps.model.Marker> markerList = new ArrayList<com.google.android.gms.maps.model.Marker>();
//		markerList.get(0).
		map.clear();
		for (Marker m : event.getMarkers()) {
			LatLng pos = new LatLng(m.getLatitude(),m.getLongitude());
			System.out.println(m.getLatitude());
			System.out.println(m.getLongitude());
			System.out.println(new Date(m.getLocationFixTime()));
			
//			MarkerOptions markerOptions = new MarkerOptions();
//			markerOptions.position(pos);
//			markerOptions.title(m.getName());
//			markerOptions.icon(BitmapDescriptorFactory
//		              .fromResource(R.drawable.ic_launcher));
//			map.addMarker(markerOptions);
//			System.out.println(pos.toString());
//			 com.google.android.gms.maps.model.Marker marker = map.addMarker(new MarkerOptions()
//		     .position(new LatLng(37.7750d, 122.4183d))
//		     .title("San Francisco")
//		     .snippet("Population: 776733"));
//			MapMarker mapMarker = new MapMarker(this, m, map);
			mapMarkerList.add(new MapMarker(this, m, map));
//			map.addMarker(new MarkerOptions()
//			    .position(pos)
//			    .title(m.getName())
//			    .icon(BitmapDescriptorFactory
//		              .fromResource(R.drawable.ic_launcher)));
			map.setMyLocationEnabled(true);
		}

	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
//	public static class PlaceholderFragment extends Fragment {
//
//		public PlaceholderFragment() {
//		}
//
//		@Override
//		public View onCreateView(LayoutInflater inflater, ViewGroup container,
//				Bundle savedInstanceState) {
//			View rootView = inflater.inflate(R.layout.fragment_map, container,
//					false);
//			return rootView;
//		}
//	}

}
