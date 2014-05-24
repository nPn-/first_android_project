package com.gmail.npnster.first_project.api_params;

public class TokenBasedRequest {
	private String api_access_token;

	public String getToken() {
		return api_access_token;
	}

	public void setToken(String token) {
		this.api_access_token = token;
	}

}
