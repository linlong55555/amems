package com.eray.thjw.productionplan.po;

import com.eray.thjw.po.BizEntity;

/**
 * b_s_003 生效区-飞机装机清单
 * 
 * @author linl
 * 
 */
public class TimeControllWareItem extends BizEntity {
	private String id;

	private String fjzch;

	private Integer bjlx;

	private String jh;

	private String xlh;

	private String nbsbm;

	private String zjh;

	private String cjjh;

	private String zwmc;

	private String ywmc;

	private String bjgzjl;

	private Integer zjsl;

	private Integer wz;

	private Integer zt;

	private String scrq;

	private String bz;

	private String azrq;

	private String azjldh;

	private String ccrq;

	private String ccjldh;

	private String llklx;

	private String llkbh;

	private Integer kzlx;

	private Integer is_dj;

	private String tsn;

	private String tso;

	private Integer cj;

	private String fjdid;

	private String kbs;

	private String sxrq;

	private Integer sxzt;

	private String whsj;

	private String dprtCode;

	private String gdid;

	private String rwid;

	private String jkbz;

	private String yc;// 虚拟字段：预拆

	private String sy;// 虚拟字段：剩余

	private String syts;// 虚拟字段：剩余天数

	private String fengzhi;// 虚拟字段:峰值

	private String keyword;

	private String gdbh;

	private String rwdh;

	private String gdzt;

	private String gdwhr;

	private String gdwhsj;

	private String rwwhr;

	private String rwwhsj;
	
	private String zjhzwms;
	

	public String getZjhzwms() {
		return zjhzwms;
	}

	public void setZjhzwms(String zjhzwms) {
		this.zjhzwms = zjhzwms;
	}

	public String getGdbh() {
		return gdbh;
	}

	public void setGdbh(String gdbh) {
		this.gdbh = gdbh;
	}


	public String getRwdh() {
		return rwdh;
	}

	public void setRwdh(String rwdh) {
		this.rwdh = rwdh;
	}

	public String getGdzt() {
		return gdzt;
	}

	public void setGdzt(String gdzt) {
		this.gdzt = gdzt;
	}

	public String getGdwhr() {
		return gdwhr;
	}

	public void setGdwhr(String gdwhr) {
		this.gdwhr = gdwhr;
	}

	public String getGdwhsj() {
		return gdwhsj;
	}

	public void setGdwhsj(String gdwhsj) {
		this.gdwhsj = gdwhsj;
	}

	public String getRwwhr() {
		return rwwhr;
	}

	public void setRwwhr(String rwwhr) {
		this.rwwhr = rwwhr;
	}

	public String getRwwhsj() {
		return rwwhsj;
	}

	public void setRwwhsj(String rwwhsj) {
		this.rwwhsj = rwwhsj;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getFengzhi() {
		return fengzhi;
	}

	public void setFengzhi(String fengzhi) {
		this.fengzhi = fengzhi;
	}

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

	public String getRwid() {
		return rwid;
	}

	public void setRwid(String rwid) {
		this.rwid = rwid;
	}

	public String getJkbz() {
		return jkbz;
	}

	public void setJkbz(String jkbz) {
		this.jkbz = jkbz;
	}

	public String getSyts() {
		return syts;
	}

	public void setSyts(String syts) {
		this.syts = syts;
	}

	public String getSy() {
		return sy;
	}

	public void setSy(String sy) {
		this.sy = sy;
	}

	public String getYc() {
		return yc;
	}

	public void setYc(String yc) {
		this.yc = yc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public Integer getBjlx() {
		return bjlx;
	}

	public void setBjlx(Integer bjlx) {
		this.bjlx = bjlx;
	}

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}

	public String getNbsbm() {
		return nbsbm;
	}

	public void setNbsbm(String nbsbm) {
		this.nbsbm = nbsbm;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}

	public String getCjjh() {
		return cjjh;
	}

	public void setCjjh(String cjjh) {
		this.cjjh = cjjh;
	}

	public String getZwmc() {
		return zwmc;
	}

	public void setZwmc(String zwmc) {
		this.zwmc = zwmc;
	}

	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	public String getBjgzjl() {
		return bjgzjl;
	}

	public void setBjgzjl(String bjgzjl) {
		this.bjgzjl = bjgzjl;
	}

	public Integer getZjsl() {
		return zjsl;
	}

	public void setZjsl(Integer zjsl) {
		this.zjsl = zjsl;
	}

	public Integer getWz() {
		return wz;
	}

	public void setWz(Integer wz) {
		this.wz = wz;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getScrq() {
		return scrq;
	}

	public void setScrq(String scrq) {
		this.scrq = scrq;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getAzrq() {
		return azrq;
	}

	public void setAzrq(String azrq) {
		this.azrq = azrq;
	}

	public String getAzjldh() {
		return azjldh;
	}

	public void setAzjldh(String azjldh) {
		this.azjldh = azjldh;
	}

	public String getCcrq() {
		return ccrq;
	}

	public void setCcrq(String ccrq) {
		this.ccrq = ccrq;
	}

	public String getCcjldh() {
		return ccjldh;
	}

	public void setCcjldh(String ccjldh) {
		this.ccjldh = ccjldh;
	}

	public String getLlklx() {
		return llklx;
	}

	public void setLlklx(String llklx) {
		this.llklx = llklx;
	}

	public String getLlkbh() {
		return llkbh;
	}

	public void setLlkbh(String llkbh) {
		this.llkbh = llkbh;
	}

	public Integer getKzlx() {
		return kzlx;
	}

	public void setKzlx(Integer kzlx) {
		this.kzlx = kzlx;
	}

	public Integer getIs_dj() {
		return is_dj;
	}

	public void setIs_dj(Integer is_dj) {
		this.is_dj = is_dj;
	}

	public String getTsn() {
		return tsn;
	}

	public void setTsn(String tsn) {
		this.tsn = tsn;
	}

	public String getTso() {
		return tso;
	}

	public void setTso(String tso) {
		this.tso = tso;
	}

	public Integer getCj() {
		return cj;
	}

	public void setCj(Integer cj) {
		this.cj = cj;
	}

	public String getFjdid() {
		return fjdid;
	}

	public void setFjdid(String fjdid) {
		this.fjdid = fjdid;
	}

	public String getKbs() {
		return kbs;
	}

	public void setKbs(String kbs) {
		this.kbs = kbs;
	}

	public String getSxrq() {
		return sxrq;
	}

	public void setSxrq(String sxrq) {
		this.sxrq = sxrq;
	}

	public Integer getSxzt() {
		return sxzt;
	}

	public void setSxzt(Integer sxzt) {
		this.sxzt = sxzt;
	}

	public String getWhsj() {
		return whsj;
	}

	public void setWhsj(String whsj) {
		this.whsj = whsj;
	}

	public String getDprtCode() {
		return dprtCode;
	}

	public void setDprtCode(String dprtCode) {
		this.dprtCode = dprtCode;
	}

}