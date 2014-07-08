package com.ma.bean;

public class PlanDetail {

	private String placeName;
	private String placeAddress;
	private String placePhoneNum;
	private float placeRating;
	
	public PlanDetail(String placeName, String placeAddress,
			String placePhoneNum, float placeRating) {
		super();
		this.placeName = placeName;
		this.placeAddress = placeAddress;
		this.placePhoneNum = placePhoneNum;
		this.placeRating = placeRating;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceAddress() {
		return placeAddress;
	}

	public void setPlaceAddress(String placeAddress) {
		this.placeAddress = placeAddress;
	}

	public String getPlacePhoneNum() {
		return placePhoneNum;
	}

	public void setPlacePhoneNum(String placePhoneNum) {
		this.placePhoneNum = placePhoneNum;
	}

	public float getPlaceRating() {
		return placeRating;
	}

	public void setPlaceRating(float placeRating) {
		this.placeRating = placeRating;
	}
	
}
