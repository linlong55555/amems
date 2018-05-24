package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;

/**
 * 
 * @Description b_g2_010 EO主表
 * @CreateTime 2017-8-20 上午9:24:17
 * @CreateBy 雷伟
 */
public class EngineeringOrder extends BizEntity{
	
	private String id;

    private String dprtcode;

    private Integer zt;

    private Integer zxbs;

    private String fBbid;

    private String bBbid;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String shbmid;

    private String shrid;

    private Date shsj;

    private String shyj;

    private String pfbmid;

    private String pfrid;

    private Date pfsj;

    private String pfyj;

    private String gbrid;

    private Date gbrq;

    private String gbyy;

    private String eobh;

    private BigDecimal bb;

    private BigDecimal isXfsc;

    private String jx;

    private String zjh;

    private String eozt;

    private Date bfrq;

    private BigDecimal jb;

    private String gzlx;

    private BigDecimal sylb;

    private String fjzch;

    private String eolb;

    private String eofj;

    private BigDecimal jhgsRs;

    private BigDecimal jhgsXss;

    private BigDecimal rii;

    private BigDecimal zxfs;

    private BigDecimal hdwz;

    private BigDecimal pfjl;

    private BigDecimal shjl;
    
    private String gznrfjid;
    
    private Integer jsgs;
    
    /** 审核人 */
    private User shr;
    
    /** 批准人 */
    private User pfr;
    
    /** 关闭人 */
    private User gbr;
    
    /** EO-附加信息 **/
    private EngineeringOrderSub eoSub;
    
    /** 技术评估单,下达指令来源 **/
    private List<Instructionsource> isList;
    
    /** 来源文件 **/
    private List<Airworthiness> lywjList;
    
    /** 分发部门 **/
    private String departmentIds;
    
    /** 参考文件  **/
    private List<Reference> ckwjList;
    
    /** 工种工时  **/
    private List<WorkHour> gzgsList;
    
    /** EO受影响出版物 **/
    private List<EOPulicationAffected> syxcbwList;
    
    /** EO监控项设置 **/
    private List<EOMonitorIemSet> jkxszList;
    
    /** EO工时/停场时间  **/
    private List<EOManhourParkingTime> gstcshList;
    
    /** EO适用性 **/
    private List<EOApplicability> syxszList;
    
    /** 器材清单  **/
    private List<MaterialTool> qcqdList;
    
    /** 工具设备  **/
    private List<MaterialTool> gjsbList;
    
    /** 工作内容  **/
    private List<WorkContent> gznrList;
    
    /** 区域和飞机站位  **/
    private List<CoverPlate> qyzwList;
    
    /** 工作内容附件 */
    private Attachment workContentAttachment;
    
    /** 分发部门回显信息  **/
    private List<DistributionDepartment> ffdeptList;
    
    /** 是否标黄显示 **/
    private boolean showYellow;
    
    /** 章节号 */
    private FixChapter fixChapter;
    
    /**前版本对象*/
    private EngineeringOrder fBbObj;
    
    /**机构*/
    private Department depart;
    
    /** 已阅*/
    private Integer yy;
    
    /** 总合*/
    private Integer zh;
    
    
       
	public Department getDepart() {
		return depart;
	}

