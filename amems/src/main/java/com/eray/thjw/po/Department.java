package com.eray.thjw.po;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public class Department implements Serializable{
    private String id;
    
    private String dprtcode;
    
    private String dprtname;

    private String parentid = "0";

    private String dprttype;

    private String remark;
    
    private Pagination pagination; 
    
    private String parentName;
    
    private Integer pxh;
    
    private Integer jdbs;
    
    //扩展区域
    private Map<String, Object> paramsMap;
    
    private DeptInfo deptInfo;
    
    public Integer getJdbs() {
		return jdbs;
	}

	public void setJdbs(Integer jdbs) {
		this.jdbs = jdbs;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	//搜索关键字
    private String keyword;
    
    public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	private String[] userIds;
    
    private String orgndprtcode;
 
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

    public String getDprtname() {
        return dprtname;
    }

    public void setDprtname(String dprtname) {
        this.dprtname = dprtname == null ? null : dprtname.trim();
    }

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public String getDprttype() {
        return dprttype;
    }

    public void setDprttype(String dprttype) {
        this.dprttype = dprttype == null ? null : dprttype.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

	public String[] getUserIds() {
		return userIds;
	}

	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}

	public String getOrgndprtcode() {
		return orgndprtcode;
	}

	public void setOrgndprtcode(String orgndprtcode) {
		this.orgndprtcode = orgndprtcode;
	}
	
	public Integer getPxh() {
		return pxh;
	}

	public void setPxh(Integer pxh) {
		this.pxh = pxh;
	}
	@Override
	public String toString() {
		return "Department [id=" + id + ", dprtcode=" + dprtcode
				+ ", dprtname=" + dprtname + ", parentid=" + parentid
				+ ", dprttype=" + dprttype + ", remark=" + remark
				+ ", pagination=" + pagination + ", keyword=" + keyword
				+ ", userIds=" + Arrays.toString(userIds) + ", orgndprtcode="
				+ orgndprtcode + "]";
	}

	
	public Map<String, Object> getParamsMap() {
		if(paramsMap == null){
			paramsMap = new HashMap<String, Object>();
		}
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}

	public DeptInfo getDeptInfo() {
		return deptInfo;
	}

	public void setDeptInfo(DeptInfo deptInfo) {
		this.deptInfo = deptInfo;
	}

}