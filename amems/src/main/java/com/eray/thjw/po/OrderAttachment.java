package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.util.Utils;

public class OrderAttachment extends BaseEntity{
    private String id;

    private String mainid;

    private String dprtcode;

    private String yswjm;

    private String wbwjm;

    private String nbwjm;

    private BigDecimal wjdx;
    
    private String wjdxStr;

    private String hzm;

    private String sm;

    private String cflj;

    private Integer yxzt;

    private Date czsj;

    private String czbmid;

    private String czrid;
    
    private User czr_user;
    
    

    public User getCzr_user() {
		return czr_user;
	}

	public void setCzr_user(User czr_user) {
		this.czr_user = czr_user;
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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getYswjm() {
        return yswjm;
    }

    public void setYswjm(String yswjm) {
        this.yswjm = yswjm == null ? null : yswjm.trim();
    }

    public String getWbwjm() {
        return wbwjm;
    }

    public void setWbwjm(String wbwjm) {
        this.wbwjm = wbwjm == null ? null : wbwjm.trim();
    }

    public String getNbwjm() {
        return nbwjm;
    }

    public void setNbwjm(String nbwjm) {
        this.nbwjm = nbwjm == null ? null : nbwjm.trim();
    }


    public String getHzm() {
        return hzm;
    }

    public void setHzm(String hzm) {
        this.hzm = hzm == null ? null : hzm.trim();
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm == null ? null : sm.trim();
    }

    public String getCflj() {
        return cflj;
    }

    public void setCflj(String cflj) {
        this.cflj = cflj == null ? null : cflj.trim();
    }

	public BigDecimal getWjdx() {
		return wjdx;
	}

	public void setWjdx(BigDecimal wjdx) {
		this.wjdx = wjdx;
	}

	public Integer getYxzt() {
		return yxzt;
	}

	public void setYxzt(Integer yxzt) {
		this.yxzt = yxzt;
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
	public String getWjdxStr() {
		
		wjdxStr = Utils.FileUtil.bytes2unitG(this.getWjdx()==null?0:this.getWjdx().multiply(BigDecimal.valueOf(1024)).intValue());
		return wjdxStr;
	}

	public void setWjdxStr(String wjdxStr) {
		this.wjdxStr = wjdxStr;
	}
}