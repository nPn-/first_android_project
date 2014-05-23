package com.gmail.npnster.first_project.int_test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;

import com.gmail.npnster.first_project.ApiExActivity;
import com.gmail.npnster.first_project.ApiExActivity.ApiCall;
import com.gmail.npnster.first_project.MyApp;
import com.gmail.npnster.first_project.PersistData;
import com.gmail.npnster.first_project.api_params.BaseResponse;
import com.gmail.npnster.first_project.api_params.CreateMicropostRequest;
import com.gmail.npnster.first_project.api_params.CreateMicropostResponse;
import com.gmail.npnster.first_project.api_params.DeleteMicropostRequest;
import com.gmail.npnster.first_project.api_params.DeleteMicropostResponse;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersResponse;
import com.gmail.npnster.first_project.api_params.GetFollowersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowersResponse;
import com.gmail.npnster.first_project.api_params.GetMicropostsRequest;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse.Micropost;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.gmail.npnster.first_project.api_params.LeaveRequest;
import com.gmail.npnster.first_project.api_params.SignupRequest;
import com.gmail.npnster.first_project.api_params.UnfollowRequest;
import com.gmail.npnster.first_project.api_params.UnfollowResponse;
import com.gmail.npnster.first_project.api_params.UpdateUserRequest;
import com.gmail.npnster.first_project.api_params.UpdateUserResponse;
import com.robotium.solo.Solo;

public class ApiExTest extends ActivityInstrumentationTestCase2<ApiExActivity> {

	public ApiExTest() {
		super(ApiExActivity.class);

	}

