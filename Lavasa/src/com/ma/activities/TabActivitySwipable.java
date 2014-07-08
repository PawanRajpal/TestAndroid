package com.ma.activities;

import java.util.ArrayList;

import com.ma.bean.Keyword;
import com.ma.database.LavasaDatabase;
import com.ma.lavasa.R;

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

@SuppressLint("NewApi")
public class TabActivitySwipable extends Fragment implements TabListener{

	View targetView;
	int positions;

	Button btnFood, btnAtm,btnBusSation,btnAll;
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	ArrayList<Keyword> keywords;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		targetView = inflater.inflate(R.layout.tab_layout_swipable, container, false);

		btnFood = (Button) targetView.findViewById(R.id.food);
		btnFood.setBackgroundResource(R.drawable.info_button_bg);
		btnAtm = (Button) targetView.findViewById(R.id.atm);
		btnAtm.setBackgroundResource(R.drawable.info_button_bg_onchange);
		btnBusSation = (Button) targetView.findViewById(R.id.bus_station);
		btnBusSation.setBackgroundResource(R.drawable.info_button_bg_onchange);
		btnAll = (Button) targetView.findViewById(R.id.all);
		btnAll.setBackgroundResource(R.drawable.info_button_bg_onchange);

		// Initialization
		viewPager = (ViewPager) targetView.findViewById(R.id.pager);

		mAdapter = new TabsPagerAdapter(this.getChildFragmentManager());
		
		keywords = LavasaDatabase.getInstance().getAttractionKeywords();
		
		btnFood.setText(keywords.get(0).getKeyword());
		btnAtm.setText(keywords.get(1).getKeyword());
		btnBusSation.setText(keywords.get(2).getKeyword());

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
				positions=position;

				switch (position) {
				case 0:
					btnFood.setBackgroundResource(R.drawable.info_button_bg);
					btnAtm.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnBusSation.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnAll.setBackgroundResource(R.drawable.info_button_bg_onchange);
					break;
				case 1:
					btnFood.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnAtm.setBackgroundResource(R.drawable.info_button_bg);
					btnBusSation.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnAll.setBackgroundResource(R.drawable.info_button_bg_onchange);
					break;
				case 2:
					btnFood.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnAtm.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnBusSation.setBackgroundResource(R.drawable.info_button_bg);
					btnAll.setBackgroundResource(R.drawable.info_button_bg_onchange);
					break;
				case 3:
					btnFood.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnAtm.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnBusSation.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnAll.setBackgroundResource(R.drawable.info_button_bg);
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

		btnFood.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				viewPager.setCurrentItem(0, true);

			}
		});
		btnAtm.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				viewPager.setCurrentItem(1, true);
			}
		});
		btnBusSation.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				viewPager.setCurrentItem(2, true);
			}
		});
		btnAll.setOnClickListener(new OnClickListener()
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