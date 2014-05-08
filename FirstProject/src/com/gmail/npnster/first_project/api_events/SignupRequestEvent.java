package com.gmail.npnster.first_project.api_events;

import com.gmail.npnster.first_project.api_params.UserRequestParams;

public class SignupRequestEvent {
	protected UserRequestParams mSignupParams;
	
	public SignupRequestEvent(UserRequestParams signupParams) {
		mSignupParams = signupParams;
	}
	
	public UserRequestParams getParams() {
		return mSignupParams;
	}

}
