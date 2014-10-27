package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.CreateDeviceRequest;
import com.gmail.npnster.first_project.api_params.CreateDeviceResponse;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionResponse;
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
import com.gmail.npnster.first_project.api_params.PatchLocationRequest;
import com.gmail.npnster.first_project.api_params.PatchLocationResponse;
import com.gmail.npnster.first_project.api_params.PostLocationRequest;
import com.gmail.npnster.first_project.api_params.PostLocationResponse;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestRequest;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestResponse;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.SigninRequest;
import com.gmail.npnster.first_project.api_params.SigninResponse;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignoutResponse;
import com.gmail.npnster.first_project.api_params.SignupResponse;
import com.gmail.npnster.first_project.api_params.UnfollowRequest;
import com.gmail.npnster.first_project.api_params.UnfollowResponse;
import com.gmail.npnster.first_project.api_params.UpdateUserRequest;
import com.gmail.npnster.first_project.api_params.UpdateUserResponse;
import com.gmail.npnster.first_project.api_params.UserRequestParams;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PATCH;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

public class RailsApiClientSync {
	private static final String API_URL = "/api/v1";


	interface RailsApiSync {

		@GET(API_URL + "/users/{id}")
		Response userParams(@Path("id") String id,
				@Query("api_access_token") String token);

		// changed to put for now - bluemix does not support patch?
		@PUT(API_URL + "/users/{id}")
		Response updateUser(@Path("id") String id, @Body UpdateUserRequest request);

		@POST(API_URL + "/signup")
		Response signup(@Body UserRequestParams requestParams);

		@POST(API_URL + "/signin")
		Response signin(@Body SigninRequest request);

		@DELETE(API_URL + "/leave")
		Response leave(@Query("email") String email,
				@Query("password") String password);

		@DELETE(API_URL + "/signout")
		Response signout(@Query("api_access_token") String token,
				@Query("email") String email,
				@Query("password") String password);

		@GET(API_URL + "/users")
		Response getUsers(@Query("api_access_token") String token,
				@Query("page") String page,
				@Query("per_page") String perPage,
				@Query("search") String search);

		@GET(API_URL + "/users/{id}/followers?per_page=all")
		Response getFollowers(@Query("api_access_token") String token,
				@Path("id") String id);

		@GET(API_URL + "/users/{id}/following?per_page=all")
		Response getFollowedUsers(@Query("api_access_token") String token,
				@Path("id") String id);

		@GET(API_URL + "/users/{id}/microposts?per_page=all")
		Response getMicroposts(@Query("api_access_token") String token,
				@Path("id") String id);

		@POST(API_URL + "/microposts")
		Response createMicropost(@Body CreateMicropostRequest request);

		@DELETE(API_URL + "/microposts/{id}")
		Response deleteMicropost(@Query("api_access_token") String token,
				@Path("id") String id);

		@POST(API_URL + "/users/{id}/follow")
		Response follow(@Path("id")String id, @Body FollowRequest request);

		@DELETE(API_URL + "/users/{id}/follow")
		Response unfollow(@Query("api_access_token") String token,
				@Path("id")String id); 

		@POST(API_URL + "/users/{id}/followers/{follower_id}/permission")
		Response grantFollowerPermission(@Path("id") String id, @Path ("follower_id") String followerId,
				@Body GrantFollowerPermissionRequest request);

		@DELETE(API_URL + "/users/{id}/followers/{follower_id}/permission")
		Response revokeFollowerPermission(@Query("api_access_token") String token,
				  @Query("permission") String permission,
				  @Path("id") String id,
				  @Path ("follower_id") String followerId);

		@POST(API_URL + "/devices")
		Response createDevice(@Body CreateDeviceRequest request);
		
		@POST(API_URL + "/devices/{gcm_reg_key}/locations")
		Response postLocation(@Path("gcm_reg_key") String gcmRegKey, @Body PostLocationRequest request);
		
		// changed to put for now - bluemix does not support patch?
		@PUT(API_URL + "/devices/{gcm_reg_key}/locations")
		Response patchLocation(@Path("gcm_reg_key") String gcmRegKey, @Body PatchLocationRequest request);
		
		@POST(API_URL + "/locations_update_requests")
		Response postPushLocationsUpdateRequest(@Body PushLocationsUpdateRequestRequest request);
		
		@GET(API_URL + "/map_markers")
		Response getMapMarkersRequest(@Query("api_access_token") String token);

	}

}
