package com.gmail.npnster.first_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class GetFailedDialog  {
	
	Context mContext;
	
	public GetFailedDialog(Context context) {
		mContext = context;
	}
	
	public void show(boolean networkError) {
		String reason = networkError ? "your internet connection is down" : "there was some sort of server error"; 
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage(String.format("Sorry, we can't display the content right now it looks like %s", reason))
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   public void onClick(DialogInterface dialog, int id) {
                   }
               });
        builder.show();
	}
	
   
}
