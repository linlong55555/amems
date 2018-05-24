package com.eray.thjw.system.po;

import com.eray.thjw.po.BaseEntity;

/**
 * 登陆账号
 * @author xu.yong
 *
 */
public class Login extends BaseEntity{

	private static final long serialVersionUID = 1L;

	private String id;

    private String username;

    private String password;

    private Integer state;

    private String jgdm;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getJgdm() {
        return jgdm;
    }

    public void setJgdm(String jgdm) {
        this.jgdm = jgdm == null ? null : jgdm.trim();
    }
}