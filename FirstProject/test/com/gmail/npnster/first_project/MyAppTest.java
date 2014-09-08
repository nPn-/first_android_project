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

	

//	MyApp app ;
	@Inject PersistData mPersistData;
	@Before
	public void setUp() throws Exception {
		// TODO Auto-generated method stub
//		app = (MyApp) Robolectric.application;
		Injector.getInstance().inject(this);
//		persistData = app.getPersistData();
		mPersistData.clearToken();
		mPersistData.clearEmailId();
	}

	
	@Test
	public void testInitalToken() {
		assertThat(mPersistData.getToken()).isEqualTo("");
		assertThat(mPersistData.readAccessToken()).as("persisted token is blank").isEqualTo("");
			
	}
	
	
	@Test	
	public void testReadToken() {
		String testToken = "test_token";
		mPersistData.saveToken(testToken);
		assertThat(mPersistData.getToken()).as("the retreived token via app").isEqualTo(testToken);
		assertThat(mPersistData.readAccessToken()).as("the retreived token via persistData").isEqualTo(testToken);
		
	}

	@Test
	public void testClearToken() {
		String testToken = "test_token";
		mPersistData.saveToken(testToken);
		assertThat(mPersistData.getToken()).as("app token matches the value stored").isEqualTo(testToken);
		assertThat(mPersistData.readAccessToken()).as("persisted token matches the value stored").isEqualTo(testToken);
		mPersistData.clearToken();
		assertThat(mPersistData.getToken()).as("app token is blank").isEqualTo("");
		assertThat(mPersistData.readAccessToken()).as("persisted token is blank").isEqualTo("");
		
	}
	
	@Test
	public void testInitalId() {
		assertThat(mPersistData.getEmail()).as("app email is blank").isEqualTo("");
		assertThat(mPersistData.readEmailId()).as("persisted email is blank").isEqualTo("");
		
	}
	
	@Test
	public void testReadId() {
		String testUserId = "test_user@example.com";
		mPersistData.saveEmailId(testUserId);
		assertThat(mPersistData.getEmail()).as("app email matches stored value").isEqualTo(testUserId);
		assertThat(mPersistData.readEmailId()).as("persisted email matches stored value").isEqualTo(testUserId);
		
	}
	
	@Test
	public void testClearId() {
		String testUserId = "test_user@example.com";
		mPersistData.saveEmailId(testUserId);
		assertThat(mPersistData.getEmail()).as("app email matches store value").isEqualTo(testUserId);
		assertThat(mPersistData.readEmailId()).as("persisted email matches store value").isEqualTo(testUserId);
		mPersistData.clearEmailId();
		assertThat(mPersistData.getEmail()).as("app email is blank").isEqualToIgnoringCase("");
		assertThat(mPersistData.readEmailId()).as("persisted email is blank").isEqualToIgnoringCase("");
		
	}
	

	@Test
	public void testInitalGcmId() {
		System.out.println("testInitalGcmId");
		assertThat(mPersistData.getGcmRegId()).as("app initial gcm reg id is blank").isEqualTo("");
		assertThat(mPersistData.readGcmRegId()).as("persisted initial gcm reg id is blank").isEqualTo("");
		
	}
	
	@Test
	public void testReadGcmId() {
		String testGcmId = "example_gcm_id";
		mPersistData.saveGcmRegId(testGcmId);
		assertThat(mPersistData.getGcmRegId()).as("app gcm id matches stored value").isEqualTo(testGcmId);
		assertThat(mPersistData.readGcmRegId()).as("persisted gcm id matches stored value").isEqualTo(testGcmId);
		
	}
	
	@Test
	public void testInitalCenterOnUserId() {
		assertThat(mPersistData.getCenterOnUserId()).as("app initial center on id is blank").isEqualTo("");
		assertThat(mPersistData.readCenterOnUserId()).as("persisted initial center on id is blank").isEqualTo("");
		
	}
	
	@Test
	public void testReadCenterOnUserId() {
		String testCenterOnUserId = "example_center_on_user_id";
		mPersistData.saveCenterOnUserId(testCenterOnUserId);
		assertThat(mPersistData.getCenterOnUserId()).as("app center on user id matches stored value").isEqualTo(testCenterOnUserId);
		assertThat(mPersistData.readCenterOnUserId()).as("persisted center on user  id matches stored value").isEqualTo(testCenterOnUserId);
		
	}

	@Test
	public void testClearGcmId() {
		String testGcmId = "example_gcm_id";
		mPersistData.saveGcmRegId(testGcmId);
		assertThat(mPersistData.getGcmRegId()).as("app gcm id matches stored value").isEqualTo(testGcmId);
		assertThat(mPersistData.readGcmRegId()).as("persisted gcm id matches stored value").isEqualTo(testGcmId);
		mPersistData.clearGcmRegId();
		assertThat(mPersistData.getGcmRegId()).as("persisted gcm id matches stored value").isEqualTo("");
		assertThat(mPersistData.readGcmRegId()).as("persisted gcm id matches stored value").isEqualTo("");
		
	}
	
	@Test
	public void testInitialUserId() {
		assertThat(mPersistData.getUserId()).as("app inital user id is blank").isEqualTo("");
		assertThat(mPersistData.readUserId()).as("persisted inital user id is blank").isEqualTo("");
		
	}
	
	@Test
	public void testGetUserId() {
		mPersistData.saveToken("2:blah");
		assertThat(mPersistData.getUserId()).as("app has the correct token").isEqualTo("2");
		assertThat(mPersistData.readUserId()).as("persisted data has the correct token").isEqualTo("2");
		
	}
	
	@Test
	public void testInitialMapBounds() {
		LatLngBounds bounds = new LatLngBounds(new LatLng(1.0,-179.0), new LatLng(89.0,179.0));
		assertThat(mPersistData.getMapBounds()).as("app has the correct inital bounds").isEqualTo(bounds);
		assertThat(mPersistData.readMapBounds()).as("persisted has the correct inital bounds").isEqualTo(bounds);
		
	}
	
	@Test
	public void testSaveMapBounds() {
		LatLngBounds bounds = new LatLngBounds(new LatLng(45.0,71.0), new LatLng(45.1f,71.1f));
		mPersistData.saveMapBounds(bounds);
		assertThat(mPersistData.getMapBounds()).as("app has the stored map bounds").isEqualTo(bounds);
		assertThat(mPersistData.readMapBounds()).as("persistData has the stored map bounds"). isEqualTo(bounds);
		
	}
	
	@Test
	public void testInitialCenterOnPosition() {
		assertThat(mPersistData.getCenterOnPosition()).as("app has the correct initial center on position").isEqualTo(0);
		assertThat(mPersistData.readCenterOnPosition()).as("persistData has the correct initial center on position").isEqualTo(0);
		
	}
	
	@Test
	public void testSaveCenterOnPosition() {
		mPersistData.saveCenterOnPosition(5);
		assertThat(mPersistData.getCenterOnPosition()).as("app has the stored centerOnPosition").isEqualTo(5);
		assertThat(mPersistData.readCenterOnPosition()).as("PesistData has the stored centerOnPosition").isEqualTo(5);
		
	}
	
	@Test
	public void testInitialCenterOnMode() {
		assertThat(mPersistData.getCenterOnMode()).as("app has the correct initial center on mode").isEqualTo(0);
		assertThat(mPersistData.readCenterOnMode()).as("PersistData has the correct initial center on mode").isEqualTo(0);
		
	}
	
	@Test
	public void testSaveCenterOnMode() {
		mPersistData.saveCenterOnMode(5);
		assertThat(mPersistData.getCenterOnMode()).as("app has the stored centerOnMode").isEqualTo(5);
		assertThat(mPersistData.readCenterOnMode()).as("persistData has the stored centerOnMode").isEqualTo(5);
		
	}
	
	@Test
	public void testMarkerFactory() {
		
		RailsMarker myMarker = new RailsMarkerBuilder().withName("john").build();
		System.out.println(String.format("marker name  = %s", myMarker.getName()));
	}
	
	

}
