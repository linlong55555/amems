package com.eray.thjw.project2.po;

/**
 * @Description b_g2_01102 维修方案生效区-维修项目关系
 * @CreateTime 2017年8月21日 下午7:25:26
 * @CreateBy 韩武
 */
public class ProjectEffective {
    private String id;

    private String mainid;

    private String jx;

    private String wxxmid;

    private String rwh;

    private String gkid;

    private String gkbh;

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

    public String getGkid() {
        return gkid;
    }

    public void setGkid(String gkid) {
        this.gkid = gkid == null ? null : gkid.trim();
    }

    public String getGkbh() {
        return gkbh;
    }

    public void setGkbh(String gkbh) {
        this.gkbh = gkbh == null ? null : gkbh.trim();
    }
}