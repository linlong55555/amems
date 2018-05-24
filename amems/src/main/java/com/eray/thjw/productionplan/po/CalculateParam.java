package com.eray.thjw.productionplan.po;

public class CalculateParam {
	 
	/**
	 * 监控项目编号
	 */
	private String jkxmbh = null;
	/**
	 * 实际值
	 */
	private String sj = null;
	/**
	 * 计划值
	 */
	private String jkz = null;
	
	public CalculateParam() {
		 
	}
	 
	public String getJkxmbh() {
		return jkxmbh;
	}
	public void setJkxmbh(String jkxmbh) {
		this.jkxmbh = jkxmbh;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}
	public String getJkz() {
		return jkz;
	}
	public void setJkz(String jkz) {
		this.jkz = jkz;
	}
	 
}