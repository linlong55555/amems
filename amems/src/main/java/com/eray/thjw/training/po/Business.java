package com.eray.thjw.training.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkRequire;

/**
 * b_p_001 岗位
 * @author ll
 *
 */
public class Business extends BizEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dgbh;

    private String dgmc;

    private String bz;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private User user; //用户
    
    private Department dprt;//组织机构
    
    private String ry;//人员数
    
    private List<BusinessToCourse> BusinessToCourses;
    
    private String dprtcode;
    
    private List<BusinessToMaintenancePersonnel> businessToMaintenancePersonnels;
    
    private List<WorkRequire> workRequire;
    
	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public List<WorkRequire> getWorkRequire() {
		return workRequire;
	}

	public void setWorkRequire(List<WorkRequire> workRequire) {
		this.workRequire = workRequire;
	}

	public List<BusinessToCourse> getBusinessToCourses() {
		return BusinessToCourses;
	}

	public void setBusinessToCourses(List<BusinessToCourse> businessToCourses) {
		BusinessToCourses = businessToCourses;
	}

	public String getRy() {
		return ry;
	}

	public void setRy(String ry) {
		this.ry = ry;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Department getDprt() {
		return dprt;
	}

	public void setDprt(Department dprt) {
		this.dprt = dprt;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDgbh() {
        return dgbh;
    }

    public void setDgbh(String dgbh) {
        this.dgbh = dgbh == null ? null : dgbh.trim();
    }

    public String getDgmc() {
        return dgmc;
    }

    public void setDgmc(String dgmc) {
        this.dgmc = dgmc == null ? null : dgmc.trim();
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

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }

	public List<BusinessToMaintenancePersonnel> getBusinessToMaintenancePersonnels() {
		return businessToMaintenancePersonnels;
	}

	public void setBusinessToMaintenancePersonnels(
			List<BusinessToMaintenancePersonnel> businessToMaintenancePersonnels) {
		this.businessToMaintenancePersonnels = businessToMaintenancePersonnels;
	}
}