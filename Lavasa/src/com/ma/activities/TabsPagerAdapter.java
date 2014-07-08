package com.ma.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsPagerAdapter extends FragmentStatePagerAdapter {

	PlacesListFragment itemsFragment;
	Bundle bundle;
	
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			itemsFragment = new PlacesListFragment();
			bundle = new Bundle();
			bundle.putString("Category", "food");
			itemsFragment.setArguments(bundle);
			return itemsFragment;
		case 1:
			// Games fragment activity
			itemsFragment = new PlacesListFragment();
			bundle = new Bundle();
			bundle.putString("Category", "atm");
			itemsFragment.setArguments(bundle);
			return itemsFragment;
		case 2:
			// Games fragment activity
			itemsFragment = new PlacesListFragment();
			bundle = new Bundle();
			bundle.putString("Category", "lodging");
			itemsFragment.setArguments(bundle);
			return itemsFragment;
		case 3:
			// Games fragment activity
			itemsFragment = new PlacesListFragment();
			bundle = new Bundle();
			bundle.putString("Category", "all");
			itemsFragment.setArguments(bundle);
			return itemsFragment;
		}
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
