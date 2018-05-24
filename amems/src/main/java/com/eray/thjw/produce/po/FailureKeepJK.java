package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * b_s2_01301 故障保留-监控
 * @Description 
 * @CreateTime 2018-1-29 下午8:18:07
 * @CreateBy 雷伟
 */
public class FailureKeepJK extends BizEntity{
	
	private static final long serialVersionUID = 1L;
	
    private String id;

    private String mainid;

    private String jklbh;

    private String jkflbh;

    private String jkz;

    private Integer jd;

    private Integer jkbs;

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

    public String getJkz() {
        return jkz;
    }

    public void setJkz(String jkz) {
        this.jkz = jkz == null ? null : jkz.trim();
    }

    public Integer getJd() {
        return jd;
    }

    public void setJd(Integer jd) {
        this.jd = jd;
    }

    public Integer getJkbs() {
        return jkbs;
    }

    public void setJkbs(Integer jkbs) {
        this.jkbs = jkbs;
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