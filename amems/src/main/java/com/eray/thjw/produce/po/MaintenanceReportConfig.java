package com.eray.thjw.produce.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description d_028 维修执管月报配置
 * @CreateTime 2018年4月24日 下午3:57:48
 * @CreateBy 韩武
 */
public class MaintenanceReportConfig {
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String fjzch;

    private String yf;

    private BigDecimal hqgs;

    private BigDecimal gzgs;

    private BigDecimal hhgs;

    private BigDecimal lxgsdj;

    private BigDecimal flxgsdj;

    private BigDecimal cyhcfybl;
    
    private String fjjx;
    
    private String yftext;
    
    private String dprtname;
    
    private String exportName;

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

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getYf() {
        return yf;
    }

    public void setYf(String yf) {
        this.yf = yf == null ? null : yf.trim();
    }

    public BigDecimal getHqgs() {
        return hqgs;
    }

    public void setHqgs(BigDecimal hqgs) {
        this.hqgs = hqgs;
    }

    public BigDecimal getGzgs() {
        return gzgs;
    }

    public void setGzgs(BigDecimal gzgs) {
        this.gzgs = gzgs;
    }

    public BigDecimal getHhgs() {
        return hhgs;
    }

    public void setHhgs(BigDecimal hhgs) {
        this.hhgs = hhgs;
    }

    public BigDecimal getLxgsdj() {
        return lxgsdj;
    }

    public void setLxgsdj(BigDecimal lxgsdj) {
        this.lxgsdj = lxgsdj;
    }

    public BigDecimal getFlxgsdj() {
        return flxgsdj;
    }

    public void setFlxgsdj(BigDecimal flxgsdj) {
        this.flxgsdj = flxgsdj;
    }

    public BigDecimal getCyhcfybl() {
        return cyhcfybl;
    }

    public void setCyhcfybl(BigDecimal cyhcfybl) {
        this.cyhcfybl = cyhcfybl;
    }

	public String getFjjx() {
		return fjjx;
	}

	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}

	public String getYftext() {
		if(yf != null){
			String year = yf.split("-")[0];
			String month = yf.split("-")[1];
			if(month.startsWith("0")){
				month = month.replaceFirst("0", "");
			}
			return year + "年" + month + "月";
		}
		return null;
	}

	public void setYftext(String yftext) {
		this.yftext = yftext;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

	public String getExportName() {
		return fjzch + "飞机" + getYftext() + "执管报告";
	}

	public void setExportName(String exportName) {
		this.exportName = exportName;
	}
	
}