package com.eray.thjw.po;

import java.util.Date;
import java.util.List;

public class RoutinginspectionrecordDetail {
    private String id;

    private String mainid;

    private String xjxmbh;

    private Integer xjxmbs;

    private String xjxmbz;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private List<OrderAttachment> orderAttachmentList; 


	

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

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
    }

    public String getXjxmbh() {
        return xjxmbh;
    }

    public void setXjxmbh(String xjxmbh) {
        this.xjxmbh = xjxmbh == null ? null : xjxmbh.trim();
    }

    public Integer getXjxmbs() {
		return xjxmbs;
	}

	public void setXjxmbs(Integer xjxmbs) {
		this.xjxmbs = xjxmbs;
	}

	public String getXjxmbz() {
        return xjxmbz;
    }

    public void setXjxmbz(String xjxmbz) {
        this.xjxmbz = xjxmbz == null ? null : xjxmbz.trim();
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