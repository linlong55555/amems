package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class FixedCheckItem extends BizEntity {
	
    private String id;

    private String wxfabh;

    private String djbh;

    private BigDecimal bb;

    private BigDecimal pxh;

    private String zwms;

    private String ywms;

    private Integer zyxs;

    private BigDecimal jhgsRs;

    private BigDecimal jhgsXss;

    private Integer yxx;

    private String bz;

    private String shbmid;

    private String shyj;

    private String shrid;
    
    private String shrusername;
    
    private String shrrealname;
    
    private String sdisplayName;

    private Date shsj;
    
    private String pfbmid;

    private String pfyj;

    private String pfrid;
    
    private String pfrusername;
    
    private String pfrrealname;
    
    private String pdisplayName;

    private Date pfsj;
    
    private String zdbmid;

    private String zdrid;
    
    private String zdrusername;

    private Date zdsj;
    
    private Date zdrqBegin;
    
    private Date zdrqEnd;

    private Date sxsj;
    
    private Date sjSxrqBegin;
    
    private Date sjSxrqEnd;

    private Integer zt;

    private Integer spzt;

    private Integer zxbs;

    private String fBbid;

    private String bBbid;

    private String dprtcode;
    
    private String jktj;
    
    private String isModify;
    
    private User user;
    
    private List<MonitorItem> monitorItemList;
    
    private List<FixedCheckWorkContent> fixedCheckWorkContentList;
    
    private Maintenance maintenance;
    
    public Maintenance getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(Maintenance maintenance) {
		this.maintenance = maintenance;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getWxfabh() {
        return wxfabh;
    }

    public void setWxfabh(String wxfabh) {
        this.wxfabh = wxfabh == null ? null : wxfabh.trim();
    }

    public String getDjbh() {
        return djbh;
    }

    public void setDjbh(String djbh) {
        this.djbh = djbh == null ? null : djbh.trim();
    }

    public BigDecimal getBb() {
        return bb;
    }

    public void setBb(BigDecimal bb) {
        this.bb = bb;
    }

    public BigDecimal getPxh() {
    	if(pxh == null){
    		return pxh;
    	}
        return pxh.setScale(0);
    }

    public void setPxh(BigDecimal pxh) {
        this.pxh = pxh;
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

    public Integer getZyxs() {
        return zyxs;
    }

    public void setZyxs(Integer zyxs) {
        this.zyxs = zyxs;
    }

    public BigDecimal getJhgsRs() {
    	if(jhgsRs == null){
    		return jhgsRs;
    	}
        return jhgsRs.setScale(0);
    }

    public void setJhgsRs(BigDecimal jhgsRs) {
        this.jhgsRs = jhgsRs;
    }

    public BigDecimal getJhgsXss() {
        return jhgsXss;
    }

    public void setJhgsXss(BigDecimal jhgsXss) {
        this.jhgsXss = jhgsXss;
    }

    public Integer getYxx() {
        return yxx;
    }

    public void setYxx(Integer yxx) {
        this.yxx = yxx;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getZdbmid() {
        return zdbmid;
    }

    public void setZdbmid(String zdbmid) {
        this.zdbmid = zdbmid == null ? null : zdbmid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }

    public Date getSxsj() {
        return sxsj;
    }

    public void setSxsj(Date sxsj) {
        this.sxsj = sxsj;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getSpzt() {
        return spzt;
    }

    public void setSpzt(Integer spzt) {
        this.spzt = spzt;
    }

    public Integer getZxbs() {
        return zxbs;
    }

    public void setZxbs(Integer zxbs) {
        this.zxbs = zxbs;
    }

    public String getfBbid() {
        return fBbid;
    }

    public void setfBbid(String fBbid) {
        this.fBbid = fBbid == null ? null : fBbid.trim();
    }

    public String getbBbid() {
        return bBbid;
    }

    public void setbBbid(String bBbid) {
        this.bBbid = bBbid == null ? null : bBbid.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<MonitorItem> getMonitorItemList() {
		return monitorItemList;
	}

	public void setMonitorItemList(List<MonitorItem> monitorItemList) {
		this.monitorItemList = monitorItemList;
	}

	public List<FixedCheckWorkContent> getFixedCheckWorkContentList() {
		return fixedCheckWorkContentList;
	}

	public void setFixedCheckWorkContentList(
			List<FixedCheckWorkContent> fixedCheckWorkContentList) {
		this.fixedCheckWorkContentList = fixedCheckWorkContentList;
	}

	public String getJktj() {
		return jktj;
	}

	public void setJktj(String jktj) {
		this.jktj = jktj;
	}

	public String getShbmid() {
		return shbmid;
	}

	public void setShbmid(String shbmid) {
		this.shbmid = shbmid;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public String getShrid() {
		return shrid;
	}
	
	public void setShrid(String shrid) {
		this.shrid = shrid;
	}

	public String getShrrealname() {
		return shrrealname;
	}

	public void setShrrealname(String shrrealname) {
		this.shrrealname = shrrealname;
	}

	public Date getShsj() {
		return shsj;
	}

	public void setShsj(Date shsj) {
		this.shsj = shsj;
	}

	public String getPfbmid() {
		return pfbmid;
	}

	public void setPfbmid(String pfbmid) {
		this.pfbmid = pfbmid;
	}

	public String getPfyj() {
		return pfyj;
	}

	public void setPfyj(String pfyj) {
		this.pfyj = pfyj;
	}

	public String getPfrid() {
		return pfrid;
	}

	public void setPfrid(String pfrid) {
		this.pfrid = pfrid;
	}

	public String getPfrrealname() {
		return pfrrealname;
	}

	public void setPfrrealname(String pfrrealname) {
		this.pfrrealname = pfrrealname;
	}

	public Date getPfsj() {
		return pfsj;
	}

	public void setPfsj(Date pfsj) {
		this.pfsj = pfsj;
	}

	public String getShrusername() {
		return shrusername;
	}

	public void setShrusername(String shrusername) {
		this.shrusername = shrusername;
	}

	public String getPfrusername() {
		return pfrusername;
	}

	public void setPfrusername(String pfrusername) {
		this.pfrusername = pfrusername;
	}

	public String getSdisplayName() {
		sdisplayName = (shrusername==null?"":shrusername).concat(" ").concat(shrrealname==null?"":shrrealname);
		return sdisplayName;
	}

	public void setSdisplayName(String sdisplayName) {
		this.sdisplayName = sdisplayName;
	}

	public String getPdisplayName() {
		pdisplayName = (pfrusername==null?"":pfrusername).concat(" ").concat(pfrrealname==null?"":pfrrealname);
		return pdisplayName;
	}

	public void setPdisplayName(String pdisplayName) {
		this.pdisplayName = pdisplayName;
	}

	public Date getZdrqBegin() {
		return zdrqBegin;
	}

	public void setZdrqBegin(Date zdrqBegin) {
		this.zdrqBegin = zdrqBegin;
	}

	public Date getZdrqEnd() {
		return zdrqEnd;
	}

	public void setZdrqEnd(Date zdrqEnd) {
		this.zdrqEnd = zdrqEnd;
	}

	public Date getSjSxrqBegin() {
		return sjSxrqBegin;
	}

	public void setSjSxrqBegin(Date sjSxrqBegin) {
		this.sjSxrqBegin = sjSxrqBegin;
	}

	public Date getSjSxrqEnd() {
		return sjSxrqEnd;
	}

	public void setSjSxrqEnd(Date sjSxrqEnd) {
		this.sjSxrqEnd = sjSxrqEnd;
	}

	public String getIsModify() {
		return isModify;
	}

	public void setIsModify(String isModify) {
		this.isModify = isModify;
	}

	public String getZdrusername() {
		return zdrusername;
	}

	public void setZdrusername(String zdrusername) {
		this.zdrusername = zdrusername;
	}

}