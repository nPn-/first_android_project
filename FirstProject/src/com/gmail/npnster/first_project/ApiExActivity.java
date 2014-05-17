package com.gmail.npnster.first_project;


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
	public enum ApiCall  { LEAVE, SIGNUP }
	private ApiCall apiCall;	
	
	
	private LeaveRequest leaveRequest;
	private LeaveResponse leaveResponse;
	
	public LeaveResponse getLeaveResponse() {
		return leaveResponse;
	}

	public void setLeaveRequest(LeaveRequest leaveRequest) {
		this.leaveRequest = leaveRequest;
	}
	
	@Subscribe public void onLeaveResponse(LeaveResponse event) {
		leaveResponse = event;
		System.out.println("onLeaveResponse");
		setCallComplete();
	}

	
	private SignupRequest signupRequest;
	private SignupResponse signupResponse;
	
	
	public SignupResponse getSignupResponse() {
		return signupResponse;
	}

	public void setSignupRequest(SignupRequest signupRequest) {
		this.signupRequest = signupRequest;
	}

	@Subscribe public void onSignupResponse(SignupResponse event) {
		System.out.println("******* here *******");
		signupResponse = event;
		System.out.println(String.format("respone is good %b", event.isSuccessful()));
		setCallComplete();
	}
	
	
	private Bus mBus;
	
	private Bus getBus() {
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
	

}
