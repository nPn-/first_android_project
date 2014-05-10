package com.gmail.npnster.first_project.api_params;

public class SignupRequest extends UserRequestParams {

	public SignupRequest(String name, String email, String password,
			String passwordConfirmation) {
		super(name, email, password, passwordConfirmation);
	}

}
