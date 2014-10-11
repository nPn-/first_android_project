package com.gmail.npnster.first_project;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.InjectView;

import com.gmail.npnster.first_project.api_params.GetFollowedUsersRequest;
import com.gmail.npnster.first_project.api_params.GetFollowedUsersResponse;
import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignoutResponse;
import com.gmail.npnster.first_project.api_params.UserListResponse.User;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class UserProfileActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_user_profile);

		if (savedInstanceState == null) {
			android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
					transaction.add(R.id.container, new PlaceholderFragment());
					transaction.commit();
		}
	}


	public static class PlaceholderFragment extends Fragment {

		@Inject Bus mBus;
		private UserProfileView mUserProfilelView;
		private UserProfilePresenter mUserProfilePresenter;
		
		
		@Override
		public void onPause() {
			super.onPause();
			mBus.unregister(mUserProfilePresenter);
		}

		@Override
		public void onResume() {
			super.onResume();
			 mBus.register(mUserProfilePresenter);
			 mUserProfilePresenter.refreshView();
		}
		
		public PlaceholderFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			System.out.println("creating UserProfile fragment");    
			Injector.getInstance().inject(this);
			mUserProfilePresenter = new UserProfilePresenter();
			mUserProfilelView = new UserProfileView(this);
			mUserProfilePresenter.setView(mUserProfilelView);
			mUserProfilelView.setPresenter(mUserProfilePresenter);
			setRetainInstance(true);
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			setHasOptionsMenu(true);
			View rootView = inflater.inflate(R.layout.fragment_user_profile, container,
					false);
			mUserProfilelView.initializeView(rootView);

			return rootView;
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			System.out.println("inflating options menu");
			inflater.inflate(R.menu.user_profile, menu);
		}
	
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			return mUserProfilelView.onOptionItemSelected(item) || super.onOptionsItemSelected(item);
		}

	}

}
