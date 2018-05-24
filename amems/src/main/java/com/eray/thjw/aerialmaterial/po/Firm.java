package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.system.po.ContactPerson;

/**
 * 
 * D_015 厂商表
 * @author liub
 *
 */
public class Firm extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String gyslb;

    private String gysbm;

    private String gysmc;

    private String dz;

    private String pzh;

    private Date sqkssj;

    private Date sqjssj;

    private String sqfw;

    private String zssm;

    private String bz;

    private Integer zt;

    private String cjrid;

    private Date cjsj;
    
    private String gysjc;
    
    private String bqydm;
    
    private List<ContactPerson> contactPersonList;//联系人
    
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

    public String getGyslb() {
        return gyslb;
    }

    public void setGyslb(String gyslb) {
        this.gyslb = gyslb == null ? null : gyslb.trim();
    }

    public String getGysbm() {
        return gysbm;
    }

    public void setGysbm(String gysbm) {
        this.gysbm = gysbm == null ? null : gysbm.trim();
    }

    public String getGysmc() {
        return gysmc;
    }

    public void setGysmc(String gysmc) {
        this.gysmc = gysmc == null ? null : gysmc.trim();
    }

    public String getDz() {
        return dz;
    }

    public void setDz(String dz) {
        this.dz = dz == null ? null : dz.trim();
    }

    public String getPzh() {
        return pzh;
    }

    public void setPzh(String pzh) {
        this.pzh = pzh == null ? null : pzh.trim();
    }

    public Date getSqkssj() {
        return sqkssj;
    }

    public void setSqkssj(Date sqkssj) {
        this.sqkssj = sqkssj;
    }

    public Date getSqjssj() {
        return sqjssj;
    }

    public void setSqjssj(Date sqjssj) {
        this.sqjssj = sqjssj;
    }

    public String getSqfw() {
        return sqfw;
    }

    public void setSqfw(String sqfw) {
        this.sqfw = sqfw == null ? null : sqfw.trim();
    }

    public String getZssm() {
        return zssm;
    }

    public void setZssm(String zssm) {
        this.zssm = zssm == null ? null : zssm.trim();
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

    public String getCjrid() {
        return cjrid;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid == null ? null : cjrid.trim();
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

	public List<ContactPerson> getContactPersonList() {
		return contactPersonList;
	}

	public void setContactPersonList(List<ContactPerson> contactPersonList) {
		this.contactPersonList = contactPersonList;
	}

	public String getGysjc() {
		return gysjc;
	}

	public void setGysjc(String gysjc) {
		this.gysjc = gysjc;
	}

	public String getBqydm() {
		return bqydm;
	}

	public void setBqydm(String bqydm) {
		this.bqydm = bqydm;
	}
    
}