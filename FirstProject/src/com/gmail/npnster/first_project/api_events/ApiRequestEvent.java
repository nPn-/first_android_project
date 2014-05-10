package com.gmail.npnster.first_project.api_events;

public class ApiRequestEvent<T> {
	protected T mParams;
	
	public ApiRequestEvent(T params) {
		mParams = params;
	}
	
	public T getParams() {
		return mParams;
	}

}
