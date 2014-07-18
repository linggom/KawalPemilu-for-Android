package com.antares.kawalpemilu.tps;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.antares.kawalpemilu.MainActivity;
import com.antares.kawalpemilu.R;
import com.antares.kawalpemilu.TabViewPagerAdapter;
import com.antares.kawalpemilu.client.RestClient;
import com.antares.kawalpemilu.client.VoteService;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

public class ViewTPSActivity extends ActionBarActivity {




	private LinearLayout layoutLoading;
	private TextView textProgress;
	private Button btnTry;
	private ProgressBar progressLoad;
	private VoteService mService;
	private PagerSlidingTabStrip mTabStrip;
	private ViewPager mPager;
	private String [][] resData;
	private TabViewPagerAdapter mAdapter;
	private int child = 0;
	private int depth = 0;
	private String title = "";
	private ActionBar mActionBar;

	
	public void stateLoad(){
		mTabStrip.setVisibility(View.GONE);
		mPager.setVisibility(View.GONE);
		layoutLoading.setVisibility(View.VISIBLE);
		textProgress.setVisibility(View.VISIBLE);
		textProgress.setText("Loading....");
		btnTry.setVisibility(View.GONE);
		progressLoad.setVisibility(View.VISIBLE);
	}

	public void stateFailed(){
		mTabStrip.setVisibility(View.GONE);
		mPager.setVisibility(View.GONE);
		layoutLoading.setVisibility(View.VISIBLE);
		textProgress.setVisibility(View.VISIBLE);
		textProgress.setText("Failed....");
		btnTry.setVisibility(View.VISIBLE);
		progressLoad.setVisibility(View.GONE);
	}

	public void stateSuccess(){
		mTabStrip.setVisibility(View.VISIBLE);
		mPager.setVisibility(View.VISIBLE);
		layoutLoading.setVisibility(View.GONE);
		textProgress.setVisibility(View.GONE);
		textProgress.setText("Failed....");
		btnTry.setVisibility(View.GONE);
		progressLoad.setVisibility(View.GONE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mActionBar = getSupportActionBar();
		layoutLoading = (LinearLayout) findViewById(R.id.layoutLoading);
		textProgress = (TextView) findViewById(R.id.textProgress);
		progressLoad = (ProgressBar) findViewById(R.id.progressLoad);
		mTabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabsProvinsi);
		btnTry  = (Button) findViewById(R.id.btnTry);
		mPager = (ViewPager) findViewById(R.id.pagerRekap);
		mAdapter = new TabViewPagerAdapter(getSupportFragmentManager(), null);
		mPager.setAdapter(mAdapter);
		mPager.setOffscreenPageLimit(3);

		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		mPager.setPageMargin(pageMargin);

		child = getIntent().getIntExtra(MainActivity.CHILD, 0);
		depth = getIntent().getIntExtra(MainActivity.DEPTH, 0);
		title = getIntent().getStringExtra(MainActivity.TITLE);
		if (title == null || title.trim().isEmpty()){
			mActionBar.setTitle("SCAN C1");
		}
		else{
			mActionBar.setTitle(title);
		}
		stateLoad();

			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setDisplayShowHomeEnabled(true);



		mService = RestClient.getVoteService();
		mService.getTPS(""+child, resultData);
	}

	OnClickListener mTryClick = new  OnClickListener() {

		@Override
		public void onClick(View v) {
			mService = RestClient.getVoteService();
			mService.getTPS(""+child, resultData);
		}
	};

	private Callback<String[][]> resultData = new Callback<String[][]>() {

		@Override
		public void failure(RetrofitError arg0) {
			// TODO Auto-generated method stub
			stateFailed();
		}

		@Override
		public void success(String[][] r, Response arg1) {
			// TODO Auto-generated method stub
			if (r == null ){
				stateFailed();
			}
			else if (r.length == 0){
				stateFailed();
			}
			else{
				
				
				mAdapter.setData(r, depth, child, true);
				mPager.setAdapter(mAdapter);
				mTabStrip.setViewPager(mPager);
				stateSuccess();
				
				resData = r;
			}

		}

	};
	
	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;

		default:
			break;
		}
		return false;
	};



}


