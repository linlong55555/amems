package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * 
 * b_h_0020101 提订单-询价表
 * @author xu.yong
 *
 */
public class Enquiry extends BizEntity{
    private String id;

    private String mainid;

    private String gysid;

    private String gysbm;

    private String gysmc;

    private BigDecimal bjClf;

    private BigDecimal bjGsf;

    private BigDecimal bjQtf;

    private Date yjdhrq;

    private String bz;

    private Integer zt;

    private String whdwid;

    private String whrid;
    
    private Date whsj;
    
    private String dprtcode;
    
    private Integer djlx;
    
    private Integer xjzt;
    
    private Integer hzzk;
    
    private Integer gysdj;
    
    private List<String> enquiryIdList;
    
    private List<Enquiry> enquiryList;
    
    /**
     * 询价人xjr
     */
    private User xjr;

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

    public String getGysid() {
        return gysid;
    }

    public void setGysid(String gysid) {
        this.gysid = gysid == null ? null : gysid.trim();
    }

    public String getGysbm() {
        return gysbm;
    }

    public void setGysbm(String gysbm) {
        this.gysbm = gysbm == null ? null : gysbm.trim();
    }

    public String getGysmc() {
        return gysmc;
    }

    public void setGysmc(String gysmc) {
        this.gysmc = gysmc == null ? null : gysmc.trim();
    }

    public BigDecimal getBjClf() {
        return bjClf;
    }

    public void setBjClf(BigDecimal bjClf) {
        this.bjClf = bjClf;
    }

    public BigDecimal getBjGsf() {
        return bjGsf;
    }

    public void setBjGsf(BigDecimal bjGsf) {
        this.bjGsf = bjGsf;
    }

    public BigDecimal getBjQtf() {
        return bjQtf;
    }

    public void setBjQtf(BigDecimal bjQtf) {
        this.bjQtf = bjQtf;
    }

    public Date getYjdhrq() {
        return yjdhrq;
    }

    public void setYjdhrq(Date yjdhrq) {
        this.yjdhrq = yjdhrq;
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

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public Integer getDjlx() {
		return djlx;
	}

	public void setDjlx(Integer djlx) {
		this.djlx = djlx;
	}

	public Integer getXjzt() {
		return xjzt;
	}

	public void setXjzt(Integer xjzt) {
		this.xjzt = xjzt;
	}

	public List<String> getEnquiryIdList() {
		return enquiryIdList;
	}

	public void setEnquiryIdList(List<String> enquiryIdList) {
		this.enquiryIdList = enquiryIdList;
	}

	public List<Enquiry> getEnquiryList() {
		return enquiryList;
	}

	public void setEnquiryList(List<Enquiry> enquiryList) {
		this.enquiryList = enquiryList;
	}

	public Integer getHzzk() {
		return hzzk;
	}

	public void setHzzk(Integer hzzk) {
		this.hzzk = hzzk;
	}

	public Integer getGysdj() {
		return gysdj;
	}

	public void setGysdj(Integer gysdj) {
		this.gysdj = gysdj;
	}

	public User getXjr() {
		return xjr;
	}

	public void setXjr(User xjr) {
		this.xjr = xjr;
	}
    
}