package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * activity type 与workcenter之间的关系 2016.08.18
 * 
 * @author ganqing
 * 
 */
@Entity
@Table(name = "pbs_activity")
public class ActivityTypeReWorkCenter extends BaseEntity {
	private String workCenter; // 工作组
	private String activityType; // activity type
	private String remarkDate; //同步时间
	
	public ActivityTypeReWorkCenter() {
		
	}

	public ActivityTypeReWorkCenter(String workCenter, String activityType,
			String remarkDate) {
		this.workCenter = workCenter;
		this.activityType = activityType;
		this.remarkDate = remarkDate;
	}

	@Column(name = "workcenter_")
	public String getWorkCenter() {
		return workCenter;
	}

	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}

	@Column(name = "activitytype_")
	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	@Column(name = "remarkdate_")
	public String getRemarkDate() {
		return remarkDate;
	}

	public void setRemarkDate(String remarkDate) {
		this.remarkDate = remarkDate;
	}

	@Override
	public String toString() {
		return "ActivityTypeReWorkCenter [workCenter=" + workCenter
				+ ", activityType=" + activityType + ", remarkDate="
				+ remarkDate + "]";
	}

	
	

}
