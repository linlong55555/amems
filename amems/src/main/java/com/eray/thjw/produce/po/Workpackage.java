package com.eray.thjw.produce.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
/**
 * 
 * @Description b_s2_007  工包135
 * @CreateTime 2017年9月23日 下午3:47:41
 * @CreateBy 岳彬彬
 */
public class Workpackage extends BizEntity{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String xfrdwid;

    private String xfrid;

    private Date xfsj;

    private String gbrid;

    private Date gbrq;

    private String gbyy;

    private String zdbmid;

    private String zdrid;

    private Date zdrq;

    private Integer zt;

    private Integer dtbs;

    private Integer wgbs;

    private String gbbh;

    private String gbmc;

    private String fjzch;

    private String gbxfdwid;

    private Integer jhWwbs;

    private String jhZxdwid;

    private String jhZxdw;

    private Date jhKsrq;

    private Date jhJsrq;

    private String wxlx;

    private String gzyq;

    private String bz;

    private Integer sjWwbs;

    private String sjZxdwid;

    private String sjZxdw;

    private Date sjKsrq;

    private Date sjJsrq;

    private String sjGzz;

    private String sjJcz;

    private String sjZd;

    private String wgfkfjid;
    /** 组织机构*/
    private Department department;
    /** 下发单位*/
    private Department xfdwDepartment;
    /** 维护人*/
    private User whr;
    /** 关闭人*/
    private User gbr;
    /** 飞机*/
    private Aircraftinfo aircrafInfo;
    
    private List<MonitoringWorkpackage> monitoringWorkpackageList;
    
    /** 工单 */
    List<Workorder> workOrders;
    
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

    public String getXfrdwid() {
        return xfrdwid;
    }

    public void setXfrdwid(String xfrdwid) {
        this.xfrdwid = xfrdwid == null ? null : xfrdwid.trim();
    }

    public String getXfrid() {
        return xfrid;
    }

    public void setXfrid(String xfrid) {
        this.xfrid = xfrid == null ? null : xfrid.trim();
    }

    public Date getXfsj() {
        return xfsj;
    }

    public void setXfsj(Date xfsj) {
        this.xfsj = xfsj;
    }

    public String getGbrid() {
        return gbrid;
    }

    public void setGbrid(String gbrid) {
        this.gbrid = gbrid == null ? null : gbrid.trim();
    }

    public Date getGbrq() {
        return gbrq;
    }

    public void setGbrq(Date gbrq) {
        this.gbrq = gbrq;
    }

    public String getGbyy() {
        return gbyy;
    }

