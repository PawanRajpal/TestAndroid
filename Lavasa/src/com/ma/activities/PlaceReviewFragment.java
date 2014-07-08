package com.ma.activities;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import com.ma.bean.PlaceReview;
import com.ma.database.LavasaDatabase;
import com.ma.lavasa.R;
import com.ma.lavasa.SharedPref;
import com.ma.socialmedia.GPlusLogin;

public class PlaceReviewFragment extends Fragment implements ReviewData {

	Button btn_AddReview;
	Bundle bundle;
	SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss");
	Date date;
	ArrayList<PlaceReview> placeReviews;
	ListView listViewReview;
	PlaceReviewFragmentAdapter adapter;
	SharedPref sharedPref;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.place_review_fragment, null);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		btn_AddReview = (Button) getView().findViewById(R.id.placereview_btn_add); 
		listViewReview = (ListView) getView().findViewById(R.id.placereview_et_review_list);

		sharedPref = new SharedPref(getActivity().getApplicationContext());

		bundle = this.getArguments();

		date = new Date();

		placeReviews = LavasaDatabase.getInstance().getPlaceReview(bundle.getInt("placeId"));

		Collections.sort(placeReviews, new OrderByDate());

		adapter = new PlaceReviewFragmentAdapter(placeReviews, getActivity());

		if(placeReviews.size()!=0){
			listViewReview.setAdapter(adapter);	
		}


		btn_AddReview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//if(sharedPref.isGoolgeLoggedIn()){
					WriteReviewDialogFragment dialogFragment = new WriteReviewDialogFragment();
					dialogFragment.setTargetFragment(PlaceReviewFragment.this, 1);
					dialogFragment.show(getFragmentManager(), null);
				//} else {
				//	Intent intent = new Intent(getActivity(), GPlusLogin.class);
				//	startActivity(intent);
				//}
			}
		});

	}

	// View Holder Class
	public static class ViewHolder {
		private ImageView image;
		private TextView title;
		private TextView description;
		private RatingBar ratingBar;
		private TextView date;
	}

	// Place detail adapter
	private class PlaceReviewFragmentAdapter extends BaseAdapter {

		Context context;
		ViewHolder holder;
		List<PlaceReview> placeReviews;

		public PlaceReviewFragmentAdapter(List<PlaceReview> data, Context context) {
			this.placeReviews = data;
			this.context = context;
		}


		@Override
		public int getCount() {
			return placeReviews.size();
		}

		@Override
		public Object getItem(int position) {
			return placeReviews.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.place_review_list_item, parent, false);
				holder = new ViewHolder();
				holder.image = (ImageView) convertView.findViewById(R.id.action_bar_image);
				holder.title = (TextView) convertView.findViewById(R.id.placereview_list_title);
				holder.description = (TextView) convertView.findViewById(R.id.placereview_list_review);
				holder.ratingBar = (RatingBar) convertView.findViewById(R.id.placereview_list_ratingbar);
				holder.date = (TextView)convertView.findViewById(R.id.placereview_list_review_date);
				convertView.setTag(holder);
			}else{

				holder = (ViewHolder) convertView.getTag();

			}

			holder.title.setText(placeReviews.get(position).getReviewTitle());
			holder.description.setText(placeReviews.get(position).getReviewDescription());
			holder.ratingBar.setRating((float)placeReviews.get(position).getReviewRating());
			String string = placeReviews.get(position).getReviewDate();
			if(string.contains("_")){
				string = string.substring(0, string.indexOf("_"));
			}
			holder.date.setText(string);
			//ImageLoader.getInstance().displayImage(itemsData.get(position).getImgUrl(), holder.image);
			return convertView;
		}
	}

	@Override
	public void saveReviewData(String title, String desc, float rating) {

		LavasaDatabase.getInstance().insertPlaceReview(bundle.getInt("placeId"), "userName", rating, title, desc, dateFormat.format(date));
		placeReviews = LavasaDatabase.getInstance().getPlaceReview(bundle.getInt("placeId"));
		Collections.sort(placeReviews, new OrderByDate());
		adapter = new PlaceReviewFragmentAdapter(placeReviews, getActivity());

		if(placeReviews.size()!=0){
			listViewReview.setAdapter(adapter);	
		}

	}

	//Name by Ascending order
	public class OrderByDate implements Comparator<PlaceReview>{

		@Override
		public int compare(PlaceReview o1, PlaceReview o2) {
			return o2.getReviewDate().compareTo(o1.getReviewDate());
		}
	}

}
