package com.eray.thjw.aerialmaterial.po;

import java.util.Date;

/**
 * @Description b_h_010 部件
 * @CreateTime 2017年10月9日 下午4:51:43
 * @CreateBy 韩武
 */
public class Component {
    private String id;

    private String dprtcode;

    private String jh;

    private String xlh;

    private Short llklx;

    private String llkbh;

    private String bjgzjl;

    private Date chucrq;

    private String tsn;

    private String tso;

    private Integer csn;

    private Integer cso;

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

    public Short getLlklx() {
        return llklx;
    }

    public void setLlklx(Short llklx) {
        this.llklx = llklx;
    }

    public String getLlkbh() {
        return llkbh;
    }

    public void setLlkbh(String llkbh) {
        this.llkbh = llkbh == null ? null : llkbh.trim();
    }

    public String getBjgzjl() {
        return bjgzjl;
    }

    public void setBjgzjl(String bjgzjl) {
        this.bjgzjl = bjgzjl == null ? null : bjgzjl.trim();
    }

    public Date getChucrq() {
        return chucrq;
    }

    public void setChucrq(Date chucrq) {
        this.chucrq = chucrq;
    }

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn == null ? null : tsn.trim();
    }

    public String getTso() {
        return tso;
    }

    public void setTso(String tso) {
        this.tso = tso == null ? null : tso.trim();
    }

    public Integer getCsn() {
		return csn;
	}

	public void setCsn(Integer csn) {
		this.csn = csn;
	}

	public Integer getCso() {
		return cso;
	}

	public void setCso(Integer cso) {
		this.cso = cso;
	}

	public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }
}