package com.eray.thjw.produce.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.MaterialTool;

/**
 * 
 * @Description b_s2_013 故障保留
 * @CreateTime 2017年9月25日 下午1:46:21
 * @CreateBy 林龙
 */
public class FailureKeep extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id; //id

    private String dprtcode; //组织机构

    private String whdwid; //维修单位id

    private String whrid;//维护人id

    private Date whsj;//维护时间

    private Integer zt; //状态

    private Integer zcblbs;//再次保留标识

    private String bldh;//保留单号

    private String fjzch; //飞机注册号

    private String zjh; //章节号

    private String hz; //航站
    
    private Integer bs145; //145标识

    private Integer blly; //保留来源
    
    private String bllyid; //保留来源id

    private String bllx; //保留类型

    private String bllxsm; //保留类型描述

    private Integer sxgsRs; //所需工时-人数

    private BigDecimal sxgsXs; //所需工时-小时

    private String blyj; //保留依据

    private String gzms; //故障描述

    private String blyy; //保留原因

    private String lscs;//临时措施

    private Integer mbs; //执行M程序标识

    private String msm;//执行M程序说明

    private Integer obs;//执行O程序标识

    private String osm;//执行O程序说明

    private Integer yxxzbs;//运行限制标识

    private String yxxzsm; //运行限制说明

    private String scSqrbmid;//首次申请人部门id

    private String scSqrid;//首次申请人id

    private String scSqrzzh;//首次申请人执照号

    private Date scSqrq;//首次申请日期

    private Date scBlqx;//首次保留期限

    private String scPzrbmid;//首次批准人部门id

    private String scPzrid;//首次批准人id

    private Date scPzrq;//首次批准日期

    private String scPzyj;//首次批准意见

    private Date scPzczsj;//首次批准操作时间

    private String zcSqrbmid;//再次申请人部门id

    private String zcSqrid;//再次申请人id

    private Date zcSqrq;//再次申请日期

    private Date zcBlqx;//再次保留期限

    private String zcBlyy;//再次保留原因

    private String zcPzrbmid;//再次批准人部门id

    private String zcPzrid;//再次批准人id

    private Date zcPzrq;//再次批准日期

    private String jfpzyj;//局方批准意见

    private String jfpzr;//局方批准人

    private Date jfpzrq;//局方批准日期

    private String gbrbmid;//关闭人部门id

    private String gbrid;//关闭人id

    private Date gbczsj;//关闭操作时间

    private String gbyy;//关闭原因

    private String gzz;//工作者id

    private Date gzrq;//工作日期

    private String jcz;//检查者

    private Date jcrq;//检查日期

    private String gdid;//工单id
    
    private String bjh;//部件号
    
    private String xlh;//部件号
    
    private String fxsj;//飞行时间
    
    private String fxxh;//飞行循环
    
    private String blfxsj;//保留飞行时间
    
    private String blfxxh;//保留飞行循环
    
    private String zcblfxsj;//再次保留飞行时间
    
    private String  zcblfxxh;//再次保留飞行循环

    private FixChapter fixChapter;//章节号
    
    private User whr; //维护人
    
    private Department dprt; //组织机构
    
    private List<Attachment> attachmentList; //d_011 附件表List

    private List<MaterialTool> materialToolList; //器材/工具list集合
    
    private List<String> yxfwdmList; //影响服务代码集合
    private List<String> sjbmdmList; //涉及部门代码集合
    private List<String> yxxzdmList; //运行限制代码集合
    private List<String> blyydmList; //保留原因代码集合
    
    private String isWarning; //是否警告
    private String sjsyStrs; //实际剩余期限
    private String scStrs; //首次期限
    private String zcStrs; //再次期限
    
    private List<String> sjList;
    private List<String> syList;
    private List<String> scblqxList;
    private List<String> zcblqxList;
    
    /** 故障保留单-监控 */
    private List<FailureKeepJK> monitors;
    
    /**
     * 供导出使用
     */
    private String fxsjExcelDatas;
    private String sjExcelDatas;
    private String syExcelDatas;
    private String scblqxExcelDatas;
    private String zcblqxExcelDatas;
    
	public List<MaterialTool> getMaterialToolList() {
		return materialToolList;
	}

	public void setMaterialToolList(List<MaterialTool> materialToolList) {
		this.materialToolList = materialToolList;
	}

	public List<Attachment> getAttachmentList() {
		return attachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		this.attachmentList = attachmentList;
	}

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public Department getDprt() {
		return dprt;
	}

	public void setDprt(Department dprt) {
		this.dprt = dprt;
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

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getZcblbs() {
		return zcblbs;
	}

	public void setZcblbs(Integer zcblbs) {
		this.zcblbs = zcblbs;
	}

	public String getBldh() {
		return bldh;
	}

	public void setBldh(String bldh) {
		this.bldh = bldh;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}

	public String getHz() {
		return hz;
	}

	public void setHz(String hz) {
		this.hz = hz;
	}

	public Integer getBlly() {
		return blly;
	}

	public void setBlly(Integer blly) {
		this.blly = blly;
	}

	public String getBllyid() {
		return bllyid;
	}

	public void setBllyid(String bllyid) {
		this.bllyid = bllyid;
	}

	public String getBllx() {
		return bllx;
	}

	public void setBllx(String bllx) {
		this.bllx = bllx;
	}

	public String getBllxsm() {
		return bllxsm;
	}

	public void setBllxsm(String bllxsm) {
		this.bllxsm = bllxsm;
	}

	public Integer getSxgsRs() {
		return sxgsRs;
	}

	public void setSxgsRs(Integer sxgsRs) {
		this.sxgsRs = sxgsRs;
	}

	public BigDecimal getSxgsXs() {
		return sxgsXs;
	}

	public void setSxgsXs(BigDecimal sxgsXs) {
		this.sxgsXs = sxgsXs;
	}

	public String getBlyj() {
		return blyj;
	}

	public void setBlyj(String blyj) {
		this.blyj = blyj;
	}

	public String getGzms() {
		return gzms;
	}

	public void setGzms(String gzms) {
		this.gzms = gzms;
	}

	public String getBlyy() {
		return blyy;
	}

	public void setBlyy(String blyy) {
		this.blyy = blyy;
	}

	public String getLscs() {
		return lscs;
	}

	public void setLscs(String lscs) {
		this.lscs = lscs;
	}

	public Integer getMbs() {
		return mbs;
	}

	public void setMbs(Integer mbs) {
		this.mbs = mbs;
	}

	public String getMsm() {
		return msm;
	}

	public void setMsm(String msm) {
		this.msm = msm;
	}

	public Integer getObs() {
		return obs;
	}

	public void setObs(Integer obs) {
		this.obs = obs;
	}

	public String getOsm() {
		return osm;
	}

	public void setOsm(String osm) {
		this.osm = osm;
	}

	public Integer getYxxzbs() {
		return yxxzbs;
	}

	public void setYxxzbs(Integer yxxzbs) {
		this.yxxzbs = yxxzbs;
	}

	public String getYxxzsm() {
		return yxxzsm;
	}

	public void setYxxzsm(String yxxzsm) {
		this.yxxzsm = yxxzsm;
	}

	public String getScSqrbmid() {
		return scSqrbmid;
	}

	public void setScSqrbmid(String scSqrbmid) {
		this.scSqrbmid = scSqrbmid;
	}

	public String getScSqrid() {
		return scSqrid;
	}

	public void setScSqrid(String scSqrid) {
		this.scSqrid = scSqrid;
	}

	public String getScSqrzzh() {
		return scSqrzzh;
	}

	public void setScSqrzzh(String scSqrzzh) {
		this.scSqrzzh = scSqrzzh;
	}

	public Date getScSqrq() {
		return scSqrq;
	}

	public void setScSqrq(Date scSqrq) {
		this.scSqrq = scSqrq;
	}

	public Date getScBlqx() {
		return scBlqx;
	}

	public void setScBlqx(Date scBlqx) {
		this.scBlqx = scBlqx;
	}

	public String getScPzrbmid() {
		return scPzrbmid;
	}

	public void setScPzrbmid(String scPzrbmid) {
		this.scPzrbmid = scPzrbmid;
	}

	public String getScPzrid() {
		return scPzrid;
	}

	public void setScPzrid(String scPzrid) {
		this.scPzrid = scPzrid;
	}

	public Date getScPzrq() {
		return scPzrq;
	}

	public void setScPzrq(Date scPzrq) {
		this.scPzrq = scPzrq;
	}

	public String getScPzyj() {
		return scPzyj;
	}

	public void setScPzyj(String scPzyj) {
		this.scPzyj = scPzyj;
	}

	public Date getScPzczsj() {
		return scPzczsj;
	}

	public void setScPzczsj(Date scPzczsj) {
		this.scPzczsj = scPzczsj;
	}

	public String getZcSqrbmid() {
		return zcSqrbmid;
	}

	public void setZcSqrbmid(String zcSqrbmid) {
		this.zcSqrbmid = zcSqrbmid;
	}

	public String getZcSqrid() {
		return zcSqrid;
	}

	public void setZcSqrid(String zcSqrid) {
		this.zcSqrid = zcSqrid;
	}

	public Date getZcSqrq() {
		return zcSqrq;
	}

	public void setZcSqrq(Date zcSqrq) {
		this.zcSqrq = zcSqrq;
	}

	public Date getZcBlqx() {
		return zcBlqx;
	}

	public void setZcBlqx(Date zcBlqx) {
		this.zcBlqx = zcBlqx;
	}

	public String getZcBlyy() {
		return zcBlyy;
	}

	public void setZcBlyy(String zcBlyy) {
		this.zcBlyy = zcBlyy;
	}

	public String getZcPzrbmid() {
		return zcPzrbmid;
	}

	public void setZcPzrbmid(String zcPzrbmid) {
		this.zcPzrbmid = zcPzrbmid;
	}

	public String getZcPzrid() {
		return zcPzrid;
	}

	public void setZcPzrid(String zcPzrid) {
		this.zcPzrid = zcPzrid;
	}

	public Date getZcPzrq() {
		return zcPzrq;
	}

	public void setZcPzrq(Date zcPzrq) {
		this.zcPzrq = zcPzrq;
	}

	public String getJfpzyj() {
		return jfpzyj;
	}

	public void setJfpzyj(String jfpzyj) {
		this.jfpzyj = jfpzyj;
	}

	public String getJfpzr() {
		return jfpzr;
	}

	public void setJfpzr(String jfpzr) {
		this.jfpzr = jfpzr;
	}

	public Date getJfpzrq() {
		return jfpzrq;
	}

	public void setJfpzrq(Date jfpzrq) {
		this.jfpzrq = jfpzrq;
	}

	public String getGbrbmid() {
		return gbrbmid;
	}

	public void setGbrbmid(String gbrbmid) {
		this.gbrbmid = gbrbmid;
	}

	public String getGbrid() {
		return gbrid;
	}

	public void setGbrid(String gbrid) {
		this.gbrid = gbrid;
	}

	public Date getGbczsj() {
		return gbczsj;
	}

	public void setGbczsj(Date gbczsj) {
		this.gbczsj = gbczsj;
	}

	public String getGbyy() {
		return gbyy;
	}

	public void setGbyy(String gbyy) {
		this.gbyy = gbyy;
	}

	public String getGzz() {
		return gzz;
	}

	public void setGzz(String gzz) {
		this.gzz = gzz;
	}

	public Date getGzrq() {
		return gzrq;
	}

	public void setGzrq(Date gzrq) {
		this.gzrq = gzrq;
	}

	public String getJcz() {
		return jcz;
	}

	public void setJcz(String jcz) {
		this.jcz = jcz;
	}

	public Date getJcrq() {
		return jcrq;
	}

	public void setJcrq(Date jcrq) {
		this.jcrq = jcrq;
	}

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

	public Integer getBs145() {
		return bs145;
	}

	public void setBs145(Integer bs145) {
		this.bs145 = bs145;
	}

	public String getBjh() {
		return bjh;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh;
	}

	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}

	public String getFxsj() {
		return fxsj;
	}

	public void setFxsj(String fxsj) {
		this.fxsj = fxsj;
	}

	public String getFxxh() {
		return fxxh;
	}

	public void setFxxh(String fxxh) {
		this.fxxh = fxxh;
	}

	public List<String> getYxfwdmList() {
		return yxfwdmList;
	}

	public void setYxfwdmList(List<String> yxfwdmList) {
		this.yxfwdmList = yxfwdmList;
	}

	public List<String> getSjbmdmList() {
		return sjbmdmList;
	}

	public void setSjbmdmList(List<String> sjbmdmList) {
		this.sjbmdmList = sjbmdmList;
	}

	public List<String> getYxxzdmList() {
		return yxxzdmList;
	}

	public void setYxxzdmList(List<String> yxxzdmList) {
		this.yxxzdmList = yxxzdmList;
	}

	public List<String> getBlyydmList() {
		return blyydmList;
	}

	public void setBlyydmList(List<String> blyydmList) {
		this.blyydmList = blyydmList;
	}

	public String getBlfxsj() {
		return blfxsj;
	}

	public void setBlfxsj(String blfxsj) {
		this.blfxsj = blfxsj;
	}

	public String getBlfxxh() {
		return blfxxh;
	}

	public void setBlfxxh(String blfxxh) {
		this.blfxxh = blfxxh;
	}

	public String getZcblfxsj() {
		return zcblfxsj;
	}

	public void setZcblfxsj(String zcblfxsj) {
		this.zcblfxsj = zcblfxsj;
	}

	public String getZcblfxxh() {
		return zcblfxxh;
	}

	public void setZcblfxxh(String zcblfxxh) {
		this.zcblfxxh = zcblfxxh;
	}

	public String getIsWarning() {
		return isWarning;
	}

	public void setIsWarning(String isWarning) {
		this.isWarning = isWarning;
	}

	public String getSjsyStrs() {
		return sjsyStrs;
	}

	public void setSjsyStrs(String sjsyStrs) {
		this.sjsyStrs = sjsyStrs;
	}

	public String getScStrs() {
		return scStrs;
	}

	public void setScStrs(String scStrs) {
		this.scStrs = scStrs;
	}

	public String getZcStrs() {
		return zcStrs;
	}

	public void setZcStrs(String zcStrs) {
		this.zcStrs = zcStrs;
	}

	public List<String> getScblqxList() {
		return scblqxList;
	}

	public void setScblqxList(List<String> scblqxList) {
		this.scblqxList = scblqxList;
	}

	public List<String> getZcblqxList() {
		return zcblqxList;
	}

	public void setZcblqxList(List<String> zcblqxList) {
		this.zcblqxList = zcblqxList;
	}

	public List<String> getSjList() {
		return sjList;
	}

	public void setSjList(List<String> sjList) {
		this.sjList = sjList;
	}

	public List<String> getSyList() {
		return syList;
	}

	public void setSyList(List<String> syList) {
		this.syList = syList;
	}

	public String getFxsjExcelDatas() {
		return fxsjExcelDatas;
	}

	public void setFxsjExcelDatas(String fxsjExcelDatas) {
		this.fxsjExcelDatas = fxsjExcelDatas;
	}

	public String getSjExcelDatas() {
		return sjExcelDatas;
	}

	public void setSjExcelDatas(String sjExcelDatas) {
		this.sjExcelDatas = sjExcelDatas;
	}

	public String getSyExcelDatas() {
		return syExcelDatas;
	}

	public void setSyExcelDatas(String syExcelDatas) {
		this.syExcelDatas = syExcelDatas;
	}

	public String getScblqxExcelDatas() {
		return scblqxExcelDatas;
	}

	public void setScblqxExcelDatas(String scblqxExcelDatas) {
		this.scblqxExcelDatas = scblqxExcelDatas;
	}

	public String getZcblqxExcelDatas() {
		return zcblqxExcelDatas;
	}

	public void setZcblqxExcelDatas(String zcblqxExcelDatas) {
		this.zcblqxExcelDatas = zcblqxExcelDatas;
	}

	public List<FailureKeepJK> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<FailureKeepJK> monitors) {
		this.monitors = monitors;
	}

}