package com.gmail.npnster.first_project;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import com.gmail.npnster.first_project.api_params.CreateMicropostRequest;
import com.gmail.npnster.first_project.api_params.FollowRequest;
import com.gmail.npnster.first_project.api_params.FollowResponse;
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
import com.gmail.npnster.first_project.api_params.GrantFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionRequest;
import com.gmail.npnster.first_project.api_params.RevokeFollowerPermissionResponse;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignoutResponse;
import com.gmail.npnster.first_project.api_params.UnfollowRequest;
import com.gmail.npnster.first_project.api_params.UnfollowResponse;
import com.gmail.npnster.first_project.api_params.UserListResponse.User;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
	private String mUserName;
	private String mCurrentUser;
	private String mUserPhoneNumber;
	private boolean mProfileRequestActive = false;
	private boolean mMicropostRequestActive = false;
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

	public Activity getContext() {
		return getView().getFragment().getActivity();
	}

	public void onSettingsOptionSelected() {
		System.out.println("item settings selected");

	}

	public void refreshView() {
		mUserDetailOptions.clear();
		optionsAdapter = new UserDetailOptionsAdapter(getContext(), mUserDetailOptions, mView);
		micropostsAdapter = new UserDetailMicropostAdapter(getContext(), mUserDetailMicroposts);
		mView.setUserDetailOptionsAdapter(optionsAdapter);
		mView.setUserDetailMicropostsAdapter(micropostsAdapter);
		mBus.post(new GetUserProfileRequest(mUserId));
		mProfileRequestActive = true;
		mBus.post(new GetMicropostsRequest(mUserId));
		mMicropostRequestActive = true;
		getContext().setProgressBarIndeterminateVisibility(true);
	}

	@Subscribe
	public void onGetMicropostsResponse(GetMicropostsResponse event) {
		mMicropostRequestActive = false;
		requestComplete();
		mUserDetailMicroposts.clear();
		mUserDetailMicroposts.addAll(event.getMicroposts());
		System.out.println(String.format("micorpost count = %s", event.getMicroposts().size()));
		micropostsAdapter.notifyDataSetChanged();
		System.out.println(String.format("microposts adapter size = %d ", micropostsAdapter.getCount()));

	}

	@Subscribe
	public void onGetUserProfileResponse(GetUserProfileResponse event) {
		System.out.println(String.format("got the user profile for user = %s", event.getName()));
		mProfileRequestActive = false;
		requestComplete();
		if (event.isSuccessful()) {
			mView.setUserIcon("http://www.gravatar.com/avatar/" + event.getGravatar_id());
			mUserName = event.getName();
			mView.setUserName(event.getName());
			mView.setUserEmailId(event.getEmail());
			setUserPhoneNumber(event.getPhoneNumber());
			mView.setUserPhoneNumber(getUserPhoneNumber());
			mView.setFollowUser(event.areFollowing());
			mView.setFollowingNotice(event.isFollowedBy());
			mUserDetailOptions.clear();
			mUserDetailOptions.addAll(new UserDetailOptions().toArrayList());
			System.out.println(String.format("user details options size = %d ", mUserDetailOptions.size()));
			for (String permission : event.getPermissionsGrantedByCurrentUserToUser()) {
				for (UserDetailOption userDetailOption : mUserDetailOptions) {
					System.out.println(String.format("checking permission %s == %s ? ", userDetailOption.getName(), permission));
					System.out.println(String.format("permission granted =  = %s", permission));
					if (userDetailOption.getName().equals(permission)) {
						userDetailOption.setEnabled(true);
						System.out
								.println(String.format("set permission true for permission name = %s", userDetailOption.getName()));
					}
				}
			}
			optionsAdapter.notifyDataSetChanged();
			System.out.println(String.format("adapter size = %d ", optionsAdapter.getCount()));
		} else {
			mView.showGetFailedDialog(event.isNetworkError());
		}
	}

	private void requestComplete() {
		if (!mProfileRequestActive && !mMicropostRequestActive) {
			getContext().setProgressBarIndeterminateVisibility(false);
		}

	}

	public void onUserPermissionChanged(int position, boolean checked) {
		String permission = mUserDetailOptions.get(position).getName();
		if (checked) {
			GrantFollowerPermissionRequest event = new GrantFollowerPermissionRequest(mCurrentUser, mUserId, permission);
			event.setFollowerName(mUserName);
			mBus.post(event);
		} else {
			RevokeFollowerPermissionRequest event = new RevokeFollowerPermissionRequest(mCurrentUser, mUserId, permission);
			event.setFollowerName(mUserName);
			mBus.post(event);
		}

	}

	public void setUserId(String userId) {
		mUserId = userId;

	}

	public void onFollowSwitchChanged(boolean isChecked) {
		if (isChecked) {
			mBus.post(new FollowRequest(mUserId, mUserName));
		} else {
			mBus.post(new UnfollowRequest(mUserId, mUserName));
		}

	}

	@Subscribe
	public void onFollowResponse(FollowResponse event) {
		if (!event.isSuccessful()) {
			System.out.println(String.format("follow request failed for user %s", event.getRequestEvent().getUserName()));
			String reason = event.isNetworkError() ? "network" : "server";
			mView.showFollowUnfollowChangeFailedDialog("follow", reason);
			refreshView();
		}
	}

	@Subscribe
	public void onUnFollowResponse(UnfollowResponse event) {
		if (!event.isSuccessful()) {
			System.out.println(String.format("unfollow request failed %s", event.getRequestEvent().getUserName()));
			String reason = event.isNetworkError() ? "network" : "server";
			mView.showFollowUnfollowChangeFailedDialog("unfollow", reason);
			refreshView();
		}
	}

	public void onMicropostssOptionSelected() {

		Intent intent = new Intent(getContext(), MicropostListActivity.class);
		intent.putExtra("user_id", mUserId);
		getContext().startActivity(intent);

	}

	@Subscribe
	public void onRevokePermissionResponse(RevokeFollowerPermissionResponse event) {
		if (!event.isSuccessful()) {
			System.out.println(String.format("permission %s revoked", event.getRequestEvent().getPermission()));
			String reason = event.isNetworkError() ? "network" : "server";
			mView.showPermissionChangeFailedDialog(event.getRequestEvent().getPermission(), "revoke", reason);
			refreshView();

		}
	}

	@Subscribe
	public void onGrantPermissionResponse(GrantFollowerPermissionResponse event) {
		if (!event.isSuccessful()) {
			System.out.println(String.format("permission %s granted", event.getRequestEvent().getPermission()));
			String reason = event.isNetworkError() ? "network" : "server";
			mView.showPermissionChangeFailedDialog(event.getRequestEvent().getPermission(), "grant", reason);
			refreshView();
		}
	}

	public void onMapOptionSelected() {
		mPersistData.saveCenterOnUserId(mUserId);
		Intent intent = new Intent(getContext(), MapActivity.class);
		getContext().startActivity(intent);
	}

	public void contactPersonVia(String via) {
		String phoneNumber = getUserPhoneNumber();
		if (phoneNumber != null && phoneNumber != "") {
			String uriString = String.format("%s:%s", via, phoneNumber);
			System.out.println(String.format("contact person with phone number = %s, via %s", phoneNumber, via));
			Intent contactIntent = new Intent(Intent.ACTION_VIEW);
			contactIntent.setData(Uri.parse(uriString));
			getContext().startActivity(contactIntent);
		}
	}

	public void onMessagePersonOptionSelected() {
		contactPersonVia("sms");
	}
	
	public void onCallPersonOptionSelected() {
		contactPersonVia("tel");
	}

	public String getUserPhoneNumber() {
		return mUserPhoneNumber;
	}

	public void setUserPhoneNumber(String userPhoneNumber) {
		mUserPhoneNumber = userPhoneNumber;
	}

}
