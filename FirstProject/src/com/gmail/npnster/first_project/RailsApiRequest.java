package com.gmail.npnster.first_project;

import java.util.HashMap;
import java.util.Map;

import android.os.AsyncTask;

public class RailsApiRequest extends AsyncTask {
	private String method;
	private Map params;
	private String urlString;
	
	public RailsApiRequest(String method, String url, Map params) {
		super();
		this.method = method;
		this.params = params;
		this.urlString = "http://10.0.2.2:3000" + url;
	}

	public Map getParams() {
		return params;
	}

	public void setParams(Map params) {
		this.params = params;
	}

	public String getUrlString() {
		return urlString;
	}

	public void setUrlString(String urlString) {
		this.urlString = urlString;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
