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
	}

	@SuppressWarnings("static-access")
	public UserParams getMyParams(Callback<UserParams> callback) {
		railsApi.userParams(app.getUserFromToken(), app.getToken(), callback);
		return null;

	}

}
