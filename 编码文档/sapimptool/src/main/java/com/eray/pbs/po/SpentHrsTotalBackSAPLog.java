package com.eray.pbs.po;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * 将为关闭的工包回传至SAP记录,回传未关闭的工单的未关闭的工单
 * @author ganqing
 *
 */
@Entity
@Table(name = "pbs_spenthrstotalbacksap_log")
public class SpentHrsTotalBackSAPLog extends BaseEntity {
	private String spentMonth; // 统计的月份
	private String rid; // 工包编号
	private BigDecimal totalhrs; // 未关闭的总工时
	private BigDecimal backsaphrs; //回传至sap工时
	private String batcNum; //批次
	private Date statisticalDate; //统计的时间（记录该批数据记录的时间）
	
	@Column(name="statisticaldate_")
	public Date getStatisticalDate() {
		return statisticalDate;
	}

	public void setStatisticalDate(Date statisticalDate) {
		this.statisticalDate = statisticalDate;
	}

	@Column(name="batcnum_")
	public String getBatcNum() {
		return batcNum;
	}

	public void setBatcNum(String batcNum) {
		this.batcNum = batcNum;
	}

	@Column(name="spentmonth_")
	public String getSpentMonth() {
		return spentMonth;
	}

	public void setSpentMonth(String spentMonth) {
		this.spentMonth = spentMonth;
	}

	@Column(name="rid_")
	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	@Column(name="totalhrs_")
	public BigDecimal getTotalhrs() {
		return totalhrs;
	}

	public void setTotalhrs(BigDecimal totalhrs) {
		this.totalhrs = totalhrs;
	}

	@Column(name="backsaphrs_")
	public BigDecimal getBacksaphrs() {
		return backsaphrs;
	}

	public void setBacksaphrs(BigDecimal backsaphrs) {
		this.backsaphrs = backsaphrs;
	}
}
