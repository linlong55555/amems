package com.eray.thjw.system.po;

import java.util.Date;
import com.eray.thjw.po.BizEntity;

@SuppressWarnings("serial")
public class DictItem extends BizEntity {
	
    private String id;

    private Integer lxid;
    
    private String dprtcode;
    
    private String sz;

    private String mc;

    private Integer xc;
   
    private String whrid;

    private Date whsj;
    
    private String displayname;
    
	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getLxid() {
        return lxid;
    }

    public void setLxid(Integer lxid) {
        this.lxid = lxid;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc == null ? null : mc.trim();
    }

    public String getSz() {
        return sz;
    }

    public void setSz(String sz) {
        this.sz = sz == null ? null : sz.trim();
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
    
    



	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}



	public Integer getXc() {
		return xc;
	}

	public void setXc(Integer xc) {
		this.xc = xc;
	}

	@Override
	public String toString() {
		return "DictItem [id=" + id + ", lxid=" + lxid + ", dprtcode=" + dprtcode + ", sz=" + sz + ", mc=" + mc
				+ ", xc=" + xc + ", whrid=" + whrid + ", whsj=" + whsj + ", displayname=" + displayname + "]";
	}


    
}