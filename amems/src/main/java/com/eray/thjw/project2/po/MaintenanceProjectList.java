package com.eray.thjw.project2.po;

import java.util.List;

/**
 * 维修项目导入
 */
public class MaintenanceProjectList {

	/**维修项目**/
	private String rwh; //任务号
	private String rwms; //任务描述
	private String wxxmdlid; //维修项目大类id
	private String wxxmdlbh; //维修项目大类编号
	private String wxxmlx; //分类
	private String zjh; //ATA章节号
	private String cmph; //CMP/CAMP号
	private String ckh; //参考号
	private String ckwj; //参考文件
	private String gzlx; //工作类别
	private String xmlx; //项目类型
	private String isbj; //RII 
	private String ali; //ALI
	private String syx; //适用性
	private String access; //接近 *
	private List<String> accessList; //接近id+盖板号
	private String zone; //区域 *
	private String fjzw; //飞机站位 *
	private String jhgsrs; //计划人数
	private String jhgsxss; //每人耗时（小时数）
	private String jsgs; //计算公式
	private String ishdwz; //后到为准
	private String bz; //备注
	
	public String getRwh() {
		return rwh;
	}
	public void setRwh(String rwh) {
		this.rwh = rwh;
	}
	public String getRwms() {
		return rwms;
	}
	public void setRwms(String rwms) {
		this.rwms = rwms;
	}
	public String getWxxmdlid() {
		return wxxmdlid;
	}
	public void setWxxmdlid(String wxxmdlid) {
		this.wxxmdlid = wxxmdlid;
	}
	public String getWxxmlx() {
		return wxxmlx;
	}
	public void setWxxmlx(String wxxmlx) {
		this.wxxmlx = wxxmlx;
	}
	public String getZjh() {
		return zjh;
	}
	public void setZjh(String zjh) {
		this.zjh = zjh;
	}
	public String getCmph() {
		return cmph;
	}
	public void setCmph(String cmph) {
		this.cmph = cmph;
	}
	public String getCkh() {
		return ckh;
	}
	public void setCkh(String ckh) {
		this.ckh = ckh;
	}
	public String getCkwj() {
		return ckwj;
	}
	public void setCkwj(String ckwj) {
		this.ckwj = ckwj;
	}
	public String getGzlx() {
		return gzlx;
	}
	public void setGzlx(String gzlx) {
		this.gzlx = gzlx;
	}
	public String getXmlx() {
		return xmlx;
	}
	public void setXmlx(String xmlx) {
		this.xmlx = xmlx;
	}
	public String getIsbj() {
		return isbj;
	}
	public void setIsbj(String isbj) {
		this.isbj = isbj;
	}
	public String getAli() {
		return ali;
	}
	public void setAli(String ali) {
		this.ali = ali;
	}
	public String getSyx() {
		return syx;
	}
	public void setSyx(String syx) {
		this.syx = syx;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getFjzw() {
		return fjzw;
	}
	public void setFjzw(String fjzw) {
		this.fjzw = fjzw;
	}
	public String getJhgsrs() {
		return jhgsrs;
	}
	public void setJhgsrs(String jhgsrs) {
		this.jhgsrs = jhgsrs;
	}
	public String getJhgsxss() {
		return jhgsxss;
	}
	public void setJhgsxss(String jhgsxss) {
		this.jhgsxss = jhgsxss;
	}
	public String getJsgs() {
		return jsgs;
	}
	public void setJsgs(String jsgs) {
		this.jsgs = jsgs;
	}
	public String getIshdwz() {
		return ishdwz;
	}
	public void setIshdwz(String ishdwz) {
		this.ishdwz = ishdwz;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getWxxmdlbh() {
		return wxxmdlbh;
	}
	public void setWxxmdlbh(String wxxmdlbh) {
		this.wxxmdlbh = wxxmdlbh;
	}
	public List<String> getAccessList() {
		return accessList;
	}
	public void setAccessList(List<String> accessList) {
		this.accessList = accessList;
	}
	
}
