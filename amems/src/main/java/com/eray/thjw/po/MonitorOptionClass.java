package com.eray.thjw.po;

import java.util.List;

public class MonitorOptionClass {
    private String jkflbh;

    private String ms;

    private Integer pxh;
    
    private List<MonitorOptionItem> itemList;

    public String getJkflbh() {
        return jkflbh;
    }

    public void setJkflbh(String jkflbh) {
        this.jkflbh = jkflbh == null ? null : jkflbh.trim();
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms == null ? null : ms.trim();
    }

    public Integer getPxh() {
        return pxh;
    }

    public void setPxh(Integer pxh) {
        this.pxh = pxh;
    }

	public List<MonitorOptionItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<MonitorOptionItem> itemList) {
		this.itemList = itemList;
	}
}