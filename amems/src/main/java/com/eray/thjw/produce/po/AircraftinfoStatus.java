package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * 
 * @Description b_s2_911 飞机状态数据
 * @CreateTime 2017年10月11日 上午10:47:08
 * @CreateBy 朱超
 */
@SuppressWarnings("serial")
public class AircraftinfoStatus extends BaseEntity {
    private String id;

    private String fjzch;

    private String dprtcode;

    private Integer wz;

    private String zjqdid;

    private String jh;

    private String xlh;

    private String jklbh;

    private String jkflbh;

    private Integer ljz;

    private Integer csz;

    private Date whsj;

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Integer getWz() {
        return wz;
    }

    public void setWz(Integer wz) {
        this.wz = wz;
    }

    public String getZjqdid() {
        return zjqdid;
    }

    public void setZjqdid(String zjqdid) {
        this.zjqdid = zjqdid == null ? null : zjqdid.trim();
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh == null ? null : jh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
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

    public Integer getLjz() {
        return ljz;
    }

    public void setLjz(Integer ljz) {
        this.ljz = ljz;
    }

    public Integer getCsz() {
        return csz;
    }

    public void setCsz(Integer csz) {
        this.csz = csz;
    }

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }
}