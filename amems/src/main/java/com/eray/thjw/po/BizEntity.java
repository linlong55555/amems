package com.eray.thjw.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;

/**
 * @author zhuchao
 * @description 所有业务实体类
 */
@SuppressWarnings("serial")
public class BizEntity extends BaseEntity{
	
	/**
     * 制单人id。关联用户表
     */
    private String zdrid;

    /**
     * 制单时间
     */
    private Date zdsj;
    
    /**
     * 机构代码
     */
    private String dprtcode;
    
    /**
     * 制单部门ID
     */
    private String zdbmid;
    
    //搜索关键字
    private String keyword;
    
    
    /**
     * 制单人zdr
     */
    private User zdr;
    
    private String zdrrealname;
    
    /**
     * 附件列表
     */
    private List<Attachment> attachments;
    
    private List<String> delIds;
    
    private String dprtname;
    
    private List<String> dprtcodes;
    
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
    
    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getZdbmid() {
		return zdbmid;
	}

	public void setZdbmid(String zdbmid) {
		this.zdbmid = zdbmid;
	}

	public String getZdrrealname() {
		return zdrrealname;
	}

	public void setZdrrealname(String zdrrealname) {
		this.zdrrealname = zdrrealname;
	}

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public List<String> getDelIds() {
		return delIds;
	}

	public void setDelIds(List<String> delIds) {
		this.delIds = delIds;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

	public List<String> getDprtcodes() {
		return dprtcodes;
	}

	public void setDprtcodes(List<String> dprtcodes) {
		this.dprtcodes = dprtcodes;
	}

	 

}
