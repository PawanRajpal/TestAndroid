package com.ma.plans;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.ma.bean.Keyword;
import com.ma.database.LavasaDatabase;
import com.ma.lavasa.R;

@SuppressLint("NewApi")
public class TabActivitySwipablePlan extends Fragment implements TabListener{

	View targetView;
	int positions;
	Button resturants,religious,mountain, all;
	private ViewPager viewPager;
	private TabsPagerAdapterPlan mAdapter;
	ArrayList<Keyword> keywords;
	Bundle bundle;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		targetView = inflater.inflate(R.layout.plan_tab_swiable, container, false);

		resturants = (Button) targetView.findViewById(R.id.plan_restaurent);
		resturants.setBackgroundResource(R.drawable.info_button_bg);
		religious = (Button) targetView.findViewById(R.id.plan_religious);
		religious.setBackgroundResource(R.drawable.info_button_bg_onchange);
		mountain = (Button) targetView.findViewById(R.id.plan_mountain);
		mountain.setBackgroundResource(R.drawable.info_button_bg_onchange);
		all = (Button) targetView.findViewById(R.id.plan_all);
		all.setBackgroundResource(R.drawable.info_button_bg_onchange);
		
		// Initialization
		viewPager = (ViewPager) targetView.findViewById(R.id.plan_pager);
		
		// Get Keywords
		keywords = LavasaDatabase.getInstance().getPlanKeywords();
		
//		bundle = new Bundle();
//		
//		bundle.putInt("packageID", 0);

		mAdapter = new TabsPagerAdapterPlan(this.getChildFragmentManager(), keywords);

		resturants.setText(keywords.get(0).getKeyword());
		religious.setText(keywords.get(1).getKeyword());
		mountain.setText(keywords.get(2).getKeyword());
		
		viewPager.setAdapter(mAdapter);
		viewPager.setCurrentItem(0, true);
		
		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener()
		{
			@Override
			public void onPageSelected(int position)
			{
				// on changing the page
				// make respected tab selected
				// actionBar.setSelectedNavigationItem(position);
				positions = position;

				switch (position) {
				case 0:
					resturants.setBackgroundResource(R.drawable.info_button_bg);
					religious.setBackgroundResource(R.drawable.info_button_bg_onchange);
					mountain.setBackgroundResource(R.drawable.info_button_bg_onchange);
					all.setBackgroundResource(R.drawable.info_button_bg_onchange);
					
					break;
				case 1:
					resturants.setBackgroundResource(R.drawable.info_button_bg_onchange);
					religious.setBackgroundResource(R.drawable.info_button_bg);
					mountain.setBackgroundResource(R.drawable.info_button_bg_onchange);
					all.setBackgroundResource(R.drawable.info_button_bg_onchange);
					break;
				case 2:
					resturants.setBackgroundResource(R.drawable.info_button_bg_onchange);
					religious.setBackgroundResource(R.drawable.info_button_bg_onchange);
					mountain.setBackgroundResource(R.drawable.info_button_bg);
					all.setBackgroundResource(R.drawable.info_button_bg_onchange);
					break;
				case 3:
					resturants.setBackgroundResource(R.drawable.info_button_bg_onchange);
					religious.setBackgroundResource(R.drawable.info_button_bg_onchange);
					mountain.setBackgroundResource(R.drawable.info_button_bg_onchange);
					all.setBackgroundResource(R.drawable.info_button_bg);
					break;
				default:
					break;
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
			}

			@Override
			public void onPageScrollStateChanged(int arg0)
			{
			}
		});

		resturants.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				viewPager.setCurrentItem(0, true);
			}
		});
		religious.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				viewPager.setCurrentItem(1, true);
			}
		});
		mountain.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				viewPager.setCurrentItem(2, true);
			}
		});
		all.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				viewPager.setCurrentItem(3, true);
			}
		});

		return targetView;
	}


	@Override
	public void onTabReselected(Tab arg0, android.app.FragmentTransaction arg1)
	{

	}

	@Override
	public void onTabSelected(Tab arg0, android.app.FragmentTransaction arg1)
	{
		viewPager.setCurrentItem(arg0.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, android.app.FragmentTransaction arg1)
	{

	}

}