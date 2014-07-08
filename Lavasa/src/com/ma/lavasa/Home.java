package com.ma.lavasa;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Debug;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.ma.activities.LoginScreen;
import com.ma.activities.StreetView;
import com.ma.activities.TabActivitySwipable;
import com.ma.adapter.NavDrawerListAdapter;
import com.ma.bean.NaviationParentItems;
import com.ma.bean.NavigationChildItems;
import com.ma.database.LavasaDatabase;
import com.ma.plans.TabActivitySwipablePlan;
import com.ma.reportincident.ReportIncident;

public class Home extends FragmentActivity {

	private DrawerLayout mDrawerLayout;
	private ExpandableListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;

	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	private NavDrawerListAdapter adapter;
	ActionBar actionBar;
	private int curItem;

	int imageArray[] = {R.drawable.ic_explore, R.drawable.ic_plan, R.drawable.ic_settings, R.drawable.ic_hepl, R.drawable.ic_explore};
	HashMap<String, List<String>> navDrawerChildItems = new HashMap<String, List<String>>();
	FragmentManager fragmentManager = getSupportFragmentManager(); 

	SensorManager mSensorManager;
	boolean isAccelerometer,isCompass,isOrientation;
	Context mContext;

	ArrayList<NaviationParentItems> naviationParentItems = new ArrayList<NaviationParentItems>();
	ArrayList<NavigationChildItems> navigationChildItems;

	Fragment fragment = null;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		mTitle = mDrawerTitle = getTitle();
		mContext=this;

		Debug.startMethodTracing("myapp");

		File dbFile = new File(LavasaDatabase.DB_PATH + LavasaDatabase.DB_NAME);
		if (!dbFile.exists()) {
			System.out.println("Copy the database ********************");
			copyDataBase();
		}

		actionBar = getActionBar();

		try {
			PackageInfo info = getPackageManager().getPackageInfo("com.ma.mapssdksample", PackageManager.GET_SIGNATURES);
			for (Signature signature : info.signatures) {
				MessageDigest md = MessageDigest.getInstance("SHA");
				md.update(signature.toByteArray());
				Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
			}
		} catch (NameNotFoundException e) {

		} catch (NoSuchAlgorithmException e) {

		}

		populateList();

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		mDrawerList = (ExpandableListView) findViewById(R.id.list_drawer);

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(Home.this, naviationParentItems, imageArray);

		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setHomeButtonEnabled(true);
		
		fragment = new TabActivitySwipable();
		loadFragment(fragment, "Explore", 0);
		mDrawerLayout.openDrawer(mDrawerList);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.app_name, R.string.app_name)

		{
			@Override
			public void onDrawerClosed(View view) {
				getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				getActionBar().setTitle("LAVASA");
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};	

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mDrawerList.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {

				adapter.setSelectedIndex(groupPosition, childPosition);

				// EXPLORE - Attractions
				if(groupPosition==0 && childPosition==0){

					fragment = new TabActivitySwipable();
					loadFragment(fragment, naviationParentItems.get(groupPosition).getEntities().get(childPosition).getName(), childPosition);

				// EXPLORE - StreetView
				}else if (groupPosition==0 && childPosition==1) {
					
					Intent intent = new Intent(getApplicationContext(), StreetView.class);
					startActivity(intent);
					
				
				// PLAN - Packages
				} else if (groupPosition==1 && childPosition==0) {

					fragment = new TabActivitySwipablePlan();
					loadFragment(fragment, naviationParentItems.get(groupPosition).getEntities().get(childPosition).getName(), childPosition);
					
				// REPORT - Report Incident
				}else if (groupPosition==2 && childPosition==0) {
					
					fragment = new ReportIncident();
					loadFragment(fragment, naviationParentItems.get(groupPosition).getEntities().get(childPosition).getName(), childPosition);

				// SETTINGS - Settings
				}else if (groupPosition==3 && childPosition==0) {

				// SETTINGS - Login
				}else if (groupPosition==3 && childPosition==1) {

					Intent intent = new Intent(getApplicationContext(), LoginScreen.class);
					startActivity(intent);

					// HELP - Help
				}else if (groupPosition==4 && childPosition==0) {

				}

				mDrawerLayout.closeDrawer(mDrawerList);

				return true;
			}
		});

		mDrawerLayout.openDrawer(mDrawerList);
		checkSensorAvailibility();
	}


	private void checkSensorAvailibility(){
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		SharedPref pref=new SharedPref(mContext);
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) != null){
			// Success! There's a magnetometer.
			isCompass=true;
		}	
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
			// Success! There's a magnetometer.
			isAccelerometer=true;
		}	
		if (mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION) != null){
			// Success! There's a magnetometer.
			isOrientation=true;
		}	

		if(isCompass && isOrientation && isAccelerometer){
			pref.setSensorAvailability(true);
		}
		else
			pref.setSensorAvailability(false);

	}

	private void copyDataBase() {
		try {
			InputStream myInput = getAssets().open(LavasaDatabase.DB_NAME);
			String outFileName = LavasaDatabase.DB_PATH + LavasaDatabase.DB_NAME;
			OutputStream myOutput = new FileOutputStream(outFileName);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}
			myOutput.flush();
			myOutput.close();
			myInput.close();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**Poplulate Navigation  */
	private void populateList() {

		List<NavigationChildItems> explore = new ArrayList<NavigationChildItems>();
		explore.add(new NavigationChildItems("Attractions", 0));
		explore.add(new NavigationChildItems("StreetView", 1));

		List<NavigationChildItems> plan = new ArrayList<NavigationChildItems>();
		plan.add(new NavigationChildItems("Packages", 0));

		List<NavigationChildItems> reportproblem = new ArrayList<NavigationChildItems>();
		reportproblem.add(new NavigationChildItems("Report Incident", 0));

		List<NavigationChildItems> settings = new ArrayList<NavigationChildItems>();
		settings.add(new NavigationChildItems("Settings", 0));
		settings.add(new NavigationChildItems("Login", 1));

		List<NavigationChildItems> help = new ArrayList<NavigationChildItems>();
		help.add(new NavigationChildItems("Help", 0));

		naviationParentItems.add(new NaviationParentItems("EXPLORE", 0, explore));
		naviationParentItems.add(new NaviationParentItems("PLAN", 1, plan));
		naviationParentItems.add(new NaviationParentItems("REPORT", 2, reportproblem));
		naviationParentItems.add(new NaviationParentItems("SETTINGS", 3, settings));
		naviationParentItems.add(new NaviationParentItems("HELP", 4, help));

	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggels
		mDrawerToggle.onConfigurationChanged(newConfig);
	}


	private void loadFragment(Fragment fragment, String title, int position){
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.content_frame, fragment);
		//fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
		setTitle(title);
		mDrawerLayout.closeDrawer(mDrawerList);
		mDrawerList.setItemChecked(position, true);
	}

	//ImageLoader.getInstance().destroy(); Writ this line in finish activity
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		FragmentManager fm = getSupportFragmentManager();

		if(keyCode==KeyEvent.KEYCODE_BACK)  
		{ 
			if(fm.getBackStackEntryCount()<1){
				finish();
			}
			try {
				this.getFragmentManager().popBackStack();
				fm.executePendingTransactions();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return super.onKeyDown(keyCode, event);
	}
}
