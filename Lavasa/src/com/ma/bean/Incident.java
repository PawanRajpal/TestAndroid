package com.ma.bean;

public class Incident {
	
	private String catName;
	private String userLocation;
	private String description;
	private String imgURL;
	private String isSynced;
	
	public Incident(String catName, String userLocation, String description,
			String imgURL, String isSynced) {
		super();
		this.catName = catName;
		this.userLocation = userLocation;
		this.description = description;
		this.imgURL = imgURL;
		this.isSynced = isSynced;
		
		
	}
	public String getCatName() {
		return catName;
	}
	public void setCatName(String catName) {
		this.catName = catName;
	}
	public String getUserLocation() {
		return userLocation;
	}
	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImgURL() {
		return imgURL;
	}
	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}
	public String getIsSynced() {
		return isSynced;
	}
	public void setIsSynced(String isSynced) {
		this.isSynced = isSynced;
	}
	
}
