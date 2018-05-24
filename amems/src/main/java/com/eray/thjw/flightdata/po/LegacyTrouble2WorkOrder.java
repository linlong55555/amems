package com.eray.thjw.flightdata.po;

import java.util.Date;

/**
 * 故障保留单,工单关系
 * @author zhuchao
 *
 */
public class LegacyTrouble2WorkOrder {
    private String id;

    private String mainid;

    private String gdlx;

    private String gdzlx;

    private String xggdid;

    private String zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String dprtcode;

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

    public String getGdlx() {
        return gdlx;
    }

    public void setGdlx(String gdlx) {
        this.gdlx = gdlx;
    }

    public String getGdzlx() {
        return gdzlx;
    }

    public void setGdzlx(String gdzlx) {
        this.gdzlx = gdzlx;
    }

    public String getXggdid() {
        return xggdid;
    }

    public void setXggdid(String xggdid) {
        this.xggdid = xggdid == null ? null : xggdid.trim();
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }
}