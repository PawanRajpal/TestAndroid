package com.ma.bean;

public class ItemsBean {

	private String title,desc,imgUrl;
	private float rating;
	private double lat,lng;
	private int id;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public float getRating() {
		return rating;
	}
	public void setRating(float rating) {
		this.rating = rating;
	}
	public double getLatitude() {
		return lat;
	}
	public void setLatitude(double la) {
		this.lat = la;
	}
	public double getLongitude() {
		return lng;
	}
	public void setLongitud(double ln) {
		this.lng = ln;
	}
	
	
}
