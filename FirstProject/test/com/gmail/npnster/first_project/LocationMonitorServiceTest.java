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
	LocationMonitorService service;
	
	
	@Before
	public void setUp() throws Exception {
		System.out.println("setting up the test");
		MyApp app = (MyApp) Robolectric.application;
		Injector.getInstance().initialize(app); 
		Injector.getInstance().inject(this);
		service = new LocationMonitorService();
		service.injectMe();

	}
	
	@Test
	public void testStartRequestMarkerUpdates() {
		InOrder inOrder = inOrder(mockMarkerRequestTimer,mockBus);
		service.startRequestMarkerUpdates();
		inOrder.verify(mockBus).post(isA(GetMapMarkersRequest.class));
		inOrder.verify(mockMarkerRequestTimer).cancel();
		inOrder.verify(mockMarkerRequestTimer).start();
		
	}
	
	@Test
	public void testStartLocationPushRequests() {
		InOrder inOrder = inOrder(mockPushRequestTimer,mockBus);
		service.startLocationPushRequests();
		inOrder.verify(mockBus).post(isA(PushLocationsUpdateRequestRequest.class));
		inOrder.verify(mockBus).post(isA(GetMapMarkersRequest.class));
		inOrder.verify(mockPushRequestTimer).cancel();
		inOrder.verify(mockPushRequestTimer).start();
		
	}
}
