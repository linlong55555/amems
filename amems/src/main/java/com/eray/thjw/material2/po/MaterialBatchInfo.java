package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description b_h_032 物料批次信息
 * @CreateTime 2018年3月23日 下午3:48:47
 * @CreateBy 韩武
 */
public class MaterialBatchInfo {
    private String id;

    private String dprtcode;

    private Date whsj;

    private String whrbmid;

    private String whrid;

    private String pch;

    private String bjh;

    private String xlh;

    private BigDecimal cb;

    private String cbbz;

    private BigDecimal jz;

    private String jzbz;

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

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }

    public String getWhrbmid() {
        return whrbmid;
    }

    public void setWhrbmid(String whrbmid) {
        this.whrbmid = whrbmid == null ? null : whrbmid.trim();
    }

    public String getWhrid() {
        return whrid;
    }

    public void setWhrid(String whrid) {
        this.whrid = whrid == null ? null : whrid.trim();
    }

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch == null ? null : pch.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public BigDecimal getCb() {
        return cb;
    }

    public void setCb(BigDecimal cb) {
        this.cb = cb;
    }

    public String getCbbz() {
        return cbbz;
    }

    public void setCbbz(String cbbz) {
        this.cbbz = cbbz == null ? null : cbbz.trim();
    }

    public BigDecimal getJz() {
        return jz;
    }

    public void setJz(BigDecimal jz) {
        this.jz = jz;
    }

    public String getJzbz() {
        return jzbz;
    }

    public void setJzbz(String jzbz) {
        this.jzbz = jzbz == null ? null : jzbz.trim();
    }
}