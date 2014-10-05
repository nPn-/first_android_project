package com.gmail.npnster.first_project;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import com.gmail.npnster.first_project.api_params.CreateMicropostRequest;
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

public class UserDetailPresenter {

	private UserDetailView mView;
	private ArrayList<Micropost> mUserDetailMicroposts = new ArrayList<Micropost>();
	private ArrayList<UserDetailOption> mUserDetailOptions = new ArrayList<UserDetailOption>();
	private ArrayAdapter<UserDetailOption> optionsAdapter;
	private ArrayAdapter<Micropost> micropostsAdapter;
	private String mUserId;
	private String mCurrentUser;
	@Inject
	PersistData mPersistData;
	@Inject
	Bus mBus;

	public UserDetailPresenter() {
		Injector.getInstance().inject(this);
		mCurrentUser = mPersistData.getUserId();

	}

	public void onOptionItemSelected(MenuItem item) {
		System.out.println("item selected");

	}

	public void setView(UserDetailView view) {
		mView = view;

	}

	public UserDetailView getView() {
		return mView;
	}

	public Context getContext() {
		return getView().getFragment().getActivity();
	}

	public void onSettingsOptionSelected() {
		System.out.println("item settings selected");

	}

	public void refreshView() {
		mUserDetailOptions.clear();
		optionsAdapter = new UserDetailOptionsAdapter(getContext(),
				mUserDetailOptions, mView);
		micropostsAdapter = new UserDetailMicropostAdapter(getContext(),
				mUserDetailMicroposts);
		mView.setUserDetailOptionsAdapter(optionsAdapter);
		mView.setUserDetailMicropostsAdapter(micropostsAdapter);
		mBus.post(new GetUserProfileRequest(mUserId));
		mBus.post(new GetMicropostsRequest(mUserId));
	}

	@Subscribe
	public void onGetMicropostsResponse(GetMicropostsResponse event) {
		mUserDetailMicroposts.clear();
		mUserDetailMicroposts.addAll(event.getMicroposts());
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
		mView.setFollowUser(event.areFollowing());
		mView.setFollowingNotice(event.isFollowedBy());
		mUserDetailOptions.clear();
		mUserDetailOptions.addAll(new UserDetailOptions().toArrayList());
		System.out.println(String.format("user details options size = %d ",
				mUserDetailOptions.size()));
		for (String permission : event
				.getPermissionsGrantedByCurrentUserToUser()) {
			for (UserDetailOption userDetailOption : mUserDetailOptions) {
				System.out.println(String.format(
						"checking permission %s == %s ? ",
						userDetailOption.getName(), permission));
				System.out.println(String.format("permission granted =  = %s",
						permission));
				if (userDetailOption.getName().equals(permission)) {
					userDetailOption.setEnabled(true);
					System.out.println(String.format(
							"set permission true for permission name = %s",
							userDetailOption.getName()));
				}
			}
		}
		optionsAdapter.notifyDataSetChanged();
		System.out.println(String.format("adapter size = %d ",
				optionsAdapter.getCount()));

	}

	public void onUserPermissionChanged(int position, boolean checked) {
		String permission = mUserDetailOptions.get(position).getName();
		if (checked) {
			mBus.post(new GrantFollowerPermissionRequest(mCurrentUser, mUserId,
					permission));
		} else {
			mBus.post(new RevokeFollowerPermissionRequest(mCurrentUser,
					mUserId, permission));
		}
	}

	public void setUserId(String userId) {
		mUserId = userId;

	}

	public void onFollowSwitchChanged(boolean isChecked) {
		if (isChecked) {
			mBus.post(new FollowRequest(mUserId));
		} else {
			mBus.post(new UnfollowRequest(mUserId));
		}

	}

	public void onMicropostssOptionSelected() {

		Intent intent = new Intent(getContext(), MicropostListActivity.class);
		intent.putExtra("user_id", mUserId);
		getContext().startActivity(intent);

	}

}
