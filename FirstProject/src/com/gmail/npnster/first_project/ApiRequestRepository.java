package com.gmail.npnster.first_project;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.gmail.npnster.first_project.RailsApiClient.RailsApi;
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


}
