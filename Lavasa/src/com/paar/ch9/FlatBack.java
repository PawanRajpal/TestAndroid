package com.paar.ch9;

import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Circle;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import com.ma.bean.ItemsBean;
import com.ma.database.LavasaDatabase;
import com.ma.lavasa.R;
import com.ma.lavasa.SharedPref;
import com.ma.location.GPSTracker;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class FlatBack extends FragmentActivity {
	private com.google.android.gms.maps.MapView mapView;

	final static String TAG = "PAAR";
	SensorManager sensorManager;

	int orientationSensor;
	float headingAngle;
	float pitchAngle;
	float rollAngle;
	GoogleMap mMap;
	Location location;
	LatLng myLocation;
	GPSTracker gpsTracker;
	SharedPref sharedPref;
	List<ItemsBean> getListData;
	Circle circle;
	  private static final double DEFAULT_RADIUS = 1000000;
	    public static final double RADIUS_OF_EARTH_METERS = 6371009;

	    private static final int WIDTH_MAX = 50;
	    private static final int HUE_MAX = 360;
	    private static final int ALPHA_MAX = 255;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// main.xml contains a MapView
		setContentView(R.layout.map);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		orientationSensor = Sensor.TYPE_ORIENTATION;
		sensorManager.registerListener(sensorEventListener,
				sensorManager.getDefaultSensor(orientationSensor),
				SensorManager.SENSOR_DELAY_NORMAL);
		sharedPref = new SharedPref(this);
		
		// extract MapView from layout
		// Do a null check to confirm that we have not already instantiated the
		// map.
		if (mMap == null) {
			// Try to obtain the map from the SupportMapFragment.
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.map_marker)).getMap();
			//registerForContextMenu(mapView);
			gpsTracker = new GPSTracker(this);
			mMap.setMyLocationEnabled(true);

			// location = new Location(mMap.getMyLocation());

			// if (location != null) {
			myLocation = new LatLng(gpsTracker.getLatitude(),
					gpsTracker.getLongitude());
			// }
			mMap.animateCamera(CameraUpdateFactory
					.newLatLngZoom(myLocation, 15));

			mMap.getUiSettings().setCompassEnabled(true);
			
