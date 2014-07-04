package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.CreateDeviceRequest;
import com.gmail.npnster.first_project.api_params.CreateDeviceResponse;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.CreateMicropostRequest;
import com.gmail.npnster.first_project.api_params.CreateMicropostResponse;
import com.gmail.npnster.first_project.api_params.DeleteMicropostRequest;
import com.gmail.npnster.first_project.api_params.DeleteMicropostResponse;
import com.gmail.npnster.first_project.api_params.FollowRequest;
import com.gmail.npnster.first_project.api_params.FollowResponse;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersResponse;
import com.gmail.npnster.first_project.api_params.GetFollowersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowersResponse;
import com.gmail.npnster.first_project.api_params.GetMicropostsRequest;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.gmail.npnster.first_project.api_params.LeaveRequest;
import com.gmail.npnster.first_project.api_params.LeaveResponse;
import com.gmail.npnster.first_project.api_params.PatchLocationRequest;
import com.gmail.npnster.first_project.api_params.PatchLocationResponse;
import com.gmail.npnster.first_project.api_params.PostLocationRequest;
import com.gmail.npnster.first_project.api_params.PostLocationResponse;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.SigninRequest;
import com.gmail.npnster.first_project.api_params.SigninResponse;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignoutResponse;
import com.gmail.npnster.first_project.api_params.SignupRequest;
import com.gmail.npnster.first_project.api_params.SignupResponse;
import com.gmail.npnster.first_project.api_params.UnfollowRequest;
import com.gmail.npnster.first_project.api_params.UnfollowResponse;
import com.gmail.npnster.first_project.api_params.UpdateUserRequest;
import com.gmail.npnster.first_project.api_params.UpdateUserResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import butterknife.OnClick;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.os.Build;

public class ApiExActivity extends Activity {

	private Button mButton;
	private TextView mTextView;

	public enum ApiCall {
		LEAVE, SIGNUP, GET_USER_PROFILE, GET_USERS, UPDATE_USER,
		GET_FOLLOWERS, GET_FOLLOWED_USERS, GET_MICROPOSTS, CREATE_MICROPOST,
		DELETE_MICROPOST, FOLLOW, UNFOLLOW, GRANT_FOLLOWER_PERMISSION, SIGNIN,
		REVOKE_FOLLOWER_PERMISSION, SIGNOUT, CREATE_DEVICE, POST_LOCATION, PATCH_LOCATION
	}

	private ApiCall apiCall;

	// leave ############
	private LeaveRequest leaveRequest;
	private LeaveResponse leaveResponse;

	public LeaveResponse getLeaveResponse() {
		return leaveResponse;
	}

	public void setLeaveRequest(LeaveRequest leaveRequest) {
		this.leaveRequest = leaveRequest;
		apiCall = ApiCall.LEAVE;
	}

	@Subscribe
	public void onLeaveResponse(LeaveResponse event) {
		leaveResponse = event;
		setCallComplete();
	}

	// ***************************

	// signup ################
	private SignupRequest signupRequest;
	private SignupResponse signupResponse;

	public SignupResponse getSignupResponse() {
		return signupResponse;
	}

	public void setSignupRequest(SignupRequest signupRequest) {
		this.signupRequest = signupRequest;
		apiCall = ApiCall.SIGNUP;
	}

	@Subscribe
	public void onSignupResponse(SignupResponse event) {
		signupResponse = event;
		setCallComplete();
	}

	// **********************

	// signin ################
	private SigninRequest signinRequest;
	private SigninResponse signinResponse;

	public SigninResponse getSigninResponse() {
		return signinResponse;
	}

	public void setSigninRequest(SigninRequest signinRequest) {
		this.signinRequest = signinRequest;
		apiCall = ApiCall.SIGNIN;
	}

	@Subscribe
	public void onSigninResponse(SigninResponse event) {
		signinResponse = event;
		setCallComplete();
	}

	// **********************

	// signout ################
	private SignoutRequest signoutRequest;
	private SignoutResponse signoutResponse;

	public SignoutResponse getSignoutResponse() {
		return signoutResponse;
	}

