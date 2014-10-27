package com.gmail.npnster.first_project;

import javax.inject.Singleton;

import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import dagger.Module;
import dagger.Provides;

@Module(
		injects = { DeviceLocationClient.class,
				    ApiRequestRepositorySync.class,
			},
		library = true, complete = false
		)
public class AndroidModule {
	private final Context mApplicationContext;

	public AndroidModule(Context applicationContext) {
		System.out.println("inside production version of android module");
		mApplicationContext = applicationContext;
	}

	/**
	 * Allow the application context to be injected but require that it be
	 * annotated with {@link ForApplication @Annotation} to explicitly
	 * differentiate it from an activity context.
	 */
	@Provides
	@Singleton
	@ForApplication
	Context provideApplicationContext() {
		return mApplicationContext;
	}
	
	@Provides
	@Singleton
	@ForApplication
	TelephonyManager provideTelephonyManager() {
		return (TelephonyManager) mApplicationContext.getSystemService(Context.TELEPHONY_SERVICE);
	}

}
