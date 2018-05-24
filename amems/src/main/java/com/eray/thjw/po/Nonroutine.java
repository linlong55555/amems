package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 非例行工单提交的表单实体(表b_g_007)
 * 
 * @author 梅志亮
 * 
 */
public class Nonroutine extends BizEntity {

	private String id; // uuid

	private Integer gdlx; // 工单类型

	private String gdjcid; // 工单基础id

	private String gdbh; // 工单编号

	private String zy; // 专业

	private BigDecimal jhgs_rs; // 计划工时 人数

	private BigDecimal jhgs_xss; // 计划工时 小时数

	private String gkly; // 工卡来源

	private String xfgdyy; // 下发工单原因

	private String bz; // 备注

	private String zddwid; // 制单单位id

	private String zdrid; // 制单人id

	private Date zdsj; // 制单时间
	
	private String zhut; // 主题

	private Integer zt; // 状态

	private String shbmid;

	private String shyj;

	private String shrid;

	private String shsj;

	private String pfbmid;

	private String pfyj;

	private String pfrid;

	private String pfsj;

	private String zdjsrid; // 指定结束人id

	private Date zdjsrq; // 指定结束日期

	private String zdjsyy; // 指定结束原因

	private String jkbz;
	
	private String pgdh;     //评估单号
	
	private String zxfl;       //执行分类
	
	private String fjzch;  //飞机注册号
	
	private String bjh;     //部件号
	
	private  String  bjxlh ;    //部件序列号
	
	private  String  zhuanye;     //   联合查询专业
	
	private String  username;     // 用户名
	
	private String realname;    //真是姓名
	
	private String gdlx2;             //工单类型子类
	
	private String dprtname;

	
	private String gcbh;
	private String gczlid;
	private String gczlzxdxid;
	
	private String jhgsRs;
	
	
	
	public String getZhut() {
		return zhut;
	}

	public void setZhut(String zhut) {
		this.zhut = zhut;
	}

	public String getGcbh() {
		return gcbh;
	}

	public void setGcbh(String gcbh) {
		this.gcbh = gcbh;
	}

	public String getGczlid() {
		return gczlid;
	}

	public void setGczlid(String gczlid) {
		this.gczlid = gczlid;
	}

	public String getGczlzxdxid() {
		return gczlzxdxid;
	}

	public void setGczlzxdxid(String gczlzxdxid) {
		this.gczlzxdxid = gczlzxdxid;
	}

	public String getGdlx2() {
		return gdlx2;
	}

	public void setGdlx2(String gdlx2) {
		this.gdlx2 = gdlx2;
	}

	public String getPgdh() {
		return pgdh;
	}

	public void setPgdh(String pgdh) {
		this.pgdh = pgdh;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getGdlx() {
		return gdlx;
	}

	public void setGdlx(Integer gdlx) {
		this.gdlx = gdlx;
	}

	public String getGdjcid() {
		return gdjcid;
	}

	public void setGdjcid(String gdjcid) {
		this.gdjcid = gdjcid;
	}

	public String getGdbh() {
		return gdbh;
	}

	public void setGdbh(String gdbh) {
		this.gdbh = gdbh;
	}

	public String getZy() {
		return zy;
	}

	public void setZy(String zy) {
		this.zy = zy;
	}

	public BigDecimal getJhgs_rs() {
		return jhgs_rs;
	}

	public void setJhgs_rs(BigDecimal jhgs_rs) {
		this.jhgs_rs = jhgs_rs;
	}

	public BigDecimal getJhgs_xss() {
		return jhgs_xss;
	}

	public void setJhgs_xss(BigDecimal jhgs_xss) {
		this.jhgs_xss = jhgs_xss;
	}

	public String getGkly() {
		return gkly;
	}

	public void setGkly(String gkly) {
		this.gkly = gkly;
	}

	public String getXfgdyy() {
		return xfgdyy;
	}

	public void setXfgdyy(String xfgdyy) {
		this.xfgdyy = xfgdyy;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getZddwid() {
		return zddwid;
	}

	public void setZddwid(String zddwid) {
		this.zddwid = zddwid;
	}

	public String getZdrid() {
		return zdrid;
	}

	public void setZdrid(String zdrid) {
		this.zdrid = zdrid;
	}

	public Date getZdsj() {
		return zdsj;
	}

	public void setZdsj(Date zdsj) {
		this.zdsj = zdsj;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getShbmid() {
		return shbmid;
	}

	public void setShbmid(String shbmid) {
		this.shbmid = shbmid;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public String getShrid() {
		return shrid;
	}

	public void setShrid(String shrid) {
		this.shrid = shrid;
	}

	public String getShsj() {
		return shsj;
	}

	public void setShsj(String shsj) {
		this.shsj = shsj;
	}

	public String getPfbmid() {
		return pfbmid;
	}

	public void setPfbmid(String pfbmid) {
		this.pfbmid = pfbmid;
	}

	public String getPfyj() {
		return pfyj;
	}

	public void setPfyj(String pfyj) {
		this.pfyj = pfyj;
	}

	public String getPfrid() {
		return pfrid;
	}

	public void setPfrid(String pfrid) {
		this.pfrid = pfrid;
	}

	public String getPfsj() {
		return pfsj;
	}

	public void setPfsj(String pfsj) {
		this.pfsj = pfsj;
	}

	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid;
	}

	public Date getZdjsrq() {
		return zdjsrq;
	}

	public void setZdjsrq(Date zdjsrq) {
		this.zdjsrq = zdjsrq;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}

	public String getJkbz() {
		return jkbz;
	}

	public void setJkbz(String jkbz) {
		this.jkbz = jkbz;
	}

	public String getZxfl() {
		return zxfl;
	}

	public void setZxfl(String zxfl) {
		this.zxfl = zxfl;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getBjh() {
		return bjh;
	}

	public void setBjh(String bjh) {
		this.bjh = bjh;
	}

	public String getBjxlh() {
		return bjxlh;
	}

	public void setBjxlh(String bjxlh) {
		this.bjxlh = bjxlh;
	}

	public String getZhuanye() {
		return zhuanye;
	}

	public void setZhuanye(String zhuanye) {
		this.zhuanye = zhuanye;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	@Override
	public String toString() {
		return "Nonroutine [id=" + id + ", gdlx=" + gdlx + ", gdjcid=" + gdjcid
				+ ", gdbh=" + gdbh + ", zy=" + zy + ", jhgs_rs=" + jhgs_rs
				+ ", jhgs_xss=" + jhgs_xss + ", gkly=" + gkly + ", xfgdyy="
				+ xfgdyy + ", bz=" + bz + ", zddwid=" + zddwid + ", zdrid="
				+ zdrid + ", zdsj=" + zdsj + ", zt=" + zt + ", shbmid="
				+ shbmid + ", shyj=" + shyj + ", shrid=" + shrid + ", shsj="
				+ shsj + ", pfbmid=" + pfbmid + ", pfyj=" + pfyj + ", pfrid="
				+ pfrid + ", pfsj=" + pfsj + ", zdjsrid=" + zdjsrid
				+ ", zdjsrq=" + zdjsrq + ", zdjsyy=" + zdjsyy + ", jkbz="
				+ jkbz + ", zxfl=" + zxfl + ", fjzch=" + fjzch + ", bjh=" + bjh
				+ ", bjxlh=" + bjxlh + ", zhuanye=" + zhuanye + ", username="
				+ username + ", realname=" + realname + "]";
	}

	public String getJhgsRs() {
		return jhgsRs;
	}

	public void setJhgsRs(String jhgsRs) {
		this.jhgsRs = jhgsRs;
	}
	
}
