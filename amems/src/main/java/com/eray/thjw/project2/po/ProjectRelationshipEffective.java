package com.eray.thjw.project2.po;

/**
 * @Description b_g2_01103 维修方案生效区-相关维修项目关系
 * @CreateTime 2017年8月21日 下午7:25:44
 * @CreateBy 韩武
 */
public class ProjectRelationshipEffective {
    private String id;

    private String mainid;

    private String jx;

    private String wxxmid;

    private String rwh;

    private String xgwxxmid;

    private String xgrwh;

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

    public String getWxxmid() {
        return wxxmid;
    }

    public void setWxxmid(String wxxmid) {
        this.wxxmid = wxxmid == null ? null : wxxmid.trim();
    }

    public String getRwh() {
        return rwh;
    }

    public void setRwh(String rwh) {
        this.rwh = rwh == null ? null : rwh.trim();
    }

    public String getXgwxxmid() {
        return xgwxxmid;
    }

    public void setXgwxxmid(String xgwxxmid) {
        this.xgwxxmid = xgwxxmid == null ? null : xgwxxmid.trim();
    }

    public String getXgrwh() {
        return xgrwh;
    }

    public void setXgrwh(String xgrwh) {
        this.xgrwh = xgrwh == null ? null : xgrwh.trim();
    }
}