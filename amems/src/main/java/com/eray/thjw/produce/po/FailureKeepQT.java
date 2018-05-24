package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * b_s2_01302 故障保留-其他
 * @Description 
 * @CreateTime 2018-1-29 下午8:22:01
 * @CreateBy 雷伟
 */
public class FailureKeepQT extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String mainid;

    private String lx;

    private String sz;

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

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx == null ? null : lx.trim();
    }

    public String getSz() {
        return sz;
    }

    public void setSz(String sz) {
        this.sz = sz == null ? null : sz.trim();
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