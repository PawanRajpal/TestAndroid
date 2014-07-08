package com.ma.plans;



import java.util.ArrayList;

import com.ma.bean.Keyword;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class TabsPagerAdapterPlan extends FragmentStatePagerAdapter {

	PlanFragment planFragment;
	
	Bundle _bundle;
	ArrayList<Keyword> keywords;
	Fragment screens[];
	public TabsPagerAdapterPlan(FragmentManager fm,ArrayList<Keyword> arrayList) {
		super(fm);
		this.keywords=arrayList;
		//this._bundle = bundle;
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			// Top Rated fragment activity
			planFragment = new PlanFragment();
			_bundle=new Bundle();
			System.out.println("is dis**********0**********************"+keywords.get(index).getKeywordID());
			_bundle.putInt("packageID", keywords.get(index).getKeywordID());
			planFragment.setArguments(_bundle);
			return planFragment;
		case 1:
			// Games fragment activity
			_bundle=new Bundle();
			_bundle.putInt("packageID", keywords.get(index).getKeywordID());
			System.out.println("is dis*************1*******************"+keywords.get(index).getKeywordID());
		
			planFragment = new PlanFragment();
			planFragment.setArguments(_bundle);
			return planFragment;
		case 2:
			// Games fragment activity
			_bundle=new Bundle();
			_bundle.putInt("packageID", keywords.get(index).getKeywordID());
			System.out.println("is dis*****2***************************"+keywords.get(index).getKeywordID());
			
			planFragment = new PlanFragment();
			planFragment.setArguments(_bundle);
			return planFragment;
		case 3:
			// Games fragment activity
			_bundle=new Bundle();
			_bundle.putInt("packageID", 3);
			//System.out.println("is dis*****************3***************"+keywords.get(index).getKeywordID());
			
			planFragment = new PlanFragment();
			planFragment.setArguments(_bundle);
			return planFragment;
		}
		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 4;
	}

}
