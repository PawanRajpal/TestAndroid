package com.ma.bean;

public class PlanBean {

	private int pkg_id, duration;
	private String pkg_name, description,image_url;
	
		public PlanBean(int pkg_id, int duration, String pkg_name, String description,String img_url) {
		super();
		this.pkg_id = pkg_id;
		this.duration = duration;
		this.pkg_name = pkg_name;
		this.description = description;
		this.image_url=img_url;
	}

		public String getImage_url() {
			return image_url;
		}

		public void setImage_url(String image_url) {
			this.image_url = image_url;
		}

		public int getPkg_id() {
			return pkg_id;
		}

		public void setPkg_id(int pkg_id) {
			this.pkg_id = pkg_id;
		}

		public int getDuration() {
			return duration;
		}

		public void setDuration(int duration) {
			this.duration = duration;
		}

		public String getPkg_name() {
			return pkg_name;
		}

		public void setPkg_name(String pkg_name) {
			this.pkg_name = pkg_name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		
		
}
