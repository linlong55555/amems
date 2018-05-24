package com.eray.thjw.po;

import java.util.Date;
import java.util.List;

import enu.ordersource.OrderSourceEnum;
//对应表b_g_002，技术通告
//sj
public class Annunciate extends BaseEntity{
	
    private String id;

    private String jstgh;

    private String ckzl;

    private String zhut;

    private String nr;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private Integer zt;
    
    private String ztText;

    private Integer dyzt;

    private String dprtcode;
    
    private String dprtname;
    
    private String tnum;
    
    private String snum;
    
    private String realname;
    
    private String username;
    
    private List<OrderSource> orderSourceList;
    
    private List<Send> sendList;
    
    private String pgdh;
    
    private String bz;

    private Date tgqx;
    
    private Date sxrq;

    private String zdjsrid;

    private Date zdjsrq;

    private String shbmid;

    private String shyj;

    private String shrid;

    private Date shsj;

    private String pfbmid;

    private String pfyj;

    private String pfrid;

    private Date pfsj;

    private String zdjsyy;
    
    private String shrrealname;
    
    private String pfrrealname;
    
    private String keyword;
    
    private String zzjg;
    
    private String bb;
    
    private User shr_user;
    
    private User pfr_user;

    private User zdr_user;

    private User jsr_user;
    
    private Department jg_dprt;
    
    private Department bm_dprt;
    
    private String Pgjg;
    
    private List<OrderAttachment> orderAttachmentList;
    
    private List<String > oldOrderAttachmentIds;
    
    private Integer syts;
    
	public User getJsr_user() {
		return jsr_user;
	}

	public void setJsr_user(User jsr_user) {
		this.jsr_user = jsr_user;
	}

	public Integer getSyts() {
		return syts;
	}

	public void setSyts(Integer syts) {
		this.syts = syts;
	}

	public List<String> getOldOrderAttachmentIds() {
		return oldOrderAttachmentIds;
	}

	public void setOldOrderAttachmentIds(List<String> oldOrderAttachmentIds) {
		this.oldOrderAttachmentIds = oldOrderAttachmentIds;
	}

	public List<OrderAttachment> getOrderAttachmentList() {
		return orderAttachmentList;
	}

	public void setOrderAttachmentList(List<OrderAttachment> orderAttachmentList) {
		this.orderAttachmentList = orderAttachmentList;
	}

	public String getZtText() {
		return zt==null?"":OrderSourceEnum.getName(zt);
	}

	public void setZtText(String ztText) {
		this.ztText = ztText;
	}

	public String getPgjg() {
		return Pgjg;
	}

	public void setPgjg(String pgjg) {
		Pgjg = pgjg;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public Department getBm_dprt() {
		return bm_dprt;
	}

	public void setBm_dprt(Department bm_dprt) {
		this.bm_dprt = bm_dprt;
	}

	public User getZdr_user() {
		return zdr_user;
	}

	public void setZdr_user(User zdr_user) {
		this.zdr_user = zdr_user;
	}

	public User getShr_user() {
		return shr_user;
	}

	public void setShr_user(User shr_user) {
		this.shr_user = shr_user;
	}

	public User getPfr_user() {
		return pfr_user;
	}

	public void setPfr_user(User pfr_user) {
		this.pfr_user = pfr_user;
	}
    
    
    
    
    public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	public Date getSxrq() {
		return sxrq;
	}

	public void setSxrq(Date sxrq) {
		this.sxrq = sxrq;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getZzjg() {
		return zzjg;
	}

	public void setZzjg(String zzjg) {
		this.zzjg = zzjg;
	}

	public List<Send> getSendList() {
		return sendList;
	}

	public void setSendList(List<Send> sendList) {
		this.sendList = sendList;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getShrrealname() {
		return shrrealname;
	}

	public void setShrrealname(String shrrealname) {
		this.shrrealname = shrrealname;
	}

	public String getPfrrealname() {
		return pfrrealname;
	}

	public void setPfrrealname(String pfrrealname) {
		this.pfrrealname = pfrrealname;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Date getTgqx() {
		return tgqx;
	}

	public void setTgqx(Date tgqx) {
		this.tgqx = tgqx;
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

	public Date getPfsj() {
		return pfsj;
	}

	public void setPfsj(Date pfsj) {
		this.pfsj = pfsj;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}

	//TODO 测试
    private String code = "1";
   
    private String codeName;

    public String getPgdh() {
		return pgdh;
	}

	public void setPgdh(String pgdh) {
		this.pgdh = pgdh;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJstgh() {
        return jstgh;
    }

    public void setJstgh(String jstgh) {
        this.jstgh = jstgh == null ? null : jstgh.trim();
    }

    public String getCkzl() {
        return ckzl;
    }

    public void setCkzl(String ckzl) {
        this.ckzl = ckzl == null ? null : ckzl.trim();
    }

    public String getZhut() {
        return zhut;
    }

    public void setZhut(String zhut) {
        this.zhut = zhut == null ? null : zhut.trim();
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr == null ? null : nr.trim();
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


    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getDyzt() {
		return dyzt;
	}

	public void setDyzt(Integer dyzt) {
		this.dyzt = dyzt;
	}

	public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

	public List<OrderSource> getOrderSourceList() {
		return orderSourceList;
	}

	public void setOrderSourceList(List<OrderSource> orderSourceList) {
		this.orderSourceList = orderSourceList;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

	public String getTnum() {
		return tnum;
	}

	public void setTnum(String tnum) {
		this.tnum = tnum;
	}

	public String getSnum() {
		return snum;
	}

	public void setSnum(String snum) {
		this.snum = snum;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}


	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

	@Override
	public String toString() {
		return "Annunciate [pagination=" + pagination + ", id=" + id
				+ ", jstgh=" + jstgh + ", ckzl=" + ckzl + ", zhut=" + zhut
				+ ", nr=" + nr + ", zdbmid=" + zdbmid + ", zdrid=" + zdrid
				+ ", zdsj=" + zdsj + ", zt=" + zt + ", dyzt=" + dyzt
				+ ", dprtcode=" + dprtcode + ", dprtname=" + dprtname
				+ ", tnum=" + tnum + ", snum=" + snum + ", realname="
				+ realname + ", orderSourceList=" + orderSourceList + ", pgdh="
				+ pgdh + ", bz=" + bz + ", tgqx=" + tgqx + ", zdjsrid="
				+ zdjsrid + ", zdjsrq=" + zdjsrq + ", shbmid=" + shbmid
				+ ", shyj=" + shyj + ", shrid=" + shrid + ", shsj=" + shsj
				+ ", pfbmid=" + pfbmid + ", pfyj=" + pfyj + ", pfrid=" + pfrid
				+ ", pfsj=" + pfsj + ", zdjsyy=" + zdjsyy + ", shrrealname="
				+ shrrealname + ", pfrrealname=" + pfrrealname + ", code="
				+ code + ", codeName=" + codeName + "]";
	}

	





    
    
}