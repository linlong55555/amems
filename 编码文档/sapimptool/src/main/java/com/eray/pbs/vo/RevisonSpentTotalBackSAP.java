package com.eray.pbs.vo;


/**
 * 回传至sap的数据
 * 
 * @author ganqing 2016.7.13
 * 
 */
public class RevisonSpentTotalBackSAP {
	private String spentMonth; // 统计的月份
	private String rid; // 工包编号
	private String total; // 总工时
	
	public RevisonSpentTotalBackSAP() {
	}
	
	public RevisonSpentTotalBackSAP(String spentMonth, String rid, String total) {
		this.spentMonth = spentMonth;
		this.rid = rid;
		this.total = total;
	}


	public String getSpentMonth() {
		return spentMonth;
	}

	public void setSpentMonth(String spentMonth) {
		this.spentMonth = spentMonth;
	}

	public String getRid() {
		return rid;
	}

	public void setRid(String rid) {
		this.rid = rid;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	

}
