package com.eray.thjw.po;

/**
 * @author liub
 * @description 角色表t_role
 * @develop date 2016.07.29
 */
public class Role extends BizEntity {

	private String id;         //id
    private String roleCode;    //角色代码
    private String roleName;    //角色名
    private String roleRemark;      //备注
    private String dprtId;      //组织机构id
    

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
}
