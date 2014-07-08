package com.ma.lavasa;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.ma.location.GPSTracker;

public class DeviceLocation extends Activity {

	Button btnShowLocation;
	GPSTracker gpsTracker;
	double latitude, longitude;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.getdevicelocation);

		btnShowLocation = (Button) findViewById(R.id.btn_showlocation);

		btnShowLocation.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				gpsTracker = new GPSTracker(DeviceLocation.this);

				if(gpsTracker.isGPSEnabled){

					latitude = gpsTracker.getLatitude();
					longitude = gpsTracker.getLongitude();
					
					

					Toast.makeText(getApplicationContext(), "Current location is : " + "\n" + "lat: " + latitude + "\n" + "long: " + longitude, Toast.LENGTH_LONG).show();

				} else {

					//gpsTracker.turnGPSOn();
					gpsTracker.showSettingsAlert();
				}
			}
		});
	}
}
