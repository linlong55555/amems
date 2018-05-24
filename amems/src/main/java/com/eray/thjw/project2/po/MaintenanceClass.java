package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * 
 * @Description 维修项目大类B_G2_01101
 * @CreateTime 2017-8-14 下午4:15:37
 * @CreateBy 刘兵
 */
public class MaintenanceClass extends BizEntity{
    private String id;

    private String dprtcode;

    private String jx;

    private String dlbh;

    private String dlzwms;

    private String dlywms;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    private User whr;
    
    private Department jg_dprt;
    
    private Department dw_dprt;
    
    private Integer xc;

    public Integer getXc() {
		return xc;
	}

	public void setXc(Integer xc) {
		this.xc = xc;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public Department getDw_dprt() {
		return dw_dprt;
	}

	public void setDw_dprt(Department dw_dprt) {
		this.dw_dprt = dw_dprt;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

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

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public String getDlbh() {
        return dlbh;
    }

    public void setDlbh(String dlbh) {
        this.dlbh = dlbh == null ? null : dlbh.trim();
    }

    public String getDlzwms() {
        return dlzwms;
    }

    public void setDlzwms(String dlzwms) {
        this.dlzwms = dlzwms == null ? null : dlzwms.trim();
    }

    public String getDlywms() {
        return dlywms;
    }

    public void setDlywms(String dlywms) {
        this.dlywms = dlywms == null ? null : dlywms.trim();
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
		return "MaintenanceClass [id=" + id + ", dprtcode=" + dprtcode
				+ ", jx=" + jx + ", dlbh=" + dlbh + ", dlzwms=" + dlzwms
				+ ", dlywms=" + dlywms + ", zt=" + zt + ", whdwid=" + whdwid
				+ ", whrid=" + whrid + ", whsj=" + whsj + ", whr=" + whr
				+ ", jg_dprt=" + jg_dprt + ", dw_dprt=" + dw_dprt
				+ ", pagination=" + pagination + ", getJg_dprt()="
				+ getJg_dprt() + ", getDw_dprt()=" + getDw_dprt()
				+ ", getWhr()=" + getWhr() + ", getId()=" + getId()
				+ ", getDprtcode()=" + getDprtcode() + ", getJx()=" + getJx()
				+ ", getDlbh()=" + getDlbh() + ", getDlzwms()=" + getDlzwms()
				+ ", getDlywms()=" + getDlywms() + ", getZt()=" + getZt()
				+ ", getWhdwid()=" + getWhdwid() + ", getWhrid()=" + getWhrid()
				+ ", getWhsj()=" + getWhsj() + ", getPagination()="
				+ getPagination() + ", getParamsMap()=" + getParamsMap()
				+ ", getCzls()=" + getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}