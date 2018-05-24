package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * b_h_00401 合同表-提订单关系
 * 
 * @author xu.yong
 * 
 */
public class ContractDetail extends BizEntity {
	private String id;

	private String mainid;

	private String tddid;

	private String tddhcid;

	private BigDecimal htClf;

	private BigDecimal htGsf;

	private BigDecimal htQtf;

	private BigDecimal htSl;

	private Date htDhrq;

	private BigDecimal dhsl;

	private BigDecimal rksl;

	private String bz;

	private Integer zt;

	private String whdwid;

	private String whrid;

    private Date whsj;
    
    /** 虚拟字段 start */
    private String bjid; //虚拟字段：部件id
    
    private BigDecimal bcdhs; //虚拟字段：本次到货数
    
    private String bjh; //虚拟字段：部件号
    
    private String zwms;//虚拟字段：中文名称
    
    private String ywms;//虚拟字段：英文名称
    
    private String cjjh;//虚拟字段：厂家件号
    
    private Integer gljb;//虚拟字段：管理级别
    
    private Integer hclx;//虚拟字段：航材类型
    
    private String jldw;//虚拟字段：单位
    
    private String sn;//虚拟字段：序列号
    
    private String pch;//虚拟字段：批次号
    
    private ReserveMain reserveMain; //提订/送修单
    
    private Contract contract;
    
    private String gysid; // 承销商id
    
    private BigDecimal ztsl; // 在途数量
    
    private String hth; // 合同号
    
    private String gysmc; // 供应商描述
    
    private Integer lx; // 类型
    /** 虚拟字段 end */

	public String getBjh() {
		return bjh;
	}

	public String getHth() {
		return hth;
	}

	public void setHth(String hth) {
		this.hth = hth;
	}

	public String getGysmc() {
		return gysmc;
	}

	public void setGysmc(String gysmc) {
		this.gysmc = gysmc;
	}

	public Integer getLx() {
		return lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}

	public BigDecimal getZtsl() {
		if(ztsl !=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(ztsl));
    	}
        return ztsl;
	}

	public void setZtsl(BigDecimal ztsl) {
		this.ztsl = ztsl;
	}

	public String getPch() {
		return pch;
	}

	public void setPch(String pch) {
		this.pch = pch;
	}

	public String getGysid() {
		return gysid;
	}

	public void setGysid(String gysid) {
		this.gysid = gysid;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh;
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

	public String getCjjh() {
		return cjjh;
	}

	public void setCjjh(String cjjh) {
		this.cjjh = cjjh;
	}

	public Integer getGljb() {
		return gljb;
	}

	public void setGljb(Integer gljb) {
		this.gljb = gljb;
	}

	public Integer getHclx() {
		return hclx;
	}

	public void setHclx(Integer hclx) {
		this.hclx = hclx;
	}

	public String getJldw() {
		return jldw;
	}

	public void setJldw(String jldw) {
		this.jldw = jldw;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
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

	public String getTddid() {
		return tddid;
	}

	public void setTddid(String tddid) {
		this.tddid = tddid == null ? null : tddid.trim();
	}

	public String getTddhcid() {
		return tddhcid;
	}

	public void setTddhcid(String tddhcid) {
		this.tddhcid = tddhcid == null ? null : tddhcid.trim();
	}

	public BigDecimal getHtClf() {
		return htClf;
	}

	public void setHtClf(BigDecimal htClf) {
		this.htClf = htClf;
	}

	public BigDecimal getHtGsf() {
		return htGsf;
	}

	public void setHtGsf(BigDecimal htGsf) {
		this.htGsf = htGsf;
	}

	public BigDecimal getHtQtf() {
		return htQtf;
	}

	public void setHtQtf(BigDecimal htQtf) {
		this.htQtf = htQtf;
	}

	public BigDecimal getHtSl() {
		return htSl;
	}

	public void setHtSl(BigDecimal htSl) {
		this.htSl = htSl;
	}

	public Date getHtDhrq() {
		return htDhrq;
	}

	public void setHtDhrq(Date htDhrq) {
		this.htDhrq = htDhrq;
	}

	public BigDecimal getDhsl() {
		return dhsl;
	}

	public void setDhsl(BigDecimal dhsl) {
		this.dhsl = dhsl;
	}

	public BigDecimal getRksl() {
		return rksl;
	}

	public void setRksl(BigDecimal rksl) {
		this.rksl = rksl;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz == null ? null : bz.trim();
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

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}

	public ReserveMain getReserveMain() {
		return reserveMain;
	}

	public void setReserveMain(ReserveMain reserveMain) {
		this.reserveMain = reserveMain;
	}

	public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
	}

	public BigDecimal getBcdhs() {
		return bcdhs;
	}

	public void setBcdhs(BigDecimal bcdhs) {
		this.bcdhs = bcdhs;
	}
	
}