package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;

/**
 * b_h_00203 送修单-航材表
 * 
 * @author xu.yong
 * 
 */
public class MaterialRepair extends BizEntity {
	private String id;

	private String mainid;

	private String kcllid;

	private Integer zt;

	private String bz;

	private Integer xjzt;

	private String xjdh;

	private String whdwid;

	private String whrid;

	private Date whsj;

	/** 虚拟字段 **/

	private String sqdh;// 合同号
	
	private String htid;// 合同id

	private String hth;// 合同号
	
	private String htlsh;// 合同号

	private String sqrid;// 申请人

	private String sqbmid;// 申请部门

	private Date sqsj;// 申请日期

	private String bjh;// 部件号

	private String zwms;// 中文名称

	private String ywms;// 英文名称

	private String cjjh;// 厂家件号

	private String shzh;// 适航证号
	
	private String xh;// 序列号

	private String kwh;// 库位号

	private String username; // 用戶名
	private String fxDateBegin;// 开始时间
	private String fxDateEnd;// 结束时间
	private String ckh;// 仓库号

	private String ckmc;// 仓库号
	private String gdid;// 仓库号

	private String sqrname;// 申请人

	private String gysid;// 供应商/承修商id

	private String kcid;// 库存id

	private List<String> idList;

	private String glgd;// 关联工单

	private ReserveMain reserveMain;// 提订单/送修单

	private MaterialHistory materialHistory;// 部件库存履历

	private Date cksj;// 出库时间
	private String ckbmid;// 出库部门
	private String cukid;// 出库人

	private String sqr;// 申请人

	private String sqbm;// 申请部门

	private String xgdjid;// 相关单据id
	
	private String sxid;// 送修id
	
	private Integer noRead;
    
    private Integer readAll;

    private String grn;// 送修id
    
	/** 虚拟字段 **/
    
	public String getHth() {
		return hth;
	}

	public String getGrn() {
		return grn;
	}

	public void setGrn(String grn) {
		this.grn = grn;
	}

	public String getShzh() {
		return shzh;
	}

	public void setShzh(String shzh) {
		this.shzh = shzh;
	}

	public String getCkmc() {
		return ckmc;
	}

	public void setCkmc(String ckmc) {
		this.ckmc = ckmc;
	}

	public String getSxid() {
		return sxid;
	}

	public void setSxid(String sxid) {
		this.sxid = sxid;
	}

	public String getHtid() {
		return htid;
	}

	public void setHtid(String htid) {
		this.htid = htid;
	}

	public String getHtlsh() {
		return htlsh;
	}

	public void setHtlsh(String htlsh) {
		this.htlsh = htlsh;
	}

	public String getXgdjid() {
		return xgdjid;
	}

	public void setXgdjid(String xgdjid) {
		this.xgdjid = xgdjid;
	}

	public String getSqr() {
		return sqr;
	}

	public void setSqr(String sqr) {
		this.sqr = sqr;
	}

	public String getSqbm() {
		return sqbm;
	}

	public void setSqbm(String sqbm) {
		this.sqbm = sqbm;
	}

	public Date getCksj() {
		return cksj;
	}

	public void setCksj(Date cksj) {
		this.cksj = cksj;
	}

	public String getCkbmid() {
		return ckbmid;
	}

	public void setCkbmid(String ckbmid) {
		this.ckbmid = ckbmid;
	}

	public String getCukid() {
		return cukid;
	}

	public void setCukid(String cukid) {
		this.cukid = cukid;
	}

	public String getSqdh() {
		return sqdh;
	}

	public void setSqdh(String sqdh) {
		this.sqdh = sqdh;
	}

	public String getKcid() {
		return kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid;
	}

	public String getGysid() {
		return gysid;
	}

	public void setGysid(String gysid) {
		this.gysid = gysid;
	}

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

	public String getSqrname() {
		return sqrname;
	}

	public void setSqrname(String sqrname) {
		this.sqrname = sqrname;
	}

	public String getCkh() {
		return ckh;
	}

	public void setCkh(String ckh) {
		this.ckh = ckh;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public void setHth(String hth) {
		this.hth = hth;
	}

	public String getSqrid() {
		return sqrid;
	}

	public void setSqrid(String sqrid) {
		this.sqrid = sqrid;
	}

	public String getSqbmid() {
		return sqbmid;
	}

	public void setSqbmid(String sqbmid) {
		this.sqbmid = sqbmid;
	}

	public Date getSqsj() {
		return sqsj;
	}

	public void setSqsj(Date sqsj) {
		this.sqsj = sqsj;
	}

	public String getBjh() {
		return bjh;
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

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getKwh() {
		return kwh;
	}

	public void setKwh(String kwh) {
		this.kwh = kwh;
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

	public String getKcllid() {
		return kcllid;
	}

	public void setKcllid(String kcllid) {
		this.kcllid = kcllid == null ? null : kcllid.trim();
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz == null ? null : bz.trim();
	}

	public Integer getXjzt() {
		return xjzt;
	}

	public void setXjzt(Integer xjzt) {
		this.xjzt = xjzt;
	}

	public String getXjdh() {
		return xjdh;
	}

	public void setXjdh(String xjdh) {
		this.xjdh = xjdh == null ? null : xjdh.trim();
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

	public String getGlgd() {
		return glgd;
	}

	public void setGlgd(String glgd) {
		this.glgd = glgd;
	}

	public ReserveMain getReserveMain() {
		return reserveMain;
	}

	public void setReserveMain(ReserveMain reserveMain) {
		this.reserveMain = reserveMain;
	}

	public MaterialHistory getMaterialHistory() {
		return materialHistory;
	}

	public void setMaterialHistory(MaterialHistory materialHistory) {
		this.materialHistory = materialHistory;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public Integer getNoRead() {
		return noRead;
	}

	public void setNoRead(Integer noRead) {
		this.noRead = noRead;
	}

	public Integer getReadAll() {
		return readAll;
	}

	public void setReadAll(Integer readAll) {
		this.readAll = readAll;
	}

}