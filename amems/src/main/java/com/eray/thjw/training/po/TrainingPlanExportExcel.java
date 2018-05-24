package com.eray.thjw.training.po;

import java.util.Date;
import java.util.List;

public class TrainingPlanExportExcel {
	
	private String dprtcode;
	
	private Integer jhlx;
	
	private String keyword;
	
	private String order;
	
	private Integer page;
	
	private Integer rows;
	
	private String sort;
	
	private List<String> fxbsList;
	
	private String jhrqBegin;
	
	private String jhrqEnd;
	
	private String year;
	
	private Integer zt;
	
	private List<String> ztList;
	
	public List<String> getZtList() {
		return ztList;
	}

	public void setZtList(List<String> ztList) {
		this.ztList = ztList;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public Integer getJhlx() {
		return jhlx;
	}

	public void setJhlx(Integer jhlx) {
		this.jhlx = jhlx;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<String> getFxbsList() {
		return fxbsList;
	}

	public void setFxbsList(List<String> fxbsList) {
		this.fxbsList = fxbsList;
	}

	public String getJhrqBegin() {
		return jhrqBegin;
	}

	public void setJhrqBegin(String jhrqBegin) {
		this.jhrqBegin = jhrqBegin;
	}

	public String getJhrqEnd() {
		return jhrqEnd;
	}

	public void setJhrqEnd(String jhrqEnd) {
		this.jhrqEnd = jhrqEnd;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}
	
	
} 
