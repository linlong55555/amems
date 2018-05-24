package com.eray.thjw.outfield.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BaseEntity;

/**
 * 
 * b_w_001 计量工具主表
 * @author xu.yong
 *
 */
public class ToolsMonitor extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private String id;

    private String bjid;

    private String bjxlh;

    private Integer zt;

    private Integer isJl;
    
    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private List<ToolsMonitorDetail> toolsMonitorDetailList;
    
    public List<ToolsMonitorDetail> getToolsMonitorDetailList() {
		return toolsMonitorDetailList;
	}

	public void setToolsMonitorDetailList(
			List<ToolsMonitorDetail> toolsMonitorDetailList) {
		this.toolsMonitorDetailList = toolsMonitorDetailList;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBjid() {
        return bjid;
    }

    public void setBjid(String bjid) {
        this.bjid = bjid == null ? null : bjid.trim();
    }

    public String getBjxlh() {
        return bjxlh;
    }

    public void setBjxlh(String bjxlh) {
        this.bjxlh = bjxlh == null ? null : bjxlh.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getIsJl() {
        return isJl;
    }

    public void setIsJl(Integer isJl) {
        this.isJl = isJl;
    }

    public String getWhbmid() {
        return whbmid;
    }

    public void setWhbmid(String whbmid) {
        this.whbmid = whbmid == null ? null : whbmid.trim();
    }

    public String getWhrid() {
        return whrid;
    }

    public void setWhrid(String whrid) {
        this.whrid = whrid == null ? null : whrid.trim();
    }

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }
}