package com.eray.thjw.aerialmaterial.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * b_h_0080101 入库单-明细信息
 * @author xu.yong
 *
 */
public class InstockDetailStock extends BaseEntity{
    private String id;

    private String mainid;

    private String kcllid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    /** 虚拟字段 start */
    private MaterialHistory materialHistory;//库存履历
    
    private InstockDetail instockDetail;
    /** 虚拟字段 end */
    
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

    public String getKcllid() {
        return kcllid;
    }

    public void setKcllid(String kcllid) {
        this.kcllid = kcllid == null ? null : kcllid.trim();
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

	public MaterialHistory getMaterialHistory() {
		return materialHistory;
	}

	public void setMaterialHistory(MaterialHistory materialHistory) {
		this.materialHistory = materialHistory;
	}

	public InstockDetail getInstockDetail() {
		return instockDetail;
	}

	public void setInstockDetail(InstockDetail instockDetail) {
		this.instockDetail = instockDetail;
	}
    
}