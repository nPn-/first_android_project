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
		System.out.println(retrofitError.toString() );
		if (!retrofitError.isNetworkError()  && retrofitError.getResponse().getStatus() < 500) { 
		T response = retrofitError != null && retrofitError.getBody() != null ? (T) retrofitError.getBody() : mResponse  ;
//		T response;
//		try { 
//			response = (T) retrofitError.getBody();
			response.setRawResponse(retrofitError.getResponse());
			response.setSuccessful(false);
			System.out.println("posting response to bus");
			mBus.post(response);
//		} catch (Exception e) {
//			System.out.println(String.format("not posting response due to - %s", retrofitError.toString()));
//		}

		} else {
			System.out.println(String.format("not posting response due to - %s", retrofitError.toString()));
		}
	}

	@Override
	public void success(T convertedResponse, Response rawResponse) {
		System.out.println(String.format("response body = %s",rawResponse.getBody()));
		T response = convertedResponse != null ? convertedResponse : mResponse ;
		response.setSuccessful(true);
		response.setRawResponse(rawResponse);
		mBus.post(response);

	}
}
