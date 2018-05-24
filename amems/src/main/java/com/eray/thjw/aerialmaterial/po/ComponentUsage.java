package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 部件使用情况表	B_H_01001
 * @author hanwu
 *
 */
public class ComponentUsage {
    private String id;

    private String mainid;

    private String dprtcode;

    private String jh;

    private String xlh;

    private Integer zt;

    private Integer isCyjs;

    private Integer csbj;

    private Date whsj;

    private Integer xdbs;

    private String fjzch;

    private String fxjldid;

    private String fxjldh;

    private Integer hc;

    private Integer fxsjXs;

    private Integer fxsjFz;

    private Integer fsjXs;

    private Integer fsjFz;

    private BigDecimal qljxh;

    private Integer jcsjXs;

    private Integer jcsjFz;

    private BigDecimal jcxh;

    private BigDecimal fdjN1;

    private BigDecimal fdjN2;

    private BigDecimal fdjN3;

    private BigDecimal fdjN4;

    private BigDecimal fdjN5;

    private BigDecimal fdjN6;

    private Integer ssdsjXs;

    private Integer ssdsjFz;

    private BigDecimal dgxh;

    private BigDecimal ts1;

    private BigDecimal ts2;

    private Integer tsfxpzid;

    private BigDecimal xsz;
    
    private String fsj;		// 发动机时间
    
    private String jcsj;	// 绞车时间
    
    private String ssdsj;	// 搜索灯时间
    
    private Integer wz;		// 位置
    
    private String zjqdid;	// 装机清单id
    
    private String fjdid;	// 父节点id
    
    private Integer cj;		// 层级

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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh == null ? null : jh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getIsCyjs() {
        return isCyjs;
    }

    public void setIsCyjs(Integer isCyjs) {
        this.isCyjs = isCyjs;
    }

    public Integer getCsbj() {
        return csbj;
    }

    public void setCsbj(Integer csbj) {
        this.csbj = csbj;
    }

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }

    public Integer getXdbs() {
        return xdbs;
    }

    public void setXdbs(Integer xdbs) {
        this.xdbs = xdbs;
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getFxjldid() {
        return fxjldid;
    }

    public void setFxjldid(String fxjldid) {
        this.fxjldid = fxjldid == null ? null : fxjldid.trim();
    }

    public String getFxjldh() {
        return fxjldh;
    }

    public void setFxjldh(String fxjldh) {
        this.fxjldh = fxjldh == null ? null : fxjldh.trim();
    }

    public Integer getHc() {
        return hc;
    }

    public void setHc(Integer hc) {
        this.hc = hc;
    }

    public Integer getFxsjXs() {
        return fxsjXs;
    }

    public void setFxsjXs(Integer fxsjXs) {
        this.fxsjXs = fxsjXs;
    }

    public Integer getFxsjFz() {
        return fxsjFz;
    }

    public void setFxsjFz(Integer fxsjFz) {
        this.fxsjFz = fxsjFz;
    }

    public Integer getFsjXs() {
        return fsjXs;
    }

    public void setFsjXs(Integer fsjXs) {
        this.fsjXs = fsjXs;
    }

    public Integer getFsjFz() {
        return fsjFz;
    }

    public void setFsjFz(Integer fsjFz) {
        this.fsjFz = fsjFz;
    }

    public BigDecimal getQljxh() {
        return qljxh;
    }

    public void setQljxh(BigDecimal qljxh) {
        this.qljxh = qljxh;
    }

    public Integer getJcsjXs() {
        return jcsjXs;
    }

    public void setJcsjXs(Integer jcsjXs) {
        this.jcsjXs = jcsjXs;
    }

    public Integer getJcsjFz() {
        return jcsjFz;
    }

    public void setJcsjFz(Integer jcsjFz) {
        this.jcsjFz = jcsjFz;
    }

    public BigDecimal getJcxh() {
        return jcxh;
    }

    public void setJcxh(BigDecimal jcxh) {
        this.jcxh = jcxh;
    }

    public BigDecimal getFdjN1() {
        return fdjN1;
    }

    public void setFdjN1(BigDecimal fdjN1) {
        this.fdjN1 = fdjN1;
    }

    public BigDecimal getFdjN2() {
        return fdjN2;
    }

    public void setFdjN2(BigDecimal fdjN2) {
        this.fdjN2 = fdjN2;
    }

    public BigDecimal getFdjN3() {
        return fdjN3;
    }

    public void setFdjN3(BigDecimal fdjN3) {
        this.fdjN3 = fdjN3;
    }

    public BigDecimal getFdjN4() {
        return fdjN4;
    }

    public void setFdjN4(BigDecimal fdjN4) {
        this.fdjN4 = fdjN4;
    }

    public BigDecimal getFdjN5() {
        return fdjN5;
    }

    public void setFdjN5(BigDecimal fdjN5) {
        this.fdjN5 = fdjN5;
    }

    public BigDecimal getFdjN6() {
        return fdjN6;
    }

    public void setFdjN6(BigDecimal fdjN6) {
        this.fdjN6 = fdjN6;
    }

    public Integer getSsdsjXs() {
        return ssdsjXs;
    }

    public void setSsdsjXs(Integer ssdsjXs) {
        this.ssdsjXs = ssdsjXs;
    }

    public Integer getSsdsjFz() {
        return ssdsjFz;
    }

    public void setSsdsjFz(Integer ssdsjFz) {
        this.ssdsjFz = ssdsjFz;
    }

    public BigDecimal getDgxh() {
        return dgxh;
    }

    public void setDgxh(BigDecimal dgxh) {
        this.dgxh = dgxh;
    }

    public BigDecimal getTs1() {
        return ts1;
    }

    public void setTs1(BigDecimal ts1) {
        this.ts1 = ts1;
    }

    public BigDecimal getTs2() {
        return ts2;
    }

    public void setTs2(BigDecimal ts2) {
        this.ts2 = ts2;
    }

    public Integer getTsfxpzid() {
        return tsfxpzid;
    }

    public void setTsfxpzid(Integer tsfxpzid) {
        this.tsfxpzid = tsfxpzid;
    }

    public BigDecimal getXsz() {
        return xsz;
    }

    public void setXsz(BigDecimal xsz) {
        this.xsz = xsz;
    }

	public String getFsj() {
		return fsj;
	}

	public void setFsj(String fsj) {
		this.fsj = fsj;
	}

	public String getJcsj() {
		return jcsj;
	}

	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}

	public String getSsdsj() {
		return ssdsj;
	}

	public void setSsdsj(String ssdsj) {
		this.ssdsj = ssdsj;
	}

	public Integer getWz() {
		return wz;
	}

	public void setWz(Integer wz) {
		this.wz = wz;
	}

	public String getZjqdid() {
		return zjqdid;
	}

	public void setZjqdid(String zjqdid) {
		this.zjqdid = zjqdid;
	}

	public String getFjdid() {
		return fjdid;
	}

	public void setFjdid(String fjdid) {
		this.fjdid = fjdid;
	}

	public Integer getCj() {
		return cj;
	}

	public void setCj(Integer cj) {
		this.cj = cj;
	}
}