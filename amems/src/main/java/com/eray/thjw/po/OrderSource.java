package com.eray.thjw.po;

import java.math.BigDecimal;
import java.util.List;

import enu.ordersource.OrderSourceEnum;

//b_g_003下达指令来源表
public class OrderSource {
	private String dprtcode;

	private String pgdid;

	private Integer zlxl;

	private String zlid;

	private String zlbh;

	private String pgdh;
	
	private String jx;
	
	private String zlzt;
	
	private String zlbhAndZlzt;
	
	private String ly;
	
	private String shzltgh;
	
	private String sxrq;
	
	private String pgqx;
	
	private Integer zt;
	
	private String ywzt;
	
	private String displayname;
	
	private BigDecimal bb;
	
	private List<String> zlids;

	public BigDecimal getBb() {
		return bb;
	}

	public void setBb(BigDecimal bb) {
		this.bb = bb;
	}

	public List<String> getZlids() {
		return zlids;
	}

	public void setZlids(List<String> zlids) {
		this.zlids = zlids;
	}

	public String getLy() {
		return ly;
	}

	public void setLy(String ly) {
		this.ly = ly;
	}

	public String getShzltgh() {
		return shzltgh;
	}

	public void setShzltgh(String shzltgh) {
		this.shzltgh = shzltgh;
	}

	public String getSxrq() {
		return sxrq;
	}

	public void setSxrq(String sxrq) {
		this.sxrq = sxrq;
	}

	public String getPgqx() {
		return pgqx;
	}

	public void setPgqx(String pgqx) {
		this.pgqx = pgqx;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

	public String getZlbhAndZlzt() {
		zlbhAndZlzt = (zlbh==null?"":zlbh).concat("(").concat(zt==null?"":OrderSourceEnum.getName(zt)).concat(")");
		return zlbhAndZlzt;
	}

	public void setZlbhAndZlzt(String zlbhAndZlzt) {
		this.zlbhAndZlzt = zlbhAndZlzt;
	}
	
	
	
	public String getZlzt() {
		return zlzt;
	}

	public void setZlzt(String zlzt) {
		this.zlzt = zlzt;
	}

	public String getJx() {
		return jx;
	}

	public void setJx(String jx) {
		this.jx = jx;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

	public String getPgdid() {
		return pgdid;
	}

	public void setPgdid(String pgdid) {
		this.pgdid = pgdid;
	}

	public Integer getZlxl() {
		return zlxl;
	}

	public void setZlxl(Integer zlxl) {
		this.zlxl = zlxl;
	}

	public String getZlid() {
		return zlid;
	}

	public void setZlid(String zlid) {
		this.zlid = zlid;
	}

	public String getZlbh() {
		return zlbh;
	}

	public void setZlbh(String zlbh) {
		this.zlbh = zlbh;
	}

	public String getPgdh() {
		return pgdh;
	}

	public void setPgdh(String pgdh) {
		this.pgdh = pgdh;
	}

	
	
	public String getYwzt() {
		return ywzt;
	}

	public void setYwzt(String ywzt) {
		this.ywzt = ywzt;
	}

	@Override
	public String toString() {
		return "OrderSource [dprtcode=" + dprtcode + ", pgdid=" + pgdid
				+ ", zlxl=" + zlxl + ", zlid=" + zlid + ", zlbh=" + zlbh
				+ ", pgdh=" + pgdh + ", jx=" + jx + ", zlzt=" + zlzt + "]";
	}

	

}