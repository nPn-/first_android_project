package com.gmail.npnster.first_project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import com.gmail.npnster.first_project.api_params.MarkerBuilder;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.Marker;
import com.google.android.gms.maps.model.LatLng;

@RunWith(RobolectricTestRunner.class)
public class MapMarkerTest {
	
	@Before
	public void setUp() throws Exception {
		// TODO Auto-generated method stub

	}

	
//	@Test
//	public void testGetLatLng() {
//		
//		Marker aMarker = new MarkerBuilder().withLatitude(41.5)
//				                            .withLongitude(-75.5)
//				                            .build();
//		MapMarker aMapMarker = new MapMarkerBuilder().withMarker(aMarker).build();
//		assertThat(aMapMarker.getLatLng()).as("map marker latlng is correct").isEqualTo(new LatLng(41.50,-75.50));
//	}
	
}
