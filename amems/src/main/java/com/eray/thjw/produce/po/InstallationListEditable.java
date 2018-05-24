package com.eray.thjw.produce.po;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;
import com.eray.thjw.util.DateUtil;

/**
 * @Description b_s2_001 装机清单-编辑区
 * @CreateTime 2017年9月22日 下午2:25:55
 * @CreateBy 韩武
 */
public class InstallationListEditable extends BaseEntity{
	
	private static final long serialVersionUID = 1L;
	

	public InstallationListEditable() {
		super();
	}
	
	public InstallationListEditable(InstallationListTemp temp) {
		super();
		this.id = temp.getId();
		this.bjh = temp.getBjh();
		this.xlh = temp.getXlh();
		this.pch = temp.getPch();
		this.ywmc = temp.getYwmc();
		this.zwmc = temp.getZwmc();
		this.cjjh = temp.getCjjh();
		this.zjh = temp.getZjh();
		this.xh = temp.getXh();
		this.jldw = temp.getJldw();
		this.wz = temp.getWz();
		this.cj = temp.getCj();
		this.fjzch = temp.getFjzch();
		this.dprtcode = temp.getDprtcode();
		this.zbid = temp.getFxjldid();
	}
	
	public InstallationListEditable(InstallationListEffective eff) {
		super();
		this.id = eff.getId();
		this.jx = eff.getJx();
		this.bjh = eff.getBjh();
		this.xlh = eff.getXlh();
		this.pch = eff.getPch();
		this.ywmc = eff.getYwmc();
		this.zwmc = eff.getZwmc();
		this.cjjh = eff.getCjjh();
		this.xh = eff.getXh();
		this.jldw = eff.getJldw();
		this.wz = eff.getWz();
		this.cj = eff.getCj();
		this.fjzch = eff.getFjzch();
		this.dprtcode = eff.getDprtcode();
		this.fjdid = eff.getFjdid();
		this.xh = eff.getXh();
		this.zjsl = eff.getZjsl();
		this.llklx = eff.getLlklx();
		this.llkbh = eff.getLlkbh();
		this.fjzw = eff.getFjzw();
		this.chucrq = eff.getChucrq();
		this.tsn = eff.getTsn();
		this.tso = eff.getTso();
		this.csn = eff.getCsn();
		this.cso = eff.getCso();
		this.bjgzjl = eff.getBjgzjl();
		this.skbs = eff.getSkbs();
		this.ssbs = eff.getSsbs();
		this.azFxjlbid = eff.getAzFxjlbid();
		this.azsj = eff.getAzsj();
		this.azjldid = eff.getAzjldid();
		this.azjldh = eff.getAzjldh();
		this.azrid = eff.getAzrid();
		this.azbz = eff.getAzbz();
		this.ccFxjlbid = eff.getCcFxjlbid();
		this.ccsj = eff.getCcsj();
		this.ccjldid = eff.getCcjldid();
		this.ccjldh = eff.getCcjldh();
		this.ccrid = eff.getCcrid();
		this.ccbz = eff.getCcbz();
		this.zjzt = eff.getZjzt();
		this.whbmid = eff.getWhbmid();
		this.whrid = eff.getWhrid();
		this.whsj = eff.getWhsj();
		this.zjjlx = eff.getZjjlx();
		this.dprtname = eff.getDprtname();
		this.whr = eff.getWhr();
		this.fixChapter = eff.getFixChapter();
		this.setParamsMap(eff.getParamsMap());
	}

	private String id;

    private String dprtcode;

    private String jx;

    private String fjzch;

    private Integer wz;

    private Integer cj;

    private String fjdid;

    private String bjh;

    private String zwmc;

    private String ywmc;

    private String cjjh;

    private String xh;

    private String zjh;

    private String jldw;

    private String xlh;

    private String pch;

    private BigDecimal zjsl;

    private Integer llklx;

    private String llkbh;

    private String fjzw;

    private Date chucrq;

    private String tsn;

    private String tso;

    private Integer csn;

    private Integer cso;

    private String bjgzjl;

    private String bz;

    private Integer skbs;

    private Integer ssbs;

    private String azFxjlbid;

    private Date azsj;