	public void setDepart(Department depart) {
		this.depart = depart;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
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

	public String getWhbmid() {
		return whbmid;
	}

	public void setWhbmid(String whbmid) {
		this.whbmid = whbmid;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	public String getShbmid() {
		return shbmid;
	}

	public void setShbmid(String shbmid) {
		this.shbmid = shbmid;
	}

	public String getShrid() {
		return shrid;
	}

	public void setShrid(String shrid) {
		this.shrid = shrid;
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
		this.shyj = shyj;
	}

	public String getPfbmid() {
		return pfbmid;
	}

	public void setPfbmid(String pfbmid) {
		this.pfbmid = pfbmid;
	}

	public String getPfrid() {
		return pfrid;
	}

	public void setPfrid(String pfrid) {
		this.pfrid = pfrid;
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
		this.pfyj = pfyj;
	}

	public String getGbrid() {
		return gbrid;
	}

	public void setGbrid(String gbrid) {
		this.gbrid = gbrid;
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
		this.gbyy = gbyy;
	}

	public String getEobh() {
		return eobh;
	}

	public void setEobh(String eobh) {
		this.eobh = eobh;
	}

	public BigDecimal getBb() {
		return bb;
	}

	public void setBb(BigDecimal bb) {
		this.bb = bb;
	}

	public BigDecimal getIsXfsc() {
		return isXfsc;
	}

	public void setIsXfsc(BigDecimal isXfsc) {
		this.isXfsc = isXfsc;
	}

	public String getJx() {
		return jx;
	}

	public void setJx(String jx) {
		this.jx = jx;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}

	public String getEozt() {
		return eozt;
	}

	public void setEozt(String eozt) {
		this.eozt = eozt;
	}

	public Date getBfrq() {
		return bfrq;
	}

	public void setBfrq(Date bfrq) {
		this.bfrq = bfrq;
	}

	public BigDecimal getJb() {
		return jb;
	}

	public void setJb(BigDecimal jb) {
		this.jb = jb;
	}

	public String getGzlx() {
		return gzlx;
	}

	public void setGzlx(String gzlx) {
		this.gzlx = gzlx;
	}

	public BigDecimal getSylb() {
		return sylb;
	}

	public void setSylb(BigDecimal sylb) {
		this.sylb = sylb;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getEolb() {
		return eolb;
	}

	public void setEolb(String eolb) {
		this.eolb = eolb;
	}

	public String getEofj() {
		return eofj;
	}

	public void setEofj(String eofj) {
		this.eofj = eofj;
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

	public BigDecimal getRii() {
		return rii;
	}

	public void setRii(BigDecimal rii) {
		this.rii = rii;
	}

	public BigDecimal getZxfs() {
		return zxfs;
	}

	public void setZxfs(BigDecimal zxfs) {
		this.zxfs = zxfs;
	}

	public BigDecimal getHdwz() {
		return hdwz;
	}

	public void setHdwz(BigDecimal hdwz) {
		this.hdwz = hdwz;
	}

	public BigDecimal getPfjl() {
		return pfjl;
	}

	public void setPfjl(BigDecimal pfjl) {
		this.pfjl = pfjl;
	}

	public BigDecimal getShjl() {
		return shjl;
	}

	public void setShjl(BigDecimal shjl) {
		this.shjl = shjl;
	}

	public EngineeringOrderSub getEoSub() {
		return eoSub;
	}

	public void setEoSub(EngineeringOrderSub eoSub) {
		this.eoSub = eoSub;
	}

	public List<Instructionsource> getIsList() {
		return isList;
	}

	public void setIsList(List<Instructionsource> isList) {
		this.isList = isList;
	}

	public String getDepartmentIds() {
		return departmentIds;
	}

	public void setDepartmentIds(String departmentIds) {
		this.departmentIds = departmentIds;
	}

	public List<Reference> getCkwjList() {
		return ckwjList;
	}

	public void setCkwjList(List<Reference> ckwjList) {
		this.ckwjList = ckwjList;
	}

	public List<WorkHour> getGzgsList() {
		return gzgsList;
	}

	public void setGzgsList(List<WorkHour> gzgsList) {
		this.gzgsList = gzgsList;
	}

	public List<EOPulicationAffected> getSyxcbwList() {
		return syxcbwList;
	}

	public void setSyxcbwList(List<EOPulicationAffected> syxcbwList) {
		this.syxcbwList = syxcbwList;
	}

	public List<EOMonitorIemSet> getJkxszList() {
		return jkxszList;
	}

	public void setJkxszList(List<EOMonitorIemSet> jkxszList) {
		this.jkxszList = jkxszList;
	}

	public List<EOManhourParkingTime> getGstcshList() {
		return gstcshList;
	}

	public void setGstcshList(List<EOManhourParkingTime> gstcshList) {
		this.gstcshList = gstcshList;
	}

	public List<MaterialTool> getQcqdList() {
		return qcqdList;
	}

	public void setQcqdList(List<MaterialTool> qcqdList) {
		this.qcqdList = qcqdList;
	}

	public List<MaterialTool> getGjsbList() {
		return gjsbList;
	}

	public void setGjsbList(List<MaterialTool> gjsbList) {
		this.gjsbList = gjsbList;
	}

	public List<WorkContent> getGznrList() {
		return gznrList;
	}

	public void setGznrList(List<WorkContent> gznrList) {
		this.gznrList = gznrList;
	}

	public List<CoverPlate> getQyzwList() {
		return qyzwList;
	}

	public void setQyzwList(List<CoverPlate> qyzwList) {
		this.qyzwList = qyzwList;
	}

	public Attachment getWorkContentAttachment() {
		return workContentAttachment;
	}

	public void setWorkContentAttachment(Attachment workContentAttachment) {
		this.workContentAttachment = workContentAttachment;
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

	public List<EOApplicability> getSyxszList() {
		return syxszList;
	}

	public void setSyxszList(List<EOApplicability> syxszList) {
		this.syxszList = syxszList;
	}

	public List<Airworthiness> getLywjList() {
		return lywjList;
	}

	public void setLywjList(List<Airworthiness> lywjList) {
		this.lywjList = lywjList;
	}

	public List<DistributionDepartment> getFfdeptList() {
		return ffdeptList;
	}

	public void setFfdeptList(List<DistributionDepartment> ffdeptList) {
		this.ffdeptList = ffdeptList;
	}

	public boolean isShowYellow() {
		return showYellow;
	}

	public void setShowYellow(boolean showYellow) {
		this.showYellow = showYellow;
	}

	public String getGznrfjid() {
		return gznrfjid;
	}

	public void setGznrfjid(String gznrfjid) {
		this.gznrfjid = gznrfjid;
	}

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public User getGbr() {
		return gbr;
	}

	public void setGbr(User gbr) {
		this.gbr = gbr;
	}

	public Integer getJsgs() {
		return jsgs;
	}

	public void setJsgs(Integer jsgs) {
		this.jsgs = jsgs;
	}

	public EngineeringOrder getfBbObj() {
		return fBbObj;
	}

	public void setfBbObj(EngineeringOrder fBbObj) {
		this.fBbObj = fBbObj;
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
}