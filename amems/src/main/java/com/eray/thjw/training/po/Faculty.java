package com.eray.thjw.training.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * @Description B_P_008教员管理
 * @CreateTime 2017年9月25日 上午11:12:43
 * @CreateBy 胡才秋
 */
public class Faculty extends BizEntity{
    private String id;

    private String dprtcode;

    //外部标识
    private Short wbbs;

    //维修人员档案ID
    private String wxrydaid;

    private String wxryid;

    //人员编号
    private String rybh;

    //姓名
    private String xm;

    //联系电话
    private String lxdh;

    //邮箱地址
    private String yxdz;

    //家庭成员
    private String jtcy;

    //性别
    private Integer xb;

    //大照片地址
    private String zpdzD;

    //小照片地址
    private String zpdzX;

    //备注
    private String bz;

    //状态
    private Integer zt;

    //维护部门id
    private String whbmid;

    //维护人id
    private String whrid;

    //维护时间
    private Date whsj;
    
    private User whr;
    
    private Department jg_dprt;
    
    private List<FacultyCourse> courseList;

    public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Short getWbbs() {
        return wbbs;
    }

    public void setWbbs(Short wbbs) {
        this.wbbs = wbbs;
    }

    public String getWxrydaid() {
        return wxrydaid;
    }

    public void setWxrydaid(String wxrydaid) {
        this.wxrydaid = wxrydaid == null ? null : wxrydaid.trim();
    }

    public String getWxryid() {
        return wxryid;
    }

    public void setWxryid(String wxryid) {
        this.wxryid = wxryid == null ? null : wxryid.trim();
    }

    public String getRybh() {
        return rybh;
    }

    public void setRybh(String rybh) {
        this.rybh = rybh == null ? null : rybh.trim();
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm == null ? null : xm.trim();
    }

    public String getLxdh() {
        return lxdh;
    }

    public void setLxdh(String lxdh) {
        this.lxdh = lxdh == null ? null : lxdh.trim();
    }

    public String getYxdz() {
        return yxdz;
    }

    public void setYxdz(String yxdz) {
        this.yxdz = yxdz == null ? null : yxdz.trim();
    }

    public String getJtcy() {
        return jtcy;
    }

    public void setJtcy(String jtcy) {
        this.jtcy = jtcy == null ? null : jtcy.trim();
    }


    public String getZpdzD() {
        return zpdzD;
    }

    public void setZpdzD(String zpdzD) {
        this.zpdzD = zpdzD == null ? null : zpdzD.trim();
    }

    public String getZpdzX() {
        return zpdzX;
    }

    public void setZpdzX(String zpdzX) {
        this.zpdzX = zpdzX == null ? null : zpdzX.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }


    public Integer getXb() {
		return xb;
	}

	public void setXb(Integer xb) {
		this.xb = xb;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
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

	public List<FacultyCourse> getCourseList() {
		return courseList;
	}

	public void setCourseList(List<FacultyCourse> courseList) {
		this.courseList = courseList;
	}
}