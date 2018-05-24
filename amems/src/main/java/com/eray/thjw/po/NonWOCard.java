package com.eray.thjw.po;

/**
 *  相关工单实体
 * b_g_00605
 */
import java.util.Date;

public class NonWOCard {
    private String id;

    private String mainid;

    private Integer xggdLx;

    private Integer xggdZlx;

    private String xggdid;

    private String xgjcgdid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String dprtcode;
    
    private String zy;
    
    private String zyName;
    
    private String zhut;
    
    private String gdbh;
    

	public String getZyName() {
		return zyName;
	}

	public void setZyName(String zyName) {
		this.zyName = zyName;
	}

	public String getGdbh() {
		return gdbh;
	}

	public void setGdbh(String gdbh) {
		this.gdbh = gdbh;
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

	public Integer getXggdLx() {
		return xggdLx;
	}

	public void setXggdLx(Integer xggdLx) {
		this.xggdLx = xggdLx;
	}

	public Integer getXggdZlx() {
		return xggdZlx;
	}

	public void setXggdZlx(Integer xggdZlx) {
		this.xggdZlx = xggdZlx;
	}

	public String getXggdid() {
		return xggdid;
	}

	public void setXggdid(String xggdid) {
		this.xggdid = xggdid;
	}

	public String getXgjcgdid() {
		return xgjcgdid;
	}

	public void setXgjcgdid(String xgjcgdid) {
		this.xgjcgdid = xgjcgdid;
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

	public String getZy() {
		return zy;
	}

	public void setZy(String zy) {
		this.zy = zy;
	}

	public String getZhut() {
		return zhut;
	}

	public void setZhut(String zhut) {
		this.zhut = zhut;
	}

	@Override
	public String toString() {
		return "NonWOCard [id=" + id + ", mainid=" + mainid + ", xggdLx="
				+ xggdLx + ", xggdZlx=" + xggdZlx + ", xggdid=" + xggdid
				+ ", xgjcgdid=" + xgjcgdid + ", zt=" + zt + ", whdwid="
				+ whdwid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", dprtcode=" + dprtcode + ", zy=" + zy + ", zhut=" + zhut
				+ "]";
	}

    
    
}