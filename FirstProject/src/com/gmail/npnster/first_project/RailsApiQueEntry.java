package com.gmail.npnster.first_project;

import java.util.ArrayList;

import javax.inject.Inject;

import android.os.Handler;
import android.os.Looper;

import com.gmail.npnster.first_project.RailsApiClientSync.RailsApiSync;
import com.gmail.npnster.first_project.api_params.BaseResponse;
import com.squareup.otto.Bus;

import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.converter.GsonConverter;

public abstract class RailsApiQueEntry<T, S  extends BaseResponse<T>> {
	RailsApiSync mRailsApiSync;
	private T mRequestEvent;
	private S mConvertedResponse;
	private S mInitalResponse;
	private Response mRawResponse;
	private Converter mConverter = new GsonConverter(new com.google.gson.Gson());
	private Handler mHandler = new Handler(Looper.getMainLooper());
	private Bus mBus;

	public RailsApiQueEntry(Bus bus, RailsApiSync railsApi, T requestEvent, S convertedResponse) {
		mRailsApiSync = railsApi;
		mRequestEvent = requestEvent;
		mConvertedResponse = convertedResponse;
		mInitalResponse = convertedResponse;
		mBus = bus;
	}

	public abstract Response makeRequest() throws RetrofitError;

	public void run() {

		System.out.println(String.format("convertedResponse = %s", mConvertedResponse.getClass().getName()));
		try {
			mRawResponse = makeRequest();			
		} catch (RetrofitError retrofitError) {
			if (retrofitError.isNetworkError()) {
				System.out.println("error is: network error");
				mConvertedResponse.setNetworkError(true);
				mConvertedResponse.setRawResponse(null);
				mConvertedResponse.setErrors(new ArrayList<String>());
				mConvertedResponse.setRequestEvent(mRequestEvent);
				postResponseToMainThread(mConvertedResponse);
				return;
			} else {
				System.out.println(String.format("error is: %s",retrofitError.getMessage() ));
				mConvertedResponse.setNetworkError(false);
				mConvertedResponse.setRawResponse(retrofitError.getResponse());
				try {
					mConvertedResponse.setErrors( ((BaseResponse) retrofitError.getBodyAs(BaseResponse.class)).getErrors());
					System.out.println("here");
				} catch (Exception e) {
					mConvertedResponse.setErrors(new ArrayList<String>());
				}
				postResponseToMainThread(mConvertedResponse);
				return;
			}
		}

		try {
			mConvertedResponse = (S) getConverter().fromBody(mRawResponse.getBody(), mConvertedResponse.getClass());
		} catch (ConversionException e) {
			e.printStackTrace();
			return;
		}
		if (mConvertedResponse == null ) {
			mConvertedResponse = mInitalResponse;
		}
		System.out.println(String.format("good convertedResponse = %s", mConvertedResponse.getClass().getName()));
		mConvertedResponse.setSuccessful(true);
		mConvertedResponse.setRawResponse(mRawResponse);
		mConvertedResponse.setRequestEvent(mRequestEvent);
		postResponseToMainThread(mConvertedResponse);

	}

	public Converter getConverter() {
		return mConverter;
	}
	
	void postResponseToMainThread(final S response) {
		mHandler.post(new Runnable() {

			@Override
			public void run() {
				System.out.println("++++back on main thread - posting API call response");
				mBus.post(response);				
			}
			
		});
	}

}
