package com.eray.thjw.project2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;
/**
 * @author 孙霁
 * @Description b_g2_000 适航性资料
 * @UpdateBy 孙霁
 */
public class Airworthiness extends BizEntity{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String jswjbh;

    private String bb;

    private String jswjzt;

    private String jswjly;

    private String jswjlx;

    private String ata;

    private String xzah;

    private String gljswjid;

    private Date bfrq;

    private Date sxrq;

    private Date dqrq;

    private Date pgqx;

    private Integer xpgbs;

    private String bz;

    private Integer zt;

    private Integer jkzt;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private String gbrid;

    private Date gbrq;

    private String gbyy;
    
    private User zdr;
    
    private User gbr;
    
    private FixChapter ZJ;
    
    private Attachment scfj;
    
    private List<TechnicalfileOrder> technicalfileOrderList;
    
    private Department jg_dprt;
    
    private List<String> techoIds;
    
    private String deleteUploadId;//删除附件id
    
    private List<Technical> technicalList;
    
    private Integer yjzt;
    
    private List<String> wjlxList;
    
    private List<String> xpgbsList;
    
    
	private Integer yjtsJb1;

	private Integer yjtsJb2;

	private Integer yjtsJb3;
    
    
	public List<String> getWjlxList() {
		return wjlxList;
	}

	public void setWjlxList(List<String> wjlxList) {
		this.wjlxList = wjlxList;
	}

	public List<String> getXpgbsList() {
		return xpgbsList;
	}

	public void setXpgbsList(List<String> xpgbsList) {
		this.xpgbsList = xpgbsList;
	}

	public Integer getYjzt() {
		return yjzt;
	}

	public void setYjzt(Integer yjzt) {
		this.yjzt = yjzt;
	}

	public List<Technical> getTechnicalList() {
		return technicalList;
	}

	public void setTechnicalList(List<Technical> technicalList) {
		this.technicalList = technicalList;
	}

	public String getDeleteUploadId() {
		return deleteUploadId;
	}

	public void setDeleteUploadId(String deleteUploadId) {
		this.deleteUploadId = deleteUploadId;
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

	public String getJswjbh() {
		return jswjbh;
	}

	public void setJswjbh(String jswjbh) {
		this.jswjbh = jswjbh;
	}

	public String getBb() {
		return bb;
	}

	public void setBb(String bb) {
		this.bb = bb;
	}

	public String getJswjzt() {
		return jswjzt;
	}

	public void setJswjzt(String jswjzt) {
		this.jswjzt = jswjzt;
	}

	public String getJswjly() {
		return jswjly;
	}

	public void setJswjly(String jswjly) {
		this.jswjly = jswjly;
	}

	public String getJswjlx() {
		return jswjlx;
	}

	public void setJswjlx(String jswjlx) {
		this.jswjlx = jswjlx;
	}

	public String getAta() {
		return ata;
	}

	public void setAta(String ata) {
		this.ata = ata;
	}

	public String getXzah() {
		return xzah;
	}

	public void setXzah(String xzah) {
		this.xzah = xzah;
	}

	public String getGljswjid() {
		return gljswjid;
	}

	public void setGljswjid(String gljswjid) {
		this.gljswjid = gljswjid;
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

	public Date getDqrq() {
		return dqrq;
	}

	public void setDqrq(Date dqrq) {
		this.dqrq = dqrq;
	}

	public Date getPgqx() {
		return pgqx;
	}

	public void setPgqx(Date pgqx) {
		this.pgqx = pgqx;
	}


	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getXpgbs() {
		return xpgbs;
	}

	public void setXpgbs(Integer xpgbs) {
		this.xpgbs = xpgbs;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getJkzt() {
		return jkzt;
	}

	public void setJkzt(Integer jkzt) {
		this.jkzt = jkzt;
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

	public Date getZdsj() {
		return zdsj;
	}

	public void setZdsj(Date zdsj) {
		this.zdsj = zdsj;
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

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public User getGbr() {
		return gbr;
	}

	public void setGbr(User gbr) {
		this.gbr = gbr;
	}

	public FixChapter getZJ() {
		return ZJ;
	}

	public void setZJ(FixChapter zJ) {
		ZJ = zJ;
	}

	public Attachment getScfj() {
		return scfj;
	}

	public void setScfj(Attachment scfj) {
		this.scfj = scfj;
	}

	public List<TechnicalfileOrder> getTechnicalfileOrderList() {
		return technicalfileOrderList;
	}

	public void setTechnicalfileOrderList(
			List<TechnicalfileOrder> technicalfileOrderList) {
		this.technicalfileOrderList = technicalfileOrderList;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public List<String> getTechoIds() {
		return techoIds;
	}

	public void setTechoIds(List<String> techoIds) {
		this.techoIds = techoIds;
	}

	public Integer getYjtsJb1() {
		return yjtsJb1;
	}

	public void setYjtsJb1(Integer yjtsJb1) {
		this.yjtsJb1 = yjtsJb1;
	}

	public Integer getYjtsJb2() {
		return yjtsJb2;
	}

	public void setYjtsJb2(Integer yjtsJb2) {
		this.yjtsJb2 = yjtsJb2;
	}

	public Integer getYjtsJb3() {
		return yjtsJb3;
	}

	public void setYjtsJb3(Integer yjtsJb3) {
		this.yjtsJb3 = yjtsJb3;
	}
    
}