	public void setSignoutRequest(SignoutRequest signoutRequest) {
		this.signoutRequest = signoutRequest;
		apiCall = ApiCall.SIGNOUT;
	}

	@Subscribe
	public void onSignoutResponse(SignoutResponse event) {
		signoutResponse = event;
		setCallComplete();
	}

	// **********************
	
	// get user profile ##################
	private GetUserProfileRequest getUserProfileRequest;
	private GetUserProfileResponse getUserProfileResponse;

	public GetUserProfileResponse getGetUserProfileResponse() {
		return getUserProfileResponse;
	}

	public void setGetUserProfileRequest(
			GetUserProfileRequest getUserProfileRequest) {
		this.getUserProfileRequest = getUserProfileRequest;
		apiCall = ApiCall.GET_USER_PROFILE;
	}

	@Subscribe
	public void onGetUserProfileResponse(GetUserProfileResponse event) {
		getUserProfileResponse = event;
		setCallComplete();
	}

	// ***********************

	// get users ##################
	private GetUsersRequest getUsersRequest;
	private GetUsersResponse getUsersResponse;

	public GetUsersResponse getGetUsersResponse() {
		return getUsersResponse;
	}

	public void setGetUsersRequest(GetUsersRequest getUsersRequest) {
		this.getUsersRequest = getUsersRequest;
		apiCall = ApiCall.GET_USERS;
	}

	@Subscribe
	public void onGetUsersResponse(GetUsersResponse event) {
		getUsersResponse = event;
		setCallComplete();
	}

	// ***********************

	// get followers ##################
	private GetFollowersRequest getFollowersRequest;
	private GetFollowersResponse getFollowersResponse;

	public GetFollowersResponse getGetFollowersResponse() {
		return getFollowersResponse;
	}

	public void setGetFollowersRequest(GetFollowersRequest getFollowersRequest) {
		this.getFollowersRequest = getFollowersRequest;
		apiCall = ApiCall.GET_FOLLOWERS;
	}

	@Subscribe
	public void onGetFollowersResponse(GetFollowersResponse event) {
		getFollowersResponse = event;
		setCallComplete();
	}

	// ***********************

	// get followed users ##################
	private GetFollowedUsersRequest getFollowedUsersRequest;
	private GetFollowedUsersResponse getFollowedUsersResponse;

	public GetFollowedUsersResponse getGetFollowedUsersResponse() {
		return getFollowedUsersResponse;
	}

	public void setGetFollowedUsersRequest(
			GetFollowedUsersRequest getFollowedUsersRequest) {
		this.getFollowedUsersRequest = getFollowedUsersRequest;
		apiCall = ApiCall.GET_FOLLOWED_USERS;
	}

	@Subscribe
	public void onGetFollowedUsersResponse(GetFollowedUsersResponse event) {
		getFollowedUsersResponse = event;
		setCallComplete();
	}

	// ***********************

	// get microposts ##################
	private GetMicropostsRequest getMicropostsRequest;
	private GetMicropostsResponse getMicropostsResponse;

	public GetMicropostsResponse getGetMicropostsResponse() {
		return getMicropostsResponse;
	}

	public void setGetMicropostsRequest(
			GetMicropostsRequest getMicropostsRequest) {
		this.getMicropostsRequest = getMicropostsRequest;
		apiCall = ApiCall.GET_MICROPOSTS;
	}

	@Subscribe
	public void onGetMicropostsResponse(GetMicropostsResponse event) {
		getMicropostsResponse = event;
		setCallComplete();
	}

	// create micropost ##################
	private CreateMicropostRequest createMicropostRequest;
	private CreateMicropostResponse createMicropostResponse;

	public CreateMicropostResponse getCreateMicropostResponse() {
		return createMicropostResponse;
	}

	public void setCreateMicropostRequest(
			CreateMicropostRequest createMicropostRequest) {
		this.createMicropostRequest = createMicropostRequest;
		apiCall = ApiCall.CREATE_MICROPOST;
	}

	@Subscribe
	public void onCreateMicropostResponse(CreateMicropostResponse event) {
		createMicropostResponse = event;
		setCallComplete();
	}

