package com.gmail.npnster.first_project;

import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;

import com.gmail.npnster.first_project.MainActivity.PlaceholderFragment;
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
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		MapFragment wrapperFragment = new WrapperFragment();
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.map_container, wrapperFragment).commit();
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	public static class WrapperFragment extends MapFragment {
		private Bus mBus;

		private MapMarkers mapMarkerList;
		private MapView mapView;
		private MapPresenter mapPresenter;
		private MapFragment mapFragment;
		private Intent startTracking;
		private Intent endTracking;

		private Context context;

		private GoogleMap map;
		private ActionBar actionBar;
		private View actionBarView;
		private boolean isViewReady = false;

		public WrapperFragment() {
			super();
		}

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
			System.out.println("resuming map fragment");
			System.out.println(String.format("actionBarView = %s", actionBarView));
			actionBar = getActivity().getActionBar();
			actionBar.setCustomView(R.layout.map_actionbar_view);
			actionBarView = actionBar.getCustomView();
			actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
					| ActionBar.DISPLAY_SHOW_HOME);
			System.out.println(String.format("actionBarView = %s", actionBarView));
			if (!isViewReady) {
//				actionBar = getActivity().getActionBar();
//				actionBar.setCustomView(R.layout.map_actionbar_view);
//				actionBarView = actionBar.getCustomView();
//				actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM
//						| ActionBar.DISPLAY_SHOW_HOME);
				mapView = new MapView(context, map, actionBarView);


				mapPresenter.setMapView(mapView);
				isViewReady = true;
			}
            mapView.setActionBarView(actionBarView);
			mapPresenter.reinitMapView();
			System.out.println(String.format("restoreing state = centerOnIndex = %d,  centerOnMode = %d",MyApp.getCenterOnPosition() ,MyApp.getCenterOnMode() ));
            mapPresenter.setCenterOnPosition(MyApp.getCenterOnPosition());
            mapPresenter.setCenterOnMode(MyApp.getCenterOnMode());
			getBus().register(mapPresenter);

//			System.out.println("posting get markers request to the bus");

//			mBus.post(new PushLocationsUpdateRequestRequest());
//			mBus.post(new GetMapMarkersRequest());
			mapView.getMap().setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
				
				@Override
				public void onMapLoaded() {
					mapView.setMapBounds(MyApp.getMapBounds());
					
				}
			});
//			mapPresenter.refreshMap();
    		context.startService(startTracking);
			// android.app.ActionBar actionBar = getActionBar();
			// View actionBarView = actionBar.getCustomView();
			// Spinner spinner = (Spinner)
			// actionBarView.findViewById(R.id.spinner);
			// centerOnSpinnerAdapter = new CenterOnSpinnerAdapter(this,
			// mapMarkerList.toArrayList());
			// System.out.println(centerOnSpinnerAdapter.getCount());

			// mBus.post(new GetUsersRequest());
		}

		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			System.out.println("pausing map fragment");
			System.out.println(String.format("state = centerOnIndex = %d,  centerOnMode = %d",mapPresenter.getGenterOnPosition() ,mapPresenter.getGenterOnMode() ));
			getBus().unregister(mapPresenter);
			MyApp.saveMapBounds(mapView.getCurrentMapBounds());
			MyApp.saveCenterOnPosition(mapPresenter.getGenterOnPosition());
			MyApp.saveCenterOnMode(mapPresenter.getGenterOnMode());
    		context.startService(endTracking);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			context = getActivity();
			startTracking = new Intent(context, LocationMonitorService.class);
			startTracking.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_RESUMED");
    		endTracking = new Intent(context, LocationMonitorService.class);
    		endTracking.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_PAUSED");
			setRetainInstance(true);
			getBus().register(this);
			mapMarkerList = new MapMarkers();
			mapPresenter = new MapPresenter(context, mapMarkerList);
			System.out.println("mapwrapper fragment created");

		}

		@Override
		public void onDestroy() {
			// TODO Auto-generated method stub
			getBus().unregister(this);
			super.onDestroy();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			// View rootView = inflater.inflate(R.layout.fragment_map_wrapper,
			// container,
			// false);
			View rootView = super.onCreateView(inflater, container,
					savedInstanceState);
			map = getMap();
			System.out.println(String.format("map = %s", map));

			// mapFragment = MapFragment.newInstance();
			// getChildFragmentManager().beginTransaction().add(R.id.map_wrapper_container,mapFragment).commit();
			System.out.println("wrapper fragment view created");
			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			System.out.println("wrapper fragment hosting activity created");

		}

	}

}
