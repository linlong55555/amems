package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @Description b_h2_00901 报废单明细
 * @CreateTime 2018年3月22日 上午10:59:37
 * @CreateBy 岳彬彬
 */
public class ScrappedInfo {
    private String id;

    private String mainid;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer sjly;

    private Integer zt;

    private String kcid;

    private BigDecimal kcsl;

    private String kcllid;

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

    public Integer getSjly() {
        return sjly;
    }

    public void setSjly(Integer sjly) {
        this.sjly = sjly;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }

    public BigDecimal getKcsl() {
        return kcsl;
    }

    public void setKcsl(BigDecimal kcsl) {
        this.kcsl = kcsl;
    }

    public String getKcllid() {
        return kcllid;
    }

    public void setKcllid(String kcllid) {
        this.kcllid = kcllid == null ? null : kcllid.trim();
    }
}