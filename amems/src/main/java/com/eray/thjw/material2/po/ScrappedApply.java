package com.eray.thjw.material2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;
/**
 * 
 * @Description bb_h2_009 报废单
 * @CreateTime 2018年3月22日 上午10:57:24
 * @CreateBy 岳彬彬
 */
public class ScrappedApply extends BizEntity{
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String bfdh;

    private Date bfrq;

    private String sm;

    private String sqbmid;

    private String sqrid;

    private Date sqsj;

    private String spbmid;

    private String sprid;

    private Date spsj;

    private String spyj;

    private String zdjsrid;

    private Date zdjsrq;

    private String zdjsyy;
    /* 报废单明细 **/
    private List<ScrappedInfo> infoList;
    /* 申请人**/
    private User sqr;   
    /* 审核人**/
    private User shr;

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

    public String getWhbmid() {
        return whbmid;
    }

    public void setWhbmid(String whbmid) {
        this.whbmid = whbmid == null ? null : whbmid.trim();
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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getBfdh() {
        return bfdh;
    }

    public void setBfdh(String bfdh) {
        this.bfdh = bfdh == null ? null : bfdh.trim();
    }

    public Date getBfrq() {
        return bfrq;
    }

    public void setBfrq(Date bfrq) {
        this.bfrq = bfrq;
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm == null ? null : sm.trim();
    }

    public String getSqbmid() {
        return sqbmid;
    }

    public void setSqbmid(String sqbmid) {
        this.sqbmid = sqbmid == null ? null : sqbmid.trim();
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid == null ? null : sqrid.trim();
    }

    public Date getSqsj() {
        return sqsj;
    }

    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
    }

    public String getSpbmid() {
        return spbmid;
    }

    public void setSpbmid(String spbmid) {
        this.spbmid = spbmid == null ? null : spbmid.trim();
    }

    public String getSprid() {
        return sprid;
    }

    public void setSprid(String sprid) {
        this.sprid = sprid == null ? null : sprid.trim();
    }

    public Date getSpsj() {
        return spsj;
    }

    public void setSpsj(Date spsj) {
        this.spsj = spsj;
    }

    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj == null ? null : spyj.trim();
    }

    public String getZdjsrid() {
        return zdjsrid;
    }

    public void setZdjsrid(String zdjsrid) {
        this.zdjsrid = zdjsrid == null ? null : zdjsrid.trim();
    }

    public Date getZdjsrq() {
        return zdjsrq;
    }

    public void setZdjsrq(Date zdjsrq) {
        this.zdjsrq = zdjsrq;
    }

    public String getZdjsyy() {
        return zdjsyy;
    }

    public void setZdjsyy(String zdjsyy) {
        this.zdjsyy = zdjsyy == null ? null : zdjsyy.trim();
    }

	public List<ScrappedInfo> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<ScrappedInfo> infoList) {
		this.infoList = infoList;
	}

	public User getSqr() {
		return sqr;
	}

	public void setSqr(User sqr) {
		this.sqr = sqr;
	}

	public User getShr() {
		return shr;
	}

	public void setShr(User shr) {
		this.shr = shr;
	}
    
}