	private Solo solo;
	private Activity activity;
	private String valid_android_token;
	private final Integer TIMEOUT = 2000;

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
		getActivity().setLeaveRequest(
				new LeaveRequest("new_email_address@example.com", "foobar"));
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

//	 public void testLeave() throws Exception {
//	 getActivity().setLeaveRequest(
//	 new LeaveRequest("jdd_012@example.com", "foobar"));
//	 clickAndWait();
//	 assertTrue("leave is successful", getActivity().getLeaveResponse()
//	 .isSuccessful());
//	 assertEquals("response is NO CONTENT (204) ", 204, getActivity()
//	 .getLeaveResponse().getRawResponse().getStatus());
//	 System.out.println("leave request done");
//	
//	 }
//	
//	 public void testSignupSucess() throws Exception {
//	 getActivity().setLeaveRequest(
//	 new LeaveRequest("jdd_012@example.com", "foobar"));
//	 clickAndWait();
//	 getActivity().setSignupRequest(
//	 new SignupRequest("john", "jdd_012@example.com", "foobar",
//	 "foobar"));
//	 clickAndWait();
//	 assertTrue("signup is successful", getActivity().getSignupResponse()
//	 .isSuccessful());
//	 assertEquals("response is CREATED (201) ", 201, getActivity()
//	 .getSignupResponse().getRawResponse().getStatus());
//	
//	 }
//	
//	 public void testSignupEmailTaken() throws Exception {
//	 getActivity().setSignupRequest(
//	 new SignupRequest("john", "jdd_012@example.com", "foobar",
//	 "foobar"));
//	 clickAndWait();
//	 getActivity().setSignupRequest(
//	 new SignupRequest("john", "jdd_012@example.com", "foobar",
//	 "foobar"));
//	 clickAndWait();
//	 System.out.println(getActivity().getSignupResponse().getErrors()
//	 .toString());
//	 assertFalse("signup is not successful", getActivity()
//	 .getSignupResponse().isSuccessful());
//	 assertTrue(
//	 "errors includes email taken",
//	 getActivity().getSignupResponse().getErrors()
//	 .contains("Email has already been taken"));
//	
//	 }
//	
//	 public void testGetProfile() throws Exception {
//	 getActivity().setGetUserProfileRequest(new GetUserProfileRequest());
//	 clickAndWait();
//	 GetUserProfileResponse getUserProfileResponse = getActivity()
//	 .getGetUserProfileResponse();
//	 assertEquals("name is robot", "robot", getUserProfileResponse.getName());
//	 assertNotNull("email is not null", getUserProfileResponse.getEmail());
//	 assertNotNull("gravatar is not null",
//	 getUserProfileResponse.getGravatar_id());
//	 assertTrue("followed count is >= 0 ",
//	 getUserProfileResponse.getFollowed_users_count() >= 0);
//	 assertTrue("followers count is >= 0",
//	 getUserProfileResponse.getFollowers_count() >= 0);
//	
//	 }
//	
//	 public void testGetProfileFail() throws Exception {
//	 setBadTokenAndId();
//	 getActivity().setGetUserProfileRequest(new GetUserProfileRequest());
//	 clickAndWait();
//	 GetUserProfileResponse getUserProfileResponse = getActivity()
//	 .getGetUserProfileResponse();
//	 assertNull("name is null", getUserProfileResponse.getName());
//	 assertNull("email is not null", getUserProfileResponse.getEmail());
//	 assertNull("gravatar is not null",
//	 getUserProfileResponse.getGravatar_id());
//	 assertNull("followed count is null ",
//	 getUserProfileResponse.getFollowed_users_count());
//	 assertNull("followers count is null",
//	 getUserProfileResponse.getFollowers_count());
//	 assertEquals("response is unauthorized (401)", 401,
//	 getUserProfileResponse.getRawResponse().getStatus());
//	
//	 }
//	
//	 public void testGetUsers() throws Exception {
//	 getActivity().setGetUsersRequest(new GetUsersRequest());
//	 clickAndWait();
//	 GetUsersResponse getUsersResponse = getActivity()
//	 .getGetUsersResponse();
//	 assertNotNull("user list is not null", getUsersResponse.getUsers());
//	 assertTrue("user list has size > 0 ", getUsersResponse.getUsers().size()
//	 > 0 );
//	 assertEquals("response is OK (200)", 200,
//	 getUsersResponse.getRawResponse().getStatus());
//	 }
//	
//	 public void testGetUsersFail() throws Exception {
//	 setBadTokenAndId();
//	 getActivity().setGetUsersRequest(new GetUsersRequest());
//	 clickAndWait();
//	 GetUsersResponse getUsersResponse = getActivity()
//	 .getGetUsersResponse();
//	 assertNotNull("user list is not null", getUsersResponse.getUsers());
//	 assertTrue("user list is empty", getUsersResponse.getUsers().size() ==
//	 0);
//	 assertEquals("response is Unauthorized (401)", 401,
//	 getUsersResponse.getRawResponse().getStatus());
//	 }
//	
//	 public void testUpdateUser() throws Exception {
//	 getActivity().setUpdateUserRequest(new UpdateUserRequest("new_user_name",
//	 "new_email_address@example.com", "foobar", "foobar"));
//	 clickAndWait();
//	 UpdateUserResponse updateUserResponse = getActivity()
//	 .getUpdateUserResponse();
//	 assertEquals("response is  Accepted (202)", 202,
//	 updateUserResponse.getRawResponse().getStatus());
//	 }
//	
//	 public void testUpdateUserFail() throws Exception {
//	 getActivity().setUpdateUserRequest(new UpdateUserRequest("new_user_name",
//	 "new_email_address@example.com", "foobar", "fxxbar"));
//	 clickAndWait();
//	 UpdateUserResponse updateUserResponse = getActivity()
//	 .getUpdateUserResponse();
//	 assertEquals("response is  Not Acceptable (406)", 406,
//	 updateUserResponse.getRawResponse().getStatus());
//	 assertTrue(
//	 "errors include password must match",
//	 getActivity().getUpdateUserResponse().getErrors()
//	 .contains("Password confirmation doesn't match Password"));
//	
//	 }
//	
//	 public void testGetFollowers() throws Exception {
//	 getActivity().setGetFollowersRequest(new GetFollowersRequest("1"));
//	 clickAndWait();
//	 GetFollowersResponse getFollowersResponse =
//	 getActivity().getGetFollowersResponse();
//	 assertTrue("followers list > 0 ",
//	 getFollowersResponse.getFollowers().size() > 0);
//	 assertEquals("response is OK (200)", 200,
//	 getFollowersResponse.getRawResponse().getStatus());
//	 }
//	
//	 public void testGetFollowersFail() throws Exception {
//	 setBadTokenAndId();
//	 getActivity().setGetFollowersRequest(new GetFollowersRequest("1"));
//	 clickAndWait();
//	 GetFollowersResponse getFollowersResponse =
//	 getActivity().getGetFollowersResponse();
//	 assertTrue("followers == 0 ", getFollowersResponse.getFollowers().size()
//	 == 0);
//	 assertEquals("response is OK (401)", 401,
//	 getFollowersResponse.getRawResponse().getStatus());
//	 }
//	
//	 public void testGetFollowedUsers() throws Exception {
//	 getActivity().setGetFollowedUsersRequest(new
//	 GetFollowedUsersRequest("1"));
//	 clickAndWait();
//	 GetFollowedUsersResponse getFollowedUsersResponse =
//	 getActivity().getGetFollowedUsersResponse();
//	 assertTrue("followedUsers list > 0 ",
//	 getFollowedUsersResponse.getFollowedUsers().size() > 0);
//	 assertEquals("response is OK (200)", 200,
//	 getFollowedUsersResponse.getRawResponse().getStatus());
//	 }
//	
//	 public void testGetFollowedUsersFail() throws Exception {
//	 setBadTokenAndId();
//	 getActivity().setGetFollowedUsersRequest(new
//	 GetFollowedUsersRequest("1"));
//	 clickAndWait();
//	 GetFollowedUsersResponse getFollowedUsersResponse =
//	 getActivity().getGetFollowedUsersResponse();
//	 assertTrue("followedUsers == 0 ",
//	 getFollowedUsersResponse.getFollowedUsers().size() == 0);
//	 assertEquals("response is OK (401)", 401,
//	 getFollowedUsersResponse.getRawResponse().getStatus());
//	 }
//
//	public void testGetMicroposts() throws Exception {
//		getActivity().setGetMicropostsRequest(new GetMicropostsRequest("1"));
//		clickAndWait();
//		GetMicropostsResponse getMicropostsResponse = getActivity()
//				.getGetMicropostsResponse();
//		assertTrue("microposts list > 0 ", getMicropostsResponse
//				.getMicroposts().size() > 0);
//		assertEquals("microposts list size == total number of microposts ",
//				getMicropostsResponse.getMicroposts().size(),
//				getMicropostsResponse.getTotal_user_microposts_count().intValue());
//		assertEquals("response is OK (200)", 200, getMicropostsResponse
//				.getRawResponse().getStatus());
//	}
//
//	public void testGetMicropostsFail() throws Exception {
//		setBadTokenAndId();
//		getActivity().setGetMicropostsRequest(new GetMicropostsRequest("1"));
//		clickAndWait();
//		GetMicropostsResponse getMicropostsResponse = getActivity()
//				.getGetMicropostsResponse();
//		assertTrue("microposts == 0 ", getMicropostsResponse.getMicroposts()
//				.size() == 0);
//		assertEquals("response is OK (401)", 401, getMicropostsResponse
//				.getRawResponse().getStatus());
//	}      
//	   
//	public void testCreateMicropost() throws Exception {
//		getActivity().setCreateMicropostRequest(new CreateMicropostRequest("content from android"));
//		clickAndWait();
//		CreateMicropostResponse createMicropostResponse = getActivity()
//				.getCreateMicropostResponse();
//		assertEquals("returned micropost content is correct", "content from android", createMicropostResponse.getMicropost().getContent());
//		assertEquals("response is created (201)", 201, createMicropostResponse
//				.getRawResponse().getStatus());
//		getActivity().setGetMicropostsRequest(new GetMicropostsRequest(createMicropostResponse.getMicropost().getUser_id().toString()));
//		clickAndWait();
//		GetMicropostsResponse getMicropostsResponse = getActivity()
//				.getGetMicropostsResponse();
//		assertEquals(getMicropostsResponse.getMicroposts().get(0).getContent(),"content from android" );
//		
//		
//	}
//	
//	public void testCreateMicropostFail() throws Exception {
//		setBadTokenAndId();
//		getActivity().setCreateMicropostRequest(new CreateMicropostRequest("content from android"));
//		clickAndWait();
//		CreateMicropostResponse createMicropostResponse = getActivity()
//				.getCreateMicropostResponse();
//		assertNull("returned micropost is null", createMicropostResponse.getMicropost());
//		assertEquals("response is created (401)", 401, createMicropostResponse
//				.getRawResponse().getStatus());
//	}
//
//	public void testDeleteMicropost() throws Exception {
//		getActivity().setCreateMicropostRequest(new CreateMicropostRequest("content from android"));
//		clickAndWait();
//		getActivity().setCreateMicropostRequest(new CreateMicropostRequest("another content from android"));
//		clickAndWait();
//		CreateMicropostResponse createMicropostResponse = getActivity()
//				.getCreateMicropostResponse();
//		assertEquals("returned micropost content is correct", "another content from android", createMicropostResponse.getMicropost().getContent());
//		assertEquals("response is created (201)", 201, createMicropostResponse
//				.getRawResponse().getStatus());
//		getActivity().setGetMicropostsRequest(new GetMicropostsRequest(createMicropostResponse.getMicropost().getUser_id().toString()));
//		clickAndWait();
//		GetMicropostsResponse getMicropostsResponse = getActivity()
//				.getGetMicropostsResponse();
//		assertEquals(getMicropostsResponse.getMicroposts().get(0).getContent(),"another content from android" );
//		getActivity().setDeleteMicropostRequest(new DeleteMicropostRequest(createMicropostResponse.getMicropost().getId().toString()));
//		clickAndWait();
//		DeleteMicropostResponse deleteMicropostResponse = getActivity().getDeleteMicropostResponse();
//		assertEquals("response is no_content (204)", 204, deleteMicropostResponse
//				.getRawResponse().getStatus());
//		getActivity().setGetMicropostsRequest(new GetMicropostsRequest(createMicropostResponse.getMicropost().getUser_id().toString()));
//		clickAndWait();
//		getMicropostsResponse = getActivity()
//				.getGetMicropostsResponse();
//		assertEquals("content should match the correct value",getMicropostsResponse.getMicroposts().get(0).getContent(),"content from android" );
//		
//	}
//	
//	public void testDeleteMicropostFail() throws Exception {
//		setBadTokenAndId();
//		getActivity().setDeleteMicropostRequest(new DeleteMicropostRequest("1"));
//		clickAndWait();
//		DeleteMicropostResponse deleteMicropostResponse = getActivity()
//				.getDeleteMicropostResponse();
//		assertEquals("response is unauthorized  (401)", 401, deleteMicropostResponse
//				.getRawResponse().getStatus());
//
//	}
	
	public void testUnfollow() throws Exception {
	getActivity().setUnfollowRequest(new UnfollowRequest("1"));
	clickAndWait();
	UnfollowResponse unfollowResponse = getActivity()
			.getUnfollowResponse();
	assertEquals("response is no contetn  (204)", 204, unfollowResponse
			.getRawResponse().getStatus());

}

	

}
