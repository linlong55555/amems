package com.eray.thjw.flightdata.po;

import java.util.Date;

/**
 * 飞行记录单还原指令表
 * @author hanwu
 *
 */
public class ReturnInstruction {
	
	/** id */
	private String id;
	
	/** 飞行记录单id */
	private String fxjldh;
	
	/** 对象类型 */
	private Integer dxlx;
	
	/** 对象id */
	private String dxid;
	
	/** 组织机构 */
	private String dprtCode;
	
	/** 维护人id */
	private String whrid;
	
	/** 维护时间 */
	private Date whsj;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFxjldh() {
		return fxjldh;
	}

	public void setFxjldh(String fxjldh) {
		this.fxjldh = fxjldh;
	}

	public Integer getDxlx() {
		return dxlx;
	}

	public void setDxlx(Integer dxlx) {
		this.dxlx = dxlx;
	}

	public String getDxid() {
		return dxid;
	}

	public void setDxid(String dxid) {
		this.dxid = dxid;
	}

	public String getDprtCode() {
		return dprtCode;
	}

	public void setDprtCode(String dprtCode) {
		this.dprtCode = dprtCode;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}
	
	
}
