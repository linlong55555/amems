package com.eray.thjw.project2.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * 
 * @Description d_004 机型数据
 * @CreateTime 2017年8月14日 上午10:10:58
 * @CreateBy 林龙
 */
public class ActType extends BizEntity{
	
    private String fjjx; //飞机机型

    private String dprtcode; //组织机构
	
    private String bz; //备注

    private Integer zt; //状态

    private String bmid; //部门id

    private String cjrid; //创建人

    private Date cjsj; //创建时间
    
    private User whr; //维护人
    
    private Department dprt; //组织机构
    
    public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Department getDprt() {
		return dprt;
	}

	public void setDprt(Department dprt) {
		this.dprt = dprt;
	}

	public String getFjjx() {
		return fjjx;
	}

	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Integer getZt() {
		return zt;
	}

	public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid == null ? null : bmid.trim();
    }

    public String getCjrid() {
        return cjrid;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid == null ? null : cjrid.trim();
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }
}