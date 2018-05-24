package com.eray.thjw.produce.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.baseinfo.po.Customer;
import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.PlaneBase;
import com.eray.thjw.po.User;

/**
 * 
 * @Description 飞机基本信息实体 对应D_007
 * @CreateTime 2017-9-19 上午9:55:30
 * @CreateBy 孙霁
 */
public class Aircraftinfo extends BizEntity{
   
	private String fjzch;
	
	private String dprtcode;
	
	private String fjjx;
	
	private String xlh;
	
	private String bzm;
	
	private Date scrq;

	private Date ccrq;
	
	private Date gmrq;
	
	private Integer isSsbdw;
	
	private String ssdwid;
	
	private String jd;
	
	private String rhyzph;
	
	private String yyyph;
	
	private String jsgzjl;
	
	private String bz;
	
	private String gjdjzh;

	private Date gjdjzjkrq;
	
	private String gjdjzfjid;
	
	private String shzh;
	
	private Date shzjkrq;
	
	private Date shzzjkrq;

	private String shzfjid;
	
	private String wxdtxkzh;
	
	private Date wxdtbfrq;
	
	private Date dtzzjkrq;
	
	private String wxdtzzfjid;
	
	private Integer fdjsl;
	
	private String tsn;
	
	private String tso;
	
	private Integer csn;
	
	private Integer cso;
	
	private Integer zt;
	
	private String whbmid;
	
	private String whrid;
	
	private Date whsj;

	private Department base;//基地
	
	private Department jg_dprt;//组织机构
	
    private User zdr;//制单人
	
	private Customer customer;//归属
	
	private List<Attachment> AttachmentList; //附件list
	
	private List<PlanInit> planInitList;//初始化飞行信息
	
	private List<PlanUsage> planUsageList;//日使用量
	
	private List<String> delAttachements;//删除附件
	
	private Date gjdjzyxq;
	
	
	public Date getGjdjzyxq() {
		return gjdjzyxq;
	}

	public void setGjdjzyxq(Date gjdjzyxq) {
		this.gjdjzyxq = gjdjzyxq;
	}

	public List<String> getDelAttachements() {
		return delAttachements;
	}

	public void setDelAttachements(List<String> delAttachements) {
		this.delAttachements = delAttachements;
	}

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public List<PlanUsage> getPlanUsageList() {
		return planUsageList;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public void setPlanUsageList(List<PlanUsage> planUsageList) {
		this.planUsageList = planUsageList;
	}

	public List<PlanInit> getPlanInitList() {
		return planInitList;
	}

	public void setPlanInitList(List<PlanInit> planInitList) {
		this.planInitList = planInitList;
	}

	public List<Attachment> getAttachmentList() {
		return AttachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		AttachmentList = attachmentList;
	}

	public Department getBase() {
		return base;
	}

	public void setBase(Department base) {
		this.base = base;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getFjjx() {
		return fjjx;
	}

	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}

	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}

	public String getBzm() {
		return bzm;
	}

	public void setBzm(String bzm) {
		this.bzm = bzm;
	}

	public Date getScrq() {
		return scrq;
	}

	public void setScrq(Date scrq) {
		this.scrq = scrq;
	}

	public Date getCcrq() {
		return ccrq;
	}

	public void setCcrq(Date ccrq) {
		this.ccrq = ccrq;
	}

	public Date getGmrq() {
		return gmrq;
	}

	public void setGmrq(Date gmrq) {
		this.gmrq = gmrq;
	}

	public Integer getIsSsbdw() {
		return isSsbdw;
	}

	public void setIsSsbdw(Integer isSsbdw) {
		this.isSsbdw = isSsbdw;
	}

	public String getSsdwid() {
		return ssdwid;
	}

	public void setSsdwid(String ssdwid) {
		this.ssdwid = ssdwid;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getRhyzph() {
		return rhyzph;
	}

	public void setRhyzph(String rhyzph) {
		this.rhyzph = rhyzph;
	}

	public String getYyyph() {
		return yyyph;
	}

	public void setYyyph(String yyyph) {
		this.yyyph = yyyph;
	}

	public String getJsgzjl() {
		return jsgzjl;
	}

	public void setJsgzjl(String jsgzjl) {
		this.jsgzjl = jsgzjl;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getGjdjzh() {
		return gjdjzh;
	}

	public void setGjdjzh(String gjdjzh) {
		this.gjdjzh = gjdjzh;
	}

	public Date getGjdjzjkrq() {
		return gjdjzjkrq;
	}

	public void setGjdjzjkrq(Date gjdjzjkrq) {
		this.gjdjzjkrq = gjdjzjkrq;
	}

	public String getGjdjzfjid() {
		return gjdjzfjid;
	}

	public void setGjdjzfjid(String gjdjzfjid) {
		this.gjdjzfjid = gjdjzfjid;
	}

	public String getShzh() {
		return shzh;
	}

	public void setShzh(String shzh) {
		this.shzh = shzh;
	}

	public Date getShzjkrq() {
		return shzjkrq;
	}

	public void setShzjkrq(Date shzjkrq) {
		this.shzjkrq = shzjkrq;
	}

	public Date getShzzjkrq() {
		return shzzjkrq;
	}

	public void setShzzjkrq(Date shzzjkrq) {
		this.shzzjkrq = shzzjkrq;
	}

	public String getShzfjid() {
		return shzfjid;
	}

	public void setShzfjid(String shzfjid) {
		this.shzfjid = shzfjid;
	}

	public String getWxdtxkzh() {
		return wxdtxkzh;
	}

	public void setWxdtxkzh(String wxdtxkzh) {
		this.wxdtxkzh = wxdtxkzh;
	}

	public Date getWxdtbfrq() {
		return wxdtbfrq;
	}

	public void setWxdtbfrq(Date wxdtbfrq) {
		this.wxdtbfrq = wxdtbfrq;
	}

	public Date getDtzzjkrq() {
		return dtzzjkrq;
	}

	public void setDtzzjkrq(Date dtzzjkrq) {
		this.dtzzjkrq = dtzzjkrq;
	}

	public String getWxdtzzfjid() {
		return wxdtzzfjid;
	}

	public void setWxdtzzfjid(String wxdtzzfjid) {
		this.wxdtzzfjid = wxdtzzfjid;
	}

	public Integer getFdjsl() {
		return fdjsl;
	}

	public void setFdjsl(Integer fdjsl) {
		this.fdjsl = fdjsl;
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

	public Integer getCsn() {
		return csn;
	}

	public void setCsn(Integer csn) {
		this.csn = csn;
	}

	public Integer getCso() {
		return cso;
	}

	public void setCso(Integer cso) {
		this.cso = cso;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getWhbmid() {
		return whbmid;
	}

	public void setWhbmid(String whbmid) {
		this.whbmid = whbmid;
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
}