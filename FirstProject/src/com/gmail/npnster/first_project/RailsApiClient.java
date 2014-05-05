package com.gmail.npnster.first_project;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

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
