package com.gmail.npnster.first_project;

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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class HomeView {

	private Fragment mFragment;
	private GetFailedDialog mGetFailedDialog;
	private HomePresenter mPresenter;
	@InjectView(R.id.followed_users_listview)
	ListView followedUsersListView;
	@InjectView(R.id.microPostContent)
	EditText microPostContent;

	public HomeView(Fragment fragment) {
		mFragment = fragment;

	}

	public Fragment getFragment() {
		return mFragment;
	}

	public boolean onOptionItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.map:
			mPresenter.onMapOptionSelected();
			return true;
		case R.id.user_list:
			mPresenter.onUserListOptionSelected();
			return true;
		case R.id.sign_out:
			mPresenter.onSignOutOptionSelected();
			return true;
		case R.id.action_settings:
			mPresenter.onSettingsOptionSelected();
			return true;
		default:
			return false;

		}

	}

	public void setPresenter(HomePresenter presenter) {
		mPresenter = presenter;

	}

	public void initializeView(View rootView) {
		ButterKnife.inject(this, rootView);
		mGetFailedDialog = new GetFailedDialog(mFragment.getActivity());
		microPostContent.setHorizontallyScrolling(false);
		microPostContent.setMaxLines(10);
		microPostContent.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,	KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_DONE || event != null ) {
					mPresenter.onMicroPostSubmit(microPostContent.getText());
					handled = true;
				}
				return handled;
			}

		});
		followedUsersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPresenter.onFollowerClicked(position);				
			}
		});

	}

	public void setFollowedUsersListAdapter(ArrayAdapter<UserListItem> adapter) {
		followedUsersListView.setAdapter(adapter);

	}

	public void resetMicroPostContent() {
		microPostContent.clearFocus();
		microPostContent.setText("");
		InputMethodManager inputManager = (InputMethodManager)
				getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.toggleSoftInput(0, 0);
				
		
	}

	private Activity getActivity() {
		return getFragment().getActivity();
	}
	
	public void showGetFailedDialog(boolean networkError) {
		mGetFailedDialog.show(networkError);
	}

//	public void onFollowedUserClicked(int position) {
//		mPresenter.onFollowerClicked(position);
//		
//		
//	}

}
