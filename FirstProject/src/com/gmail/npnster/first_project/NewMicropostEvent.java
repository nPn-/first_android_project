package com.gmail.npnster.first_project;

import javax.inject.Inject;

import android.content.Intent;

public class NewMicropostEvent {

	Intent mIntent;
	
	public NewMicropostEvent(Intent intent) {
		mIntent = intent;
	}
	
	public Intent getIntent() {
		return mIntent;
	}
	
}
