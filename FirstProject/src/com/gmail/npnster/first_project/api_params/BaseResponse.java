package com.gmail.npnster.first_project.api_params;

import java.util.ArrayList;
import java.util.List;

import retrofit.client.Response;

public class BaseResponse {
	private List<String> errors = new ArrayList<String>();
	private Boolean mSuccessful = false;
	public Response getRawResponse() {
		return rawResponse;
	}

	public void setRawResponse(Response rawResponse) {
		this.rawResponse = rawResponse;
	}

	private Response rawResponse;
	
	
	public BaseResponse() {
		super();
	}

	public List<String> getErrors() {
		return errors;
	}
	
	public void setSuccessful(Boolean successful) {
		mSuccessful = successful;
	}
	
	public Boolean isSuccessful() {
		return mSuccessful;
	}
}
