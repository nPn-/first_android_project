package com.gmail.npnster.first_project.api_params;

// empty class - no parameters need to be passed

public class GetUserProfileRequest {
	
	private String mUserId = null;
	
	public GetUserProfileRequest(String userId) {
		mUserId = userId;
	}
	
	public GetUserProfileRequest() {
		mUserId = null;
	}
	
	public String getUserId() {
		return mUserId;
	}

}
