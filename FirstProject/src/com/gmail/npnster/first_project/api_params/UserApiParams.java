package com.gmail.npnster.first_project.api_params;

public class UserApiParams {
	private String api_access_token;
	private User user;
	
	public UserApiParams() {
		api_access_token = "";
		user = null;
	}
	
	public UserApiParams(String name, String email, String password, String passwordConfirmation) {
		user = new User();
		user.name = name;
		user.email = email;
		user.password = password;
		user.password_confirmation = passwordConfirmation;
	}
	

}