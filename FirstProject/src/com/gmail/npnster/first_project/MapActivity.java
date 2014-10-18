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
import android.view.Window;

public class MapActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
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
		@Inject PersistData mPersistData;

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
				mapView = new MapView(this, map, actionBarView);
				mapPresenter.setMapView(mapView);
				isViewReady = true;
			}
//			mapView.setActivity(getActivity());
            mapView.setActionBarView(actionBarView);
			mapPresenter.reinitMapView();
			System.out.println(String.format("restoreing state = centerOnIndex = %d,  centerOnMode = %d",mPersistData.getCenterOnPosition() ,mPersistData.getCenterOnMode() ));
            mapPresenter.setCenterOnPosition(mPersistData.getCenterOnPosition());
            mapPresenter.setCenterOnMode(mPersistData.getCenterOnMode());
            mapPresenter.setCenterOnUserId(mPersistData.getCenterOnUserId());
			getBus().register(mapPresenter);
			mapView.getMap().setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
				
				@Override
				public void onMapLoaded() {
					mapView.setMapBounds(mPersistData.getMapBounds());
//					mapView.attachMapListeners();
					
					
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
			mPersistData.saveMapBounds(mapView.getCurrentMapBounds());
			mPersistData.saveCenterOnPosition(mapPresenter.getGenterOnPosition());
			mPersistData.saveCenterOnMode(mapPresenter.getGenterOnMode());
			mPersistData.saveCenterOnUserId(mapPresenter.getCenterOnUserId());
    		context.startService(endTracking);
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Injector.getInstance().inject(this);
			context = getActivity();
			startTracking = new Intent(context, LocationMonitorService.class);
			startTracking.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_RESUMED");
    		endTracking = new Intent(context, LocationMonitorService.class);
    		endTracking.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_PAUSED");
			setRetainInstance(true);
			getBus().register(this);
			mapMarkerList = new MapMarkers();
			mapPresenter = new MapPresenter(this.getActivity(),mapMarkerList);
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
