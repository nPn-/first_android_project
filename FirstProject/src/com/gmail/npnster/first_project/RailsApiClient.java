package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.CreateMicropostRequest;
import com.gmail.npnster.first_project.api_params.CreateMicropostResponse;
import com.gmail.npnster.first_project.api_params.DeleteMicropostResponse;
import com.gmail.npnster.first_project.api_params.FollowRequest;
import com.gmail.npnster.first_project.api_params.FollowResponse;
import com.gmail.npnster.first_project.api_params.GetFollowersResponse;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersResponse;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.gmail.npnster.first_project.api_params.LeaveResponse;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignoutResponse;
import com.gmail.npnster.first_project.api_params.SignupResponse;
import com.gmail.npnster.first_project.api_params.UnfollowRequest;
import com.gmail.npnster.first_project.api_params.UnfollowResponse;
import com.gmail.npnster.first_project.api_params.UpdateUserRequest;
import com.gmail.npnster.first_project.api_params.UpdateUserResponse;
import com.gmail.npnster.first_project.api_params.UserRequestParams;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
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

		@PATCH(API_URL + "/users/{id}")
		void updateUser(@Path("id") String id, @Body UpdateUserRequest request,
				Callback<UpdateUserResponse> callback);

		@POST(API_URL + "/signup")
		void signup(@Body UserRequestParams requestParams,
				Callback<SignupResponse> tokenParms);

		@DELETE(API_URL + "/leave")
		void leave(@Query("email") String email,
				@Query("password") String password,
				Callback<LeaveResponse> callback);

		@DELETE(API_URL + "/signout")
		void signout(@Query("api_access_token") String token,
				@Query("email") String email,
				@Query("password") String password,
				Callback<SignoutResponse> callback);

		@GET(API_URL + "/users?per_page=all")
		void getUsers(@Query("api_access_token") String token,
				Callback<GetUsersResponse> callback);

		@GET(API_URL + "/users/{id}/followers?per_page=all")
		void getFollowers(@Query("api_access_token") String token,
				@Path("id") String id, Callback<GetFollowersResponse> callback);

		@GET(API_URL + "/users/{id}/following?per_page=all")
		void getFollowedUsers(@Query("api_access_token") String token,
				@Path("id") String id,
				Callback<GetFollowedUsersResponse> callback);

		@GET(API_URL + "/users/{id}/microposts?per_page=all")
		void getMicroposts(@Query("api_access_token") String token,
				@Path("id") String id, Callback<GetMicropostsResponse> callback);

		@POST(API_URL + "/microposts")
		void createMicropost(@Body CreateMicropostRequest request,
				Callback<CreateMicropostResponse> callback);

		@DELETE(API_URL + "/microposts/{id}")
		void deleteMicropost(@Query("api_access_token") String token,
				@Path("id") String id,
				Callback<DeleteMicropostResponse> callback);

		@POST(API_URL + "/users/{id}/follow")
		void follow(@Path("id")String id, @Body FollowRequest request,
				Callback<FollowResponse> callback);

		@DELETE(API_URL + "/users/{id}/follow")
		void unfollow(@Query("api_access_token") String token,
				@Path("id")String id, 
				Callback<UnfollowResponse> callback);

	}

}
