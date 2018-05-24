package com.eray.thjw.flightdata.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.productionplan.po.LoadingList;

public class MountLoadingList extends BizEntity{
    private String id;

    private String mainid;

    private String zsZjqdid;

    private String fjzch;

    private Integer bjlx;

    private String jh;

    private String xlh;

    private String nbsbm;

    private String zjh;

    private String cjjh;

    private String zwmc;

    private String ywmc;

    private String bjgzjl;

    private Integer zjsl;

    private Integer wz;

    private Date scrq;

    private String bz;

    private Date azrq;

    private String azjldh;

    private Date ccrq;

    private String ccjldh;

    private Integer llklx;

    private String llkbh;

    private Integer kzlx;

    private Integer isDj;

    private String tsn;

    private String tso;

    private Integer cj;

    private String fjdid;

    private Date sxrq;

    private Integer zt;

    private Integer tbbs;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String dprtcode;

    private String xkzh;

    private String shzh;
    
    private String fjjx;
    
    private String zjh_show;
    
    private String pch;
    
    private LoadingList parent;	//父节点
    
    private List<MountSubcomponent> children;	//子节点
    
    private FixChapter fixChapter;	// 章节号
    
    private Integer zjslMax;
    
    private Integer isNew;

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

    public String getZsZjqdid() {
        return zsZjqdid;
    }

    public void setZsZjqdid(String zsZjqdid) {
        this.zsZjqdid = zsZjqdid == null ? null : zsZjqdid.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public Integer getBjlx() {
        return bjlx;
    }

    public void setBjlx(Integer bjlx) {
        this.bjlx = bjlx;
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh == null ? null : jh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public String getNbsbm() {
        return nbsbm;
    }

    public void setNbsbm(String nbsbm) {
        this.nbsbm = nbsbm == null ? null : nbsbm.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public String getCjjh() {
        return cjjh;
    }

    public void setCjjh(String cjjh) {
        this.cjjh = cjjh == null ? null : cjjh.trim();
    }

    public String getZwmc() {
        return zwmc;
    }

    public void setZwmc(String zwmc) {
        this.zwmc = zwmc == null ? null : zwmc.trim();
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc == null ? null : ywmc.trim();
    }

    public String getBjgzjl() {
        return bjgzjl;
    }

    public void setBjgzjl(String bjgzjl) {
        this.bjgzjl = bjgzjl == null ? null : bjgzjl.trim();
    }

    public Integer getZjsl() {
        return zjsl;
    }

    public void setZjsl(Integer zjsl) {
        this.zjsl = zjsl;
    }

    public Integer getWz() {
        return wz;
    }

    public void setWz(Integer wz) {
        this.wz = wz;
    }

    public Date getScrq() {
        return scrq;
    }

    public void setScrq(Date scrq) {
        this.scrq = scrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Date getAzrq() {
        return azrq;
    }

    public void setAzrq(Date azrq) {
        this.azrq = azrq;
    }

    public String getAzjldh() {
        return azjldh;
    }

    public void setAzjldh(String azjldh) {
        this.azjldh = azjldh == null ? null : azjldh.trim();
    }

    public Date getCcrq() {
        return ccrq;
    }

    public void setCcrq(Date ccrq) {
        this.ccrq = ccrq;
    }

    public String getCcjldh() {
        return ccjldh;
    }

    public void setCcjldh(String ccjldh) {
        this.ccjldh = ccjldh == null ? null : ccjldh.trim();
    }

    public Integer getLlklx() {
        return llklx;
    }

    public void setLlklx(Integer llklx) {
        this.llklx = llklx;
    }

    public String getLlkbh() {
        return llkbh;
    }

    public void setLlkbh(String llkbh) {
        this.llkbh = llkbh == null ? null : llkbh.trim();
    }

    public Integer getKzlx() {
        return kzlx;
    }

    public void setKzlx(Integer kzlx) {
        this.kzlx = kzlx;
    }

    public Integer getIsDj() {
        return isDj;
    }

    public void setIsDj(Integer isDj) {
        this.isDj = isDj;
    }

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn == null ? null : tsn.trim();
    }

    public String getTso() {
        return tso;
    }

    public void setTso(String tso) {
        this.tso = tso == null ? null : tso.trim();
    }

    public Integer getCj() {
        return cj;
    }

    public void setCj(Integer cj) {
        this.cj = cj;
    }

    public String getFjdid() {
        return fjdid;
    }

    public void setFjdid(String fjdid) {
        this.fjdid = fjdid == null ? null : fjdid.trim();
    }

    public Date getSxrq() {
        return sxrq;
    }

    public void setSxrq(Date sxrq) {
        this.sxrq = sxrq;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getTbbs() {
        return tbbs;
    }

    public void setTbbs(Integer tbbs) {
        this.tbbs = tbbs;
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

    public String getXkzh() {
        return xkzh;
    }

    public void setXkzh(String xkzh) {
        this.xkzh = xkzh == null ? null : xkzh.trim();
    }

    public String getShzh() {
        return shzh;
    }

    public void setShzh(String shzh) {
        this.shzh = shzh == null ? null : shzh.trim();
    }

	public String getFjjx() {
		return fjjx;
	}

	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}

	public String getZjh_show() {
		return zjh_show;
	}

	public void setZjh_show(String zjh_show) {
		this.zjh_show = zjh_show;
	}

	public String getPch() {
		return pch;
	}

	public void setPch(String pch) {
		this.pch = pch;
	}

	public List<MountSubcomponent> getChildren() {
		return children;
	}

	public void setChildren(List<MountSubcomponent> children) {
		this.children = children;
	}

	public LoadingList getParent() {
		return parent;
	}

	public void setParent(LoadingList parent) {
		this.parent = parent;
	}

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public Integer getZjslMax() {
		return zjslMax;
	}

	public void setZjslMax(Integer zjslMax) {
		this.zjslMax = zjslMax;
	}

	public Integer getIsNew() {
		return isNew;
	}

	public void setIsNew(Integer isNew) {
		this.isNew = isNew;
	}
	
}