package com.eray.thjw.produce.po;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description 航段信息-工作者
 * @CreateTime 2018年1月24日 上午9:54:36
 * @CreateBy 韩武
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSheetWorker extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	
    private String id;

    private String mainid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String gzzid;

    private String gzz;

    private BigDecimal ftgs;

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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getWhdwid() {
        return whdwid;
    }

    public void setWhdwid(String whdwid) {
        this.whdwid = whdwid == null ? null : whdwid.trim();
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

    public String getGzzid() {
        return gzzid;
    }

    public void setGzzid(String gzzid) {
        this.gzzid = gzzid == null ? null : gzzid.trim();
    }

    public String getGzz() {
        return gzz;
    }

    public void setGzz(String gzz) {
        this.gzz = gzz == null ? null : gzz.trim();
    }

    public BigDecimal getFtgs() {
        return ftgs;
    }

    public void setFtgs(BigDecimal ftgs) {
        this.ftgs = ftgs;
    }
}