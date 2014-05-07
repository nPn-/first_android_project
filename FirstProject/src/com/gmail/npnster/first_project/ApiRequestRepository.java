package com.gmail.npnster.first_project;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.gmail.npnster.first_project.RailsApiClient.RailsApi;
import com.gmail.npnster.first_project.api_events.ProfileRequestCompletedEvent;
import com.gmail.npnster.first_project.api_events.ProfileRequestEvent;
import com.gmail.npnster.first_project.api_events.SignoutCompletedEvent;
import com.gmail.npnster.first_project.api_events.SignoutEvent;
import com.gmail.npnster.first_project.api_params.SignoutRequestParams;
import com.gmail.npnster.first_project.api_params.UserProfileParams;
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
	public void onUserProfileRequestEvent(ProfileRequestEvent event) {
		System.out.println(String.format(
				"inside userParams,  user = %s, token = %s", mApp.getUserId(),
				mApp.getToken()));
		mRailsApi.userParams(mApp.getUserId(), mApp.getToken(), new Callback<UserProfileParams>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void success(UserProfileParams profileParms, Response response) {
				// TODO Auto-generated method stub
				mBus.post(new ProfileRequestCompletedEvent(profileParms));			
			}
			
		});

	}

	@Subscribe
	public void onSignout(SignoutEvent event) {
		mRailsApi.signout(mApp.getToken(), mApp.getEmail(), "",
				new Callback<Void>() {

					@Override
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void success(Void arg0, Response arg1) {
						mBus.post(new SignoutCompletedEvent(""));

					}

				});

	}

	public ReturnedToken signup(UserSignupParameters params) {
		return mRailsApi.signup(params);
	}

	// public void signout(Callback<Void> callback) {
	// mRailsApi.signout(mApp.getToken(), mApp.getEmail(), "", callback);
	//
	// }

}
