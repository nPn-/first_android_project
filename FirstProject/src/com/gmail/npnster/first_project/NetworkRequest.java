package com.gmail.npnster.first_project;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class NetworkRequest extends AsyncTask {

	@Override
	protected Object doInBackground(Object... params) {
		URL url;
		HttpURLConnection urlConnection = null; 
		InputStream in;
		try {
	/* sign in request	*/	
			JSONObject jsonObject = new JSONObject("{ user: { email: npnster@gmail.com, password: trans1st0r, password_confirmation: trans1st0r}})");
			url = new URL("http://10.0.2.2:3000/api/v1/signin");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("POST");
			urlConnection.setRequestProperty("Content-Type", "application/json");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
		    wr.writeBytes(jsonObject.toString());
			wr.flush();
			wr.close();
			
			urlConnection.connect();
			int code_post = urlConnection.getResponseCode();
			System.out.println(urlConnection.getHeaderFields().toString());
			
			if (code_post < 300 ) {
				in = new BufferedInputStream(urlConnection.getInputStream());
			} else {
				in = new BufferedInputStream(urlConnection.getErrorStream());
			}
		   // Log.i("info", Integer.toString(urlConnection.getResponseCode()));
			System.out.printf("code = %d\n", urlConnection.getResponseCode());
			readStream(in);
			Log.i("info", "got url connection");

	
	/* list users with valid token */	
			url = new URL("http://10.0.2.2:3000/api/v1/users" + "?api_access_token=1:cVPqYRjxiZYVJqWU9RWlOQ");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoInput(true);
			urlConnection.setRequestProperty("Content-Type", "application/json");
			//urlConnection.addRequestProperty("user", jsonObject.toString());
		
			
			urlConnection.connect();
			int code = urlConnection.getResponseCode();
			System.out.println(urlConnection.getHeaderFields().toString());
			if (code < 300 ) {
				in = new BufferedInputStream(urlConnection.getInputStream());
			} else {
				in = new BufferedInputStream(urlConnection.getErrorStream());
			}
		   // Log.i("info", Integer.toString(urlConnection.getResponseCode()));
			System.out.printf("code = %d\n", urlConnection.getResponseCode());
			Log.i("info", "got url connection");
			
		    
		    
		    Log.i("info", "got inputstream from connection");
		     readStream(in);
		    Log.i("response",Integer.toString(urlConnection.getResponseCode()));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			Log.i("info", "in execption");
			in = new BufferedInputStream(urlConnection.getErrorStream());
			Log.i("info", "got errorstream from connection");
			readStream(in);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    finally {
		     urlConnection.disconnect();
		   }
		return null;
	}
	
	private void readStream(InputStream in) {
		  Log.i("info", "in readStream");
		  BufferedReader reader = null;
		  try {
		    reader = new BufferedReader(new InputStreamReader(in));
			Log.i("info", "got reader from instream");

		    String line = "";
		    while ((line = reader.readLine()) != null) {
		      System.out.println(line);
		    }
		  } catch (IOException e) {
		    e.printStackTrace();
		  } finally {
		    if (reader != null) {
		      try {
		        reader.close();
		      } catch (IOException e) {
		        e.printStackTrace();
		        }
		    }
		  }
		} 

}
