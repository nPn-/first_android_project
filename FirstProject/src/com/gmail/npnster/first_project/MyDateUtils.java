package com.gmail.npnster.first_project;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.text.format.DateUtils;

public class MyDateUtils {
	
	public static String timeAgo(String iso8601) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		ParsePosition position = new ParsePosition(0);
		Date date = null;
		try {
			date = simpleDateFormat.parse(iso8601.replaceFirst("Z", "+00:00"), position);
		} catch ( Exception e ) {
			System.out.println("could not parese date");
		
		}
		String timeAgo = date == null ?  "" : DateUtils.getRelativeTimeSpanString(date.getTime()).toString();
		return timeAgo;
	}

}
