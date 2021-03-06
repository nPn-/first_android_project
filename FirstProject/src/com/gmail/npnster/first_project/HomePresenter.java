package com.gmail.npnster.first_project;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.inject.Inject;

import retrofit.client.Header;

import com.gmail.npnster.first_project.api_params.CreateMicropostRequest;
import com.gmail.npnster.first_project.api_params.CreateMicropostResponse;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersResponse;
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
import android.view.Window;
import android.widget.ArrayAdapter;

public class HomePresenter {

	private HomeView mView;
	private ArrayList<UserListItem> followedUsers = new ArrayList<UserListItem>();
	private ArrayAdapter<UserListItem> adapter;
	@Inject
	PersistData mPersistData;
	@Inject
	Bus mBus;

	public HomePresenter() {
		Injector.getInstance().inject(this);

	}

	public void onOptionItemSelected(MenuItem item) {
		System.out.println("item selected");

	}

	public void setView(HomeView view) {
		mView = view;

	}

	public HomeView getView() {
		return mView;
	}

	public Activity getContext() {
		return getView().getFragment().getActivity();
	}

	public void onMapOptionSelected() {
		System.out.println("item map selected");
		System.out.println("launching map activity");
		Intent intent = new Intent(getContext(), MapActivity.class);
		getContext().startActivity(intent);
	}

	public void onUserListOptionSelected() {
		System.out.println("item user selected");
		System.out.println("launching user list activity");
		Intent intent = new Intent(getContext(), UserListActivity.class);
		getContext().startActivity(intent);
	}

	public void onSignOutOptionSelected() {
		System.out.println("item sign out selected");
		System.out.println("posting signout request to the bus");
		mBus.post(new SignoutRequest());

	}

	public void onSettingsOptionSelected() {
		System.out.println("item settings selected");

	}

	@Subscribe
	public void onSignoutCompletedEvent(SignoutResponse event) {
		System.out.println("inside home activity, onSignoutCompleted");
		mPersistData.clearToken();
		mPersistData.clearEmailId();
		mPersistData.clearGcmRegId();
		getView().getFragment().getActivity().finish();

	}

	public void refreshView() {
		followedUsers.clear();
		adapter = new FollowedUsersListAdapter(getContext(), followedUsers);
		mView.setFollowedUsersListAdapter(adapter);
		mBus.post(new GetFollowedUsersRequest(mPersistData.getUserId()));
		getContext().setProgressBarIndeterminateVisibility(true);
	}

	@Subscribe
	public void onGetFollowedUsersResponse(GetFollowedUsersResponse event) {

		getContext().setProgressBarIndeterminateVisibility(false);
		if (event.isSuccessful()) {
			followedUsers.clear();
			for (GetUsersResponse.User user : event.getUsers()) {
				System.out.println(user.getName());
				String lastPost = "";
				String postedTimeAgo = "";
				if (user.hasMicropost()) {
					lastPost = user.getLastMicropost().getContent();
					postedTimeAgo = MyDateUtils.timeAgo(user.getLastMicropost().getCreated_at());
				}
				UserListItem userListIterm = new UserListItem(user.getId(), user.getName(), user.getGravatar_id(), lastPost,
						postedTimeAgo);
				followedUsers.add(userListIterm);
			}
			adapter.notifyDataSetChanged();
		} else {
			mView.showGetFailedDialog(event.isNetworkError());
		}

	}

	public void onMicroPostSubmit(Editable text) {
		CreateMicropostRequest request = new CreateMicropostRequest(text.toString());
		mView.resetMicroPostContent();
		mBus.post(request);

	}

	@Subscribe
	public void onCreateMicropostResponse(CreateMicropostResponse event) {
		refreshView();
	}

	@Subscribe
	public void onNewMicropostEvent(NewMicropostEvent event) {
		refreshView();
	}

	public void onFollowerClicked(int position) {

		Integer selectedUser = followedUsers.get(position).getUserId();
		String selectedUserAsString = String.valueOf(selectedUser);
		System.out.println(String.format("user selected = %s", selectedUserAsString));
		if (!selectedUserAsString.equals(mPersistData.getUserId())) {
			System.out.println("launching user_detail activity");
			Intent intent = new Intent(getContext(), UserDetailActivity.class);
			intent.putExtra("user_id", selectedUser);
			getContext().startActivity(intent);
		} else {
			Intent intent = new Intent(getContext(), UserProfileActivity.class);
			getContext().startActivity(intent);
		}

		// Integer userId = followedUsers.get(position).getUserId();
		// System.out.println(String.format("followed user id selected = %s",
		// userId));

	}

}
