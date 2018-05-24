package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.User;

/**
 * 
 * @Description 维修方案B_G2_011
 * @CreateTime 2017-8-14 下午4:16:27
 * @CreateBy 刘兵
 */
public class MaintenanceScheme extends BaseEntity {
    private String id;

    private String dprtcode;

    private String jx;

    private String wxfabh;

    private BigDecimal bb;

    private String zwms;

    private Date jhSxrq;

    private Date sjSxrq;

    private String bz;

    private Integer zxbs;

    private String fBbid;

    private String bBbid;

    private Integer zt;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private String shbmid;

    private String shrid;

    private Date shsj;

    private String shyj;
    
    private Integer shjl;

    private String pfbmid;

    private String pfrid;

    private Date pfsj;

    private String pfyj;
    
    private Integer pfjl;

    private String sxbmid;

    private String sxrid;

    private Date sxsj;
    
    private String gbyj;
    
    /** 下达指令来源 **/
    private List<Instructionsource> instructionsourceList;
    
    /** 技术评估单 **/
    private List<Technical> technicalList;
    
    /** 维修项目 */
    private List<MaintenanceProject> projectList;
    
    /** 维修项目-ATA分组 */
    private List<Map<String, Object>> ATAList;
    
    /** 制单人 */
    private User zdr;
    
    /** 审核人 */
    private User shr;
    
    /** 批复人 */
    private User pfr;
    
    /** 生效人 */
    private User sxr;

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

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public String getWxfabh() {
        return wxfabh;
    }

    public void setWxfabh(String wxfabh) {
        this.wxfabh = wxfabh == null ? null : wxfabh.trim();
    }

    public BigDecimal getBb() {
        return bb;
    }

    public void setBb(BigDecimal bb) {
        this.bb = bb;
    }

    public String getZwms() {
        return zwms;
    }

    public void setZwms(String zwms) {
        this.zwms = zwms == null ? null : zwms.trim();
    }

    public Date getJhSxrq() {
        return jhSxrq;
    }

    public void setJhSxrq(Date jhSxrq) {
        this.jhSxrq = jhSxrq;
    }

    public Date getSjSxrq() {
        return sjSxrq;
    }

    public void setSjSxrq(Date sjSxrq) {
        this.sjSxrq = sjSxrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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
        this.fBbid = fBbid == null ? null : fBbid.trim();
    }

    public String getbBbid() {
        return bBbid;
    }

    public void setbBbid(String bBbid) {
        this.bBbid = bBbid == null ? null : bBbid.trim();
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

    public String getPfbmid() {
        return pfbmid;
    }

    public void setPfbmid(String pfbmid) {
        this.pfbmid = pfbmid == null ? null : pfbmid.trim();
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

    public String getPfyj() {
        return pfyj;
    }

    public void setPfyj(String pfyj) {
        this.pfyj = pfyj == null ? null : pfyj.trim();
    }

    public String getSxbmid() {
        return sxbmid;
    }

    public void setSxbmid(String sxbmid) {
        this.sxbmid = sxbmid == null ? null : sxbmid.trim();
    }

    public String getSxrid() {
        return sxrid;
    }

    public void setSxrid(String sxrid) {
        this.sxrid = sxrid == null ? null : sxrid.trim();
    }

    public Date getSxsj() {
        return sxsj;
    }

    public void setSxsj(Date sxsj) {
        this.sxsj = sxsj;
    }
    
	public List<Instructionsource> getInstructionsourceList() {
		return instructionsourceList;
	}

	public void setInstructionsourceList(
			List<Instructionsource> instructionsourceList) {
		this.instructionsourceList = instructionsourceList;
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

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public User getShr() {
		return shr;
	}

	public void setShr(User shr) {
		this.shr = shr;
	}

	public User getPfr() {
		return pfr;
	}

	public void setPfr(User pfr) {
		this.pfr = pfr;
	}

	public User getSxr() {
		return sxr;
	}

	public void setSxr(User sxr) {
		this.sxr = sxr;
	}

	public String getGbyj() {
		return gbyj;
	}

	public void setGbyj(String gbyj) {
		this.gbyj = gbyj;
	}

	public List<Technical> getTechnicalList() {
		return technicalList;
	}

	public void setTechnicalList(List<Technical> technicalList) {
		this.technicalList = technicalList;
	}

	public List<MaintenanceProject> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<MaintenanceProject> projectList) {
		this.projectList = projectList;
	}

	public List<Map<String, Object>> getATAList() {
		return ATAList;
	}

	public void setATAList(List<Map<String, Object>> aTAList) {
		ATAList = aTAList;
	}

	@Override
	public String toString() {
		return "MaintenanceScheme [id=" + id + ", dprtcode=" + dprtcode
				+ ", jx=" + jx + ", wxfabh=" + wxfabh + ", bb=" + bb
				+ ", zwms=" + zwms + ", jhSxrq=" + jhSxrq + ", sjSxrq="
				+ sjSxrq + ", bz=" + bz + ", zxbs=" + zxbs + ", fBbid=" + fBbid
				+ ", bBbid=" + bBbid + ", zt=" + zt + ", zdbmid=" + zdbmid
				+ ", zdrid=" + zdrid + ", zdsj=" + zdsj + ", shbmid=" + shbmid
				+ ", shrid=" + shrid + ", shsj=" + shsj + ", shyj=" + shyj
				+ ", shjl=" + shjl + ", pfbmid=" + pfbmid + ", pfrid=" + pfrid
				+ ", pfsj=" + pfsj + ", pfyj=" + pfyj + ", pfjl=" + pfjl
				+ ", sxbmid=" + sxbmid + ", sxrid=" + sxrid + ", sxsj=" + sxsj
				+ ", gbyj=" + gbyj + ", instructionsourceList="
				+ instructionsourceList + ", zdr=" + zdr + ", shr=" + shr
				+ ", pfr=" + pfr + ", sxr=" + sxr + "]";
	}

}