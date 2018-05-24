package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;
/**
 * @Description b_g2_01302 工卡-适用单位
 * @CreateTime 2017-11-8 下午3:06:59
 * @CreateBy 刘兵
 */
public class ApplicableUnit extends BaseEntity {
    private String id;

    private String mainid;

    private String sydwid;

    private String sydw;

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

    public String getSydwid() {
        return sydwid;
    }

    public void setSydwid(String sydwid) {
        this.sydwid = sydwid == null ? null : sydwid.trim();
    }

    public String getSydw() {
        return sydw;
    }

    public void setSydw(String sydw) {
        this.sydw = sydw == null ? null : sydw.trim();
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

	@Override
	public String toString() {
		return "ApplicableUnit [id=" + id + ", mainid=" + mainid + ", sydwid="
				+ sydwid + ", sydw=" + sydw + ", zt=" + zt + ", whbmid="
				+ whbmid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", pagination=" + pagination + ", getId()=" + getId()
				+ ", getMainid()=" + getMainid() + ", getSydwid()="
				+ getSydwid() + ", getSydw()=" + getSydw() + ", getZt()="
				+ getZt() + ", getWhbmid()=" + getWhbmid() + ", getWhrid()="
				+ getWhrid() + ", getWhsj()=" + getWhsj()
				+ ", getPagination()=" + getPagination() + ", getParamsMap()="
				+ getParamsMap() + ", getCzls()=" + getCzls()
				+ ", getLogOperationEnum()=" + getLogOperationEnum()
				+ ", getZbid()=" + getZbid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}
    
    
}