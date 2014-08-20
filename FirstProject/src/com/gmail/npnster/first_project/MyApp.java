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

	private static String mApiRootUrl = "http://10.0.2.2:3000";   // for android test suite  
	
	
//	private static final String API_ROOT_URL = "http://172.16.1.105:3000";    // for local server    
//	private static final String API_ROOT_URL = "https://jdd-sample-app-rails4.herokuapp.com";  // heroku
//	private static final String API_ROOT_URL = "https://mylatitude.mybluemix.net";  // bluemix
//	private static final String API_ROOT_URL = "https://ourlatitude.mybluemix.net";  // bluemix
//	private String mApiRootUrl = "https://ourlatitude.mybluemix.net";  // bluemix

//	private MyApp singleton;  
//	private String token;
	private ApiRequestRepository apiRequestRepository;
//	private String user;
//	private String email;    
//	private String gcmRegId;
//	private LatLngBounds mapBounds;
//	private int centerOnPosition;
//	private int centerOnMode;
//	private PersistData persistData;
//	private PersistData.Cached cachedPersistData;
	private RailsApi railsApi;
	@Inject Bus mBus;
	@Inject PersistData mPersistData;
	
//	private static ObjectGraph objectGraph;
	
//	public MyApp() {
//		super();
//		System.out.println("myapp construtor");  
		
//		objectGraph = ObjectGraph.create(new ApplicationModule(this));
//		objectGraph.inject(this);
//	}

//	public static Bus getBus() {
//		return mBus;
//	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		System.out.println("app being created");
		Injector.getInstance().initialize(this);
		Injector.getInstance().inject(this);
//		Injector.getInstance().buildGraph();
//		singleton = this;
//		persistData = new PersistData(this);
//		cachedPersistData = persistData.new Cached();
//		mPersistData = new PersistData(this);
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				mApiRootUrl).build();
		railsApi = restAdapter.create(RailsApi.class);
		apiRequestRepository = new ApiRequestRepository(mPersistData,
				railsApi, mBus);
		mBus.register(apiRequestRepository);
		mBus.register(this);
//		CreateDeviceRequest request = new CreateDeviceRequest("jdd_tests_device","phone", true);
//		mBus.post(request);
		// temp to try locations
		startService(new Intent(getApplicationContext(), LocationMonitorService.class));
//		DeviceLocationClient deviceLocationClient = new DeviceLocationClient(this);
	}

//	public ApiRequestRepository getApiRequester() {
//		return apiRequestRepository;
//	}
//	
	
	
	
	// this is currently only used by the api full integration testsuite
	// I need to figure out out to properly inject an instance of the singleton into ApiExTest class
	// then this can be removed.
	public PersistData getPersistData() {
		return mPersistData;
	}

//	public MyApp getInstance() {
//		System.out.println("in get instance");
//		return singleton;
//	}

//	public String getToken() {
//		return token;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//	
//	public String getGcmRegId() {
//		return gcmRegId;
//	}
//	
//	public LatLngBounds getMapBounds() {
//		return mapBounds;
//	}
//	
//	public int getCenterOnPosition() {
//		return centerOnPosition;
//	}
//	
//	public int getCenterOnMode() {
//		return centerOnMode;
//	}
//	
//	public String getUserId() {
//		return getUserFromToken();
//	}
//
//	public void saveToken(String tokenToSave) {
//		cachedPersistData.saveAccessToken(tokenToSave);
//		user = tokenToSave.split(":")[0];
//		token = tokenToSave;
//	}
//	
//	public void clearToken() {
//		token = "";
//		cachedPersistData.clearAccessToken();
//	}
//
//	public void saveEmailId(String emailToSave) {
//		cachedPersistData.saveEmailId(emailToSave);
//		email = emailToSave;
//	}
//	
//	public void clearEmailId() {
//		cachedPersistData.clearUserId();
//		email = "";
//	}
//
//	
//	public void saveGcmRegId(String gcmRegIdToSave) {
//		cachedPersistData.saveGcmRegId(gcmRegIdToSave);
//		gcmRegId = gcmRegIdToSave;
//	}
//	
//	public void saveMapBounds(LatLngBounds bounds) {
//		cachedPersistData.saveMapBounds(bounds);
//		mapBounds = bounds;
//	}
//	
//	public void saveCenterOnPosition(int position) {
//		cachedPersistData.saveCenterOnPosition(position);
//		centerOnPosition = position;
//	}
//	
//	public void saveCenterOnMode(int mode) {
//		cachedPersistData.saveCenterOnMode(mode);
//		centerOnMode = mode;
//	}
//	
//	public void clearGcmRegId() {
//		cachedPersistData.clearGcmRegId();
//		gcmRegId = "";
//	}
//
//
//	public PersistData getPersistData() {
//		return persistData;
//	}
//              
//	protected String getUserFromToken() {
//		return token.split(":")[0];
//	}


	public String getApiRootUrl() {
		return mApiRootUrl;
	}
	
	public void setApiRootUrl(String apiRootUrl) {
		mApiRootUrl = apiRootUrl;
	}

//	public static ObjectGraph getObjectGraph() {
//		System.out.println(String.format("inside inject getObjectGraph() = %s", objectGraph) );
//		return objectGraph;
//	}
//
//	  protected List<Object> getModules() {
//		    return Arrays.asList(
//		        new AndroidModule(this),
//		        new ApplicationModule(this)
//		    );
//		  }
//
//		  public static void inject(Object object) {
//		   getObjectGraph().inject(object);
//		  }
	

}
