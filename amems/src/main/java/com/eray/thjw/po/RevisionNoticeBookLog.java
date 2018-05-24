package com.eray.thjw.po;

import java.util.Date;

/**
 * @author pingxiaojun
 * @description 修订通知书日志 po类
 * @develop date 2016.08.15
 */
public class RevisionNoticeBookLog {
    private String id;

    private String jszlh;

    private Integer tzslx;

    private String jx;
    
    private String ckzl;

    private String xdzt;

    private String xdnr;

    private String xdrid;

    private Date xdqx;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private Integer zt;

    private Integer dyzt;

    private String dprtcode;

    private Integer recXglx;

    private String recCzrid;

    private Date recCzsj;
    
    private String zdjsrid;

    private Date zdjsrq;

    private String zdjsyy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJszlh() {
        return jszlh;
    }

    public void setJszlh(String jszlh) {
        this.jszlh = jszlh == null ? null : jszlh.trim();
    }

    public Integer getTzslx() {
        return tzslx;
    }

    public void setTzslx(Integer tzslx) {
        this.tzslx = tzslx;
    }

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }
    
    public String getCkzl() {
        return ckzl;
    }

    public void setCkzl(String ckzl) {
        this.ckzl = ckzl == null ? null : ckzl.trim();
    }

    public String getXdzt() {
        return xdzt;
    }

    public void setXdzt(String xdzt) {
        this.xdzt = xdzt == null ? null : xdzt.trim();
    }

    public String getXdnr() {
        return xdnr;
    }

    public void setXdnr(String xdnr) {
        this.xdnr = xdnr == null ? null : xdnr.trim();
    }

    public String getXdrid() {
        return xdrid;
    }

    public void setXdrid(String xdrid) {
        this.xdrid = xdrid == null ? null : xdrid.trim();
    }

    public Date getXdqx() {
        return xdqx;
    }

    public void setXdqx(Date xdqx) {
        this.xdqx = xdqx;
    }

    public String getZdbmid() {
        return zdbmid;
    }

    public void setZdbmid(String zdbmid) {
        this.zdbmid = zdbmid == null ? null : zdbmid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getDyzt() {
        return dyzt;
    }

    public void setDyzt(Integer dyzt) {
        this.dyzt = dyzt;
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Integer getRecXglx() {
        return recXglx;
    }

    public void setRecXglx(Integer recXglx) {
        this.recXglx = recXglx;
    }

    public String getRecCzrid() {
        return recCzrid;
    }

    public void setRecCzrid(String recCzrid) {
        this.recCzrid = recCzrid == null ? null : recCzrid.trim();
    }

    public Date getRecCzsj() {
        return recCzsj;
    }

    public void setRecCzsj(Date recCzsj) {
        this.recCzsj = recCzsj;
    }
    
    public String getZdjsrid() {
        return zdjsrid;
    }

    public void setZdjsrid(String zdjsrid) {
        this.zdjsrid = zdjsrid == null ? null : zdjsrid.trim();
    }

    public Date getZdjsrq() {
        return zdjsrq;
    }

    public void setZdjsrq(Date zdjsrq) {
        this.zdjsrq = zdjsrq;
    }

    public String getZdjsyy() {
        return zdjsyy;
    }

    public void setZdjsyy(String zdjsyy) {
        this.zdjsyy = zdjsyy == null ? null : zdjsyy.trim();
    }
    
	@Override
	public String toString() {
		return "RevisionNoticeBookLog [id=" + id + ", jszlh=" + jszlh
				+ ", tzslx=" + tzslx + ", jx=" + jx
				+ ", ckzl=" + ckzl
				+ ", xdzt=" + xdzt + ", xdnr=" + xdnr
				+ ", xdrid=" + xdrid + ", xdqx=" + xdqx 
				+ ", zdbmid=" + zdbmid + ", zdrid=" + zdrid 
				+ ", zdsj=" + zdsj + ", zt=" + zt
				+ ", dyzt=" + dyzt + ", dprtcode=" + dprtcode
				+ ", recXglx=" + recXglx + ", recCzrid=" + recCzrid
				+ ", recCzsj=" + recCzsj
				+ "]";
	}
}