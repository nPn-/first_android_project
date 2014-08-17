package com.gmail.npnster.first_project;

import static org.mockito.Mockito.mock;

import javax.inject.Singleton;

import org.mockito.Mockito;
import org.robolectric.Robolectric;

import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

@Module(
		injects = { 
				
				MapMarkerTest.class,
				
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
		System.out.println("creating test version of ApplicationModule");
		mApp = app;
	}
	@Provides @Singleton
	GoogleMapMarkerParameters provideGoogleMapMarkerParameters() {
		System.out.println("inside dagger - mock");
		return mock(GoogleMapMarkerParameters.class);

	}
	
	@Provides @Singleton
	public MyApp provideMyApp() {
		return (MyApp) Robolectric.application;
	}

	@Provides @Singleton
	public Bus provideBus () {
		return BusProvider.getInstance();
	}
	
	
}
