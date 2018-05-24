package com.eray.thjw.project.po;

import com.eray.thjw.po.BizEntity;

/**
 * @author liub
 * @description MEL更改单-修改依据B_G_01901
 */
public class MelChangeSheetAndBasis extends BizEntity{
    private String id;

    private String mainid;

    private String yjlx;

    private String yjnr;

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

    public String getYjlx() {
        return yjlx;
    }

    public void setYjlx(String yjlx) {
        this.yjlx = yjlx == null ? null : yjlx.trim();
    }

    public String getYjnr() {
        return yjnr;
    }

    public void setYjnr(String yjnr) {
        this.yjnr = yjnr == null ? null : yjnr.trim();
    }
}