package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * b_h_019 退库明细
 * @author xu.yong
 *
 */
public class CancelStock {
    private String id;

    private String dprtcode;

    private String xgdjid;

    private BigDecimal tksl;

    private Integer zt;

    private String xgtkmxid;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

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

    public String getXgdjid() {
        return xgdjid;
    }

    public void setXgdjid(String xgdjid) {
        this.xgdjid = xgdjid == null ? null : xgdjid.trim();
    }

    public BigDecimal getTksl() {
        return tksl;
    }

    public void setTksl(BigDecimal tksl) {
        this.tksl = tksl;
    }


    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getXgtkmxid() {
        return xgtkmxid;
    }

    public void setXgtkmxid(String xgtkmxid) {
        this.xgtkmxid = xgtkmxid == null ? null : xgtkmxid.trim();
    }

    public String getZdbmid() {
        return zdbmid;
    }

    public void setZdbmid(String zdbmid) {
        this.zdbmid = zdbmid == null ? null : zdbmid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }
}