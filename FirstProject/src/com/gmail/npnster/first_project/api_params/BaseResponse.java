package com.gmail.npnster.first_project.api_params;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;

public class BaseResponse<T> {
	private ArrayList<String> errors = new ArrayList<String>();
	private Boolean mSuccessful = false;
	private Boolean mNetworkError = false;
	private Response rawResponse;
	private T mRequestEvent;
	
	public Response getRawResponse() {
		return rawResponse;
	}
	
	public void setRequestEvent(T requestEvent) {
		mRequestEvent = requestEvent;
	}
	
	public T getRequestEvent() {
		return mRequestEvent;
	}

	public void setRawResponse(Response rawResponse) {
		this.rawResponse = rawResponse;
	}

	public BaseResponse() {
		super();
	}
	
	public void setErrors(ArrayList<String> errors) {
		this.errors = errors;
	}

	public ArrayList<String> getErrors() {
		return errors;
	}
	
	public void setSuccessful(Boolean successful) {
		mSuccessful = successful;
	}
	
	public void setNetworkError(Boolean networkError) {
		mNetworkError = networkError;
	}
	
	public Boolean isSuccessful() {
		return mSuccessful;
	}
	
	public Boolean isNetworkError() {
		return mNetworkError;
	}
}
