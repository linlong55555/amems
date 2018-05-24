package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * 
 * b_h_00402 合同表-付款信息
 * @author xu.yong
 *
 */
public class ContractPay extends BizEntity{
    private String id;

    private String mainid;

    private Date fkrq;

    private Integer fkfs;

    private String fkfssm;

    private BigDecimal fkje;

    private String bz;

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

    public Date getFkrq() {
        return fkrq;
    }

    public void setFkrq(Date fkrq) {
        this.fkrq = fkrq;
    }

    public Integer getFkfs() {
        return fkfs;
    }

    public void setFkfs(Integer fkfs) {
        this.fkfs = fkfs;
    }

    public String getFkfssm() {
        return fkfssm;
    }

    public void setFkfssm(String fkfssm) {
        this.fkfssm = fkfssm == null ? null : fkfssm.trim();
    }

    public BigDecimal getFkje() {
        return fkje;
    }

    public void setFkje(BigDecimal fkje) {
        this.fkje = fkje;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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