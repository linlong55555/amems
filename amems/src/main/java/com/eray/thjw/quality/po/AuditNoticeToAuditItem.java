package com.eray.thjw.quality.po;

public class AuditNoticeToAuditItem {
    private String shtzdid;

    private String shxmdid;

    public String getShtzdid() {
        return shtzdid;
    }

    public void setShtzdid(String shtzdid) {
        this.shtzdid = shtzdid == null ? null : shtzdid.trim();
    }

    public String getShxmdid() {
        return shxmdid;
    }

    public void setShxmdid(String shxmdid) {
        this.shxmdid = shxmdid == null ? null : shxmdid.trim();
    }
}