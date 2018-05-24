package com.eray.thjw.produce.po;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.mapper.CustomJsonDateDeserializer;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * @Description b_s2_006 飞行记录本
 * @CreateTime 2017年9月30日 上午11:05:55
 * @CreateBy 徐勇
 */
public class FlightSheet extends BizEntity {
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String zddwid;

    private String zdrid;

    private Date zdsj;

    private String xddwid;

    private String xdrid;

    private Date xdsj;

    private String xdbz;

    private Integer zt;

    private Integer isXdtx;

    private String fxjlbh;

    private String fjzch;

    private String jlbym;

    private Date fxrq;

    private String bz;
    
    private Integer gjbs;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date kcsj;
    
    /** 上一页 */
    private String lastPage;
    
    /** 下一页 */
    private String nextPage;
    
    /** 制单人 */
    private User zdr;
    
    /** 修订人 */
    private User xdr;
    
    /** 飞行记录本 */
    private FlightSheetVoyage flightSheetVoyage;
    
    /** 飞行记录本-航次 */
    private List<FlightSheetVoyage> flightSheetVoyageList;
    
    /** 飞行记录本-航段 */
    private List<FlightSheetLeg> legs;
    
    /** 附件 */
    private List<Attachment> attachments;

	public List<FlightSheetVoyage> getFlightSheetVoyageList() {
		return flightSheetVoyageList;
	}

	public void setFlightSheetVoyageList(
			List<FlightSheetVoyage> flightSheetVoyageList) {
		this.flightSheetVoyageList = flightSheetVoyageList;
	}

	public FlightSheetVoyage getFlightSheetVoyage() {
		return flightSheetVoyage;
	}

	public void setFlightSheetVoyage(FlightSheetVoyage flightSheetVoyage) {
		this.flightSheetVoyage = flightSheetVoyage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

    public String getZddwid() {
        return zddwid;
    }

    public void setZddwid(String zddwid) {
        this.zddwid = zddwid == null ? null : zddwid.trim();
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

    public String getXddwid() {
        return xddwid;
    }

    public void setXddwid(String xddwid) {
        this.xddwid = xddwid == null ? null : xddwid.trim();
    }

    public String getXdrid() {
        return xdrid;
    }

    public void setXdrid(String xdrid) {
        this.xdrid = xdrid == null ? null : xdrid.trim();
    }

    public Date getXdsj() {
        return xdsj;
    }

    public void setXdsj(Date xdsj) {
        this.xdsj = xdsj;
    }

    public String getXdbz() {
        return xdbz;
    }

    public void setXdbz(String xdbz) {
        this.xdbz = xdbz == null ? null : xdbz.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getIsXdtx() {
        return isXdtx;
    }

    public void setIsXdtx(Integer isXdtx) {
        this.isXdtx = isXdtx;
    }

    public String getFxjlbh() {
        return fxjlbh;
    }

    public void setFxjlbh(String fxjlbh) {
        this.fxjlbh = fxjlbh == null ? null : fxjlbh.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getJlbym() {
        return jlbym;
    }

    public void setJlbym(String jlbym) {
        this.jlbym = jlbym == null ? null : jlbym.trim();
    }

    public Date getFxrq() {
        return fxrq;
    }

    public void setFxrq(Date fxrq) {
        this.fxrq = fxrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Date getKcsj() {
        return kcsj;
    }

    public void setKcsj(Date kcsj) {
        this.kcsj = kcsj;
    }

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public User getXdr() {
		return xdr;
	}

	public void setXdr(User xdr) {
		this.xdr = xdr;
	}

	public List<FlightSheetLeg> getLegs() {
		return legs;
	}

	public void setLegs(List<FlightSheetLeg> legs) {
		this.legs = legs;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public String getLastPage() {
		return lastPage;
	}

	public void setLastPage(String lastPage) {
		this.lastPage = lastPage;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public Integer getGjbs() {
		return gjbs;
	}

	public void setGjbs(Integer gjbs) {
		this.gjbs = gjbs;
	}
}