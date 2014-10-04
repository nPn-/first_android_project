package com.gmail.npnster.first_project;

import java.util.ArrayList;

public class UserDetailOptions {
	private ArrayList<UserDetailOption> mUserDetailOptions;
	
	public UserDetailOptions() {
		mUserDetailOptions = new ArrayList<UserDetailOption>();
		mUserDetailOptions.add(new UserDetailOption("allow_location", "See your location?"));
		mUserDetailOptions.add(new UserDetailOption("allow_email_id", "See your email id?"));
		mUserDetailOptions.add(new UserDetailOption("allow_phone_number", "See your phone number?"));
		mUserDetailOptions.add(new UserDetailOption("allow_microposts", "See your microposts?"));
	}
	
	public ArrayList<UserDetailOption> toArrayList() {
		return mUserDetailOptions;
	}
}
