package com.gmail.npnster.first_project;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class RailsErrorsDialog {

	Context mContext;
	AlertDialog dialog = null;
	AlertDialog.Builder builder;
	ArrayList<String> mErrors = null;

	public RailsErrorsDialog(Context context, ArrayList<String> errors) {
		mContext = context;
		mErrors = errors;
		String errorOrErrors = errors.size() > 1 ? "errors" : "error" ;
		CharSequence[] cs = mErrors.toArray(new CharSequence[mErrors.size()]);
		System.out.println(String.format("showing %d errors based on %d input errors", cs.length,errors.size()));
		builder = new AlertDialog.Builder(mContext);
		builder.setTitle(String.format("The form had the following %s", errorOrErrors))
		       .setItems(cs, null)
		       .setPositiveButton("OK", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int id) {
			}
		});
		dialog = builder.create();
		dialog.show();
	}
}
