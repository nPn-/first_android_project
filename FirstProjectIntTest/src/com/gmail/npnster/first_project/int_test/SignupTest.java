package com.gmail.npnster.first_project.int_test;

import android.test.ActivityInstrumentationTestCase2;

import com.gmail.npnster.first_project.MyApp;
import com.gmail.npnster.first_project.PersistData;
import com.gmail.npnster.first_project.SignUpActivity;
import com.robotium.solo.Solo;

public class SignupTest extends ActivityInstrumentationTestCase2<SignUpActivity> {

	public SignupTest() {
		super(SignUpActivity.class);
		

		// TODO Auto-generated constructor stub
	}

	private Solo solo;   

	PersistData persistData = MyApp.getPersistData();

	protected void setUp() throws Exception {
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());

	}

	protected void tearDown() throws Exception {
		super.tearDown();

	}

	public void testSignup() throws Exception {
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				getActivity().leave("jdd_012@example.com", "foobar");
			}
		});
		solo.enterText(0, "john d davis");
		solo.enterText(1, "jdd_012@example.com");
		solo.enterText(2, "foobar");
		solo.enterText(3, "foobar");
		solo.clickOnButton("Sign up!");
		solo.waitForText("Home");

	}

}
