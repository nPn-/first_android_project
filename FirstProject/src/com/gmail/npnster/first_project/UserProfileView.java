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

public class UserProfileView {

	private Fragment mFragment;
	private UserProfilePresenter mPresenter;
	@InjectView(R.id.user_profile_email_id) TextView userProfileEmailIdTextView;
	@InjectView(R.id.user_profile_phone_number) TextView userProfilePhoneNumberTextView;
	@InjectView(R.id.user_profile_name) TextView userProfileNameTextView;
//	@InjectView(R.id.user_profile_password) TextView userProfilePasswordTextView;
	@InjectView(R.id.user_profile_icon) ImageView userProfileIconImageView;
	@InjectView(R.id.user_profile_microposts) ListView userProfileMicropostsView;
	private ActionMode.Callback mMicropostActionModeCallback;

	public UserProfileView(Fragment fragment) {
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
		case R.id.action_edit:
			mPresenter.onEditOptionSelected();
			return true;
		default:
			return false;

		}

	}

	public void setPresenter(UserProfilePresenter presenter) {
		mPresenter = presenter;

	}

	public void initializeView(View rootView) {
		ButterKnife.inject(this, rootView);
		userProfileMicropostsView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(String.format("micropost item %d long clicked", position));
				mPresenter.onMicropostLongClicked(position);
				return true;
			}
			
		});
		mMicropostActionModeCallback = new ActionMode.Callback() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				System.out.println("actionmode created");
				MenuInflater inflater = mode.getMenuInflater();
				inflater.inflate(R.menu.user_profile_micropost_context_menu, menu);
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				switch (item.getItemId()) {
				case R.id.delete_micropost : 
					mPresenter.onDeleteMicropostMenuItemSelected();
					mode.finish();
					return true;

			default :
					return false;
			}
			}
		};
		
		
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
	
	
	public void setUserPhoneNumber(String phoneNumber) {
		userProfilePhoneNumberTextView.setText(phoneNumber);
		
	}

	public void setUserProfileMicropostsAdapter(
			ArrayAdapter<Micropost> micropostsAdapter) {
		userProfileMicropostsView.setAdapter(micropostsAdapter);
				
	}

	public void startMicropostActionMode() {
		getActivity().startActionMode(mMicropostActionModeCallback);
		
	}

}
