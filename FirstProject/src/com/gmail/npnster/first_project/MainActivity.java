package com.gmail.npnster.first_project;


import org.json.JSONException;
import org.json.JSONObject;

import com.turbomanage.httpclient.AsyncCallback;
import com.turbomanage.httpclient.ParameterMap;
import com.turbomanage.httpclient.android.AndroidHttpClient;
import com.turbomanage.httpclient.HttpResponse;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;




public class MainActivity extends Activity {

	protected PersistData persistData;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        persistData = new PersistData(this);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }
    
    @Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.i("info", "here");
		AndroidHttpClient httpClient = new AndroidHttpClient("https://jdd-sample-app-rails4.herokuapp.com");
		
		ParameterMap params = httpClient.newParams()
				.add("param1", "parm1_value");
				
		
		//httpClient.addHeader("head1", "head_value1");
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject("{ user: { email: npnster@gmail.com, password: trans1st0r, password_confirmation: trans1st0r}}");
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			
		}
		System.out.println("json");
		System.out.println(jsonObject.toString());
		System.out.println("end json");
		String jparams = "{ user: { email: npnster@gmail.com, password: trans1st0r, password_confirmation: trans1st0r}})";
		byte[] b = jparams.getBytes();
		// post code example below
//		httpClient.post("/api/v1/signin", "application/json", jsonObject.toString().getBytes() ,new AsyncCallback() {
//            @Override
//            public void onError(Exception e) {
//                e.printStackTrace();
//            }
//			@Override
//			public void onComplete(HttpResponse httpResponse) {
//				 System.out.println(httpResponse.getBodyAsString());		
//			}
//		});
//		NetworkRequest request = new NetworkRequest();
//		request.execute();
		Intent intent = null;
		if (persistData.readUserId() == "" ) {
			intent = new Intent(this, SignUpActivity.class);
		    intent.putExtra("ACTION", "signup");	
		} else if (persistData.readAccessToken() == "") {
			intent = new Intent(this, SignUp.class);
		    intent.putExtra("ACTION", "signin");
		} else {
			System.out.println(String.format("user = %s , token = %s", persistData.readUserId(), persistData.readAccessToken()));
			intent = new Intent(this, HomeActivity.class);
		    intent.putExtra("ACTION", "home");
		}
		
	    if (intent != null) {
	    	startActivity(intent);
	    }
	
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}