package com.eray.thjw.produce.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.eray.thjw.aerialmaterial.po.ComponentCertificate;
import com.eray.thjw.basic.po.ZoneStation;
import com.eray.thjw.mapper.CustomJsonDateDeserializer;
import com.eray.thjw.po.BaseEntity;

/**
 * @Description b_s2_002 装机清单临时区
 * @CreateTime 2017年10月9日 上午10:43:17
 * @CreateBy 徐勇
 */
public class InstallationListTemp extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String xgzjqdid;

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
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date chucrq;

    private String tsn;

    private String tso;

    private Integer csn;

    private Integer cso;

    private String bjgzjl;

    private String bz;

    private Integer skbs;

    private Integer ssbs;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date azsj;

    private String azrid;
    
    private String azr;
    
    private String azbz;
    
    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private String zjjlx;
    
    private String fxjldid;
    
    /** 父节点 */
    private InstallationListTemp parent;
    
    /** 飞机站位 */
    private List<ZoneStation> stations;
    
    /** 初始化信息 */
    private List<InstallationListTemp2Init> initDatas;
    
    /** 部件证书 */
    private List<ComponentCertificate> certificates;   

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

    public String getXgzjqdid() {
        return xgzjqdid;
    }

    public void setXgzjqdid(String xgzjqdid) {
        this.xgzjqdid = xgzjqdid == null ? null : xgzjqdid.trim();
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

    public Date getAzsj() {
        return azsj;
    }

    public void setAzsj(Date azsj) {
        this.azsj = azsj;
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

	public List<InstallationListTemp2Init> getInitDatas() {
		return initDatas;
	}

	public void setInitDatas(List<InstallationListTemp2Init> initDatas) {
		this.initDatas = initDatas;
	}

	public List<ComponentCertificate> getCertificates() {
		return certificates;
	}

	public void setCertificates(List<ComponentCertificate> certificates) {
		this.certificates = certificates;
	}

	public InstallationListTemp getParent() {
		return parent;
	}

	public void setParent(InstallationListTemp parent) {
		this.parent = parent;
	}

	public List<ZoneStation> getStations() {
		return stations;
	}

	public void setStations(List<ZoneStation> stations) {
		this.stations = stations;
	}

	public String getAzrid() {
		return azrid;
	}

	public void setAzrid(String azrid) {
		this.azrid = azrid;
	}

	public String getAzr() {
		return azr;
	}

	public void setAzr(String azr) {
		this.azr = azr;
	}

	public String getAzbz() {
		return azbz;
	}

	public void setAzbz(String azbz) {
		this.azbz = azbz;
	}

	public String getZjjlx() {
		return zjjlx;
	}

	public void setZjjlx(String zjjlx) {
		this.zjjlx = zjjlx;
	}
	
	public String getFxjldid() {
		return fxjldid;
	}

	public void setFxjldid(String fxjldid) {
		this.fxjldid = fxjldid;
	}

	public boolean equals(InstallationListTemp temp){
		if(getKeyByInstallationListTemp().equals(temp.getKeyByInstallationListTemp())){
			return true;
		}
		return false;
	}
	
	private String getKeyByInstallationListTemp(){
		StringBuilder sb = new StringBuilder();
		sb.append(convertString(bjh)).append(convertString(xlh)).append(pch);
		return sb.toString();
	} 
	
	private String convertString(String str){
		if(str == null){
			return "-";
		}
		return str;
	}
	
}