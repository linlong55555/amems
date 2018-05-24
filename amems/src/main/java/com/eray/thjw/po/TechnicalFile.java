package com.eray.thjw.po;

import java.util.Date;
import java.util.List;

import enu.ZtEnum;

public class TechnicalFile extends BizEntity {
	private Pagination pagination; // 分页

	private String id;

	private String pgdh;

	private String ly;

	private String oldshzltgh;

	private String lyname;

	private String jx;

	private String fl;

	private String flname;

	private String wjlx;

	private String wjlxname;

	private String shzltgh;

	private String bb;

	private Integer yjjb;

	private String list;

	private String list1;

	private User zdr_user;

	private User pgr_user;

	private User shr_user;

	private User pfr_user;

	private User zdjs_user;

	private TechnicalUpload technicalUpload;
	
	private QualityClose qualityClose;//质量关闭

	public QualityClose getQualityClose() {
		return qualityClose;
	}

	public void setQualityClose(QualityClose qualityClose) {
		this.qualityClose = qualityClose;
	}

	public User getZdjs_user() {
		return zdjs_user;
	}

	public void setZdjs_user(User zdjs_user) {
		this.zdjs_user = zdjs_user;
	}

	public TechnicalUpload getTechnicalUpload() {
		return technicalUpload;
	}

	public void setTechnicalUpload(TechnicalUpload technicalUpload) {
		this.technicalUpload = technicalUpload;
	}

	public User getZdr_user() {
		return zdr_user;
	}

	public void setZdr_user(User zdr_user) {
		this.zdr_user = zdr_user;
	}

	public User getPgr_user() {
		return pgr_user;
	}

