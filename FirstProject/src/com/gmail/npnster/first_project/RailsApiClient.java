package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignupResponse;
import com.gmail.npnster.first_project.api_params.UserRequestParams;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
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
				Callback<GetUserProfileResponse> callback);

//		@POST(API_URL + "/signup")
//		ReturnedToken signup(@Body UserSignupParameters params);
		
		@POST(API_URL + "/signup")
		void signup(@Body UserRequestParams requestParams, Callback<SignupResponse> tokenParms);


		@DELETE(API_URL + "/signout")
		void signout(@Query("api_access_token") String token,
				@Query("email") String email, @Query("password") String password,Callback<Void> callback );

	}

}
