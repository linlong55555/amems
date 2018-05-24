package com.eray.thjw.flightdata.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;

public class FlightRecord extends BizEntity{
	
    private String id;

    private String fxjldid;

    private Integer hc;

    private String hcms;

    private Integer xdbs;

    private Integer fxsjXs;

    private Integer fxsjFz;

    private Integer f1sjXs;

    private Integer f1sjFz;

    private Integer f2sjXs;

    private Integer f2sjFz;

    private BigDecimal qljxh;

    private Integer jcsjXs;

    private Integer jcsjFz;

    private BigDecimal jcxh;

    private BigDecimal ylFxq;

    private BigDecimal ylFxh;

    private BigDecimal f1N1;

    private BigDecimal f1N2;

    private BigDecimal f1N3;

    private BigDecimal f1N4;

    private BigDecimal f1N5;

    private BigDecimal f1N6;

    private BigDecimal f1Hy;

    private BigDecimal f1Wdyd;

    private BigDecimal f1Glyd;

    private BigDecimal f2N1;

    private BigDecimal f2N2;

    private BigDecimal f2N3;

    private BigDecimal f2N4;

    private BigDecimal f2N5;

    private BigDecimal f2N6;

    private BigDecimal f2Hy;

    private BigDecimal f2Wdyd;

    private BigDecimal f2Glyd;

    private Integer ssdsjXs;

    private Integer ssdsjFz;

    private BigDecimal dgxh;

    private BigDecimal ts1;

    private BigDecimal ts2;

    private BigDecimal mgb;

    private BigDecimal igb;

    private BigDecimal tgb;

    private String tsfxpzid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String bz;

    private String jhF1;

    private String xlhF1;

    private String jhF2;

    private String xlhF2;

    private String jhJc;

    private String xlhJc;

    private String jhWdg;

    private String xlhWdg;

    private String jhSsd;

    private String xlhSsd;
    
    private String fjzch; //虚拟字段：飞机注册号
    
    private String fxDateBegin; //虚拟字段: 飞行开始时间
    
    private String fxDateEnd; //虚拟字段: 飞行结束时间
    
    private String jlbym; //虚拟字段: 记录本页码
    
    private String fxrq; //虚拟字段: 飞行日期
    
    private String fxsj; //虚拟字段: 本次飞行时间
    
    private String fxsjQljxhZ; //虚拟字段: 总飞行时间和总起落循环，其中用#分割(不包含飞机初始化数据)
    
    private String yl; //虚拟字段: 燃油量
    
    private String ssdsj; //虚拟字段: 本次搜索灯时间
    
    private String ssdsjZ; //虚拟字段: 总次搜索灯时间
    
    private String zdjsyy;
    
    private String dgxhZ; //虚拟字段: 总外吊挂循环
    
    private String jcxhZ; //虚拟字段:总绞车循环
    
    private String jcsjZ; //虚拟字段:总绞车循环时间
    
    private String jcsj; //虚拟字段:总绞车循环时间
    
    private String f1SjN16Z; //虚拟字段:1#左发总时间和N1-6，其中用#分割
    
    private String f2SjN16Z; //虚拟字段:2#右发总时间和N1-6，其中用#分割
    
    private String ms; //虚拟字段:--特殊情况
    
    private List<PreflightData> preflightDatas = new ArrayList<PreflightData>();	//虚拟字段：飞行前数据
    
    private String avFxr;	// 电子放行人
    
    private String avZzh;	// 电子执照号
    
    private String meFxr;	// 机械放行人
    
    private String meZzh;	// 机械执照号
    
    private String jzshrk;	// 机长适航认可
    
    private Integer tbbs;	// 同步标识
    
    private String dprtcode;	//组织机构
    
    private String fxjldh;	//飞行记录单号
    
    private String tsqk;	//特殊情况
    
    
	public String getTsqk() {
		return tsqk;
	}

	public void setTsqk(String tsqk) {
		this.tsqk = tsqk;
	}

