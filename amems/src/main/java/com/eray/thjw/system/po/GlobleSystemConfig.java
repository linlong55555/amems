package com.eray.thjw.system.po;

import com.eray.thjw.po.BizEntity;


/**
 * t_sys_config表
 *
 */
public class GlobleSystemConfig extends BizEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String syscode;
	
	private String pzbm;
	
	private String ms;
	
	private String value1;
	
	private String value2;
	
	private String value3;
	
	private Integer zt;

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public String getPzbm() {
		return pzbm;
	}

	public void setPzbm(String pzbm) {
		this.pzbm = pzbm;
	}
	
	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}

	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getValue3() {
		return value3;
	}

	public void setValue3(String value3) {
		this.value3 = value3;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}
			
}