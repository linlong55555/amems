package com.eray.thjw.basic.po;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.eray.thjw.mapper.CustomJsonDateDeserializer;
import com.eray.thjw.po.BaseEntity;

/**
 * @Description D_023 区域和飞机站位对象
 * @CreateTime 2017年8月18日 下午3:16:15
 * @CreateBy 徐勇
 */
public class ZoneStation extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode; 
    

	private String dprtname; 

    private String jx;

    /**类型 1区域2飞机站位 */
    private Integer lx;

    private String sz;

    private String ms;

    private String whdwid;

    private String whrid;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date whsj;
    
    private String displayname;
    
    private String keyword;
    
    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	private Integer zt;
    
    

    public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public Integer getLx() {
        return lx;
    }

    public void setLx(Integer lx) {
        this.lx = lx;
    }

    public String getSz() {
        return sz;
    }

    public void setSz(String sz) {
        this.sz = sz == null ? null : sz.trim();
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms == null ? null : ms.trim();
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

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}
	
    public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}
    
    
}