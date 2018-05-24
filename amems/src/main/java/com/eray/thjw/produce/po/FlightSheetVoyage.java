package com.eray.thjw.produce.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.eray.thjw.mapper.CustomJsonDateDeserializer;
import com.eray.thjw.po.BaseEntity;

/**
 * @Description b_s2_00601 飞行记录本 - 航次数据
 * @CreateTime 2017年9月30日 上午11:06:27
 * @CreateBy 徐勇
 */
public class FlightSheetVoyage extends BaseEntity{
	private static final long serialVersionUID = 1L;

	private String id;

    private String mainid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer hc;

    private String hcms;

    private String hbh;
    
    private String qfz;

    private String zlz;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)  
    private Date kcsj;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date qfsj;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date ldsj;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date tcsj;

    private Integer sysjFz;

    private Integer fxsjFz;

    private Integer f1SjFz;

    private Integer f1Xh;

    private BigDecimal f1Hytjl;

    private Integer f2SjFz;

    private Integer f2Xh;

    private BigDecimal f2Hytjl;

    private Integer f3SjFz;

    private Integer f3Xh;

    private BigDecimal f3Hytjl;

    private Integer f4SjFz;

    private Integer f4Xh;

    private BigDecimal f4Hytjl;

    private Integer apuSjFz;

    private Integer apuXh;

    private BigDecimal apuHytjl;

    private Integer fxxh;

    private Integer lxqlcs;

    private String idgtksj;

    private BigDecimal yyy;

    private BigDecimal ryCyl;

    private BigDecimal ryJyl;

    private BigDecimal rySyyl;

    private String fxrid;

    private String fxr;

    private String jzid;

    private String jz;

    private String bz;
    
    private String fxrwlx;
    
    private String rydw;

    private FlightSheet flightSheet;
    
    //小计
    private FlightSheetVoyage flightSheetVoyage;
    
    public FlightSheetVoyage getFlightSheetVoyage() {
		return flightSheetVoyage;
	}

	public void setFlightSheetVoyage(FlightSheetVoyage flightSheetVoyage) {
		this.flightSheetVoyage = flightSheetVoyage;
	}

	/** 飞行前数据 */
    private List<ComponentUse> preflightData;
    
    /** 部件使用汇总 */
    private List<ComponentUseCount> componentUseCounList;
    
	public String getFxrwlx() {
		return fxrwlx;
	}

	public void setFxrwlx(String fxrwlx) {
		this.fxrwlx = fxrwlx;
	}

	public FlightSheet getFlightSheet() {
		return flightSheet;
	}

	public void setFlightSheet(FlightSheet flightSheet) {
		this.flightSheet = flightSheet;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainid() {
		return mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getWhdwid() {
		return whdwid;
	}

	public void setWhdwid(String whdwid) {
		this.whdwid = whdwid;
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

	public Integer getHc() {
		return hc;
	}

	public void setHc(Integer hc) {
		this.hc = hc;
	}

	public String getHcms() {
		return hcms;
	}

	public void setHcms(String hcms) {
		this.hcms = hcms;
	}

	public String getHbh() {
		return hbh;
	}

	public void setHbh(String hbh) {
		this.hbh = hbh;
	}

	public String getQfz() {
		return qfz;
	}

	public void setQfz(String qfz) {
		this.qfz = qfz;
	}

	public String getZlz() {
		return zlz;
	}

	public void setZlz(String zlz) {
		this.zlz = zlz;
	}

	public Date getKcsj() {
		return kcsj;
	}

	public void setKcsj(Date kcsj) {
		this.kcsj = kcsj;
	}

	public Date getQfsj() {
		return qfsj;
	}

	public void setQfsj(Date qfsj) {
		this.qfsj = qfsj;
	}

	public Date getLdsj() {
		return ldsj;
	}

	public void setLdsj(Date ldsj) {
		this.ldsj = ldsj;
	}

	public Date getTcsj() {
		return tcsj;
	}

	public void setTcsj(Date tcsj) {
		this.tcsj = tcsj;
	}

	public Integer getSysjFz() {
		return sysjFz;
	}

	public void setSysjFz(Integer sysjFz) {
		this.sysjFz = sysjFz;
	}

	public Integer getFxsjFz() {
		return fxsjFz;
	}

	public void setFxsjFz(Integer fxsjFz) {
		this.fxsjFz = fxsjFz;
	}

	public Integer getF1SjFz() {
		return f1SjFz;
	}

	public void setF1SjFz(Integer f1SjFz) {
		this.f1SjFz = f1SjFz;
	}

	public Integer getF1Xh() {
		return f1Xh;
	}

	public void setF1Xh(Integer f1Xh) {
		this.f1Xh = f1Xh;
	}

	public BigDecimal getF1Hytjl() {
		return f1Hytjl;
	}

	public void setF1Hytjl(BigDecimal f1Hytjl) {
		this.f1Hytjl = f1Hytjl;
	}

	public Integer getF2SjFz() {
		return f2SjFz;
	}

	public void setF2SjFz(Integer f2SjFz) {
		this.f2SjFz = f2SjFz;
	}

	public Integer getF2Xh() {
		return f2Xh;
	}

	public void setF2Xh(Integer f2Xh) {
		this.f2Xh = f2Xh;
	}

	public BigDecimal getF2Hytjl() {
		return f2Hytjl;
	}

	public void setF2Hytjl(BigDecimal f2Hytjl) {
		this.f2Hytjl = f2Hytjl;
	}

	public Integer getF3SjFz() {
		return f3SjFz;
	}

	public void setF3SjFz(Integer f3SjFz) {
		this.f3SjFz = f3SjFz;
	}

	public Integer getF3Xh() {
		return f3Xh;
	}

	public void setF3Xh(Integer f3Xh) {
		this.f3Xh = f3Xh;
	}

	public BigDecimal getF3Hytjl() {
		return f3Hytjl;
	}

	public void setF3Hytjl(BigDecimal f3Hytjl) {
		this.f3Hytjl = f3Hytjl;
	}

	public Integer getF4SjFz() {
		return f4SjFz;
	}

	public void setF4SjFz(Integer f4SjFz) {
		this.f4SjFz = f4SjFz;
	}

	public Integer getF4Xh() {
		return f4Xh;
	}

	public void setF4Xh(Integer f4Xh) {
		this.f4Xh = f4Xh;
	}

	public BigDecimal getF4Hytjl() {
		return f4Hytjl;
	}

	public void setF4Hytjl(BigDecimal f4Hytjl) {
		this.f4Hytjl = f4Hytjl;
	}

	public Integer getApuSjFz() {
		return apuSjFz;
	}

	public void setApuSjFz(Integer apuSjFz) {
		this.apuSjFz = apuSjFz;
	}

	public Integer getApuXh() {
		return apuXh;
	}

	public void setApuXh(Integer apuXh) {
		this.apuXh = apuXh;
	}

	public BigDecimal getApuHytjl() {
		return apuHytjl;
	}

	public void setApuHytjl(BigDecimal apuHytjl) {
		this.apuHytjl = apuHytjl;
	}

	public Integer getFxxh() {
		return fxxh;
	}

	public void setFxxh(Integer fxxh) {
		this.fxxh = fxxh;
	}

	public Integer getLxqlcs() {
		return lxqlcs;
	}

	public void setLxqlcs(Integer lxqlcs) {
		this.lxqlcs = lxqlcs;
	}

	public String getIdgtksj() {
		return idgtksj;
	}

	public void setIdgtksj(String idgtksj) {
		this.idgtksj = idgtksj;
	}

	public BigDecimal getYyy() {
		return yyy;
	}

	public void setYyy(BigDecimal yyy) {
		this.yyy = yyy;
	}

	public BigDecimal getRyCyl() {
		return ryCyl;
	}

	public void setRyCyl(BigDecimal ryCyl) {
		this.ryCyl = ryCyl;
	}

	public BigDecimal getRyJyl() {
		return ryJyl;
	}

	public void setRyJyl(BigDecimal ryJyl) {
		this.ryJyl = ryJyl;
	}

	public BigDecimal getRySyyl() {
		return rySyyl;
	}

	public void setRySyyl(BigDecimal rySyyl) {
		this.rySyyl = rySyyl;
	}

	public String getFxrid() {
		return fxrid;
	}

	public void setFxrid(String fxrid) {
		this.fxrid = fxrid;
	}

	public String getFxr() {
		return fxr;
	}

	public void setFxr(String fxr) {
		this.fxr = fxr;
	}

	public String getJzid() {
		return jzid;
	}

	public void setJzid(String jzid) {
		this.jzid = jzid;
	}

	public String getJz() {
		return jz;
	}

	public void setJz(String jz) {
		this.jz = jz;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getRydw() {
		return rydw;
	}

	public void setRydw(String rydw) {
		this.rydw = rydw;
	}

	public List<ComponentUse> getPreflightData() {
		return preflightData;
	}

	public void setPreflightData(List<ComponentUse> preflightData) {
		this.preflightData = preflightData;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ComponentUseCount> getComponentUseCounList() {
		return componentUseCounList;
	}

	public void setComponentUseCounList(List<ComponentUseCount> componentUseCounList) {
		this.componentUseCounList = componentUseCounList;
	}

 
}