package com.gmail.npnster.first_project;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;

public class PersistData {
	
	private static final String GENERAL_SETTINGS_FILE_NAME = "com.gmail.npnster.fist_project.GENERAL_SETTINGS_FILE";
	
	private Context context;
	private SharedPreferences generalSettingsFile;
	private SharedPreferences.Editor generalSettingsFileEditor;
	
	// this mainly just hides this method since it should only be called via the application class
	// which also caches a copy of the token 
	protected class Cached {
		public void saveAccessToken(String accessToken) {
			generalSettingsFileEditor.putString("ACCESS_TOKEN", accessToken);
			generalSettingsFileEditor.commit();
		}
		public void saveEmailId(String emailId) {
			generalSettingsFileEditor.putString("USER_ID", emailId);
			generalSettingsFileEditor.commit();
		}
		public void saveGcmRegId(String gcmRegId) {
			generalSettingsFileEditor.putString("GCM_REG_ID", gcmRegId);
			generalSettingsFileEditor.commit();
		}
		public void clearAccessToken() {
			generalSettingsFileEditor.putString("ACCESS_TOKEN", "");
			generalSettingsFileEditor.commit();
		}
		public void clearGcmRegId() {
			generalSettingsFileEditor.putString("GCM_REG_ID", "");
			generalSettingsFileEditor.commit();
		}
		public void clearUserId() {
			generalSettingsFileEditor.putString("USER_ID", "");
			generalSettingsFileEditor.commit();
		}
		
		public void saveMapBounds(LatLngBounds bounds) {
			generalSettingsFileEditor.putFloat("SOUTHWEST_LAT",(float) bounds.southwest.latitude);
			generalSettingsFileEditor.putFloat("SOUTHWEST_LNG",(float) bounds.southwest.longitude);
			generalSettingsFileEditor.putFloat("NORTHEAST_LAT",(float) bounds.northeast.latitude);
			generalSettingsFileEditor.putFloat("NORTHEAST_LNG",(float) bounds.northeast.longitude);
			generalSettingsFileEditor.commit();
		}


		
	}
	
	public PersistData(Context context) {
		this.context = context;
		generalSettingsFile = context.getSharedPreferences(GENERAL_SETTINGS_FILE_NAME, Context.MODE_PRIVATE );
		generalSettingsFileEditor = generalSettingsFile.edit();
		
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
	
	public LatLngBounds getMapBounds() {
		float southwestLat = generalSettingsFile.getFloat("SOUTHWEST_LAT",1.0f);
		float southwestLng = generalSettingsFile.getFloat("SOUTHWEST_LNG",-179f);
		float northeastLat = generalSettingsFile.getFloat("NORTHEAST_LAT",89.0f);
		float northeastLng = generalSettingsFile.getFloat("NORTHEAST_LNG",179f);
        return new LatLngBounds(new LatLng(southwestLat, southwestLng), new LatLng(northeastLat, northeastLng));
	}

	
	

}
