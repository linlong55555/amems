package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.basic.po.CoverPlateInformation;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description B_G2_995接近/盖板
 * @CreateTime 2017-8-14 下午3:53:58
 * @CreateBy 刘兵
 */
public class CoverPlate extends BaseEntity {
	private String id;

    private String dprtcode;

    private Integer ywlx;

    private String ywid;

    private Integer lx;

    private Integer xc;

    private String gbid;

    private String gbh;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    /** 盖板信息 */
    private CoverPlateInformation coverPlateInformation;

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

    public Integer getLx() {
        return lx;
    }

    public void setLx(Integer lx) {
        this.lx = lx;
    }

    public Integer getXc() {
        return xc;
    }

    public void setXc(Integer xc) {
        this.xc = xc;
    }

    public String getGbid() {
        return gbid;
    }

    public void setGbid(String gbid) {
        this.gbid = gbid == null ? null : gbid.trim();
    }

    public String getGbh() {
        return gbh;
    }

    public void setGbh(String gbh) {
        this.gbh = gbh == null ? null : gbh.trim();
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

	public CoverPlateInformation getCoverPlateInformation() {
		return coverPlateInformation;
	}

	public void setCoverPlateInformation(CoverPlateInformation coverPlateInformation) {
		this.coverPlateInformation = coverPlateInformation;
	}

	@Override
	public String toString() {
		return "CoverPlate [id=" + id + ", dprtcode=" + dprtcode + ", ywlx="
				+ ywlx + ", ywid=" + ywid + ", lx=" + lx + ", xc=" + xc
				+ ", gbid=" + gbid + ", gbh=" + gbh + ", whdwid=" + whdwid
				+ ", whrid=" + whrid + ", whsj=" + whsj
				+ ", coverPlateInformation=" + coverPlateInformation
				+ ", pagination=" + pagination + ", getId()=" + getId()
				+ ", getDprtcode()=" + getDprtcode() + ", getYwlx()="
				+ getYwlx() + ", getYwid()=" + getYwid() + ", getLx()="
				+ getLx() + ", getXc()=" + getXc() + ", getGbid()=" + getGbid()
				+ ", getGbh()=" + getGbh() + ", getWhdwid()=" + getWhdwid()
				+ ", getWhrid()=" + getWhrid() + ", getWhsj()=" + getWhsj()
				+ ", getCoverPlateInformation()=" + getCoverPlateInformation()
				+ ", getPagination()=" + getPagination() + ", getParamsMap()="
				+ getParamsMap() + ", getCzls()=" + getCzls()
				+ ", getLogOperationEnum()=" + getLogOperationEnum()
				+ ", getZbid()=" + getZbid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}