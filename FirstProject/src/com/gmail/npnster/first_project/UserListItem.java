package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.GetFollowedUsersResponse;
import com.gmail.npnster.first_project.api_params.UserListResponse;
import com.gmail.npnster.first_project.api_params.UserListResponse.User;

public class UserListItem {
	private Integer userId;
	private String name;
	private String gravatarId;
	private String lastMicropost;
	private String postedTimeAgo;
	
	public UserListItem(Integer user, String name, String gravatarId, String lastMicropost, String postedTimeAgo) {
		super();
		this.name = name;
		this.gravatarId = gravatarId;
		this.lastMicropost = lastMicropost;
		this.postedTimeAgo = postedTimeAgo;
		this.userId = user;
	}
	
	
	public UserListItem(User user) {
		
		
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastMicropost() {
		return lastMicropost;
	}
	public String getPostedTimeAgo() {
		return postedTimeAgo;
	}
	public void setLastMicropost(String lastMicropost) {
		this.lastMicropost = lastMicropost;
	}
	public String getGravatarId() {
		return gravatarId;
	}
	public void setGravatarId(String gravatarId) {
		this.gravatarId = gravatarId;
	}
	
	

}
