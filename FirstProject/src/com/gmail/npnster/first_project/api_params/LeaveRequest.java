package com.gmail.npnster.first_project.api_params;

public class LeaveRequest {
	private String email;
	private String password;
	
	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public LeaveRequest(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

}
