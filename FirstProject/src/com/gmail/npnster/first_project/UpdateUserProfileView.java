package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.GetMicropostsResponse.Micropost;
import com.gmail.npnster.first_project.api_params.UserListResponse.User;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class UpdateUserProfileView {

	private Fragment mFragment;
	private UpdateUserProfilePresenter mPresenter;
	@InjectView(R.id.update_user_profile_email_id) TextView userProfileEmailIdTextView;
	@InjectView(R.id.update_user_profile_name) TextView userProfileNameTextView;
	@InjectView(R.id.update_user_profile_password) TextView userProfilePasswordTextView;
	@InjectView(R.id.update_user_profile_confirmation_password) TextView userProfileConfirmationPasswordTextView;
	@InjectView(R.id.update_user_profile_current_password) TextView userProfileCurrentPasswordTextView;
	@InjectView(R.id.update_user_profile_icon) ImageView userProfileIconImageView;

	public UpdateUserProfileView(Fragment fragment) {
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

	public void setPresenter(UpdateUserProfilePresenter presenter) {
		mPresenter = presenter;
		

	}

	public void initializeView(View rootView) {
		ButterKnife.inject(this, rootView);
		userProfileCurrentPasswordTextView.setOnEditorActionListener(new OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,	KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					mPresenter.onCurrentPasswordEntered();
					handled = true;
				}
				return handled;
			}

		});

	}


	private Activity getActivity() {
		return getFragment().getActivity();
	}
	
	public void setUserIcon(String gravatarUri) {
		System.out.println(gravatarUri);
		Picasso.with(getActivity()).load(gravatarUri).into(userProfileIconImageView);
		
	}

	public void setUserName(String name) {
		userProfileNameTextView.setText(name);
		
	}

	public void setUserEmailId(String email) {
		userProfileEmailIdTextView.setText(email);
		
	}

	public String getNewUserName() {
		return userProfileNameTextView.getText().toString();
	}

	public String getNewEmailId() {
		return userProfileEmailIdTextView.getText().toString();
	}

	public String getNewPassword() {
		return userProfilePasswordTextView.getText().toString();
	}

	public String getNewConfirmationPassword() {
		return userProfileConfirmationPasswordTextView.getText().toString();
	}

	public String getCurrentPassword() {
		return userProfileCurrentPasswordTextView.getText().toString();
	}
	
	public void resetUpdateProfileForm() {
//		microPostContent.clearFocus();
//		microPostContent.setText("");
		InputMethodManager inputManager = (InputMethodManager)
				getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
				inputManager.toggleSoftInput(0, 0);
				
		
	}
	
}
