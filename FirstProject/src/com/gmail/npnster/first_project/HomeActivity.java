package com.gmail.npnster.first_project;

import org.json.JSONException;
import org.json.JSONObject;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.turbomanage.httpclient.AsyncCallback;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;
import com.turbomanage.httpclient.android.AndroidHttpClient;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.os.Build;

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
			
			PersistData mPersistData = new PersistData(this.getActivity());
			String token = mPersistData.readAccessToken();
			String user = token.split(":")[0];
			Log.i("user info ",
					String.format("user id = %s, user token = %s", user, token));
			AndroidHttpClient httpClient = new AndroidHttpClient(
					"http://10.0.2.2:3000");
			String url = "/api/v1/users/" + user;
			ParameterMap params = httpClient.newParams().add("api_access_token", token);
			httpClient.get(url, params, new AsyncCallback() {
				public void onComplete(HttpResponse httpResponse) {
					System.out.println(httpResponse.getBodyAsString());
					JSONObject jsonResponse = null;
					String gravatarId = null;
					try {
						jsonResponse = new JSONObject(httpResponse.getBodyAsString());
						gravatarId = jsonResponse.getString("gravatar_id");
						System.out.println(String.format("gravatar_id  = %s", gravatarId));
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					String gravatarURL = "http://www.gravatar.com/avatar/" + gravatarId;
					Picasso.with(getActivity()).load(gravatarURL).into((ImageView) getActivity().findViewById(R.id.imageButton1));
				}

				public void onError(Exception e) {
					e.printStackTrace();
				}

			});
			return rootView;
		}
	}

}
