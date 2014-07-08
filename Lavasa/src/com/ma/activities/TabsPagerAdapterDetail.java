package com.ma.activities;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsPagerAdapterDetail extends FragmentStatePagerAdapter {

	PlacesListFragment itemsFragment;
	PlaceDetailFragment placeDetailFragment;
	PlaceGalleryFragment placeGalleryFragment;
	PlaceMapFragment placeMapFragment;
	PlaceReviewFragment placeReviewFragment;
	Bundle _bundle;

	Fragment screens[];
	public TabsPagerAdapterDetail(FragmentManager fm, Bundle bundle) {
		super(fm);
		this._bundle = bundle;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			placeDetailFragment = new PlaceDetailFragment();
			placeDetailFragment.setArguments(_bundle);
			return placeDetailFragment;
		case 1:
			// Games fragment activity
			placeGalleryFragment = new PlaceGalleryFragment();
			placeGalleryFragment.setArguments(_bundle);
			return placeGalleryFragment;
		case 2:
			// Games fragment activity
			placeReviewFragment = new PlaceReviewFragment();
			placeReviewFragment.setArguments(_bundle);
			return placeReviewFragment;
		case 3:
			// Games fragment activity
			placeMapFragment = new PlaceMapFragment();
			placeMapFragment.setArguments(_bundle);
			return placeMapFragment;
		}
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
