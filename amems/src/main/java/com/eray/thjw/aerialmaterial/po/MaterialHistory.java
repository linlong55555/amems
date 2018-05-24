package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.material2.po.StockHistory;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * b_h_017 部件库存履历
 * @author xu.yong
 *
 */
public class MaterialHistory extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String kcid;

    private String dprtcode;

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

    private String cfyq;
    
    private String bjgzjl;
    
    private Integer llklx;
    
    private String xkzh;
    
    private String llkbh;
    
    private Integer kzlx;
    
    private Integer isDj;
    
    private BigDecimal kccb;
    
    private HCMainData hcMainData;
    
    private String gljbName;
    
    private Department jg_dprt;
    
    private String kcllid;
    
    private Integer wcsjly;
    
    //入库人
    private User rkr_user;
    
    private String whrid;
    
    private Date whsj;
    
    private String biz;  //币种
    
    private String cqid;  //产权id
    
    private String shdmxid;  //收货单明细
    
    private String uuiddm;   //uuid短码
    
  //价值
    private BigDecimal jz; 
    
    //价值币种
    private String jzbz; 
    
    private String kfkcid;	//库房库存id
    
    private String fjzch;	//飞机注册号
    
    private String qczt;	//器材状态
    
    private StockHistory stockHistory; //库存履历主信息
    
    private List<ComponentCertificate> certificates;	//证书列表
    
    public List<ComponentCertificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<ComponentCertificate> certificates) {
		this.certificates = certificates;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public StockHistory getStockHistory() {
		return stockHistory;
	}

	public void setStockHistory(StockHistory stockHistory) {
		this.stockHistory = stockHistory;
	}

	public MaterialHistory(OutFieldStock outFieldStock){
    	this.kcid = outFieldStock.getId();
    	this.kfkcid = outFieldStock.getKcid();
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
    	this.kcsl = outFieldStock.getKcsl();
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
    	this.grn = outFieldStock.getGrn();
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
    	this.biz = outFieldStock.getBiz();
    	this.jz = outFieldStock.getJz();
    	this.jzbz = outFieldStock.getJzbz();
    	this.cqid = outFieldStock.getCqid();
    	this.shdmxid = outFieldStock.getShdmxid();
    	this.uuiddm = outFieldStock.getUuiddm();
    	this.wcsjly = outFieldStock.getSjly();
    	this.fjzch = outFieldStock.getFjzch();
    	this.qczt = outFieldStock.getQczt();
    }
    
    public MaterialHistory(){}

    public MaterialHistory(Stock stock){
    	this.kcid = stock.getId();
    	this.dprtcode = stock.getDprtcode();
    	this.cklb = stock.getCklb();
    	this.ckid = stock.getCkid();
    	this.ckh = stock.getCkh();
    	this.ckmc = stock.getCkmc();
    	this.kwid = stock.getKwid();
    	this.kwh = stock.getKwh();
    	this.bjid = stock.getBjid();
    	this.bjh = stock.getBjh();
    	this.pch = stock.getPch();
    	this.sn = stock.getSn();
    	this.zwms = stock.getZwms();
    	this.ywms = stock.getYwms();
    	this.jldw = stock.getJldw();
    	this.kcsl = stock.getKcsl();
    	this.djsl = stock.getDjsl();
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
    	this.cqid = stock.getCqid();
    	this.shdmxid = stock.getShdmxid();
    	this.uuiddm = stock.getUuiddm();
    	this.jz = stock.getJz();
    	this.jzbz = stock.getJzbz();
    	this.grn = stock.getGrn();
    	this.qczt = stock.getQczt();
    }
    
    public String getGrn() {
		return grn;
	}

	public void setGrn(String grn) {
		this.grn = grn;
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

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public User getRkr_user() {
		return rkr_user;
	}

	public void setRkr_user(User rkr_user) {
		this.rkr_user = rkr_user;
	}
    
	public String getKcllid() {
		return kcllid;
	}

	public void setKcllid(String kcllid) {
		this.kcllid = kcllid;
	}

	public String getBjgzjl() {

		return bjgzjl;
	}

	public void setBjgzjl(String bjgzjl) {
		this.bjgzjl = bjgzjl;
	}

	
	public String getXkzh() {
		return xkzh;
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

	public void setXkzh(String xkzh) {
		this.xkzh = xkzh;
	}

	public Integer getLlklx() {
		return llklx;
	}

	public void setLlklx(Integer llklx) {
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
//    	if(kcsl!=null){
//    		NumberFormat nf = NumberFormat.getInstance();
//    		return new BigDecimal(nf.format(kcsl));
//    	}
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

	public String getCfyq() {
        return cfyq;
    }

    public void setCfyq(String cfyq) {
        this.cfyq = cfyq == null ? null : cfyq.trim();
    }

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}
    
	public Stock toStock(){
		Stock stock = new Stock();
		stock.setDprtcode(dprtcode);
		stock.setId(kcid);
		stock.setCklb(cklb);
		stock.setCkid(ckid);
		stock.setCkh(ckh);;
		stock.setCkmc(ckmc);
		stock.setKwid(kwid);
	    stock.setKwh(kwh);
	    stock.setBjid(bjid);
	    stock.setBjh(bjh);
	    stock.setPch(pch);
	    stock.setSn(sn);
	    stock.setZwms(zwms);
	    stock.setYwms(ywms);
	    stock.setJldw(jldw);
	    stock.setKcsl(kcsl);
	    stock.setDjsl(djsl);
	    stock.setCghtid(cghtid);
	    stock.setHtbhCg(htbhCg);
	    stock.setSxhtid(sxhtid);
	    stock.setHtbhSx(htbhSx);
	    stock.setTddid(tddid);
	    stock.setTddh(tddh);
	    stock.setRksj(rksj);
	    stock.setRkbmid(rkbmid);
	    stock.setRkrid(rkrid);
	    stock.setShzh(shzh);
	    stock.setShzwz(shzwz);
	    stock.setZt(zt);
	    stock.setBz(bz);
	    stock.setJydid(jydid);
	    stock.setHjsm(hjsm);
	    stock.setSpqx(spqx);
	    stock.setScrq(scrq);
	    stock.setXh(xh);
	    stock.setGg(gg);
	    stock.setZzcs(zzcs);
	    stock.setTsn(tsn);
	    stock.setTso(tso);
	    stock.setHcly(hcly);
	    stock.setXkzh(xkzh);
	    stock.setBjgzjl(bjgzjl);
	    stock.setLlklx(llklx);
	    stock.setLlkbh(llkbh);
	    stock.setCfyq(cfyq);
	    stock.setKzlx(kzlx);
	    stock.setIsDj(isDj);
	    stock.setKccb(kccb);
	    return stock;
	}
	 public OutFieldStock toOutFieldStock(){
		    OutFieldStock outfieldstock=new OutFieldStock();
		    outfieldstock.setKcid(kcid);
		    outfieldstock.setDprtcode(dprtcode);
		    outfieldstock.setCklb(cklb);
		    outfieldstock.setCkid(ckid);
		    outfieldstock.setCkh(ckh);;
		    outfieldstock.setCkmc(ckmc);
		    outfieldstock.setKwid(kwid);
		    outfieldstock.setKwh(kwh);
		    outfieldstock.setBjid(bjid);
		    outfieldstock.setBjh(bjh);
		    outfieldstock.setPch(pch);
		    outfieldstock.setSn(sn);
		    outfieldstock.setZwms(zwms);
		    outfieldstock.setYwms(ywms);
		    outfieldstock.setJldw(jldw);
		    outfieldstock.setKcsl(kcsl);
		    outfieldstock.setCghtid(cghtid);
		    outfieldstock.setHtbhCg(htbhCg);
		    outfieldstock.setSxhtid(sxhtid);
		    outfieldstock.setHtbhSx(htbhSx);
		    outfieldstock.setTddid(tddid);
		    outfieldstock.setTddh(tddh);
		    outfieldstock.setRksj(rksj);
		    outfieldstock.setRkbmid(rkbmid);
		    outfieldstock.setRkrid(rkrid);
		    outfieldstock.setXkzh(xkzh);;
		    outfieldstock.setShzh(shzh);
		    outfieldstock.setShzwz(shzwz);
		    outfieldstock.setZt(zt);
		    outfieldstock.setBz(bz);
		    outfieldstock.setJydid(jydid);
		    outfieldstock.setHjsm(hjsm);
		    outfieldstock.setSpqx(spqx);
		    outfieldstock.setScrq(scrq);
		    outfieldstock.setXh(xh);
		    outfieldstock.setGg(gg);
		    outfieldstock.setZzcs(zzcs);
		    outfieldstock.setTsn(tsn);
		    outfieldstock.setTso(tso);
		    outfieldstock.setHcly(hcly);
		    outfieldstock.setCfyq(cfyq);
		    outfieldstock.setBjgzjl(bjgzjl);
		    outfieldstock.setLlklx(llklx);
		    outfieldstock.setLlkbh(llkbh);
		    outfieldstock.setKzlx(kzlx);
		    outfieldstock.setIsDj(isDj);
		    outfieldstock.setKccb(kccb);
	    	return outfieldstock;
	    }

	@Override
	public String toString() {
		return "MaterialHistory [id=" + id + ", kcid=" + kcid + ", dprtcode="
				+ dprtcode + ", cklb=" + cklb + ", ckid=" + ckid + ", ckh="
				+ ckh + ", ckmc=" + ckmc + ", kwid=" + kwid + ", kwh=" + kwh
				+ ", bjid=" + bjid + ", bjh=" + bjh + ", pch=" + pch + ", sn="
				+ sn + ", zwms=" + zwms + ", ywms=" + ywms + ", jldw=" + jldw
				+ ", kcsl=" + kcsl + ", djsl=" + djsl + ", cghtid=" + cghtid
				+ ", htbhCg=" + htbhCg + ", sxhtid=" + sxhtid + ", htbhSx="
				+ htbhSx + ", tddid=" + tddid + ", tddh=" + tddh + ", rksj="
				+ rksj + ", rkbmid=" + rkbmid + ", rkrid=" + rkrid + ", shzh="
				+ shzh + ", shzwz=" + shzwz + ", zt=" + zt + ", bz=" + bz
				+ ", jydid=" + jydid + ", hjsm=" + hjsm + ", spqx=" + spqx
				+ ", scrq=" + scrq + ", xh=" + xh + ", gg=" + gg + ", zzcs="
				+ zzcs + ", tsn=" + tsn + ", tso=" + tso + ", hcly=" + hcly
				+ ", cfyq=" + cfyq + ", bjgzjl=" + bjgzjl + ", llklx=" + llklx
				+ ", xkzh=" + xkzh + ", llkbh=" + llkbh + ", kzlx=" + kzlx
				+ ", isDj=" + isDj + ", kccb=" + kccb + ", hcMainData="
				+ hcMainData + "]";
	}

	public String getGljbName() {
		return gljbName;
	}

	public void setGljbName(String gljbName) {
		this.gljbName = gljbName;
	}

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
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

	public String getUuiddm() {
		return uuiddm;
	}

	public void setUuiddm(String uuiddm) {
		this.uuiddm = uuiddm;
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

	public String getKfkcid() {
		return kfkcid;
	}

	public void setKfkcid(String kfkcid) {
		this.kfkcid = kfkcid;
	}

	public Integer getWcsjly() {
		return wcsjly;
	}

	public void setWcsjly(Integer wcsjly) {
		this.wcsjly = wcsjly;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getQczt() {
		return qczt;
	}

	public void setQczt(String qczt) {
		this.qczt = qczt;
	}
}