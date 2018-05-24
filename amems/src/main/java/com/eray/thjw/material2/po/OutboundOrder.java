package com.eray.thjw.material2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description b_h2_023 出库单
 * @CreateTime 2018年3月15日 下午3:09:40
 * @CreateBy 林龙
 */
public class OutboundOrder  extends BizEntity{
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer shlx;

    private String shdh;

    private String shbmid;

    private String shrid;

    private Date shrq;

    private String lydw;

    private String fjzch;

    private String ckid;

    private String bz;

    private Integer zt;

    private String lyid;

    private String lybh;

    private String lymc;

    private List<OutboundOrderDetails> outboundOrderDetailslist; 
    
    public List<OutboundOrderDetails> getOutboundOrderDetailslist() {
		return outboundOrderDetailslist;
	}

	public void setOutboundOrderDetailslist(
			List<OutboundOrderDetails> outboundOrderDetailslist) {
		this.outboundOrderDetailslist = outboundOrderDetailslist;
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

    public String getLydw() {
        return lydw;
    }

    public void setLydw(String lydw) {
        this.lydw = lydw == null ? null : lydw.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getCkid() {
        return ckid;
    }

    public void setCkid(String ckid) {
        this.ckid = ckid == null ? null : ckid.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }


    public Integer getShlx() {
		return shlx;
	}

	public void setShlx(Integer shlx) {
		this.shlx = shlx;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getLyid() {
        return lyid;
    }

    public void setLyid(String lyid) {
        this.lyid = lyid == null ? null : lyid.trim();
    }

    public String getLybh() {
        return lybh;
    }

    public void setLybh(String lybh) {
        this.lybh = lybh == null ? null : lybh.trim();
    }

    public String getLymc() {
        return lymc;
    }

    public void setLymc(String lymc) {
        this.lymc = lymc == null ? null : lymc.trim();
    }
}