package com.eray.thjw.flightdata.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

public class FlightRecordSheetToPlan extends BizEntity{
    private String id;

    private String fxjldid;

    private Integer rwlx;

    private Integer rwzlx;

    private String rwdid;

    private String xgdjid;

    private String gzxmbldh;

    private Date wcrq;

    private Integer hd;

    private String hdms;

    private String whnr;

    private String zrrid;

    private BigDecimal sjgsRs;

    private BigDecimal sjgsXss;

    private Integer tbbs;

    private Integer zt;

    private String bz;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String dprtcode;
    
    private List<FlightRecordSheetToDisassembly> dismountRecord;	// 拆解记录
    
    private String rowId;
    
    private String rwdh;
    
    private String rwxx;
    
    private User zrr;
    
    private List<String> ids;
    
    private String gzxx;
    
    private String clcs;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFxjldid() {
        return fxjldid;
    }

    public void setFxjldid(String fxjldid) {
        this.fxjldid = fxjldid == null ? null : fxjldid.trim();
    }

    public Integer getRwlx() {
        return rwlx;
    }

    public void setRwlx(Integer rwlx) {
        this.rwlx = rwlx;
    }

    public Integer getRwzlx() {
        return rwzlx;
    }

    public void setRwzlx(Integer rwzlx) {
        this.rwzlx = rwzlx;
    }

    public String getRwdid() {
        return rwdid;
    }

    public void setRwdid(String rwdid) {
        this.rwdid = rwdid == null ? null : rwdid.trim();
    }

    public String getXgdjid() {
        return xgdjid;
    }

    public void setXgdjid(String xgdjid) {
        this.xgdjid = xgdjid == null ? null : xgdjid.trim();
    }

    public String getGzxmbldh() {
        return gzxmbldh;
    }

    public void setGzxmbldh(String gzxmbldh) {
        this.gzxmbldh = gzxmbldh == null ? null : gzxmbldh.trim();
    }

    public Date getWcrq() {
        return wcrq;
    }

    public void setWcrq(Date wcrq) {
        this.wcrq = wcrq;
    }

    public Integer getHd() {
        return hd;
    }

    public void setHd(Integer hd) {
        this.hd = hd;
    }

    public String getHdms() {
        return hdms;
    }

    public void setHdms(String hdms) {
        this.hdms = hdms == null ? null : hdms.trim();
    }

    public String getWhnr() {
        return whnr;
    }

    public void setWhnr(String whnr) {
        this.whnr = whnr == null ? null : whnr.trim();
    }

    public String getZrrid() {
        return zrrid;
    }

    public void setZrrid(String zrrid) {
        this.zrrid = zrrid == null ? null : zrrid.trim();
    }

    public BigDecimal getSjgsRs() {
        return sjgsRs;
    }

    public void setSjgsRs(BigDecimal sjgsRs) {
        this.sjgsRs = sjgsRs;
    }

    public BigDecimal getSjgsXss() {
        return sjgsXss;
    }

    public void setSjgsXss(BigDecimal sjgsXss) {
        this.sjgsXss = sjgsXss;
    }

    public Integer getTbbs() {
        return tbbs;
    }

    public void setTbbs(Integer tbbs) {
        this.tbbs = tbbs;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

	public List<FlightRecordSheetToDisassembly> getDismountRecord() {
		return dismountRecord;
	}

	public void setDismountRecord(
			List<FlightRecordSheetToDisassembly> dismountRecord) {
		this.dismountRecord = dismountRecord;
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getRwdh() {
		return rwdh;
	}

	public void setRwdh(String rwdh) {
		this.rwdh = rwdh;
	}

	public String getRwxx() {
		return rwxx;
	}

	public void setRwxx(String rwxx) {
		this.rwxx = rwxx;
	}

	public User getZrr() {
		return zrr;
	}

	public void setZrr(User zrr) {
		this.zrr = zrr;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getGzxx() {
		return gzxx;
	}

	public void setGzxx(String gzxx) {
		this.gzxx = gzxx;
	}

	public String getClcs() {
		return clcs;
	}

	public void setClcs(String clcs) {
		this.clcs = clcs;
	}
}