package com.eray.thjw.material2.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
/**
 * 
 * @Description b_h2_021 销毁
 * @CreateTime 2018年3月27日 下午3:40:35
 * @CreateBy 岳彬彬
 */
public class Destroy extends BizEntity{
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String xhbmid;

    private String xhrid;

    private Date xhsj;

    private Integer zt;

    private String bfmxid;

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

    public String getXhbmid() {
        return xhbmid;
    }

    public void setXhbmid(String xhbmid) {
        this.xhbmid = xhbmid == null ? null : xhbmid.trim();
    }

    public String getXhrid() {
        return xhrid;
    }

    public void setXhrid(String xhrid) {
        this.xhrid = xhrid == null ? null : xhrid.trim();
    }

    public Date getXhsj() {
        return xhsj;
    }

    public void setXhsj(Date xhsj) {
        this.xhsj = xhsj;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getBfmxid() {
        return bfmxid;
    }

    public void setBfmxid(String bfmxid) {
        this.bfmxid = bfmxid == null ? null : bfmxid.trim();
    }
}