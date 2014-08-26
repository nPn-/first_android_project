package com.gmail.npnster.first_project;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import retrofit.RestAdapter;

import com.gmail.npnster.first_project.ApiExActivity.ApiCall;
import com.gmail.npnster.first_project.RailsApiClient.RailsApi;
import com.gmail.npnster.first_project.api_params.CreateDeviceRequest;
import com.gmail.npnster.first_project.api_params.PostLocationRequest;
import com.gmail.npnster.first_project.api_params.PostLocationResponse;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.model.LatLngBounds;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import dagger.ObjectGraph;
import android.app.Application;
import android.content.Intent;
import android.location.Location;

public class MyApp extends Application {    

//	private static String mApiRootUrl = "http://10.0.2.2:3000";   // for android test suite  
	
	
//	private static final String API_ROOT_URL = "http://172.16.1.105:3000";    // for local server    
//	private static final String API_ROOT_URL = "https://jdd-sample-app-rails4.herokuapp.com";  // heroku
//	private static final String API_ROOT_URL = "https://mylatitude.mybluemix.net";  // bluemix
//	private static final String API_ROOT_URL = "https://ourlatitude.mybluemix.net";  // bluemix
	private String mApiRootUrl = "https://ourlatitude.mybluemix.net";  // bluemix

	private ApiRequestRepository apiRequestRepository;
	private RailsApi railsApi;
	@Inject Bus mBus;
	@Inject PersistData mPersistData;
	Injector mInjector;
	
	@Override
	public void onCreate() { 
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("app being created");
		mInjector = Injector.getInstance().initialize(this);
		Injector.getInstance().inject(this);
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				mApiRootUrl).build();
		railsApi = restAdapter.create(RailsApi.class);
		apiRequestRepository = new ApiRequestRepository(mPersistData,
				railsApi, mBus);
		mBus.register(apiRequestRepository);
		mBus.register(this);
		startService(new Intent(getApplicationContext(), LocationMonitorService.class));
	}

	// this is currently only used by the api full integration testsuite
	// I need to figure out out to properly inject an instance of the singleton into ApiExTest class
	// then this can be removed.
	public PersistData getPersistData() {
		return mPersistData;
	}
	
	public String getApiRootUrl() {
		return mApiRootUrl;
	}
	
	public void setApiRootUrl(String apiRootUrl) {
		mApiRootUrl = apiRootUrl;
	}

}
