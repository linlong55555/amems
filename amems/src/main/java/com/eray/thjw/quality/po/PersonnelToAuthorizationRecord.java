package com.eray.thjw.quality.po;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.training.po.Business;

@SuppressWarnings("serial")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PersonnelToAuthorizationRecord extends BaseEntity{
    private String id;

    private String mainid;

    private String dprtcode;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer xxlx;

    private String zjbh;

    private Date bfrq;

    private String bfdw;

    private Date yxqKs;

    private Date yxqJs;

    private String zy;

    private String sqdj;

    private String xc;

    private String fjjx;

    private String bz;

    private Integer isJk;
    
    /** 附件 */
    private List<Attachment> attachments;
    
    /** 维修人员 */
    private MaintenancePersonnel maintenancePersonnel;
    
    /** 岗位 */
    private List<Business> businesses;
    
    /** 部门 */ 
    private Department department;
    
    private String rybh;
    
    public String getRybh() {
		return rybh;
	}

	public void setRybh(String rybh) {
		this.rybh = rybh;
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

    public Integer getXxlx() {
        return xxlx;
    }

    public void setXxlx(Integer xxlx) {
        this.xxlx = xxlx;
    }

    public String getZjbh() {
        return zjbh;
    }

    public void setZjbh(String zjbh) {
        this.zjbh = zjbh == null ? null : zjbh.trim();
    }

    public Date getBfrq() {
        return bfrq;
    }

    public void setBfrq(Date bfrq) {
        this.bfrq = bfrq;
    }

    public String getBfdw() {
        return bfdw;
    }

    public void setBfdw(String bfdw) {
        this.bfdw = bfdw == null ? null : bfdw.trim();
    }

    public Date getYxqKs() {
        return yxqKs;
    }

    public void setYxqKs(Date yxqKs) {
        this.yxqKs = yxqKs;
    }

    public Date getYxqJs() {
        return yxqJs;
    }

    public void setYxqJs(Date yxqJs) {
        this.yxqJs = yxqJs;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
    }

    public String getSqdj() {
        return sqdj;
    }

    public void setSqdj(String sqdj) {
        this.sqdj = sqdj == null ? null : sqdj.trim();
    }

    public String getXc() {
        return xc;
    }

    public void setXc(String xc) {
        this.xc = xc == null ? null : xc.trim();
    }

    public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Integer getIsJk() {
        return isJk;
    }

    public void setIsJk(Integer isJk) {
        this.isJk = isJk;
    }

	public MaintenancePersonnel getMaintenancePersonnel() {
		return maintenancePersonnel;
	}

	public void setMaintenancePersonnel(MaintenancePersonnel maintenancePersonnel) {
		this.maintenancePersonnel = maintenancePersonnel;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<Business> getBusinesses() {
		return businesses;
	}

	public void setBusinesses(List<Business> businesses) {
		this.businesses = businesses;
	}
}