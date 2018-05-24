package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.aerialmaterial.po.ComponentCertificate;

/**
 * @Description 航材上架-虚拟库存
 * @CreateTime 2018年3月23日 上午10:50:43
 * @CreateBy 韩武
 */
public class StockInShelf{
	
	private String id;

    private String kcid;

    private BigDecimal kccb;

    private String biz;
    
    private String dw;
    
    private BigDecimal jz;
    
    private String jzbz;

    private String hcly;

    private String grn;

    private Date scrq;

    private Date hjsm;

    private String tsn;

    private String tso;

    private String csn;

    private String cso;

    private String cfyq;
    
    private String dprtcode;
    
    private String bjh;
    
    private String xlh;
    
    private String pch;
    
    private String qczt;

    /** 部件证书 */
    private List<ComponentCertificate> certificates;
    
    /** 收货单-上架信息 */
    private List<OutinReceiptShelves> shelves;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKcid() {
		return kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid;
	}

	public BigDecimal getKccb() {
		return kccb;
	}

	public void setKccb(BigDecimal kccb) {
		this.kccb = kccb;
	}

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}

	public String getHcly() {
		return hcly;
	}

	public void setHcly(String hcly) {
		this.hcly = hcly;
	}

	public String getGrn() {
		return grn;
	}

	public void setGrn(String grn) {
		this.grn = grn;
	}

	public Date getScrq() {
		return scrq;
	}

	public void setScrq(Date scrq) {
		this.scrq = scrq;
	}

	public Date getHjsm() {
		return hjsm;
	}

	public void setHjsm(Date hjsm) {
		this.hjsm = hjsm;
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

	public String getCfyq() {
		return cfyq;
	}

	public void setCfyq(String cfyq) {
		this.cfyq = cfyq;
	}

	public List<ComponentCertificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<ComponentCertificate> certificates) {
		this.certificates = certificates;
	}

	public List<OutinReceiptShelves> getShelves() {
		return shelves;
	}

	public void setShelves(List<OutinReceiptShelves> shelves) {
		this.shelves = shelves;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getDw() {
		return dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
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

	public String getPch() {
		return pch;
	}

	public void setPch(String pch) {
		this.pch = pch;
	}

	public String getQczt() {
		return qczt;
	}

	public void setQczt(String qczt) {
		this.qczt = qczt;
	}
}