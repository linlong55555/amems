package com.eray.thjw.system.po;

import com.eray.thjw.po.BizEntity;

/**
 * t_sys_info表
 *
 */
public class GlobleSystemInfo extends BizEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String syscode;
	
	private String ms;
	
	private Integer zt;

	public String getSyscode() {
		return syscode;
	}

	public void setSyscode(String syscode) {
		this.syscode = syscode;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}
	
}