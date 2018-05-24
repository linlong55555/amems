package com.eray.thjw.project2.po;

import java.util.Date;

/**
 * @Description 生产指令飞机关系 b_g2_01402
 * @CreateTime 2018年4月28日 上午9:59:50
 * @CreateBy 徐勇
 */
public class ProductionOrderPlane {
    private String id;

    private String mainid;

    private Short xc;

    private String fjzch;

    private String whdwid;

    private String whrid;

    private Date whsj;

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

    public Short getXc() {
        return xc;
    }

    public void setXc(Short xc) {
        this.xc = xc;
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
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