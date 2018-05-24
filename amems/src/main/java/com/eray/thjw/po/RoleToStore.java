package com.eray.thjw.po;

/**
 * @author liub
 * @description 角色仓库t_role2wh
 * @develop date 2016.09.09
 */
public class RoleToStore {
    private String id;

    private String roleId;

    private String ckId;


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

	public String getCkId() {
        return ckId;
    }

    public void setCkId(String ckId) {
        this.ckId = ckId == null ? null : ckId.trim();
    }
}