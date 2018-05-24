package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description b_g2_01004  EO-工时/停场时间
 * @CreateTime 2017-8-21 下午3:37:01
 * @CreateBy 雷伟
 */
public class EOManhourParkingTime extends BizEntity {
    private String id;

    private String mainid;

    private BigDecimal xc;

    private String rw;

    private String zb;

    private BigDecimal rs;

    private BigDecimal zgs;

    private String ztcsj;

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
		this.mainid = mainid;
	}

	public BigDecimal getXc() {
        return xc;
    }

    public void setXc(BigDecimal xc) {
        this.xc = xc;
    }

    public String getRw() {
        return rw;
    }

    public void setRw(String rw) {
        this.rw = rw == null ? null : rw.trim();
    }

    public String getZb() {
        return zb;
    }

    public void setZb(String zb) {
        this.zb = zb == null ? null : zb.trim();
    }

    public BigDecimal getRs() {
        return rs;
    }

    public void setRs(BigDecimal rs) {
        this.rs = rs;
    }

    public BigDecimal getZgs() {
        return zgs;
    }

    public void setZgs(BigDecimal zgs) {
        this.zgs = zgs;
    }

    public String getZtcsj() {
        return ztcsj;
    }

    public void setZtcsj(String ztcsj) {
        this.ztcsj = ztcsj == null ? null : ztcsj.trim();
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

	@Override
	public String toString() {
		return "EOManhourParkingTime [id=" + id + ", mainid=" + mainid
				+ ", xc=" + xc + ", rw=" + rw + ", zb=" + zb + ", rs=" + rs
				+ ", zgs=" + zgs + ", ztcsj=" + ztcsj + ", bz=" + bz + ", zt="
				+ zt + ", whdwid=" + whdwid + ", whrid=" + whrid + ", whsj="
				+ whsj + "]";
	}
}