package com.ma.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

import com.ma.bean.ItemsBean;
import com.ma.database.LavasaDatabase;
import com.ma.lavasa.R;
import com.ma.lavasa.SharedPref;
import com.ma.location.GPSTracker;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.paar.ch9.FlatBack;
import com.paar.ch9.MainActivity;

public class PlacesListFragment extends Fragment implements OnQueryTextListener {

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

		MenuItem item = menu.add("Search");
		MenuItem item1 = menu.add("Map");

		item1.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		item.setIcon(android.R.drawable.ic_menu_search);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM
				| MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
		SearchView sv = new SearchView(getActivity());
		sv.setOnQueryTextListener(this);
		item.setActionView(sv);
	}

	View action_bar_view;
	LayoutInflater inflater;
	ListView actionBarList;
	Context mContext;
	List<ItemsBean> getListData;
	ItemList itemAdapter;
	String mCurFilter;
	FragmentManager fragmentManager; 
	Fragment fragment = null;
	SharedPref pref;
	GPSTracker gpsTracker;
	SharedPref sharedPref;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		action_bar_view = inflater.inflate(R.layout.action_bar_fragment, container, false);
		mContext = action_bar_view.getContext();
		fragmentManager = getActivity().getSupportFragmentManager();
		
		actionBarList = (ListView) action_bar_view.findViewById(R.id.action_bar_list);
		Bundle bundle = this.getArguments();
		String cat = bundle.getString("Category", "food");
		getListData = LavasaDatabase.getInstance().getItemsData(cat);
		pref = new SharedPref(getActivity());
		gpsTracker=new GPSTracker(mContext);
		setHasOptionsMenu(true);
		sharedPref = new SharedPref(getActivity());
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

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		if (item.getTitle().equals("Map")) {

			if (pref.getSensorAvailability()) {
				SharedPref.isFromAR=true;
				if (gpsTracker.getLocation() != null && isNetworkAvailable()) {
					SharedPref.isFromAR=true;
					Intent intent = new Intent(getActivity(),
							MainActivity.class);
					startActivity(intent);
				} 
				else if(gpsTracker.getLocation()!=null)
				{
					SharedPref.isFromAR=false;
					System.out.println("Inside the location getting**********************************************************");
					Intent flatBackIntent = new Intent(getActivity(), FlatBack.class);
			    	startActivity(flatBackIntent);
				}
				else
				{
					Toast.makeText(getActivity(), "GPS or Internet not available." ,
							Toast.LENGTH_SHORT).show();
				}
			} 
			else {
				SharedPref.isFromAR=false;
				Toast.makeText(getActivity(), "Sensor not available",
						Toast.LENGTH_SHORT).show();
				if(gpsTracker.getLocation()!=null){
				Intent flatBackIntent = new Intent(getActivity(), FlatBack.class);
		    	startActivity(flatBackIntent);
				}
				else{
					Toast.makeText(getActivity(), "GPS or Internet not available." ,
							Toast.LENGTH_SHORT).show();
				}
			}
		}
		return super.onOptionsItemSelected(item);
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

				fragment = new TabActivitySwipableDetail();
				loadFragment(fragment, getListData.get(position).getId());
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


	private void loadFragment(Fragment fragment, int PlaceId){
		Bundle bundle = this.getArguments();
		sharedPref.setFragmentforMap("details");
		bundle.putInt("placeId", PlaceId);
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

	}

	private class ItemList extends BaseAdapter implements Filterable {

		List<ItemsBean> itemsData;
		Context context;
		ViewHolder holder;
		List<ItemsBean> mOriginalValues;

		public ItemList(List<ItemsBean> data, Context context) {
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
				convertView = inflater.inflate(R.layout.action_bar_customized_list, parent, false);
				holder = new ViewHolder();

				holder.title = (TextView) convertView.findViewById(R.id.action_bar_title_text);
				holder.description = (TextView) convertView.findViewById(R.id.action_bar_des_text);
				holder.image = (ImageView) convertView.findViewById(R.id.action_bar_image);
				holder.ratingBar = (RatingBar) convertView.findViewById(R.id.action_rating_bar);
				holder.book_now = (Button) convertView.findViewById(R.id.action_bar_button);
				holder.title.setText(itemsData.get(position).getTitle());
				holder.ratingBar.setRating(itemsData.get(position).getRating());
				ImageLoader.getInstance().displayImage(itemsData.get(position).getImgUrl(), holder.image);
				 //holder.image.setText(itemsData.get(position).getTitle())
				convertView.setTag(holder);
			}else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.title.setText(itemsData.get(position).getTitle());
			holder.description.setText(itemsData.get(position).getDesc());
			holder.ratingBar.setRating(itemsData.get(position).getRating());
			ImageLoader.getInstance().displayImage(itemsData.get(position).getImgUrl(), holder.image);
			return convertView;
		}

		@Override
		public Filter getFilter() {

			Filter filter = new Filter() {

				@SuppressWarnings("unchecked")
				@Override
				protected void publishResults(CharSequence constraint,
						FilterResults results) {

					itemsData = (List<ItemsBean>) results.values; // has the
					// filtered
					// values
					notifyDataSetChanged(); // notifies the data with new filtered
					// values
				}

				@Override
				protected FilterResults performFiltering(CharSequence constraint) {
					FilterResults results = new FilterResults(); // Holds the
					// results of a
					// filtering
					// operation in
					// values
					List<ItemsBean> FilteredArrList = new ArrayList<ItemsBean>();

					if (mOriginalValues == null) {
						mOriginalValues = new ArrayList<ItemsBean>(itemsData); // saves
						// the
						// original
						// data
						// in
						// mOriginalValues
					}

					/********
					 * 
					 * If constraint(CharSequence that is received) is null returns
					 * the mOriginalValues(Original) values else does the Filtering
					 * and returns FilteredArrList(Filtered)
					 * 
					 ********/
					if (constraint == null || constraint.length() == 0) {

						// set the Original result to return
						results.count = mOriginalValues.size();
						results.values = mOriginalValues;
					} else {
						constraint = constraint.toString().toLowerCase();
						for (ItemsBean c : mOriginalValues) {
							if (c.getTitle()
									.toUpperCase()
									.startsWith(constraint.toString().toUpperCase()))
								FilteredArrList.add(c);
						}
						// for (int i = 0; i < mOriginalValues.size(); i++) {
						// String data = mOriginalValues.get(i).getTitle();
						// if (data.toLowerCase().startsWith(constraint.toString()))
						// {
						// FilteredArrList.add(data);
						// }
						// }
						// set the Filtered result to return
						results.count = FilteredArrList.size();
						results.values = FilteredArrList;
					}
					return results;
				}
			};
			return filter;
		}
	}
}
