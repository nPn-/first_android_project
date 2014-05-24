package com.gmail.npnster.first_project.api_params;


public class SignoutRequest extends TokenBasedRequest {
	
	private String email;
	private String password;
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	
	public SignoutRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	public SignoutRequest() {
		this.email = null;
		this.password = null;
	}


}

