package com.gmail.npnster.first_project;

import javax.inject.Singleton;

import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

@Module(
	injects = { 
				MapMarker.class,
				ApiRequestRepository.class,
				DeviceLocationClient.class,
				GcmBroadcastReceiver.class,
				HomeActivity.PlaceholderFragment.class,
				MapActivity.WrapperFragment.class,
				MainActivity.class,
				RegisterGcmActivity.class,
				SignUpActivity.class,
				ApiCheckerActivity.PlaceholderFragment.class,  //can probably remove this class completely
				MyApp.class
			  },
	library = true
	)

public class ApplicationModule {
	
	private MyApp mApp;
	
	public ApplicationModule(MyApp app) {
		System.out.println("creating production version of ApplicationModule");
		mApp = app;
	}

	@Provides
	public GoogleMapMarkerParameters provideGoogleMapMarkerParameters() {
		System.out.println("inside dagger -non mock");
		return new GoogleMapMarkerParameters();
	}
	
	@Provides @Singleton
	public MyApp provideMyApp() {
		return getApp();
	}
	
	@Provides @Singleton
	public Bus provideBus () {
		return BusProvider.getInstance();
	}
	
	public MyApp getApp() {
		return mApp;
	}

}
