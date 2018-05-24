package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.basic.po.Propertyright;
import com.eray.thjw.material2.po.MaterialBatchInfo;
import com.eray.thjw.material2.po.OutinReceipt;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

import enu.aerialmaterial.MaterialEnum;
import enu.aerialmaterial.MaterialLevelEnum;
/*
 * b_h_001 库存表
 * 
 */
public class Stock extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

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
    
    private String cfyq;
    
    private Integer kzlx;
    
    private Integer isDj;
    
    private BigDecimal kccb;
    
    private String whrid;
    
    private Date whsj;
    
    private String shdmxid;
    
    private String cjjh; //虚拟字段：厂家件号
    
    private String hclx; //虚拟字段：航材类型
    
    private String hclxText;
    
    private String hclxEj; //虚拟字段：航材二级类型
    
    private String gljb; //虚拟字段：管理级别
    
    private String gljbText;

    private String syts; //虚拟字段：剩余天数 货架寿命
    
    private String sytss; //虚拟字段：剩余天数 索赔期限
    
    private String cangkuzt; //虚拟字段：仓库状态
    
    private HCMainData hCMainData;
    
    private String dprtcode; //虚拟字段：仓库状态
    
    //扩展区域
    private String displayName;
    
    //当前组织机构
    private String oldDprtcode;
    
    //工具借用状态
    private String borrowStatus;
    
    //工具借用状态
    private String borrowStatusText;
    
    //工具位置
    private String position;
    
    //借用记录ID
    private String brrowId;
    
    private Department jg_dprt;
    
    //设备工具，使用状态
    private String syzt;
    
    //库存来源
    private String kcly;
    
    //入库人
    private User rkr_user;
    
    //库存id
    private String kcid;
    
    //销毁存放的idlist
    
    private List<String> destIds;
    
    
    private String cqid;
    
    //产权信息
    private Propertyright propertyRight;
    
    
    private String uuiddm;
    
    //币种
    private String biz; 
    
    //价值
    private BigDecimal jz; 
    
    //价值币种
    private String jzbz; 
    
    //器材状态
    private String qczt;
    
    //收货单
    private OutinReceipt receipt;
    
    //质检单
    private Inspection inspection;
    
    //物料批次信息
    private MaterialBatchInfo materialBatchInfo;
    
    
    public String getQczt() {
		return qczt;
	}

	public void setQczt(String qczt) {
		this.qczt = qczt;
	}

	public MaterialBatchInfo getMaterialBatchInfo() {
		return materialBatchInfo;
	}

	public void setMaterialBatchInfo(MaterialBatchInfo materialBatchInfo) {
		this.materialBatchInfo = materialBatchInfo;
	}
    private List<ComponentCertificate> componentCertificateList;
    
    
    
    public List<ComponentCertificate> getComponentCertificateList() {
		return componentCertificateList;
	}

	public void setComponentCertificateList(List<ComponentCertificate> componentCertificateList) {
		this.componentCertificateList = componentCertificateList;
	}

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}

	public String getUuiddm() {
		return uuiddm;
	}

	public void setUuiddm(String uuiddm) {
		this.uuiddm = uuiddm;
	}

	public String getCqid() {
		return cqid;
	}

	public void setCqid(String cqid) {
		this.cqid = cqid;
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

	public String getGrn() {
		return grn;
	}

	public void setGrn(String grn) {
		this.grn = grn;
	}

	public String getHclxText() {
    	if(hclx!=null && !"".equals(hclx)){
    		return MaterialEnum.getName(Integer.valueOf(hclx));
    	}
    		return hclxText;
	}

	public void setHclxText(String hclxText) {
		this.hclxText = hclxText;
	}

	public String getGljbText() {
		if(gljb!=null && !"".equals(gljb)){
    		return MaterialLevelEnum.getName(Integer.valueOf(gljb));
    	}
    		return gljbText;
	}

	public void setGljbText(String gljbText) {
		this.gljbText = gljbText;
	}

	public String getKcid() {
		return kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid;
	}

	public List<String> getDestIds() {
		return destIds;
	}

	public void setDestIds(List<String> destIds) {
		this.destIds = destIds;
	}

	public User getRkr_user() {
		return rkr_user;
	}

	public void setRkr_user(User rkr_user) {
		this.rkr_user = rkr_user;
	}


	public String getSytss() {
		return sytss;
	}

	public void setSytss(String sytss) {
		this.sytss = sytss;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public String getOldDprtcode() {
		return oldDprtcode;
	}

	public void setOldDprtcode(String oldDprtcode) {
		this.oldDprtcode = oldDprtcode;
	}

	public String getDisplayName() {
		displayName = (bjh==null?"":bjh).concat(" ").concat(ywms==null?"":ywms).concat(" ").concat(pch==null?(sn==null?"":sn):pch).concat(" ").concat(shzh==null?"":shzh);
		return displayName;
	}

	public void setDisplayName(String dispalyName) {
		this.displayName = dispalyName;
	}
    
	public Stock(){}
    
    public Stock(MaterialHistory materialHistory){
    	this.id = materialHistory.getKcid();
    	this.dprtcode = materialHistory.getDprtcode();
    	this.cklb = materialHistory.getCklb();
    	this.ckid = materialHistory.getCkid();
    	this.ckh = materialHistory.getCkh();
    	this.ckmc = materialHistory.getCkmc();
    	this.kwid = materialHistory.getKwid();
    	this.kwh = materialHistory.getKwh();
    	this.bjid = materialHistory.getBjid();
    	this.bjh = materialHistory.getBjh();
    	this.pch = materialHistory.getPch();
    	this.sn = materialHistory.getSn();
    	this.zwms = materialHistory.getZwms();
    	this.ywms = materialHistory.getYwms();
    	this.jldw = materialHistory.getJldw();
    	this.kcsl = materialHistory.getKcsl();
    	this.djsl = materialHistory.getDjsl();
    	this.cghtid = materialHistory.getCghtid();
    	this.htbhCg = materialHistory.getHtbhCg();
    	this.sxhtid = materialHistory.getSxhtid();
    	this.htbhSx = materialHistory.getHtbhSx();
    	this.tddid = materialHistory.getTddid();
    	this.tddh = materialHistory.getTddh();
    	this.rksj = materialHistory.getRksj();
    	this.rkbmid = materialHistory.getRkbmid();
    	this.rkrid = materialHistory.getRkrid();
    	this.shzh = materialHistory.getShzh();
    	this.shzwz = materialHistory.getShzwz();
    	this.zt = materialHistory.getZt();
    	this.bz = materialHistory.getBz();
    	this.jydid = materialHistory.getJydid();
    	this.hjsm = materialHistory.getHjsm();
    	this.spqx = materialHistory.getSpqx();
    	this.scrq = materialHistory.getScrq();
    	this.xh = materialHistory.getXh();
    	this.gg = materialHistory.getGg();
    	this.zzcs = materialHistory.getZzcs();
    	this.tsn = materialHistory.getTsn();
    	this.tso = materialHistory.getTso();
    	this.csn = materialHistory.getCsn();
    	this.cso = materialHistory.getCso();
    	this.hcly = materialHistory.getHcly();
    	this.cfyq = materialHistory.getCfyq();
    	this.bjgzjl = materialHistory.getBjgzjl();
    	this.llklx = materialHistory.getLlklx();
    	this.xkzh = materialHistory.getXkzh();
    	this.llkbh = materialHistory.getLlkbh();
    	this.kzlx = materialHistory.getKzlx();
    	this.isDj = materialHistory.getIsDj();
    	this.kccb = materialHistory.getKccb();
    	this.biz = materialHistory.getBiz();
    	this.cqid = materialHistory.getCqid();
    	this.shdmxid = materialHistory.getShdmxid();
    	this.uuiddm = materialHistory.getUuiddm();
    	this.jz = materialHistory.getJz();
    	this.jzbz = materialHistory.getJzbz();
    	this.grn = materialHistory.getGrn();
    	this.qczt = materialHistory.getQczt();
    }
    
    public Stock(OutFieldStock outFieldStock){
    	this.id=outFieldStock.getId();
    	this.dprtcode = outFieldStock.getDprtcode();
    	this.cklb = outFieldStock.getCklb();
    	this.ckid = outFieldStock.getCkid();
    	this.ckh = outFieldStock.getCkh();
    	this.ckmc = outFieldStock.getCkmc();
    	this.kwid = outFieldStock.getKwid();
    	this.kwh = outFieldStock.getKwh();
    	this.bjid = outFieldStock.getBjid();
    	this.bjh = outFieldStock.getBjh();
    	this.pch = outFieldStock.getPch();
    	this.sn = outFieldStock.getSn();
    	this.zwms = outFieldStock.getZwms();
    	this.ywms = outFieldStock.getYwms();
    	this.jldw = outFieldStock.getJldw();
    	this.cghtid = outFieldStock.getCghtid();
    	this.htbhCg = outFieldStock.getHtbhCg();
    	this.sxhtid = outFieldStock.getSxhtid();
    	this.htbhSx = outFieldStock.getHtbhSx();
    	this.tddid = outFieldStock.getTddid();
    	this.tddh = outFieldStock.getTddh();
    	this.rksj = outFieldStock.getRksj();
    	this.rkbmid = outFieldStock.getRkbmid();
    	this.rkrid = outFieldStock.getRkrid();
    	this.shzh = outFieldStock.getShzh();
    	this.shzwz = outFieldStock.getShzwz();
    	this.zt = outFieldStock.getZt();
    	this.bz = outFieldStock.getBz();
    	this.jydid = outFieldStock.getJydid();
    	this.hjsm = outFieldStock.getHjsm();
    	this.spqx = outFieldStock.getSpqx();
    	this.scrq = outFieldStock.getScrq();
    	this.xh = outFieldStock.getXh();
    	this.gg = outFieldStock.getGg();
    	this.zzcs = outFieldStock.getZzcs();
    	this.tsn = outFieldStock.getTsn();
    	this.tso = outFieldStock.getTso();
    	this.csn = outFieldStock.getCsn();
    	this.cso = outFieldStock.getCso();
    	this.hcly = outFieldStock.getHcly();
    	this.cfyq = outFieldStock.getCfyq();
    	this.bjgzjl = outFieldStock.getBjgzjl();
    	this.llklx = outFieldStock.getLlklx();
    	this.xkzh = outFieldStock.getXkzh();
    	this.llkbh = outFieldStock.getLlkbh();
    	this.kzlx = outFieldStock.getKzlx();
    	this.isDj = outFieldStock.getIsDj();
    	this.kccb = outFieldStock.getKccb();
    }
    
    
    public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
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

	public String getCangkuzt() {
		return cangkuzt;
	}

	public void setCangkuzt(String cangkuzt) {
		this.cangkuzt = cangkuzt;
	}

	public String getHclx() {
		return hclx;
	}

	public void setHclx(String hclx) {
		this.hclx = hclx;
	}

	public String getGljb() {
		return gljb;
	}

	public void setGljb(String gljb) {
		this.gljb = gljb;
	}

	public String getSyts() {
		return syts;
	}

	public void setSyts(String syts) {
		this.syts = syts;
	}

	public String getCjjh() {
		return cjjh;
	}

	public void setCjjh(String cjjh) {
		this.cjjh = cjjh;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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
    		return kcsl.setScale(2, RoundingMode.HALF_UP);
    	}
        return kcsl;
    }

    public void setKcsl(BigDecimal kcsl) {
        this.kcsl = kcsl;
    }

    public BigDecimal getDjsl() {
        return djsl;
    }

    public void setDjsl(BigDecimal djsl) {
        this.djsl = djsl;
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

	public HCMainData gethCMainData() {
		return hCMainData;
	}

	public void sethCMainData(HCMainData hCMainData) {
		this.hCMainData = hCMainData;
	}
	/**
	 * 将库存转为库存履历
	 * @return
	 */
	public MaterialHistory toMaterialHistory(){
		MaterialHistory materialHistory = new MaterialHistory();
		materialHistory.setDprtcode(dprtcode);
		materialHistory.setKcid(id);
		materialHistory.setCklb(cklb);
		materialHistory.setCkid(ckid);
		materialHistory.setCkh(ckh);;
		materialHistory.setCkmc(ckmc);
		materialHistory.setKwid(kwid);
	    materialHistory.setKwh(kwh);
	    materialHistory.setBjid(bjid);
	    materialHistory.setBjh(bjh);
	    materialHistory.setPch(pch);
	    materialHistory.setSn(sn);
	    materialHistory.setZwms(zwms);
	    materialHistory.setYwms(ywms);
	    materialHistory.setJldw(jldw);
	    materialHistory.setKcsl(kcsl);
	    materialHistory.setDjsl(djsl);
	    materialHistory.setCghtid(cghtid);
	    materialHistory.setHtbhCg(htbhCg);
	    materialHistory.setSxhtid(sxhtid);
	    materialHistory.setHtbhSx(htbhSx);
	    materialHistory.setTddid(tddid);
	    materialHistory.setTddh(tddh);
	    materialHistory.setRksj(rksj);
	    materialHistory.setRkbmid(rkbmid);
	    materialHistory.setRkrid(rkrid);
	    materialHistory.setShzh(shzh);
	    materialHistory.setShzwz(shzwz);
	    materialHistory.setZt(zt);
	    materialHistory.setBz(bz);
	    materialHistory.setJydid(jydid);
	    materialHistory.setHjsm(hjsm);
	    materialHistory.setSpqx(spqx);
	    materialHistory.setScrq(scrq);
	    materialHistory.setXh(xh);
	    materialHistory.setGg(gg);
	    materialHistory.setZzcs(zzcs);
	    materialHistory.setTsn(tsn);
	    materialHistory.setTso(tso);
	    materialHistory.setHcly(hcly);
	    materialHistory.setXkzh(xkzh);
	    materialHistory.setBjgzjl(bjgzjl);
	    materialHistory.setLlklx(llklx);
	    materialHistory.setLlkbh(llkbh);
	    materialHistory.setCfyq(cfyq);
	    materialHistory.setKzlx(kzlx);
	    materialHistory.setIsDj(isDj);
	    materialHistory.setKccb(kccb);
	    materialHistory.setCsn(csn);
	    materialHistory.setCso(cso);
	    materialHistory.setBiz(biz);//
	    materialHistory.setGrn(grn);//
	    materialHistory.setCqid(cqid);//
	    materialHistory.setShdmxid(shdmxid);//
	    materialHistory.setUuiddm(uuiddm);//
	    materialHistory.setJz(jz);//
	    materialHistory.setJzbz(jzbz);//
	    materialHistory.setQczt(qczt);
	    return materialHistory;
	    
	}
	
	/**
	 * 将库存转为外场虚拟库存
	 * @return
	 */
	public OutFieldStock toOutFieldStock(){
		OutFieldStock outFieldStock = new OutFieldStock();
		outFieldStock.setDprtcode(dprtcode);
		outFieldStock.setKcid(id);
		outFieldStock.setCklb(cklb);
		outFieldStock.setCkid(ckid);
		outFieldStock.setCkh(ckh);;
		outFieldStock.setCkmc(ckmc);
		outFieldStock.setKwid(kwid);
		outFieldStock.setKwh(kwh);
	    outFieldStock.setBjid(bjid);
	    outFieldStock.setBjh(bjh);
	    outFieldStock.setPch(pch);
	    outFieldStock.setSn(sn);
	    outFieldStock.setZwms(zwms);
	    outFieldStock.setYwms(ywms);
	    outFieldStock.setJldw(jldw);
	    outFieldStock.setKcsl(kcsl);
	    outFieldStock.setCghtid(cghtid);
	    outFieldStock.setHtbhCg(htbhCg);
	    outFieldStock.setSxhtid(sxhtid);
	    outFieldStock.setHtbhSx(htbhSx);
	    outFieldStock.setTddid(tddid);
	    outFieldStock.setTddh(tddh);
	    outFieldStock.setRksj(rksj);
	    outFieldStock.setRkbmid(rkbmid);
	    outFieldStock.setRkrid(rkrid);
	    outFieldStock.setShzh(shzh);
	    outFieldStock.setShzwz(shzwz);
	    outFieldStock.setZt(zt);
	    outFieldStock.setBz(bz);
	    outFieldStock.setJydid(jydid);
	    outFieldStock.setHjsm(hjsm);
	    outFieldStock.setSpqx(spqx);
	    outFieldStock.setScrq(scrq);
	    outFieldStock.setXh(xh);
	    outFieldStock.setGg(gg);
	    outFieldStock.setZzcs(zzcs);
	    outFieldStock.setTsn(tsn);
	    outFieldStock.setTso(tso);
	    outFieldStock.setHcly(hcly);
	    outFieldStock.setXkzh(xkzh);
	    outFieldStock.setBjgzjl(bjgzjl);
	    outFieldStock.setLlklx(llklx);
	    outFieldStock.setLlkbh(llkbh);
	    outFieldStock.setCfyq(cfyq);
	    outFieldStock.setKzlx(kzlx);
	    outFieldStock.setIsDj(isDj);
	    outFieldStock.setKccb(kccb);
	    return outFieldStock;
	    
	}

	@Override
	public String toString() {
		return "Stock [id=" + id + ", cklb=" + cklb + ", ckid=" + ckid
				+ ", ckh=" + ckh + ", ckmc=" + ckmc + ", kwid=" + kwid
				+ ", kwh=" + kwh + ", bjid=" + bjid + ", bjh=" + bjh + ", pch="
				+ pch + ", sn=" + sn + ", zwms=" + zwms + ", ywms=" + ywms
				+ ", jldw=" + jldw + ", kcsl=" + kcsl + ", djsl=" + djsl
				+ ", cghtid=" + cghtid + ", htbhCg=" + htbhCg + ", sxhtid="
				+ sxhtid + ", htbhSx=" + htbhSx + ", tddid=" + tddid
				+ ", tddh=" + tddh + ", rksj=" + rksj + ", rkbmid=" + rkbmid
				+ ", rkrid=" + rkrid + ", shzh=" + shzh + ", shzwz=" + shzwz
				+ ", zt=" + zt + ", bz=" + bz + ", jydid=" + jydid + ", hjsm="
				+ hjsm + ", spqx=" + spqx + ", scrq=" + scrq + ", xh=" + xh
				+ ", gg=" + gg + ", zzcs=" + zzcs + ", tsn=" + tsn + ", tso="
				+ tso + ", hcly=" + hcly + ", xkzh=" + xkzh + ", bjgzjl="
				+ bjgzjl + ", llklx=" + llklx + ", llkbh=" + llkbh + ", cfyq="
				+ cfyq + ", kzlx=" + kzlx + ", isDj=" + isDj + ", kccb=" + kccb
				+ ", cjjh=" + cjjh + ", hclx=" + hclx + ", gljb=" + gljb
				+ ", syts=" + syts + ", cangkuzt=" + cangkuzt + ", hCMainData="
				+ hCMainData + ", dprtcode=" + dprtcode 
				+ "]";
	}
	
	
	public QualityCheck toQualityCheck(){
		QualityCheck qualityCheck = new QualityCheck();
		
		qualityCheck.setDprtcode(dprtcode);
		qualityCheck.setHtid(StringUtils.isNotBlank(cghtid)?cghtid:sxhtid);
		qualityCheck.setHth(StringUtils.isNotBlank(htbhCg)?htbhCg:htbhSx);
		qualityCheck.setTddid(tddid);
		qualityCheck.setTddh(tddh);
		qualityCheck.setKcid(id);
		qualityCheck.setCklb(cklb);
		qualityCheck.setCkid(ckid);
		qualityCheck.setCkh(ckh);
		qualityCheck.setCkmc(ckmc);
		qualityCheck.setKwid(kwid);
		qualityCheck.setKwh(kwh);
		qualityCheck.setBjid(bjid);
		qualityCheck.setBjh(bjh);
		qualityCheck.setPch(pch);
		qualityCheck.setSn(sn);
		qualityCheck.setZwms(zwms);
		qualityCheck.setYwms(ywms);
		qualityCheck.setJldw(jldw);
		qualityCheck.setKcsl(kcsl);
		qualityCheck.setHcly(hcly);
		qualityCheck.setCfyq(cfyq);
		qualityCheck.setXkzh(xkzh);
		qualityCheck.setShzh(shzh);
		qualityCheck.setShzwz(shzwz);
		qualityCheck.setTsn(tsn);
		qualityCheck.setTso(tso);
		qualityCheck.setScrq(scrq);
		qualityCheck.setXh(xh);
		qualityCheck.setGg(gg);
		qualityCheck.setZzcs(zzcs);
		qualityCheck.setZt(1);
		return qualityCheck;
	}

	public String getBorrowStatus() {
		return borrowStatus;
	}

	public void setBorrowStatus(String borrowStatus) {
		this.borrowStatus = borrowStatus;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getBorrowStatusText() {
		return borrowStatusText;
	}

	public void setBorrowStatusText(String borrowStatusText) {
		this.borrowStatusText = borrowStatusText;
	}

	public String getBrrowId() {
		return brrowId;
	}

	public void setBrrowId(String brrowId) {
		this.brrowId = brrowId;
	}

	public String getSyzt() {
		return syzt;
	}

	public void setSyzt(String syzt) {
		this.syzt = syzt;
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

	public String getKcly() {
		return kcly;
	}

	public void setKcly(String kcly) {
		this.kcly = kcly;
	}

	public Propertyright getPropertyRight() {
		return propertyRight;
	}

	public void setPropertyRight(Propertyright propertyRight) {
		this.propertyRight = propertyRight;
	}

	public String getShdmxid() {
		return shdmxid;
	}

	public void setShdmxid(String shdmxid) {
		this.shdmxid = shdmxid;
	}

	public OutinReceipt getReceipt() {
		return receipt;
	}

	public void setReceipt(OutinReceipt receipt) {
		this.receipt = receipt;
	}

	public Inspection getInspection() {
		return inspection;
	}

	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
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

	public String getHclxEj() {
		return hclxEj;
	}

	public void setHclxEj(String hclxEj) {
		this.hclxEj = hclxEj;
	}

}