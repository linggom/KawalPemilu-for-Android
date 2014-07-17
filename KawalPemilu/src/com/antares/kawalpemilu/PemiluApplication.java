package com.antares.kawalpemilu;

import android.app.Application;

public class PemiluApplication extends Application {

	private static Application mApps;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		this.mApps = this;
	}
	
	public static Application getApplication(){
		return mApps;
	}
}
