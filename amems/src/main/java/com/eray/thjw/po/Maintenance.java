package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * @author liub
 * @description 维修方案B_G_011
 * @develop date 2016.08.15
 */
public class Maintenance extends BizEntity{
	
    private String id;

    private String dprtcode;

    private String wxfabh;

    private BigDecimal bb;

    private String zwms;

    private String ywms;

    private String jx;

    private Date jhSxrq;
    
    private Date jhSxrqBegin;
    
    private Date jhSxrqEnd;

    private Date sjSxrq;
    
    private Date sjSxrqBegin;
    
    private Date sjSxrqEnd;

    private String zdbmid;
    
    private Date zdrqBegin;
    
    private Date zdrqEnd;

    private String sxbmid;

    private String sxrid;

    private Date sxsj;

    private Integer zt;
    
    private Integer zxbs;

    private String fBbid;

    private String bBbid;
    
    private String bz;
    
    private String sxjyrid;
    
    private User jyr;
    
    private User user;
    
    private List<FixedCheckItem> fixedCheckedItems;
    
    private BigDecimal maxbb;//最大版本虚拟字段

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

    public String getWxfabh() {
        return wxfabh;
    }

    public void setWxfabh(String wxfabh) {
        this.wxfabh = wxfabh == null ? null : wxfabh.trim();
    }

    public BigDecimal getBb() {
        return bb;
    }

    public void setBb(BigDecimal bb) {
        this.bb = bb;
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

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public Date getJhSxrq() {
        return jhSxrq;
    }

    public void setJhSxrq(Date jhSxrq) {
        this.jhSxrq = jhSxrq;
    }

    public Date getSjSxrq() {
        return sjSxrq;
    }

    public void setSjSxrq(Date sjSxrq) {
        this.sjSxrq = sjSxrq;
    }

    public String getZdbmid() {
        return zdbmid;
    }

    public void setZdbmid(String zdbmid) {
        this.zdbmid = zdbmid == null ? null : zdbmid.trim();
    }

    public String getSxbmid() {
        return sxbmid;
    }

    public void setSxbmid(String sxbmid) {
        this.sxbmid = sxbmid == null ? null : sxbmid.trim();
    }

    public String getSxrid() {
        return sxrid;
    }

    public void setSxrid(String sxrid) {
        this.sxrid = sxrid == null ? null : sxrid.trim();
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
    
    public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public void setZt(Integer zt) {
        this.zt = zt;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Integer getZxbs() {
		return zxbs;
	}

	public void setZxbs(Integer zxbs) {
		this.zxbs = zxbs;
	}

	public List<FixedCheckItem> getFixedCheckedItems() {
		return fixedCheckedItems;
	}

	public void setFixedCheckedItems(List<FixedCheckItem> fixedCheckedItems) {
		this.fixedCheckedItems = fixedCheckedItems;
	}
	public Date getJhSxrqBegin() {
		return jhSxrqBegin;
	}

	public void setJhSxrqBegin(Date jhSxrqBegin) {
		this.jhSxrqBegin = jhSxrqBegin;
	}

	public Date getJhSxrqEnd() {
		return jhSxrqEnd;
	}

	public void setJhSxrqEnd(Date jhSxrqEnd) {
		this.jhSxrqEnd = jhSxrqEnd;
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

	public String getSxjyrid() {
		return sxjyrid;
	}

	public void setSxjyrid(String sxjyrid) {
		this.sxjyrid = sxjyrid;
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

	public User getJyr() {
		return jyr;
	}

	public void setJyr(User jyr) {
		this.jyr = jyr;
	}

	public BigDecimal getMaxbb() {
		return maxbb;
	}

	public void setMaxbb(BigDecimal maxbb) {
		this.maxbb = maxbb;
	}

}