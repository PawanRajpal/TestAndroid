package com.ma.database;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.maps.model.LatLng;
import com.ma.bean.Incident;
import com.ma.bean.ItemsBean;
import com.ma.bean.Keyword;
import com.ma.bean.NearByPlaceBean;
import com.ma.bean.PlaceDetail;
import com.ma.bean.PlaceReview;
import com.ma.bean.PlanBean;
import com.ma.bean.PlanDetail;
import com.ma.bean.PlanDetailItenerary;
import com.ma.bean.PlanDetailMain;

public class LavasaDatabase {

	private static LavasaDatabase instance = null;

	public static SQLiteDatabase myDataBase = null;
	public static final String DB_PATH = "/data/data/com.ma.lavasa/";
	public static final String DB_NAME = "lavasa_web_service.sqlite";
	public  final String COLUMN_ID = "_id";
	public  final String COLUMN_PRODUCTNAME = "productname";
	public  final String COLUMN_QUANTITY = "quantity";

	private final String place_table_name="place";

	public synchronized static LavasaDatabase getInstance() {
		if (instance == null) {
			instance = new LavasaDatabase();

			openDataBase();
		}
		return instance;
	}

	private static void openDataBase() throws SQLException {

		// Open the database
		String myPath = DB_PATH + DB_NAME;
		myDataBase = SQLiteDatabase.openDatabase(myPath, null,
				SQLiteDatabase.OPEN_READWRITE);

	}

	public ArrayList<ItemsBean> getItemsData(String cat) {

		ArrayList<ItemsBean> itemsData = new ArrayList<ItemsBean>();
		Cursor cursor = null;
		ItemsBean obj = null;
		String sql = "";

		// sql="Select action_item_id,action_item_name,image from  action_item WHERE appliance_app_flag LIKE 'A' ORDER BY UPPER(action_item_name)";
		if(cat=="all"){
			String[] columns = new String[] { "id", "place_name", "formatted_address", "icon", "overall_rating", "lat", "lng" };

			cursor = myDataBase.query(place_table_name, columns, null, null, null, null, null);
			//sql ="Select * FROM tbl_places WHERE " + COLUMN_PRODUCTNAME + " =  \"" + productname + "\"";
		}
		else{
			sql = "Select id, place_name, formatted_address, icon, overall_rating, lat , lng from place WHERE place_type=\""+ cat+"\"";
			cursor = myDataBase.rawQuery(sql, null);
		}

		if (cursor != null) {

			if (cursor.moveToFirst()) {
				do {
					obj = new ItemsBean();
					obj.setId(cursor.getInt(0));
					obj.setTitle(cursor.getString(1));
					obj.setDesc(cursor.getString(2));
					obj.setImgUrl(cursor.getString(3));
					obj.setRating(cursor.getFloat(4));
					obj.setLatitude(cursor.getDouble(5));
					obj.setLongitud(cursor.getDouble(6));
					itemsData.add(obj);
				} while (cursor.moveToNext());
			}
		}

		return itemsData;

	}

	/** Incident reporting Queries */

