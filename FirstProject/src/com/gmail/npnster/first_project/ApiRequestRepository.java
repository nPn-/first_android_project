package com.gmail.npnster.first_project;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.gmail.npnster.first_project.RailsApiClient.RailsApi;
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
	protected MyApp mApp;
	protected Bus mBus;

	public ApiRequestRepository(MyApp app, RailsApi railsApi, Bus bus) {
		mApp = app;
		mRailsApi = railsApi;
		mBus = bus;
	}


	@Subscribe
	public void onUserProfileRequestEvent(GetUserProfileRequest event) {
		System.out
				.println(String
						.format("inside api repro making profile request with the following userParams,  user = %s, token = %s",
								mApp.getUserId(), mApp.getToken()));
		mRailsApi.userParams(mApp.getUserId(), mApp.getToken(),
				new RailsApiCallback<GetUserProfileResponse>(mBus, new GetUserProfileResponse()));
	}

	@Subscribe
	public void onSignout(SignoutRequest event) {
		System.out.println("inside api repo - making signout request");
		mRailsApi.signout(mApp.getToken(), mApp.getEmail(), "",
				new RailsApiCallback<SignoutResponse>(mBus, new SignoutResponse()));
	}

	@Subscribe
	public void onLeave(LeaveRequest event) {
		System.out.println("inside api repo - making leave request");
		mRailsApi.leave(event.getEmail(), event.getPassword(),
				new RailsApiCallback<LeaveResponse>(mBus, new LeaveResponse() ));
	}	
	
	@Subscribe
	public void onSignup(SignupRequest event) {
		System.out.println("inside api repo - making signin request");
		mRailsApi.signup(event, new RailsApiCallback<SignupResponse>(mBus, new SignupResponse()));
	}

	@Subscribe
	public void onGetUsers(GetUsersRequest event) {
		System.out.println("inside api repo - making get users list  request");
		mRailsApi.getUsers(mApp.getToken(), new RailsApiCallback<GetUsersResponse>(mBus, new GetUsersResponse()));
	}
	
	@Subscribe
	public void onGetFollowers(GetFollowersRequest event) {
		System.out.println("inside api repo - making get followers list  request");
		mRailsApi.getFollowers(mApp.getToken(), event.getId(), new RailsApiCallback<GetFollowersResponse>(mBus, new GetFollowersResponse()));
	}

	@Subscribe
	public void onGetFollowedUsers(GetFollowedUsersRequest event) {
		System.out.println("inside api repo - making get followed users list  request");
		mRailsApi.getFollowedUsers(mApp.getToken(), event.getId(), new RailsApiCallback<GetFollowedUsersResponse>(mBus, new GetFollowedUsersResponse()));
	}

	@Subscribe
	public void onGetMicroposts(GetMicropostsRequest event) {
		System.out.println("inside api repo - making get user microposts list  request");
		mRailsApi.getMicroposts(mApp.getToken(), event.getId(), new RailsApiCallback<GetMicropostsResponse>(mBus, new GetMicropostsResponse()));
	}

	@Subscribe
	public void onCreateMicropost(CreateMicropostRequest event) {
		System.out.println("inside api repo - making create micropost  request");
		event.setApi_access_token(mApp.getToken());
		mRailsApi.createMicropost(event, new RailsApiCallback<CreateMicropostResponse>(mBus, new CreateMicropostResponse()));
	}

	@Subscribe
	public void onDeleteMicropost(DeleteMicropostRequest event) {
		System.out.println("inside api repo - making delete micropost  request");
		mRailsApi.deleteMicropost(mApp.getToken(), event.getMicropostId(), new RailsApiCallback<DeleteMicropostResponse>(mBus, new DeleteMicropostResponse()));
	}

	@Subscribe
	public void onFollow(FollowRequest event) {
		System.out.println("inside api repo - making follow request");
		event.setApi_access_token(mApp.getToken());
		mRailsApi.follow(event.getUserId(), event, new RailsApiCallback<FollowResponse>(mBus, new FollowResponse()));
	}

	@Subscribe
	public void onUnfollow(UnfollowRequest event) {
		System.out.println("inside api repo - making unfollow request");
		mRailsApi.unfollow(mApp.getToken(), event.getUserId(), new RailsApiCallback<UnfollowResponse>(mBus, new UnfollowResponse()));
	}

	@Subscribe
	public void onUpdateUser(UpdateUserRequest event) {
		System.out.println("inside api repo - making user update  request");
		event.setApiAccessToken(MyApp.getToken());
		mRailsApi.updateUser(MyApp.getUserId(), event, new RailsApiCallback<UpdateUserResponse>(mBus, new UpdateUserResponse()));
	}


}
