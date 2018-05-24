package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;

public class DetailEngineering extends  BizEntity{
    private String id;

    private String mainid;

    private String zxfl;

    private String fjzch;

    private String zjqdid;

    private String bjh;

    private String bjxlh;

    private String jkxmbhF;

    private String jkflbhF;

    private String jkzF;

    private String jkxmbhS;

    private String jkflbhS;

    private String jkzS;

    private String jkxmbhT;

    private String jkflbhT;

    private String jkzT;

    private Integer xh;

    private String gdh;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String dprtcode;
    
    private BigDecimal jhgsRs;

    private BigDecimal jhgsXss;
    
    private String bjms;
    
    private String scgd;
    
    private String zwms;
    
    private String ywms;
    
    
    

    public BigDecimal getJhgsRs() {
		return jhgsRs;
	}

	public void setJhgsRs(BigDecimal jhgsRs) {
		this.jhgsRs = jhgsRs;
	}

	public BigDecimal getJhgsXss() {
		return jhgsXss;
	}

	public void setJhgsXss(BigDecimal jhgsXss) {
		this.jhgsXss = jhgsXss;
	}

	public String getZwms() {
		return zwms;
	}

	public void setZwms(String zwms) {
		this.zwms = zwms;
	}

	public String getYwms() {
		return ywms;
	}

	public void setYwms(String ywms) {
		this.ywms = ywms;
	}

	public String getScgd() {
		return scgd;
	}

	public void setScgd(String scgd) {
		this.scgd = scgd;
	}

	public String getBjms() {
		return bjms;
	}

	public void setBjms(String bjms) {
		this.bjms = bjms;
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

    public String getZxfl() {
        return zxfl;
    }

    public void setZxfl(String zxfl) {
        this.zxfl = zxfl == null ? null : zxfl.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getZjqdid() {
        return zjqdid;
    }

    public void setZjqdid(String zjqdid) {
        this.zjqdid = zjqdid == null ? null : zjqdid.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getBjxlh() {
        return bjxlh;
    }

    public void setBjxlh(String bjxlh) {
        this.bjxlh = bjxlh == null ? null : bjxlh.trim();
    }

    public String getJkxmbhF() {
        return jkxmbhF;
    }

    public void setJkxmbhF(String jkxmbhF) {
        this.jkxmbhF = jkxmbhF == null ? null : jkxmbhF.trim();
    }

    public String getJkflbhF() {
        return jkflbhF;
    }

    public void setJkflbhF(String jkflbhF) {
        this.jkflbhF = jkflbhF == null ? null : jkflbhF.trim();
    }

    public String getJkzF() {
        return jkzF;
    }

    public void setJkzF(String jkzF) {
        this.jkzF = jkzF == null ? null : jkzF.trim();
    }

    public String getJkxmbhS() {
        return jkxmbhS;
    }

    public void setJkxmbhS(String jkxmbhS) {
        this.jkxmbhS = jkxmbhS == null ? null : jkxmbhS.trim();
    }

    public String getJkflbhS() {
        return jkflbhS;
    }

    public void setJkflbhS(String jkflbhS) {
        this.jkflbhS = jkflbhS == null ? null : jkflbhS.trim();
    }

    public String getJkzS() {
        return jkzS;
    }

    public void setJkzS(String jkzS) {
        this.jkzS = jkzS == null ? null : jkzS.trim();
    }

    public String getJkxmbhT() {
        return jkxmbhT;
    }

    public void setJkxmbhT(String jkxmbhT) {
        this.jkxmbhT = jkxmbhT == null ? null : jkxmbhT.trim();
    }

    public String getJkflbhT() {
        return jkflbhT;
    }

    public void setJkflbhT(String jkflbhT) {
        this.jkflbhT = jkflbhT == null ? null : jkflbhT.trim();
    }

    public String getJkzT() {
        return jkzT;
    }

    public void setJkzT(String jkzT) {
        this.jkzT = jkzT == null ? null : jkzT.trim();
    }

  

    public String getGdh() {
        return gdh;
    }

    public void setGdh(String gdh) {
        this.gdh = gdh == null ? null : gdh.trim();
    }

 

    public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
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
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }
}