package com.gmail.npnster.first_project.int_test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.gmail.npnster.first_project.ApiExActivity;
import com.gmail.npnster.first_project.ApiExActivity.ApiCall;
import com.gmail.npnster.first_project.MyApp;
import com.gmail.npnster.first_project.PersistData;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
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
		solo.waitForText("Done!", 1, TIMEOUT);
	}

	private void setValidTokenAndId() {
		MyApp.saveToken(valid_android_token);
		MyApp.saveEmailId("valid_android_user@example.com");
	}
	
	private void setBadTokenAndId() {
		MyApp.saveToken("0:blah");
		MyApp.saveEmailId("invalid_android_user@example.com");
	}

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
		getActivity().setLeaveRequest(
				new LeaveRequest("valid_android_user@example.com", "foobar"));
		clickAndWait();
		getActivity().setSignupRequest(
				new SignupRequest("robot", "valid_android_user@example.com",
						"foobar", "foobar"));
		clickAndWait();
		valid_android_token = getActivity().getSignupResponse().getToken();
		MyApp.saveToken(valid_android_token);
		MyApp.saveEmailId("valid_android_user@example.com");

	}

	protected void tearDown() throws Exception {
		super.tearDown();

	}

	public void testLeave() throws Exception {
		getActivity().setLeaveRequest(
				new LeaveRequest("jdd_012@example.com", "foobar"));
		clickAndWait();
		assertTrue("leave is successful", getActivity().getLeaveResponse()
				.isSuccessful());
		assertEquals("response is NO CONTENT (204) ", 204, getActivity()
				.getLeaveResponse().getRawResponse().getStatus());
		System.out.println("leave request done");

	}

	public void testSignupSucess() throws Exception {
		getActivity().setLeaveRequest(
				new LeaveRequest("jdd_012@example.com", "foobar"));
		clickAndWait();
		getActivity().setSignupRequest(
				new SignupRequest("john", "jdd_012@example.com", "foobar",
						"foobar"));
		clickAndWait();
		assertTrue("signup is successful", getActivity().getSignupResponse()
				.isSuccessful());
		assertEquals("response is CREATED (201) ", 201, getActivity()
				.getSignupResponse().getRawResponse().getStatus());

	}

	public void testSignupEmailTaken() throws Exception {
		getActivity().setSignupRequest(
				new SignupRequest("john", "jdd_012@example.com", "foobar",
						"foobar"));
		clickAndWait();
		getActivity().setSignupRequest(
				new SignupRequest("john", "jdd_012@example.com", "foobar",
						"foobar"));
		clickAndWait();
		System.out.println(getActivity().getSignupResponse().getErrors()
				.toString());
		assertFalse("signup is not successful", getActivity()
				.getSignupResponse().isSuccessful());
		assertTrue(
				"errors includes email taken",
				getActivity().getSignupResponse().getErrors()
						.contains("Email has already been taken"));

	}

	public void testGetProfile() throws Exception {
		getActivity().setGetUserProfileRequest(new GetUserProfileRequest());
		clickAndWait();
		GetUserProfileResponse getUserProfileResponse = getActivity()
				.getGetUserProfileResponse();
		assertEquals("name is robot", "robot", getUserProfileResponse.getName());
		assertNotNull("email is not null", getUserProfileResponse.getEmail());
		assertNotNull("gravatar is not null",
				getUserProfileResponse.getGravatar_id());
		assertTrue("followed count is >= 0 ",
				getUserProfileResponse.getFollowed_users_count() >= 0);
		assertTrue("followers count is >= 0",
				getUserProfileResponse.getFollowers_count() >= 0);

	}
	
	public void testGetProfileFail() throws Exception {
		setBadTokenAndId();
		getActivity().setGetUserProfileRequest(new GetUserProfileRequest());
		clickAndWait();
		GetUserProfileResponse getUserProfileResponse = getActivity()
				.getGetUserProfileResponse();
		assertNull("name is null", getUserProfileResponse.getName());
		assertNull("email is not null", getUserProfileResponse.getEmail());
		assertNull("gravatar is not null",
				getUserProfileResponse.getGravatar_id());
		assertNull("followed count is null ",
				getUserProfileResponse.getFollowed_users_count());
		assertNull("followers count is null",
				getUserProfileResponse.getFollowers_count());
		assertEquals("response is unauthorized (401)", 401, getUserProfileResponse.getRawResponse().getStatus());

	}
	
	public void testGetUsers() throws Exception {
		getActivity().setGetUsersRequest(new GetUsersRequest());
		clickAndWait();
		GetUsersResponse getUsersResponse = getActivity()
				.getGetUsersResponse();
		assertNotNull("user list is not null", getUsersResponse.getUsers());
		assertTrue("user list has size > 0 ", getUsersResponse.getUsers().size() > 0 );
		assertEquals("response is OK (200)", 200, getUsersResponse.getRawResponse().getStatus());		
	}
	
	public void testGetUsersFail() throws Exception {
		setBadTokenAndId();
		getActivity().setGetUsersRequest(new GetUsersRequest());
		clickAndWait();
		GetUsersResponse getUsersResponse = getActivity()
				.getGetUsersResponse();
		assertNotNull("user list is not null", getUsersResponse.getUsers());
		assertTrue("user list is empty", getUsersResponse.getUsers().size() == 0);
		assertEquals("response is Unauthorized (401)", 401, getUsersResponse.getRawResponse().getStatus());		
	}
	
	
}
