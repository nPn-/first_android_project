package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.UserListResponse.User;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
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
	@InjectView(R.id.users_listview)
	ListView userListView;
	@InjectView(R.id.search) EditText mSearchEditText;
	@InjectView(R.id.found_count)  TextView mFoundCount;
	

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
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				mPresenter.onUserSelected(position);

			}

		});
		userListView.setOnScrollListener(new EndlessScrollListener() {

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				mPresenter.onLoadMoreUsers(page, totalItemsCount);
				
			}

		});
		
		mSearchEditText.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void afterTextChanged(Editable s) {
				mPresenter.onSearchBoxChanged(mSearchEditText.getText());
				
			}
			
		});

	}

	public void setUserListAdapter(ArrayAdapter<User> adapter) {
		userListView.setAdapter(adapter);

	}

	private Activity getActivity() {
		return getFragment().getActivity();
	}
	
	public void setFoundCountMsg(String foundCountMsg) {
		mFoundCount.setText(foundCountMsg);
	}

}
