package com.ma.activities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import com.google.android.gms.maps.model.MarkerOptions;
import com.ma.bean.ItemsBean;
import com.ma.bean.NearByPlaceBean;
import com.ma.bean.ParseNearByPlace;
import com.ma.bean.PlaceDetail;
import com.ma.database.LavasaDatabase;
import com.ma.lavasa.GetDataAsync;
import com.ma.lavasa.OnTaskCompleted;
import com.ma.lavasa.R;
import com.ma.lavasa.SharedPref;
import com.ma.location.GPSTracker;
import com.paar.ch9.ARData;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.TextView;

public class PlaceMapFragment extends Fragment implements  OnMarkerClickListener {

	View mapView;
	GoogleMap gMap;
	GPSTracker gpsTracker;
	Context mContext;
	LatLng myLoc;
	MapFragment mapFragment;
	Bundle bundle;
	//PlaceDetail placeDetail;
	SharedPref sharedPref;
	RelativeLayout linLayout;
	SlidingDrawer slidingDrawer;
	TextView title, address;
	RatingBar ratingBar;
	Button nearBy, getMeThere;
	LatLng markerLatLng;
	HashMap<String, Float> hashMap;
	ArrayList<NearByPlaceBean> itemsBeans;
	private boolean isOnMarker;
	GetDataAsync<ParseNearByPlace> getDataAsync;
	List<ParseNearByPlace> nearByPlacesList;
	ArrayList<PlaceDetail> placeDetail;
	ArrayList<Integer> placeIDArrayList;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mapView = inflater.inflate(R.layout.map_fragment, container, false);
		gpsTracker = new GPSTracker(getActivity());
		mapFragment = ((MapFragment) getActivity().getFragmentManager()
				.findFragmentById(R.id.map_marker_Fragment));
		gMap = mapFragment.getMap();
		mContext = mapView.getContext();
		
		
		bundle = this.getArguments();
		sharedPref = new SharedPref(getActivity());
		//String whichFragment=sharedPref.getFragmentforMap();
		nearByPlacesList=new ArrayList<ParseNearByPlace>();
		
		if(sharedPref.getFragmentforMap().equalsIgnoreCase("plan"))
			placeIDArrayList=bundle.getIntegerArrayList("placeId");
		else{
		int placeId = bundle.getInt("placeId");
		placeIDArrayList=new ArrayList<Integer>();
		placeIDArrayList.add(placeId);
		}
		
		placeDetail=LavasaDatabase.getInstance().getMapPlaceDetails(placeIDArrayList);
		
		markerLatLng= new LatLng(placeDetail.get(0).getLatitude(), placeDetail.get(0).getLongitude());
		title = (TextView) mapView.findViewById(R.id.title_map_deatils);
		address = (TextView) mapView.findViewById(R.id.address_map_details);
		ratingBar = (RatingBar) mapView
				.findViewById(R.id.map_places_rating_bar);
		nearBy = (Button) mapView.findViewById(R.id.map_near_by_place);
		getMeThere = (Button) mapView.findViewById(R.id.map_get_me_there);
		gMap.setMyLocationEnabled(true);

		slidingDrawer = (SlidingDrawer) mapView
				.findViewById(R.id.slidingDrawer1);
		gMap.setOnMapClickListener(new OnMapClickListener() {

			@Override
			public void onMapClick(LatLng point) {
				// TODO Auto-generated method stub

				isOnMarker = false;
				if (slidingDrawer.isOpened())
					slidingDrawer.animateClose();
			}
		});

		gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerLatLng, 12));

		gMap.getUiSettings().setCompassEnabled(true);
		
	
		gMap.setOnMarkerClickListener(this);

		for(int i=0;i<placeDetail.size();i++){
			markerLatLng=new LatLng(placeDetail.get(i).getLatitude(), placeDetail.get(i).getLongitude());
			gMap.addMarker(new MarkerOptions()

			.title(placeDetail.get(i).getPlaceName())
			.position(
					markerLatLng)
			// .snippet(sharedPref.getPlaceAddr())
			.icon(BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
		}
		

		getMeThere.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				//				getActivity().overridePendingTransition(android.R.anim.fade_in,
//						android.R.anim.fade_out);
				Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
						.parse("http://maps.google.com/maps?saddr="
								+ gpsTracker.getLatitude() + ","
								+ gpsTracker.getLongitude() + "&daddr="
								+ markerLatLng.latitude + ","
								+ markerLatLng.longitude));

				// System.out.println("final string is******************"+http://maps.google.com/maps?saddr=/"+gpsTracker.getLatitude() + ","+ gpsTracker.getLongitude() + "&daddr="+ markerLatLng.latitude + ","
				// + markerLatLng.longitude));
				navigation.setClassName("com.google.android.apps.maps",
						"com.google.android.maps.MapsActivity");
				mContext.startActivity(navigation);
			}
		});

		// Dummy Code Change when web serive come;
		

		nearBy.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getDataAsync=new GetDataAsync<ParseNearByPlace>(mContext, "dffv", nearByPlacesList, ParseNearByPlace.class, listener);
				getDataAsync.execute();
			}
		});
		return mapView;
	}


	
	private OnTaskCompleted listener = new OnTaskCompleted() {
	  

		@Override
		public void onTaskCompleted(List<?> values) {
			// TODO Auto-generated method stub
			
			List<ParseNearByPlace> nearByPlaces=(List<ParseNearByPlace>)values;
			System.out.println("value sin Map Fragment is**************************/"+nearByPlaces.size());
			ArrayList<String> ref_id=new ArrayList<String>();
			for(int i=0;i<nearByPlaces.size();i++){
				ref_id.add(nearByPlaces.get(i).getPlace_id());
				System.out.println("value sin Map Fragment is**************************/"+nearByPlaces.get(i).getDistance());
				System.out.println("value sin Map Fragment is**************************/"+nearByPlaces.get(i).getPlace_id());
				//nearByPlaces.get(i).getPlace_id();
			}
			System.out.println("ref_id size is *****************************/"+ref_id.size());
			itemsBeans = LavasaDatabase.getInstance().getNearByPlace(ref_id);
			
			slidingDrawer.animateClose();
			hashMap = new HashMap<String, Float>();
			System.out
					.println("size of item ban is************************/"
							+ itemsBeans.size());
			for (int i = 0; i < itemsBeans.size(); i++) {

				gMap.addMarker(new MarkerOptions()
						.position(
								new LatLng(itemsBeans.get(i).getLat(),
										itemsBeans.get(i).getLng()))
						.title(itemsBeans.get(i).getName())
						.snippet(itemsBeans.get(i).getAddress())
						.icon(BitmapDescriptorFactory
								.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

				hashMap.put(itemsBeans.get(i).getName(), itemsBeans.get(i)
						.getRating());
			}
		}
	};
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		MapFragment f = (MapFragment) getActivity().getFragmentManager()
				.findFragmentById(R.id.map_marker_Fragment);
		if (f != null)
			getActivity().getFragmentManager().beginTransaction().remove(f)
					.commit();
	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
		isOnMarker = true;
		if (!slidingDrawer.isOpened())
			slidingDrawer.animateOpen();

		else {
			slidingDrawer.animateClose();

			slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

				@Override
				public void onDrawerClosed() {
					// TODO Auto-generated method stub
					if (isOnMarker)
						slidingDrawer.animateOpen();
				}
			});
		}
for(int i=0;i<placeDetail.size();i++){
		if (marker.getTitle().equalsIgnoreCase(placeDetail.get(i).getPlaceName())) {
			title.setText(placeDetail.get(i).getPlaceName());
			address.setText(placeDetail.get(i).getAddress());
			ratingBar.setRating(placeDetail.get(i).getOverallRating());
		}
}
//		else {
//
//			title.setText(marker.getTitle());
//			address.setText(marker.getSnippet());
//			ratingBar.setRating(hashMap.get(marker.getTitle()));
//		}
		return false;
	}

	

}
