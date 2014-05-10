package com.gmail.npnster.first_project;

import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.npnster.first_project.api_events.ApiResponseEvent;
import com.gmail.npnster.first_project.api_events.ApiRequestEvent;
import com.gmail.npnster.first_project.api_events.SignupCompletedEvent;
import com.gmail.npnster.first_project.api_events.SignupRequestEvent;
import com.gmail.npnster.first_project.api_params.SignupRequest;
import com.gmail.npnster.first_project.api_params.SignupResponse;
import com.gmail.npnster.first_project.api_params.UserRequestParams;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import com.turbomanage.httpclient.HttpResponse;
import com.turbomanage.httpclient.ParameterMap;
import com.turbomanage.httpclient.android.AndroidHttpClient;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Activity which displays a login screen to the user, offering registration as
 * well.
 */
public class SignUpActivity extends Activity {
	/**
	 * A dummy authentication store containing known user names and passwords.
	 * TODO: remove after connecting to a real authentication system.
	 */
	private static final String[] DUMMY_CREDENTIALS = new String[] {
			"foo@example.com:hello", "bar@example.com:world" };

	/**
	 * The default email to populate the email field with.
	 */
	public static final String EXTRA_EMAIL = "com.example.android.authenticatordemo.extra.EMAIL";

	/**
	 * Keep track of the login task to ensure we can cancel it if requested.
	 */
	// UserLoginTask mAuthTask = null;

	// Values for email and password at the time of the login attempt.
	private String mName;
	private String mEmail;
	private String mPassword;
	private String mPasswordConfirmation;

	// UI references.
	private EditText mNameView;
	private EditText mEmailView;
	private EditText mPasswordView;
	private EditText mPasswordConfirmationView;

	private View mLoginFormView;
	private View mLoginStatusView;
	private TextView mLoginStatusMessageView;
	private boolean requestInFlight = false;

	private Bus mBus;
	
	private Bus getBus() {
	    if (mBus == null) {
	      mBus = BusProvider.getInstance();
	    }
	    return mBus;
	  }

