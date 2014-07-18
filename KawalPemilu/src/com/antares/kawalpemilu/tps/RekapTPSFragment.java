package com.antares.kawalpemilu.tps;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.antares.kawalpemilu.MainActivity;
import com.antares.kawalpemilu.R;
import com.antares.kawalpemilu.model.CompileResult;
import com.antares.kawalpemilu.model.DetailTPSModel;
import com.antares.kawalpemilu.util.Style;
import com.antares.kawalpemilu.util.Style.Font.FontType;

public class RekapTPSFragment extends Fragment {


	private TextView textCandidateName1;
	private TextView textPercentageCandidate1;
	private TextView textCountCandidate1;

	private TextView textCandidateName2;
	private TextView textPercentageCandidate2;
	private TextView textCountCandidate2;

	private TextView textValidCount;
	private TextView textInvalidCount;
	
	private Activity mActivity;
	private DetailTPSModel res;
	private int depth;
	private int parent;
	private String [] data;
	public static final String REKAP = "rekap";
	private ImageView imageScan;
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
		parent= getArguments().getInt(MainActivity.CHILD, 0);
		depth = getArguments().getInt(MainActivity.DEPTH, 0);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.layout_fragment_tps, container, false);
		textCandidateName1 = (TextView) view.findViewById(R.id.textCandidateName1);
		textCandidateName2 = (TextView) view.findViewById(R.id.textCandidateName2);
		textCountCandidate1 = (TextView) view.findViewById(R.id.textCountCandidate1);
		textCountCandidate2 = (TextView) view.findViewById(R.id.textCountCandidate2);

		textPercentageCandidate1 = (TextView) view.findViewById(R.id.textPercentageCandidate1);
		textPercentageCandidate2 = (TextView) view.findViewById(R.id.textPercentageCandidate2);

		textValidCount = (TextView) view.findViewById(R.id.textValidCount);
		textInvalidCount = (TextView) view.findViewById(R.id.textInvalidCount);
		imageScan = (ImageView)view.findViewById(R.id.imageScan);
		imageScan.setVisibility(View.GONE);
		Style.applyTypeFace(mActivity, FontType.RobotoLight, textCountCandidate1, textCountCandidate2);
//		imageScan.setImageUrl("http://scanc1.kpu.go.id/viewp.php?f=000000400501.jpg", VolleySingleton.getInstance().getImageLoader());
//		Picasso.with(mActivity).load("http://scanc1.kpu.go.id/viewp.php?f=000000400501.jpg").fit().centerCrop().placeholder(R.drawable.ic_action_refresh).into(imageScan);
		
		applyData();
		
		return view;
	}
	
	

	
	public void applyData(){
		
		if (data != null){
			
			res = new DetailTPSModel(data);
			int id = CompileResult.toInt(res.getId() ) + 4;
			String url = String.format("http://scanc1.kpu.go.id/viewp.php?f=%07d%03d01.jpg", parent, id);
			textCountCandidate1.setText(String.format("%s", res.getCandidate1()));
			
			textCountCandidate2.setText(String.format("%s", res.getCandidate2()));
			
			textValidCount.setText(""+String.format("%s", res.getValid()));
			textInvalidCount.setText(""+String.format("%s", res.getInvalid()));
		}
	}

	




}


