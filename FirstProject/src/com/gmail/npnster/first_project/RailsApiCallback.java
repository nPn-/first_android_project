package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.BaseResponse;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.squareup.otto.Bus;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RailsApiCallback<T extends BaseResponse> implements Callback<T> {

	private Bus mBus;
	private T mResponse;

	public RailsApiCallback(Bus bus, T response) {
		super();
		mBus = bus;
		mResponse = response;     
	}

	@Override 
	public void failure(RetrofitError retrofitError) {
		System.out.println(retrofitError.toString());
		T response = retrofitError != null && retrofitError.getBody() != null ? (T) retrofitError.getBody() : mResponse  ;
		response.setRawResponse(retrofitError.getResponse());
		response.setSuccessful(false);
		System.out.println("posting response to bus");
		mBus.post(response);
	}

	@Override
	public void success(T convertedResponse, Response rawResponse) {
		System.out.println(rawResponse.getBody());
		T response = convertedResponse != null ? convertedResponse : mResponse ;
		response.setSuccessful(true);
		response.setRawResponse(rawResponse);
		mBus.post(response);

	}
}
