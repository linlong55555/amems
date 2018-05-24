package com.eray.thjw.basic.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;


/**
 * @Description stage配置,对应表d_024
 * @CreateTime 2017-9-14 上午11:37:24
 * @CreateBy 甘清
 */
public class Stage  extends BaseEntity {

	private static final long serialVersionUID = 1L;
	private String id; // 主键ID
	private String dprtcode; // 组织机构
	private String whbmid; // 维护部门ID
	private String whrid; // 维护人ID
	private Date whsj; // 维护时间
	private Integer zt; // stage状态
	private String jdbh; // 阶段编号
	private String jdmc; // 阶段名称
	private String jdms; // 阶段描述
	private Integer xc; // 项次
	
    private String keyword; //搜索key
    private String dprtname; //组织机构名
    private String whsjstr; //接收维护时间转义后的字符串
    private String whr; //维护人
    
	public String getWhr() {
		return whr;
	}
	public void setWhr(String whr) {
		this.whr = whr;
	}
	public String getDprtname() {
		return dprtname;
	}
	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
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
	
	public String getDprtcode() {
		return dprtcode;
	}
	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
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
	public String getJdbh() {
		return jdbh;
	}
	public void setJdbh(String jdbh) {
		this.jdbh = jdbh;
	}
	public String getJdmc() {
		return jdmc;
	}
	public void setJdmc(String jdmc) {
		this.jdmc = jdmc;
	}
	public String getJdms() {
		return jdms;
	}
	public void setJdms(String jdms) {
		this.jdms = jdms;
	}
	public Integer getXc() {
		return xc;
	}
	public void setXc(Integer xc) {
		this.xc = xc;
	}
	
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		return "Stage [id=" + id + ", dprtCode=" + dprtcode + ", whbmid="
				+ whbmid + ", whrid=" + whrid + ", whsj=" + whsj + ", zt=" + zt
				+ ", jdbh=" + jdbh + ", jdmc=" + jdmc + ", jdms=" + jdms
				+ ", xc=" + xc + "]";
	}

	
	
}
