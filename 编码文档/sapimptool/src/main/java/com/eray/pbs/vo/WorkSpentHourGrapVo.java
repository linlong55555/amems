package com.eray.pbs.vo;

/**
 * 接收spent hour的信息 2017.01.10
 * @author ganqing
 *
 */
public class WorkSpentHourGrapVo {
	
	public WorkSpentHourGrapVo() {
	}

	public WorkSpentHourGrapVo(String wid, Double spentHour) {
		this.wid = wid;
		this.spentHour = spentHour;
	}

	private String wid; //工单编号
	
	private Double spentHour; //统计的spent hour信息

	public String getWid() {
		return wid;
	}

	public void setWid(String wid) {
		this.wid = wid;
	}

	public Double getSpentHour() {
		return spentHour;
	}

	public void setSpentHour(Double spentHour) {
		this.spentHour = spentHour;
	}
	
	
	
	

}
