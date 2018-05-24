package com.eray.thjw.material2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;
/**
 * 
 * @Description b_h_028
 * @CreateTime 2018-2-26 下午2:56:57
 * @CreateBy 孙霁
 */
public class DemandSafeguard extends BizEntity{
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String bh;

    private Integer jjcd;

    private Integer isYxfx;

    private Integer isFjhtc;

    private String xqlb;

    private Date jhsyrq;

    private String sjbjh;

    private String sjbjmc;

    private String fjzch;

    private String xlh;

    private Integer fxsj;

    private Integer fxxh;

    private String xqyy;

    private String gmjy;

    private String sqrid;

    private Date sqsj;

    private String sqbmid;

    private String jhbmid;

    private String jhrid;

    private Date jhsj;

    private String jhyj;
    
    private User sqr;
    
    private User jhr;
    
    public User getSqr() {
		return sqr;
	}

	public void setSqr(User sqr) {
		this.sqr = sqr;
	}

	public User getJhr() {
		return jhr;
	}

	public void setJhr(User jhr) {
		this.jhr = jhr;
	}

	private List<DemandSafeguardDetail> demandSafeguardDetailList;

	public List<DemandSafeguardDetail> getDemandSafeguardDetailList() {
		return demandSafeguardDetailList;
	}

	public void setDemandSafeguardDetailList(
			List<DemandSafeguardDetail> demandSafeguardDetailList) {
		this.demandSafeguardDetailList = demandSafeguardDetailList;
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

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh == null ? null : bh.trim();
    }

    public Integer getJjcd() {
        return jjcd;
    }

    public void setJjcd(Integer jjcd) {
        this.jjcd = jjcd;
    }

    public Integer getIsYxfx() {
        return isYxfx;
    }

    public void setIsYxfx(Integer isYxfx) {
        this.isYxfx = isYxfx;
    }

    public Integer getIsFjhtc() {
        return isFjhtc;
    }

    public void setIsFjhtc(Integer isFjhtc) {
        this.isFjhtc = isFjhtc;
    }

    public String getXqlb() {
        return xqlb;
    }

    public void setXqlb(String xqlb) {
        this.xqlb = xqlb == null ? null : xqlb.trim();
    }

    public Date getJhsyrq() {
        return jhsyrq;
    }

    public void setJhsyrq(Date jhsyrq) {
        this.jhsyrq = jhsyrq;
    }

    public String getSjbjh() {
        return sjbjh;
    }

    public void setSjbjh(String sjbjh) {
        this.sjbjh = sjbjh == null ? null : sjbjh.trim();
    }

    public String getSjbjmc() {
        return sjbjmc;
    }

    public void setSjbjmc(String sjbjmc) {
        this.sjbjmc = sjbjmc == null ? null : sjbjmc.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public Integer getFxsj() {
        return fxsj;
    }

    public void setFxsj(Integer fxsj) {
        this.fxsj = fxsj;
    }

    public Integer getFxxh() {
        return fxxh;
    }

    public void setFxxh(Integer fxxh) {
        this.fxxh = fxxh;
    }

    public String getXqyy() {
        return xqyy;
    }

    public void setXqyy(String xqyy) {
        this.xqyy = xqyy == null ? null : xqyy.trim();
    }

    public String getGmjy() {
        return gmjy;
    }

    public void setGmjy(String gmjy) {
        this.gmjy = gmjy == null ? null : gmjy.trim();
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

    public String getSqbmid() {
        return sqbmid;
    }

    public void setSqbmid(String sqbmid) {
        this.sqbmid = sqbmid == null ? null : sqbmid.trim();
    }

    public String getJhbmid() {
        return jhbmid;
    }

    public void setJhbmid(String jhbmid) {
        this.jhbmid = jhbmid == null ? null : jhbmid.trim();
    }

    public String getJhrid() {
        return jhrid;
    }

    public void setJhrid(String jhrid) {
        this.jhrid = jhrid == null ? null : jhrid.trim();
    }

    public Date getJhsj() {
        return jhsj;
    }

    public void setJhsj(Date jhsj) {
        this.jhsj = jhsj;
    }

    public String getJhyj() {
        return jhyj;
    }

    public void setJhyj(String jhyj) {
        this.jhyj = jhyj == null ? null : jhyj.trim();
    }
}