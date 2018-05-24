package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;

/**
 * 虚拟实体 存放部件号，归还数量，出库数
 * 
 * @author xu.yong
 * 
 */
public class Piecenumber {
	private String bjid;

	private BigDecimal dghsl;

	private BigDecimal cksl;

	public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
	}

	public BigDecimal getDghsl() {
		return dghsl;
	}

	public void setDghsl(BigDecimal dghsl) {
		this.dghsl = dghsl;
	}

	public BigDecimal getCksl() {
		return cksl;
	}

	public void setCksl(BigDecimal cksl) {
		this.cksl = cksl;
	}

}