package com.eray.thjw.po;

/**
 * @author liub
 * @description 角色表t_role
 * @develop date 2016.07.29
 */
public class RoleToModel extends BizEntity {

	private String id;         //id
    private String roleId;    //角色ID
    private String dprtcode;    //组织机构
    private String fjjx;      //飞机机型
    private String fjzch;      //飞机注册号
    private Integer lx;      //类型
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getDprtcode() {
		return dprtcode;
	}
	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}
	public String getFjjx() {
		return fjjx;
	}
	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}
	public String getFjzch() {
		return fjzch;
	}
	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}
	public Integer getLx() {
		return lx;
	}
	public void setLx(Integer lx) {
		this.lx = lx;
	}

	
}
