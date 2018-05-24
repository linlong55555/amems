package com.eray.thjw.productionplan.po;

import java.util.Date;
/**
 * @author liub
 * @description 故障总结-经过思路B_S_01501
 * @develop date 2017.01.03
 */
public class MaintenanceFailureSummaryDetail {
    private String id;

    private String mainid;

    private String pgjg;

    private String clrid;

    private String clr;

    private Date clsj;

    private Integer zt;

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

    public String getPgjg() {
        return pgjg;
    }

    public void setPgjg(String pgjg) {
        this.pgjg = pgjg == null ? null : pgjg.trim();
    }

    public String getClrid() {
        return clrid;
    }

    public void setClrid(String clrid) {
        this.clrid = clrid == null ? null : clrid.trim();
    }

    public String getClr() {
        return clr;
    }

    public void setClr(String clr) {
        this.clr = clr == null ? null : clr.trim();
    }

    public Date getClsj() {
        return clsj;
    }

    public void setClsj(Date clsj) {
        this.clsj = clsj;
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