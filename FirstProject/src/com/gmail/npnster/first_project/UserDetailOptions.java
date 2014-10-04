package com.gmail.npnster.first_project;

import java.util.ArrayList;

public class UserDetailOptions {
	private ArrayList<UserDetailOption> mUserDetailOptions;
	
	public UserDetailOptions() {
		mUserDetailOptions = new ArrayList<UserDetailOption>();
		mUserDetailOptions.add(new UserDetailOption("allow_location", "See my location?"));
		mUserDetailOptions.add(new UserDetailOption("allow_email_id", "See my email id?"));
		mUserDetailOptions.add(new UserDetailOption("allow_phone_number", "See my phone number?"));
		mUserDetailOptions.add(new UserDetailOption("allow_microposts", "See my microposts?"));
	}
	
	public ArrayList<UserDetailOption> toArrayList() {
		return mUserDetailOptions;
	}
}
