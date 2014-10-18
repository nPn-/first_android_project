package com.gmail.npnster.first_project.api_params;

public class UnfollowRequest {
	private String api_access_token;
	private String userId;
	private String userName;
	
	public UnfollowRequest(String userId) {
		super();
		this.userId = userId;
	}
	
	public UnfollowRequest(String userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}
	
	public String getUserId() {
		return userId;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setApi_access_token(String api_access_token) {
		this.api_access_token = api_access_token;
	}
}
