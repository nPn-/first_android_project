package com.gmail.npnster.first_project;

import static org.mockito.Mockito.mock;

import javax.inject.Singleton;

import org.mockito.Mockito;
import org.robolectric.Robolectric;

import android.content.Context;

import com.gmail.npnster.first_project.LocationMonitorService.GetMarkerRequestTimer;
import com.gmail.npnster.first_project.LocationMonitorService.PushRequestTimer;
import com.squareup.otto.Bus;

import dagger.Module;
import dagger.Provides;

@Module(
		injects = { 
				
				MapMarkerTest.class,
//				LocationMonitorServiceTest.class,
				
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
				MapMarker.class,
				MapMarkers.class,
				MyAppTest.class,
				TestMyApp.class,
				MapPresenter.class,
				UsersListActivity.class,
//				LocationMonitorService.class,
				LocationMonitorService.GetMarkerRequestTimer.class,
				LocationMonitorService.PushRequestTimer.class,
				ApiCheckerActivity.PlaceholderFragment.class,  //can probably remove this class completely
				MyApp.class
			  },
	library = true,
	complete = false
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
	
	@Provides @Singleton
	public PersistData providePersistData () {
		System.out.println(String.format("mApp = %s", mApp));
		return new PersistData(mApp);
	}
	
	@Provides @Singleton
	public GetMarkerRequestTimer provideGetMarkerRequestTimer () {
		return mock(LocationMonitorService.GetMarkerRequestTimer.class);
	}
	
	@Provides @Singleton
	public PushRequestTimer providePushRequestTimer () {
		return mock(LocationMonitorService.PushRequestTimer.class);
	}
	
	@Provides @Singleton
	DeviceLocationClient provideDeviceLocationClient() {
		return mock(DeviceLocationClient.class);
	}
	
//	  @Provides @Singleton  Context provideApplicationContext() {
////		  return mApplicationContext;
//	    return Robolectric.application;
//	  }
	
}
