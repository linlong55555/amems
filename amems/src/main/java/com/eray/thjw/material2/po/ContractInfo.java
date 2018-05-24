package com.eray.thjw.material2.po;

import java.math.BigDecimal;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description B_H2_02201合同明细
 * @CreateTime 2018-3-8 下午2:06:18
 * @CreateBy 刘兵
 */
public class ContractInfo extends BaseEntity {
    private String id;

    private String mainid;

    private String bjid;

    private String bjh;

    private String xlh;

    private BigDecimal sl;

    private String dw;

    private String wlzt;

    private String jhq;

    private Integer jhqdw;

    private BigDecimal dj;

    private String sxyy;

    private String cqid;

    private Integer zt;
    private Integer wllb;
    
    public Integer getWllb() {
		return wllb;
	}

	public void setWllb(Integer wllb) {
		this.wllb = wllb;
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

    public String getBjid() {
        return bjid;
    }

    public void setBjid(String bjid) {
        this.bjid = bjid == null ? null : bjid.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public BigDecimal getSl() {
        return sl;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw == null ? null : dw.trim();
    }

    public String getWlzt() {
        return wlzt;
    }

    public void setWlzt(String wlzt) {
        this.wlzt = wlzt == null ? null : wlzt.trim();
    }

    public String getJhq() {
        return jhq;
    }

    public void setJhq(String jhq) {
        this.jhq = jhq == null ? null : jhq.trim();
    }

    public Integer getJhqdw() {
        return jhqdw;
    }

    public void setJhqdw(Integer jhqdw) {
        this.jhqdw = jhqdw;
    }

    public BigDecimal getDj() {
        return dj;
    }

    public void setDj(BigDecimal dj) {
        this.dj = dj;
    }

    public String getSxyy() {
        return sxyy;
    }

    public void setSxyy(String sxyy) {
        this.sxyy = sxyy == null ? null : sxyy.trim();
    }

    public String getCqid() {
        return cqid;
    }

    public void setCqid(String cqid) {
        this.cqid = cqid == null ? null : cqid.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

	@Override
	public String toString() {
		return "ContractInfo [id=" + id + ", mainid=" + mainid + ", bjid="
				+ bjid + ", bjh=" + bjh + ", xlh=" + xlh + ", sl=" + sl
				+ ", dw=" + dw + ", wlzt=" + wlzt + ", jhq=" + jhq + ", jhqdw="
				+ jhqdw + ", dj=" + dj + ", sxyy=" + sxyy + ", cqid=" + cqid
				+ ", zt=" + zt + ", pagination=" + pagination + ", getId()="
				+ getId() + ", getMainid()=" + getMainid() + ", getBjid()="
				+ getBjid() + ", getBjh()=" + getBjh() + ", getXlh()="
				+ getXlh() + ", getSl()=" + getSl() + ", getDw()=" + getDw()
				+ ", getWlzt()=" + getWlzt() + ", getJhq()=" + getJhq()
				+ ", getJhqdw()=" + getJhqdw() + ", getDj()=" + getDj()
				+ ", getSxyy()=" + getSxyy() + ", getCqid()=" + getCqid()
				+ ", getZt()=" + getZt() + ", getPagination()="
				+ getPagination() + ", getParamsMap()=" + getParamsMap()
				+ ", getCzls()=" + getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
    
}