	  public void setBus(Bus bus) {
	    mBus = bus;
	  }
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_sign_up);

		// Set up the login form.
		mEmail = getIntent().getStringExtra(EXTRA_EMAIL);
		mNameView = (EditText) findViewById(R.id.name);
		mEmailView = (EditText) findViewById(R.id.email);
		mEmailView.setText(mEmail);
		mPasswordView = (EditText) findViewById(R.id.password);
		mPasswordConfirmationView = (EditText) findViewById(R.id.password_confirmation);
		mPasswordConfirmationView
				.setOnEditorActionListener(new TextView.OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView textView, int id,
							KeyEvent keyEvent) {
						if (id == R.id.login || id == EditorInfo.IME_NULL) {
							attemptLogin();
							return true; 
						}
						return false;
					}
				});

		mLoginFormView = findViewById(R.id.login_form);
		mLoginStatusView = findViewById(R.id.login_status);
		mLoginStatusMessageView = (TextView) findViewById(R.id.login_status_message);

		findViewById(R.id.sign_in_button).setOnClickListener(
				new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						attemptLogin();
					}
				});
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getBus().register(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		getBus().unregister(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.sign_up, menu);
		return true;
	}

	/**
	 * Attempts to sign in or register the account specified by the login form.
	 * If there are form errors (invalid email, missing fields, etc.), the
	 * errors are presented and no actual login attempt is made.
	 */
	public void attemptLogin() {
//		if (mAuthTask != null) {
//			return;
//		}
		
		if (requestInFlight) {
			return;
		} else {
			requestInFlight = true;
		}

		// Reset errors.
		mNameView.setError(null);
		mEmailView.setError(null);
		mPasswordView.setError(null);
		mPasswordConfirmationView.setError(null);

		// Store values at the time of the login attempt.
		mName = mNameView.getText().toString();
		mEmail = mEmailView.getText().toString();
		mPassword = mPasswordView.getText().toString();
		mPasswordConfirmation = mPasswordConfirmationView.getText().toString();

		boolean cancel = false;
		View focusView = null;
		// Check for a valid name
		if (TextUtils.isEmpty(mName)) {
			mNameView.setError(getString(R.string.error_field_required));
			focusView = mNameView;
			cancel = true;
		} else if (mName.length() < 1) {
			mNameView.setError(getString(R.string.error_invalid_name));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid password.
		if (TextUtils.isEmpty(mPassword)) {
			mPasswordView.setError(getString(R.string.error_field_required));
			focusView = mPasswordView;
			cancel = true;
		} else if (mPassword.length() < 6) {
			mPasswordView.setError(getString(R.string.error_invalid_password));
			focusView = mPasswordView;
			cancel = true;
		}

		// Check for a valid password confirmation.
		if (!mPasswordConfirmation.equals(mPassword)) {
			mPasswordConfirmationView
					.setError(getString(R.string.error_passwords_dont_match));
			focusView = mPasswordConfirmationView;
			cancel = true;
		}

		// Check for a valid email address.
		if (TextUtils.isEmpty(mEmail)) {
			mEmailView.setError(getString(R.string.error_field_required));
			focusView = mEmailView;
			cancel = true;
		} else if (!mEmail.contains("@")) {
			mEmailView.setError(getString(R.string.error_invalid_email));
			focusView = mEmailView;
			cancel = true;
		}

		if (cancel) {
			// There was an error; don't attempt login and focus the first
			// form field with an error.
			requestInFlight = false;
			focusView.requestFocus();
		} else {
			// Show a progress spinner, and kick off a background task to
			// perform the user login attempt.
			mLoginStatusMessageView.setText(R.string.login_progress_signing_in);
			showProgress(true);
			PersistData persistData = MyApp.getPersistData();
			System.out.println("postin signup request to bus");
            mBus.post(new ApiRequestEvent<SignupRequest>(new SignupRequest(mName,mEmail,mPassword,mPasswordConfirmation )));

//			mAuthTask = new UserLoginTask();
//			mAuthTask.execute((Void) null);
		}
	}
	
	@Subscribe
	public void onSignupCompleted(ApiResponseEvent<SignupResponse> event) {
		System.out.println("in onSignupCompleted");
		System.out.println(event.toString());
		String returnedToken = event.getParams().getToken();
		System.out.println(String.format("in onSignupCompleted, got token = %s ", returnedToken));
		showProgress(false);
		requestInFlight = false;
		
		if ( returnedToken != null ) {
		    MyApp.saveToken(returnedToken);
		    MyApp.saveEmailId(mEmail);
        }	
		finish();
		Intent intent = new Intent(getApplicationContext(), MainActivity.class);
	    startActivity(intent);
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	private void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			mLoginStatusView.setVisibility(View.VISIBLE);
			mLoginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			mLoginFormView.setVisibility(View.VISIBLE);
			mLoginFormView.animate().setDuration(shortAnimTime)
					.alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							mLoginFormView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			mLoginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}

	/**
	 * Represents an asynchronous login/registration task used to authenticate
	 * the user.
	 */
//	public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {
//		@Override
//		protected Boolean doInBackground(Void... params) {
//			// TODO: attempt authentication against a network service.
//            ApiRequestRepository apiRequestRepository = MyApp.getApiRequester();
//            PersistData persistData = MyApp.getPersistData();
//            UserSignupParameters signupParams = new UserSignupParameters(mName,mEmail,mPassword,mPasswordConfirmation);
//			ReturnedToken returnedToken = null;
//            try {
//            returnedToken = apiRequestRepository.signup(signupParams);
//            } catch (RuntimeException e) {
//            	e.printStackTrace();
//			} 			
//            if ( returnedToken != null ) {
//			    MyApp.saveToken(returnedToken.getToken());
//			    MyApp.saveEmailId(mEmail);
//            }			
//			System.out.println(String.format("email = %s, token = %s", persistData.readEmailId(), persistData.readAccessToken() ));
//			return true;
//		}
//
//		@Override
//		protected void onPostExecute(final Boolean success) {
//			mAuthTask = null;
//			showProgress(false);
//
//			if (success) {
//				finish();
////				Intent intent = new Intent(getApplicationContext(), MainActivity.class);
////				startActivity(intent);
//			} else {
//				mPasswordView
//						.setError(getString(R.string.error_incorrect_password));
//				mPasswordView.requestFocus();
//			}
//		}
//
//		@Override
//		protected void onCancelled() {
//			mAuthTask = null;
//			showProgress(false);
//		}
//	}
}
