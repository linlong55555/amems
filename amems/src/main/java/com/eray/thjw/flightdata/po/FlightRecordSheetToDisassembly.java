package com.eray.thjw.flightdata.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.productionplan.po.LoadingList;

public class FlightRecordSheetToDisassembly extends BizEntity{
    private String id;

    private String cjjldh;

    private String fxjlgljlid;

    private String fxjldid;

    private Integer zsIs;

    private String zsBz;

    private String cxZjqdid;

    private String cxBz;
    
    private String cxWz;
    
    private String cxFjdid;
    
    private String cxCj;

    private String bz;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private Integer tbbs;

    private String dprtcode;
    
    private MountLoadingList on; // 装上件-装机清单
    
    private LoadingList off;	//拆下件-装机清单
    
    private MountTimeMonitorTotal timeMonitor;	// 装上件-时控件
    
    private List<MountFixedMonitor> fixedMonitor;	// 装上件-定检件
    
    private List<MountSubcomponent> children;	// 子部件
    
    private String subRowId;
    
    private String fjzch;
    
    private List<String> ids;
    
    private String zsWckcid;
    
    private String zsKclvid;
    
    private String cxWckcid;
    
    private String cxKclvid;
    
    private String cxjldh;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCjjldh() {
        return cjjldh;
    }

    public void setCjjldh(String cjjldh) {
        this.cjjldh = cjjldh == null ? null : cjjldh.trim();
    }

    public String getFxjlgljlid() {
        return fxjlgljlid;
    }

    public void setFxjlgljlid(String fxjlgljlid) {
        this.fxjlgljlid = fxjlgljlid == null ? null : fxjlgljlid.trim();
    }

    public String getFxjldid() {
        return fxjldid;
    }

    public void setFxjldid(String fxjldid) {
        this.fxjldid = fxjldid == null ? null : fxjldid.trim();
    }

    public Integer getZsIs() {
        return zsIs;
    }

    public void setZsIs(Integer zsIs) {
        this.zsIs = zsIs;
    }

    public String getZsBz() {
        return zsBz;
    }

    public void setZsBz(String zsBz) {
        this.zsBz = zsBz == null ? null : zsBz.trim();
    }

    public String getCxZjqdid() {
        return cxZjqdid;
    }

    public void setCxZjqdid(String cxZjqdid) {
        this.cxZjqdid = cxZjqdid == null ? null : cxZjqdid.trim();
    }

    public String getCxBz() {
        return cxBz;
    }

    public void setCxBz(String cxBz) {
        this.cxBz = cxBz == null ? null : cxBz.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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

    public Integer getTbbs() {
        return tbbs;
    }

    public void setTbbs(Integer tbbs) {
        this.tbbs = tbbs;
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

	public MountLoadingList getOn() {
		return on;
	}

	public void setOn(MountLoadingList on) {
		this.on = on;
	}

	public MountTimeMonitorTotal getTimeMonitor() {
		return timeMonitor;
	}

	public void setTimeMonitor(MountTimeMonitorTotal timeMonitor) {
		this.timeMonitor = timeMonitor;
	}

	public List<MountFixedMonitor> getFixedMonitor() {
		return fixedMonitor;
	}

	public void setFixedMonitor(List<MountFixedMonitor> fixedMonitor) {
		this.fixedMonitor = fixedMonitor;
	}

	public List<MountSubcomponent> getChildren() {
		return children;
	}

	public void setChildren(List<MountSubcomponent> children) {
		this.children = children;
	}

	public String getSubRowId() {
		return subRowId;
	}

	public void setSubRowId(String subRowId) {
		this.subRowId = subRowId;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public LoadingList getOff() {
		return off;
	}

	public void setOff(LoadingList off) {
		this.off = off;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getCxWz() {
		return cxWz;
	}

	public void setCxWz(String cxWz) {
		this.cxWz = cxWz;
	}

	public String getCxFjdid() {
		return cxFjdid;
	}

	public void setCxFjdid(String cxFjdid) {
		this.cxFjdid = cxFjdid;
	}

	public String getZsWckcid() {
		return zsWckcid;
	}

	public void setZsWckcid(String zsWckcid) {
		this.zsWckcid = zsWckcid;
	}

	public String getZsKclvid() {
		return zsKclvid;
	}

	public void setZsKclvid(String zsKclvid) {
		this.zsKclvid = zsKclvid;
	}

	public String getCxWckcid() {
		return cxWckcid;
	}

	public void setCxWckcid(String cxWckcid) {
		this.cxWckcid = cxWckcid;
	}

	public String getCxKclvid() {
		return cxKclvid;
	}

	public void setCxKclvid(String cxKclvid) {
		this.cxKclvid = cxKclvid;
	}

	public String getCxjldh() {
		return cxjldh;
	}

	public void setCxjldh(String cxjldh) {
		this.cxjldh = cxjldh;
	}

	public String getCxCj() {
		return cxCj;
	}

	public void setCxCj(String cxCj) {
		this.cxCj = cxCj;
	}
	
}