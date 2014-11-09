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

public abstract class RailsApiQueEntry<T, S extends BaseResponse<T>> {
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
				System.out.println(String.format("error is: %s", retrofitError.getMessage()));
				
				/*
				 * this is a copy of the code I added to the non-sync api version, i think this should work here
				 * also but has not been tested at all
				 * the only ohter concern would be starting the activity from the background thread
				 * and of others could be qued up
				 * this needs a little more work, like clearing out the que if an event fails???
				 */
				
//				if (retrofitError.getResponse().getStatus() == 401) {
//					System.out.println(String.format("response was %d - remove token and bring up signin form", retrofitError
//							.getResponse().getStatus()));
//					mPersistData.clearToken();
//					mPersistData.clearGcmRegId();
//					Intent intent = new Intent(mContext, MainActivity.class);
//					intent.putExtra("ACTION", "signin").addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//							.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					mContext.startActivity(intent);
//				}
				mConvertedResponse.setNetworkError(false);
				mConvertedResponse.setRawResponse(retrofitError.getResponse());
				try {
					mConvertedResponse.setErrors(((BaseResponse) retrofitError.getBodyAs(BaseResponse.class)).getErrors());
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
		if (mConvertedResponse == null) {
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
