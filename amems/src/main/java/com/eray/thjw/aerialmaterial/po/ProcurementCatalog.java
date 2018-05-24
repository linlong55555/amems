package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * d_013 采购目录
 * @author xu.yong
 *
 */
public class ProcurementCatalog {
    private String id;

    private String dprtcode;

    private String gysid;

    private String gysbm;

    private Integer gyslb;

    private Integer bjlb;

    private String bjid;

    private String bjh;

    private BigDecimal bjClf;

    private BigDecimal bjGsf;

    private BigDecimal bjQtf;

    private Date yxqKs;

    private Date yxqJs;

    private String bz;

    private Integer zt;

    private String bmid;

    private String cjrid;

    private Date cjsj;
    
    private List<String> bjIdList;

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

    public String getGysid() {
        return gysid;
    }

    public void setGysid(String gysid) {
        this.gysid = gysid == null ? null : gysid.trim();
    }

    public String getGysbm() {
        return gysbm;
    }

    public void setGysbm(String gysbm) {
        this.gysbm = gysbm == null ? null : gysbm.trim();
    }

    public Integer getGyslb() {
        return gyslb;
    }

    public void setGyslb(Integer gyslb) {
        this.gyslb = gyslb;
    }

    public Integer getBjlb() {
        return bjlb;
    }

    public void setBjlb(Integer bjlb) {
        this.bjlb = bjlb;
    }

    public String getBjid() {
        return bjid;
    }

    public void setBjid(String bjid) {
        this.bjid = bjid == null ? null : bjid.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public BigDecimal getBjClf() {
        return bjClf;
    }

    public void setBjClf(BigDecimal bjClf) {
        this.bjClf = bjClf;
    }

    public BigDecimal getBjGsf() {
        return bjGsf;
    }

    public void setBjGsf(BigDecimal bjGsf) {
        this.bjGsf = bjGsf;
    }

    public BigDecimal getBjQtf() {
        return bjQtf;
    }

    public void setBjQtf(BigDecimal bjQtf) {
        this.bjQtf = bjQtf;
    }

    public Date getYxqKs() {
        return yxqKs;
    }

    public void setYxqKs(Date yxqKs) {
        this.yxqKs = yxqKs;
    }

    public Date getYxqJs() {
        return yxqJs;
    }

    public void setYxqJs(Date yxqJs) {
        this.yxqJs = yxqJs;
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

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid == null ? null : bmid.trim();
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

	public List<String> getBjIdList() {
		return bjIdList;
	}

	public void setBjIdList(List<String> bjIdList) {
		this.bjIdList = bjIdList;
	}
    
}