    public void setGbyy(String gbyy) {
        this.gbyy = gbyy == null ? null : gbyy.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getDtbs() {
        return dtbs;
    }

    public void setDtbs(Integer dtbs) {
        this.dtbs = dtbs;
    }

    public Integer getWgbs() {
        return wgbs;
    }

    public void setWgbs(Integer wgbs) {
        this.wgbs = wgbs;
    }

    public String getGbbh() {
        return gbbh;
    }

    public void setGbbh(String gbbh) {
        this.gbbh = gbbh == null ? null : gbbh.trim();
    }

    public String getGbmc() {
        return gbmc;
    }

    public void setGbmc(String gbmc) {
        this.gbmc = gbmc == null ? null : gbmc.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getGbxfdwid() {
        return gbxfdwid;
    }

    public void setGbxfdwid(String gbxfdwid) {
        this.gbxfdwid = gbxfdwid == null ? null : gbxfdwid.trim();
    }

    public Integer getJhWwbs() {
        return jhWwbs;
    }

    public void setJhWwbs(Integer jhWwbs) {
        this.jhWwbs = jhWwbs;
    }

    public String getJhZxdwid() {
        return jhZxdwid;
    }

    public void setJhZxdwid(String jhZxdwid) {
        this.jhZxdwid = jhZxdwid == null ? null : jhZxdwid.trim();
    }

    public String getJhZxdw() {
        return jhZxdw;
    }

    public void setJhZxdw(String jhZxdw) {
        this.jhZxdw = jhZxdw == null ? null : jhZxdw.trim();
    }

    public Date getJhKsrq() {
        return jhKsrq;
    }

    public void setJhKsrq(Date jhKsrq) {
        this.jhKsrq = jhKsrq;
    }

    public Date getJhJsrq() {
        return jhJsrq;
    }

    public void setJhJsrq(Date jhJsrq) {
        this.jhJsrq = jhJsrq;
    }

    public String getWxlx() {
        return wxlx;
    }

    public void setWxlx(String wxlx) {
        this.wxlx = wxlx == null ? null : wxlx.trim();
    }

    public String getGzyq() {
        return gzyq;
    }

    public void setGzyq(String gzyq) {
        this.gzyq = gzyq == null ? null : gzyq.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Integer getSjWwbs() {
        return sjWwbs;
    }

    public void setSjWwbs(Integer sjWwbs) {
        this.sjWwbs = sjWwbs;
    }

    public String getSjZxdwid() {
        return sjZxdwid;
    }

    public void setSjZxdwid(String sjZxdwid) {
        this.sjZxdwid = sjZxdwid == null ? null : sjZxdwid.trim();
    }

    public String getSjZxdw() {
        return sjZxdw;
    }

    public void setSjZxdw(String sjZxdw) {
        this.sjZxdw = sjZxdw == null ? null : sjZxdw.trim();
    }

    public Date getSjKsrq() {
        return sjKsrq;
    }

    public void setSjKsrq(Date sjKsrq) {
        this.sjKsrq = sjKsrq;
    }

    public Date getSjJsrq() {
        return sjJsrq;
    }

    public void setSjJsrq(Date sjJsrq) {
        this.sjJsrq = sjJsrq;
    }

    public String getSjGzz() {
        return sjGzz;
    }

    public void setSjGzz(String sjGzz) {
        this.sjGzz = sjGzz == null ? null : sjGzz.trim();
    }

    public String getSjJcz() {
        return sjJcz;
    }

    public void setSjJcz(String sjJcz) {
        this.sjJcz = sjJcz == null ? null : sjJcz.trim();
    }

    public String getSjZd() {
        return sjZd;
    }

    public void setSjZd(String sjZd) {
        this.sjZd = sjZd == null ? null : sjZd.trim();
    }

    public String getWgfkfjid() {
        return wgfkfjid;
    }

    public void setWgfkfjid(String wgfkfjid) {
        this.wgfkfjid = wgfkfjid == null ? null : wgfkfjid.trim();
    }
    
	public String getZdbmid() {
		return zdbmid;
	}

	public void setZdbmid(String zdbmid) {
		this.zdbmid = zdbmid;
	}

	public String getZdrid() {
		return zdrid;
	}

	public void setZdrid(String zdrid) {
		this.zdrid = zdrid;
	}

	public Date getZdrq() {
		return zdrq;
	}

	public void setZdrq(Date zdrq) {
		this.zdrq = zdrq;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Aircraftinfo getAircrafInfo() {
		return aircrafInfo;
	}

	public void setAircrafInfo(Aircraftinfo aircrafInfo) {
		this.aircrafInfo = aircrafInfo;
	}

	public Department getXfdwDepartment() {
		return xfdwDepartment;
	}

	public void setXfdwDepartment(Department xfdwDepartment) {
		this.xfdwDepartment = xfdwDepartment;
	}

	public User getGbr() {
		return gbr;
	}

	public void setGbr(User gbr) {
		this.gbr = gbr;
	}

	public List<MonitoringWorkpackage> getMonitoringWorkpackageList() {
		return monitoringWorkpackageList;
	}

	public void setMonitoringWorkpackageList(
			List<MonitoringWorkpackage> monitoringWorkpackageList) {
		this.monitoringWorkpackageList = monitoringWorkpackageList;
	}

	public List<Workorder> getWorkOrders() {
		return workOrders;
	}

	public void setWorkOrders(List<Workorder> workOrders) {
		this.workOrders = workOrders;
	}

}