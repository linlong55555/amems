package com.eray.thjw.po;

import java.util.List;

/**
 * @author liub
 * @description 角色表t_role
 * @develop date 2016.07.29
 */
public class ModelRole extends BizEntity {

	private String id;         //id
    private String roleCode;    //角色代码
    private String roleName;    //角色名
    private String roleRemark;      //备注
    private String dprtId;      //组织机构id
    
    private String dprtName;//虚拟：组织机构名称
    
    private List<RoleToModel> modelToRoleList;
  
	public List<RoleToModel> getModelToRoleList() {
		return modelToRoleList;
	}
	public void setModelToRoleList(List<RoleToModel> modelToRoleList) {
		this.modelToRoleList = modelToRoleList;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleRemark() {
		return roleRemark;
	}
	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}
	public String getDprtId() {
		return dprtId;
	}
	public void setDprtId(String dprtId) {
		this.dprtId = dprtId;
	}
	public String getDprtName() {
		return dprtName;
	}
	public void setDprtName(String dprtName) {
		this.dprtName = dprtName;
	}
	
	
}
