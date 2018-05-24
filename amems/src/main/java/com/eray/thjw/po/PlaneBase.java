package com.eray.thjw.po;

import java.util.Date;

public class PlaneBase {
    private String id;

    private String dprtcode;

    private String jdms;

    private String ssjgdm;

    private Date whsj;

    private Short zt;

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

    public String getJdms() {
        return jdms;
    }

    public void setJdms(String jdms) {
        this.jdms = jdms == null ? null : jdms.trim();
    }

    public String getSsjgdm() {
        return ssjgdm;
    }

    public void setSsjgdm(String ssjgdm) {
        this.ssjgdm = ssjgdm == null ? null : ssjgdm.trim();
    }

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }

    public Short getZt() {
        return zt;
    }

    public void setZt(Short zt) {
        this.zt = zt;
    }
}