package com.eray.thjw.quality.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
/**
 * 
 * @Description d_025 流程记录
 * @CreateTime 2018年1月4日 上午10:59:21
 * @CreateBy 林龙
 */
public class ProcessRecord  extends BizEntity{
    private String id;

    private String mainid;

    private String dprtcode;

    private String czrmc;

    private String bmmc;

    private String czsm;

    private Date czsj;

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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getCzrmc() {
        return czrmc;
    }

    public void setCzrmc(String czrmc) {
        this.czrmc = czrmc == null ? null : czrmc.trim();
    }

    public String getBmmc() {
        return bmmc;
    }

    public void setBmmc(String bmmc) {
        this.bmmc = bmmc == null ? null : bmmc.trim();
    }

    public String getCzsm() {
        return czsm;
    }

    public void setCzsm(String czsm) {
        this.czsm = czsm == null ? null : czsm.trim();
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }
}