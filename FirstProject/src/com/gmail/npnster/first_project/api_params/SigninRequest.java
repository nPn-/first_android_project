package com.gmail.npnster.first_project.api_params;

public class SigninRequest {

	private User user;
	
	public SigninRequest(String email, String password) {
		super();
		user = new User();
		this.user.email = email;
		this.user.password = password;
	}
    
	private class User {
		
		private String email;
		private String password;
		
	}
}