	public Incident getIncident(){
		Incident incidents = null;
		String sql = "select * from tbl_reportincident";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			incidents = new Incident(cursor.getString(cursor.getColumnIndex("cat_name")),
					cursor.getString(cursor.getColumnIndex("user_location")),
					cursor.getString(cursor.getColumnIndex("description")),
					cursor.getString(cursor.getColumnIndex("img_url")),
					cursor.getString(cursor.getColumnIndex("is_synced")));

		}
		cursor.close();
		return incidents;
	}

	public boolean insertIncidentInfo(String catName, String userLocation, String description, String imgUrl, String isSynced) {
		int id= getIncidentID();
		ContentValues content = new ContentValues();
		content.put("incident_id", id);
		content.put("cat_name", catName);
		content.put("user_location", userLocation);
		content.put("description", description);
		content.put("img_url", imgUrl);
		content.put("is_synced", isSynced);
		myDataBase.insert("tbl_reportincident", null, content);
		return true;
	}

	public int getIncidentID() {
		String id = null;
		String sql = "select max(incident_id) + 1 as newId from tbl_reportincident";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			id = cursor.getString(cursor.getColumnIndex("newId"));
		}
		cursor.close();
		try {
			Integer.parseInt(id);
		} catch (Exception e) {
			id = "1";
		}
		return Integer.parseInt(id);
	}




	/**
	 * 
	 * @param placeId
	 * @return placeDetails
	 */

	public PlaceDetail  getPlaceDetail(int placeId){
		PlaceDetail placeDetails = null;
		String sql = "select * from place where id =" + placeId;
		Cursor cursor = myDataBase.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			placeDetails = new PlaceDetail(
					cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("user_location")),
					cursor.getString(cursor.getColumnIndex("place_type")),
					cursor.getString(cursor.getColumnIndex("place_name")),
					cursor.getString(cursor.getColumnIndex("place_reference_id")),
					cursor.getDouble(cursor.getColumnIndex("lat")),
					cursor.getDouble(cursor.getColumnIndex("lng")),
					cursor.getString(cursor.getColumnIndex("formatted_address")),
					cursor.getString(cursor.getColumnIndex("formatted_phone_number")),
					cursor.getInt(cursor.getColumnIndex("price_level")),
					cursor.getString(cursor.getColumnIndex("open_now")),
					cursor.getString(cursor.getColumnIndex("icon")),
					cursor.getString(cursor.getColumnIndex("gallery_url")),
					cursor.getString(cursor.getColumnIndex("gplus_url")),
					cursor.getString(cursor.getColumnIndex("gplus_url")),
					cursor.getString(cursor.getColumnIndex("vicinity")),
					cursor.getFloat(cursor.getColumnIndex("overall_rating")),
					cursor.getInt(cursor.getColumnIndex("is_proximity")),
					cursor.getInt(cursor.getColumnIndex("is_featured")),
					cursor.getInt(cursor.getColumnIndex("is_inside360")));
		}
		cursor.close();
		return placeDetails;
	}
	//
	//	public LatLng getLocationFromID(int id){
	//
	//		double lat=0.0,lng=0.0;
	//		String sql = "Select lat,lng from place where id=\""+ id+"\"";
	//		Cursor cursor = myDataBase.rawQuery(sql, null);
	//		if (cursor != null) {
	//
	//			if (cursor.moveToFirst()) {
	//				do {
	//					lat=(cursor.getDouble(0));
	//					lng=(cursor.getDouble(1));
	//				} while (cursor.moveToNext());
	//			}
	//		}
	//		return new LatLng(lat,lng);
	//	}

	public ArrayList<PlaceDetail> getMapPlaceDetails(ArrayList<Integer> id){


		ArrayList<PlaceDetail> placeDetails=new ArrayList<PlaceDetail>();

		String sql;
		//String sql = "Select lat,lng from place where id=\""+ id+"\"";

		Iterator<Integer> iterator=id.iterator();


		while (iterator.hasNext()){
			sql = "select place_name,formatted_address,lat,lng,overall_rating from place where id=\""+ iterator.next()+"\"";
			// System.out.println("reference id are*******************************************/"+iterator.next());
			Cursor cursor = myDataBase.rawQuery(sql, null);
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {

				placeDetails.add(new PlaceDetail(cursor.getString(0), cursor.getString(1),cursor.getDouble(2), cursor.getDouble(3), cursor.getFloat(4)));


				cursor.close();


			}
		}

		return placeDetails;
	}



	public boolean insertPlaceReview(int id, String userId, double rating, String reviewTilte, String review, String date) {
		ContentValues content = new ContentValues();
		content.put("id", id);
		content.put("user_profile_name", userId);
		content.put("rating", rating);
		content.put("review_title", reviewTilte);
		content.put("review", review);
		content.put("added_on", date);
		myDataBase.insert("review", null, content);
		return true;
	}


	public ArrayList<PlaceReview> getPlaceReview(int placeId){
		ArrayList<PlaceReview> placeReviews = new ArrayList<PlaceReview>();
		String sql = "select * from review where id=" + placeId;
		Cursor cursor = myDataBase.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			placeReviews.add(new PlaceReview(
					cursor.getString(cursor.getColumnIndex("user_profile_name")),
					cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("review_title")),
					cursor.getString(cursor.getColumnIndex("review")),
					cursor.getDouble(cursor.getColumnIndex("rating")),
					cursor.getString(cursor.getColumnIndex("added_on"))));
		}
		cursor.close();
		return placeReviews;
	}

	/** Method to get List of Packages in PLan Segment  */
	public ArrayList<PlanBean> getPackageName(int keyword_id){
		ArrayList<PlanBean> planData = new ArrayList<PlanBean>();
		String sql = "";
		if(keyword_id==3){
			sql = "select plan.* from plan inner join plan_keyword on plan.id = plan_keyword.plan_id where plan_keyword.[keyword_id]";	
		} else {

			sql = "select plan.* from plan inner join plan_keyword on plan.id = plan_keyword.plan_id where plan_keyword.[keyword_id]="+keyword_id;	
		}

		Cursor cursor = myDataBase.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			planData.add(new PlanBean(
					cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getInt(cursor.getColumnIndex("duration")),
					cursor.getString(cursor.getColumnIndex("plan_name")),
					cursor.getString(cursor.getColumnIndex("description")),
					cursor.getString(cursor.getColumnIndex("plan_img_url"))));
		}
		cursor.close();
		return planData;
	}

	/** Method to get List of Packages in PLan Segment  */
	public ArrayList<PlanDetail> getPlanDetail(int planId){
		ArrayList<PlanDetail> planDetails = new ArrayList<PlanDetail>();
		String sql = "select place.* from plan_itinerary inner join itinerary on plan_itinerary.itinerary_id=itinerary.id inner join place on itinerary.place_id=place.id where plan_itinerary.[plan_id]="+planId;
		Cursor cursor = myDataBase.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			planDetails.add(new PlanDetail(
					cursor.getString(cursor.getColumnIndex("place_name")),
					cursor.getString(cursor.getColumnIndex("formatted_address")),
					cursor.getString(cursor.getColumnIndex("formatted_phone_number")),
					cursor.getFloat(cursor.getColumnIndex("overall_rating"))));
		}
		cursor.close();
		return planDetails;
	}

	/**Sunny*/
	public ArrayList<Keyword> getAttractionKeywords(){

		ArrayList<Keyword> keywords = new ArrayList<Keyword>();

		String sql = "select a.[keyword_name] as keyword, a.[id] as placeid, b.[id] as keywordid from keyword a, place_keyword b where a.[id] = b.[keyword_id] group by [keyword_id]";

		Cursor cursor = myDataBase.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			keywords.add(new Keyword(
					cursor.getString(cursor.getColumnIndex("keyword")),
					cursor.getInt(cursor.getColumnIndex("placeid")),
					cursor.getInt(cursor.getColumnIndex("keywordid"))));
		}
		cursor.close();
		return keywords;
	}


	public ArrayList<Keyword> getPlanKeywords(){

		ArrayList<Keyword> keywords = new ArrayList<Keyword>();

		String sql = "select a.[keyword_name] as keyword, a.[id] as keywordid, b.[id] as planid from keyword a, plan_keyword b where a.[id] = b.[keyword_id] group by [keyword_id]";

		Cursor cursor = myDataBase.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			keywords.add(new Keyword(
					cursor.getString(cursor.getColumnIndex("keyword")),
					cursor.getInt(cursor.getColumnIndex("keywordid")),
					cursor.getInt(cursor.getColumnIndex("planid"))));
		}
		cursor.close();
		return keywords;
	}



	public ArrayList<NearByPlaceBean> getNearByPlace(ArrayList<String> ref_id){

		ArrayList<NearByPlaceBean> nearByPlace = new ArrayList<NearByPlaceBean>();
		System.out.println("siz eof ref id is**************************/"+ref_id.size());
		String sql;

		Iterator<String> iterator=ref_id.iterator();


		while (iterator.hasNext()){
			sql = "select place_name,formatted_address,lat,lng,overall_rating from place where place_reference_id=\""+ iterator.next()+"\"";
			// System.out.println("reference id are*******************************************/"+iterator.next());
			Cursor cursor = myDataBase.rawQuery(sql, null);
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
				NearByPlaceBean nearByPlaceBean=new NearByPlaceBean();

				nearByPlaceBean.setName(cursor.getString(0));
				nearByPlaceBean.setAddress(cursor.getString(1));
				nearByPlaceBean.setLat(cursor.getDouble(2));
				nearByPlaceBean.setLng(cursor.getDouble(3));
				nearByPlaceBean.setRating(cursor.getFloat(4));
				nearByPlace.add(nearByPlaceBean);

				cursor.close();


			}
		}
		return nearByPlace;

	}



	public ArrayList<PlanDetailItenerary> getPlanDetailItenerary(int planId){
		ArrayList<PlanDetailItenerary> planDetails = new ArrayList<PlanDetailItenerary>();
		String sql = "select place.* from plan_itinerary inner join itinerary on plan_itinerary.itinerary_id=itinerary.id inner join place on itinerary.place_id=place.id where plan_itinerary.[plan_id]=" +planId+ " group by [id]";
		Cursor cursor = myDataBase.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			planDetails.add(new PlanDetailItenerary(
					cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("place_name")),
					cursor.getString(cursor.getColumnIndex("formatted_address")),
					cursor.getString(cursor.getColumnIndex("formatted_phone_number")),
					cursor.getFloat(cursor.getColumnIndex("overall_rating"))));
		}
		cursor.close();
		return planDetails;
	}


	public PlanDetailMain getPlanDetailMain(int PlanId){
		PlanDetailMain planDetailMain = null;
		String sql = "select * from plan where id = " + PlanId; 
		Cursor cursor = myDataBase.rawQuery(sql, null);
		for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
			planDetailMain = new PlanDetailMain(
					cursor.getInt(cursor.getColumnIndex("id")),
					cursor.getString(cursor.getColumnIndex("plan_name")),
					cursor.getString(cursor.getColumnIndex("duration")),
					cursor.getString(cursor.getColumnIndex("description")),
					cursor.getString(cursor.getColumnIndex("plan_img_url")));
		}
		cursor.close();
		return planDetailMain;
	}

}
