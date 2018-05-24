package com.eray.thjw.quality.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
/**
 * 
 * @Description b_z_00501 岗位授权-培训评估
 * @CreateTime 2017年11月10日 上午10:39:15
 * @CreateBy 林龙
 */
public class PostApplicationPXPG  extends BizEntity{
    private String id;

    private String mainid;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String kcid;

    private Integer pgjg;

    private String pgyj;

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



    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }


    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getPgjg() {
		return pgjg;
	}

	public void setPgjg(Integer pgjg) {
		this.pgjg = pgjg;
	}

	public String getPgyj() {
        return pgyj;
    }

    public void setPgyj(String pgyj) {
        this.pgyj = pgyj == null ? null : pgyj.trim();
    }
}