package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.security.cert.Certificate;
import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * B_H_006_NEW 检验单
 * @author xu.yong
 *
 */
public class Inspection extends BizEntity{
    private String id;

    private String dprtcode;

    private String jydh;

    private String shdid;

    private String shdmxid;

    private BigDecimal kysl;

    private String hcly;

    private String cfyq;

    private String xkzh;

    private String shzh;

    private String shzwz;

    private String tsn;

    private String tso;
    
    private String csn;
    
    private String cso;
    
    private String grn;

    private String bz;

    private Date scrq;

    private String zzcs;

    private Date hjsm;

    private String jyrid;

    private Date jyrq;

    private Integer jyjg;

    private String jgsm;

    private Integer zt;

    private String zdbmid;

    private String zdrid;
    private String bjh;
    private String sn;

    private Date zdsj;
    
    private HCMainData hcMainData;
    
    private ReceiptSheet receiptSheet;
    
    private ReceiptSheetDetail receiptSheetDetail;
    
    private User jyr;
    
    private User whr;
    
    private User zdr;
    
    private Store store;
    
    private Storage storage;
    
    private MaterialHistory materialHistory;
    
    private GodownEntry godownEntry;

    private List<ComponentCertificate> certificates;	//证书列表
    private List<Attachment> attachmentList; //d_011 附件表List

	public String getBjh() {
		return bjh;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
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

	public List<ComponentCertificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<ComponentCertificate> certificates) {
		this.certificates = certificates;
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

    public String getJydh() {
        return jydh;
    }

    public void setJydh(String jydh) {
        this.jydh = jydh == null ? null : jydh.trim();
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

    public BigDecimal getKysl() {
        return kysl;
    }

    public void setKysl(BigDecimal kysl) {
        this.kysl = kysl;
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

    public String getXkzh() {
        return xkzh;
    }

    public void setXkzh(String xkzh) {
        this.xkzh = xkzh == null ? null : xkzh.trim();
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

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Date getScrq() {
        return scrq;
    }

    public void setScrq(Date scrq) {
        this.scrq = scrq;
    }

    public String getZzcs() {
        return zzcs;
    }

    public void setZzcs(String zzcs) {
        this.zzcs = zzcs == null ? null : zzcs.trim();
    }

    public Date getHjsm() {
        return hjsm;
    }

    public void setHjsm(Date hjsm) {
        this.hjsm = hjsm;
    }

    public String getJyrid() {
        return jyrid;
    }

    public void setJyrid(String jyrid) {
        this.jyrid = jyrid == null ? null : jyrid.trim();
    }

    public Date getJyrq() {
        return jyrq;
    }

    public void setJyrq(Date jyrq) {
        this.jyrq = jyrq;
    }

    public Integer getJyjg() {
        return jyjg;
    }

    public void setJyjg(Integer jyjg) {
        this.jyjg = jyjg;
    }

    public String getJgsm() {
        return jgsm;
    }

    public void setJgsm(String jgsm) {
        this.jgsm = jgsm == null ? null : jgsm.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
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

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}

	public ReceiptSheet getReceiptSheet() {
		return receiptSheet;
	}

	public void setReceiptSheet(ReceiptSheet receiptSheet) {
		this.receiptSheet = receiptSheet;
	}

	public ReceiptSheetDetail getReceiptSheetDetail() {
		return receiptSheetDetail;
	}

	public void setReceiptSheetDetail(ReceiptSheetDetail receiptSheetDetail) {
		this.receiptSheetDetail = receiptSheetDetail;
	}

	public User getJyr() {
		return jyr;
	}

	public void setJyr(User jyr) {
		this.jyr = jyr;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public MaterialHistory getMaterialHistory() {
		return materialHistory;
	}

	public void setMaterialHistory(MaterialHistory materialHistory) {
		this.materialHistory = materialHistory;
	}

	public GodownEntry getGodownEntry() {
		return godownEntry;
	}

	public void setGodownEntry(GodownEntry godownEntry) {
		this.godownEntry = godownEntry;
	}

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}
   
}