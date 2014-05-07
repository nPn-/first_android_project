package com.gmail.npnster.first_project;

import retrofit.RestAdapter;

import com.gmail.npnster.first_project.RailsApiClient.RailsApi;
import com.squareup.otto.Bus;

import android.app.Application;

public class MyApp extends Application {

	private static final String API_ROOT_URL = "http://10.0.2.2:3000";
	private static MyApp singleton;
	private static String token;
	private static ApiRequestRepository apiRequestRepository;
	private static String user;
	private static String email;
	private static PersistData persistData;
	private static PersistData.Cached cachedPersistData;
	protected RailsApi railsApi;
	private Bus mBus = BusProvider.getInstance();

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
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				API_ROOT_URL).build();
		railsApi = restAdapter.create(RailsApi.class);
		apiRequestRepository = new ApiRequestRepository(getInstance(),
				railsApi, mBus);
		mBus.register(apiRequestRepository);
		mBus.register(this);
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

	public static String getUserId() {
		return getUserFromToken();
	}

	public static void saveToken(String tokenToSave) {
		cachedPersistData.saveAccessToken(tokenToSave);
		user = tokenToSave.split(":")[0];
		token = tokenToSave;
	}

	public static void saveEmailId(String emailToSave) {
		cachedPersistData.saveEmailId(emailToSave);
		email = emailToSave;
	}

	public static PersistData getPersistData() {
		return persistData;
	}

	protected static String getUserFromToken() {
		return token.split(":")[0];
	}

}
