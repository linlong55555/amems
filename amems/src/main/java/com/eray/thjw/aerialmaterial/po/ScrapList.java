package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

import enu.aerialmaterial.ContractStatusEnum;
import enu.aerialmaterial.ScrapStatusEnum;
/*
 * 
 * b_h_009 报废单
 */
@SuppressWarnings("serial")
public class ScrapList extends BizEntity{
    private String id;

    private String dprtcode;

    private String bfdh;

    private String bfyy;

    private String bz;

    private Integer zt;
    
    private String ztText;

    private String spbmid;

    private String sprid;

    private Date spsj;

    private String spyj;

    private String bfbmid;

    private String bfrid;

    private Date bfsj;

    private Integer bflx;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;
    
    private String sprname;
   
    private String bfrname;
    
    private String zdrname;
    
    private String dprtname;
    
    private String zdjsrid;
    
    private Date zdjsrq;
    
    private String zdjsyy;
    
    private List<HasScrappedList> hasScrappedList;
    
    public String getZtText() {
    	return zt==null?"":ScrapStatusEnum.getName(zt);
	}

	public void setZtText(String ztText) {
		this.ztText = ztText;
	}

    private User spr;
    
    private User bfr;
    
    private User zdr;
    
    public String getSprname() {
		return sprname;
	}

	public void setSprname(String sprname) {
		this.sprname = sprname;
	}

	public String getBfrname() {
		return bfrname;
	}

	public void setBfrname(String bfrname) {
		this.bfrname = bfrname;
	}

	public String getZdrname() {
		return zdrname;
	}

	public void setZdrname(String zdrname) {
		this.zdrname = zdrname;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

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

    public String getBfdh() {
        return bfdh;
    }

    public void setBfdh(String bfdh) {
        this.bfdh = bfdh == null ? null : bfdh.trim();
    }

    public String getBfyy() {
        return bfyy;
    }

    public void setBfyy(String bfyy) {
        this.bfyy = bfyy == null ? null : bfyy.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

  

    public String getSpbmid() {
        return spbmid;
    }

    public void setSpbmid(String spbmid) {
        this.spbmid = spbmid == null ? null : spbmid.trim();
    }

    public String getSprid() {
        return sprid;
    }

    public void setSprid(String sprid) {
        this.sprid = sprid == null ? null : sprid.trim();
    }

    public Date getSpsj() {
        return spsj;
    }

    public void setSpsj(Date spsj) {
        this.spsj = spsj;
    }

    public String getSpyj() {
        return spyj;
    }

    public void setSpyj(String spyj) {
        this.spyj = spyj == null ? null : spyj.trim();
    }

    public String getBfbmid() {
        return bfbmid;
    }

    public void setBfbmid(String bfbmid) {
        this.bfbmid = bfbmid == null ? null : bfbmid.trim();
    }

    public String getBfrid() {
        return bfrid;
    }

    public void setBfrid(String bfrid) {
        this.bfrid = bfrid == null ? null : bfrid.trim();
    }

    public Date getBfsj() {
        return bfsj;
    }

    public void setBfsj(Date bfsj) {
        this.bfsj = bfsj;
    }


    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getBflx() {
		return bflx;
	}

	public void setBflx(Integer bflx) {
		this.bflx = bflx;
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

	public List<HasScrappedList> getHasScrappedList() {
		return hasScrappedList;
	}

	public void setHasScrappedList(List<HasScrappedList> hasScrappedList) {
		this.hasScrappedList = hasScrappedList;
	}

	public User getSpr() {
		return spr;
	}

	public void setSpr(User spr) {
		this.spr = spr;
	}

	public User getBfr() {
		return bfr;
	}

	public void setBfr(User bfr) {
		this.bfr = bfr;
	}

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid;
	}

	public Date getZdjsrq() {
		return zdjsrq;
	}

	public void setZdjsrq(Date zdjsrq) {
		this.zdjsrq = zdjsrq;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}
}