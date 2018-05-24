package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description b_g2_00101 技术评估单-下达指令
 * @CreateTime 2017年8月18日 下午8:51:47
 * @CreateBy 林龙
 */
public class GiveInstruction extends BizEntity{
    private String id; //主id

    private String mainid; //技术评估单id

    private Integer zlxl;//指令类型

    private String zdryid; //指定人员id

    private String sm; //说明

    private Integer zt; //状态

    private String whdwid;//维护单位id

    private String whrid; //维护人id

    private Date whsj;//维护世界

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
    }

    public Integer getZlxl() {
		return zlxl;
	}

	public void setZlxl(Integer zlxl) {
		this.zlxl = zlxl;
	}

	public String getZdryid() {
        return zdryid;
    }

    public void setZdryid(String zdryid) {
        this.zdryid = zdryid == null ? null : zdryid.trim();
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm == null ? null : sm.trim();
    }

    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getWhdwid() {
        return whdwid;
    }

    public void setWhdwid(String whdwid) {
        this.whdwid = whdwid == null ? null : whdwid.trim();
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