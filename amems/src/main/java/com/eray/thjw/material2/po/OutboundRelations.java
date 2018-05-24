package com.eray.thjw.material2.po;

import java.math.BigDecimal;

import com.eray.thjw.po.BizEntity;
/**
 * 
 * @Description b_h_029 出库关系
 * @CreateTime 2018年3月15日 下午3:10:06
 * @CreateBy 林龙
 */
public class OutboundRelations  extends BizEntity{
    private String id;

    private String dprtcode;

    private Integer zt;

    private Integer lylx;

    private String lyid;

    private String lymxid;

    private String ckdid;

    private String ckmxid;

    private BigDecimal cksl;

    private String dw;

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

    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getLylx() {
		return lylx;
	}

	public void setLylx(Integer lylx) {
		this.lylx = lylx;
	}

	public String getLyid() {
        return lyid;
    }

    public void setLyid(String lyid) {
        this.lyid = lyid == null ? null : lyid.trim();
    }

    public String getLymxid() {
        return lymxid;
    }

    public void setLymxid(String lymxid) {
        this.lymxid = lymxid == null ? null : lymxid.trim();
    }

    public String getCkdid() {
        return ckdid;
    }

    public void setCkdid(String ckdid) {
        this.ckdid = ckdid == null ? null : ckdid.trim();
    }

    public String getCkmxid() {
        return ckmxid;
    }

    public void setCkmxid(String ckmxid) {
        this.ckmxid = ckmxid == null ? null : ckmxid.trim();
    }

    public BigDecimal getCksl() {
        return cksl;
    }

    public void setCksl(BigDecimal cksl) {
        this.cksl = cksl;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw == null ? null : dw.trim();
    }
}