    private String azjldid;

    private String azjldh;

    private String azrid;

    private String azr;

    private String azbz;

    private String ccFxjlbid;

    private Date ccsj;

    private String ccjldid;

    private String ccjldh;

    private String ccrid;

    private String ccr;

    private String ccbz;

    private Integer zjzt;

    private Integer tbbs;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private String zjjlx;
    
    private String zbid;
    
    private String dprtname;
    
    private String cqid;
    
    private BigDecimal kccb;
    
    private String biz;
    
    /** 部件名称 */
    private String displayName;
    
    /** 详细名称 */
    private String detailName;
    
    /** 维护人 */
    private User whr;
    
    /** 章节号 */
    private FixChapter fixChapter;
    
    /** 部件初始化数据 */
    private List<InstallationListEditable2Init> initDatas;
    
    /** 部件证书 */
    private List<ComponentCertificate> certificates;
    
    /** 父节点 */
    private InstallationListEditable parent;
    
    /** 飞机站位 */
    private List<ZoneStation> stations;
    
    /** 对应的生效区装机清单 */
    private InstallationListEffective effective;
    
    /** 虚拟字段：对应装上件件号，来源于装机清单生效区（工单） */
    private String cjZsjjh;
    
    /** 虚拟字段：对应装上件序列号，来源于装机清单生效区（工单） */
    private String cjZsjxlh;
    
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

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public Integer getWz() {
        return wz;
    }

    public void setWz(Integer wz) {
        this.wz = wz;
    }

    public Integer getCj() {
        return cj;
    }

    public void setCj(Integer cj) {
        this.cj = cj;
    }

    public String getFjdid() {
        return fjdid;
    }

