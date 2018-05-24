package com.eray.thjw.po;

/**
 * @author meizhiliang
 * @disble  工单通用实体（包含b_g_007,b_g_016,b_g_010）
 */

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import enu.OrderZtEnum;

@SuppressWarnings("serial")
public class WorkOrder extends WorkOrderSupplement {

	private String id;

	private Integer gdlx;

	private Integer gdzlx;

	private String gdjcid;

	private String gdbh;

	private String gczlid;

	private String gczlzxdxid;

	private String gczlbh;

	private String zy;

	private String zyName;

	private BigDecimal jhgsRs;

	private BigDecimal jhgsXss;

	private String xfgdyy;

	private String bz;

	private String zddwid;

	private String zdrid;

	private Date zdsj;

	private Integer zt;

	private String dprtcode;

	private String zdjsrid;

	private Date zdjsrq;

	private String zdjsyy;

	private String shbmid;

	private String shyj;

	private String shrid;

	private Date shsj;

	private String pfbmid;

	private String pfyj;

	private String pfrid;

	private Date pfsj;

	private String jkbz;

	private String zhut;

	private String djrwdid;

	private String djgkid;

	private BaseWorkOrder baseWorkOrder; // 基础工单实体 和工单是一对一关系

	private String zjh;

	private String jd;

	private String glxx;

	private Integer isXyjszy;

	private String tcsj;

	private String refJhly;

	private String nbxh;

	private List<String> ids;

	private String cksc; // 参考手册及版次

	private String ckgk; // 厂家工卡及版次

	private String gzzw; // 工作站位

	private String bcwj; // 补充文件

	private String gzdd; // 工作地点

	private String gdbhAndztText; // 编号加状态

	private String ztText; // 状态文本

	private String EoidAndGdbhAndztText;

	private String czlx; // 用于记录操作类型

	private String gzzid; // 工作组id
	
	private String displaygzz; // 工作组显示
	
	public String getDisplaygzz() {
		return displaygzz;
	}

	public void setDisplaygzz(String displaygzz) {
		this.displaygzz = displaygzz;
	}

	public String getGzzid() {
		return gzzid;
	}

	public void setGzzid(String gzzid) {
		this.gzzid = gzzid;
	}

	public String getCzlx() {
		return czlx;
	}

	public void setCzlx(String czlx) {
		this.czlx = czlx;
	}

	public String getZyName() {
		return zyName;
	}

	public void setZyName(String zyName) {
		this.zyName = zyName;
	}
    
	public String getGdbhAndztText() {
		return gdbhAndztText;
	}

	public void setGdbhAndztText(String gdbhAndztText) {
		this.gdbhAndztText = gdbhAndztText;
	}

	public String getEoidAndGdbhAndztText() {
		if(zt!=null){
			EoidAndGdbhAndztText = (id == null ? "" : id)
					.concat("^")
					.concat(gdbh == null ? "" : gdbh)
					.concat("(")
					.concat(OrderZtEnum.getName(zt) == null ? "" : OrderZtEnum
							.getName(zt)).concat(")");
		}
		return EoidAndGdbhAndztText;
	}

	public void setEoidAndGdbhAndztText(String eoidAndGdbhAndztText) {
		EoidAndGdbhAndztText = eoidAndGdbhAndztText;
	}
	public String getZtText() {
		if(zt!=null){
			ztText = OrderZtEnum.getName(zt);
		}
		return ztText;
	}

	public void setZtText(String ztText) {
		this.ztText = ztText;
	}

	public String getCksc() {
		return cksc;
	}

	public void setCksc(String cksc) {
		this.cksc = cksc;
	}

	public String getCkgk() {
		return ckgk;
	}

	public void setCkgk(String ckgk) {
		this.ckgk = ckgk;
	}

	public String getGzzw() {
		return gzzw;
	}

	public void setGzzw(String gzzw) {
		this.gzzw = gzzw;
	}

	public String getBcwj() {
		return bcwj;
	}

	public void setBcwj(String bcwj) {
		this.bcwj = bcwj;
	}

	public String getGzdd() {
		return gzdd;
	}

	public void setGzdd(String gzdd) {
		this.gzdd = gzdd;
	}

	public Integer getGdzlx() {
		return gdzlx;
	}

