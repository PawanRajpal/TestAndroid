package com.ma.lavasa;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class Splash extends Activity {


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				startMainMenuScreen();

			}
		}, 2000);
		super.onStart();
	}



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);


		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()

		.cacheInMemory(true).cacheOnDisc(true).resetViewBeforeLoading(true)
		.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).defaultDisplayImageOptions(
						defaultOptions)
						.build();
		ImageLoader.getInstance().init(config);


	}

	protected void startMainMenuScreen() {
		Intent intent = new Intent(this, Home.class);
		startActivity(intent);
		this.finish();
	}

}
