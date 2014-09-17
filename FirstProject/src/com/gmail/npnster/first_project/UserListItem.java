package com.gmail.npnster.first_project;

public class UserListItem {
	private String name;
	private String gravatarId;
	private String lastMicropost;
	private String postedTimeAgo;
	
	public UserListItem(String name, String gravatarId, String lastMicropost, String postedTimeAgo) {
		super();
		this.name = name;
		this.gravatarId = gravatarId;
		this.lastMicropost = lastMicropost;
		this.postedTimeAgo = postedTimeAgo;
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
