package com.eray.thjw.productionplan.po;

import java.math.BigDecimal;

import com.eray.thjw.po.BizEntity;

/**
 * b_s_009 计划任务
 * 
 * @author linlong
 * 
 */
public class ScheduledTask extends BizEntity {

	private String id;

	private String rwdh;

	private Integer rwlx;

	private Integer rwzlx;

	private String fxjldid;

	private Integer zt;

	private Integer xszt;

	private String xggdid;

	private String rwxx;

	private String fjzch;

	private String bjh;

	private String xlh;

	private String dysj;

	private String dybmid;

	private String dyrid;

	private BigDecimal jhgsRs;

	private BigDecimal jhgsXs;

	private BigDecimal sjgsRs;

	private BigDecimal sjgsXs;

	private Integer gsshZt;

	private String gsshDcbbh;

	private String gsshTjrid;

	private String gsshTjrq;

	private String gsshBz;

	private String bz;

	private String whdwid;

	private String whrid;

	private String whsj;

	private String zdjsrid;

	private String zdjsrq;

	private String zdjsyy;

	private String fxDateBegin; // 虚拟字段: 飞行开始时间

	private String fxDateEnd; // 虚拟字段: 飞行结束时间
	
	private String gdzt; // 虚拟字段: 工单状态
	
	private String fxrq; // 虚拟字段: 完成日期
	
	private String pcl; // 虚拟字段: 偏差值
	
	private String dcbbh; // 虚拟字段: 调查表编号
	
	private String tjrxx; // 虚拟字段: 统计人信息
	
	private String tjrid; // 虚拟字段: 统计日期
	
	private String tjrq; // 虚拟字段: 统计日期
	

	public String getTjrid() {
		return tjrid;
	}

	public void setTjrid(String tjrid) {
		this.tjrid = tjrid;
	}

	public String getGdzt() {
		return gdzt;
	}

	public void setGdzt(String gdzt) {
		this.gdzt = gdzt;
	}

	public String getFxrq() {
		return fxrq;
	}

	public void setFxrq(String fxrq) {
		this.fxrq = fxrq;
	}

	public String getPcl() {
		return pcl;
	}

	public void setPcl(String pcl) {
		this.pcl = pcl;
	}

	public String getDcbbh() {
		return dcbbh;
	}

	public void setDcbbh(String dcbbh) {
		this.dcbbh = dcbbh;
	}

	public String getTjrxx() {
		return tjrxx;
	}

	public void setTjrxx(String tjrxx) {
		this.tjrxx = tjrxx;
	}

	public String getTjrq() {
		return tjrq;
	}

	public void setTjrq(String tjrq) {
		this.tjrq = tjrq;
	}

	public String getFxDateBegin() {
		return fxDateBegin;
	}

	public void setFxDateBegin(String fxDateBegin) {
		this.fxDateBegin = fxDateBegin;
	}

	public String getFxDateEnd() {
		return fxDateEnd;
	}

	public void setFxDateEnd(String fxDateEnd) {
		this.fxDateEnd = fxDateEnd;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRwdh() {
		return rwdh;
	}

	public void setRwdh(String rwdh) {
		this.rwdh = rwdh;
	}

	public Integer getRwlx() {
		return rwlx;
	}

	public void setRwlx(Integer rwlx) {
		this.rwlx = rwlx;
	}

	public Integer getRwzlx() {
		return rwzlx;
	}

	public void setRwzlx(Integer rwzlx) {
		this.rwzlx = rwzlx;
	}

	public String getFxjldid() {
		return fxjldid;
	}

	public void setFxjldid(String fxjldid) {
		this.fxjldid = fxjldid;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getXszt() {
		return xszt;
	}

	public void setXszt(Integer xszt) {
		this.xszt = xszt;
	}

	public String getXggdid() {
		return xggdid;
	}

	public void setXggdid(String xggdid) {
		this.xggdid = xggdid;
	}

	public String getRwxx() {
		return rwxx;
	}

	public void setRwxx(String rwxx) {
		this.rwxx = rwxx;
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

	public String getDysj() {
		return dysj;
	}

	public void setDysj(String dysj) {
		this.dysj = dysj;
	}

	public String getDybmid() {
		return dybmid;
	}

	public void setDybmid(String dybmid) {
		this.dybmid = dybmid;
	}

	public String getDyrid() {
		return dyrid;
	}

	public void setDyrid(String dyrid) {
		this.dyrid = dyrid;
	}

	public BigDecimal getJhgsRs() {
		return jhgsRs;
	}

	public void setJhgsRs(BigDecimal jhgsRs) {
		this.jhgsRs = jhgsRs;
	}

	public BigDecimal getJhgsXs() {
		return jhgsXs;
	}

	public void setJhgsXs(BigDecimal jhgsXs) {
		this.jhgsXs = jhgsXs;
	}

	public BigDecimal getSjgsRs() {
		return sjgsRs;
	}

	public void setSjgsRs(BigDecimal sjgsRs) {
		this.sjgsRs = sjgsRs;
	}

	public BigDecimal getSjgsXs() {
		return sjgsXs;
	}

	public void setSjgsXs(BigDecimal sjgsXs) {
		this.sjgsXs = sjgsXs;
	}

	public Integer getGsshZt() {
		return gsshZt;
	}

	public void setGsshZt(Integer gsshZt) {
		this.gsshZt = gsshZt;
	}

	public String getGsshDcbbh() {
		return gsshDcbbh;
	}

	public void setGsshDcbbh(String gsshDcbbh) {
		this.gsshDcbbh = gsshDcbbh;
	}

	public String getGsshTjrid() {
		return gsshTjrid;
	}

	public void setGsshTjrid(String gsshTjrid) {
		this.gsshTjrid = gsshTjrid;
	}

	public String getGsshTjrq() {
		return gsshTjrq;
	}

	public void setGsshTjrq(String gsshTjrq) {
		this.gsshTjrq = gsshTjrq;
	}

	public String getGsshBz() {
		return gsshBz;
	}

	public void setGsshBz(String gsshBz) {
		this.gsshBz = gsshBz;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
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

	public String getWhsj() {
		return whsj;
	}

	public void setWhsj(String whsj) {
		this.whsj = whsj;
	}

	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid;
	}

	public String getZdjsrq() {
		return zdjsrq;
	}

	public void setZdjsrq(String zdjsrq) {
		this.zdjsrq = zdjsrq;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}

}