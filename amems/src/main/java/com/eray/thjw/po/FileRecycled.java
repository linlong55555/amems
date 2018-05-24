package com.eray.thjw.po;

import java.util.Date;

/**
 * @Description d_018 文件目录-回收站
 * @CreateTime 2018年1月26日 上午10:33:47
 * @CreateBy 韩武
 */
public class FileRecycled extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String mkdm;

    private String dprtcode;

    private String wjid;

    private String wjlx;

    private String wjmc;

    private String hzm;

    private Date czsj;

    private String czbmid;

    private String czrid;
    
    /** 原始位置 */
    private String yswz;
    
    /** 操作人 */
    private User czr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMkdm() {
        return mkdm;
    }

    public void setMkdm(String mkdm) {
        this.mkdm = mkdm == null ? null : mkdm.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getWjid() {
        return wjid;
    }

    public void setWjid(String wjid) {
        this.wjid = wjid == null ? null : wjid.trim();
    }

    public String getWjlx() {
        return wjlx;
    }

    public void setWjlx(String wjlx) {
        this.wjlx = wjlx == null ? null : wjlx.trim();
    }

    public String getWjmc() {
        return wjmc;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc == null ? null : wjmc.trim();
    }

    public String getHzm() {
        return hzm;
    }

    public void setHzm(String hzm) {
        this.hzm = hzm == null ? null : hzm.trim();
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
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

	public User getCzr() {
		return czr;
	}

	public void setCzr(User czr) {
		this.czr = czr;
	}

	public String getYswz() {
		return yswz;
	}

	public void setYswz(String yswz) {
		this.yswz = yswz;
	}
}