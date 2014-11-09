package com.gmail.npnster.first_project.api_params;

public class UpdateUserResponse extends BaseResponse<UpdateUserRequest> {
	
	private String api_access_token = null;
	
	public String getToken() {
		return api_access_token;
	}

}
