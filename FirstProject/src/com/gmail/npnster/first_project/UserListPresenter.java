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
	private String mSearch = null;
	private int mUsersFound = 0;

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

	public Activity getContext() {
		return getView().getFragment().getActivity();
	}

	public void onSettingsOptionSelected() {
		System.out.println("item settings selected");

	}

	public void refreshView() {
		mUserList.clear();
		adapter = new UserListAdapter(getContext(), mUserList);
		mView.setUserListAdapter(adapter);
		mBus.post(new GetUsersRequest(1, 30, mSearch));
		getContext().setProgressBarIndeterminateVisibility(true);
	}

	@Subscribe
	public void onGetUsersResponse(GetUsersResponse event) {

		System.out.println(String.format("**got user list response loadmore = %b", event.getRequestEvent().isLoadMore()));
		getContext().setProgressBarIndeterminateVisibility(false);
		if (!event.getRequestEvent().isLoadMore()) {
			System.out.println("clearing list");
			mUserList.clear();
		}
		System.out.println(String.format("total found = %d", event.getFoundCount()));
		getView().setFoundCountMsg(String.format("Users found = %d", event.getFoundCount()));
		for (GetUsersResponse.User user : event.getUsers()) {
			System.out.println(user.getName());
			mUserList.add(user);
		}
		adapter.notifyDataSetChanged();
		System.out.println(String.format("**finished user list response loadmore = %b", event.getRequestEvent().isLoadMore()));

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

	public void onLoadMoreUsers(int page, int totalItemsCount) {
		if (page > 1) {
			System.out.println(String.format("time to add more users, requesting page = %d", page));
			mBus.post(new GetUsersRequest(page, 30, mSearch, true));
			getContext().setProgressBarIndeterminateVisibility(true);
		}

	}

	public void onSearchBoxChanged(Editable text) {
		// System.out.println(String.format("new search field =  %s", text));
		mSearch = text.toString();
		System.out.println(String.format("new search field =  %s", mSearch));
		refreshView();

	}

}
