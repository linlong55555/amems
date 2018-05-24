package com.eray.thjw.quality.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;
/**
 * 
 * @Description b_z_006 年度计划
 * @CreateTime 2018年1月4日 上午10:58:27
 * @CreateBy 林龙
 */
public class AnnualPlan  extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private Integer nf;

    private Integer bbh;

    private String ndjhsm;

    private Integer zt;

    private String shyj;

    private String shrid;

    private Date shsj;

    private String pfyj;

    private String pfrid;

    private Date pfsj;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private List<Attachment> attachmentList; //d_011 附件表List
    
    /** 维护人 */
    private User whr;

    public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
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

    public Integer getNf() {
		return nf;
	}

	public void setNf(Integer nf) {
		this.nf = nf;
	}

	public Integer getBbh() {
		return bbh;
	}

	public void setBbh(Integer bbh) {
		this.bbh = bbh;
	}

	public String getNdjhsm() {
		return ndjhsm;
	}

	public void setNdjhsm(String ndjhsm) {
		this.ndjhsm = ndjhsm;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj == null ? null : shyj.trim();
    }

    public String getShrid() {
        return shrid;
    }

    public void setShrid(String shrid) {
        this.shrid = shrid == null ? null : shrid.trim();
    }

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }

    public String getPfyj() {
        return pfyj;
    }

    public void setPfyj(String pfyj) {
        this.pfyj = pfyj == null ? null : pfyj.trim();
    }

    public String getPfrid() {
        return pfrid;
    }

    public void setPfrid(String pfrid) {
        this.pfrid = pfrid == null ? null : pfrid.trim();
    }

    public Date getPfsj() {
        return pfsj;
    }

    public void setPfsj(Date pfsj) {
        this.pfsj = pfsj;
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

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	@Override
	public String toString() {
		return "AnnualPlan [id=" + id + ", dprtcode=" + dprtcode + ", nf=" + nf
				+ ", bbh=" + bbh + ", ndjhsm=" + ndjhsm + ", zt=" + zt
				+ ", shyj=" + shyj + ", shrid=" + shrid + ", shsj=" + shsj
				+ ", pfyj=" + pfyj + ", pfrid=" + pfrid + ", pfsj=" + pfsj
				+ ", whbmid=" + whbmid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", attachmentList=" + attachmentList + ", whr=" + whr
				+ ", pagination=" + pagination + ", getAttachmentList()="
				+ getAttachmentList() + ", getId()=" + getId()
				+ ", getDprtcode()=" + getDprtcode() + ", getNf()=" + getNf()
				+ ", getBbh()=" + getBbh() + ", getNdjhsm()=" + getNdjhsm()
				+ ", getZt()=" + getZt() + ", getShyj()=" + getShyj()
				+ ", getShrid()=" + getShrid() + ", getShsj()=" + getShsj()
				+ ", getPfyj()=" + getPfyj() + ", getPfrid()=" + getPfrid()
				+ ", getPfsj()=" + getPfsj() + ", getWhbmid()=" + getWhbmid()
				+ ", getWhrid()=" + getWhrid() + ", getWhsj()=" + getWhsj()
				+ ", getWhr()=" + getWhr() + ", getZdrid()=" + getZdrid()
				+ ", getZdsj()=" + getZdsj() + ", getKeyword()=" + getKeyword()
				+ ", getZdbmid()=" + getZdbmid() + ", getZdrrealname()="
				+ getZdrrealname() + ", getZdr()=" + getZdr()
				+ ", getAttachments()=" + getAttachments() + ", getDelIds()="
				+ getDelIds() + ", getDprtname()=" + getDprtname()
				+ ", getDprtcodes()=" + getDprtcodes() + ", getPagination()="
				+ getPagination() + ", getParamsMap()=" + getParamsMap()
				+ ", getCzls()=" + getCzls() + ", getLogOperationEnum()="
				+ getLogOperationEnum() + ", getZbid()=" + getZbid()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}