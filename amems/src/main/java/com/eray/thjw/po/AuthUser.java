package com.eray.thjw.po;

public class AuthUser {
    private String username;

    private String id;

    private String password;

    private String orgid;

    //扩展属性
    private String topOrgId;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid == null ? null : orgid.trim();
    }

	public String getTopOrgId() {
		return topOrgId;
	}

	public void setTopOrgId(String topOrgId) {
		this.topOrgId = topOrgId;
	}
}