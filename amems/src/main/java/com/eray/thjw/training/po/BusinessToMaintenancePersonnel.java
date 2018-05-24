package com.eray.thjw.training.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.quality.po.MaintenancePersonnel;
//b_p_006 岗位 人员关系表
public class BusinessToMaintenancePersonnel extends BizEntity {
    private String id;

    private String gwid;
    
    private String wxrydaid;

    private Integer zt;
    
    private Date ksrq;
    
    private Date jzrq;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private MaintenancePersonnel maintenancePersonnel;
    
    private Department dprt;
    
    private List<Business> businesss;
    
    private List<String> ids;
    
    private String gwbh;
    
    private String rydabh;
    
    private String fjjx;
    
    private String lb;
    
	public String getFjjx() {
		return fjjx;
	}

	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}

	public String getLb() {
		return lb;
	}

	public void setLb(String lb) {
		this.lb = lb;
	}

	public String getRydabh() {
		return rydabh;
	}

	public void setRydabh(String rydabh) {
		this.rydabh = rydabh;
	}

	public String getGwbh() {
		return gwbh;
	}

	public void setGwbh(String gwbh) {
		this.gwbh = gwbh;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public List<Business> getBusinesss() {
		return businesss;
	}

	public void setBusinesss(List<Business> businesss) {
		this.businesss = businesss;
	}

	public Department getDprt() {
		return dprt;
	}

	public void setDprt(Department dprt) {
		this.dprt = dprt;
	}

	public MaintenancePersonnel getMaintenancePersonnel() {
		return maintenancePersonnel;
	}

	public void setMaintenancePersonnel(MaintenancePersonnel maintenancePersonnel) {
		this.maintenancePersonnel = maintenancePersonnel;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGwid() {
        return gwid;
    }

    public void setGwid(String gwid) {
        this.gwid = gwid == null ? null : gwid.trim();
    }

    public String getWxrydaid() {
        return wxrydaid;
    }

    public void setWxrydaid(String wxrydaid) {
        this.wxrydaid = wxrydaid == null ? null : wxrydaid.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getWhbmid() {
        return whbmid;
    }

    public void setWhbmid(String whbmid) {
        this.whbmid = whbmid == null ? null : whbmid.trim();
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

    public Date getKsrq() {
		return ksrq;
	}

	public void setKsrq(Date ksrq) {
		this.ksrq = ksrq;
	}

	public Date getJzrq() {
		return jzrq;
	}

	public void setJzrq(Date jzrq) {
		this.jzrq = jzrq;
	}

	public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }
}