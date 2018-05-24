package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.MonitorOptionClass;
import com.eray.thjw.po.MonitorOptionItem;

/**
 * 
 * @Description 维修项目-监控项目B_G2_01201
 * @CreateTime 2017-8-14 下午4:17:18
 * @CreateBy 刘兵
 */
public class ProjectMonitor extends BaseEntity {
    private String id;

    private String mainid;

    private String jklbh;

    private String jkflbh;

    private String scz;

    private Integer dwScz;

    private String zqz;

    private Integer dwZqz;

    private String ydzQ;

    private Integer ydzQdw;

    private String ydzH;

    private Integer ydzHdw;
    
    private String jkdxid;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private String bjh;
    
    private String cj;
    
    /** 监控大类 */
    private MonitorOptionClass monitorOptionClass;
    
    /** 监控设置项 */
    private MonitorOptionItem monitorOptionItem;
    
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

    public String getJklbh() {
        return jklbh;
    }

    public void setJklbh(String jklbh) {
        this.jklbh = jklbh == null ? null : jklbh.trim();
    }

    public String getJkflbh() {
        return jkflbh;
    }

    public void setJkflbh(String jkflbh) {
        this.jkflbh = jkflbh == null ? null : jkflbh.trim();
    }

    public String getScz() {
        return scz;
    }

    public void setScz(String scz) {
        this.scz = scz == null ? null : scz.trim();
    }

    public Integer getDwScz() {
        return dwScz;
    }

    public void setDwScz(Integer dwScz) {
        this.dwScz = dwScz;
    }

    public String getZqz() {
        return zqz;
    }

    public void setZqz(String zqz) {
        this.zqz = zqz == null ? null : zqz.trim();
    }

    public Integer getDwZqz() {
        return dwZqz;
    }

    public void setDwZqz(Integer dwZqz) {
        this.dwZqz = dwZqz;
    }

    public String getYdzQ() {
        return ydzQ;
    }

    public void setYdzQ(String ydzQ) {
        this.ydzQ = ydzQ == null ? null : ydzQ.trim();
    }

    public Integer getYdzQdw() {
        return ydzQdw;
    }

    public void setYdzQdw(Integer ydzQdw) {
        this.ydzQdw = ydzQdw;
    }

    public String getYdzH() {
        return ydzH;
    }

    public void setYdzH(String ydzH) {
        this.ydzH = ydzH == null ? null : ydzH.trim();
    }

    public Integer getYdzHdw() {
        return ydzHdw;
    }

    public void setYdzHdw(Integer ydzHdw) {
        this.ydzHdw = ydzHdw;
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
    

	public String getJkdxid() {
		return jkdxid;
	}

	public void setJkdxid(String jkdxid) {
		this.jkdxid = jkdxid == null ? null : jkdxid.trim();
	}

	public MonitorOptionClass getMonitorOptionClass() {
		return monitorOptionClass;
	}

	public void setMonitorOptionClass(MonitorOptionClass monitorOptionClass) {
		this.monitorOptionClass = monitorOptionClass;
	}

	public MonitorOptionItem getMonitorOptionItem() {
		return monitorOptionItem;
	}

	public void setMonitorOptionItem(MonitorOptionItem monitorOptionItem) {
		this.monitorOptionItem = monitorOptionItem;
	}

	public String getBjh() {
		return bjh;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh;
	}
	
	public String getCj() {
		return cj;
	}

	public void setCj(String cj) {
		this.cj = cj;
	}

	@Override
	public String toString() {
		return "ProjectMonitor [id=" + id + ", mainid=" + mainid + ", jklbh="
				+ jklbh + ", jkflbh=" + jkflbh + ", scz=" + scz + ", dwScz="
				+ dwScz + ", zqz=" + zqz + ", dwZqz=" + dwZqz + ", ydzQ="
				+ ydzQ + ", ydzQdw=" + ydzQdw + ", ydzH=" + ydzH + ", ydzHdw="
				+ ydzHdw + ", jkdxid=" + jkdxid + ", zt=" + zt + ", whbmid="
				+ whbmid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", monitorOptionClass=" + monitorOptionClass
				+ ", monitorOptionItem=" + monitorOptionItem + ", pagination="
				+ pagination + ", getId()=" + getId() + ", getMainid()="
				+ getMainid() + ", getJklbh()=" + getJklbh() + ", getJkflbh()="
				+ getJkflbh() + ", getScz()=" + getScz() + ", getDwScz()="
				+ getDwScz() + ", getZqz()=" + getZqz() + ", getDwZqz()="
				+ getDwZqz() + ", getYdzQ()=" + getYdzQ() + ", getYdzQdw()="
				+ getYdzQdw() + ", getYdzH()=" + getYdzH() + ", getYdzHdw()="
				+ getYdzHdw() + ", getZt()=" + getZt() + ", getWhbmid()="
				+ getWhbmid() + ", getWhrid()=" + getWhrid() + ", getWhsj()="
				+ getWhsj() + ", getJkdxid()=" + getJkdxid()
				+ ", getMonitorOptionClass()=" + getMonitorOptionClass()
				+ ", getMonitorOptionItem()=" + getMonitorOptionItem()
				+ ", getPagination()=" + getPagination() + ", getParamsMap()="
				+ getParamsMap() + ", getCzls()=" + getCzls()
				+ ", getLogOperationEnum()=" + getLogOperationEnum()
				+ ", getZbid()=" + getZbid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}