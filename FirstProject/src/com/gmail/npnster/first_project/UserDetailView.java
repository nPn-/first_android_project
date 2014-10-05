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
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

public class UserDetailView {

	private Fragment mFragment;
	private UserDetailPresenter mPresenter;
	@InjectView(R.id.user_detail_email_id) TextView userDetailEmailIdTextView;
	@InjectView(R.id.user_detail_phone_number) TextView userDetailPhoneNumberTextView;
	@InjectView(R.id.user_detail_name) TextView userDetailNameTextView;
	@InjectView(R.id.user_detail_icon) ImageView userDetailIconImageView;
	@InjectView(R.id.user_detail_options) ListView userDetailOptionsView;
	@InjectView(R.id.user_detail_microposts) ListView userDetailMicropostsView;
	@InjectView(R.id.following_notice) TextView userDetailFollowingNoticeView;
	@InjectView(R.id.follow_switch) Switch userDetailFollowSwitchView;

	public UserDetailView(Fragment fragment) {
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
		case R.id.microposts:
			mPresenter.onMicropostssOptionSelected();
			return true;
		default:
			return false;

		}

	}

	public void setPresenter(UserDetailPresenter presenter) {
		mPresenter = presenter;

	}

	public void initializeView(View rootView) {
		ButterKnife.inject(this, rootView);
		userDetailFollowSwitchView.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mPresenter.onFollowSwitchChanged(isChecked);
				
			}
			
		});


	}

	public void setUserDetailOptionsAdapter(ArrayAdapter<UserDetailOption> adapter) {
		userDetailOptionsView.setAdapter(adapter);

	}


	private Activity getActivity() {
		return getFragment().getActivity();
	}

	public void onOptionChanged(int position, boolean checked) {
		System.out.println(String.format("option changed %d, state = %s", position, checked ));
		mPresenter.onUserPermissionChanged(position, checked);
		
	}

	public void setUserIcon(String gravatarUri) {
		System.out.println(gravatarUri);
		Picasso.with(getActivity()).load(gravatarUri).into(userDetailIconImageView);
		
	}

	public void setUserName(String name) {
		userDetailNameTextView.setText(name);
		
	}

	public void setUserEmailId(String email) {
		userDetailEmailIdTextView.setText(email);
		
	}
	
	public void setFollowingNotice(boolean following) {
		if (following) {
			userDetailFollowingNoticeView.setText("This user is following you!");
			userDetailFollowingNoticeView.setTextColor(getActivity().getResources().getColor(R.color.Orange));
		} else {
			userDetailFollowingNoticeView.setText("This user is not following you");
		}
	}

	public void setUserPhoneNumber(String phoneNumber) {
		userDetailPhoneNumberTextView.setText(phoneNumber);
		
	}

	public void setFollowUser(boolean areFollowing) {
		userDetailFollowSwitchView.setChecked(areFollowing);
		
	}

	public void setUserDetailMicropostsAdapter(
			ArrayAdapter<Micropost> micropostsAdapter) {
		userDetailMicropostsView.setAdapter(micropostsAdapter);
		
		
	}

}
