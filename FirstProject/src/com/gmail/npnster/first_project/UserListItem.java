package com.gmail.npnster.first_project;

public class UserListItem {
	private String name;
	private String gravatarId;
	public UserListItem(String name, String gravatarId) {
		super();
		this.name = name;
		this.gravatarId = gravatarId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGravatarId() {
		return gravatarId;
	}
	public void setGravatarId(String gravatarId) {
		this.gravatarId = gravatarId;
	}
	
	

}
