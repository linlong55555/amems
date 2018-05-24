package com.eray.thjw.training.po;

/**
 * b_p_004 人员最近培训记录 
 * @author Admin_PC
 *
 */
public class PersonnelRecentTrainingRecord {
    private String id;

    private String dprtcode;

    private String kcid;

    private String wxrydaid;

    private String pxjlid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }

    public String getWxrydaid() {
        return wxrydaid;
    }

    public void setWxrydaid(String wxrydaid) {
        this.wxrydaid = wxrydaid == null ? null : wxrydaid.trim();
    }

    public String getPxjlid() {
        return pxjlid;
    }

    public void setPxjlid(String pxjlid) {
        this.pxjlid = pxjlid == null ? null : pxjlid.trim();
    }
}