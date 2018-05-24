package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 
 * @Description b_h_023
 * @CreateTime 2018-3-23 下午2:32:52
 * @CreateBy 孙霁
 */
public class FreezResume {
    private String id;

    private Integer kclx;

    private String kcid;

    private Integer ywlx;

    private String ywid;

    private BigDecimal djsl;

    private String ywbh;

    private Date czsj;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }


    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }


    public Integer getKclx() {
		return kclx;
	}

	public void setKclx(Integer kclx) {
		this.kclx = kclx;
	}

	public Integer getYwlx() {
		return ywlx;
	}

	public void setYwlx(Integer ywlx) {
		this.ywlx = ywlx;
	}

	public String getYwid() {
        return ywid;
    }

    public void setYwid(String ywid) {
        this.ywid = ywid == null ? null : ywid.trim();
    }

    public BigDecimal getDjsl() {
        return djsl;
    }

    public void setDjsl(BigDecimal djsl) {
        this.djsl = djsl;
    }

    public String getYwbh() {
        return ywbh;
    }

    public void setYwbh(String ywbh) {
        this.ywbh = ywbh == null ? null : ywbh.trim();
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }
}