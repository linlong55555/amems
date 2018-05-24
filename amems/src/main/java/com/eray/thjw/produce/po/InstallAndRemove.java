package com.eray.thjw.produce.po;

import java.util.Date;

/**
 * 
 * @Description 拆装信息对象（无数据库实体对应）
 * @CreateTime 2017年11月28日 上午10:41:57
 * @CreateBy 徐勇
 */
public class InstallAndRemove{
   
	/** 装机清单id */
	private String id;
	
	/** 拆装时间  */
	private Date operTime;
	
	/** 拆装类型 */
	private Integer installedStatus;
	
	/** 工单拆装记录ID */
	private String workIOId;
	
	/** 库存ID */
	private String kcid;
	
	/** 库存履历id */
	private String kcllid;
	
	/** 是否修改 */
	private boolean isEdit;

	public InstallAndRemove() {
		super();
	}
	
	public InstallAndRemove(String id, Date operTime, Integer installedStatus, String workIOId) {
		super();
		this.id = id;
		this.operTime = operTime;
		this.installedStatus = installedStatus;
		this.isEdit = false;
		this.workIOId = workIOId;
	}
	

	public InstallAndRemove(String id, Date operTime, Integer installedStatus, String workIOId, String kcid, String kcllid) {
		super();
		this.id = id;
		this.operTime = operTime;
		this.installedStatus = installedStatus;
		this.isEdit = false;
		this.workIOId = workIOId;
		this.kcid = kcid;
		this.kcllid = kcllid;
	}
	
	public InstallAndRemove(String id, Date operTime, Integer installedStatus, boolean isEdit, String workIOId, String kcid, String kcllid) {
		super();
		this.id = id;
		this.operTime = operTime;
		this.installedStatus = installedStatus;
		this.isEdit = isEdit;
		this.workIOId = workIOId;
		this.kcid = kcid;
		this.kcllid = kcllid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getOperTime() {
		return operTime;
	}

	public void setOperTime(Date operTime) {
		this.operTime = operTime;
	}

	public Integer getInstalledStatus() {
		return installedStatus;
	}

	public void setInstalledStatus(Integer installedStatus) {
		this.installedStatus = installedStatus;
	}

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
	}

	public String getWorkIOId() {
		return workIOId;
	}

	public void setWorkIOId(String workIOId) {
		this.workIOId = workIOId;
	}

	public String getKcid() {
		return kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid;
	}

	public String getKcllid() {
		return kcllid;
	}

	public void setKcllid(String kcllid) {
		this.kcllid = kcllid;
	}
	
}