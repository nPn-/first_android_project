package com.gmail.npnster.first_project.api_params;

import java.util.ArrayList;
import java.util.List;

public class BaseResponse {
	private List<String> errors = new ArrayList<String>();
	private Boolean mSuccessful = false;
	
	public List<String> getErrors() {
		return errors;
	}
	
	public void setSuccessful(Boolean successful) {
		mSuccessful = successful;
	}
	
	public Boolean isSuccessful() {
		return mSuccessful;
	}
}
