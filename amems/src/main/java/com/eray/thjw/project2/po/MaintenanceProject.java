package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;

/**
 * 
 * @Description 维修项目主表B_G2_012
 * @CreateTime 2017-8-14 下午4:16:01
 * @CreateBy 刘兵
 */
public class MaintenanceProject extends BaseEntity {
    private String id;

    private String dprtcode;

    private String jx;

    private String wxfabh;

    private Integer wxxmlx;

    private String rwh;

    private BigDecimal bb;
    
    private String ckh;

    private String rwms;

    private String syx;

    private String ckwj;

    private String gzlx;

    private String xmlx;

    private Integer isBj;

    private Integer ali;

    private BigDecimal jhgsRs;

    private BigDecimal jhgsXss;

    private String gkbh;
    
    private Integer jsgs;
    
    private Integer isHdwz;
    
    private String bz;

    private Integer zt;
    
    private Integer yxbs;

    private String fBbid;

    private String bBbid;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private String wxxmdlid;

    private String zjh;
    
    private String eobh;
    
    private String cmph;
    
    private Integer wz;
    
    private String jgms;
    
    /** 章节号 */
    private FixChapter fixChapter;
    
    /** 维修项目大类 */
    private MaintenanceClass maintenanceClass;
    
    /** 维修项目-监控项目 */
    private List<ProjectMonitor> projectMonitors;
    
    /** 维修项目对应飞机 */
    private List<ProjectModel> projectModelList;
    
    /** 关联维修项目-父节点 */
    private ProjectRelationship projectRelationship; 
    
    /** 关联维修项目-子节点 */
    private List<ProjectRelationship> projectRelationshipList;
    
    /** 维修项目-接近/盖板 */
    private List<CoverPlate> coverPlateList;
    
    /** 关联部件关系 */
    private List<ProjectMaterial> projectMaterialList;
    
    /** 附件列表 */
    private List<Attachment> attachments;
    
    /** EO */
    private EngineeringOrder eo;
    
    /** 制单人 */
    private User zdr;
    
    /** 关联维修项目 */
    List<MaintenanceProject> projectList;
    
    /** 改版的维修方案 */
    MaintenanceScheme scheme;
    
    /** 最新的维修方案 */
    MaintenanceScheme newestScheme;
    
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

    public Integer getWxxmlx() {
        return wxxmlx;
    }

    public void setWxxmlx(Integer wxxmlx) {
        this.wxxmlx = wxxmlx;
    }

    public String getRwh() {
        return rwh;
    }

    public void setRwh(String rwh) {
        this.rwh = rwh == null ? null : rwh.trim();
    }

    public BigDecimal getBb() {
        return bb;
    }

    public void setBb(BigDecimal bb) {
        this.bb = bb;
    }

    public String getRwms() {
        return rwms;
    }

    public void setRwms(String rwms) {
        this.rwms = rwms == null ? null : rwms.trim();
    }

    public String getSyx() {
        return syx;
    }

    public void setSyx(String syx) {
        this.syx = syx == null ? null : syx.trim();
    }

    public String getCkwj() {
        return ckwj;
    }

    public void setCkwj(String ckwj) {
        this.ckwj = ckwj == null ? null : ckwj.trim();
    }

    public String getGzlx() {
        return gzlx;
    }

    public void setGzlx(String gzlx) {
        this.gzlx = gzlx == null ? null : gzlx.trim();
    }

    public String getXmlx() {
        return xmlx;
    }

    public void setXmlx(String xmlx) {
        this.xmlx = xmlx == null ? null : xmlx.trim();
    }

    public Integer getIsBj() {
        return isBj;
    }

    public void setIsBj(Integer isBj) {
        this.isBj = isBj;
    }

    public Integer getAli() {
        return ali;
    }

    public void setAli(Integer ali) {
        this.ali = ali;
    }

    public BigDecimal getJhgsRs() {
        return jhgsRs;
    }

    public void setJhgsRs(BigDecimal jhgsRs) {
        this.jhgsRs = jhgsRs;
    }

    public BigDecimal getJhgsXss() {
        return jhgsXss;
    }

    public void setJhgsXss(BigDecimal jhgsXss) {
        this.jhgsXss = jhgsXss;
    }

    public String getGkbh() {
        return gkbh;
    }

