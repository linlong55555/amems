package com.eray.thjw.training.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description b_p_00801 教员信息-授权课程
 * @CreateTime 2017年9月25日 下午4:35:28
 * @CreateBy 胡才秋
 */
public class FacultyCourse extends BizEntity{
    private String id;

    private String mainid;

    private String kcid;

    private Date sqksrq;

    private Date sqjsrq;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

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

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }

    public Date getSqksrq() {
        return sqksrq;
    }

    public void setSqksrq(Date sqksrq) {
        this.sqksrq = sqksrq;
    }

    public Date getSqjsrq() {
        return sqjsrq;
    }

    public void setSqjsrq(Date sqjsrq) {
        this.sqjsrq = sqjsrq;
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
        this.whbmid = whbmid == null ? null : whbmid.trim();
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