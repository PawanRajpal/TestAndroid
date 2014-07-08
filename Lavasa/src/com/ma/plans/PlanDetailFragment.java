package com.ma.plans;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ma.activities.PlaceMapFragment;
import com.ma.bean.PlanDetailItenerary;
import com.ma.bean.PlanDetailMain;
import com.ma.database.LavasaDatabase;
import com.ma.lavasa.R;
import com.ma.lavasa.SharedPref;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PlanDetailFragment extends Fragment{
	
	PlanDetailMain planDetailMain = null;
	Bundle bundle;
	Button buttonBookNow, buttonMap;
	FragmentManager fragmentManager;
	Fragment fragment = null;
	ImageView imageView;
	TextView textViewPackageName, textViewPackageDuration;
	ListView plandetaillistiteneraryView;
	ArrayList<PlanDetailItenerary> planDetailList;
	PlanDetailFragmentAdapter detailFragmentAdapter;
	ArrayList<Integer> placeIdArryalist;
	View view;
	SharedPref pref;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		fragmentManager = getActivity().getSupportFragmentManager();
		
		pref = new SharedPref(getActivity());
		
		imageView = (ImageView) view.findViewById(R.id.plan_detail_image);
		textViewPackageName = (TextView) view.findViewById(R.id.plan_detail_packagename);
		textViewPackageDuration = (TextView) view.findViewById(R.id.plan_detail_duration);
		plandetaillistiteneraryView = (ListView) view.findViewById(R.id.plan_detail_list_itenerary);

		// Find Views
		buttonBookNow = (Button) view.findViewById(R.id.plan_detail_btn_book);
		buttonMap = (Button) view.findViewById(R.id.plan_detail_btn_map);
		
		if(savedInstanceState==null){
		    bundle = this.getArguments();
		    System.out.println("Inside null save instance state*********************");
		}
		else{
			bundle=savedInstanceState.getBundle("planID");
			System.out.println("Inside  save instance state*********************"+bundle.getInt("packageId"));
		}
		
		System.out.println("bundle value is*******************************************************/"+bundle.getInt("packageId"));
		planDetailMain = LavasaDatabase.getInstance().getPlanDetailMain(pref.getPlaceId());
		planDetailList = LavasaDatabase.getInstance().getPlanDetailItenerary(pref.getPlaceId());
		textViewPackageName.setText(planDetailMain.getPlanName());
		ImageLoader.getInstance().displayImage(planDetailMain.getPlanImg(), imageView);
		textViewPackageDuration.setText(planDetailMain.getPlanDuration());
		
		
		detailFragmentAdapter = new PlanDetailFragmentAdapter(planDetailList, getActivity());
		plandetaillistiteneraryView.setAdapter(detailFragmentAdapter);
		placeIdArryalist=new ArrayList<Integer>();
		for(int i= 0; i<planDetailList.size();i++){
			
			placeIdArryalist.add(planDetailList.get(i).getPlaceId());
		}
		

		buttonBookNow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				fragment = new BookingFragment();
				loadFragment(fragment, 1);

			}
		});
		
		
		buttonMap.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				fragment = new PlaceMapFragment();
				bundle.putIntegerArrayList("placeId", placeIdArryalist);
				loadFragment(fragment, 0);
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.plan_detail_fragment, null);
		
		return view;
	}

	private void loadFragment(Fragment fragment, int packageId){
		bundle.putInt("packageId", packageId);
		fragment.setArguments(bundle);
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
		fragmentTransaction.replace(R.id.content_frame, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	// View Holder Class
	public static class ViewHolder {
		private TextView title;
		private TextView address;
		private TextView phone;
		private RatingBar ratingBar;
	}

	// Place detail adapter
	private class PlanDetailFragmentAdapter extends BaseAdapter {

		Context context;
		ViewHolder holder;
		ArrayList<PlanDetailItenerary> _planDetails;

		public PlanDetailFragmentAdapter(ArrayList<PlanDetailItenerary> list, Context context) {
			this._planDetails = list;
			this.context = context;
		}

		@Override
		public int getCount() {
			return _planDetails.size();
		}

		@Override
		public Object getItem(int position) {
			return _planDetails.get(position);
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.plan_itenerary_list_item, parent, false);
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.itenerary_list_title);
				holder.address = (TextView) convertView.findViewById(R.id.itenerary_list_address);
				holder.ratingBar = (RatingBar) convertView.findViewById(R.id.itenerary_list_ratingbar);
				holder.phone = (TextView) convertView.findViewById(R.id.itenerary_list_phone_number);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(_planDetails.get(position).getPlaceName());
			holder.address.setText(_planDetails.get(position).getPlaceAddress());
			holder.phone.setText(_planDetails.get(position).getPlacePhoneNum());
			holder.ratingBar.setRating(_planDetails.get(position).getPlaceRating());
			//ImageLoader.getInstance().displayImage(itemsData.get(position).getImgUrl(), holder.image);
			return convertView;
		}
	}

}
