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
import com.gmail.npnster.first_project.api_params.GetMicropostsRequest;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse;
import com.gmail.npnster.first_project.api_params.GetMicropostsResponse.Micropost;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
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

public class MicropostListPresenter {

	private MicropostListView mView;
	private ArrayList<Micropost> mMicropostList = new ArrayList<Micropost>();
	private ArrayAdapter<Micropost> adapter;
	private String mUserId;
	private String mCurrentUser;
	private boolean mProfileRequestActive = false;
	private boolean mMicropostRequestActive = false;
	@Inject
	PersistData mPersistData;
	@Inject
	Bus mBus;

	public MicropostListPresenter() {
		Injector.getInstance().inject(this);
		mCurrentUser = mPersistData.getUserId();

	}

	public void onOptionItemSelected(MenuItem item) {
		System.out.println("item selected");

	}

	public void setView(MicropostListView view) {
		mView = view;

	}

	public MicropostListView getView() {
		return mView;
	}

	public Activity getContext() {
		return getView().getFragment().getActivity();
	}

	public void onSettingsOptionSelected() {
		System.out.println("item settings selected");

	}

	public void refreshView() {
		mMicropostList.clear();
		adapter = new UserDetailMicropostAdapter(getContext(), mMicropostList);
		mView.setMicropostListAdapter(adapter);
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
		mMicropostList.clear();
		mMicropostList.addAll(event.getMicroposts());
		System.out.println(String.format("micorpost count = %s", event.getMicroposts().size()));
		adapter.notifyDataSetChanged();
		System.out.println(String.format("microposts adapter size = %d ", adapter.getCount()));

	}

	@Subscribe
	public void onGetUserProfileResponse(GetUserProfileResponse event) {
		mProfileRequestActive = false;
		requestComplete();
		mView.setUserIcon("http://www.gravatar.com/avatar/" + event.getGravatar_id());
		mView.setUserName(event.getName());

	}

	public void setUserId(String userId) {
		mUserId = userId;

	}

	public void onMicropostSelected(int position) {
		// TODO Auto-generated method stub

	}

	private void requestComplete() {
		if (!mProfileRequestActive && !mMicropostRequestActive) {
			getContext().setProgressBarIndeterminateVisibility(false);
		}

	}

}
