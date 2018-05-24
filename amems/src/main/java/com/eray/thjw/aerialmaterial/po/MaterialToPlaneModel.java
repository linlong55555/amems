package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
/**
 * @author liub
 * @description 航材对应机型关系表D_00801
 * @develop date 2016.09.12
 */
public class MaterialToPlaneModel {
    private String id;

    private String mainid;

    private String fjjx;

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

    public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
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