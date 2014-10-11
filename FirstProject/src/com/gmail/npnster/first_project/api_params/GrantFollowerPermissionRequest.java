package com.gmail.npnster.first_project.api_params;

public class GrantFollowerPermissionRequest extends TokenBasedRequest {

	private String userId;
	private String followerId;
	private String permission;
	private String mFollowerName = null;
	
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
	
	public String getPermission() {
		return permission;
	}
	
	public void setFollowerName(String name) {
		mFollowerName = name;
	}
	
	public String getFollowerName() {
		return mFollowerName;
	}


}
