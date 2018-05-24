package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * b_h_020 收货单
 * @author xu.yong
 *
 */
public class ReceiptSheet extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = -7388365439510480693L;

	private String id;

    private String dprtcode;

    private Integer shlx;

    private String shdh;

    private String shbmid;

    private String shrid;

    private Date shrq;

    private String jddxid;

    private String jdfzr;

    private Integer zt;

    private String zdrid;

    private Date zdsj;

    private String bz;

    private String xgdjid;
    
    private String fhdw;
    
    /**
     * 收货人（对象）
     */
    private User shr;
    
    /**
     * 开始收货日期（查询条件）
     */
    private Date shrqBegin;
    
    /**
     * 结束收货日期（查询条件）
     */
    private Date shrqEnd;
    
    /**
     * 制单人
     */
    private User zdr;
    
    /**
     * 相关单据编号
     */
    private String xgdjbh;
    
    /**
     * 质检单
     */
    private List<Inspection> inspections;
    
    /**
     * 收货单详细
     */
    private List<ReceiptSheetDetail> details;
    
    /**
     * 借调对象
     */
    private Secondment secondment;
    
    /**
     * 退货单
     */
    private List<ReturnedPurchase> returnedPurchaseList;
    
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

    public Integer getShlx() {
        return shlx;
    }

    public void setShlx(Integer shlx) {
        this.shlx = shlx;
    }

    public String getShdh() {
        return shdh;
    }

    public void setShdh(String shdh) {
        this.shdh = shdh == null ? null : shdh.trim();
    }

    public String getShbmid() {
        return shbmid;
    }

    public void setShbmid(String shbmid) {
        this.shbmid = shbmid == null ? null : shbmid.trim();
    }

    public String getShrid() {
        return shrid;
    }

    public void setShrid(String shrid) {
        this.shrid = shrid == null ? null : shrid.trim();
    }

    public Date getShrq() {
        return shrq;
    }

    public void setShrq(Date shrq) {
        this.shrq = shrq;
    }

    public String getJddxid() {
        return jddxid;
    }

    public void setJddxid(String jddxid) {
        this.jddxid = jddxid == null ? null : jddxid.trim();
    }

    public String getJdfzr() {
        return jdfzr;
    }

    public void setJdfzr(String jdfzr) {
        this.jdfzr = jdfzr == null ? null : jdfzr.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
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

	public User getShr() {
		return shr;
	}

	public void setShr(User shr) {
		this.shr = shr;
	}

	public Date getShrqBegin() {
		return shrqBegin;
	}

	public void setShrqBegin(Date shrqBegin) {
		this.shrqBegin = shrqBegin;
	}

	public Date getShrqEnd() {
		return shrqEnd;
	}

	public void setShrqEnd(Date shrqEnd) {
		this.shrqEnd = shrqEnd;
	}

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public String getXgdjbh() {
		return xgdjbh;
	}

	public void setXgdjbh(String xgdjbh) {
		this.xgdjbh = xgdjbh;
	}

	public List<Inspection> getInspections() {
		return inspections;
	}

	public void setInspections(List<Inspection> inspections) {
		this.inspections = inspections;
	}

	public List<ReceiptSheetDetail> getDetails() {
		return details;
	}

	public void setDetails(List<ReceiptSheetDetail> details) {
		this.details = details;
	}

	public String getFhdw() {
		return fhdw;
	}

	public void setFhdw(String fhdw) {
		this.fhdw = fhdw;
	}

	public Secondment getSecondment() {
		return secondment;
	}

	public void setSecondment(Secondment secondment) {
		this.secondment = secondment;
	}

	public List<ReturnedPurchase> getReturnedPurchaseList() {
		return returnedPurchaseList;
	}

	public void setReturnedPurchaseList(List<ReturnedPurchase> returnedPurchaseList) {
		this.returnedPurchaseList = returnedPurchaseList;
	}
		
}