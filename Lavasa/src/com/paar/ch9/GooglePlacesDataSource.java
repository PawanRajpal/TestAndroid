package com.paar.ch9;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ma.lavasa.R;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

/**
 * This class extends DataSource to fetch data from Google Places.
 * 
 * @author Justin Wetherell <phishman3579@gmail.com>
 */
public class GooglePlacesDataSource extends NetworkDataSource {

	//private static final String URL = "https://maps.googleapis.com/maps/api/place/search/json?";
	private static final String URL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?";
	
	//private static final String TYPES = "hotel|airport|amusement_park|aquarium|art_gallery|bus_station|campground|car_rental|city_hall|embassy|establishment|hindu_temple|local_governemnt_office|mosque|museum|night_club|park|place_of_worship|police|post_office|stadium|spa|subway_station|synagogue|taxi_stand|train_station|travel_agency|University|zoo";
	public static  String TYPES="" ;
	private static String key = null;
	public static Bitmap icon = null;

	public GooglePlacesDataSource(Resources res) {
		if (res == null) throw new NullPointerException();

		key ="AIzaSyAQvzugukuVMYA_-DaxpwXKeMlZl1IpSZU";

		createIcon(res);
	}

	protected void createIcon(Resources res) {
		if (res == null) throw new NullPointerException();

		icon = BitmapFactory.decodeResource(res, R.drawable.food);
	}

	@Override
	public String createRequestURL(double lat, double lon, double alt, float radius, String locale) {
		try {
			return URL + "location="+lat+","+lon+"&radius="+(radius*1000.0f)+"&types="+TYPES+"&sensor=true&key="+key;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public List<Marker> parse(String URL) {
		if (URL == null) throw new NullPointerException();

		InputStream stream = null;
		stream = getHttpGETInputStream(URL);
		if (stream == null) throw new NullPointerException();

		String string = null;
		string = getHttpInputString(stream);
		if (string == null) throw new NullPointerException();

		JSONObject json = null;
		try {
			json = new JSONObject(string);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (json == null) throw new NullPointerException();

		return parse(json);
	}

	@Override
	public List<Marker> parse(JSONObject root) {
		if (root == null) throw new NullPointerException();

		JSONObject jo = null;
		JSONArray dataArray = null;
		List<Marker> markers = new ArrayList<Marker>();

		try {
			if (root.has("results")) dataArray = root.getJSONArray("results");
			if (dataArray == null) return markers;
			int top = Math.min(MAX, dataArray.length());
			for (int i = 0; i < top; i++) {
				jo = dataArray.getJSONObject(i);
				Marker ma = processJSONObject(jo);
				if (ma != null) markers.add(ma);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return markers;
	}
	
	private Marker processJSONObject(JSONObject jo) {
		if (jo == null) throw new NullPointerException();

		if (!jo.has("geometry")) throw new NullPointerException();

		Marker ma = null;
		//System.out.println("Json value is**********************************/"+jo.toString());
		try {
			Double lat = null, lon = null;
			float rating=0;
			String vicinity="";
			String user="";
			if (!jo.isNull("geometry")) {
				JSONObject geo = jo.getJSONObject("geometry");
				JSONObject coordinates = geo.getJSONObject("location");
				lat = Double.parseDouble(coordinates.getString("lat"));
				lon = Double.parseDouble(coordinates.getString("lng"));
			}
			if (lat != null) {
				 user = jo.getString("name");
//				if(user.length()>10){
//					user=user.substring(0, 10)+"...";
//				}
				
			}
			if (!jo.isNull("rating")) {
				//JSONObject rate = jo.getJSONObject("rating");
				rating=Float.parseFloat(jo.getString("rating"));
				//System.out.println("rating is**************************************/"+rating);
			}
			if (!jo.isNull("vicinity")) {
				//JSONObject rate = jo.getJSONObject("rating");
				vicinity=(jo.getString("vicinity"));
				//System.out.println("vicinity is**************************************/"+vicinity);
			}
			
			ma = new IconMarker(user, lat, lon, 0, Color.RED, icon,vicinity,rating);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ma;
	}
}