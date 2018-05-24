package com.eray.thjw.project2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * @Description 指术指令 B_G_004
 * @CreateTime 2017年9月5日 上午10:43:38
 * @CreateBy 徐勇
 */
public class TechnicalInstruction extends BizEntity{
    private String id;

    private String jszlh;

    private String jx;

    private String ckzl;

    private String zhut;

    private String nr;

    private String lysm;

    private String jsrid;

    private Integer jszt;

    private String fcrid;

    private Date fcrq;

    private String zxsx;

    private String bflyyj;

    private String bb;

    private String bz;

    private Integer zt;

    private Integer dyzt;

    private String dprtcode;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private String shbmid;

    private String shyj;

    private String shrid;

    private Date shsj;

    private Integer shjl;

    private String pfbmid;

    private String pfyj;

    private String pfrid;

    private Date pfsj;

    private Integer pfjl;

    private String zdjsrid;

    private Date zdjsrq;

    private String zdjsyy;
    
    private Integer zxbs;
    
    private String fBbid;
    
    private String bBbid;
    /** 工作内容 */
    private List<WorkContent> workContentList;     
    /** 下达指令来源 */
    private List<Instructionsource> isList;
    /** 制单人 */
    private User zdr_user;
    /** 发出人 */
    private User fcr_user;
    /** 接收人 */
    private User jsr_user;
    /** 审核人 */
    private User shr_user;
    /** 批复人 */
    private User pfr_user;
    /** 制单部门 */
    private Department zd_dprt;
    /**组织机构 */
    private Department jg_dprt;
    /** 关闭人 **/
    private User gbr_user; 
    /** 前版本*/
    private TechnicalInstruction previous;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getJszlh() {
        return jszlh;
    }

    public void setJszlh(String jszlh) {
        this.jszlh = jszlh == null ? null : jszlh.trim();
    }

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public String getCkzl() {
        return ckzl;
    }

    public void setCkzl(String ckzl) {
        this.ckzl = ckzl == null ? null : ckzl.trim();
    }

    public String getZhut() {
        return zhut;
    }

    public void setZhut(String zhut) {
        this.zhut = zhut == null ? null : zhut.trim();
    }

    public String getNr() {
        return nr;
    }

    public void setNr(String nr) {
        this.nr = nr == null ? null : nr.trim();
    }

    public String getLysm() {
        return lysm;
    }

    public void setLysm(String lysm) {
        this.lysm = lysm == null ? null : lysm.trim();
    }

    public String getJsrid() {
        return jsrid;
    }

    public void setJsrid(String jsrid) {
        this.jsrid = jsrid == null ? null : jsrid.trim();
    }

    public Integer getJszt() {
        return jszt;
    }

    public void setJszt(Integer jszt) {
        this.jszt = jszt;
    }

    public String getFcrid() {
        return fcrid;
    }

    public void setFcrid(String fcrid) {
        this.fcrid = fcrid == null ? null : fcrid.trim();
    }

    public Date getFcrq() {
        return fcrq;
    }

    public void setFcrq(Date fcrq) {
        this.fcrq = fcrq;
    }

    public String getZxsx() {
        return zxsx;
    }

    public void setZxsx(String zxsx) {
        this.zxsx = zxsx == null ? null : zxsx.trim();
    }

    public String getBflyyj() {
        return bflyyj;
    }

    public void setBflyyj(String bflyyj) {
        this.bflyyj = bflyyj == null ? null : bflyyj.trim();
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb == null ? null : bb.trim();
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

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getDyzt() {
        return dyzt;
    }

    public void setDyzt(Integer dyzt) {
        this.dyzt = dyzt;
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getZdbmid() {
        return zdbmid;
    }

    public void setZdbmid(String zdbmid) {
        this.zdbmid = zdbmid == null ? null : zdbmid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }

    public String getShbmid() {
        return shbmid;
    }

    public void setShbmid(String shbmid) {
        this.shbmid = shbmid == null ? null : shbmid.trim();
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj == null ? null : shyj.trim();
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

    public Integer getShjl() {
        return shjl;
    }

    public void setShjl(Integer shjl) {
        this.shjl = shjl;
    }

    public String getPfbmid() {
        return pfbmid;
    }

    public void setPfbmid(String pfbmid) {
        this.pfbmid = pfbmid == null ? null : pfbmid.trim();
    }

    public String getPfyj() {
        return pfyj;
    }

    public void setPfyj(String pfyj) {
        this.pfyj = pfyj == null ? null : pfyj.trim();
    }

    public String getPfrid() {
        return pfrid;
    }

    public void setPfrid(String pfrid) {
        this.pfrid = pfrid == null ? null : pfrid.trim();
    }

    public Date getPfsj() {
        return pfsj;
    }

    public void setPfsj(Date pfsj) {
        this.pfsj = pfsj;
    }

    public Integer getPfjl() {
        return pfjl;
    }

    public void setPfjl(Integer pfjl) {
        this.pfjl = pfjl;
    }

    public String getZdjsrid() {
        return zdjsrid;
    }

    public void setZdjsrid(String zdjsrid) {
        this.zdjsrid = zdjsrid == null ? null : zdjsrid.trim();
    }

    public Date getZdjsrq() {
        return zdjsrq;
    }

    public void setZdjsrq(Date zdjsrq) {
        this.zdjsrq = zdjsrq;
    }

    public String getZdjsyy() {
        return zdjsyy;
    }

    public void setZdjsyy(String zdjsyy) {
        this.zdjsyy = zdjsyy == null ? null : zdjsyy.trim();
    }

	public List<WorkContent> getWorkContentList() {
		return workContentList;
	}

	public void setWorkContentList(List<WorkContent> workContentList) {
		this.workContentList = workContentList;
	}

	public List<Instructionsource> getIsList() {
		return isList;
	}

	public void setIsList(List<Instructionsource> isList) {
		this.isList = isList;
	}

	public User getZdr_user() {
		return zdr_user;
	}

	public void setZdr_user(User zdr_user) {
		this.zdr_user = zdr_user;
	}

	public User getShr_user() {
		return shr_user;
	}

	public void setShr_user(User shr_user) {
		this.shr_user = shr_user;
	}

	public User getPfr_user() {
		return pfr_user;
	}

	public void setPfr_user(User pfr_user) {
		this.pfr_user = pfr_user;
	}

	public Department getZd_dprt() {
		return zd_dprt;
	}

	public void setZd_dprt(Department zd_dprt) {
		this.zd_dprt = zd_dprt;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public User getGbr_user() {
		return gbr_user;
	}

	public void setGbr_user(User gbr_user) {
		this.gbr_user = gbr_user;
	}

	public User getJsr_user() {
		return jsr_user;
	}

	public void setJsr_user(User jsr_user) {
		this.jsr_user = jsr_user;
	}

	public User getFcr_user() {
		return fcr_user;
	}

	public void setFcr_user(User fcr_user) {
		this.fcr_user = fcr_user;
	}

	public Integer getZxbs() {
		return zxbs;
	}

	public void setZxbs(Integer zxbs) {
		this.zxbs = zxbs;
	}

	public String getfBbid() {
		return fBbid;
	}

	public void setfBbid(String fBbid) {
		this.fBbid = fBbid;
	}

	public String getbBbid() {
		return bBbid;
	}

	public void setbBbid(String bBbid) {
		this.bBbid = bBbid;
	}

	public TechnicalInstruction getPrevious() {
		return previous;
	}

	public void setPrevious(TechnicalInstruction previous) {
		this.previous = previous;
	}
    
}