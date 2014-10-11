package com.gmail.npnster.first_project;

import java.util.ArrayList;

import com.gmail.npnster.first_project.api_params.BaseResponse;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.squareup.otto.Bus;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RailsApiCallback<T,S extends BaseResponse> implements Callback<S> {

	private Bus mBus;
	private S mResponse;
	private T mRequestEvent;

	public RailsApiCallback(Bus bus, T requestEvent, S response) {
		super();
		mBus = bus;
		mResponse = response;    
		mRequestEvent = requestEvent;
		mResponse.setRequestEvent(requestEvent);
//		System.out.println(String.format("request event = %s", mResponse.getRequestEvent().toString()));
	}

	@Override 
	public void failure(RetrofitError retrofitError) {
		
		System.out.println("inside RetrofitError failure callback");
		mResponse.setSuccessful(false);
		if (retrofitError.isNetworkError()) {
			System.out.println("error is: network error");
			mResponse.setNetworkError(true);
			mResponse.setRawResponse(null);
			mResponse.setErrors(new ArrayList<String>());
			mBus.post(mResponse);
		} else {
			System.out.println(String.format("error is: %s",retrofitError.getMessage() ));
			mResponse.setNetworkError(false);
			mResponse.setRawResponse(retrofitError.getResponse());
			try {
				mResponse.setErrors( ((BaseResponse) retrofitError.getBodyAs(BaseResponse.class)).getErrors());
			} catch (Exception e) {
				mResponse.setErrors(new ArrayList<String>());
			}
			mBus.post(mResponse);			
		}
//		System.out.println(retrofitError.toString() );
		
//		System.out.println(String.format("message = %s, localized message = %s", retrofitError.getMessage(), retrofitError.getLocalizedMessage()));
//		if (retrofitError.getResponse() != null ) {
//			System.out.println(String.format("retrofit error - status code = %d", retrofitError.getResponse().getStatus()));
//		} else {
//			System.out.println("retrofit error - response was null");
//		}
//		
//		if (retrofitError.isNetworkError()) {
//			System.out.println("retrofit error - network error");
//		} else {			
//			System.out.println("retrofit error - this was not a network error");
//		}
//		
//		if (retrofitError.getBody() == null) {
//			System.out.println("retrofit error - body is null");
//		} else {
//			System.out.println("retrofit error - body is not null");
//			
//		}
		
		
//		if (!retrofitError.isNetworkError()  && retrofitError.getResponse().getStatus() < 500) { 
//		T response = retrofitError != null && retrofitError.getBody() != null ? (T) retrofitError.getBody() : mResponse  ;
//  //		T response;
//  //		try { 
//  //			response = (T) retrofitError.getBody();
//			response.setRawResponse(retrofitError.getResponse());
//			response.setSuccessful(false);
//			System.out.println("posting response to bus");
//			mBus.post(response);
//  //		} catch (Exception e) {
//  //			System.out.println(String.format("not posting response due to - %s", retrofitError.toString()));
//  //		}
//
//		} else {
//			System.out.println(String.format("not posting response due to - %s", retrofitError.toString()));
//		}
		
	}

	@Override
	public void success(S convertedResponse, Response rawResponse) {
		System.out.println(String.format("response body = %s",rawResponse.getBody()));
		S response = convertedResponse != null ? convertedResponse : mResponse ;
		response.setSuccessful(true);
		response.setRawResponse(rawResponse);
		response.setRequestEvent(mRequestEvent);
		mBus.post(response);

	}
}
