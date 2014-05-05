package com.gmail.npnster.first_project;

public class UserSignupParameters {
	private User user;
	
	public UserSignupParameters(String name, String email, String password, String passwordConfirmation) {
		user = new User();
		user.name = name;
		user.email = email;
		user.password = password;
		user.password_confirmation = passwordConfirmation;
	}
	
	protected class User {
		protected String name;
		protected String email;
		protected String password;
		protected String password_confirmation;
	}

}
