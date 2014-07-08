package com.ma.plans;

import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ma.bean.ItemsBean;
import com.ma.bean.PlanBean;
import com.ma.database.LavasaDatabase;
import com.ma.lavasa.R;
import com.ma.lavasa.SharedPref;
import com.ma.location.GPSTracker;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PlanFragment extends Fragment implements OnQueryTextListener {

	View action_bar_view;
	LayoutInflater inflater;
	ListView actionBarList;
	Context mContext;
	List<PlanBean> getListData;
	ItemList itemAdapter;
	String mCurFilter;
	FragmentManager fragmentManager; 
	Fragment fragment = null;
	SharedPref pref;
	GPSTracker gpsTracker;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		action_bar_view = inflater.inflate(R.layout.action_bar_fragment, container, false);
		mContext = action_bar_view.getContext();
		fragmentManager = getActivity().getSupportFragmentManager();

		actionBarList = (ListView) action_bar_view.findViewById(R.id.action_bar_list);
		Bundle bundle = this.getArguments();
		int packageID = bundle.getInt("packageID");
		System.out.println("package id is***********************************/"+packageID);
		getListData = LavasaDatabase.getInstance().getPackageName(packageID);
		pref = new SharedPref(getActivity());
		gpsTracker = new GPSTracker(mContext);
		setHasOptionsMenu(true);

		try {
			if (getListData != null) {
				if (getListData.size() > 0) {
					itemAdapter = new ItemList(getListData, mContext);
					actionBarList.setAdapter(itemAdapter);
				}
			}

			actionBarList.setTextFilterEnabled(true);
		} catch (NullPointerException exception) {
			Toast toast = Toast.makeText(getActivity(), "No Item Found.", Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}
		return action_bar_view;
	}



	public boolean isNetworkAvailable() {
		ConnectivityManager connectivity = (ConnectivityManager) mContext
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (connectivity != null) {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();

			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					Log.i("Class", info[i].getState().toString());
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
			actionBarList.setOnItemClickListener(new OnItemClickListener() {
		
					@Override
					public void onItemClick(AdapterView<?> arg0, View view, int position, long Id) {
		
						fragment = new PlanDetailFragment();
						System.out.println(getListData.get(position).getPkg_id());
						pref.setPlaceId(getListData.get(position).getPkg_id());
						loadFragment(fragment, getListData.get(position).getPkg_id());
					}
			});
	}


	@Override
	public boolean onQueryTextChange(String newText) {
		if (TextUtils.isEmpty(newText)) {
			actionBarList.clearTextFilter();
		} else {
			((Filterable) itemAdapter).getFilter().filter(newText.toString().trim());
			actionBarList.setFilterText(newText.toString().trim());
		}
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		return false;
	}


	private void loadFragment(Fragment fragment, int packageId){
		Bundle bundle = new Bundle();
		pref.setFragmentforMap("plan");
		bundle.putInt("packageId", packageId);
		fragment.setArguments(bundle);
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
		fragmentTransaction.replace(R.id.content_frame, fragment);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	// View Holder
	public static class ViewHolder {
		private ImageView image;
		private TextView title;
		private TextView description;
		private RatingBar ratingBar;
		private Button book_now;
		private TextView duration;

	}

	private class ItemList extends BaseAdapter  {

		List<PlanBean> itemsData;
		Context context;
		ViewHolder holder;
		List<ItemsBean> mOriginalValues;

		public ItemList(List<PlanBean> data, Context context) {
			this.itemsData = data;
			this.context = context;
			//this.mOriginalValues = data;
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
				convertView = inflater.inflate(R.layout.plan_list_item, parent, false);
				holder = new ViewHolder();

				holder.title = (TextView) convertView.findViewById(R.id.action_bar_title_text);
				holder.description = (TextView) convertView.findViewById(R.id.action_bar_des_text);
				holder.image = (ImageView) convertView.findViewById(R.id.action_bar_image);
				holder.ratingBar = (RatingBar) convertView.findViewById(R.id.action_rating_bar);
				holder.duration = (TextView) convertView.findViewById(R.id.duration);
				holder.book_now = (Button) convertView.findViewById(R.id.action_bar_button);
				holder.title.setText(itemsData.get(position).getPkg_name());
				holder.duration.setText("Duration: "+String.valueOf(itemsData.get(position).getDuration()));
				holder.description.setText(itemsData.get(position).getDescription());
				holder.ratingBar.setVisibility(View.GONE);
				//holder.ratingBar.setRating(itemsData.get(position).getRating());
				ImageLoader.getInstance().displayImage(itemsData.get(position).getImage_url(), holder.image);
				//holder.image.setText(itemsData.get(position).getTitle())
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(itemsData.get(position).getPkg_name());
			holder.description.setText(itemsData.get(position).getDescription());
			holder.duration.setText("Duration: "+String.valueOf(itemsData.get(position).getDuration()));
			//holder.ratingBar.setRating(itemsData.get(position).getRating());
			ImageLoader.getInstance().displayImage(itemsData.get(position).getImage_url(), holder.image);
			return convertView;
		}

	}
}
