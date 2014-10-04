package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.UserListResponse.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class UserListView {

	private Fragment mFragment;
	private UserListPresenter mPresenter;
	@InjectView(R.id.users_listview) ListView userListView;

	public UserListView(Fragment fragment) {
		mFragment = fragment;


	}

	public Fragment getFragment() {
		return mFragment;
	}

	public boolean onOptionItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_settings:
			mPresenter.onSettingsOptionSelected();
			return true;
		default:
			return false;

		}

	}

	public void setPresenter(UserListPresenter presenter) {
		mPresenter = presenter;

	}

	public void initializeView(View rootView) {
		ButterKnife.inject(this, rootView);
		userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPresenter.onUserSelected(position);
				
			}

			

			
			
		});



	}

	public void setUserListAdapter(ArrayAdapter<User> adapter) {
		userListView.setAdapter(adapter);


	}


	private Activity getActivity() {
		return getFragment().getActivity();
	}
	
	

}
