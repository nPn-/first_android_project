package com.gmail.npnster.first_project.api_params;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignupResponse {
	private String token;
	private List<String> errors = new ArrayList<String>();
	private Boolean mSuccessful;

	public List<String> getErrors() {
		return errors;
	}
	public String getToken() {
		return token;
	}
	
	public void setSuccessful(Boolean successful) {
		mSuccessful = successful;
	}
	
	public Boolean isSuccessful() {
		return mSuccessful;
	}

}
