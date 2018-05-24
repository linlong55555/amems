package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * 
 * @Description b_h_02801
 * @CreateTime 2018-2-26 下午2:57:09
 * @CreateBy 孙霁
 */
public class DemandSafeguardDetail extends BizEntity{

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
    
    private User whr;
    
    private User sqr;
    
    private List<String> idList;
    
    private List<String> bzztList;
    
    private List<String> wllbList;
    
    
    public List<String> getWllbList() {
		return wllbList;
	}

	public void setWllbList(List<String> wllbList) {
		this.wllbList = wllbList;
	}

	public List<String> getBzztList() {
		return bzztList;
	}

	public void setBzztList(List<String> bzztList) {
		this.bzztList = bzztList;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public User getSqr() {
		return sqr;
	}

	public void setSqr(User sqr) {
		this.sqr = sqr;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

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

    public String getXingh() {
        return xingh;
    }

    public void setXingh(String xingh) {
        this.xingh = xingh == null ? null : xingh.trim();
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg == null ? null : gg.trim();
    }

    public String getJhly() {
        return jhly;
    }

    public void setJhly(String jhly) {
        this.jhly = jhly == null ? null : jhly.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public String getBzjh() {
        return bzjh;
    }

    public void setBzjh(String bzjh) {
        this.bzjh = bzjh == null ? null : bzjh.trim();
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
        this.dw = dw == null ? null : dw.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public String getThj() {
        return thj;
    }

    public void setThj(String thj) {
        this.thj = thj == null ? null : thj.trim();
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
        this.bzbz = bzbz == null ? null : bzbz.trim();
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