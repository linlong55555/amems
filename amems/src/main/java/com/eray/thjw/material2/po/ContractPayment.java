package com.eray.thjw.material2.po;

import java.math.BigDecimal;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description B_H2_02202合同收付款信息
 * @CreateTime 2018-3-8 下午2:03:07
 * @CreateBy 刘兵
 */
public class ContractPayment extends BaseEntity {
    private String id;

    private String mainid;

    private Integer lx;

    private String sfkfs;

    private BigDecimal je;

    private String htmxid;

    private String fphm;

    private String bz;

    private Integer zt;

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

    public Integer getLx() {
        return lx;
    }

    public void setLx(Integer lx) {
        this.lx = lx;
    }

    public String getSfkfs() {
        return sfkfs;
    }

    public void setSfkfs(String sfkfs) {
        this.sfkfs = sfkfs == null ? null : sfkfs.trim();
    }

    public BigDecimal getJe() {
        return je;
    }

    public void setJe(BigDecimal je) {
        this.je = je;
    }

    public String getHtmxid() {
        return htmxid;
    }

    public void setHtmxid(String htmxid) {
        this.htmxid = htmxid == null ? null : htmxid.trim();
    }

    public String getFphm() {
        return fphm;
    }

    public void setFphm(String fphm) {
        this.fphm = fphm == null ? null : fphm.trim();
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

	@Override
	public String toString() {
		return "ContractPayment [id=" + id + ", mainid=" + mainid + ", lx="
				+ lx + ", sfkfs=" + sfkfs + ", je=" + je + ", htmxid=" + htmxid
				+ ", fphm=" + fphm + ", bz=" + bz + ", zt=" + zt
				+ ", pagination=" + pagination + ", getId()=" + getId()
				+ ", getMainid()=" + getMainid() + ", getLx()=" + getLx()
				+ ", getSfkfs()=" + getSfkfs() + ", getJe()=" + getJe()
				+ ", getHtmxid()=" + getHtmxid() + ", getFphm()=" + getFphm()
				+ ", getBz()=" + getBz() + ", getZt()=" + getZt()
				+ ", getPagination()=" + getPagination() + ", getParamsMap()="
				+ getParamsMap() + ", getCzls()=" + getCzls()
				+ ", getLogOperationEnum()=" + getLogOperationEnum()
				+ ", getZbid()=" + getZbid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
    
}