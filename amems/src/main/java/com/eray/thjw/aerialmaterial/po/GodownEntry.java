package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;

/**
 * b_h_008_new 入库单
 * 
 * @author xu.yong
 * 
 */
public class GodownEntry extends BizEntity {
	private String id;

	private String dprtcode;

	private Integer rklx;

	private String rkdh;

	private String rkbmid;

	private String rukid;

	private Date rkrq;

	private Integer zt;

	private String bz;

	private String xgdjid;

	private String jydid;

	private String shdid;

	private String shdmxid;

	private String zdbmid;

	private String zdrid;

	private Date zdsj;
	
	private String fhdw;

	private String grn;
	
	private BigDecimal rksl; //入库数量
	
	private HCMainData hcmainData; // 航材表

	private ReceiptSheetDetail receiptSheetDetail; // 收货单明细

	private ReceiptSheet receiptSheet; // 收货单
	
	private Inspection inspection;// 检验单

	private List<GodownEntryDetail> godownEntryDetailList;//入库附表

	
	public String getGrn() {
		return grn;
	}


	public void setGrn(String grn) {
		this.grn = grn;
	}


	public BigDecimal getRksl() {
		return rksl;
	}


	public void setRksl(BigDecimal rksl) {
		this.rksl = rksl;
	}


	public List<GodownEntryDetail> getGodownEntryDetailList() {
		return godownEntryDetailList;
	}
	

	public ReceiptSheet getReceiptSheet() {
		return receiptSheet;
	}

	public void setReceiptSheet(ReceiptSheet receiptSheet) {
		this.receiptSheet = receiptSheet;
	}



	public void setGodownEntryDetailList(
			List<GodownEntryDetail> godownEntryDetailList) {
		this.godownEntryDetailList = godownEntryDetailList;
	}

	public HCMainData getHcmainData() {
		return hcmainData;
	}

	public void setHcmainData(HCMainData hcmainData) {
		this.hcmainData = hcmainData;
	}

	public ReceiptSheetDetail getReceiptSheetDetail() {
		return receiptSheetDetail;
	}

	public void setReceiptSheetDetail(ReceiptSheetDetail receiptSheetDetail) {
		this.receiptSheetDetail = receiptSheetDetail;
	}

	public Inspection getInspection() {
		return inspection;
	}

	public void setInspection(Inspection inspection) {
		this.inspection = inspection;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode == null ? null : dprtcode.trim();
	}

	public Integer getRklx() {
		return rklx;
	}

	public void setRklx(Integer rklx) {
		this.rklx = rklx;
	}

	public String getRkdh() {
		return rkdh;
	}

	public void setRkdh(String rkdh) {
		this.rkdh = rkdh == null ? null : rkdh.trim();
	}

	public String getRkbmid() {
		return rkbmid;
	}

	public void setRkbmid(String rkbmid) {
		this.rkbmid = rkbmid == null ? null : rkbmid.trim();
	}

	public String getRukid() {
		return rukid;
	}

	public void setRukid(String rukid) {
		this.rukid = rukid == null ? null : rukid.trim();
	}

	public Date getRkrq() {
		return rkrq;
	}

	public void setRkrq(Date rkrq) {
		this.rkrq = rkrq;
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

	public String getXgdjid() {
		return xgdjid;
	}

	public void setXgdjid(String xgdjid) {
		this.xgdjid = xgdjid == null ? null : xgdjid.trim();
	}

	public String getJydid() {
		return jydid;
	}

	public void setJydid(String jydid) {
		this.jydid = jydid == null ? null : jydid.trim();
	}

	public String getShdid() {
		return shdid;
	}

	public void setShdid(String shdid) {
		this.shdid = shdid == null ? null : shdid.trim();
	}

	public String getShdmxid() {
		return shdmxid;
	}

	public void setShdmxid(String shdmxid) {
		this.shdmxid = shdmxid == null ? null : shdmxid.trim();
	}

	public String getZdbmid() {
		return zdbmid;
	}

	public void setZdbmid(String zdbmid) {
		this.zdbmid = zdbmid == null ? null : zdbmid.trim();
	}

	public String getZdrid() {
		return zdrid;
	}

	public void setZdrid(String zdrid) {
		this.zdrid = zdrid == null ? null : zdrid.trim();
	}

	public Date getZdsj() {
		return zdsj;
	}

	public void setZdsj(Date zdsj) {
		this.zdsj = zdsj;
	}


	public String getFhdw() {
		return fhdw;
	}


	public void setFhdw(String fhdw) {
		this.fhdw = fhdw;
	}
}