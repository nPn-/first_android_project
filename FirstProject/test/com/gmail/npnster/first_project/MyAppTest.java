package com.gmail.npnster.first_project;

import javax.inject.Inject;

import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.RailsMarker;
import com.gmail.npnster.first_project.api_params.RailsMarkerBuilder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;








//import static org.hamcrest.CoreMatchers.equalTo;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThat;
import static org.fest.assertions.api.ANDROID.assertThat;

@RunWith(RobolectricTestRunner.class)
public class MyAppTest  {

	

	MyApp app ;
	PersistData persistData;
	@Before
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
		app = (MyApp) Robolectric.application;
		persistData = app.getPersistData();
		app.clearToken();
		app.clearEmailId();
	}

	
	@Test
	public void testInitalToken() {
		assertThat(app.getToken()).isEqualTo("");
		assertThat(persistData.readAccessToken()).as("persisted token is blank").isEqualTo("");
			
	}
	
	
	@Test	
	public void testReadToken() {
		String testToken = "test_token";
		app.saveToken(testToken);
		assertThat(app.getToken()).as("the retreived token via app").isEqualTo(testToken);
		assertThat(persistData.readAccessToken()).as("the retreived token via persistData").isEqualTo(testToken);
		
	}

	@Test
	public void testClearToken() {
		String testToken = "test_token";
		app.saveToken(testToken);
		assertThat(app.getToken()).as("app token matches the value stored").isEqualTo(testToken);
		assertThat(persistData.readAccessToken()).as("persisted token matches the value stored").isEqualTo(testToken);
		app.clearToken();
		assertThat(app.getToken()).as("app token is blank").isEqualTo("");
		assertThat(persistData.readAccessToken()).as("persisted token is blank").isEqualTo("");
		
	}
	
	@Test
	public void testInitalId() {
		assertThat(app.getEmail()).as("app email is blank").isEqualTo("");
		assertThat(persistData.readEmailId()).as("persisted email is blank").isEqualTo("");
		
	}
	
	@Test
	public void testReadId() {
		String testUserId = "test_user@example.com";
		app.saveEmailId(testUserId);
		assertThat(app.getEmail()).as("app email matches stored value").isEqualTo(testUserId);
		assertThat(persistData.readEmailId()).as("persisted email matches stored value").isEqualTo(testUserId);
		
	}
	
	@Test
	public void testClearId() {
		String testUserId = "test_user@example.com";
		app.saveEmailId(testUserId);
		assertThat(app.getEmail()).as("app email matches store value").isEqualTo(testUserId);
		assertThat(persistData.readEmailId()).as("persisted email matches store value").isEqualTo(testUserId);
		app.clearEmailId();
		assertThat(app.getEmail()).as("app email is blank").isEqualToIgnoringCase("");
		assertThat(persistData.readEmailId()).as("persisted email is blank").isEqualToIgnoringCase("");
		
	}
	

	@Test
	public void testInitalGcmId() {
		System.out.println("testInitalGcmId");
		assertThat(app.getGcmRegId()).as("app initial gcm reg id is blank").isEqualTo("");
		assertThat(persistData.readGcmRegId()).as("persisted initial gcm reg id is blank").isEqualTo("");
		
	}
	
	@Test
	public void testReadGcmId() {
		String testGcmId = "example_gcm_id";
		app.saveGcmRegId(testGcmId);
		assertThat(app.getGcmRegId()).as("app gcm id matches stored value").isEqualTo(testGcmId);
		assertThat(persistData.readGcmRegId()).as("persisted gcm id matches stored value").isEqualTo(testGcmId);
		
	}

	@Test
	public void testClearGcmId() {
		String testGcmId = "example_gcm_id";
		app.saveGcmRegId(testGcmId);
		assertThat(app.getGcmRegId()).as("app gcm id matches stored value").isEqualTo(testGcmId);
		assertThat(persistData.readGcmRegId()).as("persisted gcm id matches stored value").isEqualTo(testGcmId);
		app.clearGcmRegId();
		assertThat(app.getGcmRegId()).as("persisted gcm id matches stored value").isEqualTo("");
		assertThat(persistData.readGcmRegId()).as("persisted gcm id matches stored value").isEqualTo("");
		
	}
	
	@Test
	public void testInitialUserId() {
		assertThat(app.getUserId()).as("app inital user id is blank").isEqualTo("");
		assertThat(persistData.readUserId()).as("persisted inital user id is blank").isEqualTo("");
		
	}
	
	@Test
	public void testGetUserId() {
		app.saveToken("2:blah");
		assertThat(app.getUserId()).as("app has the correct token").isEqualTo("2");
		assertThat(persistData.readUserId()).as("persisted data has the correct token").isEqualTo("2");
		
	}
	
	@Test
	public void testInitialMapBounds() {
		LatLngBounds bounds = new LatLngBounds(new LatLng(1.0,-179.0), new LatLng(89.0,179.0));
		assertThat(app.getMapBounds()).as("app has the correct inital bounds").isEqualTo(bounds);
		assertThat(persistData.readMapBounds()).as("persisted has the correct inital bounds").isEqualTo(bounds);
		
	}
	
	@Test
	public void testSaveMapBounds() {
		LatLngBounds bounds = new LatLngBounds(new LatLng(45.0,71.0), new LatLng(45.1f,71.1f));
		app.saveMapBounds(bounds);
		assertThat(app.getMapBounds()).as("app has the stored map bounds").isEqualTo(bounds);
		assertThat(persistData.readMapBounds()).as("persistData has the stored map bounds"). isEqualTo(bounds);
		
	}
	
	@Test
	public void testInitialCenterOnPosition() {
		assertThat(app.getCenterOnPosition()).as("app has the correct initial center on position").isEqualTo(0);
		assertThat(persistData.readCenterOnPosition()).as("persistData has the correct initial center on position").isEqualTo(0);
		
	}
	
	@Test
	public void testSaveCenterOnPosition() {
		app.saveCenterOnPosition(5);
		assertThat(app.getCenterOnPosition()).as("app has the stored centerOnPosition").isEqualTo(5);
		assertThat(persistData.readCenterOnPosition()).as("PesistData has the stored centerOnPosition").isEqualTo(5);
		
	}
	
	@Test
	public void testInitialCenterOnMode() {
		assertThat(app.getCenterOnMode()).as("app has the correct initial center on mode").isEqualTo(0);
		assertThat(persistData.readCenterOnMode()).as("PersistData has the correct initial center on mode").isEqualTo(0);
		
	}
	
	@Test
	public void testSaveCenterOnMode() {
		app.saveCenterOnMode(5);
		assertThat(app.getCenterOnMode()).as("app has the stored centerOnMode").isEqualTo(5);
		assertThat(persistData.readCenterOnMode()).as("persistData has the stored centerOnMode").isEqualTo(5);
		
	}
	
	@Test
	public void testMarkerFactory() {
		
		RailsMarker myMarker = new RailsMarkerBuilder().withName("john").build();
		System.out.println(String.format("marker name  = %s", myMarker.getName()));
	}
	
	

}