	public void setGdzlx(Integer gdzlx) {
		this.gdzlx = gdzlx;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getNbxh() {
		return nbxh;
	}

	public void setNbxh(String nbxh) {
		this.nbxh = nbxh;
	}

	public String getDjrwdid() {
		return djrwdid;
	}

	public void setDjrwdid(String djrwdid) {
		this.djrwdid = djrwdid;
	}

	public String getDjgkid() {
		return djgkid;
	}

	public void setDjgkid(String djgkid) {
		this.djgkid = djgkid;
	}

	public String getZjh() {
		return zjh;
	}

	public void setZjh(String zjh) {
		this.zjh = zjh;
	}

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getGlxx() {
		return glxx;
	}

	public void setGlxx(String glxx) {
		this.glxx = glxx;
	}

	public Integer getIsXyjszy() {
		return isXyjszy;
	}

	public void setIsXyjszy(Integer isXyjszy) {
		this.isXyjszy = isXyjszy;
	}

	public String getTcsj() {
		return tcsj;
	}

	public void setTcsj(String tcsj) {
		this.tcsj = tcsj;
	}

	public String getRefJhly() {
		return refJhly;
	}

	public void setRefJhly(String refJhly) {
		this.refJhly = refJhly;
	}

	public Integer getGdlx() {
		return gdlx;
	}

	public void setGdlx(Integer gdlx) {
		this.gdlx = gdlx;
	}

	public BaseWorkOrder getBaseWorkOrder() {
		return baseWorkOrder;
	}

	public void setBaseWorkOrder(BaseWorkOrder baseWorkOrder) {
		this.baseWorkOrder = baseWorkOrder;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getGdjcid() {
		return gdjcid;
	}

	public void setGdjcid(String gdjcid) {
		this.gdjcid = gdjcid == null ? null : gdjcid.trim();
	}

	public String getGdbh() {
		return gdbh;
	}

	public void setGdbh(String gdbh) {
		this.gdbh = gdbh == null ? null : gdbh.trim();
	}

	public String getGczlid() {
		return gczlid;
	}

	public void setGczlid(String gczlid) {
		this.gczlid = gczlid == null ? null : gczlid.trim();
	}

	public String getGczlzxdxid() {
		return gczlzxdxid;
	}

	public void setGczlzxdxid(String gczlzxdxid) {
		this.gczlzxdxid = gczlzxdxid == null ? null : gczlzxdxid.trim();
	}

	public String getGczlbh() {
		return gczlbh;
	}

	public void setGczlbh(String gczlbh) {
		this.gczlbh = gczlbh == null ? null : gczlbh.trim();
	}

	public String getZy() {
		return zy;
	}

	public void setZy(String zy) {
		this.zy = zy == null ? null : zy.trim();
	}

	public BigDecimal getJhgsRs() {
		if(jhgsRs==null){
			return jhgsRs;
		}
		return jhgsRs.setScale(0);
	}

	public void setJhgsRs(BigDecimal jhgsRs) {
		this.jhgsRs = jhgsRs;
	}

	public BigDecimal getJhgsXss() {
		return jhgsXss;
	}

	public void setJhgsXss(BigDecimal jhgsXss) {
		this.jhgsXss = jhgsXss;
	}

	public String getXfgdyy() {
		return xfgdyy;
	}

	public void setXfgdyy(String xfgdyy) {
		this.xfgdyy = xfgdyy == null ? null : xfgdyy.trim();
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz == null ? null : bz.trim();
	}

	public String getZddwid() {
		return zddwid;
	}

	public void setZddwid(String zddwid) {
		this.zddwid = zddwid == null ? null : zddwid.trim();
	}

	public String getZdrid() {
		return zdrid;
	}

	public void setZdrid(String zdrid) {
		this.zdrid = zdrid == null ? null : zdrid.trim();
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

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode == null ? null : dprtcode.trim();
	}

	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid == null ? null : zdjsrid.trim();
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
		this.zdjsyy = zdjsyy == null ? null : zdjsyy.trim();
	}

	public String getShbmid() {
		return shbmid;
	}

	public void setShbmid(String shbmid) {
		this.shbmid = shbmid == null ? null : shbmid.trim();
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj == null ? null : shyj.trim();
	}

	public String getShrid() {
		return shrid;
	}

	public void setShrid(String shrid) {
		this.shrid = shrid == null ? null : shrid.trim();
	}

	public Date getShsj() {
		return shsj;
	}

	public void setShsj(Date shsj) {
		this.shsj = shsj;
	}

	public String getPfbmid() {
		return pfbmid;
	}

	public void setPfbmid(String pfbmid) {
		this.pfbmid = pfbmid == null ? null : pfbmid.trim();
	}

	public String getPfyj() {
		return pfyj;
	}

	public void setPfyj(String pfyj) {
		this.pfyj = pfyj == null ? null : pfyj.trim();
	}

	public String getPfrid() {
		return pfrid;
	}

	public void setPfrid(String pfrid) {
		this.pfrid = pfrid == null ? null : pfrid.trim();
	}

	public Date getPfsj() {
		return pfsj;
	}

	public void setPfsj(Date pfsj) {
		this.pfsj = pfsj;
	}

	public String getJkbz() {
		return jkbz;
	}

	public void setJkbz(String jkbz) {
		this.jkbz = jkbz == null ? null : jkbz.trim();
	}

	public String getZhut() {
		return zhut;
	}

	public void setZhut(String zhut) {
		this.zhut = zhut == null ? null : zhut.trim();
	}

	@Override
	public String toString() {
		return "WorkOrder [id=" + id + ", gdlx=" + gdlx + ", gdjcid=" + gdjcid
				+ ", gdbh=" + gdbh + ", gczlid=" + gczlid + ", gczlzxdxid="
				+ gczlzxdxid + ", gczlbh=" + gczlbh + ", zy=" + zy
				+ ", jhgsRs=" + jhgsRs + ", jhgsXss=" + jhgsXss + ", xfgdyy="
				+ xfgdyy + ", bz=" + bz + ", zddwid=" + zddwid + ", zdrid="
				+ zdrid + ", zdsj=" + zdsj + ", zt=" + zt + ", dprtcode="
				+ dprtcode + ", zdjsrid=" + zdjsrid + ", zdjsrq=" + zdjsrq
				+ ", zdjsyy=" + zdjsyy + ", shbmid=" + shbmid + ", shyj="
				+ shyj + ", shrid=" + shrid + ", shsj=" + shsj + ", pfbmid="
				+ pfbmid + ", pfyj=" + pfyj + ", pfrid=" + pfrid + ", pfsj="
				+ pfsj + ", jkbz=" + jkbz + ", zhut=" + zhut
				+ ", baseWorkOrder=" + baseWorkOrder + "]";
	}

}