package com.gmail.npnster.first_project.api_params;

public class GetFollowedUsersRequest {
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public GetFollowedUsersRequest(String id) {
		super();
		this.id = id;
	}
}
