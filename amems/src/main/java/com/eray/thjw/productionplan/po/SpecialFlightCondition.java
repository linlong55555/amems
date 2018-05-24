package com.eray.thjw.productionplan.po;

import java.util.Date;

public class SpecialFlightCondition {
    private String id;

    private String bh;

    private String jc;

    private String ms;

    private String bz;

    private String fjjx;

    private String cjdwid;

    private String cjrid;

    private Date cjsj;

    private Integer zt;

    private String dprtcode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh == null ? null : bh.trim();
    }

    public String getJc() {
        return jc;
    }

    public void setJc(String jc) {
        this.jc = jc == null ? null : jc.trim();
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms == null ? null : ms.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
    }

    public String getCjdwid() {
        return cjdwid;
    }

    public void setCjdwid(String cjdwid) {
        this.cjdwid = cjdwid == null ? null : cjdwid.trim();
    }

    public String getCjrid() {
        return cjrid;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid == null ? null : cjrid.trim();
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }
}