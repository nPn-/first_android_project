package com.gmail.npnster.first_project.api_events;

import com.gmail.npnster.first_project.api_params.SignupResponse;

public class SignupCompletedEvent {
	
	private SignupResponse mSignupCompletedParams;
	public SignupResponse getParams() {
		return mSignupCompletedParams;
	}
	public SignupCompletedEvent(SignupResponse signupResponse) {
		mSignupCompletedParams = signupResponse;
	}

}
