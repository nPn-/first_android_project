package com.gmail.npnster.first_project.api_params;

public class CreateMicropostRequest {

	private String api_access_token;
	private Micropost micropost;
	
	public void setApi_access_token(String api_access_token) {
		this.api_access_token = api_access_token;
	}

	public CreateMicropostRequest(String content) {
		micropost = new Micropost(content);
	}
		
	private class Micropost {
	
		private String content;
		
		public Micropost(String content) {
			this.content = content;
		}		
	}
}
