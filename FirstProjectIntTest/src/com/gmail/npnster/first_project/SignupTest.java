package com.gmail.npnster.first_project;

import javax.inject.Inject;

import android.test.ActivityInstrumentationTestCase2;

import com.gmail.npnster.first_project.MyApp;
import com.gmail.npnster.first_project.PersistData;
import com.gmail.npnster.first_project.SignUpActivity;
import com.robotium.solo.Solo;

public class SignupTest extends ActivityInstrumentationTestCase2<SignUpActivity> {

	
	private String storedEmailId = null;
	private String storedToken = null ;
	MyApp mApp;
	
	public SignupTest() {
		super(SignUpActivity.class);
		mApp = MyApp.getObjectGraph().get(MyApp.class);
//		PersistData persistData = mApp.getPersistData();

		

		// TODO Auto-generated constructor stub
	}

	private Solo solo;   


	protected void setUp() throws Exception {
		super.setUp(); 
		System.out.println("setting up test");
//		PersistData persistData =  new PersistData(getActivity());
//		if (storedEmailId == null) storedEmailId = persistData.readEmailId();
//		if (storedToken == null) storedToken = persistData.readAccessToken();
//		System.out.println(String.format("got the following from the device email = %s token = %s", storedEmailId, storedToken));
		mApp.setApiRootUrl("http://10.0.2.2:3000"); 

		solo = new Solo(getInstrumentation(), getActivity());

	}

	protected void tearDown() throws Exception {
		super.tearDown();
		System.out.println("tearing down test");
//		System.out.println(String.format("saving the following from the device email = %s. token = %s", storedEmailId, storedToken));
//		MyApp.saveEmailId(storedEmailId);
//		MyApp.saveToken(storedToken);

	}

	public void testSignup() throws Exception {
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				getActivity().leave("jdd_012@example.com", "foobar");
			}
		});
		solo.clickOnCheckBox(0);
		solo.enterText(0, "john d davis");
		solo.enterText(1, "jdd_012@example.com");
		solo.enterText(2, "foobar");
		solo.enterText(3, "foobar");
		solo.clickOnButton("Sign Up!");
		solo.waitForText("Home");   
		
	}
	
	public void testSignUpSignOutSignInSequence() throws Exception {
		getActivity().runOnUiThread(new Runnable() {
			public void run() {
				getActivity().leave("jdd_012@example.com", "foobar");
			}
		});
		solo.clickOnCheckBox(0);
		solo.enterText(0, "john d davis");
		solo.enterText(1, "jdd_012@example.com");
		solo.enterText(2, "foobar");
		solo.enterText(3, "foobar");
		solo.clickOnButton("Sign Up!");
		solo.waitForText("Home");   
		solo.clickOnButton("Sign out");
		solo.waitForText("Welcome");
		solo.enterText(0, "jdd_012@example.com");
		solo.enterText(1, "foobar");
		solo.clickOnButton("Sign In!");
		solo.waitForText("Home");   
		

	}

}
