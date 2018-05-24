package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.aerialmaterial.po.HCMainData;
/**
 * 
 * @Description d_00701 飞机初始化数据
 * @CreateTime 2017-9-21 上午11:56:35
 * @CreateBy 孙霁
 */
public class PlanInit {
    private String id;

    private String fjzch;

    private String dprtcode;

    private Short wz;

    private String jh;

    private String xlh;

    private String jklbh;

    private String jkflbh;

    private String csz;

    private String zjqdid;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private HCMainData hCMainData;

    public HCMainData gethCMainData() {
		return hCMainData;
	}

	public void sethCMainData(HCMainData hCMainData) {
		this.hCMainData = hCMainData;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Short getWz() {
        return wz;
    }

    public void setWz(Short wz) {
        this.wz = wz;
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh == null ? null : jh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
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

    public String getCsz() {
        return csz;
    }

    public void setCsz(String csz) {
        this.csz = csz == null ? null : csz.trim();
    }

    public String getZjqdid() {
        return zjqdid;
    }

    public void setZjqdid(String zjqdid) {
        this.zjqdid = zjqdid == null ? null : zjqdid.trim();
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
}