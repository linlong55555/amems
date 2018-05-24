package com.eray.thjw.system.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * t_custom_block 用户自定义块
 * @author xu.yong
 *
 */
public class CustomBlock extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String mainid;

    private String kbm;

    private Short zt;

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

    public String getKbm() {
        return kbm;
    }

    public void setKbm(String kbm) {
        this.kbm = kbm == null ? null : kbm.trim();
    }

    public Short getZt() {
        return zt;
    }

    public void setZt(Short zt) {
        this.zt = zt;
    }

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }
}