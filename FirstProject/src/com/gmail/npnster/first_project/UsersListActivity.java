package com.gmail.npnster.first_project;

import java.util.ArrayList;

import javax.inject.Inject;

import com.gmail.npnster.first_project.api_params.GetUserProfileRequest;
import com.gmail.npnster.first_project.api_params.GetUsersRequest;
import com.gmail.npnster.first_project.api_params.GetUsersResponse;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Build;

public class UsersListActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_users_list);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new UserListFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.users_list, menu);
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
	public static class UserListFragment extends ListFragment {

		private ArrayList<UserListItem> values = new ArrayList<UserListItem>();
		private ArrayAdapter<UserListItem> adapter;
		@Inject Bus mBus;

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
		public void onPause() {
			// TODO Auto-generated method stub
			super.onPause();
			mBus.unregister(this);
		}
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			 mBus.register(this);
			 System.out.println("posting get userslist request to the bus");
			 mBus.post(new GetUsersRequest());
		}
		
		@Subscribe
		public void onGetUsersResponseAvailable(GetUsersResponse event) {
			values.clear();
			for (GetUsersResponse.User user : event.getUsers()) {
				System.out.println(user.getName());
				UserListItem userListIterm = new UserListItem(user.getName(), user.getGravatar_id(), "", "");
				values.add(userListIterm);
			}
			adapter.notifyDataSetChanged();
			
		}
		
		public UserListFragment() {
			Injector.getInstance().inject(this);
		}

		// @Override
		// public View onCreateView(LayoutInflater inflater, ViewGroup
		// container,
		// Bundle savedInstanceState) {
		// View rootView = inflater.inflate(R.layout.fragment_users_list,
		// container, false);
		// return rootView;
		// }

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			adapter = new UsersListAdapter(getActivity(), values);
			setListAdapter(adapter);
		}

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			// do something with the data
		}

	}
}
