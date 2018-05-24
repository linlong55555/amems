package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
/**
 * 
 * @Description b_g2_00102 技术评估单-适用性
 * @CreateTime 2018年3月29日 下午2:53:22
 * @CreateBy 林龙
 */
public class TEApplicability extends BizEntity{
    private String id;

    private String mainid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer xc;

    private String bh;

    private String xlh;

    private String szwz;

    private Integer jkzt;

    private String jkbz;

    private Integer kjbs;

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
		this.whdwid = whdwid;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	public Integer getXc() {
		return xc;
	}

	public void setXc(Integer xc) {
		this.xc = xc;
	}

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}

	public String getSzwz() {
		return szwz;
	}

	public void setSzwz(String szwz) {
		this.szwz = szwz;
	}

	public Integer getJkzt() {
		return jkzt;
	}

	public void setJkzt(Integer jkzt) {
		this.jkzt = jkzt;
	}

	public String getJkbz() {
		return jkbz;
	}

	public void setJkbz(String jkbz) {
		this.jkbz = jkbz;
	}

	public Integer getKjbs() {
		return kjbs;
	}

	public void setKjbs(Integer kjbs) {
		this.kjbs = kjbs;
	}

    
}