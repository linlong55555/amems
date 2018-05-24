package com.eray.thjw.po;

import java.math.BigDecimal;

public class ScheduledCheckCard extends BizEntity {
	
    private String id;

    /**
     * 工单基础id
     */
    private String gdjcid;

    /**
     * 定检工作id
     */
    private String djgzid;

    /**
     * 工单编号
     */
    private String gdbh;

    /**
     * 专业。来源于数据字典
     * 
     */
    private String zy;

    /**
     * 计划工时-人数
     */
    private BigDecimal jhgsRs;

    /**
     * 计划工时-小时数
     */
    private BigDecimal jhgsXss;

    /**
     * 厂家工卡
     */
    private String cjgk;

    /**
     * 有效性：0无效、1有效
     */
    private Integer yxx;

    /**
     * 备注
     */
    private String bz;

    /**
     * 制单单位id。关联组织结构表
     */
    private String zddwid;

    

    /**
     * 状态：1保存、2提交、8、作废、9关闭
     */
    private Short zt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGdjcid() {
        return gdjcid;
    }

    public void setGdjcid(String gdjcid) {
        this.gdjcid = gdjcid == null ? null : gdjcid.trim();
    }

    public String getDjgzid() {
        return djgzid;
    }

    public void setDjgzid(String djgzid) {
        this.djgzid = djgzid == null ? null : djgzid.trim();
    }

    public String getGdbh() {
        return gdbh;
    }

    public void setGdbh(String gdbh) {
        this.gdbh = gdbh == null ? null : gdbh.trim();
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
    }

    public BigDecimal getJhgsRs() {
        return jhgsRs;
    }

    public void setJhgsRs(BigDecimal jhgsRs) {
        this.jhgsRs = jhgsRs;
    }

    public BigDecimal getJhgsXss() {
        return jhgsXss;
    }

    public void setJhgsXss(BigDecimal jhgsXss) {
        this.jhgsXss = jhgsXss;
    }

    public String getCjgk() {
        return cjgk;
    }

    public void setCjgk(String cjgk) {
        this.cjgk = cjgk == null ? null : cjgk.trim();
    }

    public Integer getYxx() {
        return yxx;
    }

    public void setYxx(Integer yxx) {
        this.yxx = yxx;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getZddwid() {
        return zddwid;
    }

    public void setZddwid(String zddwid) {
        this.zddwid = zddwid == null ? null : zddwid.trim();
    }

    public Short getZt() {
        return zt;
    }

    public void setZt(Short zt) {
        this.zt = zt;
    }

}