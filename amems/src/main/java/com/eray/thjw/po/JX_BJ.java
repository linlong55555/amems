package com.eray.thjw.po;

import java.util.Date;
/**
 * 机型关联部件的实体  D_00401
 * @author MZL
 *
 */
public class JX_BJ {
    private String fjjx;

    private String dprtcode;

    private String jh;

    private String zwmc;

    private String ywmc;

    private String bz;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Short zt;
    
    private Pagination pagination;    //分页

    public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
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

    public String getZwmc() {
        return zwmc;
    }

    public void setZwmc(String zwmc) {
        this.zwmc = zwmc == null ? null : zwmc.trim();
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc == null ? null : ywmc.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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

    public Short getZt() {
        return zt;
    }

    public void setZt(Short zt) {
        this.zt = zt;
    }

	@Override
	public String toString() {
		return "JX_BJ [fjjx=" + fjjx + ", dprtcode=" + dprtcode + ", jh=" + jh
				+ ", zwmc=" + zwmc + ", ywmc=" + ywmc + ", bz=" + bz
				+ ", whbmid=" + whbmid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", zt=" + zt + ", pagination=" + pagination + "]";
	}
    
    
}