package com.eray.thjw.system.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * t_message 消息表
 * @author xu.yong
 *
 */
public class Message extends BizEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String bt;

    private String nr;

    private String zrdw;

    private Date fbsj;

    private Date yxqKs;

    private Date yxqJs;

    private Short jjd;

    private Short zt;

    private Short lybs;

    private String lyxt;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private String dprtcode;
    
    private User whr;
    
    private Date searchBeginDate; // 查询开始时间

	private Date searchEndDate; // 查询结束时间
	
	private Department wh_department;// 维护部门
	
	private String keyword;
	
	private String dprtname;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt == null ? null : bt.trim();
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr == null ? null : nr.trim();
    }

    public String getZrdw() {
        return zrdw;
    }

    public void setZrdw(String zrdw) {
        this.zrdw = zrdw == null ? null : zrdw.trim();
    }

    public Date getFbsj() {
        return fbsj;
    }

    public void setFbsj(Date fbsj) {
        this.fbsj = fbsj;
    }

    public Date getYxqKs() {
        return yxqKs;
    }

    public void setYxqKs(Date yxqKs) {
        this.yxqKs = yxqKs;
    }

    public Date getYxqJs() {
        return yxqJs;
    }

    public void setYxqJs(Date yxqJs) {
        this.yxqJs = yxqJs;
    }

    public Short getJjd() {
        return jjd;
    }

    public void setJjd(Short jjd) {
        this.jjd = jjd;
    }

    public Short getZt() {
        return zt;
    }

    public void setZt(Short zt) {
        this.zt = zt;
    }

    public Short getLybs() {
        return lybs;
    }

    public void setLybs(Short lybs) {
        this.lybs = lybs;
    }

    public String getLyxt() {
        return lyxt;
    }

    public void setLyxt(String lyxt) {
        this.lyxt = lyxt == null ? null : lyxt.trim();
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

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Date getSearchBeginDate() {
		return searchBeginDate;
	}

	public void setSearchBeginDate(Date searchBeginDate) {
		this.searchBeginDate = searchBeginDate;
	}

	public Date getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(Date searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public Department getWh_department() {
		return wh_department;
	}

	public void setWh_department(Department wh_department) {
		this.wh_department = wh_department;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}
	
}