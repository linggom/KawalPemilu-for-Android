package com.antares.kawalpemilu;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.antares.kawalpemilu.client.RestClient;
import com.antares.kawalpemilu.client.VoteService;
import com.antares.kawalpemilu.model.CompileResult;
import com.astuetz.viewpager.extensions.PagerSlidingTabStrip;

public class MainActivity extends ActionBarActivity {




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
	private Menu menu;
	public static final String CHILD = "child";
	public static final String DEPTH = "depth";
	public static final String PARENT = "parent";
	public static final String TITLE = "title";
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

		child = getIntent().getIntExtra(CHILD, 0);
		depth = getIntent().getIntExtra(DEPTH, 0);
		title = getIntent().getStringExtra(TITLE);
		if (depth > 0){
			mActionBar.setDisplayHomeAsUpEnabled(true);
			mActionBar.setDisplayShowHomeEnabled(true);
		}
		if (title == null || title.trim().isEmpty()){
			mActionBar.setTitle("SCAN C1 Nasional");
		}
		else{
			mActionBar.setTitle(title);
		}
		stateLoad();




		mService = RestClient.getVoteService();
		mService.getHie(""+child, resultData);
	}

	OnClickListener mTryClick = new  OnClickListener() {

		@Override
		public void onClick(View v) {
			mService = RestClient.getVoteService();
			mService.getHie(""+child, resultData);
		}
	};


	public boolean onCreateOptionsMenu(android.view.Menu menu) {		
		getMenuInflater().inflate(R.menu.main, menu);
		this.menu = menu;
		return super.onCreateOptionsMenu(menu);
	};
	

	public boolean onOptionsItemSelected(android.view.MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		case R.id.action_refresh:
			stateLoad();
			mService.getHie(""+child, resultData);
			item.setEnabled(false);
			break;
		default:
			break;
		}
		return false;
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
			menu.getItem(0).setEnabled(true);
			if (r == null ){
				stateFailed();
			}
			else if (r.length == 0){
				stateFailed();
			}
			else{

				String [] a  = new String[]{"0","Total Rekapitulasi","0","0","0","0","0","0","0","0"};
				for (int i = 0; i < r.length; i++) {
					int x = CompileResult.toInt(r[i][2]);
					a[0] = "-29081990";
					a[1] = "Total Rekapitulasi";
					a[2]= ""+(CompileResult.toInt(a[2]) + x) ;



					x = CompileResult.toInt(r[i][3]);
					a[3]= ""+ (CompileResult.toInt(a[3]) + x) ;;

					x = CompileResult.toInt(r[i][4]);
					a[4]= ""+ (CompileResult.toInt(a[4]) + x) ;;

					x = CompileResult.toInt(r[i][5]);
					a[5]= ""+ (CompileResult.toInt(a[5]) + x) ;;

					x = CompileResult.toInt(r[i][6]);
					a[6]= ""+ (CompileResult.toInt(a[6]) + x) ;;

					x = CompileResult.toInt(r[i][7]);
					a[7]= ""+ (CompileResult.toInt(a[7]) + x) ;;

					x = CompileResult.toInt(r[i][8]);
					a[8]= ""+ (CompileResult.toInt(a[8]) + x) ;;

					x = CompileResult.toInt(r[i][9]);
					a[9]= ""+ (CompileResult.toInt(a[9]) + x) ;;
				}
				String [][]newData = new String[r.length +1][20];
				for (int j = 0; j < r.length+1; j++) {

					if (j == 0){
						for (int j2 = 0; j2 < a.length; j2++) {
							newData[j][j2] = a[j2];
						}
					}
					else{
						for (int j2 = 0; j2 < r[j-1].length; j2++) {
							newData[j][j2] = r[j-1][j2];
						}
					}
				}

				mAdapter.setData(newData, depth);
				mPager.setAdapter(mAdapter);
				mTabStrip.setViewPager(mPager);
				stateSuccess();

				resData = newData;
			}

		}

	};


	
	

}


