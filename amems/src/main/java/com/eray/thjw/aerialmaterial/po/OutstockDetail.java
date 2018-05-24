package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * b_h_01301 出库单明细表
 * 
 * @author xu.yong
 * 
 */
public class OutstockDetail extends BaseEntity {
	private static final long serialVersionUID = 1L;

	private String id;

	private String mainid;

	private String lydid;

	private String lydmxid;

	private String wpqdid;

	private String kcllid;

	private String whdwid;

	private String whrid;

	private Date whsj;

	private BigDecimal tksl;

	private Integer zt;
	/** 虚拟字段 开始 **/

	private Integer cklx; // 出库类型

	private Integer ckzt; // 出库状态

	private String zwms;

	private String ywms;

	private String pch;

	private String sn;
	
	private String grn;

	private String shzh;

	private Date hjsm;

	private BigDecimal kcsl;

	private String jldw;

	private String ckh;
	
	private String ckmc;
	
	private String kwh;

	private String cjjh;

	private Integer hclx;
	
	private String ckid;
	
	private Integer gljb;//管理级别
	
	private String bjh;
	
	private MaterialHistory materialHistory;//航材履历
	
	private Outstock outstock;

	/** 虚拟字段 结束 **/
	
	

	public BigDecimal getTksl() {
		return tksl;
	}

	public String getGrn() {
		return grn;
	}

	public void setGrn(String grn) {
		this.grn = grn;
	}

	public String getCkmc() {
		return ckmc;
	}

	public void setCkmc(String ckmc) {
		this.ckmc = ckmc;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBjh() {
		return bjh;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh;
	}

	public String getCkid() {
		return ckid;
	}

	public void setCkid(String ckid) {
		this.ckid = ckid;
	}

	public Integer getCklx() {
		return cklx;
	}

	public void setCklx(Integer cklx) {
		this.cklx = cklx;
	}

	public Integer getCkzt() {
		return ckzt;
	}

	public void setCkzt(Integer ckzt) {
		this.ckzt = ckzt;
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

	public String getPch() {
		return pch;
	}

	public void setPch(String pch) {
		this.pch = pch;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getShzh() {
		return shzh;
	}

	public void setShzh(String shzh) {
		this.shzh = shzh;
	}

	public Date getHjsm() {
		return hjsm;
	}

	public void setHjsm(Date hjsm) {
		this.hjsm = hjsm;
	}

	public BigDecimal getKcsl() {
		if(kcsl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(kcsl));
    	}
        return kcsl;
	}

	public void setKcsl(BigDecimal kcsl) {
		this.kcsl = kcsl;
	}

	public String getJldw() {
		return jldw;
	}

	public void setJldw(String jldw) {
		this.jldw = jldw;
	}

	public String getCkh() {
		return ckh;
	}

	public void setCkh(String ckh) {
		this.ckh = ckh;
	}

	public String getKwh() {
		return kwh;
	}

	public void setKwh(String kwh) {
		this.kwh = kwh;
	}

	public String getCjjh() {
		return cjjh;
	}

	public void setCjjh(String cjjh) {
		this.cjjh = cjjh;
	}

	public Integer getHclx() {
		return hclx;
	}

	public void setHclx(Integer hclx) {
		this.hclx = hclx;
	}

	public void setTksl(BigDecimal tksl) {
		this.tksl = tksl;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

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

	public String getLydid() {
		return lydid;
	}

	public void setLydid(String lydid) {
		this.lydid = lydid == null ? null : lydid.trim();
	}

	public String getLydmxid() {
		return lydmxid;
	}

	public void setLydmxid(String lydmxid) {
		this.lydmxid = lydmxid == null ? null : lydmxid.trim();
	}

	public String getWpqdid() {
		return wpqdid;
	}

	public void setWpqdid(String wpqdid) {
		this.wpqdid = wpqdid == null ? null : wpqdid.trim();
	}

	public String getKcllid() {
		return kcllid;
	}

	public void setKcllid(String kcllid) {
		this.kcllid = kcllid == null ? null : kcllid.trim();
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

	public Integer getGljb() {
		return gljb;
	}

	public void setGljb(Integer gljb) {
		this.gljb = gljb;
	}

	public MaterialHistory getMaterialHistory() {
		return materialHistory;
	}

	public void setMaterialHistory(MaterialHistory materialHistory) {
		this.materialHistory = materialHistory;
	}

	public Outstock getOutstock() {
		return outstock;
	}

	public void setOutstock(Outstock outstock) {
		this.outstock = outstock;
	}
	
}