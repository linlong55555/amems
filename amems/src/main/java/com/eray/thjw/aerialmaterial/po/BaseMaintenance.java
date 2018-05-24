package com.eray.thjw.aerialmaterial.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

public class BaseMaintenance extends BizEntity{
	
	private String id;
	
	private String dprtcode;
	
	private String jdms;
	
	private String ssjgdm;
	
	private Date whsj;
	
	private Integer zt;
	
	private String whrid;
	
	private User whr;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getJdms() {
		return jdms;
	}

	public void setJdms(String jdms) {
		this.jdms = jdms;
	}

	public String getSsjgdm() {
		return ssjgdm;
	}

	public void setSsjgdm(String ssjgdm) {
		this.ssjgdm = ssjgdm;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}	

}
