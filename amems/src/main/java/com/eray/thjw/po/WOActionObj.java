package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.Date;
/**
 * 工单执行对象实体
 * @author Administrator
 *对应表：b_g_00604
 */
public class WOActionObj {
    
	private String id;

    private String mainid;

    private String zxfl;

    private String fjzch;

    private String zjqdid;

    private String bjh;

    private String bjxlh;

    private String jkxmbhF;

    private String jkflbhF;

    private String jkzF;

    private String jkxmbhS;

    private String jkflbhS;

    private String jkzS;

    private String jkxmbhT;

    private String jkflbhT;

    private String jkzT;

    private BigDecimal jhFxsx;

    private Integer jhQljxh;

    private Integer xh;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String dprtcode;
    
    private String bjName;//虚拟字段
    
    private Integer gdlx;
    
    private String fjjx;
    
    

	public String getFjjx() {
		return fjjx;
	}

	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}

	public String getBjName() {
		return bjName;
	}

	public void setBjName(String bjName) {
		this.bjName = bjName;
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

	public String getZjqdid() {
		return zjqdid;
	}

	public void setZjqdid(String zjqdid) {
		this.zjqdid = zjqdid;
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

	public String getJkxmbhF() {
		return jkxmbhF;
	}

	public void setJkxmbhF(String jkxmbhF) {
		this.jkxmbhF = jkxmbhF;
	}

	public String getJkflbhF() {
		return jkflbhF;
	}

	public void setJkflbhF(String jkflbhF) {
		this.jkflbhF = jkflbhF;
	}

	public String getJkzF() {
		return jkzF;
	}

	public void setJkzF(String jkzF) {
		this.jkzF = jkzF;
	}

	public String getJkxmbhS() {
		return jkxmbhS;
	}

	public void setJkxmbhS(String jkxmbhS) {
		this.jkxmbhS = jkxmbhS;
	}

	public String getJkflbhS() {
		return jkflbhS;
	}

	public void setJkflbhS(String jkflbhS) {
		this.jkflbhS = jkflbhS;
	}

	public String getJkzS() {
		return jkzS;
	}

	public void setJkzS(String jkzS) {
		this.jkzS = jkzS;
	}

	public String getJkxmbhT() {
		return jkxmbhT;
	}

	public void setJkxmbhT(String jkxmbhT) {
		this.jkxmbhT = jkxmbhT;
	}

	public String getJkflbhT() {
		return jkflbhT;
	}

	public void setJkflbhT(String jkflbhT) {
		this.jkflbhT = jkflbhT;
	}

	public String getJkzT() {
		return jkzT;
	}

	public void setJkzT(String jkzT) {
		this.jkzT = jkzT;
	}

	public BigDecimal getJhFxsx() {
		return jhFxsx;
	}

	public void setJhFxsx(BigDecimal jhFxsx) {
		this.jhFxsx = jhFxsx;
	}

	public Integer getJhQljxh() {
		return jhQljxh;
	}

	public void setJhQljxh(Integer jhQljxh) {
		this.jhQljxh = jhQljxh;
	}

	public Integer getXh() {
		return xh;
	}

	public void setXh(Integer xh) {
		this.xh = xh;
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

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public Integer getGdlx() {
		return gdlx;
	}

	public void setGdlx(Integer gdlx) {
		this.gdlx = gdlx;
	}

	@Override
	public String toString() {
		return "WOActionObj [id=" + id + ", mainid=" + mainid + ", zxfl="
				+ zxfl + ", fjzch=" + fjzch + ", zjqdid=" + zjqdid + ", bjh="
				+ bjh + ", bjxlh=" + bjxlh + ", jkxmbhF=" + jkxmbhF
				+ ", jkflbhF=" + jkflbhF + ", jkzF=" + jkzF + ", jkxmbhS="
				+ jkxmbhS + ", jkflbhS=" + jkflbhS + ", jkzS=" + jkzS
				+ ", jkxmbhT=" + jkxmbhT + ", jkflbhT=" + jkflbhT + ", jkzT="
				+ jkzT + ", jhFxsx=" + jhFxsx + ", jhQljxh=" + jhQljxh
				+ ", xh=" + xh + ", zt=" + zt + ", whdwid=" + whdwid
				+ ", whrid=" + whrid + ", whsj=" + whsj + ", dprtcode="
				+ dprtcode + "]";
	}

 
}