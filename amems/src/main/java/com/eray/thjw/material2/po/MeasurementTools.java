package com.eray.thjw.material2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
/**
 * 
 * @Description b_w_001 计量工具主表
 * @CreateTime 2018年2月7日 上午9:43:23
 * @CreateBy 林龙
 */
public class MeasurementTools extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String bjid;

    private String bjxlh;

    private Integer zt;

    private Integer isJl;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private List<MeasurementToolsDetails> measurementToolsDetailsList;

	public List<MeasurementToolsDetails> getMeasurementToolsDetailsList() {
		return measurementToolsDetailsList;
	}

	public void setMeasurementToolsDetailsList(
			List<MeasurementToolsDetails> measurementToolsDetailsList) {
		this.measurementToolsDetailsList = measurementToolsDetailsList;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBjid() {
        return bjid;
    }

    public void setBjid(String bjid) {
        this.bjid = bjid == null ? null : bjid.trim();
    }

    public String getBjxlh() {
        return bjxlh;
    }

    public void setBjxlh(String bjxlh) {
        this.bjxlh = bjxlh == null ? null : bjxlh.trim();
    }

    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getIsJl() {
		return isJl;
	}

	public void setIsJl(Integer isJl) {
		this.isJl = isJl;
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
}