package com.eray.thjw.basic.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;
/**
 * @author liub
 * @description 盖板信息D_021
 */
public class CoverPlateInformation extends BizEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String fjjx;

    private String gbbh;

    private String gbmc;

    private String szqywz;

    private BigDecimal kggs;

    private BigDecimal hggs;
    
    private String qy;
    
    private String rlgbbs;
    
    private String whrxm;
    
    private String dprtname;
    


    public String getWhrxm() {
		return whrxm;
	}

	public void setWhrxm(String whrxm) {
		this.whrxm = whrxm;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
    }

    public String getGbbh() {
        return gbbh;
    }

    public void setGbbh(String gbbh) {
        this.gbbh = gbbh == null ? null : gbbh.trim();
    }

    public String getGbmc() {
        return gbmc;
    }

    public void setGbmc(String gbmc) {
        this.gbmc = gbmc == null ? null : gbmc.trim();
    }

    public String getSzqywz() {
        return szqywz;
    }

    public void setSzqywz(String szqywz) {
        this.szqywz = szqywz == null ? null : szqywz.trim();
    }

    public BigDecimal getKggs() {
        return kggs;
    }

    public void setKggs(BigDecimal kggs) {
        this.kggs = kggs;
    }

    public BigDecimal getHggs() {
        return hggs;
    }

    public void setHggs(BigDecimal hggs) {
        this.hggs = hggs;
    }

	public String getQy() {
		return qy;
	}

	public void setQy(String qy) {
		this.qy = qy;
	}

	public String getRlgbbs() {
		return rlgbbs;
	}

	public void setRlgbbs(String rlgbbs) {
		this.rlgbbs = rlgbbs;
	}
    
    
}