package com.gmail.npnster.first_project;

import java.io.IOException;

import javax.inject.Inject;

import com.gmail.npnster.first_project.api_params.CreateDeviceRequest;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.squareup.otto.Bus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class RegisterGcmActivity extends Activity {

	private View mProgressBar;
	private GoogleCloudMessaging gcm;
	private String gcmRegId;
	private Context context;
	private PersistData persistData;
	@Inject Bus mBus;
	@Inject PersistData mPersistData;
	@Inject @ForApplication TelephonyManager mTelephonyManager;

	private Bus getBus() {
		return mBus;
	}
	
//	private Bus getBus() {
//	    if (mBus == null) {
//	      mBus = BusProvider.getInstance();
//	    }
//	    return mBus;
//	  }
//
//	  public void setBus(Bus bus) {
//	    mBus = bus;
//	  }

	@Override
	protected void onResume() {
		super.onResume();
		mProgressBar.animate();
		getBus().register(this);
		GcmRegistrationTask registrationTask = new GcmRegistrationTask();
		if (mPersistData.getGcmRegId() == "") {
			registrationTask.execute(null,null,null);
		}
		

	}
	     
	@Override
	public void onPause() {
		super.onPause();
		getBus().unregister(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Injector.getInstance().inject(this);
		setContentView(R.layout.activity_register_gcm);
		mProgressBar = findViewById(R.id.gcm_reg_progress_bar);
		context = getApplicationContext();
		System.out.println("trying to register for gcm id");

	}

	private class GcmRegistrationTask extends AsyncTask {

		@Override
		protected Void doInBackground(Object... params) {
			if (gcm == null) {
				gcm = GoogleCloudMessaging.getInstance(context);
				try {
					System.out.println("making call to gcm unreg/reg");
					gcm.unregister();
					gcmRegId = gcm.register("255040819715");

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mPersistData.readAccessToken() != "") {
				mPersistData.saveGcmRegId(gcmRegId);
				String phoneNumber = mTelephonyManager.getLine1Number();
				System.out.println(String.format("iso country code = %s,  phone code = %s", mTelephonyManager.getSimCountryIso(), ISOCountryCodeMap.getPhoneCode(mTelephonyManager.getSimCountryIso())));
				phoneNumber = ISOCountryCodeMap.getPhoneCode(mTelephonyManager.getSimCountryIso()) + phoneNumber;
				CreateDeviceRequest createDeviceRequest = new CreateDeviceRequest(
						gcmRegId, "android_phone", true, phoneNumber);
				mBus.post(createDeviceRequest);
			}
			finish();
		}
	}

}
