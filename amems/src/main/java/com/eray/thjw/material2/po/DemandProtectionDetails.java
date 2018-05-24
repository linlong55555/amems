package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;
/**
 * 
 * @Description b_h_02801 需求保障明细
 * @CreateTime 2018年2月26日 上午10:03:05
 * @CreateBy 林龙
 */
public class DemandProtectionDetails extends BizEntity{
    private String id;

    private String mainid;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer wllb;

    private String bjid;

    private String bjh;

    private String bjmc;

    private String xingh;

    private String gg;

    private String jhly;

    private String zjh;

    private String bzjh;

    private BigDecimal xqsl;

    private String dw;

    private String xlh;

    private String thj;

    private Integer bzzt;

    private String bzbz;

    private Integer xqbs;

    private Integer qrbs;

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

	public Integer getWllb() {
		return wllb;
	}

	public void setWllb(Integer wllb) {
		this.wllb = wllb;
	}

	public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
	}

	public String getBjh() {
		return bjh;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh;
	}

	public String getBjmc() {
		return bjmc;
	}

	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}

	public String getXingh() {
		return xingh;
	}

	public void setXingh(String xingh) {
		this.xingh = xingh;
	}

	public String getGg() {
		return gg;
	}

	public void setGg(String gg) {
		this.gg = gg;
	}

	public String getJhly() {
		return jhly;
	}

	public void setJhly(String jhly) {
		this.jhly = jhly;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}

	public String getBzjh() {
		return bzjh;
	}

	public void setBzjh(String bzjh) {
		this.bzjh = bzjh;
	}

	public BigDecimal getXqsl() {
		return xqsl;
	}

	public void setXqsl(BigDecimal xqsl) {
		this.xqsl = xqsl;
	}

	public String getDw() {
		return dw;
	}

	public void setDw(String dw) {
		this.dw = dw;
	}

	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}

	public String getThj() {
		return thj;
	}

	public void setThj(String thj) {
		this.thj = thj;
	}

	public Integer getBzzt() {
		return bzzt;
	}

	public void setBzzt(Integer bzzt) {
		this.bzzt = bzzt;
	}

	public String getBzbz() {
		return bzbz;
	}

	public void setBzbz(String bzbz) {
		this.bzbz = bzbz;
	}

	public Integer getXqbs() {
		return xqbs;
	}

	public void setXqbs(Integer xqbs) {
		this.xqbs = xqbs;
	}

	public Integer getQrbs() {
		return qrbs;
	}

	public void setQrbs(Integer qrbs) {
		this.qrbs = qrbs;
	}

   
}