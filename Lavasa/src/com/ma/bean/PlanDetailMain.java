package com.ma.bean;

public class PlanDetailMain {

	private int planId;;
	private String planName;
	private String planDuration;
	private String planDesc;;
	private String planImg;
	
	
	public PlanDetailMain(int planId, String planName, String planDuration,
			String planDesc, String planImg) {
		super();
		this.planId = planId;
		this.planName = planName;
		this.planDuration = planDuration;
		this.planDesc = planDesc;
		this.planImg = planImg;
	}


	public int getPlanId() {
		return planId;
	}


	public void setPlanId(int planId) {
		this.planId = planId;
	}


	public String getPlanName() {
		return planName;
	}


	public void setPlanName(String planName) {
		this.planName = planName;
	}


	public String getPlanDuration() {
		return planDuration;
	}


	public void setPlanDuration(String planDuration) {
		this.planDuration = planDuration;
	}


	public String getPlanDesc() {
		return planDesc;
	}


	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}


	public String getPlanImg() {
		return planImg;
	}


	public void setPlanImg(String planImg) {
		this.planImg = planImg;
	}
	
}
