package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * d_00803 航材对应价格
 * @author xu.yong
 *
 */
@SuppressWarnings("serial")
public class MaterialCost extends MaterialCostSupport{
    
	private String id;

    private String mainid;

    private BigDecimal juescb;

    private BigDecimal jiescb;

    private BigDecimal gscb;

    private String whrid;

    private Date whsj;
    

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

    public BigDecimal getJuescb() {
        return juescb;
    }

    public void setJuescb(BigDecimal juescb) {
        this.juescb = juescb;
    }

    public BigDecimal getJiescb() {
        return jiescb;
    }

    public void setJiescb(BigDecimal jiescb) {
        this.jiescb = jiescb;
    }

    public BigDecimal getGscb() {
        return gscb;
    }

    public void setGscb(BigDecimal gscb) {
        this.gscb = gscb;
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
}