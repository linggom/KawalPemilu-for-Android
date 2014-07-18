package com.antares.kawalpemilu;

import com.antares.kawalpemilu.client.AlarmReceiver;

import android.app.Application;

public class PemiluApplication extends Application {

	private static Application mApps;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.mApps = this;
		AlarmReceiver a = new AlarmReceiver();
		a.setAlarm(this);
	}
	
	public static Application getApplication(){
		return mApps;
	}
}
