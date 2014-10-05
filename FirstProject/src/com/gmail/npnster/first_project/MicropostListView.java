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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class MicropostListView {

	private Fragment mFragment;
	private MicropostListPresenter mPresenter;
	@InjectView(R.id.micropost_listview) ListView micropostListView;
	@InjectView(R.id.user_micropost_icon) ImageView userIconImageView;
	@InjectView(R.id.user_micropost_name) TextView userNameTextView;

	public MicropostListView(Fragment fragment) {
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

	public void setPresenter(MicropostListPresenter presenter) {
		mPresenter = presenter;

	}

	public void initializeView(View rootView) {
		ButterKnife.inject(this, rootView);
		micropostListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				mPresenter.onMicropostSelected(position);	
			}			
		});
	}

	public void setMicropostListAdapter(ArrayAdapter<Micropost> adapter) {
		micropostListView.setAdapter(adapter);


	}

	
	public void setUserIcon(String gravatarUri) {
		System.out.println(gravatarUri);
		Picasso.with(getActivity()).load(gravatarUri).into(userIconImageView);
		
	}

	public void setUserName(String name) {
		userNameTextView.setText(name);
		
	}


	private Activity getActivity() {
		return getFragment().getActivity();
	}
	
	

}
