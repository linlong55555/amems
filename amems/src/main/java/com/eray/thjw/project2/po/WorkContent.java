package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description B_G2_996 工作内容
 * @CreateTime 2017-8-14 下午4:02:52
 * @CreateBy 刘兵
 */
public class WorkContent extends BaseEntity {
    private String id;

    private String dprtcode;

    private Integer ywlx;

    private String ywid;

    private Integer xc;

    private String gznr;

    private Integer isBj;

    private String gzz;

    private String jcz;

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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Integer getYwlx() {
        return ywlx;
    }

    public void setYwlx(Integer ywlx) {
        this.ywlx = ywlx;
    }

    public String getYwid() {
        return ywid;
    }

    public void setYwid(String ywid) {
        this.ywid = ywid == null ? null : ywid.trim();
    }

    public Integer getXc() {
        return xc;
    }

    public void setXc(Integer xc) {
        this.xc = xc;
    }

    public String getGznr() {
        return gznr;
    }

    public void setGznr(String gznr) {
        this.gznr = gznr == null ? null : gznr.trim();
    }

    public Integer getIsBj() {
        return isBj;
    }

    public void setIsBj(Integer isBj) {
        this.isBj = isBj;
    }

    public String getGzz() {
        return gzz;
    }

    public void setGzz(String gzz) {
        this.gzz = gzz == null ? null : gzz.trim();
    }

    public String getJcz() {
        return jcz;
    }

    public void setJcz(String jcz) {
        this.jcz = jcz == null ? null : jcz.trim();
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
		return "WorkContent [id=" + id + ", dprtcode=" + dprtcode + ", ywlx="
				+ ywlx + ", ywid=" + ywid + ", xc=" + xc + ", gznr=" + gznr
				+ ", isBj=" + isBj + ", gzz=" + gzz + ", jcz=" + jcz + ", zt="
				+ zt + ", whdwid=" + whdwid + ", whrid=" + whrid + ", whsj="
				+ whsj + ", pagination=" + pagination + ", getId()=" + getId()
				+ ", getDprtcode()=" + getDprtcode() + ", getYwlx()="
				+ getYwlx() + ", getYwid()=" + getYwid() + ", getXc()="
				+ getXc() + ", getGznr()=" + getGznr() + ", getIsBj()="
				+ getIsBj() + ", getGzz()=" + getGzz() + ", getJcz()="
				+ getJcz() + ", getZt()=" + getZt() + ", getWhdwid()="
				+ getWhdwid() + ", getWhrid()=" + getWhrid() + ", getWhsj()="
				+ getWhsj() + ", getPagination()=" + getPagination()
				+ ", getParamsMap()=" + getParamsMap() + ", getCzls()="
				+ getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}