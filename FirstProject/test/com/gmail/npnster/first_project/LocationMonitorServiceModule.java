package com.gmail.npnster.first_project;

import javax.inject.Named;
import javax.inject.Singleton;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import dagger.Module;
import dagger.Provides;
import static org.mockito.Mockito.mock;

@Module(
		injects = { 
				LocationMonitorServiceTest.class,
				LocationMonitorService.class,
		},
		library = true,
		addsTo =  ApplicationModule.class,
		complete = false
//		includes = {  ApplicationModule.class, AndroidModule.class }
		)
public class LocationMonitorServiceModule {
	
	private Context mContext;
	private Intent mIntent;
	
	LocationMonitorServiceModule(Context context, Intent intent) {
		mContext = context;
		mIntent = intent;
		System.out.println("mock location monitor service module");
		
	}
	
	@Provides @Singleton PendingIntent provideGcmKeepAlivePendingIntent() {
		System.out.println("pending intent provider");
//		return PendingIntent.getBroadcast(mContext, 0, mIntent, PendingIntent.FLAG_CANCEL_CURRENT);
		return mock(PendingIntent.class);
	}
	
	@Provides  @Singleton AlarmManager provideGcmKeepAliveAlarmManager() {
//		return (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
		return mock(AlarmManager.class);
	}
	 
//  @Provides @NamedProvider("gcmKeepAliveIntent") Intent provideGcmKeepAliveIntent() {
//    return new Intent("com.gmail.npnster.first_project.gcmKeepAlive");
//  }


}
