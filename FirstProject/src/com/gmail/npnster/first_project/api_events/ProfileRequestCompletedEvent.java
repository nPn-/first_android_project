package com.gmail.npnster.first_project.api_events;

import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;

public class ProfileRequestCompletedEvent {
	private GetUserProfileResponse mUserProfileParams;
	
	public ProfileRequestCompletedEvent(GetUserProfileResponse getUserProfileResponse) {
		mUserProfileParams = getUserProfileResponse;
	}
	
	public GetUserProfileResponse getParams() {
		System.out.println("params are being requested from the UserProfileCompletedEvent");
		return mUserProfileParams;
	}

}
