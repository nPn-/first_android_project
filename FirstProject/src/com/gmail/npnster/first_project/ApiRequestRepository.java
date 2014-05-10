package com.gmail.npnster.first_project;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.gmail.npnster.first_project.RailsApiClient.RailsApi;
import com.gmail.npnster.first_project.api_events.ApiResponseEvent;
import com.gmail.npnster.first_project.api_events.ApiRequestEvent;
import com.gmail.npnster.first_project.api_events.ProfileRequestCompletedEvent;
import com.gmail.npnster.first_project.api_events.ProfileRequestEvent;
import com.gmail.npnster.first_project.api_events.SignoutCompletedEvent;
import com.gmail.npnster.first_project.api_events.SignoutEvent;
import com.gmail.npnster.first_project.api_events.SignupCompletedEvent;
import com.gmail.npnster.first_project.api_events.SignupRequestEvent;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
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

//	@SuppressWarnings("static-access")
//	public UserParams getMyParams(Callback<UserParams> callback) {
//		System.out.println(String.format(
//				"inside userParams,  user = %s, token = %s", mApp.getUserId(),
//				mApp.getToken()));
//		mRailsApi.userParams(mApp.getUserId(), mApp.getToken(), callback);
//		return null;
//
//	}

	@Subscribe
	public void onUserProfileRequestEvent(ApiRequestEvent<GetUserProfileRequest> event) {
		System.out.println(String.format(
				"inside api repro making profile request with the following userParams,  user = %s, token = %s", mApp.getUserId(),
				mApp.getToken()));
		mRailsApi.userParams(mApp.getUserId(), mApp.getToken(), new Callback<GetUserProfileResponse>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void success(GetUserProfileResponse profileParms, Response response) {
				// TODO Auto-generated method stub
				mBus.post(new ApiResponseEvent<GetUserProfileResponse>(profileParms));			
			}
			
		});

	}

	@Subscribe
	public void onSignout(ApiRequestEvent<SignoutRequest> event) {
		System.out.println("inside api repo - making signout request");
		mRailsApi.signout(mApp.getToken(), mApp.getEmail(), "",
				new Callback<Void>() {

					@Override
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void success(Void arg0, Response arg1) {
						mBus.post(new ApiResponseEvent<SignoutResponse>(null));

					}

				});

	}

	
//	public ReturnedToken signup(UserSignupParameters params) {
//		return mRailsApi.signup(params);
//	}
	
	@Subscribe
	public void onSignup(ApiRequestEvent<SignupRequest> event) {
		System.out.println("inside api repo - making signin request");
		mRailsApi.signup(event.getParams(), new Callback<SignupResponse>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void success(SignupResponse tokenParms, Response arg1) {
				// TODO Auto-generated method stub
				mBus.post(new ApiResponseEvent<SignupResponse>(tokenParms));
				
			}
			
		});
	}
	


	// public void signout(Callback<Void> callback) {
	// mRailsApi.signout(mApp.getToken(), mApp.getEmail(), "", callback);
	//
	// }

}
