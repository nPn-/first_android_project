package com.gmail.npnster.first_project;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import com.gmail.npnster.first_project.api_params.CreateMicropostRequest;
import com.gmail.npnster.first_project.api_params.CreateMicropostResponse;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersResponse;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignoutResponse;
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

public class UserListPresenter {

	private UserListView mView;
	private ArrayList<User> mUserList = new ArrayList<User>();
	private ArrayAdapter<User> adapter;
	@Inject
	PersistData mPersistData;
	@Inject
	Bus mBus;

	public UserListPresenter() {
		Injector.getInstance().inject(this);

	}

	public void onOptionItemSelected(MenuItem item) {
		System.out.println("item selected");

	}

	public void setView(UserListView view) {
		mView = view;

	}

	public UserListView getView() {
		return mView;
	}

	public Context getContext() {
		return getView().getFragment().getActivity();
	}

	public void onSettingsOptionSelected() {
		System.out.println("item settings selected");

	}

	public void refreshView() {
		mUserList.clear();
		adapter = new UserListAdapter(getContext(), mUserList);
		mView.setUserListAdapter(adapter);
		mBus.post(new GetUsersRequest());
	}

	@Subscribe
	public void onGetUsersResponse(GetUsersResponse event) {

		mUserList.clear();
		for (GetUsersResponse.User user : event.getUsers()) {
			System.out.println(user.getName());
			mUserList.add(user);
		}
		adapter.notifyDataSetChanged();

	}

	public void onUserSelected(int position) {
		Integer selectedUser = mUserList.get(position).getId();
		String selectedUserAsString = String.valueOf(selectedUser);
		System.out.println(String.format("user selected = %s", selectedUserAsString));
		if (!selectedUserAsString.equals(mPersistData.getUserId())) {
			System.out.println("launching user_detail activity");
			Intent intent = new Intent(getContext(), UserDetailActivity.class);
			intent.putExtra("user_id", mUserList.get(position).getId());
			getContext().startActivity(intent);
		} else {
			Intent intent = new Intent(getContext(), UserProfileActivity.class);
			getContext().startActivity(intent);
		}
		
	}


	
}
