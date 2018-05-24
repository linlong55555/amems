package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;

public class StockFreezeHistory {
    private String id;

    private Integer kclx;

    private String kcid;

    private Integer ywlx;
    
    private String ywbh;

    private String ywid;

    private BigDecimal djsl;
    
    private Date czsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getKclx() {
        return kclx;
    }

    public void setKclx(Integer kclx) {
        this.kclx = kclx;
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }

    public Integer getYwlx() {
        return ywlx;
    }

    public void setYwlx(Integer ywlx) {
        this.ywlx = ywlx;
    }

    public String getYwid() {
        return ywid;
    }

    public void setYwid(String ywid) {
        this.ywid = ywid == null ? null : ywid.trim();
    }

    public BigDecimal getDjsl() {
        return djsl;
    }

    public void setDjsl(BigDecimal djsl) {
        this.djsl = djsl;
    }

	public String getYwbh() {
		return ywbh;
	}

	public void setYwbh(String ywbh) {
		this.ywbh = ywbh;
	}

	public Date getCzsj() {
		return czsj;
	}

	public void setCzsj(Date czsj) {
		this.czsj = czsj;
	}
    
}