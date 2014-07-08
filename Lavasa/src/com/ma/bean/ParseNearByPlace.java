package com.ma.bean;

import com.google.gson.annotations.SerializedName;

public class ParseNearByPlace {
	
@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "place"+this.place+"Distance"+this.dist;
	}
@SerializedName("place")	
private String place;
@SerializedName("dist")	
private String dist;

public String getPlace_id() {
	return place;
}
public void setPlace_id(String place_id) {
	this.place = place_id;
}
public String getDistance() {
	return dist;
}
public void setDistance(String distance) {
	this.dist = distance;
}



}
