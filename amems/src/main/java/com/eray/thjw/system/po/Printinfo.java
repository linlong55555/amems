package com.eray.thjw.system.po;

import java.util.Date;

public class Printinfo {
    private String id;

    private String dprtcode;

    private Integer ywlx;

    private String ywid;

    private Date casj;

    private String czrid;

    private String czip;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Integer getYwlx() {
        return ywlx;
    }

    public void setYwlx(Integer ywlx) {
        this.ywlx = ywlx;
    }

    public String getYwid() {
        return ywid;
    }

    public void setYwid(String ywid) {
        this.ywid = ywid == null ? null : ywid.trim();
    }

    public Date getCasj() {
        return casj;
    }

    public void setCasj(Date casj) {
        this.casj = casj;
    }

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid == null ? null : czrid.trim();
    }

    public String getCzip() {
        return czip;
    }

    public void setCzip(String czip) {
        this.czip = czip == null ? null : czip.trim();
    }
}