package com.gmail.npnster.first_project;

import javax.inject.Inject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.squareup.otto.Bus;

import android.app.Activity;
import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	public static class WrapperFragment extends MapFragment {
		@Inject Bus mBus;
		@Inject MyApp mApp;

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
			return mBus;
		}
		
//		private Bus getBus() {
//			if (mBus == null) {
//				mBus = BusProvider.getInstance();
//			}
//			return mBus;
//		}
//
//		public void setBus(Bus bus) {
//			mBus = bus;
//		}

		@Override
		public void onResume() {
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
				mapView = new MapView(context, map, actionBarView);
				mapPresenter.setMapView(mapView);
				isViewReady = true;
			}
            mapView.setActionBarView(actionBarView);
			mapPresenter.reinitMapView();
			System.out.println(String.format("restoreing state = centerOnIndex = %d,  centerOnMode = %d",mApp.getCenterOnPosition() ,mApp.getCenterOnMode() ));
            mapPresenter.setCenterOnPosition(mApp.getCenterOnPosition());
            mapPresenter.setCenterOnMode(mApp.getCenterOnMode());
			getBus().register(mapPresenter);
			mapView.getMap().setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
				
				@Override
				public void onMapLoaded() {
					mapView.setMapBounds(mApp.getMapBounds());
					
				}
			});
    		context.startService(startTracking);
		}

		@Override
		public void onPause() {
			super.onPause();
			System.out.println("pausing map fragment");
			System.out.println(String.format("state = centerOnIndex = %d,  centerOnMode = %d",mapPresenter.getGenterOnPosition() ,mapPresenter.getGenterOnMode() ));
			getBus().unregister(mapPresenter);
			mApp.saveMapBounds(mapView.getCurrentMapBounds());
			mApp.saveCenterOnPosition(mapPresenter.getGenterOnPosition());
			mApp.saveCenterOnMode(mapPresenter.getGenterOnMode());
    		context.startService(endTracking);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			MyApp.inject(this);
			context = getActivity();
			startTracking = new Intent(context, LocationMonitorService.class);
			startTracking.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_RESUMED");
    		endTracking = new Intent(context, LocationMonitorService.class);
    		endTracking.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_PAUSED");
			setRetainInstance(true);
			getBus().register(this);
			mapMarkerList = new MapMarkers();
			mapPresenter = new MapPresenter(mapMarkerList);
			System.out.println("mapwrapper fragment created");

		}

		@Override
		public void onDestroy() {
			getBus().unregister(this);
			super.onDestroy();
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = super.onCreateView(inflater, container,
					savedInstanceState);
			map = getMap();
			System.out.println(String.format("map = %s", map));
			System.out.println("wrapper fragment view created");
			return rootView;
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			System.out.println("wrapper fragment hosting activity created");

		}

	}

}
