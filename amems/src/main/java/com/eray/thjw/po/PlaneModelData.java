package com.eray.thjw.po;

/**
 * @author 梅志亮
 * @time 2016-08-15
 * @describe 机型数据2的实体类 D_004表
 */
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.productionplan.po.PlaneData;

@SuppressWarnings("serial")
public class PlaneModelData extends BizEntity {

	public List<JX_BJ> getjX_BJList() {
		return jX_BJList;
	}

	public void setjX_BJList(List<JX_BJ> jX_BJList) {
		this.jX_BJList = jX_BJList;
	}

	private List<JX_BJ> jX_BJList; // 关联部件号的集合

	private String fjjx; // 飞机机型

	private  BigDecimal rJsfxsj; // 机身日飞行时间

	private BigDecimal rSsdsj; // 搜索灯日使用时间

	private BigDecimal rJcsj; // 绞车日使用时间

	private Integer rQljxh; // 起落架日使用时间

	private Integer rJcxh; // 绞车日循环使用量

	private Integer rWdgxh; // 外吊挂日循环使用量

	private Integer rSsdxh; // 搜索灯日循环使用量

	private Integer rN1; // N1日使用量

	private Integer rN2; // N2日使用量

	private Integer rN3; // N3日使用量

	private Integer rN4; // N4日使用量

	private Integer rN5; // N5日使用量

	private Integer rN6; // N6日使用量

	private Integer rTsjk1; // 特殊监控1

	private Integer rTsjk2; // 特殊监控2

	private String bmid; // 部门Id

	private String cjrid; // 创建人ID

	private Date cjsj; // 创建时间

	private Integer zt; // 状态

	private Integer gsDjjh; // 定检计划计算工时

	private Integer isTsqk; // 是否有特殊情况

	private String fjzch; // 虚拟字段：飞机注册号

	private String zdid; // 拼接联合主键

	private List<PlaneData> planeDataList;

	private String dprtname; 
	
	private String displayname;
	
	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getZdid() {
		return zdid;
	}

	public void setZdid(String zdid) {
		this.zdid = zdid;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public Integer getIsTsqk() {
		return isTsqk;
	}

	public void setIsTsqk(Integer isTsqk) {
		this.isTsqk = isTsqk;
	}

	public Integer getGsDjjh() {
		return gsDjjh;
	}

	public void setGsDjjh(Integer gsDjjh) {
		this.gsDjjh = gsDjjh;
	}
	public String getFjjx() {
		return fjjx;
	}
	public void setFjjx(String fjjx) {
		this.fjjx = fjjx == null ? null : fjjx.trim();
	}

	public BigDecimal getrJsfxsj() {
		if(rJsfxsj == null){
    		return rJsfxsj;
    	}
        return rJsfxsj.setScale(0);
	}

	public void setrJsfxsj(BigDecimal rJsfxsj) {
		this.rJsfxsj = rJsfxsj;
	}

	public BigDecimal getrSsdsj() {
		if(rSsdsj == null){
    		return rSsdsj;
    	}
        return rSsdsj.setScale(0);
	}

	public void setrSsdsj(BigDecimal rSsdsj) {
		this.rSsdsj = rSsdsj;
	}

	public BigDecimal getrJcsj() {
		if(rJcsj == null){
    		return rJcsj;
    	}
        return rJcsj.setScale(0);
	}

	public void setrJcsj(BigDecimal rJcsj) {
		this.rJcsj = rJcsj;
	}

	public Integer getrQljxh() {
		return rQljxh;
	}

	public void setrQljxh(Integer rQljxh) {
		this.rQljxh = rQljxh;
	}

	public Integer getrJcxh() {
		return rJcxh;
	}

	public void setrJcxh(Integer rJcxh) {
		this.rJcxh = rJcxh;
	}

	public Integer getrWdgxh() {
		return rWdgxh;
	}

	public void setrWdgxh(Integer rWdgxh) {
		this.rWdgxh = rWdgxh;
	}

	public Integer getrSsdxh() {
		return rSsdxh;
	}

	public void setrSsdxh(Integer rSsdxh) {
		this.rSsdxh = rSsdxh;
	}

	public Integer getrN1() {
		return rN1;
	}

	public void setrN1(Integer rN1) {
		this.rN1 = rN1;
	}

	public Integer getrN2() {
		return rN2;
	}

	public void setrN2(Integer rN2) {
		this.rN2 = rN2;
	}

	public Integer getrN3() {
		return rN3;
	}

	public void setrN3(Integer rN3) {
		this.rN3 = rN3;
	}

	public Integer getrN4() {
		return rN4;
	}

	public void setrN4(Integer rN4) {
		this.rN4 = rN4;
	}

	public Integer getrN5() {
		return rN5;
	}

	public void setrN5(Integer rN5) {
		this.rN5 = rN5;
	}

	public Integer getrN6() {
		return rN6;
	}

	public void setrN6(Integer rN6) {
		this.rN6 = rN6;
	}

	public Integer getrTsjk1() {
		return rTsjk1;
	}

	public void setrTsjk1(Integer rTsjk1) {
		this.rTsjk1 = rTsjk1;
	}

	public Integer getrTsjk2() {
		return rTsjk2;
	}

	public void setrTsjk2(Integer rTsjk2) {
		this.rTsjk2 = rTsjk2;
	}

	public String getBmid() {
		return bmid;
	}

	public void setBmid(String bmid) {
		this.bmid = bmid == null ? null : bmid.trim();
	}

	public String getCjrid() {
		return cjrid;
	}

	public void setCjrid(String cjrid) {
		this.cjrid = cjrid == null ? null : cjrid.trim();
	}

	public Date getCjsj() {
		return cjsj;
	}

	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public List<PlaneData> getPlaneDataList() {
		return planeDataList;
	}

	public void setPlaneDataList(List<PlaneData> planeDataList) {
		this.planeDataList = planeDataList;
	}
}