package com.paar.ch9;

import com.ma.lavasa.R;

import android.app.Dialog;
import android.content.Context;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;


public class PlacesDialog extends Dialog {
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		 if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			 		
		    	
		    	
		    }
		return super.onKeyDown(keyCode, event);
	}



	Marker kMarker;
	TextView title,address;
	Button get_me_there;
	Context dContext;

	
RatingBar ratingBar;

public PlacesDialog(Context context,Marker marker) {
	super(context);
	// TODO Auto-generated constructor stub
	
	this.dContext=context;
	this.kMarker=marker;
}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog);
		title=(TextView)findViewById(R.id.title);
		get_me_there=(Button)findViewById(R.id.get_me_there);
		title.setText(kMarker.getName());
		address=(TextView)findViewById(R.id.address);
		ratingBar=(RatingBar)findViewById(R.id.ratingBar);
		address.setText(kMarker.getVicinity());
		ratingBar.setRating(kMarker.getRating());
		
//		
//		getWindow().setBackgroundDrawable(
//				new ColorDrawable(
//						android.graphics.Color.TRANSPARENT));
		
		
		
		
		
		get_me_there.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
				
				
				
//						 Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
//		                .parse("google.streetview:cbll= 53.426398"+",-2.242795&cbp=3a,75y,348.34h,69.59t&mz=21"
//		                       ));
//		      
//		        	dContext.startActivity(navigation);
				 Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
			                .parse("http://maps.google.com/maps?saddr="
			                        + ARData.getCurrentLocation().getLatitude() + ","
			                        + ARData.getCurrentLocation().getLongitude() + "&daddr="
			                        + kMarker.getLatitude() + "," + kMarker.getLongitude()));
			        navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
			        	dContext.startActivity(navigation);
			}

			

		});
//		
//		
	}

	

}
