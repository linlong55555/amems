package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description b_g2_01003  EO-受影响出版物
 * @CreateTime 2017-8-20 下午3:09:07
 * @CreateBy 雷伟
 */
public class EOPulicationAffected extends BizEntity {
    private String id;

    private String mainid;

    private BigDecimal xc;

    private String lx;

    private String wjh;

    private String sm;

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

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx == null ? null : lx.trim();
    }

    public String getWjh() {
        return wjh;
    }

    public void setWjh(String wjh) {
        this.wjh = wjh == null ? null : wjh.trim();
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm == null ? null : sm.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer integer) {
        this.zt = integer;
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
		return "PulicationAffected [id=" + id + ", mainid=" + mainid + ", xc="
				+ xc + ", lx=" + lx + ", wjh=" + wjh + ", sm=" + sm + ", zt="
				+ zt + ", whdwid=" + whdwid + ", whrid=" + whrid + ", whsj="
				+ whsj + "]";
	}
}