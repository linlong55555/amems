package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * 
 * @Description b_h_026 退料登记
 * @CreateTime 2018年3月5日 上午9:45:03
 * @CreateBy 林龙
 */
public class BackRegister extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String whrbmid;

    private String whrid;

    private Date whsj;

    private Date tlrq;

    private String bjid;

    private String bjh;

    private String bjmc;

    private String pch;

    private String xlh;

    private BigDecimal tlsl;

    private String dw;

    private Integer sfky;

    private Integer bjly;

    private String fjzch;

    private String sm;

    private Integer zt;

    private String wckcid;

    private Integer wllb;

    private User whr; //维护人
    
    private Department dprt; //组织机构
    
    private List<OutinReceiptShelves> receiptShelveslist; //b_h2_0200101 收货单-上架List集合
    
    private List<ReceivingRelationship> receivingRelationshiplist; //b_h2_0200101 收货单-上架List集合
	

	public List<ReceivingRelationship> getReceivingRelationshiplist() {
		return receivingRelationshiplist;
	}

	public void setReceivingRelationshiplist(
			List<ReceivingRelationship> receivingRelationshiplist) {
		this.receivingRelationshiplist = receivingRelationshiplist;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<OutinReceiptShelves> getReceiptShelveslist() {
		return receiptShelveslist;
	}

	public void setReceiptShelveslist(List<OutinReceiptShelves> receiptShelveslist) {
		this.receiptShelveslist = receiptShelveslist;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Department getDprt() {
		return dprt;
	}

	public void setDprt(Department dprt) {
		this.dprt = dprt;
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

    public String getWhrbmid() {
        return whrbmid;
    }

    public void setWhrbmid(String whrbmid) {
        this.whrbmid = whrbmid == null ? null : whrbmid.trim();
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

    public Date getTlrq() {
        return tlrq;
    }

    public void setTlrq(Date tlrq) {
        this.tlrq = tlrq;
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

    public String getBjmc() {
        return bjmc;
    }

    public void setBjmc(String bjmc) {
        this.bjmc = bjmc == null ? null : bjmc.trim();
    }

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch == null ? null : pch.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public BigDecimal getTlsl() {
        return tlsl;
    }

    public void setTlsl(BigDecimal tlsl) {
        this.tlsl = tlsl;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw == null ? null : dw.trim();
    }

	public Integer getSfky() {
		return sfky;
	}

	public void setSfky(Integer sfky) {
		this.sfky = sfky;
	}

	public Integer getBjly() {
		return bjly;
	}

	public void setBjly(Integer bjly) {
		this.bjly = bjly;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getSm() {
		return sm;
	}

	public void setSm(String sm) {
		this.sm = sm;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getWckcid() {
		return wckcid;
	}

	public void setWckcid(String wckcid) {
		this.wckcid = wckcid;
	}

	public Integer getWllb() {
		return wllb;
	}

	public void setWllb(Integer wllb) {
		this.wllb = wllb;
	}

   
}