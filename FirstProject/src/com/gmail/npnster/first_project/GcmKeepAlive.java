package com.gmail.npnster.first_project;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;

public class GcmKeepAlive extends CountDownTimer {

	protected CountDownTimer timer;
	protected Context mContext;
	protected Intent gTalkHeartBeatIntent;
	protected Intent mcsHeartBeatIntent;

	public GcmKeepAlive(Context context) {
		super(4 * 60 * 1000, 4 * 60 * 1000);
		mContext = context;
		gTalkHeartBeatIntent = new Intent(
				"com.google.android.intent.action.GTALK_HEARTBEAT");
		mcsHeartBeatIntent = new Intent(
				"com.google.android.intent.action.MCS_HEARTBEAT");
		System.out.println("stariing heartbeat countdown timer");
		this.start();
	}

	@Override
	public void onTick(long millisUntilFinished) {

	}

	@Override
	public void onFinish() {

		broadcastIntents();
		this.start();

	}

	public void broadcastIntents() {
		System.out.println("sending heart beat to keep gcm alive");
		mContext.sendBroadcast(gTalkHeartBeatIntent);
		mContext.sendBroadcast(mcsHeartBeatIntent);
	}

}