//			 circle = mMap.addCircle(new CircleOptions()
//             .center(myLocation)
//             .radius(DEFAULT_RADIUS)
//             .strokeWidth(10)
//             .strokeColor(android.R.color.holo_blue_bright)
//             .fillColor(android.R.color.holo_green_light));
			// Check if we were successful in obtaining the map.
			if (mMap != null) {

				// Adding marker on the Google Map
				
				if (SharedPref.isFromAR) {
					if(ARData.cache.size()>1){
					for (int i = 0; i < ARData.cache.size(); i++) {
						
						LatLng perth = new LatLng(ARData.cache.get(i)
								.getLatitude(), ARData.cache.get(i)
								.getLongitude());
//						//
						Bitmap.Config conf = Bitmap.Config.ARGB_8888;
						Bitmap bmp = Bitmap.createBitmap(100, 100, conf);
//						Canvas canvas1 = new Canvas(bmp);
//
//						Paint color = new Paint();
//						color.setTextSize(35);
//						color.setColor(Color.BLACK);
////
////						//modify canvas
//						canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
//						   android.R.color.transparent), 0,0, color);
//						canvas1.drawText(ARData.cache.get(i).getName(), 40, 50, color);
						MarkerOptions markerOptions = new MarkerOptions();

						// Setting latitude and longitude for the marker
			
						markerOptions.position(perth);
						markerOptions.snippet((ARData.cache.get(i)
								.getVicinity()));
						markerOptions.title(ARData.cache.get(i).getName());
						if(GooglePlacesDataSource.TYPES.contains("food"))
						markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bmp));
						else if(GooglePlacesDataSource.TYPES.equalsIgnoreCase("hospital"))
							markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.hospital_map));
						else if(GooglePlacesDataSource.TYPES.equalsIgnoreCase("atm"))
							markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.atm));
						
						Marker marker=mMap.addMarker(markerOptions);
						marker.showInfoWindow();
						
						
						
						
						
					}

						// mMap.moveCamera(CameraUpdateFactory.newLatLng(perth));
						//
						// // Zoom in the Google Map
						// mMap.animateCamera(CameraUpdateFactory.zoomTo(1));
						// double lati=(ARData.cache.get(i).getLatitude());
						// double longLat=(ARData.cache.get(i).getLatitude());
						// mMap.addMarker(new MarkerOptions().position(new
						// LatLng(lati,longLat)).title(ARData.cache.get(i).getName()).snippet(ARData.cache.get(i).getVicinity()));
					}

				} 
				else {

					System.out
							.println("Inside the else part*************************************");
					getListData = LavasaDatabase.getInstance().getItemsData(
							"all");
					System.out
							.println("Inside the else part*************************************"
									+ getListData.size());
					for (int i = 0; i < getListData.size(); i++) {
						LatLng perth = new LatLng(getListData.get(i)
								.getLatitude(), getListData.get(i)
								.getLongitude());

						System.out
								.println("Lat size is***********************************/"
										+ getListData.get(i).getLatitude());
						System.out
								.println("Lng size is***********************************/"
										+ getListData.get(i).getLongitude());

						MarkerOptions markerOptions = new MarkerOptions();

						// Setting latitude and longitude for the marker
						markerOptions.position(perth);
						markerOptions.snippet(getListData.get(i).getDesc());
						markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.food_map));
						mMap.addMarker(markerOptions).setTitle(
								getListData.get(i).getTitle());

						// mMap.moveCamera(CameraUpdateFactory.newLatLng(perth));
						//
						// // Zoom in the Google Map
						// mMap.animateCamera(CameraUpdateFactory.zoomTo(1));
						// double lati=(ARData.cache.get(i).getLatitude());
						// double longLat=(ARData.cache.get(i).getLatitude());
						// mMap.addMarker(new MarkerOptions().position(new
						// LatLng(lati,longLat)).title(ARData.cache.get(i).getName()).snippet(ARData.cache.get(i).getVicinity()));
					}
				}
			}

		}
		// mapView.setBuiltInZoomControls(true);
		//
		// // create an overlay that shows our current location
		// myLocationOverlay = new FixLocation(this, mapView);
		//
		// // add this overlay to the MapView and refresh it
		// mapView.getOverlays().add(myLocationOverlay);
		// mapView.postInvalidate();
		//
		// // call convenience method that zooms map on our location
		// zoomToMyLocation();
	}

	  @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
		  
		  System.out.println("Insiode on create options menu***********************");
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.menu, menu);
	        return true;
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	        Log.v(TAG, "onOptionsItemSelected() item="+item);
	        switch (item.getItemId()) {
	            case R.id.food:
	            	ARData.markerList.clear();
					ARData.cache.clear();
				
					GooglePlacesDataSource.TYPES = "food|restaurant|hotels";
					//updateDataOnZoom();
//	                showRadar = !showRadar;
//	                item.setTitle(((showRadar)? "Hide" : "Show")+" Radar");
	                break;
	            case R.id.atm:
	            	ARData.markerList.clear();
					ARData.cache.clear();
					GooglePlacesDataSource.TYPES = "atm";
					//updateDataOnZoom();
//	                showZoomBar = !showZoomBar;
//	                item.setTitle(((showZoomBar)? "Hide" : "Show")+" Zoom Bar");
//	                zoomLayout.setVisibility((showZoomBar)?LinearLayout.VISIBLE:LinearLayout.GONE);
	                break;
	            case R.id.hospital:
	            	ARData.markerList.clear();
					ARData.cache.clear();
					GooglePlacesDataSource.TYPES = "hospital";
					//updateDataOnZoom();
					break;
	            case R.id.all:
	            	ARData.markerList.clear();
					ARData.cache.clear();
					GooglePlacesDataSource.TYPES = "food|restaurant|hotels|hospitals|shopping_mall|train_station|taxi_stand|store|hindu_temple|health|school|bus_station";
					//updateDataOnZoom();
					break;
	            case R.id.exit:
	                finish();
	                break;
	        }
	        return true;
	    }

	final SensorEventListener sensorEventListener = new SensorEventListener() {
		public void onSensorChanged(SensorEvent sensorEvent) {
			if (sensorEvent.sensor.getType() == Sensor.TYPE_ORIENTATION) {
				headingAngle = sensorEvent.values[0];
				pitchAngle = sensorEvent.values[1];
				rollAngle = sensorEvent.values[2];

				// Log.d(TAG, "Heading: " + String.valueOf(headingAngle));
				// Log.d(TAG, "Pitch: " + String.valueOf(pitchAngle));
				// Log.d(TAG, "Roll: " + String.valueOf(rollAngle));
				//
				if (((pitchAngle > 7 || pitchAngle < -7) && (rollAngle > 60 || rollAngle < -60))
						&& SharedPref.isFromAR) {

					// Toast.makeText(getApplicationContext(), "Camera",
					// Toast.LENGTH_SHORT).show();
					SensorsActivity.isCameraView = false;
					launchCameraView();
				}
			}
		}

		public void onAccuracyChanged(Sensor arg0, int arg1) {
			// TODO Auto-generated method stub

		}
	};

	public void launchCameraView() {
		finish();
		// Intent cameraView = new Intent(this,
		// ASimpleAppUsingARActivity.class);
		// startActivity(cameraView);
	}

	@Override
	protected void onResume() {
		super.onResume();
		// myLocationOverlay.enableMyLocation();
	}

	@Override
	protected void onPause() {
		super.onPause();
		// myLocationOverlay.disableMyLocation();
	}

	protected boolean isRouteDisplayed() {
		return false;
	}
	
	private void addText(){
		

	}
}
