package com.gmail.npnster.first_project;

import static org.assertj.core.api.Assertions.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.graphics.Bitmap;

import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.RailsMarker;
import com.google.android.gms.maps.model.LatLng;
import com.squareup.otto.Bus;

import static org.mockito.Mockito.*;

//@Config(manifest = "/home/john/git/first_project/FirstProject/AndroidManifest.xml")
@RunWith(RobolectricTestRunner.class)
public class MapMarkerTest {
	Bus mockBus = mock(Bus.class);
	RailsMarker mockRailsMarker = mock(RailsMarker.class);
	@Inject
	GoogleMapMarkerParameters mockGoogleMapMarkerParameters;
	
	
	@Before
	public void setUp() throws Exception {
		System.out.println("setting up the test");
		MyApp app = (MyApp) Robolectric.application;
		Injector.getInstance().initialize(app); 
		Injector.getInstance().inject(this);

	}

	
	@Test
	public void testGetLatLng() {	

		when(mockRailsMarker.getLatitude()).thenReturn(41.5);
		when(mockRailsMarker.getLongitude()).thenReturn(-75.5);
		MapMarker androidMapMarker = new MapMarkerBuilder().withMarker(mockRailsMarker)
				                                           .build();
		assertThat(androidMapMarker.getLatLng()).as("map marker latlng is correct").isEqualTo(new LatLng(41.5,-75.50));
	}
	
	@Test
	public void testOnBitMapLoaded() {
		Bitmap mockBitmap = mock(Bitmap.class);
		when(mockRailsMarker.getGravatarUrl()).thenReturn("a_rgavatar_url");
		MapMarker androidMapMarker = new MapMarkerBuilder().withBus(mockBus)
				.withMarker(mockRailsMarker)
				.build();
		androidMapMarker.onBitmapLoaded(mockBitmap, null);
		verify(mockBus).post(isA(MarkerReadyEvent.class));
		assertThat(androidMapMarker.getBitmap()).as("bitmap matches passed Bitmap argument").isEqualTo(mockBitmap);
	}
	
	@Test
	public void testOnBitMapFailed() {
		when(mockRailsMarker.getGravatarUrl()).thenReturn("a_rgavatar_url");
		MapMarker androidMapMarker = new MapMarkerBuilder().withBus(mockBus)
				                                           .withMarker(mockRailsMarker)
				                                           .build();
		androidMapMarker.onBitmapFailed(null);
		verify(mockBus).post(isA(MarkerReadyEvent.class));
		assertThat(androidMapMarker.getBitmap()).as("bitmap is null").isNull();
	}

	@Test 
	public void testGetGoogleMapMarkerParametersWithoutAccuracy() {
		when(mockRailsMarker.hasAccuracy()).thenReturn(false);
		when(mockRailsMarker.getAccuracy()).thenReturn(null);
		MapMarker androidMapMarker = new MapMarkerBuilder().withBus(mockBus)
				.withMarker(mockRailsMarker)
				.build();
		androidMapMarker.getGoogleMapMarkerParameters();
		verify(mockGoogleMapMarkerParameters).setCircleRadius(0f);
		
	}
	
	@Test 
	public void testGetGoogleMapMarkerParametersWithAccuracy() {
		when(mockRailsMarker.hasAccuracy()).thenReturn(true);
		when(mockRailsMarker.getAccuracy()).thenReturn(44.0f);
		MapMarker androidMapMarker = new MapMarkerBuilder().withBus(mockBus)
				.withMarker(mockRailsMarker)
				.build();
		androidMapMarker.getGoogleMapMarkerParameters();
		verify(mockGoogleMapMarkerParameters).setCircleRadius(44.0f);
		
	}
	
	@Test 
	public void testGetInfoWindowData() {
		when(mockRailsMarker.hasAccuracy()).thenReturn(true);
		when(mockRailsMarker.getAccuracy()).thenReturn(44.0f);
		when(mockRailsMarker.hasSpeed()).thenReturn(true);
		when(mockRailsMarker.getSpeed()).thenReturn(25.0f);
		when(mockRailsMarker.hasBearing()).thenReturn(true);
		when(mockRailsMarker.getBearing()).thenReturn(180.0f);
		MapMarker androidMapMarker = new MapMarkerBuilder().withMarker(mockRailsMarker)
				                                           .build();
		assertThat(androidMapMarker.getInfoWindowData()).contains("Accuracy", "Speed", "Bearing")
		                                                .contains("144 feet", "56 mph", "180 degrees");
		when(mockRailsMarker.hasAccuracy()).thenReturn(false);
		when(mockRailsMarker.hasSpeed()).thenReturn(false);
		when(mockRailsMarker.hasBearing()).thenReturn(false);
		assertThat(androidMapMarker.getInfoWindowData()).doesNotContain("Accuracy")
		                                                .doesNotContain("Speed")
		                                                .doesNotContain("Bearing");
		
	}
	
}
