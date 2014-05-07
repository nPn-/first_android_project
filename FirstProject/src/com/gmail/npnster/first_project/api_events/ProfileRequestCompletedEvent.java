package com.gmail.npnster.first_project.api_events;

import com.gmail.npnster.first_project.api_params.UserProfileParams;

public class ProfileRequestCompletedEvent {
	private UserProfileParams mUserProfileParams;
	
	public ProfileRequestCompletedEvent(UserProfileParams userProfileParams) {
		mUserProfileParams = userProfileParams;
	}
	
	public UserProfileParams getParams() {
		System.out.println("params are being requested from the UserProfileCompletedEvent");
		return mUserProfileParams;
	}

}
