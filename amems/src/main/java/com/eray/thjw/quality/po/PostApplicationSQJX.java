package com.eray.thjw.quality.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * @Description b_z_00503 岗位授权-申请机型
 * @CreateTime 2018-1-25 下午3:30:16
 * @CreateBy 刘兵
 */
public class PostApplicationSQJX extends BizEntity{
    private String id;

    private String mainid;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String fjjx;

    private String lb;

    private Date yxqKs;

    private Date yxqJs;

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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
    }

    public String getLb() {
        return lb;
    }

    public void setLb(String lb) {
        this.lb = lb == null ? null : lb.trim();
    }

    public Date getYxqKs() {
        return yxqKs;
    }

    public void setYxqKs(Date yxqKs) {
        this.yxqKs = yxqKs;
    }

    public Date getYxqJs() {
        return yxqJs;
    }

    public void setYxqJs(Date yxqJs) {
        this.yxqJs = yxqJs;
    }
}