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

	// @SuppressWarnings("static-access")
	// public UserParams getMyParams(Callback<UserParams> callback) {
	// System.out.println(String.format(
	// "inside userParams,  user = %s, token = %s", mApp.getUserId(),
	// mApp.getToken()));
	// mRailsApi.userParams(mApp.getUserId(), mApp.getToken(), callback);
	// return null;
	//
	// }

	@Subscribe
	public void onUserProfileRequestEvent(GetUserProfileRequest event) {
		System.out
				.println(String
						.format("inside api repro making profile request with the following userParams,  user = %s, token = %s",
								mApp.getUserId(), mApp.getToken()));
		mRailsApi.userParams(mApp.getUserId(), mApp.getToken(),
				new Callback<GetUserProfileResponse>() {

					@Override
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void success(
							GetUserProfileResponse getUserProfileResponse,
							Response response) {
						// TODO Auto-generated method stub
						mBus.post(getUserProfileResponse);
					}

				});

	}

	@Subscribe
	public void onSignout(SignoutRequest event) {
		System.out.println("inside api repo - making signout request");
		mRailsApi.signout(mApp.getToken(), mApp.getEmail(), "",
				new Callback<SignoutResponse>() {

					@Override
					public void failure(RetrofitError arg0) {
						// TODO Auto-generated method stub

					}

					@Override
					public void success(SignoutResponse signoutResponse,
							Response arg1) {
						// need to create one since the api just returns a
						// header with no body , hence the response is null
						mBus.post(new SignoutResponse());

					}

				});

	}

	// public ReturnedToken signup(UserSignupParameters params) {
	// return mRailsApi.signup(params);
	// }

	@Subscribe
	public void onSignup(SignupRequest event) {
		System.out.println("inside api repo - making signin request");
		mRailsApi.signup(event, new Callback<SignupResponse>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				SignupResponse signupResponse = (SignupResponse) arg0.getBody();
				signupResponse.setSuccessful(false);
				mBus.post(signupResponse);
				System.out.println("in failure method for signup request");
				System.out.println(((SignupResponse) arg0.getBody())
						.getErrors());
				List<String> errorList = ((SignupResponse) arg0.getBody())
						.getErrors();
				System.out.println(String.format("response status code = %d", arg0.getResponse().getStatus()));
				for (String error : errorList) {
					System.out.println(error);
				}
				

			}

			@Override
			public void success(SignupResponse signupResponse, Response arg1) {
				// TODO Auto-generated method stub
				signupResponse.setSuccessful(true);
				mBus.post(signupResponse);

			}

		});
	}

	@Subscribe
	public void onGetUsers(GetUsersRequest event) {
		System.out.println("inside api repo - making get users list  request");
		mRailsApi.getUsers(mApp.getToken(), new Callback<GetUsersResponse>() {

			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void success(GetUsersResponse getUsersResponse, Response arg1) {
				// TODO Auto-generated method stub
				mBus.post(getUsersResponse);

			}

		});
	}

	// public void signout(Callback<Void> callback) {
	// mRailsApi.signout(mApp.getToken(), mApp.getEmail(), "", callback);
	//
	// }

}
