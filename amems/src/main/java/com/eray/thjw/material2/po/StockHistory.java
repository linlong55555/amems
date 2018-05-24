package com.eray.thjw.material2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.aerialmaterial.po.Stock;
import com.eray.thjw.aerialmaterial.po.Store;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.BizEntity;

/**
 * @Description b_h_025 库存履历主信息
 * @CreateTime 2018年3月13日 下午4:40:38
 * @CreateBy 韩武
 */
public class StockHistory extends BizEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private Date czsj;

    private String czrbmid;

    private String czrid;

    private String czr;

    private Integer czlx;

    private Integer czzlx;

    private String czsm;

    private String kcid;

    private String ywid;

    private String ywbh;

    private String ywmxid;
    
    private List<Stock> stockList;

    public List<Stock> getStockList() {
		return stockList;
	}

	public void setStockList(List<Stock> stockList) {
		this.stockList = stockList;
	}

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

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }

    public String getCzrbmid() {
        return czrbmid;
    }

    public void setCzrbmid(String czrbmid) {
        this.czrbmid = czrbmid == null ? null : czrbmid.trim();
    }

    public String getCzrid() {
        return czrid;
    }

    public void setCzrid(String czrid) {
        this.czrid = czrid == null ? null : czrid.trim();
    }

    public String getCzr() {
        return czr;
    }

    public void setCzr(String czr) {
        this.czr = czr == null ? null : czr.trim();
    }

    public Integer getCzlx() {
        return czlx;
    }

    public void setCzlx(Integer czlx) {
        this.czlx = czlx;
    }

    public Integer getCzzlx() {
        return czzlx;
    }

    public void setCzzlx(Integer czzlx) {
        this.czzlx = czzlx;
    }

    public String getCzsm() {
        return czsm;
    }

    public void setCzsm(String czsm) {
        this.czsm = czsm == null ? null : czsm.trim();
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }

    public String getYwid() {
        return ywid;
    }

    public void setYwid(String ywid) {
        this.ywid = ywid == null ? null : ywid.trim();
    }

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh == null ? null : ywbh.trim();
    }

    public String getYwmxid() {
        return ywmxid;
    }

    public void setYwmxid(String ywmxid) {
        this.ywmxid = ywmxid == null ? null : ywmxid.trim();
    }
}