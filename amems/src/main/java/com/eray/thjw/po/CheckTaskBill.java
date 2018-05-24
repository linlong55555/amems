package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * b_g_014 定检任务主表
 * 
 * @author linlong
 * 
 */
@SuppressWarnings("serial")
public class CheckTaskBill extends BizEntity {
	private String id;

	private String djrwbh;

	private String wxfabh;

	private String djbh;

	private BigDecimal bb;

	private String zwms;

	private String ywms;

	private Integer zyxs;

	private BigDecimal jhgsRs;

	private BigDecimal jhgsXss;

	private String bz;

	private String zdbmid;

	private String zdrid;

	private Date zdsj;

	private Integer zt;

	private String dprtcode;

	private String shbmid;

	private String shyj;

	private String shrid;

	private String shsj;

	private String pfbmid;

	private String pfyj;

	private String pfrid;

	private String pfsj;

	private String zdjsrid;

	private Date zdjsrq;

	private String zdjsyy;

	private String fjzch;

	private String bjh;

	private String xlh;

	private String djxmid;

	// 补充字段
	private String jkxmbhF;

	private String jkzF;

	private String jkxmbhS;

	private String jkzS;

	private String jkxmbhT;

	private String jkzT;

	private String username;

	private String realname;

	private String dprtname;

	private String jkxm;

	private String jkbz;
	
	private String displayname;
	
	

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getJkbz() {
		return jkbz;
	}

	public void setJkbz(String jkbz) {
		this.jkbz = jkbz;
	}

	public String getJkxmbhF() {
		return jkxmbhF;
	}

	public void setJkxmbhF(String jkxmbhF) {
		this.jkxmbhF = jkxmbhF;
	}

	public String getJkzF() {
		return jkzF;
	}

	public void setJkzF(String jkzF) {
		this.jkzF = jkzF;
	}

	public String getJkxmbhS() {
		return jkxmbhS;
	}

	public void setJkxmbhS(String jkxmbhS) {
		this.jkxmbhS = jkxmbhS;
	}

	public String getJkzS() {
		return jkzS;
	}

	public void setJkzS(String jkzS) {
		this.jkzS = jkzS;
	}

	public String getJkxmbhT() {
		return jkxmbhT;
	}

	public void setJkxmbhT(String jkxmbhT) {
		this.jkxmbhT = jkxmbhT;
	}

	public String getJkzT() {
		return jkzT;
	}

	public void setJkzT(String jkzT) {
		this.jkzT = jkzT;
	}

	public String getJkxm() {
		return jkxm;
	}

	public void setJkxm(String jkxm) {
		this.jkxm = jkxm;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getDjxmid() {
		return djxmid;
	}

	public void setDjxmid(String djxmid) {
		this.djxmid = djxmid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDjrwbh() {
		return djrwbh;
	}

	public void setDjrwbh(String djrwbh) {
		this.djrwbh = djrwbh;
	}

	public String getWxfabh() {
		return wxfabh;
	}

	public void setWxfabh(String wxfabh) {
		this.wxfabh = wxfabh;
	}

	public String getDjbh() {
		return djbh;
	}

	public void setDjbh(String djbh) {
		this.djbh = djbh;
	}

	public String getZwms() {
		return zwms;
	}

	public void setZwms(String zwms) {
		this.zwms = zwms;
	}

	public String getYwms() {
		return ywms;
	}

	public void setYwms(String ywms) {
		this.ywms = ywms;
	}

	public Integer getZyxs() {
		return zyxs;
	}

	public void setZyxs(Integer zyxs) {
		this.zyxs = zyxs;
	}

	public BigDecimal getBb() {
		return bb;
	}

	public void setBb(BigDecimal bb) {
		this.bb = bb;
	}

	public BigDecimal getJhgsRs() {
		if(jhgsRs == null){
    		return jhgsRs;
    	}
        return jhgsRs.setScale(0);
	}

	public void setJhgsRs(BigDecimal jhgsRs) {
		this.jhgsRs = jhgsRs;
	}

	public BigDecimal getJhgsXss() {
		return jhgsXss;
	}

	public void setJhgsXss(BigDecimal jhgsXss) {
		this.jhgsXss = jhgsXss;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getZdbmid() {
		return zdbmid;
	}

	public void setZdbmid(String zdbmid) {
		this.zdbmid = zdbmid;
	}

	public String getZdrid() {
		return zdrid;
	}

	public void setZdrid(String zdrid) {
		this.zdrid = zdrid;
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

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getShbmid() {
		return shbmid;
	}

	public void setShbmid(String shbmid) {
		this.shbmid = shbmid;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public String getShrid() {
		return shrid;
	}

	public void setShrid(String shrid) {
		this.shrid = shrid;
	}

	public String getShsj() {
		return shsj;
	}

	public void setShsj(String shsj) {
		this.shsj = shsj;
	}

	public String getPfbmid() {
		return pfbmid;
	}

	public void setPfbmid(String pfbmid) {
		this.pfbmid = pfbmid;
	}

	public String getPfyj() {
		return pfyj;
	}

	public void setPfyj(String pfyj) {
		this.pfyj = pfyj;
	}

	public String getPfrid() {
		return pfrid;
	}

	public void setPfrid(String pfrid) {
		this.pfrid = pfrid;
	}

	public String getPfsj() {
		return pfsj;
	}

	public void setPfsj(String pfsj) {
		this.pfsj = pfsj;
	}

	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid;
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
		this.zdjsyy = zdjsyy;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getBjh() {
		return bjh;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh;
	}

	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}

}