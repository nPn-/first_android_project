package com.gmail.npnster.first_project;




import javax.inject.Inject;

import com.gmail.npnster.first_project.api_params.SignupRequest;
import com.gmail.npnster.first_project.api_params.SignupResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;

public class ApiCheckerActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_api_checker);

	       if (savedInstanceState == null) {
	            getFragmentManager().beginTransaction()
	                    .add(R.id.container, new PlaceholderFragment(), "api_fragment")
	                    .commit();
	        }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.api_checker, menu);
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
		private TextView dummy;
		@Inject Bus mBus;
		@Inject MyApp app;
//		PersistData persistData = app.getPersistData();

//		private Bus getBus() {
//			if (mBus == null) {
//				mBus = BusProvider.getInstance();
//			}
//			return mBus;
//		}
//
//		public void setBus(Bus bus) {
//			mBus = bus;
//		}

		
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			
			dummy = (TextView) getActivity().findViewById(R.id.textViewDummy);
			if (dummy == null) System.out.println("dummy is null");
			dummy.setVisibility(View.GONE);

			mBus.register(this);
		}
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			super.onCreate(savedInstanceState);
			MyApp.inject(this);
		}

		@Override
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			mBus.unregister(this);
		}

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_api_checker,
					container, false);
			
			return rootView;
			
		}
		
		public void testSignup() {
			getActivity().findViewById(R.id.textViewDummy).setVisibility(View.GONE);
					mBus.post(new SignupRequest("John", "me@example.com", "foobar", "foobar"));
					
				}
			
			
		
		
		@Subscribe
		public void onSignupResponseAvailable(SignupResponse event) {
			System.out.println("******in onSignupCompleted");
			getActivity().findViewById(R.id.textViewDummy).setVisibility(View.VISIBLE);
		}
		
		
	}

}
