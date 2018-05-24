package com.eray.thjw.material2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
/**
 * 
 * @Description b_w_00101 计量工具监控明细表
 * @CreateTime 2018年2月7日 上午9:43:38
 * @CreateBy 林龙
 */
public class MeasurementToolsDetails extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String mainid;

    private String bjxlh;

    private String bjh;

    private String zwms;

    private String ywms;

    private String gg;

    private String xh;

    private Integer bjbs;

    private String bz;

    private Date jyScrq;

    private Date jyXcrq;

    private Integer jyZq;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String jlfs;

    private String jldj;
    
    private Integer jyZqdw;

    private User whr; //维护人
    
    private Department dprt; //组织机构
    
    private MeasurementTools mes; //计量工具主表
    
    private List<Attachment> attachmentList; //d_011 附件表List
    
    private MeasurementToolsDetailsHistory measurementToolsDetailsHistory; //计量工具监控明细历史表
    
    private List<String> bhList; // id list

	public List<String> getBhList() {
		return bhList;
	}

	public void setBhList(List<String> bhList) {
		this.bhList = bhList;
	}

	public MeasurementToolsDetailsHistory getMeasurementToolsDetailsHistory() {
		return measurementToolsDetailsHistory;
	}

	public void setMeasurementToolsDetailsHistory(
			MeasurementToolsDetailsHistory measurementToolsDetailsHistory) {
		this.measurementToolsDetailsHistory = measurementToolsDetailsHistory;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public MeasurementTools getMes() {
		return mes;
	}

	public void setMes(MeasurementTools mes) {
		this.mes = mes;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Department getDprt() {
		return dprt;
	}

	public void setDprt(Department dprt) {
		this.dprt = dprt;
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

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
    }

    public String getBjxlh() {
        return bjxlh;
    }

    public void setBjxlh(String bjxlh) {
        this.bjxlh = bjxlh == null ? null : bjxlh.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
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

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg == null ? null : gg.trim();
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh == null ? null : xh.trim();
    }

	public Integer getBjbs() {
		return bjbs;
	}

	public void setBjbs(Integer bjbs) {
		this.bjbs = bjbs;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Date getJyScrq() {
		return jyScrq;
	}

	public void setJyScrq(Date jyScrq) {
		this.jyScrq = jyScrq;
	}

	public Date getJyXcrq() {
		return jyXcrq;
	}

	public void setJyXcrq(Date jyXcrq) {
		this.jyXcrq = jyXcrq;
	}

	public Integer getJyZq() {
		return jyZq;
	}

	public void setJyZq(Integer jyZq) {
		this.jyZq = jyZq;
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
		this.whbmid = whbmid;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	public String getJlfs() {
		return jlfs;
	}

	public void setJlfs(String jlfs) {
		this.jlfs = jlfs;
	}

	public String getJldj() {
		return jldj;
	}

	public void setJldj(String jldj) {
		this.jldj = jldj;
	}

	public Integer getJyZqdw() {
		return jyZqdw;
	}

	public void setJyZqdw(Integer jyZqdw) {
		this.jyZqdw = jyZqdw;
	}

   
}