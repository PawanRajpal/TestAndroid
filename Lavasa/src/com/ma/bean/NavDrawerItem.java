package com.ma.bean;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
public class NavDrawerItem implements Serializable {

	private String title;
	private int icon;
	private List<NavDrawerChildItem> navDrawerChildItems;
	
	
	public NavDrawerItem() {
		super();
	}

	public NavDrawerItem(String title, int icon, List<NavDrawerChildItem> navDrawerChildItems) {
		this.title = title;
		this.icon = icon;
		this.navDrawerChildItems = navDrawerChildItems;
	}

	public NavDrawerItem(String title, int icon) {
		this.title = title;
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public List<NavDrawerChildItem> getNavDrawerChildItems() {
		return navDrawerChildItems;
	}

	public void setNavDrawerChildItems(List<NavDrawerChildItem> navDrawerChildItems) {
		this.navDrawerChildItems = navDrawerChildItems;
	}
	

}
