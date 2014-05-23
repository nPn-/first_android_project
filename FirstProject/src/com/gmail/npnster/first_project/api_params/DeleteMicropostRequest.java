package com.gmail.npnster.first_project.api_params;

public class DeleteMicropostRequest {
	private String micropostId;

	public String getMicropostId() {
		return micropostId;
	}

	public DeleteMicropostRequest(String micropostId) {
		super();
		this.micropostId = micropostId;
	}
	
}
