package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description b_s2_00603 航段信息-防冰液信息
 * @CreateTime 2017年10月24日 下午4:58:11
 * @CreateBy 韩武
 */
public class FlightSheetAntiIce extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String mainid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String hdid;

    private Integer xc;

    private String fbylx;

    private String fbdm;

    private String kssj;

    private String bcsj;
    
    private String fxjldid;

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

    public String getHdid() {
        return hdid;
    }

    public void setHdid(String hdid) {
        this.hdid = hdid == null ? null : hdid.trim();
    }

    public Integer getXc() {
        return xc;
    }

    public void setXc(Integer xc) {
        this.xc = xc;
    }

    public String getFbylx() {
        return fbylx;
    }

    public void setFbylx(String fbylx) {
        this.fbylx = fbylx == null ? null : fbylx.trim();
    }

    public String getFbdm() {
        return fbdm;
    }

    public void setFbdm(String fbdm) {
        this.fbdm = fbdm == null ? null : fbdm.trim();
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj == null ? null : kssj.trim();
    }

    public String getBcsj() {
        return bcsj;
    }

    public void setBcsj(String bcsj) {
        this.bcsj = bcsj == null ? null : bcsj.trim();
    }

	public String getFxjldid() {
		return fxjldid;
	}

	public void setFxjldid(String fxjldid) {
		this.fxjldid = fxjldid;
	}
}