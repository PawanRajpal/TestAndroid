package com.ma.activities;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import com.ma.lavasa.R;

public class FullScreenGalleryViewDialogFragment extends DialogFragment{

	//	private Utils utils;
	private FullScreenGalleryAdapter adapter;
	private ViewPager viewPager;
	int position;
	Button btnClose;
	ArrayList<String>imagesurls;
	String fromTravelogue;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		return inflater.inflate(R.layout.fullscreen_view, null);
	}

	@Override
	public void onActivityCreated(Bundle arg0) {
		super.onActivityCreated(arg0);
		viewPager = (ViewPager) getView().findViewById(R.id.pager);
		// close button click event
		btnClose= (Button)getView().findViewById(R.id.btnClose);
		//		utils = new Utils(getActivity());

		//		Intent i = getActivity().getIntent();
		//		int position = i.getIntExtra("position", 0);
		Bundle bndle=getArguments();
		position=bndle.getInt("position");
		imagesurls=bndle.getStringArrayList("imagesurls");
		fromTravelogue=bndle.getString("fromTravelogue");
		adapter = new FullScreenGalleryAdapter(getActivity(), imagesurls,fromTravelogue);

		viewPager.setAdapter(adapter);

		// displaying selected image first
		viewPager.setCurrentItem(position);


		btnClose.setOnClickListener(new View.OnClickListener() {			
			@Override
			public void onClick(View v) {
				getDialog().dismiss();
			}
		}); 
	}}