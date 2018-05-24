package com.eray.thjw.productionplan.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

@SuppressWarnings("serial")
public class PlaneLoad extends BizEntity{
    
	private String id;
	
	private Date zxrq;
	
	private String fjzch;
	
	private String ms;
	
	private BigDecimal zxyzd_x1;
	
	private BigDecimal zxyzd_x2;
	
	private BigDecimal zxyzd_x3;
	
	private BigDecimal zxyzd_y1;
	
	private BigDecimal zxyzd_y2;
	
	private BigDecimal zxyzd_y3;
	
	private BigDecimal zxyzd_z1;
	
	private BigDecimal zxyzd_z2;
	
	private BigDecimal zxyzd_z3;
	
	private BigDecimal wjzd;
	
	private String gx;
	
	private BigDecimal fjzl_sz;
	
	private String fjzl_dw;
	
	private BigDecimal hxzx_sz;
	
	private String hxzx_dw;
	
	private BigDecimal zxzx_sz;
	
	private String zxzx_dw;
	
	private String bz;
	
	private Integer zt;
	
	private String zddwid;
	
	private String zdrid;
	
	private Date zdsj;
	
	private User zdr;
	
	private Department zd_department;
	
	private List<PlaneLoadInfo> planeLoadInfolist;
	
	private List<String> planeLoadInfoIds;
	
	private List<String> scwjIds;
	
	private List<Attachment> attachmens;
	
	
    private User zdr_user;
    
    public User getZdr_user() {
		return zdr_user;
	}

	public void setZdr_user(User zdr_user) {
		this.zdr_user = zdr_user;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public Department getBm_dprt() {
		return bm_dprt;
	}

	public void setBm_dprt(Department bm_dprt) {
		this.bm_dprt = bm_dprt;
	}

	private Department jg_dprt;
    
    private Department bm_dprt;
	
	
  /*  private String dprtcode;
	
	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}*/

	public List<String> getScwjIds() {
		return scwjIds;
	}

	public List<Attachment> getAttachmens() {
		return attachmens;
	}

	public void setAttachmens(List<Attachment> attachmens) {
		this.attachmens = attachmens;
	}

	public void setScwjIds(List<String> scwjIds) {
		this.scwjIds = scwjIds;
	}

	public List<String> getPlaneLoadInfoIds() {
		return planeLoadInfoIds;
	}

	public void setPlaneLoadInfoIds(List<String> planeLoadInfoIds) {
		this.planeLoadInfoIds = planeLoadInfoIds;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getZxrq() {
		return zxrq;
	}

	public void setZxrq(Date zxrq) {
		this.zxrq = zxrq;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public BigDecimal getZxyzd_x1() {
		return zxyzd_x1;
	}

	public void setZxyzd_x1(BigDecimal zxyzd_x1) {
		this.zxyzd_x1 = zxyzd_x1;
	}

	public BigDecimal getZxyzd_x2() {
		return zxyzd_x2;
	}

	public void setZxyzd_x2(BigDecimal zxyzd_x2) {
		this.zxyzd_x2 = zxyzd_x2;
	}

	public BigDecimal getZxyzd_x3() {
		return zxyzd_x3;
	}

	public void setZxyzd_x3(BigDecimal zxyzd_x3) {
		this.zxyzd_x3 = zxyzd_x3;
	}

	public BigDecimal getZxyzd_y1() {
		return zxyzd_y1;
	}

	public void setZxyzd_y1(BigDecimal zxyzd_y1) {
		this.zxyzd_y1 = zxyzd_y1;
	}

	public BigDecimal getZxyzd_y2() {
		return zxyzd_y2;
	}

	public void setZxyzd_y2(BigDecimal zxyzd_y2) {
		this.zxyzd_y2 = zxyzd_y2;
	}

	public BigDecimal getZxyzd_y3() {
		return zxyzd_y3;
	}

	public void setZxyzd_y3(BigDecimal zxyzd_y3) {
		this.zxyzd_y3 = zxyzd_y3;
	}

	public BigDecimal getZxyzd_z1() {
		return zxyzd_z1;
	}

	public void setZxyzd_z1(BigDecimal zxyzd_z1) {
		this.zxyzd_z1 = zxyzd_z1;
	}

	public BigDecimal getZxyzd_z2() {
		return zxyzd_z2;
	}

	public void setZxyzd_z2(BigDecimal zxyzd_z2) {
		this.zxyzd_z2 = zxyzd_z2;
	}

	public BigDecimal getZxyzd_z3() {
		return zxyzd_z3;
	}

	public void setZxyzd_z3(BigDecimal zxyzd_z3) {
		this.zxyzd_z3 = zxyzd_z3;
	}

	public BigDecimal getWjzd() {
		return wjzd;
	}

	public void setWjzd(BigDecimal wjzd) {
		this.wjzd = wjzd;
	}

	public String getGx() {
		return gx;
	}

	public void setGx(String gx) {
		this.gx = gx;
	}

	public BigDecimal getFjzl_sz() {
		return fjzl_sz;
	}

	public void setFjzl_sz(BigDecimal fjzl_sz) {
		this.fjzl_sz = fjzl_sz;
	}

	public String getFjzl_dw() {
		return fjzl_dw;
	}

	public void setFjzl_dw(String fjzl_dw) {
		this.fjzl_dw = fjzl_dw;
	}

	public BigDecimal getHxzx_sz() {
		return hxzx_sz;
	}

	public void setHxzx_sz(BigDecimal hxzx_sz) {
		this.hxzx_sz = hxzx_sz;
	}

	public String getHxzx_dw() {
		return hxzx_dw;
	}

	public void setHxzx_dw(String hxzx_dw) {
		this.hxzx_dw = hxzx_dw;
	}

	public BigDecimal getZxzx_sz() {
		return zxzx_sz;
	}

	public void setZxzx_sz(BigDecimal zxzx_sz) {
		this.zxzx_sz = zxzx_sz;
	}

	public String getZxzx_dw() {
		return zxzx_dw;
	}

	public void setZxzx_dw(String zxzx_dw) {
		this.zxzx_dw = zxzx_dw;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
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

	public User getZdr() {
		return zdr;
	}

	public void setZdr(User zdr) {
		this.zdr = zdr;
	}

	public Department getZd_department() {
		return zd_department;
	}

	public void setZd_department(Department zd_department) {
		this.zd_department = zd_department;
	}

	public List<PlaneLoadInfo> getPlaneLoadInfolist() {
		return planeLoadInfolist;
	}

	public void setPlaneLoadInfolist(List<PlaneLoadInfo> planeLoadInfolist) {
		this.planeLoadInfolist = planeLoadInfolist;
	}

}