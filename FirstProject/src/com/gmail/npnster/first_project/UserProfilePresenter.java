package com.gmail.npnster.first_project;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import com.gmail.npnster.first_project.api_params.CreateMicropostRequest;
import com.gmail.npnster.first_project.api_params.DeleteMicropostRequest;
import com.gmail.npnster.first_project.api_params.DeleteMicropostResponse;
import com.gmail.npnster.first_project.api_params.FollowRequest;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersResponse;
import com.gmail.npnster.first_project.api_params.GetMicropostsRequest;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse.Micropost;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignoutResponse;
import com.gmail.npnster.first_project.api_params.UnfollowRequest;
import com.gmail.npnster.first_project.api_params.UserListResponse.User;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class UserProfilePresenter {

	private UserProfileView mView;
	private ArrayList<Micropost> mUserProfileMicroposts = new ArrayList<Micropost>();
	private ArrayAdapter<Micropost> micropostsAdapter;
	private String mCurrentUser;
	@Inject PersistData mPersistData;
	@Inject	Bus mBus;
	private Micropost mLongClickedMicropost;

	public UserProfilePresenter() {
		Injector.getInstance().inject(this);
		mCurrentUser = mPersistData.getUserId();

	}

	public void onOptionItemSelected(MenuItem item) {
		System.out.println("item selected");

	}

	public void setView(UserProfileView view) {
		mView = view;

	}

	public UserProfileView getView() {
		return mView;
	}

	public Context getContext() {
		return getView().getFragment().getActivity();
	}

	public void onSettingsOptionSelected() {
		System.out.println("item settings selected");

	}

	public void refreshView() {
		micropostsAdapter = new UserProfileMicropostAdapter(getContext(),
				mUserProfileMicroposts);
		mView.setUserProfileMicropostsAdapter(micropostsAdapter);
		mBus.post(new GetUserProfileRequest(mCurrentUser));
		mBus.post(new GetMicropostsRequest(mCurrentUser));
	}

	@Subscribe
	public void onGetMicropostsResponse(GetMicropostsResponse event) {
		mUserProfileMicroposts.clear();
		mUserProfileMicroposts.addAll(event.getMicroposts());
		System.out.println(String.format("micorpost count = %s", event
				.getMicroposts().size()));
		micropostsAdapter.notifyDataSetChanged();
		System.out.println(String.format("microposts adapter size = %d ",
				micropostsAdapter.getCount()));

	}

	@Subscribe
	public void onGetUserProfileResponse(GetUserProfileResponse event) {
		System.out.println(String.format("got the user profile for user = %s",
				event.getName()));
		mView.setUserIcon("http://www.gravatar.com/avatar/"
				+ event.getGravatar_id());
		mView.setUserName(event.getName());
		mView.setUserEmailId(event.getEmail());
		mView.setUserPhoneNumber(event.getPhoneNumber());

	}


	public void onMicropostLongClicked(int position) {
		mLongClickedMicropost = mUserProfileMicroposts.get(position);
		mView.startMicropostActionMode();
		
	}

	public void onDeleteMicropostMenuItemSelected() {
		mBus.post(new DeleteMicropostRequest(String.valueOf(mLongClickedMicropost.getId())));
		
	}
	
	@Subscribe 
	public void onDeleteMicropostResponse(DeleteMicropostResponse event) {
		if (event.isSuccessful()) {
			mBus.post(new GetMicropostsRequest(mCurrentUser));
		}
	}

	public void onEditOptionSelected() {
		Intent intent = new Intent(getContext(), UpdateUserProfileActivity.class);
		getContext().startActivity(intent);
		
	}




}
