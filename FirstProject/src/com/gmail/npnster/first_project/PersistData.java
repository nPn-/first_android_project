package com.gmail.npnster.first_project;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;

public class PersistData {
	
	private static final String GENERAL_SETTINGS_FILE_NAME = "com.gmail.npnster.fist_project.GENERAL_SETTINGS_FILE";
	
	private MyApp mApp;
	private SharedPreferences generalSettingsFile;
	private SharedPreferences.Editor generalSettingsFileEditor;
	private String token;
	private String user;
	private String email;    
	private String gcmRegId;
	private LatLngBounds mapBounds;
	private int centerOnPosition;
	private int centerOnMode;


	
	// this mainly just hides this method since it should only be called via the application class
	// which also caches a copy of the token 
//	private class Cached {
		private void writeAccessToken(String accessToken) {
			generalSettingsFileEditor.putString("ACCESS_TOKEN", accessToken);
			generalSettingsFileEditor.commit();
		}
		private void writeEmailId(String emailId) {
			generalSettingsFileEditor.putString("USER_ID", emailId);
			generalSettingsFileEditor.commit();
		}
		private void writeGcmRegId(String gcmRegId) {
			generalSettingsFileEditor.putString("GCM_REG_ID", gcmRegId);
			generalSettingsFileEditor.commit();
		}
		private void writeClearAccessToken() {
			generalSettingsFileEditor.putString("ACCESS_TOKEN", "");
			generalSettingsFileEditor.commit();
		}
		private void writeClearGcmRegId() {
			generalSettingsFileEditor.putString("GCM_REG_ID", "");
			generalSettingsFileEditor.commit();
		}
		private void writeClearUserId() {
			generalSettingsFileEditor.putString("USER_ID", "");
			generalSettingsFileEditor.commit();
		}
		
		private void writeMapBounds(LatLngBounds bounds) {
			generalSettingsFileEditor.putFloat("SOUTHWEST_LAT",(float) bounds.southwest.latitude);
			generalSettingsFileEditor.putFloat("SOUTHWEST_LNG",(float) bounds.southwest.longitude);
			generalSettingsFileEditor.putFloat("NORTHEAST_LAT",(float) bounds.northeast.latitude);
			generalSettingsFileEditor.putFloat("NORTHEAST_LNG",(float) bounds.northeast.longitude);
			generalSettingsFileEditor.commit();
		}
		
		private void writeCenterOnPosition(int position) {
			generalSettingsFileEditor.putInt("CENTER_ON_POSITION", position);
			generalSettingsFileEditor.commit();
		}
		
		private void writeCenterOnMode(int mode) {
			generalSettingsFileEditor.putInt("CENTER_ON_MODE", mode);
			generalSettingsFileEditor.commit();
		}


		
//	}
	
	public PersistData(MyApp app) {
		mApp = app;
		System.out.println(String.format("in peristdata const context = %s", mApp));
		System.out.println(String.format("in peristdata file = %s", GENERAL_SETTINGS_FILE_NAME));
		generalSettingsFile = mApp.getSharedPreferences(GENERAL_SETTINGS_FILE_NAME, Context.MODE_PRIVATE );
		generalSettingsFileEditor = generalSettingsFile.edit();
		
		token = readAccessToken();
		email = readEmailId();
		user = getUserFromToken();
		gcmRegId = readGcmRegId();
		mapBounds = readMapBounds();
		centerOnPosition = readCenterOnPosition();
		centerOnMode = readCenterOnMode();
		
	}
	
	public String readAccessToken() {
		String token = generalSettingsFile.getString("ACCESS_TOKEN","");
		return(token);
	}
	

	public String readGcmRegId() {
		String gcmRegId = generalSettingsFile.getString("GCM_REG_ID","");
		return(gcmRegId);
	}
	


	public String readEmailId() {
		String emaiId = generalSettingsFile.getString("USER_ID","");
		return(emaiId);
	}
	
	public String readUserId() {
		return readAccessToken().split(":")[0];
	}
	
	public LatLngBounds readMapBounds() {
		float southwestLat = generalSettingsFile.getFloat("SOUTHWEST_LAT",1.0f);
		float southwestLng = generalSettingsFile.getFloat("SOUTHWEST_LNG",-179f);
		float northeastLat = generalSettingsFile.getFloat("NORTHEAST_LAT",89.0f);
		float northeastLng = generalSettingsFile.getFloat("NORTHEAST_LNG",179f);
		return new LatLngBounds(new LatLng(southwestLat, southwestLng), new LatLng(northeastLat, northeastLng));
	}
	
	public int readCenterOnPosition() {
		int position = generalSettingsFile.getInt("CENTER_ON_POSITION", 0);
		return position;
	}
	
	public int readCenterOnMode() {
		int mode = generalSettingsFile.getInt("CENTER_ON_MODE", 0);
		return mode;
	}

	public String getToken() {
		return token;
	}

	public String getEmail() {
		return email;
	}
	
	public String getGcmRegId() {
		return gcmRegId;
	}
	
	public LatLngBounds getMapBounds() {
		return mapBounds;
	}
	
	public int getCenterOnPosition() {
		return centerOnPosition;
	}
	
	public int getCenterOnMode() {
		return centerOnMode;
	}
	
	public String getUserId() {
		return getUserFromToken();
	}

	public void saveToken(String tokenToSave) {
		writeAccessToken(tokenToSave);
		user = tokenToSave.split(":")[0];
		token = tokenToSave;
	}
	
	public void clearToken() {
		token = "";
		writeClearAccessToken();
	}

	public void saveEmailId(String emailToSave) {
		writeEmailId(emailToSave);
		email = emailToSave;
	}
	
	public void clearEmailId() {
		writeClearUserId();
		email = "";
	}

	
	public void saveGcmRegId(String gcmRegIdToSave) {
		writeGcmRegId(gcmRegIdToSave);
		gcmRegId = gcmRegIdToSave;
	}
	
	public void saveMapBounds(LatLngBounds bounds) {
		writeMapBounds(bounds);
		mapBounds = bounds;
	}
	
	public void saveCenterOnPosition(int position) {
		writeCenterOnPosition(position);
		centerOnPosition = position;
	}
	
	public void saveCenterOnMode(int mode) {
		writeCenterOnMode(mode);
		centerOnMode = mode;
	}
	
	public void clearGcmRegId() {
		writeClearGcmRegId();
		gcmRegId = "";
	}


//	public PersistData getPersistData() {
//		return persistData;
//	}
              
	protected String getUserFromToken() {
		return token.split(":")[0];
	}

	
	

}
