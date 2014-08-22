//package com.gmail.npnster.first_project;
//
//import javax.inject.Singleton;
//
//import com.gmail.npnster.first_project.LocationMonitorService.GetMarkerRequestTimer;
//import com.squareup.otto.Bus;
//
//import dagger.Module;
//import dagger.Provides;
//
//@Module(
//	injects = { 
//				MapMarker.class,
//				ApiRequestRepository.class,
//				DeviceLocationClient.class,
//				GcmBroadcastReceiver.class,
//				HomeActivity.PlaceholderFragment.class,
//				MapActivity.WrapperFragment.class,
//				MainActivity.class,
//				RegisterGcmActivity.class,
//				SignUpActivity.class,
//				ApiExActivity.class,
//				LocationMonitorService.class,
//				MapMarker.class,
//				MapMarkers.class,
//				MapPresenter.class,
//				UsersListActivity.class,
//				PersistData.class,
//				ApiCheckerActivity.PlaceholderFragment.class,  //can probably remove this class completely
//				MyApp.class
//			  },
//	library = true,
//	overrides = true
//	)
//
//public class ApplicationModuleTest {
//	
//	private MyApp mApp;
//	
//	public ApplicationModuleTest(MyApp app) {
//		System.out.println("creating production version of ApplicationModule");
//		mApp = app;
//	}
//	
//	@Provides @Singleton
//	public MyApp provideMyApp() {
//		return mApp;
//	}
//
//	@Provides
//	public GoogleMapMarkerParameters provideGoogleMapMarkerParameters() {
//		System.out.println("inside dagger -non mock");
//		return new GoogleMapMarkerParameters();
//	}
//	
//	
//	@Provides @Singleton
//	public Bus provideBus () {  
//		return new Bus();
//	}
//	
//	@Provides @Singleton
//	public PersistData providePersistData () {
//		System.out.println(String.format("mApp = %s", mApp));
//		return new PersistData(mApp);
//	}
//	
//	@Provides @Singleton
//	public LocationMonitorService.GetMarkerRequestTimer provideGetMarkerRequestTimer () {
//		return new LocationMonitorService.GetMarkerRequestTimer(10*60000,10000);
//	}
//	
//	@Provides @Singleton
//	public LocationMonitorService.PushRequestTimer providePushRequestTimer () {
//		return new LocationMonitorService.PushRequestTimer(10*60000,60000);
//	}	
//}
