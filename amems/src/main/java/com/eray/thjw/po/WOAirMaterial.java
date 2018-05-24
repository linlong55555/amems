package com.eray.thjw.po;
/**
 * 工单航材耗材实体
 * 对应表：b_g_00601
 */
import java.math.BigDecimal;
import java.util.Date;

public class WOAirMaterial extends BizEntity {
	
    private String id;

    private String mainid;

    private String jh;

    private String zwmc;

    private String ywmc;

    private BigDecimal sl;

    private Integer lx;

    private String bz;

    private Integer xh;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String dprtcode;

    private String hcid;
   
    private String bjid;
    
    private String kcsl;

    private String refJhly;           //件号来源
    
    private String kykcsl;
    
    
	public String getKykcsl() {
		return kykcsl;
	}

	public void setKykcsl(String kykcsl) {
		this.kykcsl = kykcsl;
	}

	public String getRefJhly() {
		return refJhly;
	}

	public void setRefJhly(String refJhly) {
		this.refJhly = refJhly;
	}

	public String getKcsl() {
		return kcsl;
	}

	public void setKcsl(String kcsl) {
		this.kcsl = kcsl;
	}

	public String getHcid() {
		return hcid;
	}

	public void setHcid(String hcid) {
		this.hcid = hcid;
	}

	public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
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

	public String getJh() {
		return jh;
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public String getZwmc() {
		return zwmc;
	}

	public void setZwmc(String zwmc) {
		this.zwmc = zwmc;
	}

	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	public BigDecimal getSl() {
		if(sl == null){
    		return sl;
    	}
        return sl.setScale(0);
	}

	public void setSl(BigDecimal sl) {
		this.sl = sl;
	}


	public Integer getLx() {
		return lx;
	}

	public void setLx(Integer lx) {
		this.lx = lx;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
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

	@Override
	public String toString() {
		return "WOAirMaterial [id=" + id + ", mainid=" + mainid + ", jh=" + jh
				+ ", zwmc=" + zwmc + ", ywmc=" + ywmc + ", sl=" + sl + ", lx="
				+ lx + ", bz=" + bz + ", xh=" + xh + ", zt=" + zt + ", whdwid="
				+ whdwid + ", whrid=" + whrid + ", whsj=" + whsj
				+ ", dprtcode=" + dprtcode + "]";
	}
}