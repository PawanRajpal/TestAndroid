package com.ma.activities;

import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.internal.ah;
import com.ma.bean.ItemsBean;
import com.ma.bean.PlaceDetail;
import com.ma.database.LavasaDatabase;
import com.ma.lavasa.R;
import com.ma.lavasa.SharedPref;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PlaceDetailFragment extends Fragment {

	String mCurFilter;
	LayoutInflater inflater;
	ListView actionBarList;
	Context mContext;
	List<ItemsBean> getListData;
	PlaceDetailFragmentAdapter placeDetailFragmentAdapter;
	ImageView iV_PlaceImage;
	Button btnBookNow;
	RatingBar rBar_Rating;
	TextView tv_numofRatings, tv_peopleReviews, tv_currencySymbol, tv_rate, tv_stayDuration, tv_detailTitle, tv_detailDesc;
	String imageURL = "http://www.hok.com/uploads/2012/05/24/3-new-images-z5b.jpg";
	//SharedPref sharedPref;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.place_detail_fragment, container, false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		actionBarList = (ListView) getView().findViewById(R.id.action_bar_list);
		tv_numofRatings = (TextView) getView().findViewById(R.id.placedetail_tv_numofratings);
		tv_peopleReviews = (TextView) getView().findViewById(R.id.placedetail_tv_peoplereview);
		tv_currencySymbol = (TextView) getView().findViewById(R.id.placedetail_tv_currencysymbol);
		tv_rate = (TextView) getView().findViewById(R.id.placedetail_tv_rateofplace);
		tv_stayDuration = (TextView) getView().findViewById(R.id.placedetail_tv_staydetail);
		tv_detailTitle = (TextView) getView().findViewById(R.id.placedetail_tv_detail_tilte);
		tv_detailDesc = (TextView) getView().findViewById(R.id.placedetail_tv_place_desc);

		iV_PlaceImage = (ImageView) getView().findViewById(R.id.placedetail_iv_image);

		rBar_Rating = (RatingBar) getView().findViewById(R.id.placedetail_rating_bar);
	//	sharedPref=new SharedPref(getActivity());
		ImageLoader.getInstance().displayImage(imageURL, iV_PlaceImage);
		
		Bundle bundle = this.getArguments();
		
		PlaceDetail placeDetail =  LavasaDatabase.getInstance().getPlaceDetail(bundle.getInt("placeId"));
		
		
		//sharedPref.setPlaceDetails(placeDetail.getPlaceName(), placeDetail.getAddress(),placeDetail.getOverallRating());
		tv_detailDesc.setText(placeDetail.getAddress());
		rBar_Rating.setRating((float)placeDetail.getOverallRating());
		
		
/*		Bundle bundle = this.getArguments();
		String cat = bundle.getString("Category", "food");
		getListData = LavasaDatabase.getInstance().getItemsData(cat);
		setHasOptionsMenu(true);

		try {
			if (getListData != null) {
				if (getListData.size() > 0) {
					placeDetailFragmentAdapter = new PlaceDetailFragmentAdapter(getListData, mContext);
					actionBarList.setAdapter(placeDetailFragmentAdapter);
				}
			}
		} catch (NullPointerException exception) {
			Toast toast = Toast.makeText(getActivity(), "No Item Found.", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}*/
	}

	// View Holder Class
	public static class ViewHolder {
		private ImageView image;
		private TextView title;
		private TextView description;
		private RatingBar ratingBar;
		private Button book_now;

	}

	// Place detail adapter
	private class PlaceDetailFragmentAdapter extends BaseAdapter {

		List<ItemsBean> itemsData;
		Context context;
		ViewHolder holder;
		List<ItemsBean> mOriginalValues;

		public PlaceDetailFragmentAdapter(List<ItemsBean> data, Context context) {
			this.itemsData = data;
			this.context = context;
		}

		@Override
		public int getCount() {
			return itemsData.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int id) {
			return id;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.action_bar_customized_list, parent, false);
				holder = new ViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.action_bar_title_text);
				holder.description = (TextView) convertView.findViewById(R.id.action_bar_des_text);
				holder.image = (ImageView) convertView.findViewById(R.id.action_bar_image);
				holder.ratingBar = (RatingBar) convertView.findViewById(R.id.action_rating_bar);
				holder.book_now = (Button) convertView.findViewById(R.id.action_bar_button);
				//ImageLoader.getInstance().displayImage(itemsData.get(position).getImgUrl(), holder.image);
				// holder.image.setText(itemsData.get(position).get)
				convertView.setTag(holder);
			}else
			{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(itemsData.get(position).getTitle());
			holder.description.setText(itemsData.get(position).getDesc());
			holder.ratingBar.setRating(itemsData.get(position).getRating());
			//ImageLoader.getInstance().displayImage(itemsData.get(position).getImgUrl(), holder.image);
			return convertView;
		}
	}
}

