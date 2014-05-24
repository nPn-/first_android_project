package com.gmail.npnster.first_project.api_params;

public class GrantFollowerPermissionRequest extends TokenBasedRequest {

	private String userId;
	private String followerId;
	private String permission;
	
	public GrantFollowerPermissionRequest(String userId, String followerId, String permission) {
		super();
		this.userId = userId;
		this.followerId = followerId;
		this.permission = permission;
		
	}
	
	public String getUserId() {
		return userId;
	}

	public String getFollowerId() {
		return followerId;
	}


}
