package com.eray.thjw.produce.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.MaterialTool;

/**
 * 
 * @Description b_s2_012 缺陷保留
 * @CreateTime 2017年10月14日 下午3:34:15
 * @CreateBy 林龙
 */
public class DefectKeep extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String bldh;

    private String fjzch;

    private String sqrbmid;

    private String sqrid;

    private Date sqrq;

    private Integer bs145;

    private String lygdid;

    private String qxms;

    private Date xfqx;

    private String pzrbmid;

    private String pzrid;

    private String pzyj;

    private Date pzsj;

    private String yxxz;

    private String gzz;

    private Date gzrq;

    private String gbrbmid;

    private String gbrid;

    private Date gbsj;

    private String gbyy;

    private String gdid;

   private User whr; //维护人
    
    private Department dprt; //组织机构
    
    private List<Attachment> attachmentList; //d_011 附件表List

    private List<MaterialTool> materialToolList; //器材/工具list集合
    
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

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<MaterialTool> getMaterialToolList() {
		return materialToolList;
	}

	public void setMaterialToolList(List<MaterialTool> materialToolList) {
		this.materialToolList = materialToolList;
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

   

    public String getBldh() {
        return bldh;
    }

    public void setBldh(String bldh) {
        this.bldh = bldh == null ? null : bldh.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getSqrbmid() {
        return sqrbmid;
    }

    public void setSqrbmid(String sqrbmid) {
        this.sqrbmid = sqrbmid == null ? null : sqrbmid.trim();
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid == null ? null : sqrid.trim();
    }

    public Date getSqrq() {
        return sqrq;
    }

    public void setSqrq(Date sqrq) {
        this.sqrq = sqrq;
    }

    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getBs145() {
		return bs145;
	}

	public void setBs145(Integer bs145) {
		this.bs145 = bs145;
	}

	public String getLygdid() {
		return lygdid;
	}

	public void setLygdid(String lygdid) {
		this.lygdid = lygdid;
	}

	public String getQxms() {
        return qxms;
    }

    public void setQxms(String qxms) {
        this.qxms = qxms == null ? null : qxms.trim();
    }

    public Date getXfqx() {
        return xfqx;
    }

    public void setXfqx(Date xfqx) {
        this.xfqx = xfqx;
    }

    public String getPzrbmid() {
        return pzrbmid;
    }

    public void setPzrbmid(String pzrbmid) {
        this.pzrbmid = pzrbmid == null ? null : pzrbmid.trim();
    }

    public String getPzrid() {
        return pzrid;
    }

    public void setPzrid(String pzrid) {
        this.pzrid = pzrid == null ? null : pzrid.trim();
    }

    public String getPzyj() {
        return pzyj;
    }

    public void setPzyj(String pzyj) {
        this.pzyj = pzyj == null ? null : pzyj.trim();
    }

    public Date getPzsj() {
        return pzsj;
    }

    public void setPzsj(Date pzsj) {
        this.pzsj = pzsj;
    }

    public String getYxxz() {
        return yxxz;
    }

    public void setYxxz(String yxxz) {
        this.yxxz = yxxz == null ? null : yxxz.trim();
    }

    public String getGzz() {
        return gzz;
    }

    public void setGzz(String gzz) {
        this.gzz = gzz == null ? null : gzz.trim();
    }

    public Date getGzrq() {
        return gzrq;
    }

    public void setGzrq(Date gzrq) {
        this.gzrq = gzrq;
    }

    public String getGbrbmid() {
        return gbrbmid;
    }

    public void setGbrbmid(String gbrbmid) {
        this.gbrbmid = gbrbmid == null ? null : gbrbmid.trim();
    }

    public String getGbrid() {
        return gbrid;
    }

    public void setGbrid(String gbrid) {
        this.gbrid = gbrid == null ? null : gbrid.trim();
    }

    public Date getGbsj() {
        return gbsj;
    }

    public void setGbsj(Date gbsj) {
        this.gbsj = gbsj;
    }

    public String getGbyy() {
        return gbyy;
    }

    public void setGbyy(String gbyy) {
        this.gbyy = gbyy == null ? null : gbyy.trim();
    }

    public String getGdid() {
        return gdid;
    }

    public void setGdid(String gdid) {
        this.gdid = gdid == null ? null : gdid.trim();
    }
}