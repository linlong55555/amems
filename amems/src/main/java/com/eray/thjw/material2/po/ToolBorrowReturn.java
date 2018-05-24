package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description b_h_00101 工具借用/归还状态
 * @CreateTime 2018年3月28日 上午11:21:48
 * @CreateBy 韩武
 */
public class ToolBorrowReturn {
    private String id;

    private String jyZrrid;

    private String jyZrrmc;

    private Date jySj;

    private BigDecimal jySl;

    private String jyBz;

    private Integer cqjybs;

    private String czbmid;

    private String czrid;

    private Date czsj;
    
    /** 1.借用	2.归还 */
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJyZrrid() {
        return jyZrrid;
    }

    public void setJyZrrid(String jyZrrid) {
        this.jyZrrid = jyZrrid == null ? null : jyZrrid.trim();
    }

    public String getJyZrrmc() {
        return jyZrrmc;
    }

    public void setJyZrrmc(String jyZrrmc) {
        this.jyZrrmc = jyZrrmc == null ? null : jyZrrmc.trim();
    }

    public Date getJySj() {
        return jySj;
    }

    public void setJySj(Date jySj) {
        this.jySj = jySj;
    }

    public BigDecimal getJySl() {
        return jySl;
    }

    public void setJySl(BigDecimal jySl) {
        this.jySl = jySl;
    }

    public String getJyBz() {
        return jyBz;
    }

    public void setJyBz(String jyBz) {
        this.jyBz = jyBz == null ? null : jyBz.trim();
    }

    public Integer getCqjybs() {
        return cqjybs;
    }

    public void setCqjybs(Integer cqjybs) {
        this.cqjybs = cqjybs;
    }

    public String getCzbmid() {
        return czbmid;
    }

    public void setCzbmid(String czbmid) {
        this.czbmid = czbmid == null ? null : czbmid.trim();
    }

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid == null ? null : czrid.trim();
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}