package com.gmail.npnster.first_project.api_params;

public class UnfollowRequest {
	private String api_access_token;
	private String userId;
	
	public UnfollowRequest(String userId) {
		super();
		this.userId = userId;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setApi_access_token(String api_access_token) {
		this.api_access_token = api_access_token;
	}
}
