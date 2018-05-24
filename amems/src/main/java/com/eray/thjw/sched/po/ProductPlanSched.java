package com.eray.thjw.sched.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

@SuppressWarnings("serial")
public class ProductPlanSched extends BizEntity{
    private String id;

    private String zlid;

    private String dprtcode;

    private Date zxrq;

    private String gkid;

    private String bjid;

    private String fjzch;

    private String zjqdid;

    private String bjh;

    private String bjxlh;

    private String jkxmbhF;

    private String jkflbhF;

    private String jkzF;

    private String jkxmbhS;

    private String jkflbhS;

    private String jkzS;

    private String jkxmbhT;

    private String jkflbhT;

    private String jkzT;

    private Short zt;

    private Date zdsj;
    
    //定检项目编号
    private String djbh;
    //定检项目名称
    private String djzwmc;
    //部件英文
    private String bjywms;
    //部件中文
    private String bjzwms;
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getZlid() {
        return zlid;
    }

    public void setZlid(String zlid) {
        this.zlid = zlid == null ? null : zlid.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Date getZxrq() {
        return zxrq;
    }

    public void setZxrq(Date zxrq) {
        this.zxrq = zxrq;
    }

    public String getGkid() {
        return gkid;
    }

    public void setGkid(String gkid) {
        this.gkid = gkid == null ? null : gkid.trim();
    }

    public String getBjid() {
        return bjid;
    }

    public void setBjid(String bjid) {
        this.bjid = bjid == null ? null : bjid.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getZjqdid() {
        return zjqdid;
    }

    public void setZjqdid(String zjqdid) {
        this.zjqdid = zjqdid == null ? null : zjqdid.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getBjxlh() {
        return bjxlh;
    }

    public void setBjxlh(String bjxlh) {
        this.bjxlh = bjxlh == null ? null : bjxlh.trim();
    }

    public String getJkxmbhF() {
        return jkxmbhF;
    }

    public void setJkxmbhF(String jkxmbhF) {
        this.jkxmbhF = jkxmbhF == null ? null : jkxmbhF.trim();
    }

    public String getJkflbhF() {
        return jkflbhF;
    }

    public void setJkflbhF(String jkflbhF) {
        this.jkflbhF = jkflbhF == null ? null : jkflbhF.trim();
    }

    public String getJkzF() {
        return jkzF;
    }

    public void setJkzF(String jkzF) {
        this.jkzF = jkzF == null ? null : jkzF.trim();
    }

    public String getJkxmbhS() {
        return jkxmbhS;
    }

    public void setJkxmbhS(String jkxmbhS) {
        this.jkxmbhS = jkxmbhS == null ? null : jkxmbhS.trim();
    }

    public String getJkflbhS() {
        return jkflbhS;
    }

    public void setJkflbhS(String jkflbhS) {
        this.jkflbhS = jkflbhS == null ? null : jkflbhS.trim();
    }

    public String getJkzS() {
        return jkzS;
    }

    public void setJkzS(String jkzS) {
        this.jkzS = jkzS == null ? null : jkzS.trim();
    }

    public String getJkxmbhT() {
        return jkxmbhT;
    }

    public void setJkxmbhT(String jkxmbhT) {
        this.jkxmbhT = jkxmbhT == null ? null : jkxmbhT.trim();
    }

    public String getJkflbhT() {
        return jkflbhT;
    }

    public void setJkflbhT(String jkflbhT) {
        this.jkflbhT = jkflbhT == null ? null : jkflbhT.trim();
    }

    public String getJkzT() {
        return jkzT;
    }

    public void setJkzT(String jkzT) {
        this.jkzT = jkzT == null ? null : jkzT.trim();
    }

    public Short getZt() {
        return zt;
    }

    public void setZt(Short zt) {
        this.zt = zt;
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }

	public String getDjbh() {
		return djbh;
	}

	public void setDjbh(String djbh) {
		this.djbh = djbh;
	}

	public String getDjzwmc() {
		return djzwmc;
	}

	public void setDjzwmc(String djzwmc) {
		this.djzwmc = djzwmc;
	}

	public String getBjywms() {
		return bjywms;
	}

	public void setBjywms(String bjywms) {
		this.bjywms = bjywms;
	}

	public String getBjzwms() {
		return bjzwms;
	}

	public void setBjzwms(String bjzwms) {
		this.bjzwms = bjzwms;
	}
}