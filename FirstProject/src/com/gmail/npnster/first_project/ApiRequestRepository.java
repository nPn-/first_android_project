package com.gmail.npnster.first_project;

import java.util.List;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.gmail.npnster.first_project.RailsApiClient.RailsApi;
import com.gmail.npnster.first_project.api_params.CreateDeviceRequest;
import com.gmail.npnster.first_project.api_params.CreateDeviceResponse;
import com.gmail.npnster.first_project.api_params.GetMapMarkersRequest;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.CreateMicropostRequest;
import com.gmail.npnster.first_project.api_params.CreateMicropostResponse;
import com.gmail.npnster.first_project.api_params.DeleteMicropostRequest;
import com.gmail.npnster.first_project.api_params.DeleteMicropostResponse;
import com.gmail.npnster.first_project.api_params.FollowRequest;
import com.gmail.npnster.first_project.api_params.FollowResponse;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersResponse;
import com.gmail.npnster.first_project.api_params.GetFollowersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowersResponse;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersRequest;
import com.gmail.npnster.first_project.api_params.GetMicropostsRequest;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.gmail.npnster.first_project.api_params.LeaveRequest;
import com.gmail.npnster.first_project.api_params.LeaveResponse;
import com.gmail.npnster.first_project.api_params.PatchLocationRequest;
import com.gmail.npnster.first_project.api_params.PatchLocationResponse;
import com.gmail.npnster.first_project.api_params.PostLocationRequest;
import com.gmail.npnster.first_project.api_params.PostLocationResponse;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestRequest;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestResponse;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.SigninRequest;
import com.gmail.npnster.first_project.api_params.SigninResponse;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignoutResponse;
import com.gmail.npnster.first_project.api_params.SignupRequest;
import com.gmail.npnster.first_project.api_params.SignupResponse;
import com.gmail.npnster.first_project.api_params.UnfollowRequest;
import com.gmail.npnster.first_project.api_params.UnfollowResponse;
import com.gmail.npnster.first_project.api_params.UpdateUserRequest;
import com.gmail.npnster.first_project.api_params.UpdateUserResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ApiRequestRepository {

	protected RailsApi mRailsApi;
	PersistData mPersistData;
	protected Bus mBus;

	public ApiRequestRepository(PersistData persistData, RailsApi railsApi, Bus bus) {
		mPersistData = persistData;
		mRailsApi = railsApi;
		mBus = bus;
	}


	@Subscribe
	public void onUserProfileRequestEvent(GetUserProfileRequest event) {
		String userToFetch = event.getUserId() == null ? mPersistData.getUserId() : event.getUserId();
		System.out
				.println(String
						.format("inside api repro making profile request with the following userParams,  user = %s, token = %s",
								userToFetch, mPersistData.getToken()));
		mRailsApi.userParams(userToFetch, mPersistData.getToken(),
				new RailsApiCallback<GetUserProfileRequest,GetUserProfileResponse>(mBus, event ,new GetUserProfileResponse()));
	}

	@Subscribe
	public void onSignout(SignoutRequest event) {
		System.out.println("inside api repo - making signout request");
		String token = event.getToken() == null ? mPersistData.getToken() : event.getToken();
		String email = event.getEmail() == null ? mPersistData.getEmail() : event.getEmail();
		String password = event.getPassword() == null ? "" : event.getPassword();
		mRailsApi.signout(token, email, password,
				new RailsApiCallback<SignoutRequest,SignoutResponse>(mBus, event, new SignoutResponse()));
	}

	@Subscribe
	public void onLeave(LeaveRequest event) {
		System.out.println("inside api repo - making leave request");
		mRailsApi.leave(event.getEmail(), event.getPassword(),
				new RailsApiCallback<LeaveRequest,LeaveResponse>(mBus, event, new LeaveResponse() ));
	}	
	
	@Subscribe
	public void onSignup(SignupRequest event) {
		System.out.println("inside api repo - making signup request");
		mRailsApi.signup(event, new RailsApiCallback<SignupRequest,SignupResponse>(mBus, event, new SignupResponse()));
	}

	@Subscribe
	public void onSignin(SigninRequest event) {
		System.out.println("inside api repo - making signin request");
		mRailsApi.signup(event, new RailsApiCallback<SigninRequest,SigninResponse>(mBus, event, new SigninResponse()));
	}

	@Subscribe
	public void onGetUsers(GetUsersRequest event) {
		System.out.println("inside api repo - making get users list  request");
		mRailsApi.getUsers(mPersistData.getToken(), new RailsApiCallback<GetUsersRequest,GetUsersResponse>(mBus, event, new GetUsersResponse()));
	}
	
	@Subscribe
	public void onGetFollowers(GetFollowersRequest event) {
		System.out.println("inside api repo - making get followers list  request");
		mRailsApi.getFollowers(mPersistData.getToken(), event.getId(), new RailsApiCallback<GetFollowersRequest,GetFollowersResponse>(mBus, event, new GetFollowersResponse()));
	}

	@Subscribe
	public void onGetFollowedUsers(GetFollowedUsersRequest event) {
		System.out.println("inside api repo - making get followed users list  request");
		String token = event.getToken() != null ? event.getToken() : mPersistData.getToken();
		mRailsApi.getFollowedUsers(token, event.getId(), new RailsApiCallback<GetFollowedUsersRequest, GetFollowedUsersResponse>(mBus, event, new GetFollowedUsersResponse()));
	}

	@Subscribe
	public void onGetMicroposts(GetMicropostsRequest event) {
		System.out.println("inside api repo - making get user microposts list  request");
		String token = event.getToken() != null ? event.getToken() : mPersistData.getToken();
		mRailsApi.getMicroposts(token, event.getId(), new RailsApiCallback<GetMicropostsRequest, GetMicropostsResponse>(mBus, event, new GetMicropostsResponse()));
	}

	@Subscribe
	public void onCreateMicropost(CreateMicropostRequest event) {
		System.out.println("inside api repo - making create micropost  request");
		event.setApi_access_token(mPersistData.getToken());
		mRailsApi.createMicropost(event, new RailsApiCallback<CreateMicropostRequest,CreateMicropostResponse>(mBus, event, new CreateMicropostResponse()));
	}

	@Subscribe
	public void onDeleteMicropost(DeleteMicropostRequest event) {
		System.out.println("inside api repo - making delete micropost  request");
		mRailsApi.deleteMicropost(mPersistData.getToken(), event.getMicropostId(), new RailsApiCallback<DeleteMicropostRequest,DeleteMicropostResponse>(mBus, event, new DeleteMicropostResponse()));
	}

	@Subscribe
	public void onFollow(FollowRequest event) {
		System.out.println("inside api repo - making follow request");
		if (event.getToken() == null) event.setToken(mPersistData.getToken());
		mRailsApi.follow(event.getUserId(), event, new RailsApiCallback<FollowRequest,FollowResponse>(mBus, event, new FollowResponse()));
	}

	@Subscribe
	public void onUnfollow(UnfollowRequest event) {
		System.out.println("inside api repo - making unfollow request");
		mRailsApi.unfollow(mPersistData.getToken(), event.getUserId(), new RailsApiCallback<UnfollowRequest,UnfollowResponse>(mBus, event, new UnfollowResponse()));
	}

	@Subscribe
	public void onGrantFollowerPermission(GrantFollowerPermissionRequest event) {
		System.out.println("inside api repo - making grant follower permission request");
		if (event.getToken() == null) event.setToken(mPersistData.getToken());
		mRailsApi.grantFollowerPermission(event.getUserId(), event.getFollowerId(), event, new RailsApiCallback<GrantFollowerPermissionRequest,GrantFollowerPermissionResponse>(mBus, event, new GrantFollowerPermissionResponse()));
	}
	@Subscribe
	public void onRevokeFollowerPermission(RevokeFollowerPermissionRequest event) {
		System.out.println("inside api repo - making revoke follower permission request");
		String token;
		token = event.getToken() == null ? mPersistData.getToken() : event.getToken();
		mRailsApi.revokeFollowerPermission(token, event.getPermission(), event.getUserId(), event.getFollowerId(), new RailsApiCallback<RevokeFollowerPermissionRequest,RevokeFollowerPermissionResponse>(mBus, event, new RevokeFollowerPermissionResponse()));
	}

	@Subscribe
	public void onUpdateUser(UpdateUserRequest event) {
		System.out.println("inside api repo - making user update  request");
		event.setApiAccessToken(mPersistData.getToken());
		mRailsApi.updateUser(mPersistData.getUserId(), event, new RailsApiCallback<UpdateUserRequest,UpdateUserResponse>(mBus, event, new UpdateUserResponse()));
	}
	
	@Subscribe
	public void onCreateDevice(CreateDeviceRequest event) {
		System.out.println("inside api repo - making create device  request");
		event.setToken(mPersistData.getToken());
		mRailsApi.createDevice(event, new RailsApiCallback<CreateDeviceRequest,CreateDeviceResponse>(mBus, event, new CreateDeviceResponse()));
	}

	@Subscribe
	public void onPostLocation(PostLocationRequest event) {
		System.out.println("inside api repo - making post location  request");
		System.out.println(event.getClass().toString());
		event.setToken(mPersistData.getToken());
		if (!event.getGcmRegKey().equals("")) { 
			mRailsApi.postLocation(event.getGcmRegKey(), event, new RailsApiCallback<PostLocationRequest,PostLocationResponse>(mBus, event, new PostLocationResponse()));
		}
	}
	
	@Subscribe
	public void onPatchLocation(PatchLocationRequest event) {
		System.out.println("inside api repo - making patch location  request");
		System.out.println(event.getClass().toString());
		event.setToken(mPersistData.getToken());
		if (!event.getGcmRegKey().equals("")) { 
			mRailsApi.patchLocation(event.getGcmRegKey(), event, new RailsApiCallback<PatchLocationRequest,PatchLocationResponse>(mBus, event, new PatchLocationResponse()));
		}
	}
	
	@Subscribe
	public void onLocationsUpdateRequest(PushLocationsUpdateRequestRequest event) {
		System.out.println("inside api repo - making locations update  request");
		System.out.println(event.getClass().toString());
		event.setToken(mPersistData.getToken());
		mRailsApi.postPushLocationsUpdateRequest(event, new RailsApiCallback<PushLocationsUpdateRequestRequest,PushLocationsUpdateRequestResponse>(mBus, event, new PushLocationsUpdateRequestResponse()));
	}
	
	@Subscribe
	public void onGetMapMarkersRequest(GetMapMarkersRequest event) {
		System.out.println("inside api repo - making get map markers request");
		System.out.println(event.getClass().toString());
		mRailsApi.getMapMarkersRequest(mPersistData.getToken(), new RailsApiCallback<GetMapMarkersRequest,GetMapMarkersResponse>(mBus, event, new GetMapMarkersResponse()));
	}


}
