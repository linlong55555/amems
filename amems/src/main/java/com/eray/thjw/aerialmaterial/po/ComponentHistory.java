package com.eray.thjw.aerialmaterial.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description B_H2_025 部件数据更新
 * @CreateTime 2017年10月20日 上午10:42:53
 * @CreateBy 徐勇
 */
public class ComponentHistory extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String jh;

    private String xlh;

    private Date chucrq;

    private String tsn;

    private String tso;

    private Integer csn;

    private Integer cso;

    private Date whsj;

    private String fjzch;

    private String zjqdid;

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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
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
}