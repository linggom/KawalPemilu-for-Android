package com.antares.kawalpemilu;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.antares.kawalpemilu.model.CompileResult;
import com.antares.kawalpemilu.tps.ViewTPSActivity;
import com.antares.kawalpemilu.util.Style;
import com.antares.kawalpemilu.util.Style.Font.FontType;

public class RekapPerCityFragment extends Fragment {


	private TextView textCandidateName1;
	private TextView textPercentageCandidate1;
	private TextView textCountCandidate1;

	private TextView textCandidateName2;
	private TextView textPercentageCandidate2;
	private TextView textCountCandidate2;

	private TextView textValidCount;
	private TextView textInvalidCount;
	private TextView textErrorCount;
	private TextView textEnteredCount;
	private TextView textAvailableAndTotal;
	private Activity mActivity;
	private CompileResult res;
	private Button btnMasuk;
	private int depth;
	private String [] data;
	private String title;
	public static final String REKAP = "rekap";

	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		this.mActivity = activity;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		data = getArguments().getStringArray(REKAP);
		depth = getArguments().getInt(MainActivity.DEPTH, 0);
		title = getArguments().getString(MainActivity.TITLE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_fragment_rekap, container, false);
		textCandidateName1 = (TextView) view.findViewById(R.id.textCandidateName1);
		textCandidateName2 = (TextView) view.findViewById(R.id.textCandidateName2);
		textCountCandidate1 = (TextView) view.findViewById(R.id.textCountCandidate1);
		textCountCandidate2 = (TextView) view.findViewById(R.id.textCountCandidate2);

		textPercentageCandidate1 = (TextView) view.findViewById(R.id.textPercentageCandidate1);
		textPercentageCandidate2 = (TextView) view.findViewById(R.id.textPercentageCandidate2);

		textValidCount = (TextView) view.findViewById(R.id.textValidCount);
		textInvalidCount = (TextView) view.findViewById(R.id.textInvalidCount);
		textErrorCount = (TextView) view.findViewById(R.id.textErrorCount);
		textEnteredCount = (TextView) view.findViewById(R.id.textEnteredCount);
		textAvailableAndTotal = (TextView) view.findViewById(R.id.textAvailableAndTotal);
		btnMasuk = (Button) view.findViewById(R.id.btnIn);
		Style.applyTypeFace(mActivity, FontType.RobotoLight, textCountCandidate1, textCountCandidate2, textValidCount, textInvalidCount, textErrorCount, textEnteredCount, textAvailableAndTotal);

		
		applyData();
		
		return view;
	}
	
	

	
	public void applyData(){
		
		if (data != null){
			
			res = new CompileResult(data);
			if(res.getId().toString().equals("-29081990")){
				btnMasuk.setVisibility(View.GONE);
			}
			else{
				btnMasuk.setVisibility(View.VISIBLE);
			}
			
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
			double result = ((double)res.getPending() / res.getTotal())*100;
			angleFormated = df.format(result);
			textAvailableAndTotal.setText(""+String.format("%,d", res.getPending()) +"/" + String.format("%,d", res.getTotal()) + "("+angleFormated+"%)");
			btnMasuk.setOnClickListener(mInside);
		}
	}

	
	OnClickListener mInside = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (depth == 3){
				
//				Toast.makeText(mActivity, "Will provide detail for TPS", Toast.LENGTH_LONG).show();
				Intent i = new Intent(mActivity, ViewTPSActivity.class);
				i.putExtra(MainActivity.CHILD, res.getId() );
				i.putExtra(MainActivity.DEPTH, depth +1 );
				i.putExtra(MainActivity.TITLE, title);
				startActivity(i);
			}
			else{
				Intent i = new Intent(mActivity, MainActivity.class);
				i.putExtra(MainActivity.CHILD, res.getId() );
				i.putExtra(MainActivity.DEPTH, depth +1 );
				i.putExtra(MainActivity.TITLE, title);
				startActivity(i);
			}
		}
	};




}


