package com.gmail.npnster.first_project.api_params;

public class UserRequestParams {
	private String mApiAccessToken;
	private User mUser;
	
	public UserRequestParams(String apiAccessToken) {
		mApiAccessToken = apiAccessToken;
		mUser = null;
	}
	
	public UserRequestParams(String name, String email, String password, String passwordConfirmation) {
		mUser = new User();
		mUser.mName = name;
		mUser.mEmail = email;
		mUser.mPassword = password;
		mUser.mPasswordConfirmation = passwordConfirmation;
	}
	

}