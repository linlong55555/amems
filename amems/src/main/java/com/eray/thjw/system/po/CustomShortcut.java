package com.eray.thjw.system.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Menu;

/**
 * t_custom_quick 用户自定义快捷方式
 * @author xu.yong
 *
 */
public class CustomShortcut extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String yhid;

    private String zwms;

    private String ywms;

    private String cdid;

    private Short zt;

    private Date whsj;
    
    /** 虚拟字段 start */
    private Menu menu;//菜单
    /** 虚拟字段 end */

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getYhid() {
        return yhid;
    }

    public void setYhid(String yhid) {
        this.yhid = yhid == null ? null : yhid.trim();
    }

    public String getZwms() {
        return zwms;
    }

    public void setZwms(String zwms) {
        this.zwms = zwms == null ? null : zwms.trim();
    }

    public String getYwms() {
        return ywms;
    }

    public void setYwms(String ywms) {
        this.ywms = ywms == null ? null : ywms.trim();
    }

    public String getCdid() {
        return cdid;
    }

    public void setCdid(String cdid) {
        this.cdid = cdid == null ? null : cdid.trim();
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

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
}