package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.gmail.npnster.first_project.api_params.LeaveRequest;
import com.gmail.npnster.first_project.api_params.LeaveResponse;
import com.gmail.npnster.first_project.api_params.SignupRequest;
import com.gmail.npnster.first_project.api_params.SignupResponse;
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
		LEAVE, SIGNUP, GET_USER_PROFILE, GET_USERS
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
	//***************************

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
	//**********************

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
	//***********************

	// get users  ##################
	private GetUsersRequest getUsersRequest;
	private GetUsersResponse getUsersResponse;

	public GetUsersResponse getGetUsersResponse() {
		return getUsersResponse;
	}

	public void setGetUsersRequest(
			GetUsersRequest getUsersRequest) {
		this.getUsersRequest = getUsersRequest;
		apiCall = ApiCall.GET_USERS;
	}

	@Subscribe
	public void onGetUsersResponse(GetUsersResponse event) {
		getUsersResponse = event;
		setCallComplete();
	}
	//***********************

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
				case GET_USER_PROFILE:
					mBus.post(getUserProfileRequest);
					break;
				case GET_USERS:
					mBus.post(getUsersRequest);

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
