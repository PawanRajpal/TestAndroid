package com.ma.lavasa;

import com.google.android.gms.internal.ad;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {

	public static final String Shared_Pref = "Lavasa";
	public static SharedPreferences settings;
	public static SharedPreferences.Editor editor;
	public Context mContext;
	public static boolean isFromAR=false;
	
	
	public SharedPref(Context context){
		this.mContext=context;
	}
	
	public void setSensorAvailability(boolean avail) {
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		editor = settings.edit();
		editor.remove("isAvailable");
		editor.putBoolean("isAvailable", avail);
		editor.commit();
	}

	public boolean getSensorAvailability() {
		boolean isAvailable;
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		isAvailable = settings.getBoolean("isAvailable", false);
		return isAvailable;
	}
	
	public void setPlaceDetails12(String name,String address,float rate) {
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		editor = settings.edit();
		//editor.remove("place_name");
		editor.putString("place_name", name);
		editor.putString("place_addr", address);
		editor.putFloat("rating", rate);
		editor.commit();
	}

	public String getPlaceName12() {
		String title;
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		title = settings.getString("place_name", "");
		return title;
	}
	
	public String getPlaceAddr12() {
		String address;
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		address = settings.getString("place_addr", "");
		return address;
	}
	
	public float getRating12() {
		float rate;
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		rate = settings.getFloat("rating", 0);
		return rate;
	}
	
	public boolean isGoolgeLoggedIn(){
		boolean status;
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		status = settings.getBoolean("googleLogin", false);
		return status;
		
	}
	
	public void setGoogleLogin(boolean status){
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		editor = settings.edit();
		editor.putBoolean("googleLogin", status);
		editor.commit();
	}
	
	public void setFragmentforMap(String name) {
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		editor = settings.edit();
		//editor.remove("place_name");
		editor.putString("fragment_name", name);
		
		editor.commit();
	}

	public String getFragmentforMap() {
		String title;
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		title = settings.getString("fragment_name", "");
		return title;
	}
	
	public void setPlaceId(int placeId) {
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		editor = settings.edit();
		editor.putInt("placeId", placeId);
		editor.commit();
	}

	public int getPlaceId() {
		int placeId;
		settings = mContext.getSharedPreferences(Shared_Pref, 0);
		placeId = settings.getInt("placeId", 0);
		return placeId;
	}
	
}
