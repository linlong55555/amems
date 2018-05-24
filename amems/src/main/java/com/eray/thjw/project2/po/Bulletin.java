package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
/**
 * @author 岳彬彬
 * @Description b_g2_002 技术通告
 * @UpdateBy 岳彬彬
 */
public class Bulletin extends BizEntity{
    private String id;

    private String dprtcode;

    private String jstgh;

    private BigDecimal bb;

    private Date tgqx;

    private String jx;

    private Date bfrq;

    private Date sxrq;

    private String zhut;

    private Integer sj;

    private String yxx;

    private String lysm;

    private String ckzl;

    private String bj;

    private String ms;

    private String cs;

    private Integer tglb;

    private String gbyy;

    private Integer isFjxx;

    private Integer isWcfjpc;

    private String eoMao;

    private Integer isFjSyxbj;

    private String syxbjFj;

    private Integer isBjzjhs;

    private Integer isBjSyxbj;

    private String syxbjBj;

    private Integer isWpc;

    private String bz;

    private Integer zt;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private String zdjsrid;

    private Date zdjsrq;

    private String zdjsyy;

    private String shbmid;

    private String shyj;

    private String shrid;

    private Date shsj;

    private String pfbmid;

    private String pfyj;

    private String pfrid;

    private Date pfsj;
    
    private Integer shjl;
    
    private Integer pfjl;
    
    private Integer zxbs;
    
    private String fBbid;
    
    private String bBbid;
    
    /** 制单人 **/
    private User zdr_user;
    /** 审核人 **/
    private User shr_user;
    /** 批复人 **/
    private User pfr_user;
    /** 制单部门 **/
    private Department zd_dprt;
    /**组织机构 **/
    private Department jg_dprt;
    /** 下达指令来源 **/
    private List<Instructionsource> isList;
    /** 技术评估单 **/
    private List<Technical> technicalList;
    /** 分发部门 **/
    private List<DistributionDepartment> dDepartmentList;
    /** 剩余天数**/
    private Integer syts;
    /** 是否接收**/
    private Integer isjs;
    /** 已阅*/
    private Integer yy;
    /** 总合*/
    private Integer zh;
    /** 前版本*/
    private Bulletin previous;
    
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

    public String getJstgh() {
        return jstgh;
    }

    public void setJstgh(String jstgh) {
        this.jstgh = jstgh == null ? null : jstgh.trim();
    }

    public BigDecimal getBb() {
        return bb;
    }

    public void setBb(BigDecimal bb) {
        this.bb = bb;
    }

    public Date getTgqx() {
        return tgqx;
    }

    public void setTgqx(Date tgqx) {
        this.tgqx = tgqx;
    }

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public Date getBfrq() {
        return bfrq;
    }

    public void setBfrq(Date bfrq) {
        this.bfrq = bfrq;
    }

    public Date getSxrq() {
        return sxrq;
    }

    public void setSxrq(Date sxrq) {
        this.sxrq = sxrq;
    }

    public String getZhut() {
        return zhut;
    }

    public void setZhut(String zhut) {
        this.zhut = zhut == null ? null : zhut.trim();
    }

    public Integer getSj() {
        return sj;
    }

    public void setSj(Integer sj) {
        this.sj = sj;
    }

    public String getYxx() {
        return yxx;
    }

    public void setYxx(String yxx) {
        this.yxx = yxx == null ? null : yxx.trim();
    }

    public String getLysm() {
        return lysm;
    }

    public void setLysm(String lysm) {
        this.lysm = lysm == null ? null : lysm.trim();
    }

    public String getCkzl() {
        return ckzl;
    }

    public void setCkzl(String ckzl) {
        this.ckzl = ckzl == null ? null : ckzl.trim();
    }

    public String getBj() {
        return bj;
    }

    public void setBj(String bj) {
        this.bj = bj == null ? null : bj.trim();
    }

    public String getMs() {
        return ms;
    }

    public void setMs(String ms) {
        this.ms = ms == null ? null : ms.trim();
    }

    public String getCs() {
        return cs;
    }

    public void setCs(String cs) {
        this.cs = cs == null ? null : cs.trim();
    }

    public Integer getTglb() {
        return tglb;
    }

    public void setTglb(Integer tglb) {
        this.tglb = tglb;
    }

    public String getGbyy() {
        return gbyy;
    }

    public void setGbyy(String gbyy) {
        this.gbyy = gbyy == null ? null : gbyy.trim();
    }

    public Integer getIsFjxx() {
        return isFjxx;
    }

    public void setIsFjxx(Integer isFjxx) {
        this.isFjxx = isFjxx;
    }

    public Integer getIsWcfjpc() {
        return isWcfjpc;
    }