    public void setFjdid(String fjdid) {
        this.fjdid = fjdid == null ? null : fjdid.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public String getZwmc() {
        return zwmc;
    }

    public void setZwmc(String zwmc) {
        this.zwmc = zwmc == null ? null : zwmc.trim();
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc == null ? null : ywmc.trim();
    }

    public String getCjjh() {
        return cjjh;
    }

    public void setCjjh(String cjjh) {
        this.cjjh = cjjh == null ? null : cjjh.trim();
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh == null ? null : xh.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public String getJldw() {
        return jldw;
    }

    public void setJldw(String jldw) {
        this.jldw = jldw == null ? null : jldw.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch == null ? null : pch.trim();
    }

    public BigDecimal getZjsl() {
        return zjsl;
    }

    public void setZjsl(BigDecimal zjsl) {
        this.zjsl = zjsl;
    }

    public Integer getLlklx() {
        return llklx;
    }

    public void setLlklx(Integer llklx) {
        this.llklx = llklx;
    }

    public String getLlkbh() {
        return llkbh;
    }

    public void setLlkbh(String llkbh) {
        this.llkbh = llkbh == null ? null : llkbh.trim();
    }

    public String getFjzw() {
        return fjzw;
    }

    public void setFjzw(String fjzw) {
        this.fjzw = fjzw == null ? null : fjzw.trim();
    }

    public Date getChucrq() {
        return chucrq;
    }

    public void setChucrq(Date chucrq) {
        this.chucrq = chucrq;
    }

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn == null ? null : tsn.trim();
    }

    public String getTso() {
        return tso;
    }

    public void setTso(String tso) {
        this.tso = tso == null ? null : tso.trim();
    }

    public Integer getCsn() {
        return csn;
    }

    public void setCsn(Integer csn) {
        this.csn = csn;
    }

    public Integer getCso() {
        return cso;
    }

    public void setCso(Integer cso) {
        this.cso = cso;
    }

    public String getBjgzjl() {
        return bjgzjl;
    }

    public void setBjgzjl(String bjgzjl) {
        this.bjgzjl = bjgzjl == null ? null : bjgzjl.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Integer getSkbs() {
        return skbs;
    }

    public void setSkbs(Integer skbs) {
        this.skbs = skbs;
    }

    public Integer getSsbs() {
        return ssbs;
    }

    public void setSsbs(Integer ssbs) {
        this.ssbs = ssbs;
    }

    public String getAzFxjlbid() {
        return azFxjlbid;
    }

    public void setAzFxjlbid(String azFxjlbid) {
        this.azFxjlbid = azFxjlbid == null ? null : azFxjlbid.trim();
    }

    public Date getAzsj() {
        return azsj;
    }

    public void setAzsj(Date azsj) {
        this.azsj = azsj;
    }

    public String getAzjldid() {
        return azjldid;
    }

    public void setAzjldid(String azjldid) {
        this.azjldid = azjldid == null ? null : azjldid.trim();
    }

    public String getAzjldh() {
        return azjldh;
    }

    public void setAzjldh(String azjldh) {
        this.azjldh = azjldh == null ? null : azjldh.trim();
    }

    public String getAzrid() {
        return azrid;
    }

    public void setAzrid(String azrid) {
        this.azrid = azrid == null ? null : azrid.trim();
    }

    public String getAzr() {
        return azr;
    }

    public void setAzr(String azr) {
        this.azr = azr == null ? null : azr.trim();
    }

    public String getAzbz() {
        return azbz;
    }

    public void setAzbz(String azbz) {
        this.azbz = azbz == null ? null : azbz.trim();
    }

    public String getCcFxjlbid() {
        return ccFxjlbid;
    }

    public void setCcFxjlbid(String ccFxjlbid) {
        this.ccFxjlbid = ccFxjlbid == null ? null : ccFxjlbid.trim();
    }

    public Date getCcsj() {
        return ccsj;
    }

    public void setCcsj(Date ccsj) {
        this.ccsj = ccsj;
    }

    public String getCcjldid() {
        return ccjldid;
    }

    public void setCcjldid(String ccjldid) {
        this.ccjldid = ccjldid == null ? null : ccjldid.trim();
    }

    public String getCcjldh() {
        return ccjldh;
    }

    public void setCcjldh(String ccjldh) {
        this.ccjldh = ccjldh == null ? null : ccjldh.trim();
    }

    public String getCcrid() {
        return ccrid;
    }

    public void setCcrid(String ccrid) {
        this.ccrid = ccrid == null ? null : ccrid.trim();
    }

    public String getCcr() {
        return ccr;
    }

    public void setCcr(String ccr) {
        this.ccr = ccr == null ? null : ccr.trim();
    }

    public String getCcbz() {
        return ccbz;
    }

    public void setCcbz(String ccbz) {
        this.ccbz = ccbz == null ? null : ccbz.trim();
    }

    public Integer getZjzt() {
        return zjzt;
    }

    public void setZjzt(Integer zjzt) {
        this.zjzt = zjzt;
    }

    public Integer getTbbs() {
        return tbbs;
    }

    public void setTbbs(Integer tbbs) {
        this.tbbs = tbbs;
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

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public String getDisplayName() {
		return (this.ywmc == null ? "" : this.ywmc) 
				+ " " + (this.zwmc == null ? "" : this.zwmc);
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDetailName() {
		return (this.fixChapter == null ? "" : this.fixChapter.getDisplayName()) 
				+ " " + (this.bjh == null ? "" : this.bjh) 
				+ " " + (this.ywmc == null ? "" : this.ywmc) 
				+ " " + (this.zwmc == null ? "" : this.zwmc) 
				+ " " + (this.xlh == null ? "" : this.xlh);
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public List<InstallationListEditable2Init> getInitDatas() {
		return initDatas;
	}

	public void setInitDatas(List<InstallationListEditable2Init> initDatas) {
		this.initDatas = initDatas;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}
	
	public List<ComponentCertificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<ComponentCertificate> certificates) {
		this.certificates = certificates;
	}

	public InstallationListEditable getParent() {
		return parent;
	}

	public void setParent(InstallationListEditable parent) {
		this.parent = parent;
	}

	public List<ZoneStation> getStations() {
		return stations;
	}

	public void setStations(List<ZoneStation> stations) {
		this.stations = stations;
	}

	public InstallationListEffective getEffective() {
		return effective;
	}

	public void setEffective(InstallationListEffective effective) {
		this.effective = effective;
	}

	/**
	 * @throws ParseException 
	 * @Description 生成安装时间
	 * @CreateTime 2017年9月28日 下午3:14:16
	 * @CreateBy 韩武
	 */
	public void generateAzsj(){
		if(azsj == null){
			Map<String, Object> param = getParamsMap();
			if(param.get("azrq") != null){
				String azrq = String.valueOf(param.get("azrq"));
				String azsj = String.valueOf(param.get("azsj"));
				if(StringUtils.isBlank(azsj)){
					azsj = "00:00";
				}
				azsj += ":00";
				try {
					setAzsj(DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE_TIME, azrq + " " + azsj));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * @Description 生成拆除时间
	 * @CreateTime 2017年9月28日 下午3:14:16
	 * @CreateBy 韩武
	 */
	public void generateCcsj(){
		Map<String, Object> param = getParamsMap();
		if (param.get("ccrq") != null && !"".equals(param.get("ccrq"))) {
			String ccrq = String.valueOf(param.get("ccrq"));
			String ccsj = String.valueOf(param.get("ccsj"));
			if (StringUtils.isBlank(ccsj)) {
				ccsj = "00:00";
			}
			ccsj += ":00";
			try {
				setCcsj(DateUtil.getStr2Date(DateUtil.DEFAULT_FORMAT_DATE_TIME, ccrq + " " + ccsj));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	public String getCjZsjjh() {
		return cjZsjjh;
	}

	public void setCjZsjjh(String cjZsjjh) {
		this.cjZsjjh = cjZsjjh;
	}

	public String getCjZsjxlh() {
		return cjZsjxlh;
	}

	public void setCjZsjxlh(String cjZsjxlh) {
		this.cjZsjxlh = cjZsjxlh;
	}

	public String getZjjlx() {
		return zjjlx;
	}

	public void setZjjlx(String zjjlx) {
		this.zjjlx = zjjlx;
	}

	public String getZbid() {
		return zbid;
	}

	public void setZbid(String zbid) {
		this.zbid = zbid;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

	public String getCqid() {
		return cqid;
	}

	public void setCqid(String cqid) {
		this.cqid = cqid;
	}

	public BigDecimal getKccb() {
		return kccb;
	}

	public void setKccb(BigDecimal kccb) {
		this.kccb = kccb;
	}

	public String getBiz() {
		return biz;
	}

	public void setBiz(String biz) {
		this.biz = biz;
	}

	@Override
	public String toString() {
		return "InstallationListEditable [id=" + id + ", dprtcode=" + dprtcode
				+ ", jx=" + jx + ", fjzch=" + fjzch + ", wz=" + wz + ", cj="
				+ cj + ", fjdid=" + fjdid + ", bjh=" + bjh + ", zwmc=" + zwmc
				+ ", ywmc=" + ywmc + ", cjjh=" + cjjh + ", xh=" + xh + ", zjh="
				+ zjh + ", jldw=" + jldw + ", xlh=" + xlh + ", pch=" + pch
				+ ", zjsl=" + zjsl + ", llklx=" + llklx + ", llkbh=" + llkbh
				+ ", fjzw=" + fjzw + ", chucrq=" + chucrq + ", tsn=" + tsn
				+ ", tso=" + tso + ", csn=" + csn + ", cso=" + cso
				+ ", bjgzjl=" + bjgzjl + ", bz=" + bz + ", skbs=" + skbs
				+ ", ssbs=" + ssbs + ", azFxjlbid=" + azFxjlbid + ", azsj="
				+ azsj + ", azjldid=" + azjldid + ", azjldh=" + azjldh
				+ ", azrid=" + azrid + ", azr=" + azr + ", azbz=" + azbz
				+ ", ccFxjlbid=" + ccFxjlbid + ", ccsj=" + ccsj + ", ccjldid="
				+ ccjldid + ", ccjldh=" + ccjldh + ", ccrid=" + ccrid
				+ ", ccr=" + ccr + ", ccbz=" + ccbz + ", zjzt=" + zjzt
				+ ", tbbs=" + tbbs + ", whbmid=" + whbmid + ", whrid=" + whrid
				+ ", whsj=" + whsj + ", displayName=" + displayName
				+ ", fixChapter=" + fixChapter + ", initDatas=" + initDatas
				+ "]";
	}
	
}