	// delete micropost ##################
	private DeleteMicropostRequest deleteMicropostRequest;
	private DeleteMicropostResponse deleteMicropostResponse;

	public DeleteMicropostResponse getDeleteMicropostResponse() {
		return deleteMicropostResponse;
	}

	public void setDeleteMicropostRequest(
			DeleteMicropostRequest deleteMicropostRequest) {
		this.deleteMicropostRequest = deleteMicropostRequest;
		apiCall = ApiCall.DELETE_MICROPOST;
	}

	@Subscribe
	public void onDeleteMicropostResponse(DeleteMicropostResponse event) {
		deleteMicropostResponse = event;
		setCallComplete();
	}

	//***********************
	
	// follow user ##################
	private FollowRequest followRequest;
	private FollowResponse followResponse;

	public FollowResponse getFollowResponse() {
		return followResponse;
	}

	public void setFollowRequest(
			FollowRequest followRequest) {
		this.followRequest = followRequest;
		apiCall = ApiCall.FOLLOW;
	}

	@Subscribe
	public void onFollowResponse(FollowResponse event) {
		followResponse = event;
		setCallComplete();
	}

	// ***********************

	// unfollow user ##################
	private UnfollowRequest unfollowRequest;
	private UnfollowResponse unfollowResponse;

	public UnfollowResponse getUnfollowResponse() {
		return unfollowResponse;
	}

	public void setUnfollowRequest(
			UnfollowRequest unfollowRequest) {
		this.unfollowRequest = unfollowRequest;
		apiCall = ApiCall.UNFOLLOW;
	}

	@Subscribe
	public void onUnfollowResponse(UnfollowResponse event) {
		unfollowResponse = event;
		setCallComplete();
	}

	// ***********************

	// grant follower permission ##################
	private GrantFollowerPermissionRequest grantFollowerPermissionRequest;
	private GrantFollowerPermissionResponse grantFollowerPermissionResponse;

	public GrantFollowerPermissionResponse getGrantFollowerPermissionResponse() {
		return grantFollowerPermissionResponse;
	}

	public void setGrantFollowerPermissionRequest(
			GrantFollowerPermissionRequest grantFollowerPermissionRequest) {
		this.grantFollowerPermissionRequest = grantFollowerPermissionRequest;
		apiCall = ApiCall.GRANT_FOLLOWER_PERMISSION;
	}

	@Subscribe
	public void onGrantFollowerPermissionResponse(GrantFollowerPermissionResponse event) {
		grantFollowerPermissionResponse = event;
		setCallComplete();
	}
	
	// ***********************

	// revoke follower permission ##################
	private RevokeFollowerPermissionRequest revokeFollowerPermissionRequest;
	private RevokeFollowerPermissionResponse revokeFollowerPermissionResponse;

	public RevokeFollowerPermissionResponse getRevokeFollowerPermissionResponse() {
		return revokeFollowerPermissionResponse;
	}

	public void setRevokeFollowerPermissionRequest(
			RevokeFollowerPermissionRequest revokeFollowerPermissionRequest) {
		this.revokeFollowerPermissionRequest = revokeFollowerPermissionRequest;
		apiCall = ApiCall.REVOKE_FOLLOWER_PERMISSION;
	}

	@Subscribe
	public void onRevokeFollowerPermissionResponse(RevokeFollowerPermissionResponse event) {
		revokeFollowerPermissionResponse = event;
		setCallComplete();
	}
	
	// ***********************


	// update user ##################
	private UpdateUserRequest updateUserRequest;
	private UpdateUserResponse updateUserResponse;

	public UpdateUserResponse getUpdateUserResponse() {
		return updateUserResponse;
	}

	public void setUpdateUserRequest(UpdateUserRequest updateUserRequest) {
		this.updateUserRequest = updateUserRequest;
		apiCall = ApiCall.UPDATE_USER;
	}

	@Subscribe
	public void onUpdateUserResponse(UpdateUserResponse event) {
		updateUserResponse = event;
		setCallComplete();
	}

	// ***********************

	// create device ##################
	private CreateDeviceRequest createDeviceRequest;
	private CreateDeviceResponse createDeviceResponse;

