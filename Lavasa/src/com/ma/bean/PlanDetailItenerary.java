package com.ma.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PlanDetailItenerary implements Serializable {

	private int placeId;
	private String placeName;
	private String placeAddress;
	private String placePhoneNum;
	private float placeRating;
	
	public PlanDetailItenerary(int placeId, String placeName,
			String placeAddress, String placePhoneNum, float placeRating) {
		super();
		this.placeId = placeId;
		this.placeName = placeName;
		this.placeAddress = placeAddress;
		this.placePhoneNum = placePhoneNum;
		this.placeRating = placeRating;
	}

	public int getPlaceId() {
		return placeId;
	}

	public void setPlaceId(int placeId) {
		this.placeId = placeId;
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
