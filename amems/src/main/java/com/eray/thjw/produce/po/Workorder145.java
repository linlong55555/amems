package com.eray.thjw.produce.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.project2.po.CoverPlate;
import com.eray.thjw.project2.po.MaterialTool;
import com.eray.thjw.project2.po.Reference;
import com.eray.thjw.project2.po.WorkContent;
import com.eray.thjw.project2.po.WorkHour;

/**
 * 
 * @Description 145工单 b_s2_014
 * @CreateTime 2017年10月10日 下午2:43:13
 * @CreateBy 林龙
 */
public class Workorder145 extends BizEntity{
    private String id;

    private String gdsbid;

    private String dprtcode;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String xfrdwid;

    private String xfrid;

    private Date xfsj;

    private String wgfkrdwid;

    private String wgfkrid;

    private Date wgfksj;

    private String gbrid;

    private Date gbrq;

    private String gbyy;

    private String zdbmid;

    private String zdrid;

    private Date zdrq;

    private Integer zt;

    private Integer xmblbs;

    private Integer wgbs;

    private String jksjid;

    private String gbid;

    private String gkid;

    private String gkbh;

    private String djbgdid;

    private String gdbh;

    private Integer gdlx;

    private String gdbt;
    
    private String lyrwid;

    private String lyrwh;

    private String fjjx;

    private String fjzch;

    private String fjxlh;

    private String zjh;

    private Integer bjbs;

    private String bgrid;

    private String bgr;

    private Date kdrq;

    private Date jhKsrq;

    private Date jhJsrq;

    private String zy;

    private String gzlb;

    private Integer jhgsRs;

    private BigDecimal jhgsXss;

    private String jhZd;

    private String gzzid;

    private String ybgs;

    private String qxms;

    private String jyclfa;

    private String gznrfjid;

    private String wgfkfjid;

    private Date sjKsrq;

    private Date sjJsrq;

    private String sjGzz;

    private String sjJcz;
    
    private String sjJczid;

    private BigDecimal sjGs;

    private String sjZd;

    private String gzxx;

    private String clcs;

    private String dqGzzid;

    private String dqJdid;

    private String dqZxzt;

    private String dqZxzzt;

    private Integer dxfbs;
    
    private Integer hsgs;
    
    private String gbbh; //工包编号
    
    private int blLx; //保留类型 1:故障保留  2:缺陷保留
    private String blId; //保留类型ID
    
    /** 区域、接近、飞机站位  */
    private List<CoverPlate> coverPlateList;
    
    /** 工种工时 */
    private List<WorkHour> workHourList;
    
    /** 相关参考文件 */
    private List<Reference> referenceList;
    
    /** 器材、工具 */
    private List<MaterialTool> materialToolList;
    
    /** 工作内容 */
    private List<WorkContent> workContentList;
    
    /** 工作内容附件 */
    private Attachment workContentAttachment;
    
    /** 附件列表 */
    private List<Attachment> attachmentList;
    
    /** 工序列表 */
    private List<WorkorderProcessHours> processList;
    
    /**章节号*/
    private FixChapter fixChapter;
    
    /**下发人单位*/
    private Department xfrDepartment;
    
    /**145工包*/
    private Workpackage145 workpackage145;
    
    /** 拆换件记录**/
    private List<Workorder145IORecord> woIORecordList;
    
    /** 工作者 */
    private List<WorkOrder145Worker> workers;
    
    public String getGbbh() {
		return gbbh;
	}

