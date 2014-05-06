package com.gmail.npnster.first_project;

import android.app.Application;

public class MyApp extends Application {
	
	private static MyApp singleton;
	private static String token;
	private static ApiRequester apiRequester;
	
	private static String user;
	private static String email;
	private static PersistData persistData;
	private static PersistData.Cached cachedPersistData;

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
		apiRequester = new ApiRequester("http://10.0.2.2:3000", getInstance());
	}
	
	public static ApiRequester getApiRequester() {
		return apiRequester;
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
