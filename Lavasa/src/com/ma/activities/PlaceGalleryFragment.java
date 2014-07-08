package com.ma.activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ma.bean.ItemsBean;
import com.ma.lavasa.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class PlaceGalleryFragment extends Fragment {

	String mCurFilter;
	LayoutInflater inflater;
	Context mContext;
	List<ItemsBean> getListData;
	PlaceGalleryFragmentAdapter placeGalleryFragmentAdapter;
	GridView gallery_gv_gallery;
	ArrayList<String> images;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		images = new ArrayList<String>();
		images.add("http://www.hok.com/uploads/2012/05/24/3-new-images-z5b.jpg");
		images.add("http://www.thehindubusinessline.com/multimedia/dynamic/00808/BL15LAVASA2_808457g.jpg");
		images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQm34O0PcNi6Cs0fd-0ZkHyf0JqIDmJsGxOo9t2XhVFgqOtUG62");
		images.add("http://images5.makemytrip.com/images//hotels/200903261319445218/Exterior_View.jpg");
		images.add("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcRMPdcNaejBDnukTmz1ycYsjhJRJg1nNODphlAHPNnFuOA6SIz8");
		images.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRrimWaQ0utVOLaIZ4UhgoTCI3OclVcJkiAcg4txIVTf2C0S0m_lg");
		images.add("https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTco6d6pTzP3sYUqK9dqHhAMVn6Ik6Jh8YuZouI-RQc6JJeTNPEgw");
		return inflater.inflate(R.layout.place_gallery_fragment, container, false);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		gallery_gv_gallery = (GridView) getView().findViewById(R.id.gallery_gv_gallery);
		placeGalleryFragmentAdapter = new PlaceGalleryFragmentAdapter(images);
		gallery_gv_gallery.setAdapter(placeGalleryFragmentAdapter);
		gallery_gv_gallery.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

				Bundle bundlw = new Bundle();
				bundlw.putStringArrayList("imagesurls", images);
				FullScreenGalleryViewDialogFragment imageViewerDialogFrag= new FullScreenGalleryViewDialogFragment();
				imageViewerDialogFrag.setArguments(bundlw);
				imageViewerDialogFrag.show(getFragmentManager(), "imageViewerDialogFrag");				
			}
		});
	}

	// View Holder Class
	public static class ViewHolder {
		private ImageView image;
		private TextView title;
		private TextView description;
		private RatingBar ratingBar;
		private Button book_now;
	}

	public class PlaceGalleryFragmentAdapter extends BaseAdapter
	{
		ImageLoader imageLoader;
		LayoutInflater inflator;
		ArrayList<String> _imagesURL;


		public PlaceGalleryFragmentAdapter(ArrayList<String> imageURL)
		{
			
			this._imagesURL = imageURL;
			try {
				inflator = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}
		@Override
		public int getCount() {
			return _imagesURL.size();
		}

		@Override
		public Object getItem(int position) {
			return _imagesURL.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			convertView = inflator.inflate(R.layout.gridview_entity_image, null);
			ImageView imageView = (ImageView) convertView.findViewById(R.id.iV_entity_image);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);	
			ImageLoader.getInstance().displayImage(_imagesURL.get(position), imageView);
			return convertView;
		}
	}
}

