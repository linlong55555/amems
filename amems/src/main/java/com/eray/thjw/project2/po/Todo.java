package com.eray.thjw.project2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;
/**
 * 
 * @Description t_todo 待办事宜表
 * @CreateTime 2017-8-21 上午11:02:28
 * @CreateBy 孙霁
 */
public class Todo extends BizEntity{
	private String id;

    private String lyid;

    private String lybh;

    private String bb;

    private String fjjx;

    private Integer dbgzlx;
    //与dbgzlx对应的名字
    private String  dbgzlxName;
    
    private Integer jd;

    private String sm;

    private Date blqx;

    private String blrid;
    
    private String gnbm;
    
    private String dbywid;

    private Integer yxzt;
     
    private Integer zt;
    //与zt对应的名字
    private String ztName;

    private String fkyj;

    private Date fksj;
    
    private User blr;

    private User zpr;//指派人
    
    private  List<Todors> todorsList;
    
    private  List<Instructionsource> instructionsourceList;
    
    private List<User> userList;//后台任务办理人
    
    
    
	public Integer getJd() {
		return jd;
	}

	public void setJd(Integer jd) {
		this.jd = jd;
	}

	public String getGnbm() {
		return gnbm;
	}

	public void setGnbm(String gnbm) {
		this.gnbm = gnbm==null?"":gnbm.trim();
	}

	public String getDbywid() {
		return dbywid;
	}

	public void setDbywid(String dbywid) {
		this.dbywid = dbywid;
	}

	public List<Instructionsource> getInstructionsourceList() {
		return instructionsourceList;
	}

	public void setInstructionsourceList(
			List<Instructionsource> instructionsourceList) {
		this.instructionsourceList = instructionsourceList;
	}

	public List<Todors> getTodorsList() {
		return todorsList;
	}

	public void setTodorsList(List<Todors> todorsList) {
		this.todorsList = todorsList;
	}

	public User getZpr() {
		return zpr;
	}

	public void setZpr(User zpr) {
		this.zpr = zpr;
	}

	public User getBlr() {
		return blr;
	}

	public void setBlr(User blr) {
		this.blr = blr;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getLyid() {
        return lyid;
    }

    public void setLyid(String lyid) {
        this.lyid = lyid == null ? null : lyid.trim();
    }

    public String getLybh() {
        return lybh;
    }

    public void setLybh(String lybh) {
        this.lybh = lybh == null ? null : lybh.trim();
    }

    public String getSm() {
        return sm;
    }

    public void setSm(String sm) {
        this.sm = sm == null ? null : sm.trim();
    }
    
    public Date getBlqx() {
		return blqx;
	}

	public void setBlqx(Date blqx) {
        this.blqx = blqx;
    }

    public String getBlrid() {
        return blrid;
    }

    public void setBlrid(String blrid) {
        this.blrid = blrid == null ? null : blrid.trim();
    }


    public Integer getDbgzlx() {
		return dbgzlx;
	}

	public void setDbgzlx(Integer dbgzlx) {
		this.dbgzlx = dbgzlx;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getFkyj() {
        return fkyj;
    }

    public void setFkyj(String fkyj) {
        this.fkyj = fkyj == null ? null : fkyj.trim();
    }

    public Date getFksj() {
        return fksj;
    }

    public void setFksj(Date fksj) {
        this.fksj = fksj;
    }

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	public String getFjjx() {
		return fjjx;
	}

	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}

	public Integer getYxzt() {
		return yxzt;
	}

	public void setYxzt(Integer yxzt) {
		this.yxzt = yxzt;
	}

	public String getDbgzlxName() {
		return dbgzlxName;
	}

	public void setDbgzlxName(String dbgzlxName) {
		this.dbgzlxName = dbgzlxName;
	}

	public String getZtName() {
		return ztName;
	}

	public void setZtName(String ztName) {
		this.ztName = ztName;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}