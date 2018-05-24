package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.system.po.WorkGroup;

import enu.ordersource.OrderSourceEnum;

public class JobCard extends BizEntity{
    private String id;

    private String gdjcid;

    private String djbh;

    private String nbxh;

    private String gdbh;
    
    private String wxfabh;

    private String zy;
    
    private String zyName;

    private BigDecimal jhgsRs;

    private BigDecimal jhgsXss;

    private String jx;

    private String gzzt;

    private String cjgk;

    private Integer yxx;

    private String bz;

    private String shbmid;

    private String shyj;

    private String shrid;

    private Date shsj;

    private String pfbmid;

    private String pfyj;

    private String pfrid;

    private Date pfsj;

    private String zddwid;

    private String zdrid;

    private Date zdsj;

    private Integer zt;
    
    private String ztText;

    private String dprtcode;
    
    private String username;
    
    private String realname;
    
    private String shrrealname;

    private String pfrrealname;
    
    private String wxfazwms;

    private String djxmzwms;

    private String djxmid;
    
    private String oldgdbh;
    
    private String pc;
    
    private String cksc;
    
    private String ckgk;
    
    private String gzzw;
    
    private String bcwj;
    
    private String zjh;
    
    private String gzdd;
    
    private String bb;
    
    private String zjhZwms;
    
    private String wxfabb;
    
    private User shr_user;
    
    private User pfr_user;
    
    private User zdr_user;

    private User zdjs_user;
    
    private String zdjsrid;
    
    private Date zdjsrq;
    
    private String zdjsyy;
    
    private String gzzId; //工作组id
    
    private Integer gklx;	//工卡类型
    
    private WorkGroup gzz;	//工作组
    
   //航材
    private List<WOAirMaterial> woAirMaterialList;
    
    //工作内容
    private List<WOJobContent> woJobContentList;
    
    //工单附件
    private List<WOJobEnclosure> woJobenclosureList;
    
    //相关工卡
    private List<NonWOCard> nonwocardList;
    
  //航材(旧)
    private List<String> oldWoAirMaterialList;
    
    //工作内容(旧)
    private List<String> oldWoJobContentList;
    
    //工单附件(旧)
    private List<String> oldWoJobenclosureList;
    
    //相关工卡(旧)
    private List<String> oldNonwocardList;
    
    private String scmsYw;
    
    private String scmsZw;
    
    private String djgznrid;
    
    private String djxmmc;
    
    private String wxfamc;
    
    private List<String> xdtzsid;
    
    
	public List<String> getXdtzsid() {
		return xdtzsid;
	}

	public void setXdtzsid(List<String> xdtzsid) {
		this.xdtzsid = xdtzsid;
	}

	public String getDjxmmc() {
		return djxmmc;
	}

	public void setDjxmmc(String djxmmc) {
		this.djxmmc = djxmmc;
	}

	public String getWxfamc() {
		return wxfamc;
	}

	public void setWxfamc(String wxfamc) {
		this.wxfamc = wxfamc;
	}

	public String getDjgznrid() {
		return djgznrid;
	}

	public void setDjgznrid(String djgznrid) {
		this.djgznrid = djgznrid;
	}

	public String getScmsYw() {
		return scmsYw;
	}

	public void setScmsYw(String scmsYw) {
		this.scmsYw = scmsYw;
	}

	public String getScmsZw() {
		return scmsZw;
	}

	public void setScmsZw(String scmsZw) {
		this.scmsZw = scmsZw;
	}

	public Integer getGklx() {
		return gklx;
	}

	public void setGklx(Integer gklx) {
		this.gklx = gklx;
	}

	public WorkGroup getGzz() {
		return gzz;
	}

	public void setGzz(WorkGroup gzz) {
		this.gzz = gzz;
	}

	public String getGzzId() {
		return gzzId;
	}

	public void setGzzId(String gzzId) {
		this.gzzId = gzzId;
	}

	public String getZyName() {
		return zyName;
	}

	public void setZyName(String zyName) {
		this.zyName = zyName;
	}

	public User getZdjs_user() {
		return zdjs_user;
	}

	public void setZdjs_user(User zdjs_user) {
		this.zdjs_user = zdjs_user;
	}

	public String getZtText() {
		return zt==null?"":OrderSourceEnum.getName(zt);
	}

	public void setZtText(String ztText) {
		this.ztText = ztText;
	}

	public User getZdr_user() {
		return zdr_user;
	}

