package com.eray.thjw.material2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BaseEntity;

/**
 * @Description B_H2_022合同管理
 * @CreateTime 2018-3-8 下午2:05:37
 * @CreateBy 刘兵
 */
public class ContractMgnt extends BaseEntity {
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private Integer htlx;

    private String hth;

    private Date htrq;

    private String biz;

    private String jffs;

    private Integer xgflx;

    private String xgfid;

    private String xgfms;

    private String bz;

    private String gbrid;

    private Date gbrq;

    private String gbyy;
    
    /** 其它附件 */
    private List<Attachment> attachmentList;
    
    /** 合同明细 */
    private List<ContractInfo> contractInfoList;

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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getHtlx() {
        return htlx;
    }

    public void setHtlx(Integer htlx) {
        this.htlx = htlx;
    }

    public String getHth() {
        return hth;
    }

    public void setHth(String hth) {
        this.hth = hth == null ? null : hth.trim();
    }

    public Date getHtrq() {
        return htrq;
    }

    public void setHtrq(Date htrq) {
        this.htrq = htrq;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz == null ? null : biz.trim();
    }

    public String getJffs() {
        return jffs;
    }

    public void setJffs(String jffs) {
        this.jffs = jffs == null ? null : jffs.trim();
    }

    public Integer getXgflx() {
        return xgflx;
    }

    public void setXgflx(Integer xgflx) {
        this.xgflx = xgflx;
    }

    public String getXgfid() {
        return xgfid;
    }

    public void setXgfid(String xgfid) {
        this.xgfid = xgfid == null ? null : xgfid.trim();
    }

    public String getXgfms() {
        return xgfms;
    }

    public void setXgfms(String xgfms) {
        this.xgfms = xgfms == null ? null : xgfms.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getGbrid() {
        return gbrid;
    }

    public void setGbrid(String gbrid) {
        this.gbrid = gbrid == null ? null : gbrid.trim();
    }

    public Date getGbrq() {
        return gbrq;
    }

    public void setGbrq(Date gbrq) {
        this.gbrq = gbrq;
    }

    public String getGbyy() {
        return gbyy;
    }

    public void setGbyy(String gbyy) {
        this.gbyy = gbyy == null ? null : gbyy.trim();
    }

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<ContractInfo> getContractInfoList() {
		return contractInfoList;
	}

	public void setContractInfoList(List<ContractInfo> contractInfoList) {
		this.contractInfoList = contractInfoList;
	}

	@Override
	public String toString() {
		return "ContractMgnt [id=" + id + ", dprtcode=" + dprtcode
				+ ", whbmid=" + whbmid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", zt=" + zt + ", htlx=" + htlx + ", hth=" + hth + ", htrq="
				+ htrq + ", biz=" + biz + ", jffs=" + jffs + ", xgflx=" + xgflx
				+ ", xgfid=" + xgfid + ", xgfms=" + xgfms + ", bz=" + bz
				+ ", gbrid=" + gbrid + ", gbrq=" + gbrq + ", gbyy=" + gbyy
				+ ", attachmentList=" + attachmentList + ", contractInfoList="
				+ contractInfoList + ", pagination=" + pagination
				+ ", getId()=" + getId() + ", getDprtcode()=" + getDprtcode()
				+ ", getWhbmid()=" + getWhbmid() + ", getWhrid()=" + getWhrid()
				+ ", getWhsj()=" + getWhsj() + ", getZt()=" + getZt()
				+ ", getHtlx()=" + getHtlx() + ", getHth()=" + getHth()
				+ ", getHtrq()=" + getHtrq() + ", getBiz()=" + getBiz()
				+ ", getJffs()=" + getJffs() + ", getXgflx()=" + getXgflx()
				+ ", getXgfid()=" + getXgfid() + ", getXgfms()=" + getXgfms()
				+ ", getBz()=" + getBz() + ", getGbrid()=" + getGbrid()
				+ ", getGbrq()=" + getGbrq() + ", getGbyy()=" + getGbyy()
				+ ", getAttachmentList()=" + getAttachmentList()
				+ ", getContractInfoList()=" + getContractInfoList()
				+ ", getPagination()=" + getPagination() + ", getParamsMap()="
				+ getParamsMap() + ", getCzls()=" + getCzls()
				+ ", getLogOperationEnum()=" + getLogOperationEnum()
				+ ", getZbid()=" + getZbid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}