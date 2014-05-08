package com.gmail.npnster.first_project.api_params;

public class UserRequestParams {
	private String api_access_token;
	private User user;
	
	public UserRequestParams(String apiAccessToken) {
		api_access_token = apiAccessToken;
		user = null;
	}
	
	public UserRequestParams(String name, String email, String password, String passwordConfirmation) {
		user = new User();
		user.name = name;
		user.email = email;
		user.password = password;
		user.password_confirmation = passwordConfirmation;
	}
	

}