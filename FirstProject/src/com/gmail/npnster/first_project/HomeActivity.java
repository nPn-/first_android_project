package com.gmail.npnster.first_project;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.InjectView;

import com.gmail.npnster.first_project.api_params.SignoutApiParams;
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

		@InjectView(R.id.sign_out_button)
		Button signOutButton;

		@OnClick(R.id.sign_out_button)
		void signOut() {
			System.out.println("signout");
			Log.i("signout", "signout");
			MyApp.getApiRequester().signout(new Callback<Void>() {

				@Override
				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void success(Void arg0, Response arg1) {
					// TODO Auto-generated method stub
					MyApp.getPersistData().clearAccessToken();
					MyApp.getPersistData().clearUserId();
					
				}
			});
		}

		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			ApiRequester apiRequester = MyApp.getApiRequester();
			apiRequester.getMyParams(new Callback<UserParams>() {
				@Override
				public void success(UserParams params, Response response) {
					System.out.println(String.format("gravatar id = %s",
							params.getGravatar_id()));
					String gravatarURL = "http://www.gravatar.com/avatar/"
							+ params.getGravatar_id();
					Picasso.with(getActivity())
							.load(gravatarURL)
							.into((ImageView) getActivity().findViewById(
									R.id.imageButton1));
				}

				@Override
				public void failure(RetrofitError retroFitError) {

				}
			});
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
