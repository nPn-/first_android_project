package com.gmail.npnster.first_project.api_events;

import com.gmail.npnster.first_project.api_params.SignupCompletedParams;

public class SignupCompletedEvent {
	
	private SignupCompletedParams mSignupCompletedParams;
	public SignupCompletedParams getParams() {
		return mSignupCompletedParams;
	}
	public SignupCompletedEvent(SignupCompletedParams signupCompletedParams) {
		mSignupCompletedParams = signupCompletedParams;
	}

}
