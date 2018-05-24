package com.eray.thjw.quality.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
/**
 * 
 * @Description b_z_005 岗位授权
 * @CreateTime 2017年11月10日 上午10:35:51
 * @CreateBy 林龙
 */
public class PostApplication  extends BizEntity{
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer zt;

    private String sqbmid;

    private String sqrid;

    private String sqrdaid;

    private Date sqsj;

    private String sqbz;

    private String sqsqdh;

    private Integer sqlx;

    private String sqgwid;

    private String shbmid;

    private String shrid;

    private Date shsj;

    private String shyj;

    private Integer shjl;

    private String pgbmid;

    private String pgrid;

    private Date pgsj;

    private String pgyj;

    private Integer pgjl;

    private String plkhcj;

    private Integer plpgjg;

    private Date yxqKs;

    private Date yxqJs;

    private String sqsfjid;

    private String gbrid;

    private Date gbrq;

    private String gbyy;

    private String qtzz;

    private Date qtzzyxq;

    private User whr; //维护人
    
    private User shr; //维护人
    
    private User pgr; //评估人
    
    private Department dprt; //组织机构
    
    /** 岗位授权-培训评估 */
    private List<PostApplicationPXPG> applicationPXPGList;
    
    /** 岗位授权-人员资质评估 */
    private List<PostApplicationRYZZPG> postApplicationRYZZPGList;
    
    /** 岗位授权-申请机型 */
    private List<PostApplicationSQJX> postApplicationSQJXList;
    
    /** 其它附件 */
    private List<Attachment> attachmentList;
    
    public User getShr() {
		return shr;
	}

	public void setShr(User shr) {
		this.shr = shr;
	}

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

   

    public String getSqbmid() {
        return sqbmid;
    }

    public void setSqbmid(String sqbmid) {
        this.sqbmid = sqbmid == null ? null : sqbmid.trim();
    }

    public String getSqrid() {
        return sqrid;
    }

    public void setSqrid(String sqrid) {
        this.sqrid = sqrid == null ? null : sqrid.trim();
    }

    public String getSqrdaid() {
        return sqrdaid;
    }

    public void setSqrdaid(String sqrdaid) {
        this.sqrdaid = sqrdaid == null ? null : sqrdaid.trim();
    }

    public Date getSqsj() {
        return sqsj;
    }

    public void setSqsj(Date sqsj) {
        this.sqsj = sqsj;
    }

    public String getSqbz() {
        return sqbz;
    }

    public void setSqbz(String sqbz) {
        this.sqbz = sqbz == null ? null : sqbz.trim();
    }

    public String getSqsqdh() {
        return sqsqdh;
    }

    public void setSqsqdh(String sqsqdh) {
        this.sqsqdh = sqsqdh == null ? null : sqsqdh.trim();
    }

    

    public String getSqgwid() {
        return sqgwid;
    }

    public void setSqgwid(String sqgwid) {
        this.sqgwid = sqgwid == null ? null : sqgwid.trim();
    }

    public String getShbmid() {
        return shbmid;
    }

    public void setShbmid(String shbmid) {
        this.shbmid = shbmid == null ? null : shbmid.trim();
    }

    public String getShrid() {
        return shrid;
    }

    public void setShrid(String shrid) {
        this.shrid = shrid == null ? null : shrid.trim();
    }

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj == null ? null : shyj.trim();
    }

  

    public String getPgbmid() {
        return pgbmid;
    }

    public void setPgbmid(String pgbmid) {
        this.pgbmid = pgbmid == null ? null : pgbmid.trim();
    }

    public String getPgrid() {
        return pgrid;
    }

    public void setPgrid(String pgrid) {
        this.pgrid = pgrid == null ? null : pgrid.trim();
    }

    public Date getPgsj() {
        return pgsj;
    }

    public void setPgsj(Date pgsj) {
        this.pgsj = pgsj;
    }

    public String getPgyj() {
        return pgyj;
    }

    public void setPgyj(String pgyj) {
        this.pgyj = pgyj == null ? null : pgyj.trim();
    }

  

    public String getPlkhcj() {
        return plkhcj;
    }

    public void setPlkhcj(String plkhcj) {
        this.plkhcj = plkhcj == null ? null : plkhcj.trim();
    }


    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getSqlx() {
		return sqlx;
	}

	public void setSqlx(Integer sqlx) {
		this.sqlx = sqlx;
	}

	public Integer getShjl() {
		return shjl;
	}

	public void setShjl(Integer shjl) {
		this.shjl = shjl;
	}

	public Integer getPgjl() {
		return pgjl;
	}

	public void setPgjl(Integer pgjl) {
		this.pgjl = pgjl;
	}

	public Integer getPlpgjg() {
		return plpgjg;
	}

	public void setPlpgjg(Integer plpgjg) {
		this.plpgjg = plpgjg;
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

    public String getSqsfjid() {
        return sqsfjid;
    }

    public void setSqsfjid(String sqsfjid) {
        this.sqsfjid = sqsfjid == null ? null : sqsfjid.trim();
    }

    public String getGbrid() {
        return gbrid;
    }

    public void setGbrid(String gbrid) {
        this.gbrid = gbrid == null ? null : gbrid.trim();
    }

    public Date getGbrq() {
        return gbrq;
    }

    public void setGbrq(Date gbrq) {
        this.gbrq = gbrq;
    }

    public String getGbyy() {
        return gbyy;
    }

    public void setGbyy(String gbyy) {
        this.gbyy = gbyy == null ? null : gbyy.trim();
    }

    public String getQtzz() {
        return qtzz;
    }

    public void setQtzz(String qtzz) {
        this.qtzz = qtzz == null ? null : qtzz.trim();
    }

    public Date getQtzzyxq() {
        return qtzzyxq;
    }

    public void setQtzzyxq(Date qtzzyxq) {
        this.qtzzyxq = qtzzyxq;
    }

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public User getPgr() {
		return pgr;
	}

	public void setPgr(User pgr) {
		this.pgr = pgr;
	}

	public List<PostApplicationPXPG> getApplicationPXPGList() {
		return applicationPXPGList;
	}

	public void setApplicationPXPGList(List<PostApplicationPXPG> applicationPXPGList) {
		this.applicationPXPGList = applicationPXPGList;
	}

	public List<PostApplicationRYZZPG> getPostApplicationRYZZPGList() {
		return postApplicationRYZZPGList;
	}

	public void setPostApplicationRYZZPGList(
			List<PostApplicationRYZZPG> postApplicationRYZZPGList) {
		this.postApplicationRYZZPGList = postApplicationRYZZPGList;
	}

	public List<PostApplicationSQJX> getPostApplicationSQJXList() {
		return postApplicationSQJXList;
	}

	public void setPostApplicationSQJXList(
			List<PostApplicationSQJX> postApplicationSQJXList) {
		this.postApplicationSQJXList = postApplicationSQJXList;
	}
	
	
}