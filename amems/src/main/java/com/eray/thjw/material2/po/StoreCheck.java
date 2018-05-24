package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description b_h_031 盘点记录
 * @CreateTime 2018-3-20 下午2:04:25
 * @CreateBy 刘兵
 */
public class StoreCheck extends BaseEntity{
    private String id;

    private String dprtcode;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private Integer zt;

    private String bz;

    private Integer ykbs;

    private BigDecimal yksl;

    private BigDecimal ykcsl;

    private String kcllid;

    private Integer wllx;

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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Integer getYkbs() {
        return ykbs;
    }

    public void setYkbs(Integer ykbs) {
        this.ykbs = ykbs;
    }

    public BigDecimal getYksl() {
        return yksl;
    }

    public void setYksl(BigDecimal yksl) {
        this.yksl = yksl;
    }

    public BigDecimal getYkcsl() {
        return ykcsl;
    }

    public void setYkcsl(BigDecimal ykcsl) {
        this.ykcsl = ykcsl;
    }

    public String getKcllid() {
        return kcllid;
    }

    public void setKcllid(String kcllid) {
        this.kcllid = kcllid == null ? null : kcllid.trim();
    }

    public Integer getWllx() {
        return wllx;
    }

    public void setWllx(Integer wllx) {
        this.wllx = wllx;
    }

	@Override
	public String toString() {
		return "StoreCheck [id=" + id + ", dprtcode=" + dprtcode + ", zdbmid="
				+ zdbmid + ", zdrid=" + zdrid + ", zdsj=" + zdsj + ", zt=" + zt
				+ ", bz=" + bz + ", ykbs=" + ykbs + ", yksl=" + yksl
				+ ", ykcsl=" + ykcsl + ", kcllid=" + kcllid + ", wllx=" + wllx
				+ ", pagination=" + pagination + ", getId()=" + getId()
				+ ", getDprtcode()=" + getDprtcode() + ", getZdbmid()="
				+ getZdbmid() + ", getZdrid()=" + getZdrid() + ", getZdsj()="
				+ getZdsj() + ", getZt()=" + getZt() + ", getBz()=" + getBz()
				+ ", getYkbs()=" + getYkbs() + ", getYksl()=" + getYksl()
				+ ", getYkcsl()=" + getYkcsl() + ", getKcllid()=" + getKcllid()
				+ ", getWllx()=" + getWllx() + ", getPagination()="
				+ getPagination() + ", getParamsMap()=" + getParamsMap()
				+ ", getCzls()=" + getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
    
}