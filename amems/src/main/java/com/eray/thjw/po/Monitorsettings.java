package com.eray.thjw.po;

import java.util.List;

public class Monitorsettings extends BaseEntity{

	private String key;

	private Integer yjtsJb1;

	private Integer yjtsJb2;

	private Integer yjtsJb3;

	private String dprtcode;
	
	private List<String> keys;
	
	private List<Monitorsettings> monitorsettingsList;
	
	private List<Monitorsettings> subTypeList; //适航性资料子类型
	
	
	public List<Monitorsettings> getMonitorsettingsList() {
		return monitorsettingsList;
	}

	public void setMonitorsettingsList(List<Monitorsettings> monitorsettingsList) {
		this.monitorsettingsList = monitorsettingsList;
	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		this.keys = keys;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getYjtsJb1() {
		return yjtsJb1;
	}

	public void setYjtsJb1(Integer yjtsJb1) {
		this.yjtsJb1 = yjtsJb1;
	}

	public Integer getYjtsJb2() {
		return yjtsJb2;
	}

	public void setYjtsJb2(Integer yjtsJb2) {
		this.yjtsJb2 = yjtsJb2;
	}

	public Integer getYjtsJb3() {
		return yjtsJb3;
	}

	public void setYjtsJb3(Integer yjtsJb3) {
		this.yjtsJb3 = yjtsJb3;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public List<Monitorsettings> getSubTypeList() {
		return subTypeList;
	}

	public void setSubTypeList(List<Monitorsettings> subTypeList) {
		this.subTypeList = subTypeList;
	}

	@Override
	public String toString() {
		return "Monitorsettings [key=" + key + ", yjtsJb1=" + yjtsJb1
				+ ", yjtsJb2=" + yjtsJb2 + ", yjtsJb3=" + yjtsJb3
				+ ", dprtcode=" + dprtcode + ", keys=" + keys
				+ ", monitorsettingsList=" + monitorsettingsList
				+ ", subTypeList=" + subTypeList + "]";
	}
	
}
