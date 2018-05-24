package com.eray.pbs.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * 记录同步的log日志信息 2016.07.22
 * 
 * @author ganqing
 * 
 */
@Entity
@Table(name = "pbs_synckqdatadoor_log")
public class SyncKqDataDoorLog extends BaseEntity {

	private Date syncDate = new Date(); // 同步数据的日期
	private String recIDS; // 同步的序列号
	private long maxrecID; // 同步的截止最大的序列号
	private String isSet; // 是否将标识位置1,Y/N
	private Date lastUpdate = new Date(); //最后一次的更新时间

	public SyncKqDataDoorLog() {
	}

	public SyncKqDataDoorLog(String recIDS, long maxrecID, String isSet) {
		this.recIDS = recIDS;
		this.maxrecID = maxrecID;
		this.isSet = isSet;
	}
	
	@Column(name = "lastupdate_")
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Column(name = "syncdate_")
	public Date getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}

	@Column(name = "recids_")
	public String getRecIDS() {
		return recIDS;
	}

	public void setRecIDS(String recIDS) {
		this.recIDS = recIDS;
	}

	@Column(name = "maxrecid_")
	public long getMaxrecID() {
		return maxrecID;
	}

	public void setMaxrecID(long maxrecID) {
		this.maxrecID = maxrecID;
	}

	@Column(name = "isset_")
	public String getIsSet() {
		return isSet;
	}

	public void setIsSet(String isSet) {
		this.isSet = isSet;
	}

	@Override
	public String toString() {
		return "SyncKqDataDoorLog [syncDate=" + syncDate + ", recIDS=" + recIDS
				+ ", maxrecID=" + maxrecID + ", isSet=" + isSet + "]";
	}
	
	

}