	public void setPgr_user(User pgr_user) {
		this.pgr_user = pgr_user;
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

	public String getOldshzltgh() {
		return oldshzltgh;
	}

	public void setOldshzltgh(String oldshzltgh) {
		this.oldshzltgh = oldshzltgh;
	}

	public String getList() {
		return list;
	}

	public void setList(String list) {
		this.list = list;
	}

	public String getList1() {
		return list1;
	}

	public void setList1(String list1) {
		this.list1 = list1;
	}

	private String bbname;

	public String getBbname() {
		return bbname;
	}

	public void setBbname(String bbname) {
		this.bbname = bbname;
	}

	public String getWjlxname() {
		return wjlxname;
	}

	public void setWjlxname(String wjlxname) {
		this.wjlxname = wjlxname;
	}

	private Date sxrq;

	private Date pgqx;

	private String sxrqDate;// 生效日期

	private String pgqxDate;// 评估日期

	private String wjzt;

	private String sjgz;

	private String wjzy;

	private Integer syx;

	private Integer isJstg;

	private Integer isJszl;

	private Integer isWxfa;
	
	private Integer isXdmel;
	
	private Integer isXdgk;

	private Integer isGczl;

	private Integer isFjgd;

	private Integer isFlxgd;

	private Integer isZjfj;

	private Integer isQt;

	private Integer zt;

	private String jgdm;

	private String keyword;

	private String dprtname;

	private Date fbrq;

	private String dj;

	private String jjcd;

	private String wczl;

	private String xgwj;

	private String gbtj;

	private String isMfhc;

	private String isZbhc;

	private String isTsgj;

	private String isYxzlph;

	private String fwxjnr;
	
	public Integer getIsXdmel() {
		return isXdmel;
	}

	public void setIsXdmel(Integer isXdmel) {
		this.isXdmel = isXdmel;
	}

	public Integer getIsXdgk() {
		return isXdgk;
	}

	public void setIsXdgk(Integer isXdgk) {
		this.isXdgk = isXdgk;
	}

	public Date getFbrq() {
		return fbrq;
	}

	public void setFbrq(Date fbrq) {
		this.fbrq = fbrq;
	}

	public String getDj() {
		return dj;
	}

	public void setDj(String dj) {
		this.dj = dj;
	}

	public String getJjcd() {
		return jjcd;
	}

	public void setJjcd(String jjcd) {
		this.jjcd = jjcd;
	}

	public String getWczl() {
		return wczl;
	}

	public void setWczl(String wczl) {
		this.wczl = wczl;
	}

	public String getXgwj() {
		return xgwj;
	}

	public void setXgwj(String xgwj) {
		this.xgwj = xgwj;
	}

	public String getGbtj() {
		return gbtj;
	}

	public void setGbtj(String gbtj) {
		this.gbtj = gbtj;
	}

	public String getIsMfhc() {
		return isMfhc;
	}

	public void setIsMfhc(String isMfhc) {
		this.isMfhc = isMfhc;
	}

	public String getIsZbhc() {
		return isZbhc;
	}

	public void setIsZbhc(String isZbhc) {
		this.isZbhc = isZbhc;
	}

	public String getIsTsgj() {
		return isTsgj;
	}

	public void setIsTsgj(String isTsgj) {
		this.isTsgj = isTsgj;
	}

	public String getIsYxzlph() {
		return isYxzlph;
	}

	public void setIsYxzlph(String isYxzlph) {
		this.isYxzlph = isYxzlph;
	}

	public String getFwxjnr() {
		return fwxjnr;
	}

	public void setFwxjnr(String fwxjnr) {
		this.fwxjnr = fwxjnr;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getJgdm() {
		return jgdm;
	}

	public void setJgdm(String jgdm) {
		this.jgdm = jgdm;
	}

	public Integer getYjjb() {
		return yjjb;
	}

	public void setYjjb(Integer yjjb) {
		this.yjjb = yjjb;
	}

	private Integer dyzt;

	private String dprtcode;

	private String fzbmid;

	private String fzrid;

	private String zdbmid;

	private String zdrid;

	private String pgbmid2;

	private String pgyj;

	private String pgrid;

	private String pgrname;

	private Date pgsj;

	private String shbmid;

	private String shyj;

	private String shrid;

	private Date shsj;

	private String pfbmid;

	private String pfyj;

	private String pfrid;

	private Date pfsj;

	private String zlid;

	private Integer syts;

	private String zlbh; // 指令编号

	private String syxdx; // 受影响对象

	private String qtMs; // 复选-其他说明

	private String bz; // 备注

	private String zdjsrid; // 指定结束人id

	private String zdjsyy; // 指定结束原因

	private List<String> pgdids;

	private List<Affected> affected;// 受影响的列

	private String pgdsId;

	private String wj;

	private String wjdz;

	private String zdjsrq;

	private String zjh;

	private FixChapter zj;
	
    private String ztText;
    
	public String getZtText() {
		return zt==null?"":ZtEnum.getName(zt);
	}

	public void setZtText(String ztText) {
		this.ztText = ztText;
	}

	public FixChapter getZj() {
		return zj;
	}

	public void setZj(FixChapter zj) {
		this.zj = zj;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}

	public String getZdjsrq() {
		return zdjsrq;
	}

	public void setZdjsrq(String zdjsrq) {
		this.zdjsrq = zdjsrq;
	}

	public String getWjdz() {
		return wjdz;
	}

	public void setWjdz(String wjdz) {
		this.wjdz = wjdz;
	}

	public String getWj() {
		return wj;
	}

	public void setWj(String wj) {
		this.wj = wj;
	}

	public String getPgdsId() {
		return pgdsId;
	}

	public void setPgdsId(String pgdsId) {
		this.pgdsId = pgdsId;
	}

	public List<String> getPgdids() {
		return pgdids;
	}

	public void setPgdids(List<String> pgdids) {
		this.pgdids = pgdids;
	}

	public String getSyxdx() {
		return syxdx;
	}

	public void setSyxdx(String syxdx) {
		this.syxdx = syxdx;
	}

	public String getQtMs() {
		return qtMs;
	}

	public void setQtMs(String qtMs) {
		this.qtMs = qtMs;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}

	public String getZlid() {
		return zlid;
	}

	public String getZlbh() {
		return zlbh;
	}

	public void setZlbh(String zlbh) {
		this.zlbh = zlbh;
	}

	public String getPgrname() {
		return pgrname;
	}

	public void setPgrname(String pgrname) {
		this.pgrname = pgrname;
	}

	public String getSxrqDate() {
		return sxrqDate;
	}

	public void setSxrqDate(String sxrqDate) {
		this.sxrqDate = sxrqDate;
	}

	public String getPgqxDate() {
		return pgqxDate;
	}

	public void setPgqxDate(String pgqxDate) {
		this.pgqxDate = pgqxDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPgdh() {
		return pgdh;
	}

	public void setPgdh(String pgdh) {
		this.pgdh = pgdh;
	}

	public List<Affected> getAffected() {
		return affected;
	}

	public void setAffected(List<Affected> affected) {
		this.affected = affected;
	}

	public String getLy() {
		return ly;
	}

	public void setLy(String ly) {
		this.ly = ly;
	}

	public String getJx() {
		return jx;
	}

	public void setJx(String jx) {
		this.jx = jx;
	}

	public String getFl() {
		return fl;
	}

	public void setFl(String fl) {
		this.fl = fl;
	}

	public Integer getSyts() {
		return syts;
	}

	public void setSyts(Integer syts) {
		this.syts = syts;
	}

	public String getShzltgh() {
		return shzltgh;
	}

	public void setShzltgh(String shzltgh) {
		this.shzltgh = shzltgh;
	}

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	public String getWjzt() {
		return wjzt;
	}

	public void setWjzt(String wjzt) {
		this.wjzt = wjzt;
	}

	public String getSjgz() {
		return sjgz;
	}

	public void setSjgz(String sjgz) {
		this.sjgz = sjgz;
	}

	public String getWjzy() {
		return wjzy;
	}

	public void setWjzy(String wjzy) {
		this.wjzy = wjzy;
	}

	public Integer getSyx() {
		return syx;
	}

	public void setSyx(Integer syx) {
		this.syx = syx;
	}

	public Integer getIsJstg() {
		return isJstg;
	}

	public void setIsJstg(Integer isJstg) {
		this.isJstg = isJstg;
	}

	public Integer getIsJszl() {
		return isJszl;
	}

	public void setIsJszl(Integer isJszl) {
		this.isJszl = isJszl;
	}

	public Integer getIsWxfa() {
		return isWxfa;
	}

	public void setIsWxfa(Integer isWxfa) {
		this.isWxfa = isWxfa;
	}

	public Integer getIsGczl() {
		return isGczl;
	}

	public void setIsGczl(Integer isGczl) {
		this.isGczl = isGczl;
	}

	public Integer getIsFjgd() {
		return isFjgd;
	}

	public void setIsFjgd(Integer isFjgd) {
		this.isFjgd = isFjgd;
	}

	public Integer getIsFlxgd() {
		return isFlxgd;
	}

	public void setIsFlxgd(Integer isFlxgd) {
		this.isFlxgd = isFlxgd;
	}

	public Integer getIsZjfj() {
		return isZjfj;
	}

	public void setIsZjfj(Integer isZjfj) {
		this.isZjfj = isZjfj;
	}

	public Integer getIsQt() {
		return isQt;
	}

	public void setIsQt(Integer isQt) {
		this.isQt = isQt;
	}

	public String getWjlx() {
		return wjlx;
	}

	public void setWjlx(String wjlx) {
		this.wjlx = wjlx;
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
		this.dprtcode = dprtcode;
	}

	public String getFzbmid() {
		return fzbmid;
	}

	public void setFzbmid(String fzbmid) {
		this.fzbmid = fzbmid;
	}

	public String getFzrid() {
		return fzrid;
	}

	public void setFzrid(String fzrid) {
		this.fzrid = fzrid;
	}

	public String getZdbmid() {
		return zdbmid;
	}

	public void setZdbmid(String zdbmid) {
		this.zdbmid = zdbmid;
	}

	public String getZdrid() {
		return zdrid;
	}

	public void setZdrid(String zdrid) {
		this.zdrid = zdrid;
	}

	public String getPgbmid2() {
		return pgbmid2;
	}

	public void setPgbmid2(String pgbmid2) {
		this.pgbmid2 = pgbmid2;
	}

	public String getPgyj() {
		return pgyj;
	}

	public void setPgyj(String pgyj) {
		this.pgyj = pgyj;
	}

	public String getPgrid() {
		return pgrid;
	}

	public void setPgrid(String pgrid) {
		this.pgrid = pgrid;
	}

	public String getShbmid() {
		return shbmid;
	}

	public void setShbmid(String shbmid) {
		this.shbmid = shbmid;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public String getShrid() {
		return shrid;
	}

	public void setShrid(String shrid) {
		this.shrid = shrid;
	}

	public String getPfbmid() {
		return pfbmid;
	}

	public void setPfbmid(String pfbmid) {
		this.pfbmid = pfbmid;
	}

	public String getPfyj() {
		return pfyj;
	}

	public void setPfyj(String pfyj) {
		this.pfyj = pfyj;
	}

	public String getPfrid() {
		return pfrid;
	}

	public void setPfrid(String pfrid) {
		this.pfrid = pfrid;
	}

	public Date getSxrq() {
		return sxrq;
	}

	public void setSxrq(Date sxrq) {
		this.sxrq = sxrq;
	}

	public Date getPgqx() {
		return pgqx;
	}

	public void setPgqx(Date pgqx) {
		this.pgqx = pgqx;
	}

	public Date getPgsj() {
		return pgsj;
	}

	public void setPgsj(Date pgsj) {
		this.pgsj = pgsj;
	}

	public Date getShsj() {
		return shsj;
	}

	public void setShsj(Date shsj) {
		this.shsj = shsj;
	}

	public Date getPfsj() {
		return pfsj;
	}

	public void setPfsj(Date pfsj) {
		this.pfsj = pfsj;
	}

	public String getFlname() {
		return flname;
	}

	public void setFlname(String flname) {
		this.flname = flname;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public String getLyname() {
		return lyname;
	}

	public void setLyname(String lyname) {
		this.lyname = lyname;
	}

	public String getId() {
		return id;
	}

	public void setZlid(String zlid) {
		this.zlid = zlid;
	}

	@Override
	public String toString() {
		return "TechnicalFile [pagination=" + pagination + ", id=" + id + ", pgdh=" + pgdh + ", ly=" + ly + ", lyname="
				+ lyname + ", jx=" + jx + ", fl=" + fl + ", flname=" + flname + ", wjlx=" + wjlx + ", shzltgh="
				+ shzltgh + ", bb=" + bb + ", bbname=" + bbname + ", sxrq=" + sxrq + ", pgqx=" + pgqx + ", sxrqDate="
				+ sxrqDate + ", pgqxDate=" + pgqxDate + ", wjzt=" + wjzt + ", sjgz=" + sjgz + ", wjzy=" + wjzy
				+ ", syx=" + syx + ", isJstg=" + isJstg + ", isJszl=" + isJszl + ", isWxfa=" + isWxfa + ", isGczl="
				+ isGczl + ", isFjgd=" + isFjgd + ", isFlxgd=" + isFlxgd + ", isZjfj=" + isZjfj + ", isQt=" + isQt
				+ ", zt=" + zt + ", dyzt=" + dyzt + ", dprtcode=" + dprtcode + ", fzbmid=" + fzbmid + ", fzrid=" + fzrid
				+ ", zdbmid=" + zdbmid + ", zdrid=" + zdrid + ", pgbmid2=" + pgbmid2 + ", pgyj=" + pgyj + ", pgrid="
				+ pgrid + ", pgrname=" + pgrname + ", pgsj=" + pgsj + ", shbmid=" + shbmid + ", shyj=" + shyj
				+ ", shrid=" + shrid + ", shsj=" + shsj + ", pfbmid=" + pfbmid + ", pfyj=" + pfyj + ", pfrid=" + pfrid
				+ ", pfsj=" + pfsj + ", zlid=" + zlid + ", syts=" + syts + ", zlbh=" + zlbh + ", syxdx=" + syxdx
				+ ", qtMs=" + qtMs + ", bz=" + bz + ", zdjsrid=" + zdjsrid + ", zdjsrq=" + zdjsrq + ", zdjsyy=" + zdjsyy
				+ ", pgdids=" + pgdids + ", pgdsId=" + pgdsId + "]";
	}

}