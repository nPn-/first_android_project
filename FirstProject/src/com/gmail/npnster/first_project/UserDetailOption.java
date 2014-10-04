package com.gmail.npnster.first_project;

public class UserDetailOption {
	
	private String mDisplayName;
	private String mName;
	private boolean mEnabled;
	
	public UserDetailOption(String name, String displayName) {
		mName = name;
		mDisplayName = displayName;
		mEnabled = false;
	}
	
	public String getName() {
		return mName;
	}
	
	public String getDisplayName() {
		return mDisplayName;
	}
	
	public boolean isEnabled() {
		return mEnabled;
	}
	
	public void setEnabled(boolean enabled) {
		mEnabled = enabled;
	}

}
