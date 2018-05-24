                                                             package com.eray.thjw.po;

import java.util.Date;
import java.util.List;
//b_g_00201发送表
public class Send extends BizEntity{
    private String mainid;

    private String jsbmid;

    private String jsrid;

    private Date jssj;

    private Integer zt;
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
    private Integer dyzt;

    private Integer jsfs;

    private String dprtcode;
    
    private String realname;
    
    private String dprtname;
    
    private String zhut;
    
    private String id;
    
    private List<OrderSource> orderSourceList;
    
    private String username;
    
    private User use;
    
    private Department bm;
    
    private String jstgid;
    
    private String jstgh;
    
    


	public String getJstgh() {
		return jstgh;
	}

	public void setJstgh(String jstgh) {
		this.jstgh = jstgh;
	}

	public String getJstgid() {
		return jstgid;
	}

	public void setJstgid(String jstgid) {
		this.jstgid = jstgid;
	}

	public User getUse() {
		return use;
	}

	public void setUse(User use) {
		this.use = use;
	}

	public Department getBm() {
		return bm;
	}

	public void setBm(Department bm) {
		this.bm = bm;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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
		this.dprtname = dprtname;
	}

	public String getZhut() {
		return zhut;
	}

	public void setZhut(String zhut) {
		this.zhut = zhut;
	}

	public List<OrderSource> getOrderSourceList() {
		return orderSourceList;
	}

	public void setOrderSourceList(List<OrderSource> orderSourceList) {
		this.orderSourceList = orderSourceList;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
    }

    public String getJsbmid() {
        return jsbmid;
    }

    public void setJsbmid(String jsbmid) {
        this.jsbmid = jsbmid == null ? null : jsbmid.trim();
    }

    public String getJsrid() {
		return jsrid;
	}

	public void setJsrid(String jsrid) {
		this.jsrid = jsrid;
	}

	public Date getJssj() {
		return jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getDyzt() {
		return dyzt;
	}

	public void setDyzt(Integer dyzt) {
		this.dyzt = dyzt;
	}

	public Integer getJsfs() {
		return jsfs;
	}

	public void setJsfs(Integer jsfs) {
		this.jsfs = jsfs;
	}

	public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

	@Override
	public String toString() {
		return "Send [mainid=" + mainid + ", jsbmid=" + jsbmid + ", jsrid="
				+ jsrid + ", jssj=" + jssj + ", zt=" + zt + ", dyzt=" + dyzt
				+ ", jsfs=" + jsfs + ", dprtcode=" + dprtcode + ", realname="
				+ realname + ", dprtname=" + dprtname + ", zhut=" + zhut
				+ ", id=" + id + ", orderSourceList=" + orderSourceList + "]";
	}


    

    
}