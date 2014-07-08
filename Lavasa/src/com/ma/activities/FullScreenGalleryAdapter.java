package com.ma.activities;

import java.net.URL;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.ma.imageslider.TouchImageView;
import com.ma.lavasa.R;
import com.nostra13.universalimageloader.core.ImageLoader;

public class FullScreenGalleryAdapter extends PagerAdapter {
	private Activity _activity;
	private LayoutInflater inflater;
	ArrayList<String>imagesurls;
	String fromTravelogue;
	URL url = null;
	// constructor
	public FullScreenGalleryAdapter(Context activity, ArrayList<String>imagesurls,String fromTravelogue) {
		this._activity = (Activity) activity;
		this.imagesurls = imagesurls;
		this.fromTravelogue=fromTravelogue;
	}

	@Override
	public int getCount() {
		return this.imagesurls.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((RelativeLayout) object);
	}

	@Override
	public Object instantiateItem(ViewGroup container, final int position) {
		final TouchImageView imgDisplay;

		inflater = (LayoutInflater) _activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container, false);

		imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.imgDisplay);
		//        imgDisplay.setScaleType(ImageView.ScaleType.CENTER_CROP);


		new Handler().post(new Runnable() {

			@Override
			public void run() {
				ImageLoader.getInstance().displayImage(imagesurls.get(position), imgDisplay);
			}
		});

		((ViewPager) container).addView(viewLayout);

		return viewLayout;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		((ViewPager) container).removeView((RelativeLayout) object);
	}

}
