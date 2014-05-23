package com.gmail.npnster.first_project.api_params;

public class UpdateUserRequest extends UserRequestParams {
	

	public UpdateUserRequest(String name, String email, String password,
			String passwordConfirmation) {
		super(name, email, password, passwordConfirmation);
		
	}
	
	public void setApiAccessToken(String token) {
		super.setApiAccessToken(token);
	}
}
