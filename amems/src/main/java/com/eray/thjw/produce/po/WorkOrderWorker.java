package com.eray.thjw.produce.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * @Description b_s2_00802 135工单信息-工作者
 * @CreateTime 2018年1月25日 上午10:14:13
 * @CreateBy 韩武
 */
public class WorkOrderWorker extends BizEntity{
    private String id;

    private String mainid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String gzzid;

    private String gzz;

    private BigDecimal ftgs;

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

    public String getGzzid() {
        return gzzid;
    }

    public void setGzzid(String gzzid) {
        this.gzzid = gzzid == null ? null : gzzid.trim();
    }

    public String getGzz() {
        return gzz;
    }

    public void setGzz(String gzz) {
        this.gzz = gzz == null ? null : gzz.trim();
    }

    public BigDecimal getFtgs() {
        return ftgs;
    }

    public void setFtgs(BigDecimal ftgs) {
        this.ftgs = ftgs;
    }
}