	public void setGbbh(String gbbh) {
		this.gbbh = gbbh;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGdsbid() {
        return gdsbid;
    }

    public void setGdsbid(String gdsbid) {
        this.gdsbid = gdsbid == null ? null : gdsbid.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getWhdwid() {
        return whdwid;
    }

    public void setWhdwid(String whdwid) {
        this.whdwid = whdwid == null ? null : whdwid.trim();
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

    public String getXfrdwid() {
        return xfrdwid;
    }

    public void setXfrdwid(String xfrdwid) {
        this.xfrdwid = xfrdwid == null ? null : xfrdwid.trim();
    }

    public String getXfrid() {
        return xfrid;
    }

    public void setXfrid(String xfrid) {
        this.xfrid = xfrid == null ? null : xfrid.trim();
    }

    public Date getXfsj() {
        return xfsj;
    }

    public void setXfsj(Date xfsj) {
        this.xfsj = xfsj;
    }

    public String getWgfkrdwid() {
        return wgfkrdwid;
    }

    public void setWgfkrdwid(String wgfkrdwid) {
        this.wgfkrdwid = wgfkrdwid == null ? null : wgfkrdwid.trim();
    }

    public String getWgfkrid() {
        return wgfkrid;
    }

    public void setWgfkrid(String wgfkrid) {
        this.wgfkrid = wgfkrid == null ? null : wgfkrid.trim();
    }

    public Date getWgfksj() {
        return wgfksj;
    }

    public void setWgfksj(Date wgfksj) {
        this.wgfksj = wgfksj;
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

    public Date getZdrq() {
        return zdrq;
    }

    public void setZdrq(Date zdrq) {
        this.zdrq = zdrq;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getXmblbs() {
        return xmblbs;
    }

    public void setXmblbs(Integer xmblbs) {
        this.xmblbs = xmblbs;
    }

    public Integer getWgbs() {
        return wgbs;
    }

    public void setWgbs(Integer wgbs) {
        this.wgbs = wgbs;
    }

    public String getJksjid() {
        return jksjid;
    }

    public void setJksjid(String jksjid) {
        this.jksjid = jksjid == null ? null : jksjid.trim();
    }

    public String getGbid() {
        return gbid;
    }

    public void setGbid(String gbid) {
        this.gbid = gbid == null ? null : gbid.trim();
    }

    public String getGkid() {
        return gkid;
    }

    public void setGkid(String gkid) {
        this.gkid = gkid == null ? null : gkid.trim();
    }

    public String getGkbh() {
        return gkbh;
    }

    public void setGkbh(String gkbh) {
        this.gkbh = gkbh == null ? null : gkbh.trim();
    }

    public String getDjbgdid() {
        return djbgdid;
    }

    public void setDjbgdid(String djbgdid) {
        this.djbgdid = djbgdid == null ? null : djbgdid.trim();
    }

    public String getGdbh() {
        return gdbh;
    }

    public void setGdbh(String gdbh) {
        this.gdbh = gdbh == null ? null : gdbh.trim();
    }

    public Integer getGdlx() {
        return gdlx;
    }

    public void setGdlx(Integer gdlx) {
        this.gdlx = gdlx;
    }

    public String getGdbt() {
        return gdbt;
    }

    public void setGdbt(String gdbt) {
        this.gdbt = gdbt == null ? null : gdbt.trim();
    }

    public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getFjxlh() {
        return fjxlh;
    }

    public void setFjxlh(String fjxlh) {
        this.fjxlh = fjxlh == null ? null : fjxlh.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public Integer getBjbs() {
        return bjbs;
    }

    public void setBjbs(Integer bjbs) {
        this.bjbs = bjbs;
    }

    public String getBgrid() {
        return bgrid;
    }

    public void setBgrid(String bgrid) {
        this.bgrid = bgrid == null ? null : bgrid.trim();
    }

    public String getBgr() {
        return bgr;
    }

    public void setBgr(String bgr) {
        this.bgr = bgr == null ? null : bgr.trim();
    }

    public Date getKdrq() {
        return kdrq;
    }

    public void setKdrq(Date kdrq) {
        this.kdrq = kdrq;
    }

    public Date getJhKsrq() {
        return jhKsrq;
    }

    public void setJhKsrq(Date jhKsrq) {
        this.jhKsrq = jhKsrq;
    }

    public Date getJhJsrq() {
        return jhJsrq;
    }

    public void setJhJsrq(Date jhJsrq) {
        this.jhJsrq = jhJsrq;
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
    }

    public String getGzlb() {
        return gzlb;
    }

    public void setGzlb(String gzlb) {
        this.gzlb = gzlb == null ? null : gzlb.trim();
    }

    public Integer getJhgsRs() {
        return jhgsRs;
    }

    public void setJhgsRs(Integer jhgsRs) {
        this.jhgsRs = jhgsRs;
    }

    public BigDecimal getJhgsXss() {
        return jhgsXss;
    }

    public void setJhgsXss(BigDecimal jhgsXss) {
        this.jhgsXss = jhgsXss;
    }

    public String getJhZd() {
        return jhZd;
    }

    public void setJhZd(String jhZd) {
        this.jhZd = jhZd == null ? null : jhZd.trim();
    }

    public String getGzzid() {
        return gzzid;
    }

    public void setGzzid(String gzzid) {
        this.gzzid = gzzid == null ? null : gzzid.trim();
    }

    public String getYbgs() {
        return ybgs;
    }

    public void setYbgs(String ybgs) {
        this.ybgs = ybgs == null ? null : ybgs.trim();
    }

    public String getQxms() {
        return qxms;
    }

    public void setQxms(String qxms) {
        this.qxms = qxms == null ? null : qxms.trim();
    }

    public String getJyclfa() {
        return jyclfa;
    }

    public void setJyclfa(String jyclfa) {
        this.jyclfa = jyclfa == null ? null : jyclfa.trim();
    }

    public String getGznrfjid() {
        return gznrfjid;
    }

    public void setGznrfjid(String gznrfjid) {
        this.gznrfjid = gznrfjid == null ? null : gznrfjid.trim();
    }

    public String getWgfkfjid() {
        return wgfkfjid;
    }

    public void setWgfkfjid(String wgfkfjid) {
        this.wgfkfjid = wgfkfjid == null ? null : wgfkfjid.trim();
    }

    public Date getSjKsrq() {
        return sjKsrq;
    }

    public void setSjKsrq(Date sjKsrq) {
        this.sjKsrq = sjKsrq;
    }

    public Date getSjJsrq() {
        return sjJsrq;
    }

    public void setSjJsrq(Date sjJsrq) {
        this.sjJsrq = sjJsrq;
    }

    public String getSjGzz() {
        return sjGzz;
    }

    public void setSjGzz(String sjGzz) {
        this.sjGzz = sjGzz == null ? null : sjGzz.trim();
    }

    public String getSjJcz() {
        return sjJcz;
    }

    public void setSjJcz(String sjJcz) {
        this.sjJcz = sjJcz == null ? null : sjJcz.trim();
    }

    public BigDecimal getSjGs() {
        return sjGs;
    }

    public void setSjGs(BigDecimal sjGs) {
        this.sjGs = sjGs;
    }

    public String getSjZd() {
        return sjZd;
    }

    public void setSjZd(String sjZd) {
        this.sjZd = sjZd == null ? null : sjZd.trim();
    }

    public String getGzxx() {
        return gzxx;
    }

    public void setGzxx(String gzxx) {
        this.gzxx = gzxx == null ? null : gzxx.trim();
    }

    public String getClcs() {
        return clcs;
    }

    public void setClcs(String clcs) {
        this.clcs = clcs == null ? null : clcs.trim();
    }

    public String getDqGzzid() {
        return dqGzzid;
    }

    public void setDqGzzid(String dqGzzid) {
        this.dqGzzid = dqGzzid == null ? null : dqGzzid.trim();
    }

    public String getDqJdid() {
        return dqJdid;
    }

    public void setDqJdid(String dqJdid) {
        this.dqJdid = dqJdid == null ? null : dqJdid.trim();
    }

    public String getDqZxzt() {
        return dqZxzt;
    }

    public void setDqZxzt(String dqZxzt) {
        this.dqZxzt = dqZxzt == null ? null : dqZxzt.trim();
    }

    public String getDqZxzzt() {
        return dqZxzzt;
    }

    public void setDqZxzzt(String dqZxzzt) {
        this.dqZxzzt = dqZxzzt == null ? null : dqZxzzt.trim();
    }

    public Integer getDxfbs() {
        return dxfbs;
    }

    public void setDxfbs(Integer dxfbs) {
        this.dxfbs = dxfbs;
    }

	public String getLyrwid() {
		return lyrwid;
	}

	public void setLyrwid(String lyrwid) {
		this.lyrwid = lyrwid;
	}

	public String getLyrwh() {
		return lyrwh;
	}

	public void setLyrwh(String lyrwh) {
		this.lyrwh = lyrwh;
	}

	public int getBlLx() {
		return blLx;
	}

	public void setBlLx(int blLx) {
		this.blLx = blLx;
	}

	public String getBlId() {
		return blId;
	}

	public void setBlId(String blId) {
		this.blId = blId;
	}

	public List<CoverPlate> getCoverPlateList() {
		return coverPlateList;
	}

	public void setCoverPlateList(List<CoverPlate> coverPlateList) {
		this.coverPlateList = coverPlateList;
	}

	public List<WorkHour> getWorkHourList() {
		return workHourList;
	}

	public void setWorkHourList(List<WorkHour> workHourList) {
		this.workHourList = workHourList;
	}

	public List<Reference> getReferenceList() {
		return referenceList;
	}

	public void setReferenceList(List<Reference> referenceList) {
		this.referenceList = referenceList;
	}

	public List<MaterialTool> getMaterialToolList() {
		return materialToolList;
	}

	public void setMaterialToolList(List<MaterialTool> materialToolList) {
		this.materialToolList = materialToolList;
	}

	public List<WorkContent> getWorkContentList() {
		return workContentList;
	}

	public void setWorkContentList(List<WorkContent> workContentList) {
		this.workContentList = workContentList;
	}

	public Attachment getWorkContentAttachment() {
		return workContentAttachment;
	}

	public void setWorkContentAttachment(Attachment workContentAttachment) {
		this.workContentAttachment = workContentAttachment;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public List<WorkorderProcessHours> getProcessList() {
		return processList;
	}

	public void setProcessList(List<WorkorderProcessHours> processList) {
		this.processList = processList;
	}

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public Department getXfrDepartment() {
		return xfrDepartment;
	}

	public void setXfrDepartment(Department xfrDepartment) {
		this.xfrDepartment = xfrDepartment;
	}

	public Workpackage145 getWorkpackage145() {
		return workpackage145;
	}

	public void setWorkpackage145(Workpackage145 workpackage145) {
		this.workpackage145 = workpackage145;
	}

	public List<Workorder145IORecord> getWoIORecordList() {
		return woIORecordList;
	}

	public void setWoIORecordList(List<Workorder145IORecord> woIORecordList) {
		this.woIORecordList = woIORecordList;
	}

	public String getSjJczid() {
		return sjJczid;
	}

	public void setSjJczid(String sjJczid) {
		this.sjJczid = sjJczid;
	}

	public Integer getHsgs() {
		return hsgs;
	}

	public void setHsgs(Integer hsgs) {
		this.hsgs = hsgs;
	}

	public List<WorkOrder145Worker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<WorkOrder145Worker> workers) {
		this.workers = workers;
	}
	
}