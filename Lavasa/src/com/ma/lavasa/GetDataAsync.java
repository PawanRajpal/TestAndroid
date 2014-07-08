package com.ma.lavasa;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ma.bean.ParseNearByPlace;


public class GetDataAsync<T> extends AsyncTask<Void, Void, List<?>>  {
	@Override
	protected void onPostExecute(List<?> result) {
		// TODO Auto-generated method stub
		completed.onTaskCompleted(result);	
		dialog.dismiss();
}


	private static final String TAG = "PostFetcher";
	public static final String SERVER_URL = "http://10.0.0.63:8080/Webservice/getplacedata";
	List<T> reqBean;
	Context mContext;
	String url;
	private Class<T> generalClass;
	OnTaskCompleted completed;
	JSONObject jsonObject;
	List<ParseNearByPlace> twitterUsers;
	ProgressDialog dialog;
	
	public GetDataAsync(Context context,String url,List<T> bean,Class<T> gClass,OnTaskCompleted onTaskCompleted){
		this.mContext=context;
		this.url=url;
		this.reqBean=bean;
		this.generalClass=gClass;
		this.completed=onTaskCompleted;
		dialog=new ProgressDialog(mContext);
		dialog.setTitle("Loading...");
		dialog.show();
	}
	@Override
	protected List<?> doInBackground(Void... params) {
		try {
			//Create an HTTP client
			HttpClient client = new DefaultHttpClient();
			HttpPost post = new HttpPost(SERVER_URL);
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(5);
            nameValuePairs.add(new BasicNameValuePair("lat", "18.407011"));
            nameValuePairs.add(new BasicNameValuePair("lng", "73.50626"));
            nameValuePairs.add(new BasicNameValuePair("type", "food"));
            nameValuePairs.add(new BasicNameValuePair("key", "AIzaSyAzAYkAVXp2GkQeCEY1GLyQw8N0XfNmsVo"));
            nameValuePairs.add(new BasicNameValuePair("rad", "500"));
           
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			//Perform the request and check the status code
			HttpResponse response = client.execute(post);
			
			
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == 200) {
				HttpEntity entity = response.getEntity();
				//System.out.println("getting response is*************************************/"+entity.toString());
				//System.out.println("Response is***********************************/"+EntityUtils.toString(entity));
				InputStream content = entity.getContent();
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(content), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}
					content.close();
					
					
					 jsonObject=new JSONObject(sb.toString());
					//json = 
					System.out.println("value of json parse is********************************/"+sb.toString());
				} catch (Exception e) {
					Log.e("Buffer Error", "Error converting result " + e.toString());
				}
				try {
					
					if (!jsonObject.isNull("nearby"))
					{
						//Getting students array
						JSONArray array = jsonObject.getJSONArray("nearby");
						
						//Creating empty json object for students loop
						JSONObject currentStudent;
						GsonBuilder gsonBuilder = new GsonBuilder();
//						//gsonBuilder.setDateFormat("M/d/yy hh:mm a");
						Gson gson = gsonBuilder.create();
						  twitterUsers = new ArrayList<ParseNearByPlace>();
						//looping all the students and adding them parsed one by one to the list
						for (int i = 0 ; i < array.length() ; i ++){
							currentStudent = array.getJSONObject(i);
							
							//Method to parse students and add them to the list
							//addCurrentStudentToStudentsList(currentStudent, studentsList);
							
							 T byPlaces = gson.fromJson(currentStudent.toString(), generalClass);
							 reqBean.add(byPlaces);
						}
					}
					System.out.println("size is************************************/"+reqBean.size());
					//Read the server response and attempt to parse it as JSON
//					Reader reader = new InputStreamReader(content);
//					
//					GsonBuilder gsonBuilder = new GsonBuilder();
//					//gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//					Gson gson = gsonBuilder.create();
//					 JsonParser jsonParser = new JsonParser();
//					 	      JsonArray userArray =  jsonParser.parse(reader).getAsJsonArray();
//					 	      List<ParseNearByPlace> twitterUsers = new ArrayList<ParseNearByPlace>();
//					 	      for ( JsonElement aUser : userArray ){
//					 	    	 ParseNearByPlace byPlaces = (gson.fromJson(aUser, ParseNearByPlace.class));
//					 	    	 twitterUsers.add(byPlaces);
//					 	      }
					
					//ParseNearByPlace byPlaces = (gson.fromJson(reader, ParseNearByPlace.class));
				//	System.out.println("value is************************************/"+byPlaces.getDistance());
			//		System.out.println("value is***********************************/"+byPlaces.size());
//					System.out.println("value is***********************************/"+byPlaces.get(0).getDistance());
//					System.out.println("value is***********************************/"+byPlaces.get(0).getPlace_id());
					content.close();
									//handlePostsList(posts);
				} catch (Exception ex) {
					Log.e(TAG, "Failed to parse JSON due to: " + ex);
					failedLoadingPosts();
				}
			} else {
				Log.e(TAG, "Server responded with status code: " + statusLine.getStatusCode());
				failedLoadingPosts();
			}
		} catch(Exception ex) {
			Log.e(TAG, "Failed to send HTTP POST request due to: " + ex);
			failedLoadingPosts();
		}
		return reqBean;

	} 
	
	private void failedLoadingPosts() {
//		runOnUiThread(new Runnable() {
//			@Override
//			public void run() {
//				Toast.makeText(getap, "Failed to load Posts. Have a look at LogCat.", Toast.LENGTH_SHORT).show();
//			}
//		});
	}


}
