package com.gmail.npnster.first_project;

import javax.inject.Inject;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends Activity {

	@Inject PersistData mPersistData;
	@Inject @ForApplication TelephonyManager mTelephonyManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Injector.getInstance().inject(this);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.i("info", "here");
		Intent intent = null;

		System.out.println(String.format("email id = %s", mPersistData.readEmailId()));
		System.out.println(String.format("access token = %s", mPersistData.readEmailId()));
		System.out.println(String.format("reg id = %s", mPersistData.getGcmRegId()));
		System.out.println(String.format("phone number = %s", mTelephonyManager.getLine1Number()));

		if (mPersistData.readEmailId() == "") {
			intent = new Intent(this, SignUpActivity.class);
			intent.putExtra("ACTION", "signup");
		} else if (mPersistData.readAccessToken() == "") {
			intent = new Intent(this, SignInActivity.class);
			intent.putExtra("ACTION", "signin");
		} else if  (mPersistData.getGcmRegId() == "") {
			intent = new Intent(this, RegisterGcmActivity.class);
			System.out.println("starting reg gcm activity");
		} else {
			System.out.println(String.format("user = %s , token = %s",
					mPersistData.readEmailId(), mPersistData.readAccessToken()));
			intent = new Intent(this, HomeActivity.class);
			intent.putExtra("ACTION", "home");
		}
		
		if (intent != null) {
			startActivity(intent);
		}

		// force launch userlist for now
		// intent = new Intent(this, UsersListActivity.class);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}