    public void setIsWcfjpc(Integer isWcfjpc) {
        this.isWcfjpc = isWcfjpc;
    }

    public String getEoMao() {
        return eoMao;
    }

    public void setEoMao(String eoMao) {
        this.eoMao = eoMao == null ? null : eoMao.trim();
    }

    public Integer getIsFjSyxbj() {
        return isFjSyxbj;
    }

    public void setIsFjSyxbj(Integer isFjSyxbj) {
        this.isFjSyxbj = isFjSyxbj;
    }

    public String getSyxbjFj() {
        return syxbjFj;
    }

    public void setSyxbjFj(String syxbjFj) {
        this.syxbjFj = syxbjFj == null ? null : syxbjFj.trim();
    }

    public Integer getIsBjzjhs() {
        return isBjzjhs;
    }

    public void setIsBjzjhs(Integer isBjzjhs) {
        this.isBjzjhs = isBjzjhs;
    }

    public Integer getIsBjSyxbj() {
        return isBjSyxbj;
    }

    public void setIsBjSyxbj(Integer isBjSyxbj) {
        this.isBjSyxbj = isBjSyxbj;
    }

    public String getSyxbjBj() {
        return syxbjBj;
    }

    public void setSyxbjBj(String syxbjBj) {
        this.syxbjBj = syxbjBj == null ? null : syxbjBj.trim();
    }

    public Integer getIsWpc() {
        return isWpc;
    }

    public void setIsWpc(Integer isWpc) {
        this.isWpc = isWpc;
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

	public List<Instructionsource> getIsList() {
		return isList;
	}

	public void setIsList(List<Instructionsource> isList) {
		this.isList = isList;
	}
	
	public List<DistributionDepartment> getdDepartmentList() {
		return dDepartmentList;
	}

	public void setdDepartmentList(List<DistributionDepartment> dDepartmentList) {
		this.dDepartmentList = dDepartmentList;
	}

	public List<Technical> getTechnicalList() {
		return technicalList;
	}

	public void setTechnicalList(List<Technical> technicalList) {
		this.technicalList = technicalList;
	}

	public Integer getSyts() {
		return syts;
	}

	public void setSyts(Integer syts) {
		this.syts = syts;
	}
	
	public Integer getShjl() {
		return shjl;
	}

	public void setShjl(Integer shjl) {
		this.shjl = shjl;
	}

	public Integer getPfjl() {
		return pfjl;
	}

	public void setPfjl(Integer pfjl) {
		this.pfjl = pfjl;
	}
	
	public Integer getIsjs() {
		return isjs;
	}

	public void setIsjs(Integer isjs) {
		this.isjs = isjs;
	}
	
	public Integer getYy() {
		return yy;
	}

	public void setYy(Integer yy) {
		this.yy = yy;
	}

	public Integer getZh() {
		return zh;
	}

	public void setZh(Integer zh) {
		this.zh = zh;
	}

	public Bulletin getPrevious() {
		return previous;
	}

	public void setPrevious(Bulletin previous) {
		this.previous = previous;
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

	@Override
	public String toString() {
		return "Bulletin [id=" + id + ", dprtcode=" + dprtcode + ", jstgh=" + jstgh + ", bb=" + bb + ", tgqx=" + tgqx
				+ ", jx=" + jx + ", bfrq=" + bfrq + ", sxrq=" + sxrq + ", zhut=" + zhut + ", sj=" + sj + ", yxx=" + yxx
				+ ", lysm=" + lysm + ", ckzl=" + ckzl + ", bj=" + bj + ", ms=" + ms + ", cs=" + cs + ", tglb=" + tglb
				+ ", gbyy=" + gbyy + ", isFjxx=" + isFjxx + ", isWcfjpc=" + isWcfjpc + ", eoMao=" + eoMao
				+ ", isFjSyxbj=" + isFjSyxbj + ", syxbjFj=" + syxbjFj + ", isBjzjhs=" + isBjzjhs + ", isBjSyxbj="
				+ isBjSyxbj + ", syxbjBj=" + syxbjBj + ", isWpc=" + isWpc + ", bz=" + bz + ", zt=" + zt + ", zdbmid="
				+ zdbmid + ", zdrid=" + zdrid + ", zdsj=" + zdsj + ", zdjsrid=" + zdjsrid + ", zdjsrq=" + zdjsrq
				+ ", zdjsyy=" + zdjsyy + ", shbmid=" + shbmid + ", shyj=" + shyj + ", shrid=" + shrid + ", shsj=" + shsj
				+ ", pfbmid=" + pfbmid + ", pfyj=" + pfyj + ", pfrid=" + pfrid + ", pfsj=" + pfsj + "]";
	}
    
}