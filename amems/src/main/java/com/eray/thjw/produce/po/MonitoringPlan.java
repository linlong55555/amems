package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.project2.po.EOMonitorIemSet;
import com.eray.thjw.project2.po.ProductionOrderMonitor;
import com.eray.thjw.project2.po.ProjectMonitor;

/**
 * @Description B_S2_904 监控数据-(计划)执行数据
 * @CreateTime 2017-9-23 下午3:32:58
 * @CreateBy 刘兵
 */
public class MonitoringPlan extends BaseEntity{

	private String id;

    private String jksjid;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String jklbh;

    private String jkflbh;

    private String jhqsz;

    private String jhz;

    private String sjz;

    private String csz;

    private Integer wz;
    
    private String scz;

    private Integer dwScz;

    private String zqz;

    private Integer dwZqz;

    private String ydzQ;

    private Integer ydzQdw;

    private String ydzH;

    private Integer ydzHdw;
    
    /** 监控数据-上次执行数据 */
    private MonitoringLast monitoringLast;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJksjid() {
        return jksjid;
    }

    public void setJksjid(String jksjid) {
        this.jksjid = jksjid == null ? null : jksjid.trim();
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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
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

    public String getJhqsz() {
        return jhqsz;
    }

    public void setJhqsz(String jhqsz) {
        this.jhqsz = jhqsz == null ? null : jhqsz.trim();
    }

    public String getJhz() {
        return jhz;
    }

    public void setJhz(String jhz) {
        this.jhz = jhz == null ? null : jhz.trim();
    }

    public String getSjz() {
        return sjz;
    }

    public void setSjz(String sjz) {
        this.sjz = sjz == null ? null : sjz.trim();
    }

    public String getCsz() {
        return csz;
    }

    public void setCsz(String csz) {
        this.csz = csz == null ? null : csz.trim();
    }

    public Integer getWz() {
        return wz;
    }

    public void setWz(Integer wz) {
        this.wz = wz;
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

	public String getScz() {
		return scz;
	}

	public void setScz(String scz) {
		this.scz = scz;
	}

	public Integer getDwScz() {
		return dwScz;
	}

	public void setDwScz(Integer dwScz) {
		this.dwScz = dwScz;
	}

	public MonitoringLast getMonitoringLast() {
		return monitoringLast;
	}

	public void setMonitoringLast(MonitoringLast monitoringLast) {
		this.monitoringLast = monitoringLast;
	}

	@Override
	public String toString() {
		return "MonitoringPlan [id=" + id + ", jksjid=" + jksjid + ", whdwid="
				+ whdwid + ", whrid=" + whrid + ", whsj=" + whsj + ", zt=" + zt
				+ ", jklbh=" + jklbh + ", jkflbh=" + jkflbh + ", jhqsz="
				+ jhqsz + ", jhz=" + jhz + ", sjz=" + sjz + ", csz=" + csz
				+ ", wz=" + wz + ", scz=" + scz + ", dwScz=" + dwScz + ", zqz="
				+ zqz + ", dwZqz=" + dwZqz + ", ydzQ=" + ydzQ + ", ydzQdw="
				+ ydzQdw + ", ydzH=" + ydzH + ", ydzHdw=" + ydzHdw
				+ ", monitoringLast=" + monitoringLast + ", pagination="
				+ pagination + ", getId()=" + getId() + ", getJksjid()="
				+ getJksjid() + ", getWhdwid()=" + getWhdwid()
				+ ", getWhrid()=" + getWhrid() + ", getWhsj()=" + getWhsj()
				+ ", getZt()=" + getZt() + ", getJklbh()=" + getJklbh()
				+ ", getJkflbh()=" + getJkflbh() + ", getJhqsz()=" + getJhqsz()
				+ ", getJhz()=" + getJhz() + ", getSjz()=" + getSjz()
				+ ", getCsz()=" + getCsz() + ", getWz()=" + getWz()
				+ ", getZqz()=" + getZqz() + ", getDwZqz()=" + getDwZqz()
				+ ", getYdzQ()=" + getYdzQ() + ", getYdzQdw()=" + getYdzQdw()
				+ ", getYdzH()=" + getYdzH() + ", getYdzHdw()=" + getYdzHdw()
				+ ", getScz()=" + getScz() + ", getDwScz()=" + getDwScz()
				+ ", getMonitoringLast()=" + getMonitoringLast()
				+ ", getPagination()=" + getPagination() + ", getParamsMap()="
				+ getParamsMap() + ", getCzls()=" + getCzls()
				+ ", getLogOperationEnum()=" + getLogOperationEnum()
				+ ", getZbid()=" + getZbid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public MonitoringPlan() {
		super();
	}
	
	public MonitoringPlan(ProjectMonitor projectMonitor) {
		super();
		this.jklbh = projectMonitor.getJklbh();
		this.jkflbh = projectMonitor.getJkflbh();
		this.scz = projectMonitor.getScz();
		this.dwScz = projectMonitor.getDwScz();
		this.zqz = projectMonitor.getZqz();
		this.dwZqz = projectMonitor.getDwZqz();
		this.ydzQ = projectMonitor.getYdzQ();
		this.ydzQdw = projectMonitor.getYdzQdw();
		this.ydzH = projectMonitor.getYdzH();
		this.ydzHdw = projectMonitor.getYdzHdw();
	}
	
	public MonitoringPlan(EOMonitorIemSet eoMonitorIemSet) {
		super();
		this.jklbh = eoMonitorIemSet.getJklbh();
		this.jkflbh = eoMonitorIemSet.getJkflbh();
		this.scz = eoMonitorIemSet.getScz();
		this.dwScz = eoMonitorIemSet.getDwScz().intValue();
		this.zqz = eoMonitorIemSet.getZqz();
		this.dwZqz = eoMonitorIemSet.getDwZqz() == null ? null:eoMonitorIemSet.getDwZqz().intValue();
		this.ydzQ = eoMonitorIemSet.getYdzQ();
		this.ydzQdw = eoMonitorIemSet.getYdzQdw() == null ? null : eoMonitorIemSet.getYdzQdw().intValue();
		this.ydzH = eoMonitorIemSet.getYdzH();
		this.ydzHdw = eoMonitorIemSet.getYdzHdw() == null ? null : eoMonitorIemSet.getYdzHdw().intValue();
	}
	
	public MonitoringPlan(ProductionOrderMonitor productionOrderMonitor) {
		super();
		this.jklbh = productionOrderMonitor.getJklbh();
		this.jkflbh = productionOrderMonitor.getJkflbh();
		this.scz = productionOrderMonitor.getScz();
		this.dwScz = productionOrderMonitor.getDwScz();
		this.zqz = productionOrderMonitor.getZqz();
		this.dwZqz = productionOrderMonitor.getDwZqz();
		this.ydzQ = productionOrderMonitor.getYdzQ();
		this.ydzQdw = productionOrderMonitor.getYdzQdw();
		this.ydzH = productionOrderMonitor.getYdzH();
		this.ydzHdw = productionOrderMonitor.getYdzHdw();
	}

}