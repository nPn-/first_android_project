package com.gmail.npnster.first_project;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiRequestBuilder {
	
	private static final String SIGNUP_PATH = "/api/vi/signup";
	private String serverAddress;
	private Boolean secure;
	private String format;
	

	public ApiRequestBuilder(String serverAddress, Boolean secure) {
		super();
		this.serverAddress = serverAddress;
		this.secure = secure;
		this.format = secure ? "https://" : "http://";
		
	}
	
	public ApiRequestBuilder(String serverAddress) {
		this(serverAddress, false);
	}
	
	public ApiRequestBuilder() {
		this("10.0.2.2:3000", false);
	}
	
    

}
