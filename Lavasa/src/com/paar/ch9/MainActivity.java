package com.paar.ch9;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import com.ma.lavasa.R;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;


public class MainActivity extends AugmentedActivity {
    private static final String TAG = "MainActivity";
    private static final String locale = "en";
    private static final BlockingQueue<Runnable> queue = new ArrayBlockingQueue<Runnable>(1);
    private static final ThreadPoolExecutor exeService = new ThreadPoolExecutor(1, 1, 20, TimeUnit.SECONDS, queue);
	private static final Map<String,NetworkDataSource> sources = new ConcurrentHashMap<String,NetworkDataSource>();
	private static ProgressDialog progressDialog;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        NetworkDataSource googlePlaces = new GooglePlacesDataSource(this.getResources());
        sources.put("googlePlaces", googlePlaces);
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
//        NetworkDataSource twitter = new TwitterDataSource(this.getResources());
//        sources.put("twitter",twitter);
//        NetworkDataSource wikipedia = new WikipediaDataSource(this.getResources());
//        sources.put("wiki",wikipedia);
    }

	@Override
    public void onStart() {
        super.onStart();
        
        Location last = ARData.getCurrentLocation();
        progressDialog.show();
        updateData(last.getLatitude(),last.getLongitude(),last.getAltitude());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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
				updateDataOnZoom();
//                showRadar = !showRadar;
//                item.setTitle(((showRadar)? "Hide" : "Show")+" Radar");
                break;
            case R.id.atm:
            	ARData.markerList.clear();
				ARData.cache.clear();
				GooglePlacesDataSource.TYPES = "atm";
				updateDataOnZoom();
//                showZoomBar = !showZoomBar;
//                item.setTitle(((showZoomBar)? "Hide" : "Show")+" Zoom Bar");
//                zoomLayout.setVisibility((showZoomBar)?LinearLayout.VISIBLE:LinearLayout.GONE);
                break;
            case R.id.hospital:
            	ARData.markerList.clear();
				ARData.cache.clear();
				GooglePlacesDataSource.TYPES = "hospital";
				updateDataOnZoom();
				break;
            case R.id.all:
            	ARData.markerList.clear();
				ARData.cache.clear();
				GooglePlacesDataSource.TYPES = "food|restaurant|hotels|hospitals|shopping_mall|train_station|taxi_stand|store|hindu_temple|health|school|bus_station";
				updateDataOnZoom();
				break;
            case R.id.exit:
                finish();
                break;
        }
        return true;
    }

	@Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        
        updateData(location.getLatitude(),location.getLongitude(),location.getAltitude());
    }

	@Override
	protected void markerTouched(Marker marker) {
		
		PlacesDialog dialog=new PlacesDialog(this, marker);
		dialog.show();
//		 Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
//	                .parse("http://maps.google.com/maps?saddr="
//	                        + ARData.getCurrentLocation().getLatitude() + ","
//	                        + ARData.getCurrentLocation().getLongitude() + "&daddr="
//	                        + marker.getLatitude() + "," + marker.getLongitude()));
//	        navigation.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
//	        startActivity(navigation);
	}

    @Override
	protected void updateDataOnZoom() {
	    super.updateDataOnZoom();
        Location last = ARData.getCurrentLocation();
        updateData(last.getLatitude(),last.getLongitude(),last.getAltitude());
	}
    
    private void updateData(final double lat, final double lon, final double alt) {
        try {
            exeService.execute(
                new Runnable() {
                    
                    public void run() {
                        for (NetworkDataSource source : sources.values())
                            download(source, lat, lon, alt);
                    }
                }
            );
        } catch (RejectedExecutionException rej) {
            Log.w(TAG, "Not running new download Runnable, queue is full.");
        } catch (Exception e) {
            Log.e(TAG, "Exception running download Runnable.",e);
        }
    }
    
    private static boolean download(NetworkDataSource source, double lat, double lon, double alt) {
		if (source==null) return false;
		
		String url = null;
		try {
			url = source.createRequestURL(lat, lon, alt, ARData.getRadius(), locale);    	
		} catch (NullPointerException e) {
			return false;
		}
    	
		List<Marker> markers = null;
		try {
			markers = source.parse(url);
		} catch (NullPointerException e) {
			return false;
		}
		progressDialog.dismiss();
    	ARData.addMarkers(markers);
    	return true;
    }
}