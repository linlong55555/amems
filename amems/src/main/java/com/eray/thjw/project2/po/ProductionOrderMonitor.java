package com.eray.thjw.project2.po;

import java.util.Date;

/**
 * @Description 生产指令监控项 b_g2_01401
 * @CreateTime 2018年4月28日 上午9:59:31
 * @CreateBy 徐勇
 */
public class ProductionOrderMonitor {
    private String id;

    private String mainid;

    private String jklbh;

    private String jkflbh;

    private String scz;

    private Integer dwScz;

    private String zqz;

    private Integer dwZqz;

    private String ydzQ;

    private Integer ydzQdw;

    private String ydzH;

    private Integer ydzHdw;

    private Integer zt;

    private String whbmid;

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

    public String getJklbh() {
        return jklbh;
    }

    public void setJklbh(String jklbh) {
        this.jklbh = jklbh == null ? null : jklbh.trim();
    }

    public String getJkflbh() {
        return jkflbh;
    }

    public void setJkflbh(String jkflbh) {
        this.jkflbh = jkflbh == null ? null : jkflbh.trim();
    }

    public String getScz() {
        return scz;
    }

    public void setScz(String scz) {
        this.scz = scz == null ? null : scz.trim();
    }

    public Integer getDwScz() {
        return dwScz;
    }

    public void setDwScz(Integer dwScz) {
        this.dwScz = dwScz;
    }

    public String getZqz() {
        return zqz;
    }

    public void setZqz(String zqz) {
        this.zqz = zqz == null ? null : zqz.trim();
    }

    public Integer getDwZqz() {
        return dwZqz;
    }

    public void setDwZqz(Integer dwZqz) {
        this.dwZqz = dwZqz;
    }

    public String getYdzQ() {
        return ydzQ;
    }

    public void setYdzQ(String ydzQ) {
        this.ydzQ = ydzQ == null ? null : ydzQ.trim();
    }

    public Integer getYdzQdw() {
        return ydzQdw;
    }

    public void setYdzQdw(Integer ydzQdw) {
        this.ydzQdw = ydzQdw;
    }

    public String getYdzH() {
        return ydzH;
    }

    public void setYdzH(String ydzH) {
        this.ydzH = ydzH == null ? null : ydzH.trim();
    }

    public Integer getYdzHdw() {
        return ydzHdw;
    }

    public void setYdzHdw(Integer ydzHdw) {
        this.ydzHdw = ydzHdw;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
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
}