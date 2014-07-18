package com.antares.kawalpemilu;

import java.text.DecimalFormat;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.antares.kawalpemilu.client.RestClient;
import com.antares.kawalpemilu.client.VoteService;
import com.antares.kawalpemilu.model.CompileResult;
import com.google.gson.Gson;

public class DetailProvinceActivity extends ActionBarActivity {


	private TextView textPercentageCandidate1;
	private TextView textCountCandidate1;

	private TextView textPercentageCandidate2;
	private TextView textCountCandidate2;

	private TextView textValidCount;
	private TextView textInvalidCount;
	private TextView textErrorCount;
	private TextView textEnteredCount;
	private TextView textAvailableAndTotal;


	private VoteService mService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_fragment_rekap);

		textCountCandidate1 = (TextView) findViewById(R.id.textCountCandidate1);
		textCountCandidate2 = (TextView) findViewById(R.id.textCountCandidate2);

		textPercentageCandidate1 = (TextView) findViewById(R.id.textPercentageCandidate1);
		textPercentageCandidate2 = (TextView) findViewById(R.id.textPercentageCandidate2);

		textValidCount = (TextView) findViewById(R.id.textValidCount);
		textInvalidCount = (TextView) findViewById(R.id.textInvalidCount);
		textErrorCount = (TextView) findViewById(R.id.textErrorCount);
		textEnteredCount = (TextView) findViewById(R.id.textEnteredCount);
		textAvailableAndTotal = (TextView) findViewById(R.id.textAvailableAndTotal);

		mService = RestClient.getVoteService();
		mService.getHie("0", resultData);
	}

	private Callback<String[][]> resultData = new Callback<String[][]>() {

		@Override
		public void failure(RetrofitError arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void success(String[][] r, Response arg1) {
			// TODO Auto-generated method stub
			CompileResult res = new CompileResult(r[0]);
			textCountCandidate1.setText(String.format("%,d", res.getResultCandidate1()));
			
			textCountCandidate2.setText(String.format("%,d", res.getResultCandidate2()));
			double total = res.getResultCandidate1() + res.getResultCandidate2();
			double p1 =  res.getResultCandidate1()/total * 100 ; 
			double p2 = 100 - p1;
			DecimalFormat df = new DecimalFormat("#.00");
			String angleFormated = df.format(p1);
			textPercentageCandidate1.setText(angleFormated+"%");
			
			angleFormated = df.format(p2);
			textPercentageCandidate2.setText(angleFormated +"%");
			
			if (p1 < p2){
				textPercentageCandidate2.setBackgroundColor(Color.parseColor("#2ecc71"));
				textPercentageCandidate2.setTextColor(Color.WHITE);
				textPercentageCandidate1.setBackgroundColor(Color.TRANSPARENT);
				textPercentageCandidate1.setTextColor(Color.BLACK);
			}
			else{
				textPercentageCandidate1.setBackgroundColor(Color.parseColor("#2ecc71"));
				textPercentageCandidate1.setTextColor(Color.WHITE);
				textPercentageCandidate2.setBackgroundColor(Color.TRANSPARENT);
				textPercentageCandidate2.setTextColor(Color.BLACK);
			}

			textValidCount.setText(""+String.format("%,d", res.getValidCount()));
			textInvalidCount.setText(""+String.format("%,d", res.getInvalidCount()));
			textErrorCount.setText(""+String.format("%,d", res.getErrorTPS()));
			textEnteredCount.setText(""+String.format("%,d", res.getEnteredCount()));
			textAvailableAndTotal.setText(""+String.format("%,d", res.getPending()) +"/" + String.format("%,d", res.getTotal()));
		}

	};



	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_refresh) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}


