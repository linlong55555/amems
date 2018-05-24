package com.eray.thjw.flightdata.po;

import java.util.List;

public class MountTimeMonitorTotal {
    private String id;

    private List<MountSpecialCondition> conditions;	// 特殊情况设置
    
    private List<MountTimeMonitor> settings;	// 时控件设置
    
    private String tsn;

    private String tso;
    
    private String fjzch;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<MountTimeMonitor> getSettings() {
		return settings;
	}

	public void setSettings(List<MountTimeMonitor> settings) {
		this.settings = settings;
	}

	public List<MountSpecialCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<MountSpecialCondition> conditions) {
		this.conditions = conditions;
	}

	public String getTsn() {
		return tsn;
	}

	public void setTsn(String tsn) {
		this.tsn = tsn;
	}

	public String getTso() {
		return tso;
	}

	public void setTso(String tso) {
		this.tso = tso;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}
	
}