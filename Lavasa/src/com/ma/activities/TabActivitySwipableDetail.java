package com.ma.activities;

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
public class TabActivitySwipableDetail extends Fragment implements TabListener{

	View targetView;
	int positions;
	Button btnDetail, btnGallery, btnReviews, btnMap;
	private ViewPager viewPager;
	private TabsPagerAdapterDetail mAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		targetView = inflater.inflate(R.layout.tab_layout_swipable_detail, container, false);

		btnDetail = (Button) targetView.findViewById(R.id.btn_detail);
		btnDetail.setBackgroundResource(R.drawable.info_button_bg);
		btnGallery = (Button) targetView.findViewById(R.id.btn_gallery);
		btnGallery.setBackgroundResource(R.drawable.info_button_bg_onchange);
		btnReviews = (Button) targetView.findViewById(R.id.btn_reviews);
		btnReviews.setBackgroundResource(R.drawable.info_button_bg_onchange);
		btnMap = (Button) targetView.findViewById(R.id.btn_map);
		btnMap.setBackgroundResource(R.drawable.info_button_bg_onchange);

		// Initialization
		viewPager = (ViewPager) targetView.findViewById(R.id.pager_detail);

		Bundle bundle = this.getArguments();

		mAdapter = new TabsPagerAdapterDetail(this.getChildFragmentManager(), bundle);

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
					btnDetail.setBackgroundResource(R.drawable.info_button_bg);
					btnGallery.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnReviews.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnMap.setBackgroundResource(R.drawable.info_button_bg_onchange);
					break;
				case 1:
					btnDetail.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnGallery.setBackgroundResource(R.drawable.info_button_bg);
					btnReviews.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnMap.setBackgroundResource(R.drawable.info_button_bg_onchange);
					break;
				case 2:
					btnDetail.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnGallery.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnReviews.setBackgroundResource(R.drawable.info_button_bg);
					btnMap.setBackgroundResource(R.drawable.info_button_bg_onchange);
					break;
				case 3:
					btnDetail.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnGallery.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnReviews.setBackgroundResource(R.drawable.info_button_bg_onchange);
					btnMap.setBackgroundResource(R.drawable.info_button_bg);
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

		btnDetail.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				viewPager.setCurrentItem(0, true);

			}
		});
		btnGallery.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				viewPager.setCurrentItem(1, true);
			}
		});
		btnReviews.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View arg0)
			{
				viewPager.setCurrentItem(2, true);
			}
		});
		btnMap.setOnClickListener(new OnClickListener()
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