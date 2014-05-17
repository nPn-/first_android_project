package com.gmail.npnster.first_project.int_test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.gmail.npnster.first_project.ApiExActivity;
import com.gmail.npnster.first_project.ApiExActivity.ApiCall;
import com.gmail.npnster.first_project.MyApp;
import com.gmail.npnster.first_project.PersistData;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.LeaveRequest;
import com.gmail.npnster.first_project.api_params.SignupRequest;
import com.robotium.solo.Solo;

public class ApiExTest extends ActivityInstrumentationTestCase2<ApiExActivity> {
	
	public ApiExTest() {
		super(ApiExActivity.class);
		
	}

	private Solo solo;
	private Activity activity;
	private String valid_android_token;
	private final Integer TIMEOUT = 500;

	PersistData persistData = MyApp.getPersistData();
	
	private void clickAndWait() {
	    solo.clickOnButton("Go!");
	    solo.waitForText("Done!",1,TIMEOUT);		
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	    getActivity().setLeaveRequest(new LeaveRequest("valid_android_user@example.com", "foobar"));
	    clickAndWait();
	    getActivity().setSignupRequest(new SignupRequest("robot", "valid_android_user@example.com", "foobar", "foobar"));
	    clickAndWait();
	    valid_android_token = getActivity().getSignupResponse().getToken();
	    MyApp.saveToken(valid_android_token);
	    MyApp.saveEmailId("valid_android_user@example.com");
		

	}

	protected void tearDown() throws Exception {
		super.tearDown();

	}
	
	public void testLeave() throws Exception {
	    getActivity().setLeaveRequest(new LeaveRequest("jdd_012@example.com", "foobar"));
	    clickAndWait();
	    assertTrue("leave is successful", getActivity().getLeaveResponse().isSuccessful());
        System.out.println("leave request done");

	}
	
	public void testSignupSucess() throws Exception {
	    getActivity().setLeaveRequest(new LeaveRequest("jdd_012@example.com", "foobar"));
	    clickAndWait();
	    getActivity().setSignupRequest(new SignupRequest("john", "jdd_012@example.com", "foobar", "foobar"));
	    clickAndWait();
 	    assertTrue("signup is successful", getActivity().getSignupResponse().isSuccessful());
	    
		
	}
	
	public void testSignupEmailTaken() throws Exception {
	    getActivity().setSignupRequest(new SignupRequest("john", "jdd_012@example.com", "foobar", "foobar"));
	    clickAndWait();
	    getActivity().setSignupRequest(new SignupRequest("john", "jdd_012@example.com", "foobar", "foobar"));
	    clickAndWait();
	    System.out.println(getActivity().getSignupResponse().getErrors().toString());
	    assertFalse("signup is not successful", getActivity().getSignupResponse().isSuccessful());
	    assertTrue("errors includes email taken", getActivity().getSignupResponse().getErrors().contains("Email has already been taken"));
	    
		
	}
	
	public void testGetProfile() throws Exception {
	    getActivity().setGetUserProfileRequest(new GetUserProfileRequest());
	    clickAndWait();
	    GetUserProfileResponse getUserProfileResponse = getActivity().getGetUserProfileResponse();
	    assertEquals("name is robot", "robot", getUserProfileResponse.getName());
	    assertNotNull("email is not null",  getUserProfileResponse.getEmail());
	    assertNotNull("gravatar is not null",  getUserProfileResponse.getGravatar_id());
	    assertTrue("followed count is >= 0 ", getUserProfileResponse.getFollowed_users_count() >= 0);
	    assertTrue("followers count is >= 0", getUserProfileResponse.getFollowers_count() >= 0);
	   
		
	}

}
