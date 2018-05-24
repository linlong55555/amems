package com.eray.pbs.vo;

/**
 * 工单及工序的相关元素 2016.08.02
 * @author ganqing
 *
 */
public class WorkAndOperation {
	
	private String currentWorkCenter; //当前工作组
	private String activityType; //工序的ActivityType
	
	public String getCurrentWorkCenter() {
		return currentWorkCenter;
	}
	public void setCurrentWorkCenter(String currentWorkCenter) {
		this.currentWorkCenter = currentWorkCenter;
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	@Override
	public String toString() {
		return "WorkAndOperation [currentWorkCenter=" + currentWorkCenter
				+ ", activityType=" + activityType + "]";
	}
	

	
	
}
