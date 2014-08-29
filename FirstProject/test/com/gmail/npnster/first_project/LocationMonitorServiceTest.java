package com.gmail.npnster.first_project;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.*;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import com.gmail.npnster.first_project.LocationMonitorService.GetMarkerRequestTimer;
import com.gmail.npnster.first_project.LocationMonitorService.PushRequestTimer;
import com.gmail.npnster.first_project.api_params.GetMapMarkersRequest;
import com.gmail.npnster.first_project.api_params.PushLocationsUpdateRequestRequest;
import com.squareup.otto.Bus;

@RunWith(RobolectricTestRunner.class)
public class LocationMonitorServiceTest {
	@Inject GetMarkerRequestTimer mockMarkerRequestTimer;
	@Inject PushRequestTimer mockPushRequestTimer;
	@Inject Bus mockBus;
	@Inject DeviceLocationClient mockDeviceLocationClient;
	PendingIntent mockPendingIntent;
	AlarmManager mockAlarmManager;
	LocationMonitorService service;
	
	
	@Before
	public void setUp() throws Exception {
		System.out.println("setting up the test");
		MyApp app = (MyApp) Robolectric.application;
		Injector.getInstance().initialize(app); 
		Injector.getInstance().injectWith(this,new LocationMonitorServiceModule(null));
		service = new LocationMonitorService();
		service.injectMe();

	}
	
	@Test
	public void testStartRequestMarkerUpdates() {
		service.startRequestMarkerUpdates();
		verifyStareRequestMarkerUpdates();
		
	}
	
	void verifyStareRequestMarkerUpdates() {
		InOrder inOrder = inOrder(mockMarkerRequestTimer,mockBus);
		inOrder.verify(mockBus).post(isA(GetMapMarkersRequest.class));
		inOrder.verify(mockMarkerRequestTimer).cancel();
		inOrder.verify(mockMarkerRequestTimer).start();
		
	}
	
	@Test
	public void testEndRequestMarkerUpdates() {
		service.endRequestMarkerUpdates();
		verify(mockMarkerRequestTimer).cancel();
		
	}
	
	@Test
	public void testStartLocationPushRequests() {
		service.startLocationPushRequests();
		verifyStartLocationPushRequests();
		
	}
	
	void verifyStartLocationPushRequests() {
		InOrder inOrder = inOrder(mockPushRequestTimer,mockBus);
		inOrder.verify(mockBus).post(isA(PushLocationsUpdateRequestRequest.class));
		inOrder.verify(mockBus).post(isA(GetMapMarkersRequest.class));
		inOrder.verify(mockPushRequestTimer).cancel();
		inOrder.verify(mockPushRequestTimer).start();
		
	}
	
	@Test
	public void testEndLocationPushRequests() {
		service.endLocationPushRequests();
		verify(mockPushRequestTimer).cancel();
		
	}
	
	@Test
	public void testOnCreate() {
		service.onCreate();
		mockAlarmManager = service.getAlarmManager();
		mockPendingIntent = service.getGcmKeepAlivePendingIntent();
		System.out.println(String.format("in test am = %s", mockAlarmManager));
		verify(mockAlarmManager).setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000, 4*60*1000, mockPendingIntent);
		
	}
	
	@Test
	public void testPauseFragment() {
		Intent intent = new Intent();
		intent.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_PAUSED");
		service.onStartCommand(intent, 0, 0);
		verify(mockMarkerRequestTimer).cancel();
		verify(mockPushRequestTimer).cancel();
		
	}
	
	@Test
	public void testResumeFragment() {
		Intent intent = new Intent();
		intent.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.MAP_FRAGMENT_RESUMED");
		service.onStartCommand(intent, 0, 0);
		verifyStartLocationPushRequests();
		verifyStareRequestMarkerUpdates();
		
	}
	
	@Test
	public void testRequestForUpdateLocationUpdatesReceived() {
		Intent intent = new Intent();
		intent.addCategory("COM.GMAIL.NPNSTER.FIRST_PROJECT.LOCATION_UPDATE_REQUEST_RECEIVED");
		service.onStartCommand(intent, 0, 0);
		verify(mockDeviceLocationClient).requestLLocationUpdates();;
		
	}
	
	
}
