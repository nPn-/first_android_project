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
				ApiExActivity.class,
				LocationMonitorService.class,
				MapMarker.class,
				MapMarkers.class,
				MapPresenter.class,
				UsersListActivity.class,
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
		System.out.println("inside provide MyApp for test");
//		return (MyApp) Robolectric.application;
		return mApp;
	}

	@Provides @Singleton
	public Bus provideBus () {
		return mock(Bus.class);
	}
	
	
}
