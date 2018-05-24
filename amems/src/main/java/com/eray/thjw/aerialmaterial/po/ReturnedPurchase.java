package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/*
 * b_h_022 退货单
 * 
 */
public class ReturnedPurchase extends BizEntity{
    private String id;

    private String dprtcode;

    private String fhdw;

    private String thdh;

    private String thbmid;

    private String thrid;

    private Date thrq;

    private Integer zt;

    private String zdrid;

    private Date zdsj;

    private String bz;
    
    private String ckdid;
    
    private Department dprt;//组织机构
    
    private User zdr;//制单人

    private User thr;//退货人
    
    private Outstock outstock;//出库单
    
    private List<ReturnedPurchaseDetail> returnedPurchaseDetailList;
    
    private String shdid;//虚拟字段:收货单Id 
    
    public List<ReturnedPurchaseDetail> getReturnedPurchaseDetailList() {
		return returnedPurchaseDetailList;
	}

	public void setReturnedPurchaseDetailList(
			List<ReturnedPurchaseDetail> returnedPurchaseDetailList) {
		this.returnedPurchaseDetailList = returnedPurchaseDetailList;
	}

	public Outstock getOutstock() {
		return outstock;
	}

	public void setOutstock(Outstock outstock) {
		this.outstock = outstock;
	}

	public Department getDprt() {
		return dprt;
	}

	public void setDprt(Department dprt) {
		this.dprt = dprt;
	}

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public User getThr() {
		return thr;
	}

	public void setThr(User thr) {
		this.thr = thr;
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

    public String getFhdw() {
        return fhdw;
    }

    public void setFhdw(String fhdw) {
        this.fhdw = fhdw == null ? null : fhdw.trim();
    }

    public String getThdh() {
        return thdh;
    }

    public void setThdh(String thdh) {
        this.thdh = thdh == null ? null : thdh.trim();
    }

    public String getThbmid() {
        return thbmid;
    }

    public void setThbmid(String thbmid) {
        this.thbmid = thbmid == null ? null : thbmid.trim();
    }

    public String getThrid() {
        return thrid;
    }

    public void setThrid(String thrid) {
        this.thrid = thrid == null ? null : thrid.trim();
    }

    public Date getThrq() {
        return thrq;
    }

    public void setThrq(Date thrq) {
        this.thrq = thrq;
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

    public String getCkdid() {
        return ckdid;
    }

    public void setCkdid(String ckdid) {
        this.ckdid = ckdid == null ? null : ckdid.trim();
    }

	public String getShdid() {
		return shdid;
	}

	public void setShdid(String shdid) {
		this.shdid = shdid;
	}
    
}