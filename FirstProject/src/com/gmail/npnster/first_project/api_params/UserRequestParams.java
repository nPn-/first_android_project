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
		api_access_token = null;
	}
	
	public UserRequestParams(String name, String email, String password, String passwordConfirmation, String currentPassword) {
		user = new User();
		user.name = name;
		user.email = email;
		user.password = password;
		user.password_confirmation = passwordConfirmation;
		user.current_password = currentPassword;
		api_access_token = null;
	}

	private class User {
		protected String name;
		protected String email;
		protected String password;
		protected String password_confirmation;
		protected String current_password;
	}
	
	public void setApiAccessToken(String token) {
		api_access_token = token;
	}
	

}