package com.eray.thjw.training.po;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;
import com.eray.thjw.quality.po.MaintenancePersonnel;

/**
 * @author liub
 * @description 培训计划-培训课程人员表B_P_00201
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlanPerson extends BizEntity {
	private String id;

	private String dprtcode;

	private String pxjhid;

	private String wxrydaid;

	private String xm;

	private String gzdw;

	private Integer kq;

	private BigDecimal cql;

	private String zs;

	private String cj;

	private Integer khjg;

	private Date xcpxrq;

	private String kcid;

	private String kcmc;

	private String kcbm;

	private String kcnr;

	private String pxgh;

	private String bz;

	private Integer isYc;

	private Integer isSc;

	private Integer cybs;

	private String pxlb;

	private Integer fxbs;

	private String pxxs;

	private String ksxs;

	private String kcdd;

	private String jsid;
	
	private String jsxm;

	private Date sjKsrq;

	private String sjKssj;

	private Date sjJsrq;

	private String sjJssj;

	private String sjks;

	private Integer zt;

	private Integer jszt;

	private String whbmid;

	private String whrid;

	private Date whsj;

	private MaintenancePersonnel maintenancePersonnel;

	private Course course;

	private User user;
	
	private String fjjx;
	
	private String zy;
	
	private Department jg_dprt;
	
	private String rybh;

	public String getRybh() {
		return rybh;
	}

	public void setRybh(String rybh) {
		this.rybh = rybh;
	}

	public String getJsid() {
		return jsid;
	}

	public void setJsid(String jsid) {
		this.jsid = jsid;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getKcmc() {
		return kcmc;
	}

	public void setKcmc(String kcmc) {
		this.kcmc = kcmc;
	}

	public MaintenancePersonnel getMaintenancePersonnel() {
		return maintenancePersonnel;
	}

	public void setMaintenancePersonnel(
			MaintenancePersonnel maintenancePersonnel) {
		this.maintenancePersonnel = maintenancePersonnel;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode == null ? null : dprtcode.trim();
	}

	public String getPxjhid() {
		return pxjhid;
	}

	public void setPxjhid(String pxjhid) {
		this.pxjhid = pxjhid == null ? null : pxjhid.trim();
	}

	public String getWxrydaid() {
		return wxrydaid;
	}

	public void setWxrydaid(String wxrydaid) {
		this.wxrydaid = wxrydaid == null ? null : wxrydaid.trim();
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm == null ? null : xm.trim();
	}

	public String getGzdw() {
		return gzdw;
	}

	public void setGzdw(String gzdw) {
		this.gzdw = gzdw == null ? null : gzdw.trim();
	}

	public Integer getKq() {
		return kq;
	}

	public void setKq(Integer kq) {
		this.kq = kq;
	}

	public BigDecimal getCql() {
		return cql;
	}

	public void setCql(BigDecimal cql) {
		this.cql = cql;
	}

	public String getZs() {
		return zs;
	}

	public void setZs(String zs) {
		this.zs = zs == null ? null : zs.trim();
	}

	public String getCj() {
		return cj;
	}

	public void setCj(String cj) {
		this.cj = cj == null ? null : cj.trim();
	}

	public Integer getKhjg() {
		return khjg;
	}

	public void setKhjg(Integer khjg) {
		this.khjg = khjg;
	}

	public Date getXcpxrq() {
		return xcpxrq;
	}

	public void setXcpxrq(Date xcpxrq) {
		this.xcpxrq = xcpxrq;
	}

	public String getKcid() {
		return kcid;
	}

	public void setKcid(String kcid) {
		this.kcid = kcid == null ? null : kcid.trim();
	}

	public String getKcbm() {
		return kcbm;
	}

	public void setKcbm(String kcbm) {
		this.kcbm = kcbm == null ? null : kcbm.trim();
	}

	public String getKcnr() {
		return kcnr;
	}

	public void setKcnr(String kcnr) {
		this.kcnr = kcnr == null ? null : kcnr.trim();
	}

	public String getPxgh() {
		return pxgh;
	}

	public void setPxgh(String pxgh) {
		this.pxgh = pxgh == null ? null : pxgh.trim();
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz == null ? null : bz.trim();
	}

	public Integer getIsYc() {
		return isYc;
	}

	public void setIsYc(Integer isYc) {
		this.isYc = isYc;
	}

	public Integer getIsSc() {
		return isSc;
	}

	public void setIsSc(Integer isSc) {
		this.isSc = isSc;
	}

	public Integer getCybs() {
		return cybs;
	}

	public void setCybs(Integer cybs) {
		this.cybs = cybs;
	}

	public String getPxlb() {
		return pxlb;
	}

	public void setPxlb(String pxlb) {
		this.pxlb = pxlb == null ? null : pxlb.trim();
	}

	public Integer getFxbs() {
		return fxbs;
	}

	public void setFxbs(Integer fxbs) {
		this.fxbs = fxbs;
	}

	public String getPxxs() {
		return pxxs;
	}

	public void setPxxs(String pxxs) {
		this.pxxs = pxxs == null ? null : pxxs.trim();
	}

	public String getKsxs() {
		return ksxs;
	}

	public void setKsxs(String ksxs) {
		this.ksxs = ksxs == null ? null : ksxs.trim();
	}

	public String getKcdd() {
		return kcdd;
	}

	public void setKcdd(String kcdd) {
		this.kcdd = kcdd == null ? null : kcdd.trim();
	}

	public String getJsxm() {
		return jsxm;
	}

	public void setJsxm(String jsxm) {
		this.jsxm = jsxm == null ? null : jsxm.trim();
	}

	public Date getSjKsrq() {
		return sjKsrq;
	}

	public void setSjKsrq(Date sjKsrq) {
		this.sjKsrq = sjKsrq;
	}

	public String getSjKssj() {
		return sjKssj;
	}

	public void setSjKssj(String sjKssj) {
		this.sjKssj = sjKssj == null ? null : sjKssj.trim();
	}

	public Date getSjJsrq() {
		return sjJsrq;
	}

	public void setSjJsrq(Date sjJsrq) {
		this.sjJsrq = sjJsrq;
	}

	public String getSjJssj() {
		return sjJssj;
	}

	public void setSjJssj(String sjJssj) {
		this.sjJssj = sjJssj == null ? null : sjJssj.trim();
	}

	public String getSjks() {
		return sjks;
	}

	public void setSjks(String sjks) {
		this.sjks = sjks == null ? null : sjks.trim();
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getJszt() {
		return jszt;
	}

	public void setJszt(Integer jszt) {
		this.jszt = jszt;
	}

	public String getWhbmid() {
		return whbmid;
	}

	public void setWhbmid(String whbmid) {
		this.whbmid = whbmid == null ? null : whbmid.trim();
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid == null ? null : whrid.trim();
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	public String getFjjx() {
		return fjjx;
	}

	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}

	public String getZy() {
		return zy;
	}

	public void setZy(String zy) {
		this.zy = zy;
	}
}