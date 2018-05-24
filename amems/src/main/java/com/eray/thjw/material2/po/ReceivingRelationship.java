package com.eray.thjw.material2.po;

import java.math.BigDecimal;

import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description 对应b_h_027表
 * @CreateTime 2018-3-2 下午2:24:26
 * @CreateBy 孙霁
 */
public class ReceivingRelationship extends BizEntity{
    private String id;

    private String dprtcode;

    private Integer zt;

    private Integer lylx;

    private String lyid;

    private String lymxid;

    private String shdid;

    private String shmxid;

    private BigDecimal shsl;

    private String dw;

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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getLylx() {
        return lylx;
    }

    public void setLylx(Integer lylx) {
        this.lylx = lylx;
    }

    public String getLyid() {
        return lyid;
    }

    public void setLyid(String lyid) {
        this.lyid = lyid == null ? null : lyid.trim();
    }

    public String getLymxid() {
        return lymxid;
    }

    public void setLymxid(String lymxid) {
        this.lymxid = lymxid == null ? null : lymxid.trim();
    }

    public String getShdid() {
        return shdid;
    }

    public void setShdid(String shdid) {
        this.shdid = shdid == null ? null : shdid.trim();
    }

    public String getShmxid() {
        return shmxid;
    }

    public void setShmxid(String shmxid) {
        this.shmxid = shmxid == null ? null : shmxid.trim();
    }

    public BigDecimal getShsl() {
        return shsl;
    }

    public void setShsl(BigDecimal shsl) {
        this.shsl = shsl;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw == null ? null : dw.trim();
    }
}