package com.gmail.npnster.first_project.api_params;

public class RevokeFollowerPermissionRequest extends TokenBasedRequest {

	private String userId;
	private String followerId;
	private String permission;
	
	public RevokeFollowerPermissionRequest(String userId, String followerId, String permission) {
		super();
		this.userId = userId;
		this.followerId = followerId;
		this.permission = permission;
		
	}
	
	public String getPermission() {
		return permission;
	}

	public String getUserId() {
		return userId;
	}

	public String getFollowerId() {
		return followerId;
	}

}
