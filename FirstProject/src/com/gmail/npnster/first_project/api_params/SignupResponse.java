package com.gmail.npnster.first_project.api_params;

public class SignupResponse extends BaseResponse<SignupRequest> {
	private String token;

	public String getToken() {
		String.format("**** token = %s",token);
		return token;
	}
	
}
