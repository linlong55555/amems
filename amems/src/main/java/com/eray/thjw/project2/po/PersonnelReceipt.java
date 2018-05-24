package com.eray.thjw.project2.po;

import java.util.Date;
/**
 * @CreateBy 岳彬彬
 * @Description b_g2_994 人员接收表
 * @CreateTime 2017年8月12日 下午5:01:24
 */
public class PersonnelReceipt {
    private String id;

    private String dprtcode;

    private Integer ywlx;

    private String ywid;

    private String jsbmid;

    private String jsrid;

    private Date jssj;

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

    public String getJsbmid() {
        return jsbmid;
    }

    public void setJsbmid(String jsbmid) {
        this.jsbmid = jsbmid == null ? null : jsbmid.trim();
    }

    public String getJsrid() {
        return jsrid;
    }

    public void setJsrid(String jsrid) {
        this.jsrid = jsrid == null ? null : jsrid.trim();
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }
}