	public void setZdr_user(User zdr_user) {
		this.zdr_user = zdr_user;
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

	public List<String> getOldWoAirMaterialList() {
		return oldWoAirMaterialList;
	}

	public void setOldWoAirMaterialList(List<String> oldWoAirMaterialList) {
		this.oldWoAirMaterialList = oldWoAirMaterialList;
	}

	public List<String> getOldWoJobContentList() {
		return oldWoJobContentList;
	}

	public void setOldWoJobContentList(List<String> oldWoJobContentList) {
		this.oldWoJobContentList = oldWoJobContentList;
	}

	public List<String> getOldWoJobenclosureList() {
		return oldWoJobenclosureList;
	}

	public void setOldWoJobenclosureList(List<String> oldWoJobenclosureList) {
		this.oldWoJobenclosureList = oldWoJobenclosureList;
	}

	public List<String> getOldNonwocardList() {
		return oldNonwocardList;
	}

	public void setOldNonwocardList(List<String> oldNonwocardList) {
		this.oldNonwocardList = oldNonwocardList;
	}

	public String getWxfabb() {
		return wxfabb;
	}

	public void setWxfabb(String wxfabb) {
		this.wxfabb = wxfabb;
	}

	public String getZjhZwms() {
		return zjhZwms;
	}

	public void setZjhZwms(String zjhZwms) {
		this.zjhZwms = zjhZwms;
	}

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	public String getCksc() {
		return cksc;
	}

	public void setCksc(String cksc) {
		this.cksc = cksc;
	}

	public String getCkgk() {
		return ckgk;
	}

	public void setCkgk(String ckgk) {
		this.ckgk = ckgk;
	}

	public String getGzzw() {
		return gzzw;
	}

	public void setGzzw(String gzzw) {
		this.gzzw = gzzw;
	}

	public String getBcwj() {
		return bcwj;
	}

	public void setBcwj(String bcwj) {
		this.bcwj = bcwj;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}

	public String getGzdd() {
		return gzdd;
	}

	public void setGzdd(String gzdd) {
		this.gzdd = gzdd;
	}

	public String getOldgdbh() {
		return oldgdbh;
	}

	public void setOldgdbh(String oldgdbh) {
		this.oldgdbh = oldgdbh;
	}

	public String getDjxmid() {
		return djxmid;
	}

	public void setDjxmid(String djxmid) {
		this.djxmid = djxmid;
	}

	public String getDjxmzwms() {
		return djxmzwms;
	}

	public void setDjxmzwms(String djxmzwms) {
		this.djxmzwms = djxmzwms;
	}

	public String getWxfazwms() {
		return wxfazwms;
	}

	public void setWxfazwms(String wxfazwms) {
		this.wxfazwms = wxfazwms;
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

	public List<NonWOCard> getNonwocardList() {
		return nonwocardList;
	}

	public void setNonwocardList(List<NonWOCard> nonwocardList) {
		this.nonwocardList = nonwocardList;
	}

	public List<WOAirMaterial> getWoAirMaterialList() {
		return woAirMaterialList;
	}

	public void setWoAirMaterialList(List<WOAirMaterial> woAirMaterialList) {
		this.woAirMaterialList = woAirMaterialList;
	}

	public List<WOJobContent> getWoJobContentList() {
		return woJobContentList;
	}

	public void setWoJobContentList(List<WOJobContent> woJobContentList) {
		this.woJobContentList = woJobContentList;
	}

	public List<WOJobEnclosure> getWoJobenclosureList() {
		return woJobenclosureList;
	}

	public void setWoJobenclosureList(List<WOJobEnclosure> woJobenclosureList) {
		this.woJobenclosureList = woJobenclosureList;
	}

	public String getWxfabh() {
		return wxfabh;
	}

	public void setWxfabh(String wxfabh) {
		this.wxfabh = wxfabh;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGdjcid() {
        return gdjcid;
    }

    public void setGdjcid(String gdjcid) {
        this.gdjcid = gdjcid == null ? null : gdjcid.trim();
    }

    public String getDjbh() {
        return djbh;
    }

    public void setDjbh(String djbh) {
        this.djbh = djbh == null ? null : djbh.trim();
    }

    public String getNbxh() {
        return nbxh;
    }

    public void setNbxh(String nbxh) {
        this.nbxh = nbxh == null ? null : nbxh.trim();
    }

    public String getGdbh() {
        return gdbh;
    }

    public void setGdbh(String gdbh) {
        this.gdbh = gdbh == null ? null : gdbh.trim();
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
    }

	public BigDecimal getJhgsRs() {
		if(jhgsRs==null){
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

    public String getJx() {
        return jx;
    }
    
    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public String getGzzt() {
        return gzzt;
    }

    public void setGzzt(String gzzt) {
        this.gzzt = gzzt == null ? null : gzzt.trim();
    }

    public String getCjgk() {
        return cjgk;
    }

    public void setCjgk(String cjgk) {
        this.cjgk = cjgk == null ? null : cjgk.trim();
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

    public String getShbmid() {
        return shbmid;
    }

    public void setShbmid(String shbmid) {
        this.shbmid = shbmid == null ? null : shbmid.trim();
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj == null ? null : shyj.trim();
    }

    public String getShrid() {
        return shrid;
    }

    public void setShrid(String shrid) {
        this.shrid = shrid == null ? null : shrid.trim();
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
        this.pfbmid = pfbmid == null ? null : pfbmid.trim();
    }

    public String getPfyj() {
        return pfyj;
    }

    public void setPfyj(String pfyj) {
        this.pfyj = pfyj == null ? null : pfyj.trim();
    }

    public String getPfrid() {
        return pfrid;
    }

    public void setPfrid(String pfrid) {
        this.pfrid = pfrid == null ? null : pfrid.trim();
    }

    public Date getPfsj() {
        return pfsj;
    }

    public void setPfsj(Date pfsj) {
        this.pfsj = pfsj;
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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }
}