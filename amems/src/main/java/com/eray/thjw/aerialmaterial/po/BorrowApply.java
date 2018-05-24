package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * b_h_018 借入申请主表
 * @author xu.yong
 *
 */
public class BorrowApply extends BizEntity{
    private String id;

    private String dprtcode;

    private String sqdh;

    private String fjzch;

    private String jddxid;

    private String bz;

    private Integer zt;

    private String sqdwid;

    private String sqrid;
    
    private Date sqsj;
    
    private String zdjsrid;
    
    private Date zdjsrq;
    
    private String zdjsyy;
    
    private String zdbmid;
    
    private String zdrid;
    
    private String zdrs;//制单人
    
    private Date zdsj;
    
    private String sqr; //申请人
    
    private String zdjsr; //指定结束人
    
    private String jddxlx;//借调对象类型

    private String jddxms;//借调对象类型
    
    private String jddxbh;//借调对象编号
    
    private String fxDateBegin; //开始时间
    
    private String fxDateEnd;//结束时间
    
    private String userCode;//除自己
 
    /** 关系 start */
    private Secondment secondment;//借调对象
    
    private User sqrUser;//申请人
    /** 关系 end */
    
    private String ckdh;
    

    public String getZdjsr() {
		return zdjsr;
	}



	public void setZdjsr(String zdjsr) {
		this.zdjsr = zdjsr;
	}



	public String getUserCode() {
		return userCode;
	}



	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}



	public String getJddxbh() {
		return jddxbh;
	}



	public void setJddxbh(String jddxbh) {
		this.jddxbh = jddxbh;
	}



	public String getCkdh() {
		return ckdh;
	}



	public void setCkdh(String ckdh) {
		this.ckdh = ckdh;
	}

	private List<BorrowApplyDetail> reserveDetailList;
    

    private List<String> reserveDetailIdList;

  



	public List<String> getReserveDetailIdList() {
		return reserveDetailIdList;
	}



	public void setReserveDetailIdList(List<String> reserveDetailIdList) {
		this.reserveDetailIdList = reserveDetailIdList;
	}



	public List<BorrowApplyDetail> getReserveDetailList() {
		return reserveDetailList;
	}



	public void setReserveDetailList(List<BorrowApplyDetail> reserveDetailList) {
		this.reserveDetailList = reserveDetailList;
	}



	public String getId() {
        return id;
    }

   

	public String getZdrs() {
		return zdrs;
	}



	public void setZdrs(String zdrs) {
		this.zdrs = zdrs;
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

	public String getZdbmid() {
		return zdbmid;
	}

	public void setZdbmid(String zdbmid) {
		this.zdbmid = zdbmid;
	}

	public String getZdrid() {
		return zdrid;
	}

	public void setZdrid(String zdrid) {
		this.zdrid = zdrid;
	}

	public Date getZdsj() {
		return zdsj;
	}

	public void setZdsj(Date zdsj) {
		this.zdsj = zdsj;
	}

	public String getJddxms() {
		return jddxms;
	}

	public void setJddxms(String jddxms) {
		this.jddxms = jddxms;
	}

	public String getSqr() {
		return sqr;
	}

	public void setSqr(String sqr) {
		this.sqr = sqr;
	}

	public String getJddxlx() {
		return jddxlx;
	}

	public void setJddxlx(String jddxlx) {
		this.jddxlx = jddxlx;
	}

	public String getFxDateBegin() {
		return fxDateBegin;
	}

	public void setFxDateBegin(String fxDateBegin) {
		this.fxDateBegin = fxDateBegin;
	}

	public String getFxDateEnd() {
		return fxDateEnd;
	}

	public void setFxDateEnd(String fxDateEnd) {
		this.fxDateEnd = fxDateEnd;
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

    public String getSqdh() {
        return sqdh;
    }

    public void setSqdh(String sqdh) {
        this.sqdh = sqdh == null ? null : sqdh.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getJddxid() {
        return jddxid;
    }

    public void setJddxid(String jddxid) {
        this.jddxid = jddxid == null ? null : jddxid.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

   

    public Integer getZt() {
		return zt;
	}



	public void setZt(Integer zt) {
		this.zt = zt;
	}



	public String getSqdwid() {
        return sqdwid;
    }

    public void setSqdwid(String sqdwid) {
        this.sqdwid = sqdwid == null ? null : sqdwid.trim();
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

	public Secondment getSecondment() {
		return secondment;
	}

	public void setSecondment(Secondment secondment) {
		this.secondment = secondment;
	}

	public User getSqrUser() {
		return sqrUser;
	}

	public void setSqrUser(User sqrUser) {
		this.sqrUser = sqrUser;
	}
	
}