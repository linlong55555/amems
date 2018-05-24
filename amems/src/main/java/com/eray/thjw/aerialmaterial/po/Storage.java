package com.eray.thjw.aerialmaterial.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * @author liub
 * @description 库位D_00901
 * @develop date 2016.09.13
 */
public class Storage extends BizEntity{
	private String id;

    private String mainid;

    private String kwh;

    private String dprtcode;

    private String ckh;

    private Integer cklb;

    private String bz;

    private Integer zt;

    private String bmid;

    private String cjrid;

    private Date cjsj;
    
    /** 虚拟字段 start */
    private Store store;//仓库
    /** 虚拟字段 end */

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

    public String getKwh() {
        return kwh;
    }

    public void setKwh(String kwh) {
        this.kwh = kwh == null ? null : kwh.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh == null ? null : ckh.trim();
    }

    public Integer getCklb() {
        return cklb;
    }

    public void setCklb(Integer cklb) {
        this.cklb = cklb;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid == null ? null : bmid.trim();
    }

    public String getCjrid() {
        return cjrid;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid == null ? null : cjrid.trim();
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}
    
}