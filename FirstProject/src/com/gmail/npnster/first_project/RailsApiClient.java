package com.gmail.npnster.first_project;

import java.util.List;

import android.content.Context;
import android.util.Log;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

public class RailsApiClient {
	private static final String API_URL = "/api/v1";

	interface RailsApi {
		
		@GET(API_URL + "/users/{id}")
		void userParams(@Path("id") String id,
				@Query("api_access_token") String token,
				Callback<UserParams> callback);
	
		@POST(API_URL + "/signup")
		ReturnedToken signup(@Body UserSignupParameters params);

	}

}
