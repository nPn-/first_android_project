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

public class MicropostListActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_micropost_list);

		if (savedInstanceState == null) {
			android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
					transaction.add(R.id.container, new PlaceholderFragment());
					transaction.commit();
		}
	}


	public static class PlaceholderFragment extends Fragment {

		@Inject Bus mBus;
		private MicropostListView mMicropostListView;
		private MicropostListPresenter mMicropostListPresenter;
		private String mUserId;
		
		@Override
		public void onPause() {
			super.onPause();
			mBus.unregister(mMicropostListPresenter);
		}

		@Override
		public void onResume() {
			super.onResume();
			 mBus.register(mMicropostListPresenter);
			 String id = getActivity().getIntent().getStringExtra("user_id");
			 System.out.println(String.format("id recived = %s", id));
			if (id !=null) {
				mUserId = id;
			}
			 mMicropostListPresenter.setUserId(mUserId);
			 mMicropostListPresenter.refreshView();
		}
		
		public PlaceholderFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			System.out.println("creating MicropostList fragment");    
			Injector.getInstance().inject(this);
			mMicropostListPresenter = new MicropostListPresenter();
			mMicropostListView = new MicropostListView(this);
			mMicropostListPresenter.setView(mMicropostListView);
			mMicropostListView.setPresenter(mMicropostListPresenter);
			
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			setHasOptionsMenu(true);
			View rootView = inflater.inflate(R.layout.fragment_micropost_list, container,
					false);
			mMicropostListView.initializeView(rootView);

			return rootView;
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			System.out.println("inflating options menu");
			inflater.inflate(R.menu.micropost_list, menu);
		}
	
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			return mMicropostListView.onOptionItemSelected(item) || super.onOptionsItemSelected(item);
		}

	}

}
