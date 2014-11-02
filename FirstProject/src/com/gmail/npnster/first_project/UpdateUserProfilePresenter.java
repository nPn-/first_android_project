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
import com.gmail.npnster.first_project.api_params.UpdateUserRequest;
import com.gmail.npnster.first_project.api_params.UpdateUserResponse;
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

public class UpdateUserProfilePresenter {

	private UpdateUserProfileView mView;
	private String mCurrentUser;
	private String mCurrentName;
	private String mCurrentEmailId;
	@Inject PersistData mPersistData;
	@Inject	Bus mBus;

	public UpdateUserProfilePresenter() {
		Injector.getInstance().inject(this);
		mCurrentUser = mPersistData.getUserId();

	}

	public void onOptionItemSelected(MenuItem item) {
		System.out.println("item selected");

	}

	public void setView(UpdateUserProfileView view) {
		mView = view;

	}

	public UpdateUserProfileView getView() {
		return mView;
	}

	public Activity getContext() {
		return getView().getFragment().getActivity();
	}

	public void onSettingsOptionSelected() {
		System.out.println("item settings selected");

	}

	public void refreshView() {
		mBus.post(new GetUserProfileRequest(mCurrentUser));
		getContext().setProgressBarIndeterminateVisibility(true);
	}



	@Subscribe
	public void onGetUserProfileResponse(GetUserProfileResponse event) {
		System.out.println(String.format("got the user profile for user = %s",
				event.getName()));
		getContext().setProgressBarIndeterminateVisibility(false);
		mView.setUserIcon("http://www.gravatar.com/avatar/"
				+ event.getGravatar_id());
		mCurrentName = event.getName();
		mCurrentEmailId = event.getEmail();
		mView.setUserName(mCurrentName);
		mView.setUserEmailId(mCurrentEmailId);
		mView.userProfilePasswordTextView.setText(null);
		mView.userProfileConfirmationPasswordTextView.setText(null);
		mView.userProfileCurrentPasswordTextView.setText(null);

	}
	
	
	public void onCurrentPasswordEntered() {
		String newUserName = mView.getNewUserName();
		String newEmailId = mView.getNewEmailId();
		String newPassword = mView.getNewPassword();
		String newConfirmationPassword = mView.getNewConfirmationPassword();
		String currentPassword = mView.getCurrentPassword();
		System.out.println("current password entered");
//		if (currentPassword.length() > 5 && newPassword.length() > 5 && newPassword.equals(newConfirmationPassword)) {
//			System.out.println(String.format("process update for user = %s", mCurrentUser));
//		}
		String pw = newPassword == null ? "" : newPassword;
		String confirmPw = newConfirmationPassword == null ? "" : newConfirmationPassword;
		mBus.post(new UpdateUserRequest(newUserName, newEmailId,pw,confirmPw, currentPassword));
//		refreshView();
		
	}
	
	@Subscribe public void onUserUpdateRequestCompleted(UpdateUserResponse event) {
		refreshView();
		
		mView.resetUpdateProfileForm();
	}





}
