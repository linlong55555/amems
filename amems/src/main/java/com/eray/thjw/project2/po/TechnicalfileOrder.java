package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;
/**
 * @author 孙霁
 * @Description b_g2_00001 技术文件-评估指令
 * @UpdateBy 孙霁
 */
public class TechnicalfileOrder extends BizEntity{
    private String id;

    private String mainid;

    private String fjjx;

    private String pgrid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    private User pgr_user;
    
    private String dprtmentId;
    
    private String index;	//虚拟字段		用于前台对比
    

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getDprtmentId() {
		return dprtmentId;
	}

	public void setDprtmentId(String dprtmentId) {
		this.dprtmentId = dprtmentId;
	}

	public User getPgr_user() {
		return pgr_user;
	}

	public void setPgr_user(User pgr_user) {
		this.pgr_user = pgr_user;
	}

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

    public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
    }

    public String getPgrid() {
        return pgrid;
    }

    public void setPgrid(String pgrid) {
        this.pgrid = pgrid == null ? null : pgrid.trim();
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
}