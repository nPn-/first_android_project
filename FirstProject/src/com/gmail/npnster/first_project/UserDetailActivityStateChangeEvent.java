package com.gmail.npnster.first_project;

public class UserDetailActivityStateChangeEvent {
	
	
	public enum State { RESUMED, PAUSED };
	private  State mState;
	
    public void setState(State state) {
    	mState = state;
    }
	
	public State getState() {
		return mState;
	}

}
