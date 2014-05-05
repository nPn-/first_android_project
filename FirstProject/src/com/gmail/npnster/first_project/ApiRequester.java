package com.gmail.npnster.first_project;

import retrofit.RestAdapter;
import retrofit.Callback;

import com.gmail.npnster.first_project.RailsApiClient.RailsApi;

public class ApiRequester {

	protected RailsApi railsApi;
	protected MyApp app;

	public ApiRequester(String endpoint, MyApp app) {
		RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(
				endpoint).build();
		railsApi = restAdapter.create(RailsApi.class);
		this.app = app;
	}

	@SuppressWarnings("static-access")
	public UserParams getMyParams(Callback<UserParams> callback) {
		System.out.println(String.format("inside userParams,  user = %s, token = %s", app.getUserId(), app.getToken()));
		railsApi.userParams(app.getUserId(), app.getToken(), callback);
		return null;

	}
	
	public ReturnedToken signup(UserSignupParameters params) {
		return railsApi.signup(params);
	}

}
