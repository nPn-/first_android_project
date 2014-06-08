package com.gmail.npnster.first_project;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.InjectView;

import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUserProfileResponse;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.gmail.npnster.first_project.api_params.SignoutRequest;
import com.gmail.npnster.first_project.api_params.SignoutResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
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
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			getBus().unregister(this);
		}

		@InjectView(R.id.sign_out_button)
		Button signOutButton;
		
		private Bus mBus;
		
		private Bus getBus() {
		    if (mBus == null) {
		      mBus = BusProvider.getInstance();
		    }
		    return mBus;
		  }

		  public void setBus(Bus bus) {
		    mBus = bus;
		  }
		
		

		@OnClick(R.id.sign_out_button)
		void signOut() {
			System.out.println("posting signout request to the bus");
			Log.i("signout", "signout");
			mBus.post(new SignoutRequest());
		}
		
		@Subscribe
		public void onSignoutCompletedEvent(SignoutResponse event) {
			System.out.println("inside home activity, onSignoutCompleted");
			MyApp.getPersistData().clearAccessToken();
			MyApp.getPersistData().clearUserId();
		}

		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			 getBus().register(this);
			 System.out.println("posting get user profile request to the bus");
			 mBus.post(new GetUserProfileRequest());
		//	 mBus.post(new GetUsersRequest());  
		}
		
		@Subscribe
		public void onProfileRequestCompleted(GetUserProfileResponse event) {
			System.out.println("inside home activity - onProfileRequestCompleted");
			//GetUserProfileResponse params = event.getParams();
			String gravatarURL = "http://www.gravatar.com/avatar/"
					+ event.getGravatar_id();
			Picasso.with(getActivity())
			.load(gravatarURL)
			.into((ImageView) getActivity().findViewById(
					R.id.imageButton1));
			
		}
		
		// just tying to see if the user list is working
		@Subscribe
		public void onGetUsersResponseAvailable(GetUsersResponse event) {
			for (GetUsersResponse.User user : event.getUsers()) {
				System.out.println(user.getName());
			}
			
		}

		public PlaceholderFragment() {
		}

		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);

		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_home, container,
					false);
			ButterKnife.inject(this, rootView);

			return rootView;
		}
	}

}
