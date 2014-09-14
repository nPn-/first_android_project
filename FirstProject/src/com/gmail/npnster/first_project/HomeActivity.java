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
import android.widget.Button;
import android.widget.ImageView;

public class HomeActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		if (savedInstanceState == null) {
			android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
					transaction.add(R.id.container, new PlaceholderFragment());
					transaction.commit();
		}
	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//
//		System.out.println("inflating options menu");
//		getMenuInflater().inflate(R.menu.home, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		@Inject Bus mBus;
//		@Inject PersistData mPersistData;
//		
//		@InjectView(R.id.map_button) 
//		Button mapButton;
//		
//		@InjectView(R.id.sign_out_button)
//		Button signOutButton;
		
		private HomeView mHomeView;
		private HomePresenter mHomePresenter;
		
		@Override
		public void onPause() {
			super.onPause();
//			mBus.unregister(this);
			mBus.unregister(mHomePresenter);
		}

//		  @OnClick(R.id.sign_out_button)
//		  void signOut() {
//			  System.out.println("posting signout request to the bus");
//			  Log.i("signout", "signout");
//			  mBus.post(new SignoutRequest());
//		  }
//		  
//
//		@OnClick(R.id.map_button)
//		void onMapButton() {
//			System.out.println("launching map activity");
//			Intent intent = new Intent(getActivity(), MapActivity.class);
//			getActivity().startActivity(intent);
//			
//		}
//		
//		@Subscribe
//		public void onSignoutCompletedEvent(SignoutResponse event) {
//			System.out.println("inside home activity, onSignoutCompleted");
//			mPersistData.clearToken();
//			mPersistData.clearEmailId();
//			mPersistData.clearGcmRegId();
//			this.getActivity().finish();
//		
//			
//		}

		@Override
		public void onResume() {
			super.onResume();
			
//			 mBus.register(this);
			 mBus.register(mHomePresenter);
//			 System.out.println("posting get user profile request to the bus");
//			 mBus.post(new GetUserProfileRequest());
			 //	 mBus.post(new GetUsersRequest());  
//			 mBus.post(new GetFollowedUsersRequest(mPersistData.getUserId()));  
		}
		
//		
//		@Subscribe 
//		public void onGetFollowedUsersRequestCompleted(GetFollowedUsersResponse event ) {
//			System.out.println(String.format("followed users request completed, followed users list size = %d", event.getUsers().size()));
//			for (User followedUser : event.getUsers()) {
//				if ( followedUser.hasMicropost() ) {
//					System.out.println(String.format("User = %s, last post = %s", followedUser.getName(),
//							followedUser.lastMicropost().getContent()));
//				}
//			}
//			
//		}
//		
//		
//		@Subscribe
//		public void onProfileRequestCompleted(GetUserProfileResponse event) {
//			System.out.println("inside home activity - onProfileRequestCompleted");
//			//GetUserProfileResponse params = event.getParams();
//			String gravatarURL = "http://www.gravatar.com/avatar/"
//					+ event.getGravatar_id();
//		
////			Picasso.with(getActivity()).setDebugging(true);
//			Picasso.with(getActivity())
//			.load(gravatarURL)
//			.into((ImageView) getActivity().findViewById(
//					R.id.imageButton1));
//			System.out.println("leaving onProfileRequestCompleted");
//		}
//		
//		// just tying to see if the user list is working
//		@Subscribe
//		public void onGetUsersResponseAvailable(GetUsersResponse event) {
//			for (GetUsersResponse.User user : event.getUsers()) {
//				System.out.println(user.getName());
//			}
//			
//		}
//
		public PlaceholderFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			System.out.println("creating home fragment");    
			Injector.getInstance().inject(this);
			mHomePresenter = new HomePresenter();
			mHomeView = new HomeView(this);
			mHomePresenter.setView(mHomeView);
			mHomeView.setPresenter(mHomePresenter);
			

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			setHasOptionsMenu(true);
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
//			ButterKnife.inject(this, rootView);

			return rootView;
		}
		
		@Override
		public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
			System.out.println("inflating options menu");
			inflater.inflate(R.menu.home, menu);
		}
	
		@Override
		public boolean onOptionsItemSelected(MenuItem item) {
			return mHomeView.onOptionItemSelected(item) || super.onOptionsItemSelected(item);
		}

	}

}
