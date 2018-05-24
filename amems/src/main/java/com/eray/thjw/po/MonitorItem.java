package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;

public class MonitorItem {
    private String id;

    private String djxmid;

    private String jklbh;

    private String jkflbh;

    private BigDecimal zqz;

    private Integer dw;

    private Integer pxh;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String dprtcode;
    
    private String jklms;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDjxmid() {
        return djxmid;
    }

    public void setDjxmid(String djxmid) {
        this.djxmid = djxmid == null ? null : djxmid.trim();
    }

    public String getJklbh() {
        return jklbh;
    }

    public void setJklbh(String jklbh) {
        this.jklbh = jklbh == null ? null : jklbh.trim();
    }

    public String getJkflbh() {
        return jkflbh;
    }

    public void setJkflbh(String jkflbh) {
        this.jkflbh = jkflbh == null ? null : jkflbh.trim();
    }

    public BigDecimal getZqz() {
        return zqz;
    }

    public void setZqz(BigDecimal zqz) {
        this.zqz = zqz;
    }

    public Integer getDw() {
        return dw;
    }

    public void setDw(Integer dw) {
        this.dw = dw;
    }

    public Integer getPxh() {
        return pxh;
    }

    public void setPxh(Integer pxh) {
        this.pxh = pxh;
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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

	public String getJklms() {
		return jklms;
	}

	public void setJklms(String jklms) {
		this.jklms = jklms;
	}

	@Override
	public String toString() {
		return "MonitorItem [id=" + id + ", djxmid=" + djxmid + ", jklbh="
				+ jklbh + ", jkflbh=" + jkflbh + ", zqz=" + zqz + ", dw=" + dw
				+ ", pxh=" + pxh + ", whbmid=" + whbmid + ", whrid=" + whrid
				+ ", whsj=" + whsj + ", zt=" + zt + ", dprtcode=" + dprtcode
				+ "]";
	}
}