package com.gmail.npnster.first_project;

import javax.inject.Inject;

import android.app.IntentService;
import android.content.Intent;

public class ApiIntentService extends IntentService {

	public ApiIntentService() {
		super("ApiIntentService");
	    System.out.println(String.format("***Intent service %s created", this.getClass().getName()));
	    Injector.getInstance().inject(this);
	}

	@Inject RailsApiQue mRailsApiQue;
	
	@Override
	protected void onHandleIntent(Intent intent) {
		
		System.out.println("----- begin API call ----");
		RailsApiQueEntry entry = mRailsApiQue.poll();
		entry.run();
		System.out.println("---- end API call ----");
		
	}

}
