package com.eray.thjw.quality.po;

/**
 * 
 * @Description b_z_008 年度审核计划与审核通知单关系
 * @CreateTime 2018-1-15 下午4:02:14
 * @CreateBy 孙霁
 */
public class PlanToNotice {
    private String ndshjhid;

    private String shtzdid;

    public String getNdshjhid() {
        return ndshjhid;
    }

    public void setNdshjhid(String ndshjhid) {
        this.ndshjhid = ndshjhid == null ? null : ndshjhid.trim();
    }

    public String getShtzdid() {
        return shtzdid;
    }

    public void setShtzdid(String shtzdid) {
        this.shtzdid = shtzdid == null ? null : shtzdid.trim();
    }
}