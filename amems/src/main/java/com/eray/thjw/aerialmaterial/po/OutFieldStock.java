package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.util.Utils;

/**
 * b_h_003 外场虚拟库存表
 * 
 * @author xu.yong
 * 
 */
public class OutFieldStock extends BizEntity{
	private String id;

	private String xgdjid;

	private String kcid;

	private String dprtcode;

	private Integer sjly;

	private Integer cklb;

	private String ckid;

	private String ckh;

	private String ckmc;

	private String kwid;

	private String kwh;

	private String bjid;

	private String bjh;

	private String pch;

	private String sn;

	private String zwms;

	private String ywms;

	private String jldw;

	private BigDecimal kcsl;
	
	private BigDecimal djsl;

	private String cghtid;

	private String htbhCg;

	private String sxhtid;

	private String htbhSx;

	private String tddid;

	private String tddh;

	private Date rksj;

	private String rkbmid;

	private String rkrid;

	private String shzh;

	private String shzwz;

	private Integer zt;

	private String bz;

	private String jydid;

	private Date hjsm;

	private Date spqx;

	private Date scrq;

	private String xh;

	private String gg;
	
	private String grn;

	private String zzcs;

	private String tsn;

	private String tso;
	private String csn;
	
	private String cso;

	private String hcly;

	private String xkzh;

	private String bjgzjl;

	private Integer llklx;

	private String llkbh;

	private Integer kzlx;

	private Integer isDj;

	private BigDecimal kccb;
	
	private BigDecimal jz;
	
	private String jzbz;
	
	private String cfyq;
	
	private String biz;
	
	private String fjzch;
	
	private String cqid;
	
	private String shdmxid;
	
	private String qczt;	//器材状态

	private String displayName; // 显示名称

	private List<String> dprtcodes; // 角色对应的组织机构集合

	private HCMainData hcMainData; // 航材主数据

    private String cjjh; //虚拟字段：厂家件号
    
    private String hclx; //虚拟字段：航材类型
    
    private Integer gljb; //虚拟字段：管理级别
	
    private String bfyy; //虚拟字段：报废原因
    
    private String whrid;	// 维护人id
    
    private Date whsj;	// 维护时间
    
    private List<String> notIds;
    
    private User sjr;
    
    private User xgr;
    
    public String getUuiddm() {
		return uuiddm;
	}

	public void setUuiddm(String uuiddm) {
		this.uuiddm = uuiddm;
	}

	private String uuiddm;

    /** 部件证书 */
    private List<ComponentCertificate> certificates;
    
	public OutFieldStock(){}
	
    public OutFieldStock(Stock stock){
    	this.kcid = stock.getId();
    	this.dprtcode = stock.getDprtcode();
    	this.cklb = stock.getCklb();
    	this.ckid = stock.getCkid();
    	this.ckh = stock.getCkh();
    	this.kwid = stock.getKwid();
    	this.ckmc = stock.getCkmc();
    	this.kwh = stock.getKwh();
    	this.bjid = stock.getBjid();
    	this.bjh = stock.getBjh();
    	this.pch = stock.getPch();
    	this.sn = stock.getSn();
    	this.zwms = stock.getZwms();
    	this.ywms = stock.getYwms();
    	this.jldw = stock.getJldw();
    	this.kcsl = stock.getKcsl();
    	this.cghtid = stock.getCghtid();
    	this.htbhCg = stock.getHtbhCg();
    	this.sxhtid = stock.getSxhtid();
    	this.htbhSx = stock.getHtbhSx();
    	this.tddid = stock.getTddid();
    	this.tddh = stock.getTddh();
    	this.rksj = stock.getRksj();
    	this.rkbmid = stock.getRkbmid();
    	this.rkrid = stock.getRkrid();
    	this.shzh = stock.getShzh();
    	this.shzwz = stock.getShzwz();
    	this.zt = stock.getZt();
    	this.bz = stock.getBz();
    	this.jydid = stock.getJydid();
    	this.hjsm = stock.getHjsm();
    	this.spqx = stock.getSpqx();
    	this.scrq = stock.getScrq();
    	this.xh = stock.getXh();
    	this.gg = stock.getGg();
    	this.grn = stock.getGrn();
    	this.zzcs = stock.getZzcs();
    	this.tsn = stock.getTsn();
    	this.tso = stock.getTso();
    	this.csn = stock.getCsn();
    	this.cso = stock.getCso();
    	this.hcly = stock.getHcly();
    	this.cfyq = stock.getCfyq();
    	this.bjgzjl = stock.getBjgzjl();
    	this.llklx = stock.getLlklx();
    	this.xkzh = stock.getXkzh();
    	this.llkbh = stock.getLlkbh();
    	this.kzlx = stock.getKzlx();
    	this.isDj = stock.getIsDj();
    	this.kccb = stock.getKccb();
    	this.biz = stock.getBiz();
    	this.whrid = stock.getWhrid();
    	this.whsj = stock.getWhsj();
//    	this.fjzch = stock.getfjzch
    	this.cqid = stock.getCqid();
    	this.shdmxid = stock.getShdmxid();
    	this.uuiddm = stock.getUuiddm();
    	this.jz = stock.getJz();
    	this.jzbz = stock.getJzbz();
    	this.qczt = stock.getQczt();
    }
	