    public void setGkbh(String gkbh) {
        this.gkbh = gkbh == null ? null : gkbh.trim();
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

    public String getWxxmdlid() {
        return wxxmdlid;
    }

    public void setWxxmdlid(String wxxmdlid) {
        this.wxxmdlid = wxxmdlid == null ? null : wxxmdlid.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public MaintenanceClass getMaintenanceClass() {
		return maintenanceClass;
	}

	public void setMaintenanceClass(MaintenanceClass maintenanceClass) {
		this.maintenanceClass = maintenanceClass;
	}

	public List<ProjectMonitor> getProjectMonitors() {
		return projectMonitors;
	}

	public void setProjectMonitors(List<ProjectMonitor> projectMonitors) {
		this.projectMonitors = projectMonitors;
	}

	public List<ProjectModel> getProjectModelList() {
		return projectModelList;
	}

	public void setProjectModelList(List<ProjectModel> projectModelList) {
		this.projectModelList = projectModelList;
	}

	public ProjectRelationship getProjectRelationship() {
		return projectRelationship;
	}

	public void setProjectRelationship(ProjectRelationship projectRelationship) {
		this.projectRelationship = projectRelationship;
	}
	
	public List<ProjectRelationship> getProjectRelationshipList() {
		return projectRelationshipList;
	}

	public void setProjectRelationshipList(
			List<ProjectRelationship> projectRelationshipList) {
		this.projectRelationshipList = projectRelationshipList;
	}

	public List<CoverPlate> getCoverPlateList() {
		return coverPlateList;
	}

	public void setCoverPlateList(List<CoverPlate> coverPlateList) {
		this.coverPlateList = coverPlateList;
	}

	public List<ProjectMaterial> getProjectMaterialList() {
		return projectMaterialList;
	}

	public void setProjectMaterialList(List<ProjectMaterial> projectMaterialList) {
		this.projectMaterialList = projectMaterialList;
	}

	public String getCkh() {
		return ckh;
	}

	public void setCkh(String ckh) {
		this.ckh = ckh == null ? null : ckh.trim();
	}

	public Integer getJsgs() {
		return jsgs;
	}

	public void setJsgs(Integer jsgs) {
		this.jsgs = jsgs;
	}

	public Integer getIsHdwz() {
		return isHdwz;
	}

	public void setIsHdwz(Integer isHdwz) {
		this.isHdwz = isHdwz;
	}

	public Integer getYxbs() {
		return yxbs;
	}

	public void setYxbs(Integer yxbs) {
		this.yxbs = yxbs;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public EngineeringOrder getEo() {
		return eo;
	}

	public void setEo(EngineeringOrder eo) {
		this.eo = eo;
	}

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public String getEobh() {
		return eobh;
	}

	public void setEobh(String eobh) {
		this.eobh = eobh;
	}

	public List<MaintenanceProject> getProjectList() {
		return projectList;
	}

	public void setProjectList(List<MaintenanceProject> projectList) {
		this.projectList = projectList;
	}

	public MaintenanceScheme getScheme() {
		return scheme == null ? new MaintenanceScheme() : scheme;
	}

	public void setScheme(MaintenanceScheme scheme) {
		this.scheme = scheme;
	}

	public String getCmph() {
		return cmph;
	}

	public void setCmph(String cmph) {
		this.cmph = cmph;
	}

	public Integer getWz() {
		return wz;
	}

	public void setWz(Integer wz) {
		this.wz = wz;
	}

	public MaintenanceScheme getNewestScheme() {
		return newestScheme;
	}

	public void setNewestScheme(MaintenanceScheme newestScheme) {
		this.newestScheme = newestScheme;
	}

	public String getJgms() {
		return jgms;
	}

	public void setJgms(String jgms) {
		this.jgms = jgms;
	}
	
	/**
	 * @Description 比较维修项目
	 * @CreateTime 2018-1-30 下午5:20:17
	 * @CreateBy 刘兵
	 * @param p1
	 * @param p2
	 */
	public void compareColumn(MaintenanceProject p1, MaintenanceProject p2){
		if(null != p2 && p2.getId().equals(p1.getfBbid())){
			
			p1.getParamsMap().put("prerwh", false);
			p1.getParamsMap().put("preoperator", false);
			
			p1.getParamsMap().put("prebb", !cp(p1.getBb(), p2.getBb()));
			p1.getParamsMap().put("preckh", !cp(p1.getCkh(), p2.getCkh()));
			p1.getParamsMap().put("prerwms", !cp(p1.getRwms(), p2.getRwms()));
			p1.getParamsMap().put("presyxstr", !cp(p1.getParamsMap().get("syxstr"), p2.getParamsMap().get("syxstr")));
			p1.getParamsMap().put("prewxxmlx", !cp(p1.getWxxmlx(), p2.getWxxmlx()));
			
			if(!compareMonitorList(p1.getProjectMonitors(), p2.getProjectMonitors())){
				for (int i = 0; i < p1.getProjectMonitors().size(); i++) {
					p1.getProjectMonitors().get(i).getParamsMap().put("prejkx", true);
				}
			}
			
			p1.getParamsMap().put("pregkbh", !cp(p1.getGkbh(), p2.getGkbh()));
			p1.getParamsMap().put("preckwj", !cp(p1.getCkwj(), p2.getCkwj()));
			p1.getParamsMap().put("pregzlx", !cp(p1.getGzlx(), p2.getGzlx()));
			p1.getParamsMap().put("prexmlx", !cp(p1.getXmlx(), p2.getXmlx()));
			p1.getParamsMap().put("preisBj", !cp(p1.getIsBj(), p2.getIsBj()));
			p1.getParamsMap().put("preqyStr", !cp(p1.getParamsMap().get("qyStr"), p2.getParamsMap().get("qyStr")));
			p1.getParamsMap().put("prejjStr", !cp(p1.getParamsMap().get("jjStr"), p2.getParamsMap().get("jjStr")));
			p1.getParamsMap().put("prezjh", !cp(p1.getZjh(), p2.getZjh()));
			p1.getParamsMap().put("prewxxmdl", !cp(p1.getWxxmdlid(), p2.getWxxmdlid()));
			p1.getParamsMap().put("prefjzwStr", !cp(p1.getParamsMap().get("fjzwStr"), p2.getParamsMap().get("fjzwStr")));
			p1.getParamsMap().put("preali", !cp(p1.getAli(), p2.getAli()));
			p1.getParamsMap().put("pretotal", !cp(p1.getParamsMap().get("total"), p2.getParamsMap().get("total")));
			p1.getParamsMap().put("prebz", !cp(p1.getBz(), p2.getBz()));
		}
	}
	
	/**
	 * @Description 比较监控项集合
	 * @CreateTime 2018-2-2 下午1:53:27
	 * @CreateBy 刘兵
	 * @param p1List
	 * @param p2List
	 * @return boolean
	 */
	private boolean compareMonitorList(List<ProjectMonitor> p1List, List<ProjectMonitor> p2List){
		if(p1List.size() != p2List.size()){
			return false;
		}
		for (int i = 0; i < p1List.size(); i++) {
			if(!compareMonitor(p1List.get(i), p2List.get(i))){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @Description 比较监控项
	 * @CreateTime 2018-2-2 下午1:52:56
	 * @CreateBy 刘兵
	 * @param p1
	 * @param p2
	 * @return boolean
	 */
	private boolean compareMonitor(ProjectMonitor p1, ProjectMonitor p2){
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		sb1.append(p1.getBjh())
		   .append(p1.getParamsMap().get("jkmsstr"))
		   .append(p1.getParamsMap().get("scstr"))
		   .append(p1.getParamsMap().get("zqstr"))
		   .append(p1.getParamsMap().get("ydstr"));
		sb2.append(p2.getBjh())
		   .append(p2.getParamsMap().get("jkmsstr"))
		   .append(p2.getParamsMap().get("scstr"))
		   .append(p2.getParamsMap().get("zqstr"))
		   .append(p2.getParamsMap().get("ydstr"));
		return sb1.toString().equals(sb2.toString());
	}
	
	/**
	 * @Description 比较字符串
	 * @CreateTime 2018-1-30 下午5:24:25
	 * @CreateBy 刘兵
	 * @param s1
	 * @param s2
	 * @param diffParam
	 * @return
	 */
	private boolean cp(Object s1, Object s2){
		s1 = s1==null?"":s1.toString();
		s2 = s2==null?"":s2.toString();
		return s1.equals(s2);
	}

	@Override
	public String toString() {
		return "MaintenanceProject [id=" + id + ", dprtcode=" + dprtcode
				+ ", jx=" + jx + ", wxfabh=" + wxfabh + ", wxxmlx=" + wxxmlx
				+ ", rwh=" + rwh + ", bb=" + bb + ", ckh=" + ckh + ", rwms="
				+ rwms + ", syx=" + syx + ", ckwj=" + ckwj + ", gzlx=" + gzlx
				+ ", xmlx=" + xmlx + ", isBj=" + isBj + ", ali=" + ali
				+ ", jhgsRs=" + jhgsRs + ", jhgsXss=" + jhgsXss + ", gkbh="
				+ gkbh + ", jsgs=" + jsgs + ", isHdwz=" + isHdwz + ", bz=" + bz
				+ ", zt=" + zt + ", yxbs=" + yxbs + ", fBbid=" + fBbid
				+ ", bBbid=" + bBbid + ", zdbmid=" + zdbmid + ", zdrid="
				+ zdrid + ", zdsj=" + zdsj + ", wxxmdlid=" + wxxmdlid
				+ ", zjh=" + zjh + ", eobh=" + eobh + ", cmph=" + cmph
				+ ", wz=" + wz + ", fixChapter=" + fixChapter
				+ ", maintenanceClass=" + maintenanceClass
				+ ", projectMonitors=" + projectMonitors
				+ ", projectModelList=" + projectModelList
				+ ", projectRelationship=" + projectRelationship
				+ ", projectRelationshipList=" + projectRelationshipList
				+ ", coverPlateList=" + coverPlateList
				+ ", projectMaterialList=" + projectMaterialList
				+ ", attachments=" + attachments + ", eo=" + eo + ", zdr="
				+ zdr + ", projectList=" + projectList + ", scheme=" + scheme
				+ ", newestScheme=" + newestScheme + ", pagination="
				+ pagination + ", getId()=" + getId() + ", getDprtcode()="
				+ getDprtcode() + ", getJx()=" + getJx() + ", getWxfabh()="
				+ getWxfabh() + ", getWxxmlx()=" + getWxxmlx() + ", getRwh()="
				+ getRwh() + ", getBb()=" + getBb() + ", getRwms()="
				+ getRwms() + ", getSyx()=" + getSyx() + ", getCkwj()="
				+ getCkwj() + ", getGzlx()=" + getGzlx() + ", getXmlx()="
				+ getXmlx() + ", getIsBj()=" + getIsBj() + ", getAli()="
				+ getAli() + ", getJhgsRs()=" + getJhgsRs() + ", getJhgsXss()="
				+ getJhgsXss() + ", getGkbh()=" + getGkbh() + ", getBz()="
				+ getBz() + ", getZt()=" + getZt() + ", getfBbid()="
				+ getfBbid() + ", getbBbid()=" + getbBbid() + ", getZdbmid()="
				+ getZdbmid() + ", getZdrid()=" + getZdrid() + ", getZdsj()="
				+ getZdsj() + ", getWxxmdlid()=" + getWxxmdlid()
				+ ", getZjh()=" + getZjh() + ", getFixChapter()="
				+ getFixChapter() + ", getMaintenanceClass()="
				+ getMaintenanceClass() + ", getProjectMonitors()="
				+ getProjectMonitors() + ", getProjectModelList()="
				+ getProjectModelList() + ", getProjectRelationship()="
				+ getProjectRelationship() + ", getProjectRelationshipList()="
				+ getProjectRelationshipList() + ", getCoverPlateList()="
				+ getCoverPlateList() + ", getProjectMaterialList()="
				+ getProjectMaterialList() + ", getCkh()=" + getCkh()
				+ ", getJsgs()=" + getJsgs() + ", getIsHdwz()=" + getIsHdwz()
				+ ", getYxbs()=" + getYxbs() + ", getAttachments()="
				+ getAttachments() + ", getEo()=" + getEo() + ", getZdr()="
				+ getZdr() + ", getEobh()=" + getEobh() + ", getProjectList()="
				+ getProjectList() + ", getScheme()=" + getScheme()
				+ ", getCmph()=" + getCmph() + ", getWz()=" + getWz()
				+ ", getNewestScheme()=" + getNewestScheme()
				+ ", getPagination()=" + getPagination() + ", getParamsMap()="
				+ getParamsMap() + ", getCzls()=" + getCzls()
				+ ", getLogOperationEnum()=" + getLogOperationEnum()
				+ ", getZbid()=" + getZbid() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

}