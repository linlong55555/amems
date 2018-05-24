package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;

/**
 * b_h_01201 外派部件对应库存信息
 * @author xu.yong
 *
 */
public class ExpatriateDetail {
    private String id;

    private String mainid;

    private Integer sjlx;

    private Integer gldjlx;

    private String djid;

    private String kcllid;

    private String dprtcode;
    
    private BigDecimal cxsl;

    public BigDecimal getCxsl() {
		return cxsl;
	}

	public void setCxsl(BigDecimal cxsl) {
		this.cxsl = cxsl;
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

    public Integer getSjlx() {
        return sjlx;
    }

    public void setSjlx(Integer sjlx) {
        this.sjlx = sjlx;
    }

    public Integer getGldjlx() {
        return gldjlx;
    }

    public void setGldjlx(Integer gldjlx) {
        this.gldjlx = gldjlx;
    }

    public String getDjid() {
        return djid;
    }

    public void setDjid(String djid) {
        this.djid = djid == null ? null : djid.trim();
    }

    public String getKcllid() {
        return kcllid;
    }

    public void setKcllid(String kcllid) {
        this.kcllid = kcllid == null ? null : kcllid.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }
}