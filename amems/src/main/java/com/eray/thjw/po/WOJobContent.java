package com.eray.thjw.po;

import java.util.Date;

/**
 * 工单工作内容表
 * @author meizhiliang
 *对应表 b_g_00602
 */
public class WOJobContent {
    private String id;

    private String mainid;

    private String gznr;

    private Integer isBj;

    private String gzz;

    private Integer xh;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String dprtcode;

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

    public Integer getXh() {
        return xh;
    }

    public void setXh(Integer xh) {
        this.xh = xh;
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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

	@Override
	public String toString() {
		return "WOJobContent [id=" + id + ", mainid=" + mainid + ", gznr="
				+ gznr + ", isBj=" + isBj + ", gzz=" + gzz + ", xh=" + xh
				+ ", zt=" + zt + ", whdwid=" + whdwid + ", whrid=" + whrid
				+ ", whsj=" + whsj + ", dprtcode=" + dprtcode + "]";
	}
    
}