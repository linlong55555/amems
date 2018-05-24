package com.eray.thjw.productionplan.po;

public class PlaneInitData {
	private String fjzch;

	private String dprtcode;

	private String initXmbh;

	private String initXmms;

	private String initValue;

	private Integer jsbs;

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch == null ? null : fjzch.trim();
	}

	public String getInitXmbh() {
		return initXmbh;
	}

	public void setInitXmbh(String initXmbh) {
		this.initXmbh = initXmbh == null ? null : initXmbh.trim();
	}

	public String getInitXmms() {
		return initXmms;
	}

	public void setInitXmms(String initXmms) {
		this.initXmms = initXmms == null ? null : initXmms.trim();
	}

	public String getInitValue() {
		return initValue;
	}

	public void setInitValue(String initValue) {
		this.initValue = initValue == null ? null : initValue.trim();
	}

	public Integer getJsbs() {
		return jsbs;
	}

	public void setJsbs(Integer jsbs) {
		this.jsbs = jsbs;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

}