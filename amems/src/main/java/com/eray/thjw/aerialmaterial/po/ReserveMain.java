package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * 
 * 航材提订/送修申请 b_h_002 提订单/送修单
 * @author xu.yong
 *
 */
public class ReserveMain extends BizEntity {
    private String id;

    private String dprtcode;

    private Integer djlx;

    private String sqdh;

    private Integer zt;

    private String sqyy;

    private Integer jjcd;

    private Date yqqx;

    private String sqbmid;

    private String sqrid;
    
    private String sqrusername;
    
    private String sqrrealname;

    private Date sqsj;
    
    private Date sqrqBegin;
    
    private Date sqrqEnd;

    private String spbmid;

    private String sprid;
    
    private String sprusername;
    
    private String sprrealname;

    private Date spsj;

    private String spyj;
    
    private String zdjsrid;
    
    private String zdjsrq;
    
    private String zdjsyy;
    
    private String kcid;
    
    private String kcidOld;
    
    private String kcllid;
    
    private String glgd;//关联工单
    
    private String tdmc;
    
    private List<String> reserveDetailIdList;
    
    private List<ReserveDetail> reserveDetailList;
    
    private List<ReserveWorkOrder> reserveWorkOrderList;
    
    private Integer noRead;
    
    private Integer readAll;
    
    private User sqr;
    
    private User spr;
    
    private User jsr;

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

    public Integer getDjlx() {
        return djlx;
    }

    public void setDjlx(Integer djlx) {
        this.djlx = djlx;
    }

    public String getSqdh() {
        return sqdh;
    }

    public void setSqdh(String sqdh) {
        this.sqdh = sqdh == null ? null : sqdh.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getSqyy() {
        return sqyy;
    }

    public void setSqyy(String sqyy) {
        this.sqyy = sqyy == null ? null : sqyy.trim();
    }

    public Integer getJjcd() {
        return jjcd;
    }

    public void setJjcd(Integer jjcd) {
        this.jjcd = jjcd;
    }

    public Date getYqqx() {
        return yqqx;
    }

    public void setYqqx(Date yqqx) {
        this.yqqx = yqqx;
    }

    public String getSqbmid() {
        return sqbmid;
    }

    public void setSqbmid(String sqbmid) {
        this.sqbmid = sqbmid == null ? null : sqbmid.trim();
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid == null ? null : sqrid.trim();
    }

    public Date getSqsj() {
        return sqsj;
    }

    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
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
    
	public Date getSqrqBegin() {
		return sqrqBegin;
	}

	public void setSqrqBegin(Date sqrqBegin) {
		this.sqrqBegin = sqrqBegin;
	}

	public Date getSqrqEnd() {
		return sqrqEnd;
	}

	public void setSqrqEnd(Date sqrqEnd) {
		this.sqrqEnd = sqrqEnd;
	}

	public String getSqrusername() {
		return sqrusername;
	}

	public void setSqrusername(String sqrusername) {
		this.sqrusername = sqrusername;
	}

	public String getSqrrealname() {
		return sqrrealname;
	}

	public void setSqrrealname(String sqrrealname) {
		this.sqrrealname = sqrrealname;
	}

	public String getSprusername() {
		return sprusername;
	}

	public void setSprusername(String sprusername) {
		this.sprusername = sprusername;
	}

	public String getSprrealname() {
		return sprrealname;
	}

	public void setSprrealname(String sprrealname) {
		this.sprrealname = sprrealname;
	}
	
	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid;
	}

	public String getZdjsrq() {
		return zdjsrq;
	}

	public void setZdjsrq(String zdjsrq) {
		this.zdjsrq = zdjsrq;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}

	public List<String> getReserveDetailIdList() {
		return reserveDetailIdList;
	}

	public void setReserveDetailIdList(List<String> reserveDetailIdList) {
		this.reserveDetailIdList = reserveDetailIdList;
	}

	public List<ReserveDetail> getReserveDetailList() {
		return reserveDetailList;
	}

	public void setReserveDetailList(List<ReserveDetail> reserveDetailList) {
		this.reserveDetailList = reserveDetailList;
	}
	
	public List<ReserveWorkOrder> getReserveWorkOrderList() {
		return reserveWorkOrderList;
	}

	public void setReserveWorkOrderList(List<ReserveWorkOrder> reserveWorkOrderList) {
		this.reserveWorkOrderList = reserveWorkOrderList;
	}

	public String getKcid() {
		return kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid;
	}

	public String getKcllid() {
		return kcllid;
	}

	public void setKcllid(String kcllid) {
		this.kcllid = kcllid;
	}

	public String getKcidOld() {
		return kcidOld;
	}

	public void setKcidOld(String kcidOld) {
		this.kcidOld = kcidOld;
	}

	public User getSqr() {
		return sqr;
	}

	public void setSqr(User sqr) {
		this.sqr = sqr;
	}

	public User getSpr() {
		return spr;
	}

	public void setSpr(User spr) {
		this.spr = spr;
	}

	public Integer getNoRead() {
		return noRead;
	}

	public void setNoRead(Integer noRead) {
		this.noRead = noRead;
	}

	public Integer getReadAll() {
		return readAll;
	}

	public void setReadAll(Integer readAll) {
		this.readAll = readAll;
	}

	public String getGlgd() {
		return glgd;
	}

	public void setGlgd(String glgd) {
		this.glgd = glgd;
	}

	public User getJsr() {
		return jsr;
	}

	public void setJsr(User jsr) {
		this.jsr = jsr;
	}

	public String getTdmc() {
		return tdmc;
	}

	public void setTdmc(String tdmc) {
		this.tdmc = tdmc;
	}

}