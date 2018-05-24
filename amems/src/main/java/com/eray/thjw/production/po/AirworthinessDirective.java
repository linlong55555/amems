package com.eray.thjw.production.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
/**
 * 适航指令
 * @author zhuchao
 *
 */
@SuppressWarnings("serial")
public class AirworthinessDirective extends BizEntity {
    private String id;

    private String lsh;

    private String fjzch;

    private Date bshYjrq;

    private Date bshSjrq;

    private Date bshRq;

    private String bshSpdh;

    private String bshPzrid;

    private Date shYjrq;

    private Date shSjrq;

    private Date shRq;

    private String shSpdh;

    private String shPzrid;

    private String bz;

   /* private String zddwid;

    private String zdrid;

    private Date zdsj;*/
    
    private String whdwid;

    private String whrid;

    private Date whsj;
    
    private String tcyy;
    

    private String zt;
    
    //扩展区域
    private Date bshYjrqStart;
    private Date bshYjrqEnd;
    private Date bshSjrqStart;
    private Date bshSjrqEnd;
    
    private Date shYjrqStart;
    private Date shYjrqEnd;
    private Date shSjrqStart;
    private Date shSjrqEnd;
    
    
    private String bshpzrName;
    private String shpzrName;
    //不适航原因
    private Integer bshyy;
    
     

    public String getTcyy() {
		return tcyy;
	}

	public void setTcyy(String tcyy) {
		this.tcyy = tcyy;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLsh() {
        return lsh;
    }

    public void setLsh(String lsh) {
        this.lsh = lsh == null ? null : lsh.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public Date getBshYjrq() {
        return bshYjrq;
    }

    public void setBshYjrq(Date bshYjrq) {
        this.bshYjrq = bshYjrq;
    }

    public Date getBshSjrq() {
        return bshSjrq;
    }

    public void setBshSjrq(Date bshSjrq) {
        this.bshSjrq = bshSjrq;
    }

    public Date getBshRq() {
        return bshRq;
    }

    public void setBshRq(Date bshRq) {
        this.bshRq = bshRq;
    }

    public String getBshSpdh() {
        return bshSpdh;
    }

    public void setBshSpdh(String bshSpdh) {
        this.bshSpdh = bshSpdh == null ? null : bshSpdh.trim();
    }

    public String getBshPzrid() {
        return bshPzrid;
    }

    public void setBshPzrid(String bshPzrid) {
        this.bshPzrid = bshPzrid == null ? null : bshPzrid.trim();
    }

    public Date getShYjrq() {
        return shYjrq;
    }

    public void setShYjrq(Date shYjrq) {
        this.shYjrq = shYjrq;
    }

    public Date getShSjrq() {
        return shSjrq;
    }

    public void setShSjrq(Date shSjrq) {
        this.shSjrq = shSjrq;
    }

    public Date getShRq() {
        return shRq;
    }

    public void setShRq(Date shRq) {
        this.shRq = shRq;
    }

    public String getShSpdh() {
        return shSpdh;
    }

    public void setShSpdh(String shSpdh) {
        this.shSpdh = shSpdh == null ? null : shSpdh.trim();
    }

    public String getShPzrid() {
        return shPzrid;
    }

    public void setShPzrid(String shPzrid) {
        this.shPzrid = shPzrid == null ? null : shPzrid.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

	public Date getBshYjrqStart() {
		return bshYjrqStart;
	}

	public void setBshYjrqStart(Date bshYjrqStart) {
		this.bshYjrqStart = bshYjrqStart;
	}

	public Date getBshYjrqEnd() {
		return bshYjrqEnd;
	}

	public void setBshYjrqEnd(Date bshYjrqEnd) {
		this.bshYjrqEnd = bshYjrqEnd;
	}

	public Date getBshSjrqStart() {
		return bshSjrqStart;
	}

	public void setBshSjrqStart(Date bshSjrqStart) {
		this.bshSjrqStart = bshSjrqStart;
	}

	public Date getBshSjrqEnd() {
		return bshSjrqEnd;
	}

	public void setBshSjrqEnd(Date bshSjrqEnd) {
		this.bshSjrqEnd = bshSjrqEnd;
	}

	public Date getShYjrqStart() {
		return shYjrqStart;
	}

	public void setShYjrqStart(Date shYjrqStart) {
		this.shYjrqStart = shYjrqStart;
	}

	public Date getShYjrqEnd() {
		return shYjrqEnd;
	}

	public void setShYjrqEnd(Date shYjrqEnd) {
		this.shYjrqEnd = shYjrqEnd;
	}

	public Date getShSjrqStart() {
		return shSjrqStart;
	}

	public void setShSjrqStart(Date shSjrqStart) {
		this.shSjrqStart = shSjrqStart;
	}

	public Date getShSjrqEnd() {
		return shSjrqEnd;
	}

	public void setShSjrqEnd(Date shSjrqEnd) {
		this.shSjrqEnd = shSjrqEnd;
	}

	public String getBshpzrName() {
		return bshpzrName;
	}

	public void setBshpzrName(String bshpzrName) {
		this.bshpzrName = bshpzrName;
	}

	public String getShpzrName() {
		return shpzrName;
	}

	public void setShpzrName(String shpzrName) {
		this.shpzrName = shpzrName;
	}

	public Integer getBshyy() {
		return bshyy;
	}

	public void setBshyy(Integer bshyy) {
		this.bshyy = bshyy;
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
}