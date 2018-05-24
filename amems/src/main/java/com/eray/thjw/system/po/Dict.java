package com.eray.thjw.system.po;

import java.util.List;

import com.eray.thjw.po.BizEntity;

@SuppressWarnings("serial")
public class Dict extends BizEntity {
	
    private Integer lxid;
    
    private String dprtCode;

    private String lxmc;

    private String xlms;

    private Integer isBj;

    private Integer isXz;

    private Integer isSc;

    private Integer isMc;

    private Integer isSz;
    
    private List<DictItem> itemList;
    
    

    public Integer getLxid() {
        return lxid;
    }

    public void setLxid(Integer lxid) {
        this.lxid = lxid;
    }

    public String getLxmc() {
        return lxmc;
    }

    public void setLxmc(String lxmc) {
        this.lxmc = lxmc == null ? null : lxmc.trim();
    }

    public String getXlms() {
        return xlms;
    }

    public void setXlms(String xlms) {
        this.xlms = xlms == null ? null : xlms.trim();
    }

    public Integer getIsBj() {
        return isBj;
    }

    public void setIsBj(Integer isBj) {
        this.isBj = isBj;
    }

    public Integer getIsXz() {
        return isXz;
    }

    public void setIsXz(Integer isXz) {
        this.isXz = isXz;
    }

    public Integer getIsSc() {
        return isSc;
    }

    public void setIsSc(Integer isSc) {
        this.isSc = isSc;
    }

    public Integer getIsMc() {
        return isMc;
    }

    public void setIsMc(Integer isMc) {
        this.isMc = isMc;
    }

    public Integer getIsSz() {
        return isSz;
    }

    public void setIsSz(Integer isSz) {
        this.isSz = isSz;
    }

	public List<DictItem> getItemList() {
		return itemList;
	}

	public void setItemList(List<DictItem> itemList) {
		this.itemList = itemList;
	}
	
	

	public String getDprtCode() {
		return dprtCode;
	}

	public void setDprtCode(String dprtCode) {
		this.dprtCode = dprtCode;
	}

	@Override
	public String toString() {
		return "Dict [lxid=" + lxid + ", dprtCode=" + dprtCode + ", lxmc=" + lxmc + ", xlms=" + xlms + ", isBj=" + isBj
				+ ", isXz=" + isXz + ", isSc=" + isSc + ", isMc=" + isMc + ", isSz=" + isSz + ", itemList=" + itemList
				+ "]";
	}


	
}