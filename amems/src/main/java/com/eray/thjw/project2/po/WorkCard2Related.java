package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.FixChapter;

/**
 * @Description B_G2_01301 工卡-关联工卡
 * @CreateTime 2017-8-25 下午5:21:19
 * @CreateBy 刘兵
 */
public class WorkCard2Related extends BaseEntity {
    private String id;

    private String mainid;

    private String jx;

    private String gkh;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    /** 工卡 */
    private WorkCard workCard;

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

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public String getGkh() {
        return gkh;
    }

    public void setGkh(String gkh) {
        this.gkh = gkh == null ? null : gkh.trim();
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

	public WorkCard getWorkCard() {
		return workCard;
	}

	public void setWorkCard(WorkCard workCard) {
		this.workCard = workCard;
	}

	@Override
	public String toString() {
		return "WorkCard2Related [id=" + id + ", mainid=" + mainid + ", jx="
				+ jx + ", gkh=" + gkh + ", zt=" + zt + ", whbmid=" + whbmid
				+ ", whrid=" + whrid + ", whsj=" + whsj + ", workCard="
				+ workCard + ", pagination=" + pagination + ", getId()="
				+ getId() + ", getMainid()=" + getMainid() + ", getJx()="
				+ getJx() + ", getGkh()=" + getGkh() + ", getZt()=" + getZt()
				+ ", getWhbmid()=" + getWhbmid() + ", getWhrid()=" + getWhrid()
				+ ", getWhsj()=" + getWhsj() + ", getWorkCard()="
				+ getWorkCard() + ", getPagination()=" + getPagination()
				+ ", getParamsMap()=" + getParamsMap() + ", getCzls()="
				+ getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}