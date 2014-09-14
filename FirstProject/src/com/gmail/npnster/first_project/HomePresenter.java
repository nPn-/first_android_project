package com.gmail.npnster.first_project;

import javax.inject.Inject;

import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignoutResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MenuItem;

public class HomePresenter {
	
	private HomeView mView;
	@Inject PersistData mPersistData;
	@Inject Bus mBus;
	
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
	
	public Context getContext() {
		return getView().getFragment().getActivity();
	}

	public void onMapOptionSelected() {
		System.out.println("item map selected");
		System.out.println("launching map activity");
		Intent intent = new Intent(getContext(), MapActivity.class);
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
	

}
