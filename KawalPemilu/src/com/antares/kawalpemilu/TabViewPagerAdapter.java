package com.antares.kawalpemilu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.antares.kawalpemilu.tps.RekapTPSFragment;

public class TabViewPagerAdapter extends FragmentPagerAdapter {

	private String[][] RESULTDATA = null;
	private int depth = 0;
	private boolean tps = false;
	private int child = 0;
	
	public TabViewPagerAdapter(FragmentManager fm, String [][] data) {
		super(fm);
		this.RESULTDATA = data;
	}
	
	public void setData(String [][] data, int depth){
		this.RESULTDATA = data;
		this.depth = depth;
		notifyDataSetChanged();
	}
	public void setData(String [][] data, int depth, int child ,boolean tps){
		this.RESULTDATA = data;
		this.depth = depth;
		this.tps = tps;
		this.child = child;
		notifyDataSetChanged();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return (RESULTDATA == null) ? "":(tps)?"TPS #"+RESULTDATA[position][0]:""+RESULTDATA[position][1];
	}

	@Override
	public int getCount() {
		return (null == RESULTDATA) ?  0 : RESULTDATA.length;
	}


	@Override
	public Fragment getItem(int position) {
		Fragment f = null;
		if (tps){
			f = new RekapTPSFragment();
			Bundle data = new Bundle();
			data.putStringArray(RekapPerCityFragment.REKAP, RESULTDATA[position]);
			data.putInt(MainActivity.DEPTH, depth);
			data.putString(MainActivity.TITLE,RESULTDATA[position][1] );
			data.putInt(MainActivity.CHILD, child);
			f.setArguments(data);
		}
		else{
			f = new RekapPerCityFragment();
			Bundle data = new Bundle();
			data.putStringArray(RekapPerCityFragment.REKAP, RESULTDATA[position]);
			data.putInt(MainActivity.DEPTH, depth);
			data.putString(MainActivity.TITLE,RESULTDATA[position][1] );
			f.setArguments(data);
		}
		return f;
	}

}