package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.OrderAttachment;
import com.eray.thjw.po.User;

import enu.aerialmaterial.ContractStatusEnum;

/**
 * B_H_021 销毁单
 * @author xu.yong
 *
 */
public class Destruction extends BizEntity{
    private String id;

    private String dprtcode;

    private String xhdh;

    private String xhrid;

    private Date xhrq;

    private Integer zt;
    
    private String ztText;

    private String bz;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;
    
    private List<String> stockIds;
    
    private List<OrderAttachment> orderAttachmentList;
    
    private List<String> deleteScwjId;
    
    private User zdr_user;

    private User xhr_user;
    
    private Department jg_dprt;
    
    private Department bm_dprt;
    
	public List<String> getDeleteScwjId() {
		return deleteScwjId;
	}

	public void setDeleteScwjId(List<String> deleteScwjId) {
		this.deleteScwjId = deleteScwjId;
	}

	public String getZtText() {
		return zt==null?"":ContractStatusEnum.getName(zt);
	}

	public void setZtText(String ztText) {
		this.ztText = ztText;
	}
    
    public User getZdr_user() {
		return zdr_user;
	}

	public void setZdr_user(User zdr_user) {
		this.zdr_user = zdr_user;
	}
	
	public User getXhr_user() {
		return xhr_user;
	}

	public void setXhr_user(User xhr_user) {
		this.xhr_user = xhr_user;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public Department getBm_dprt() {
		return bm_dprt;
	}

	public void setBm_dprt(Department bm_dprt) {
		this.bm_dprt = bm_dprt;
	}

	public List<String> getStockIds() {
		return stockIds;
	}

	public void setStockIds(List<String> stockIds) {
		this.stockIds = stockIds;
	}

	public List<OrderAttachment> getOrderAttachmentList() {
		return orderAttachmentList;
	}

	public void setOrderAttachmentList(List<OrderAttachment> orderAttachmentList) {
		this.orderAttachmentList = orderAttachmentList;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getXhdh() {
        return xhdh;
    }

    public void setXhdh(String xhdh) {
        this.xhdh = xhdh == null ? null : xhdh.trim();
    }

    public String getXhrid() {
        return xhrid;
    }

    public void setXhrid(String xhrid) {
        this.xhrid = xhrid == null ? null : xhrid.trim();
    }

    public Date getXhrq() {
        return xhrq;
    }

    public void setXhrq(Date xhrq) {
        this.xhrq = xhrq;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getZdbmid() {
        return zdbmid;
    }

    public void setZdbmid(String zdbmid) {
        this.zdbmid = zdbmid == null ? null : zdbmid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }
}