	public CreateDeviceResponse getCreateDeviceResponse() {
		return createDeviceResponse;
	}

	public void setCreateDeviceRequest(CreateDeviceRequest event) {
		this.createDeviceRequest = event;
		apiCall = ApiCall.CREATE_DEVICE;
	}

	@Subscribe
	public void onCreateDeviceResponse(CreateDeviceResponse event) {
		createDeviceResponse = event;
		setCallComplete();
	}

	// ***********************
	
	// post location ##################
	private PostLocationRequest postLocationRequest;
	private PostLocationResponse postLocationResponse;
	
	public PostLocationResponse getPostLocationResponse() {
		return postLocationResponse;
	}
	
	public void setPostLocationRequest(PostLocationRequest event) {
		this.postLocationRequest = event;
		apiCall = ApiCall.POST_LOCATION;
	}
	
	@Subscribe
	public void onPostLocationResponse(PostLocationResponse event) {
		postLocationResponse = event;
		setCallComplete();
	}
	
	// ***********************
	
	// patch location ##################
	private PatchLocationRequest patchLocationRequest;
	private PatchLocationResponse patchLocationResponse;

	public PatchLocationResponse getPatchLocationResponse() {
		return patchLocationResponse;
	}

	public void setPatchLocationRequest(PatchLocationRequest event) {
		this.patchLocationRequest = event;
		apiCall = ApiCall.PATCH_LOCATION;
	}

	@Subscribe
	public void onPatchLocationResponse(PatchLocationResponse event) {
		patchLocationResponse = event;
		setCallComplete();
	}

	// ***********************
	
	
	private Bus mBus;

	protected Bus getBus() {
		if (mBus == null) {
			mBus = BusProvider.getInstance();
		}
		return mBus;
	}

	public void setBus(Bus bus) {
		mBus = bus;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_api_ex);
		mButton = (Button) findViewById(R.id.button);
		mTextView = (TextView) findViewById(R.id.textView);
		getBus();

		mButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				clearCallComplete();
				switch (apiCall) {
				case LEAVE:
					mBus.post(leaveRequest);
					break;
				case SIGNUP:
					mBus.post(signupRequest);
					break;
				case SIGNIN:
					mBus.post(signinRequest);
					break;
				case GET_USER_PROFILE:
					mBus.post(getUserProfileRequest);
					break;
				case GET_USERS:
					mBus.post(getUsersRequest);
					break;
				case UPDATE_USER:
					mBus.post(updateUserRequest);
					break;
				case GET_FOLLOWERS:
					mBus.post(getFollowersRequest);
					break;
				case GET_FOLLOWED_USERS:
					mBus.post(getFollowedUsersRequest);
					break;
				case GET_MICROPOSTS:
					mBus.post(getMicropostsRequest);
					break;
				case CREATE_MICROPOST:
					mBus.post(createMicropostRequest);
					break;
				case DELETE_MICROPOST:
					mBus.post(deleteMicropostRequest);
					break;
				case FOLLOW:
					mBus.post(followRequest);
					break;
				case UNFOLLOW:
					mBus.post(unfollowRequest);
					break;
				case GRANT_FOLLOWER_PERMISSION:
					mBus.post(grantFollowerPermissionRequest);
					break;
				case REVOKE_FOLLOWER_PERMISSION:
					mBus.post(revokeFollowerPermissionRequest);
					break;
				case SIGNOUT:
					mBus.post(signoutRequest);
					break;
				case CREATE_DEVICE:
					mBus.post(createDeviceRequest);
					break;
				case POST_LOCATION:
					mBus.post(postLocationRequest);
					break;
				case PATCH_LOCATION:
					mBus.post(patchLocationRequest);
					break;

				}

			}
		});

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getBus().register(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getBus().unregister(this);
	}

	public void setApiCall(ApiCall apiCall) {
		this.apiCall = apiCall;
	}

	public void setCallComplete() {
		mTextView.setText("DONE!");
	}

	public void clearCallComplete() {
		mTextView.setText("running!");
	}

	public void makeRequest(ApiCall apiCall) {
		this.apiCall = apiCall;
		mButton.performClick();
	}

}