	public String getJcsj() {
		return jcsj;
	}

	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}

	public String getFxrq() {
		return fxrq;
	}

	public void setFxrq(String fxrq) {
		this.fxrq = fxrq;
	}

	public String getFxsj() {
		return fxsj;
	}

	public void setFxsj(String fxsj) {
		this.fxsj = fxsj;
	}

	public String getFxsjQljxhZ() {
		return fxsjQljxhZ;
	}

	public void setFxsjQljxhZ(String fxsjQljxhZ) {
		this.fxsjQljxhZ = fxsjQljxhZ;
	}

	public String getYl() {
		return yl;
	}

	public void setYl(String yl) {
		this.yl = yl;
	}

	public String getSsdsj() {
		return ssdsj;
	}

	public void setSsdsj(String ssdsj) {
		this.ssdsj = ssdsj;
	}

	public String getSsdsjZ() {
		return ssdsjZ;
	}

	public void setSsdsjZ(String ssdsjZ) {
		this.ssdsjZ = ssdsjZ;
	}

	

	public String getDgxhZ() {
		return dgxhZ;
	}

	public void setDgxhZ(String dgxhZ) {
		this.dgxhZ = dgxhZ;
	}

	public String getJcxhZ() {
		return jcxhZ;
	}

	public void setJcxhZ(String jcxhZ) {
		this.jcxhZ = jcxhZ;
	}

	public String getJcsjZ() {
		return jcsjZ;
	}

	public void setJcsjZ(String jcsjZ) {
		this.jcsjZ = jcsjZ;
	}

	public String getF1SjN16Z() {
		return f1SjN16Z;
	}

	public void setF1SjN16Z(String f1SjN16Z) {
		this.f1SjN16Z = f1SjN16Z;
	}

	public String getF2SjN16Z() {
		return f2SjN16Z;
	}

	public void setF2SjN16Z(String f2SjN16Z) {
		this.f2SjN16Z = f2SjN16Z;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public String getJlbym() {
		return jlbym;
	}

	public void setJlbym(String jlbym) {
		this.jlbym = jlbym;
	}

	public String getFxDateBegin() {
		return fxDateBegin;
	}

	public void setFxDateBegin(String fxDateBegin) {
		this.fxDateBegin = fxDateBegin;
	}

	public String getFxDateEnd() {
		return fxDateEnd;
	}

	public void setFxDateEnd(String fxDateEnd) {
		this.fxDateEnd = fxDateEnd;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFxjldid() {
		return fxjldid;
	}

	public void setFxjldid(String fxjldid) {
		this.fxjldid = fxjldid;
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

	public Integer getXdbs() {
		return xdbs;
	}

	public void setXdbs(Integer xdbs) {
		this.xdbs = xdbs;
	}

	public Integer getFxsjXs() {
		return fxsjXs;
	}

	public void setFxsjXs(Integer fxsjXs) {
		this.fxsjXs = fxsjXs;
	}

	public Integer getFxsjFz() {
		return fxsjFz;
	}

	public void setFxsjFz(Integer fxsjFz) {
		this.fxsjFz = fxsjFz;
	}

	public Integer getF1sjXs() {
		return f1sjXs;
	}

	public void setF1sjXs(Integer f1sjXs) {
		this.f1sjXs = f1sjXs;
	}

	public Integer getF1sjFz() {
		return f1sjFz;
	}

	public void setF1sjFz(Integer f1sjFz) {
		this.f1sjFz = f1sjFz;
	}

	public Integer getF2sjXs() {
		return f2sjXs;
	}

	public void setF2sjXs(Integer f2sjXs) {
		this.f2sjXs = f2sjXs;
	}

	public Integer getF2sjFz() {
		return f2sjFz;
	}

	public void setF2sjFz(Integer f2sjFz) {
		this.f2sjFz = f2sjFz;
	}

	public BigDecimal getQljxh() {
		return qljxh;
	}

	public void setQljxh(BigDecimal qljxh) {
		this.qljxh = qljxh;
	}

	public Integer getJcsjXs() {
		return jcsjXs;
	}

	public void setJcsjXs(Integer jcsjXs) {
		this.jcsjXs = jcsjXs;
	}

	public Integer getJcsjFz() {
		return jcsjFz;
	}

	public void setJcsjFz(Integer jcsjFz) {
		this.jcsjFz = jcsjFz;
	}

	public BigDecimal getJcxh() {
		return jcxh;
	}

	public void setJcxh(BigDecimal jcxh) {
		this.jcxh = jcxh;
	}

	public BigDecimal getYlFxq() {
		return ylFxq;
	}

	public void setYlFxq(BigDecimal ylFxq) {
		this.ylFxq = ylFxq;
	}

	public BigDecimal getYlFxh() {
		return ylFxh;
	}

	public void setYlFxh(BigDecimal ylFxh) {
		this.ylFxh = ylFxh;
	}

	public BigDecimal getF1N1() {
		return f1N1;
	}

	public void setF1N1(BigDecimal f1n1) {
		f1N1 = f1n1;
	}

	public BigDecimal getF1N2() {
		return f1N2;
	}

	public void setF1N2(BigDecimal f1n2) {
		f1N2 = f1n2;
	}

	public BigDecimal getF1N3() {
		return f1N3;
	}

	public void setF1N3(BigDecimal f1n3) {
		f1N3 = f1n3;
	}

	public BigDecimal getF1N4() {
		return f1N4;
	}

	public void setF1N4(BigDecimal f1n4) {
		f1N4 = f1n4;
	}

	public BigDecimal getF1N5() {
		return f1N5;
	}

	public void setF1N5(BigDecimal f1n5) {
		f1N5 = f1n5;
	}

	public BigDecimal getF1N6() {
		return f1N6;
	}

	public void setF1N6(BigDecimal f1n6) {
		f1N6 = f1n6;
	}

	public BigDecimal getF1Hy() {
		return f1Hy;
	}

	public void setF1Hy(BigDecimal f1Hy) {
		this.f1Hy = f1Hy;
	}

	public BigDecimal getF1Wdyd() {
		return f1Wdyd;
	}

	public void setF1Wdyd(BigDecimal f1Wdyd) {
		this.f1Wdyd = f1Wdyd;
	}

	public BigDecimal getF1Glyd() {
		return f1Glyd;
	}

	public void setF1Glyd(BigDecimal f1Glyd) {
		this.f1Glyd = f1Glyd;
	}

	public BigDecimal getF2N1() {
		return f2N1;
	}

	public void setF2N1(BigDecimal f2n1) {
		f2N1 = f2n1;
	}

	public BigDecimal getF2N2() {
		return f2N2;
	}

	public void setF2N2(BigDecimal f2n2) {
		f2N2 = f2n2;
	}

	public BigDecimal getF2N3() {
		return f2N3;
	}

	public void setF2N3(BigDecimal f2n3) {
		f2N3 = f2n3;
	}

	public BigDecimal getF2N4() {
		return f2N4;
	}

	public void setF2N4(BigDecimal f2n4) {
		f2N4 = f2n4;
	}

	public BigDecimal getF2N5() {
		return f2N5;
	}

	public void setF2N5(BigDecimal f2n5) {
		f2N5 = f2n5;
	}

	public BigDecimal getF2N6() {
		return f2N6;
	}

	public void setF2N6(BigDecimal f2n6) {
		f2N6 = f2n6;
	}

	public BigDecimal getF2Hy() {
		return f2Hy;
	}

	public void setF2Hy(BigDecimal f2Hy) {
		this.f2Hy = f2Hy;
	}

	public BigDecimal getF2Wdyd() {
		return f2Wdyd;
	}

	public void setF2Wdyd(BigDecimal f2Wdyd) {
		this.f2Wdyd = f2Wdyd;
	}

	public BigDecimal getF2Glyd() {
		return f2Glyd;
	}

	public void setF2Glyd(BigDecimal f2Glyd) {
		this.f2Glyd = f2Glyd;
	}

	public Integer getSsdsjXs() {
		return ssdsjXs;
	}

	public void setSsdsjXs(Integer ssdsjXs) {
		this.ssdsjXs = ssdsjXs;
	}

	public Integer getSsdsjFz() {
		return ssdsjFz;
	}

	public void setSsdsjFz(Integer ssdsjFz) {
		this.ssdsjFz = ssdsjFz;
	}

	public BigDecimal getDgxh() {
		return dgxh;
	}

	public void setDgxh(BigDecimal dgxh) {
		this.dgxh = dgxh;
	}

	public BigDecimal getTs1() {
		return ts1;
	}

	public void setTs1(BigDecimal ts1) {
		this.ts1 = ts1;
	}

	public BigDecimal getTs2() {
		return ts2;
	}

	public void setTs2(BigDecimal ts2) {
		this.ts2 = ts2;
	}

	public BigDecimal getMgb() {
		return mgb;
	}

	public void setMgb(BigDecimal mgb) {
		this.mgb = mgb;
	}

	public BigDecimal getIgb() {
		return igb;
	}

	public void setIgb(BigDecimal igb) {
		this.igb = igb;
	}

	public BigDecimal getTgb() {
		return tgb;
	}

	public void setTgb(BigDecimal tgb) {
		this.tgb = tgb;
	}

	public String getTsfxpzid() {
		return tsfxpzid;
	}

	public void setTsfxpzid(String tsfxpzid) {
		this.tsfxpzid = tsfxpzid;
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

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getJhF1() {
		return jhF1;
	}

	public void setJhF1(String jhF1) {
		this.jhF1 = jhF1;
	}

	public String getXlhF1() {
		return xlhF1;
	}

	public void setXlhF1(String xlhF1) {
		this.xlhF1 = xlhF1;
	}

	public String getJhF2() {
		return jhF2;
	}

	public void setJhF2(String jhF2) {
		this.jhF2 = jhF2;
	}

	public String getXlhF2() {
		return xlhF2;
	}

	public void setXlhF2(String xlhF2) {
		this.xlhF2 = xlhF2;
	}

	public String getJhJc() {
		return jhJc;
	}

	public void setJhJc(String jhJc) {
		this.jhJc = jhJc;
	}

	public String getXlhJc() {
		return xlhJc;
	}

	public void setXlhJc(String xlhJc) {
		this.xlhJc = xlhJc;
	}

	public String getJhWdg() {
		return jhWdg;
	}

	public void setJhWdg(String jhWdg) {
		this.jhWdg = jhWdg;
	}

	public String getXlhWdg() {
		return xlhWdg;
	}

	public void setXlhWdg(String xlhWdg) {
		this.xlhWdg = xlhWdg;
	}

	public String getJhSsd() {
		return jhSsd;
	}

	public void setJhSsd(String jhSsd) {
		this.jhSsd = jhSsd;
	}

	public String getXlhSsd() {
		return xlhSsd;
	}

	public void setXlhSsd(String xlhSsd) {
		this.xlhSsd = xlhSsd;
	}

	public List<PreflightData> getPreflightDatas() {
		return preflightDatas;
	}

	public void setPreflightDatas(List<PreflightData> preflightDatas) {
		this.preflightDatas = preflightDatas;
	}

	public String getAvFxr() {
		return avFxr;
	}

	public void setAvFxr(String avFxr) {
		this.avFxr = avFxr;
	}

	public String getAvZzh() {
		return avZzh;
	}

	public void setAvZzh(String avZzh) {
		this.avZzh = avZzh;
	}

	public String getMeFxr() {
		return meFxr;
	}

	public void setMeFxr(String meFxr) {
		this.meFxr = meFxr;
	}

	public String getMeZzh() {
		return meZzh;
	}

	public void setMeZzh(String meZzh) {
		this.meZzh = meZzh;
	}

	public String getJzshrk() {
		return jzshrk;
	}

	public void setJzshrk(String jzshrk) {
		this.jzshrk = jzshrk;
	}

	public Integer getTbbs() {
		return tbbs;
	}

	public void setTbbs(Integer tbbs) {
		this.tbbs = tbbs;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getFxjldh() {
		return fxjldh;
	}

	public void setFxjldh(String fxjldh) {
		this.fxjldh = fxjldh;
	}
    
}