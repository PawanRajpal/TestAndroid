package com.ma.bean;

public class Keyword {

	private String keyword;
	private int keywordID;
	private int packageId;
	
	public Keyword(String keyword, int keywordID, int packageId) {
		super();
		this.keyword = keyword;
		this.keywordID = keywordID;
		this.packageId = packageId;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getKeywordID() {
		return keywordID;
	}
	public void setKeywordID(int keywordID) {
		this.keywordID = keywordID;
	}
	public int getPackageId() {
		return packageId;
	}
	public void setPackageId(int packageId) {
		this.packageId = packageId;
	}

}
