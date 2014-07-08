package com.ma.bean;

public class PlaceReview {
	
	private String userId;
	private int reviewId;
	private String reviewTitle;
	private String reviewDescription;
	private double reviewRating;
	private String reviewDate;
	
	public PlaceReview(String userId, int reviewId, String reviewTitle,
			String reviewDescription, double reviewRating, String reviewDate) {
		super();
		this.userId = userId;
		this.reviewId = reviewId;
		this.reviewTitle = reviewTitle;
		this.reviewDescription = reviewDescription;
		this.reviewRating = reviewRating;
		this.reviewDate = reviewDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public int getReviewId() {
		return reviewId;
	}

	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}

	public String getReviewTitle() {
		return reviewTitle;
	}

	public void setReviewTitle(String reviewTitle) {
		this.reviewTitle = reviewTitle;
	}

	public String getReviewDescription() {
		return reviewDescription;
	}

	public void setReviewDescription(String reviewDescription) {
		this.reviewDescription = reviewDescription;
	}

	public double getReviewRating() {
		return reviewRating;
	}

	public void setReviewRating(double reviewRating) {
		this.reviewRating = reviewRating;
	}

	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}


}
