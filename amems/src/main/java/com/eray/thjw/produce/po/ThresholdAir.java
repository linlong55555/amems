package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * 
 * @Description t_threshold_air 系统阀值设置-飞机
 * @CreateTime 2017年9月19日 下午3:13:18
 * @CreateBy 林龙
 */
public class ThresholdAir extends BizEntity{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String fjzch; //飞机注册号
	
	private String dprtcode; //组织机构
	
	private String jklbh; //监控项目编号
	
	private String jkflbh;//监控分类编号
	
	private Integer fz;//阀值
	
	private String whbmid; //维护部门id。关联组织结构表
	
	private String whrid; //维护人id。关联用户表

	private Date whsj;//维护时间。yyyymmddhh24miss

	private Integer lx; //类型：1按百分比、2按数值
	
	public Integer getLx() {
		return lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getJklbh() {
		return jklbh;
	}

	public void setJklbh(String jklbh) {
		this.jklbh = jklbh;
	}

	public String getJkflbh() {
		return jkflbh;
	}

	public void setJkflbh(String jkflbh) {
		this.jkflbh = jkflbh;
	}

	public Integer getFz() {
		return fz;
	}

	public void setFz(Integer fz) {
		this.fz = fz;
	}

	public String getWhbmid() {
		return whbmid;
	}

	public void setWhbmid(String whbmid) {
		this.whbmid = whbmid;
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
}