package com.gmail.npnster.first_project.api_events;

import com.gmail.npnster.first_project.api_params.SignupResponse;

public class ApiResponseEvent<T> {
	private T mResponseParams;
	public ApiResponseEvent(T responseParams) {
		mResponseParams = responseParams;
	}
	public T getParams() {
		System.out.println("getting params from generic apirequestcompleted class");
		return mResponseParams;
	}

}
