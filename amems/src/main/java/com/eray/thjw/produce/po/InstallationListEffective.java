package com.eray.thjw.produce.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.eray.thjw.aerialmaterial.po.HCMainData;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.mapper.CustomJsonDateDeserializer;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;

/**
 * @Description b_s2_004 装机清单-生效区
 * @CreateTime 2017年9月22日 下午2:26:57
 * @CreateBy 韩武
 */
public class InstallationListEffective extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String jx;

    private String fjzch;

    private Integer wz;

    private Integer cj;

    private String fjdid;

    private String bjh;

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

    private String cjZsjjh;

    private String cjZsjxlh;

    private Integer zjzt;

    private Integer yxbs;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private String zjjlx;
    
    private String dprtname;
    
    /** 维护人 */
    private User whr;
    
    /** 部件初始化数据 */
    private List<InstallationListEffective2Init> initDatas;
    
    /** 飞机站位 */
    private List<ZoneStation> stations;
    
    /** 父节点 */
    private InstallationListEffective parent;
    
    private String ywmc;
    
    private String zwmc;
    
    private String xh;
    
    private String jldw;
    
    private String cjjh;
    
    private String displayName;
    
    private String detailName;
    
    private FixChapter fixChapter;
    
    private HCMainData hcMainData;
    
    private String drbs;
    
    private String cqid;
    
    private BigDecimal kccb;
    
    private String biz;
    
    public String getDrbs() {
		return drbs;
	}

	public void setDrbs(String drbs) {
		this.drbs = drbs;
	}

	/** 计算时间 */
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)  
    private Date jssj;

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

    public String getCjZsjjh() {
        return cjZsjjh;
    }

    public void setCjZsjjh(String cjZsjjh) {
        this.cjZsjjh = cjZsjjh == null ? null : cjZsjjh.trim();
    }

    public String getCjZsjxlh() {
        return cjZsjxlh;
    }

    public void setCjZsjxlh(String cjZsjxlh) {
        this.cjZsjxlh = cjZsjxlh == null ? null : cjZsjxlh.trim();
    }

    public Integer getZjzt() {
        return zjzt;
    }

    public void setZjzt(Integer zjzt) {
        this.zjzt = zjzt;
    }

    public Integer getYxbs() {
        return yxbs;
    }

    public void setYxbs(Integer yxbs) {
        this.yxbs = yxbs;
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

	public List<InstallationListEffective2Init> getInitDatas() {
		return initDatas;
	}

	public void setInitDatas(List<InstallationListEffective2Init> initDatas) {
		this.initDatas = initDatas;
	}

	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	public String getZwmc() {
		return zwmc;
	}

	public void setZwmc(String zwmc) {
		this.zwmc = zwmc;
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

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}
	
	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public String getXh() {
		return xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getJldw() {
		return jldw;
	}

	public void setJldw(String jldw) {
		this.jldw = jldw;
	}

	public List<ZoneStation> getStations() {
		return stations;
	}

	public void setStations(List<ZoneStation> stations) {
		this.stations = stations;
	}

	public String getCjjh() {
		return cjjh;
	}

	public void setCjjh(String cjjh) {
		this.cjjh = cjjh;
	}

	public String getZjjlx() {
		return zjjlx;
	}

	public void setZjjlx(String zjjlx) {
		this.zjjlx = zjjlx;
	}

	public InstallationListEffective getParent() {
		return parent;
	}

	public void setParent(InstallationListEffective parent) {
		this.parent = parent;
	}

	public Date getJssj() {
		return jssj;
	}

	public void setJssj(Date jssj) {
		this.jssj = jssj;
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
		return "InstallationListEffective [id=" + id + ", dprtcode=" + dprtcode
				+ ", jx=" + jx + ", fjzch=" + fjzch + ", wz=" + wz + ", cj="
				+ cj + ", fjdid=" + fjdid + ", bjh=" + bjh + ", xlh=" + xlh
				+ ", pch=" + pch + ", zjsl=" + zjsl + ", llklx=" + llklx
				+ ", llkbh=" + llkbh + ", fjzw=" + fjzw + ", chucrq=" + chucrq
				+ ", tsn=" + tsn + ", tso=" + tso + ", csn=" + csn + ", cso="
				+ cso + ", bjgzjl=" + bjgzjl + ", bz=" + bz + ", skbs=" + skbs
				+ ", ssbs=" + ssbs + ", azFxjlbid=" + azFxjlbid + ", azsj="
				+ azsj + ", azjldid=" + azjldid + ", azjldh=" + azjldh
				+ ", azrid=" + azrid + ", azr=" + azr + ", azbz=" + azbz
				+ ", ccFxjlbid=" + ccFxjlbid + ", ccsj=" + ccsj + ", ccjldid="
				+ ccjldid + ", ccjldh=" + ccjldh + ", ccrid=" + ccrid
				+ ", ccr=" + ccr + ", ccbz=" + ccbz + ", cjZsjjh=" + cjZsjjh
				+ ", cjZsjxlh=" + cjZsjxlh + ", zjzt=" + zjzt + ", yxbs="
				+ yxbs + ", whbmid=" + whbmid + ", whrid=" + whrid + ", whsj="
				+ whsj + "]";
	}
}