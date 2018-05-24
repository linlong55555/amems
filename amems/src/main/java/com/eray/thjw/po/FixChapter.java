package com.eray.thjw.po;

import java.util.Date;

@SuppressWarnings("serial")
public class FixChapter extends BizEntity {
    private String zjh;

    private String zwms;

    private String ywms;

    private String rJcsj;

    private String bmid;

    private String cjrid;

    private Date cjsj;  
    
    private Integer zt;
    
    private String displayname;
    
    private String dprtname;

    /** 扩展字段,章节号+英文描述 ,用于页面回显 **/
    private String displayName;
    
	public String getDisplayName() {
		displayName = (zjh==null?"":zjh).concat(" ").concat(ywms==null?"":ywms);
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
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

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}
    
    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public String getZwms() {
        return zwms;
    }

    public void setZwms(String zwms) {
        this.zwms = zwms == null ? null : zwms.trim();
    }

    public String getYwms() {
        return ywms;
    }

    public void setYwms(String ywms) {
        this.ywms = ywms == null ? null : ywms.trim();
    }

    public String getrJcsj() {
        return rJcsj;
    }

    public void setrJcsj(String rJcsj) {
        this.rJcsj = rJcsj == null ? null : rJcsj.trim();
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
        this.cjrid = cjrid;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }
}