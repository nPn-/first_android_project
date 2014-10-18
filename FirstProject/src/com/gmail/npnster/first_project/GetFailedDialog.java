package com.gmail.npnster.first_project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class GetFailedDialog {

	Context mContext;
	AlertDialog dialog = null;
	AlertDialog.Builder builder;
	String currentMessage = null;

	public GetFailedDialog(Context context) {
		mContext = context;
	}

	public void show(boolean networkError) {

		String reason = networkError ? "your internet connection is down" : "there was some sort of server error";
		String newMessage = String.format("Sorry, we can't display the content right now it looks like %s, please try again later", reason);
		if (dialog == null) {
			builder = new AlertDialog.Builder(mContext);
					builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
						}
					});
			dialog = builder.create();
		} 
		dialog.setMessage(newMessage);
		if ( !dialog.isShowing() || (currentMessage != null && !newMessage.equals(currentMessage) )) {
			dialog.show();
		}
		currentMessage = newMessage;
	}
}
