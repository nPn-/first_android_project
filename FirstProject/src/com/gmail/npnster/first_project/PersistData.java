package com.gmail.npnster.first_project;

import android.content.Context;
import android.content.SharedPreferences;

public class PersistData {
	
	private static final String GENERAL_SETTINGS_FILE_NAME = "com.gmail.npnster.fist_project.GENERAL_SETTINGS_FILE";
	
	private Context context;
	private SharedPreferences generalSettingsFile;
	private SharedPreferences.Editor generalSettingsFileEditor;
	
	public PersistData(Context context) {
		this.context = context;
		generalSettingsFile = context.getSharedPreferences(GENERAL_SETTINGS_FILE_NAME, Context.MODE_PRIVATE );
		generalSettingsFileEditor = generalSettingsFile.edit();
		
	}
	
	public void saveAccessToken(String accessToken) {
		generalSettingsFileEditor.putString("ACCESS_TOKEN", accessToken);
		generalSettingsFileEditor.commit();
	}

	public String readAccessToken() {
		String token = generalSettingsFile.getString("ACCESS_TOKEN","");
		return(token);
	}
	
	public void clearAccessToken() {
		generalSettingsFileEditor.putString("ACCESS_TOKEN", "");
		generalSettingsFileEditor.commit();
	}

	
	public void saveUserId(String accessToken) {
		generalSettingsFileEditor.putString("USER_ID", accessToken);
		generalSettingsFileEditor.commit();
	}

	public String readUserId() {
		String token = generalSettingsFile.getString("USER_ID","");
		return(token);
	}
	
	public void clearUserId() {
		generalSettingsFileEditor.putString("USER_ID", "");
		generalSettingsFileEditor.commit();
	}

}
