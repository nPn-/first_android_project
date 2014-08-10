package com.gmail.npnster.first_project;

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

import android.app.Application;
import android.content.Intent;
import android.location.Location;

public class MyApp extends Application {    

	private static String mApiRootUrl = "http://10.0.2.2:3000";   // for android test suite  
	
	
//	private static final String API_ROOT_URL = "http://172.16.1.105:3000";    // for local server    
//	private static final String API_ROOT_URL = "https://jdd-sample-app-rails4.herokuapp.com";  // heroku
//	private static final String API_ROOT_URL = "https://mylatitude.mybluemix.net";  // bluemix
//	private static final String API_ROOT_URL = "https://ourlatitude.mybluemix.net";  // bluemix
//	private static  String mApiRootUrl = "https://ourlatitude.mybluemix.net";  // bluemix

	private static MyApp singleton;
	private static String token;
	private static ApiRequestRepository apiRequestRepository;
	private static String user;
	private static String email;    
	private static String gcmRegId;
	private static LatLngBounds mapBounds;
	private static int centerOnPosition;
	private static int centerOnMode;
	private static PersistData persistData;
	private static PersistData.Cached cachedPersistData;
	protected RailsApi railsApi;
	private static Bus mBus = BusProvider.getInstance();

	public static Bus getBus() {
		return mBus;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		singleton = this;
		persistData = new PersistData(this);
		cachedPersistData = persistData.new Cached();

		token = persistData.readAccessToken();
		email = persistData.readEmailId();
		user = getUserFromToken();
		gcmRegId = persistData.readGcmRegId();
		mapBounds = persistData.readMapBounds();
		centerOnPosition = persistData.readCenterOnPosition();
		centerOnMode = persistData.readCenterOnMode();
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				mApiRootUrl).build();
		railsApi = restAdapter.create(RailsApi.class);
		apiRequestRepository = new ApiRequestRepository(getInstance(),
				railsApi, mBus);
		mBus.register(apiRequestRepository);
		mBus.register(this);
//		CreateDeviceRequest request = new CreateDeviceRequest("jdd_tests_device","phone", true);
//		mBus.post(request);
		// temp to try locations
		startService(new Intent(getApplicationContext(), LocationMonitorService.class));
//		DeviceLocationClient deviceLocationClient = new DeviceLocationClient(this);
	}

	public static ApiRequestRepository getApiRequester() {
		return apiRequestRepository;
	}

	public static MyApp getInstance() {
		System.out.println("in get instance");
		return singleton;
	}

	public static String getToken() {
		return token;
	}

	public static String getEmail() {
		return email;
	}
	
	public static String getGcmRegId() {
		return gcmRegId;
	}
	
	public static LatLngBounds getMapBounds() {
		return mapBounds;
	}
	
	public static int getCenterOnPosition() {
		return centerOnPosition;
	}
	
	public static int getCenterOnMode() {
		return centerOnMode;
	}
	
	public static String getUserId() {
		return getUserFromToken();
	}

	public static void saveToken(String tokenToSave) {
		cachedPersistData.saveAccessToken(tokenToSave);
		user = tokenToSave.split(":")[0];
		token = tokenToSave;
	}
	
	public static void clearToken() {
		token = "";
		cachedPersistData.clearAccessToken();
	}

	public static void saveEmailId(String emailToSave) {
		cachedPersistData.saveEmailId(emailToSave);
		email = emailToSave;
	}
	
	public static void clearEmailId() {
		cachedPersistData.clearUserId();
		email = "";
	}

	
	public static void saveGcmRegId(String gcmRegIdToSave) {
		cachedPersistData.saveGcmRegId(gcmRegIdToSave);
		gcmRegId = gcmRegIdToSave;
	}
	
	public static void saveMapBounds(LatLngBounds bounds) {
		cachedPersistData.saveMapBounds(bounds);
		mapBounds = bounds;
	}
	
	public static void saveCenterOnPosition(int position) {
		cachedPersistData.saveCenterOnPosition(position);
		centerOnPosition = position;
	}
	
	public static void saveCenterOnMode(int mode) {
		cachedPersistData.saveCenterOnMode(mode);
		centerOnMode = mode;
	}
	
	public static void clearGcmRegId() {
		cachedPersistData.clearGcmRegId();
		gcmRegId = "";
	}


	public static PersistData getPersistData() {
		return persistData;
	}
              
	protected static String getUserFromToken() {
		return token.split(":")[0];
	}


	public static String getApiRootUrl() {
		return mApiRootUrl;
	}
	
	public static void setApiRootUrl(String apiRootUrl) {
		mApiRootUrl = apiRootUrl;
	}


	
//	@Subscribe
//	public void postLocationResponse(PostLocationResponse response) {
//		System.out.println(response.getRawResponse().getStatus());
//		System.out.println(response.getErrors());
//		
//	}

}
