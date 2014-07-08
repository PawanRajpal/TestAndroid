package com.ma.bean;

public class PlaceDetail {

	private int id;
	private String userLocation;
	private String placeType;
	private String placeName;
	private String placeRefId;
	private double latitude;
	private double longitude;
	private String address;
	private String phoneNumber;
	private int price;
	private String openNow;
	private String iconURL;
	private String galleryURL;
	private String gplusURL;
	private String websiteURL;
	private String vicinity;
	private float overallRating;
	private int isProximity;
	private int isFeatured;
	private int is360;

	public PlaceDetail(int id, String userLocation, String placeType,
			String placeName, String placeRefId, double latitude,
			double longitude, String address, String phoneNumber, int price,
			String openNow, String iconURL, String galleryURL, String gplusURL,
			String websiteURL, String vicinity, float overallRating,
			int isProximity, int isFeatured, int is360) {
		super();
		this.id = id;
		this.userLocation = userLocation;
		this.placeType = placeType;
		this.placeName = placeName;
		this.placeRefId = placeRefId;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.price = price;
		this.openNow = openNow;
		this.iconURL = iconURL;
		this.galleryURL = galleryURL;
		this.gplusURL = gplusURL;
		this.websiteURL = websiteURL;
		this.vicinity = vicinity;
		this.overallRating = overallRating;
		this.isProximity = isProximity;
		this.isFeatured = isFeatured;
		this.is360 = is360;
	}

	public PlaceDetail(String placeName,String address, double latitude,
			double longitude,  float overallRating) {
		super();
		

		this.placeName = placeName;

		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;

		this.overallRating = overallRating;

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserLocation() {
		return userLocation;
	}

	public void setUserLocation(String userLocation) {
		this.userLocation = userLocation;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceRefId() {
		return placeRefId;
	}

	public void setPlaceRefId(String placeRefId) {
		this.placeRefId = placeRefId;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getOpenNow() {
		return openNow;
	}

	public void setOpenNow(String openNow) {
		this.openNow = openNow;
	}

	public String getIconURL() {
		return iconURL;
	}

	public void setIconURL(String iconURL) {
		this.iconURL = iconURL;
	}

	public String getGalleryURL() {
		return galleryURL;
	}

	public void setGalleryURL(String galleryURL) {
		this.galleryURL = galleryURL;
	}

	public String getGplusURL() {
		return gplusURL;
	}

	public void setGplusURL(String gplusURL) {
		this.gplusURL = gplusURL;
	}

	public String getWebsiteURL() {
		return websiteURL;
	}

	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}

	public String getVicinity() {
		return vicinity;
	}

	public void setVicinity(String vicinity) {
		this.vicinity = vicinity;
	}

	public float getOverallRating() {
		return overallRating;
	}

	public void setOverallRating(float overallRating) {
		this.overallRating = overallRating;
	}

	public int getIsProximity() {
		return isProximity;
	}

	public void setIsProximity(int isProximity) {
		this.isProximity = isProximity;
	}

	public int getIsFeatured() {
		return isFeatured;
	}

	public void setIsFeatured(int isFeatured) {
		this.isFeatured = isFeatured;
	}

	public int getIs360() {
		return is360;
	}

	public void setIs360(int is360) {
		this.is360 = is360;
	}

}
