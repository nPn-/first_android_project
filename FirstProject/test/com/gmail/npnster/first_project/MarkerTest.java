package com.gmail.npnster.first_project;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import com.gmail.npnster.first_project.api_params.MarkerBuilder;
import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.Marker;

@RunWith(RobolectricTestRunner.class)
public class MarkerTest {
	
	@Before
	public void setUp() throws Exception {
		// TODO Auto-generated method stub

	}

	
	@Test
	public void testMarkerFactory() {
		
		Marker myMarker = new MarkerBuilder().withName("john").build();
		assertThat(myMarker.getName()).as("marker name is correct").isEqualTo("john");
	}
	
}
