package com.eray.thjw.baseinfo.po;

import java.util.Date;

/** 
 * @Description 飞机关键部位表，对应表D_02001 项目信息-关键部件
 * @CreateTime 2017-9-25 下午4:38:09
 * @CreateBy 甘清	
 */
public class ProjectMainArea {
	private String id; //数据库主键
	private String mainid; //项目ID
	private String whbmid; //维护部门id
	private String whrid; //维护人id
	private Date whsj; //维护时间
	private Integer zt; //状态
	private Integer wz; //位置：11机身、21 1发、22 2发、23 3发、 24 4发、 31 APU
	private String jh;  //件号
	private String xlh; //序列号
	private String xh; //型号
	private String jklbh; //监控类编号--关联D_00601表（监控项表）
	private String jkflbh; //监控分类编号-->d_006表
	private Integer sjz; //数据值
	
	private String sjzstr; // 数据值 字符串
	private String whsjstr; //维护时间 String 
	 

	public String getSjzstr() {
		return sjzstr;
	}
	public void setSjzstr(String sjzstr) {
		this.sjzstr = sjzstr;
	}
	public String getWhsjstr() {
		return whsjstr;
	}
	public void setWhsjstr(String whsjstr) {
		this.whsjstr = whsjstr;
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
	public Integer getZt() {
		return zt;
	}
	public void setZt(Integer zt) {
		this.zt = zt;
	}
	public Integer getWz() {
		return wz;
	}
	public void setWz(Integer wz) {
		this.wz = wz;
	}
	public String getJh() {
		return jh;
	}
	public void setJh(String jh) {
		this.jh = jh;
	}
	public String getXlh() {
		return xlh;
	}
	public void setXlh(String xlh) {
		this.xlh = xlh;
	}
	public String getXh() {
		return xh;
	}
	public void setXh(String xh) {
		this.xh = xh;
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
	public Integer getSjz() {
		return sjz;
	}
	public void setSjz(Integer sjz) {
		this.sjz = sjz;
	}
	
	
}