	public User getSjr() {
		return sjr;
	}

	public void setSjr(User sjr) {
		this.sjr = sjr;
	}

	public User getXgr() {
		return xgr;
	}

	public void setXgr(User xgr) {
		this.xgr = xgr;
	}

	public BigDecimal getJz() {
		return jz;
	}

	public void setJz(BigDecimal jz) {
		this.jz = jz;
	}

	public String getJzbz() {
		return jzbz;
	}

	public void setJzbz(String jzbz) {
		this.jzbz = jzbz;
	}

	public List<ComponentCertificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<ComponentCertificate> certificates) {
		this.certificates = certificates;
	}

	public String getCsn() {
		return csn;
	}

	public void setCsn(String csn) {
		this.csn = csn;
	}

	public String getCso() {
		return cso;
	}

	public void setCso(String cso) {
		this.cso = cso;
	}

	public String getBfyy() {
		return bfyy;
	}

	public void setBfyy(String bfyy) {
		this.bfyy = bfyy;
	}

	public String getCjjh() {
		return cjjh;
	}

	public void setCjjh(String cjjh) {
		this.cjjh = cjjh;
	}

	public String getHclx() {
		return hclx;
	}

	public void setHclx(String hclx) {
		this.hclx = hclx;
	}


	public Integer getGljb() {
		return gljb;
	}

	public void setGljb(Integer gljb) {
		this.gljb = gljb;
	}

	public String getCfyq() {
		return cfyq;
	}

	public void setCfyq(String cfyq) {
		this.cfyq = cfyq;
	}

	public String getXkzh() {
		return xkzh;
	}

	public void setXkzh(String xkzh) {
		this.xkzh = xkzh;
	}

	public String getBjgzjl() {
		return bjgzjl;
	}

	public void setBjgzjl(String bjgzjl) {
		this.bjgzjl = bjgzjl;
	}

	public String getLlkbh() {
		return llkbh;
	}

	public void setLlkbh(String llkbh) {
		this.llkbh = llkbh;
	}

	public Integer getLlklx() {
		return llklx;
	}

	public void setLlklx(Integer llklx) {
		this.llklx = llklx;
	}

	public Integer getKzlx() {
		return kzlx;
	}

	public void setKzlx(Integer kzlx) {
		this.kzlx = kzlx;
	}

	public Integer getIsDj() {
		return isDj;
	}

	public void setIsDj(Integer isDj) {
		this.isDj = isDj;
	}

	public BigDecimal getKccb() {
		return kccb;
	}

	public void setKccb(BigDecimal kccb) {
		this.kccb = kccb;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getXgdjid() {
		return xgdjid;
	}

	public void setXgdjid(String xgdjid) {
		this.xgdjid = xgdjid == null ? null : xgdjid.trim();
	}

	public String getKcid() {
		return kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid == null ? null : kcid.trim();
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode == null ? null : dprtcode.trim();
	}

	public String getCkid() {
		return ckid;
	}

	public void setCkid(String ckid) {
		this.ckid = ckid == null ? null : ckid.trim();
	}

	public String getCkh() {
		return ckh;
	}

	public void setCkh(String ckh) {
		this.ckh = ckh == null ? null : ckh.trim();
	}

	public String getCkmc() {
		return ckmc;
	}

	public void setCkmc(String ckmc) {
		this.ckmc = ckmc == null ? null : ckmc.trim();
	}

	public String getKwid() {
		return kwid;
	}

	public void setKwid(String kwid) {
		this.kwid = kwid == null ? null : kwid.trim();
	}

	public String getKwh() {
		return kwh;
	}

	public void setKwh(String kwh) {
		this.kwh = kwh == null ? null : kwh.trim();
	}

	public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid == null ? null : bjid.trim();
	}

	public String getBjh() {
		return bjh;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh == null ? null : bjh.trim();
	}

	public String getPch() {
		return pch;
	}

	public void setPch(String pch) {
		this.pch = pch == null ? null : pch.trim();
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn == null ? null : sn.trim();
	}

	public String getZwms() {
		return zwms;
	}

	public void setZwms(String zwms) {
		this.zwms = zwms == null ? null : zwms.trim();
	}

	public String getYwms() {
		return ywms;
	}

	public void setYwms(String ywms) {
		this.ywms = ywms == null ? null : ywms.trim();
	}

	public String getJldw() {
		return jldw;
	}

	public void setJldw(String jldw) {
		this.jldw = jldw == null ? null : jldw.trim();
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

	public String getCghtid() {
		return cghtid;
	}

	public void setCghtid(String cghtid) {
		this.cghtid = cghtid == null ? null : cghtid.trim();
	}

	public String getHtbhCg() {
		return htbhCg;
	}

	public void setHtbhCg(String htbhCg) {
		this.htbhCg = htbhCg == null ? null : htbhCg.trim();
	}

	public String getSxhtid() {
		return sxhtid;
	}

	public void setSxhtid(String sxhtid) {
		this.sxhtid = sxhtid == null ? null : sxhtid.trim();
	}

	public String getHtbhSx() {
		return htbhSx;
	}

	public void setHtbhSx(String htbhSx) {
		this.htbhSx = htbhSx == null ? null : htbhSx.trim();
	}

	public String getTddid() {
		return tddid;
	}

	public void setTddid(String tddid) {
		this.tddid = tddid == null ? null : tddid.trim();
	}

	public String getTddh() {
		return tddh;
	}

	public void setTddh(String tddh) {
		this.tddh = tddh == null ? null : tddh.trim();
	}

	public Date getRksj() {
		return rksj;
	}

	public void setRksj(Date rksj) {
		this.rksj = rksj;
	}

	public String getRkbmid() {
		return rkbmid;
	}

	public void setRkbmid(String rkbmid) {
		this.rkbmid = rkbmid == null ? null : rkbmid.trim();
	}

	public String getRkrid() {
		return rkrid;
	}

	public void setRkrid(String rkrid) {
		this.rkrid = rkrid == null ? null : rkrid.trim();
	}

	public String getShzh() {
		return shzh;
	}

	public void setShzh(String shzh) {
		this.shzh = shzh == null ? null : shzh.trim();
	}

	public String getShzwz() {
		return shzwz;
	}

	public void setShzwz(String shzwz) {
		this.shzwz = shzwz == null ? null : shzwz.trim();
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz == null ? null : bz.trim();
	}

	public String getJydid() {
		return jydid;
	}

	public void setJydid(String jydid) {
		this.jydid = jydid == null ? null : jydid.trim();
	}

	public Date getHjsm() {
		return hjsm;
	}

	public void setHjsm(Date hjsm) {
		this.hjsm = hjsm;
	}

	public Date getSpqx() {
		return spqx;
	}

	public void setSpqx(Date spqx) {
		this.spqx = spqx;
	}

	public Date getScrq() {
		return scrq;
	}

	public void setScrq(Date scrq) {
		this.scrq = scrq;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh == null ? null : xh.trim();
	}

	public String getGg() {
		return gg;
	}

	public void setGg(String gg) {
		this.gg = gg == null ? null : gg.trim();
	}

	public String getZzcs() {
		return zzcs;
	}

	public void setZzcs(String zzcs) {
		this.zzcs = zzcs == null ? null : zzcs.trim();
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

	public Integer getSjly() {
		return sjly;
	}

	public void setSjly(Integer sjly) {
		this.sjly = sjly;
	}

	public Integer getCklb() {
		return cklb;
	}

	public void setCklb(Integer cklb) {
		this.cklb = cklb;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}



	public String getHcly() {
		return hcly;
	}

	public void setHcly(String hcly) {
		this.hcly = hcly;
	}

	public String getDisplayName() {
		if (displayName == null) {
			displayName = "";
			if (!Utils.Str.isEmpty(ywms)) {
				displayName += (ywms + " ");
			}
			if (!Utils.Str.isEmpty(zwms)) {
				displayName += (zwms + " ");
			}
			if (!Utils.Str.isEmpty(bjh)) {
				displayName += (bjh + " ");
			}
			if (!Utils.Str.isEmpty(sn)) {
				displayName += sn;
			} else if (!Utils.Str.isEmpty(pch)) {
				displayName += pch;
			}
		}
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}

	public List<String> getDprtcodes() {
		return dprtcodes;
	}

	public void setDprtcodes(List<String> dprtcodes) {
		this.dprtcodes = dprtcodes;
	}

	public List<String> getNotIds() {
		return notIds;
	}

	public void setNotIds(List<String> notIds) {
		this.notIds = notIds;
	}

	public BigDecimal getDjsl() {
		return djsl;
	}

	public void setDjsl(BigDecimal djsl) {
		this.djsl = djsl;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	public String getGrn() {
		return grn;
	}

	public void setGrn(String grn) {
		this.grn = grn;
	}

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getCqid() {
		return cqid;
	}

	public void setCqid(String cqid) {
		this.cqid = cqid;
	}

	public String getShdmxid() {
		return shdmxid;
	}

	public void setShdmxid(String shdmxid) {
		this.shdmxid = shdmxid;
	}

	public String getQczt() {
		return qczt;
	}

	public void setQczt(String qczt) {
		this.qczt = qczt;
	}

}