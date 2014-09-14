package com.gmail.npnster.first_project;

import android.app.ActionBar;
import android.support.v4.app.Fragment;
import android.view.MenuItem;


public class HomeView {
	
	private Fragment mFragment;
	private HomePresenter mPresenter;
	
	public HomeView(Fragment fragment) {
		mFragment = fragment;
	
		
		
	}
	
	public Fragment getFragment() {
		return mFragment;
	}

	public boolean onOptionItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case  R.id.map :
			mPresenter.onMapOptionSelected();
			return true;
		case  R.id.sign_out :
			mPresenter.onSignOutOptionSelected();
			return true;
		case  R.id.action_settings :
			mPresenter.onSettingsOptionSelected();
			return true;
		default :
			return false;
				
		}
		
	}

	public void setPresenter(HomePresenter presenter) {
		mPresenter = presenter;
		
	}
	
	
	
	

}
