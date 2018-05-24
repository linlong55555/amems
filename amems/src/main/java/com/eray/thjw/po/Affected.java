package com.eray.thjw.po;

/**
 * @author liub
 * @description B_G_00102
 * @develop date 2016.07.29
 */
public class Affected extends BaseEntity{

	private String id; // id
	private String mainid; // mainid
	private String zxfl; // 执行分类
	private String fjzch; // 飞机注册号
	private String zjqdid; // 装机清单id
	private String bjh; // 部件号
	private String bjxlh; // 部件序列号
	private Integer zt; // 状态
	private String whdwid; // 维护单位id
	private String whrid; // 维护人id
	private String whsj; // 维护时间
	private String dprtCode;// 机构代码
	private String jhgsRs;// 计划工时-人数
	private String jhgsXss;// 计划工时-小时数
	private Affected affected;// 机构代码
	private String isUpd;
	private String bjms;

	
	

	public String getBjms() {
		return bjms;
	}

	public void setBjms(String bjms) {
		this.bjms = bjms;
	}

	public String getJhgsRs() {
		if(jhgsRs!=null && !"".equals(jhgsRs)){
			if(jhgsRs.indexOf(".")!=-1){
				return jhgsRs.substring(0, jhgsRs.indexOf("."));
			}
		}
		return jhgsRs;
	}

	public void setJhgsRs(String jhgsRs) {
		this.jhgsRs = jhgsRs;
	}

	public String getJhgsXss() {
		return jhgsXss;
	}

	public void setJhgsXss(String jhgsXss) {
		this.jhgsXss = jhgsXss;
	}

	public String getIsUpd() {
		return isUpd;
	}

	public void setIsUpd(String isUpd) {
		this.isUpd = isUpd;
	}

	public Affected getAffected() {
		return affected;
	}

	public void setAffected(Affected affected) {
		this.affected = affected;
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

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
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

	public String getWhsj() {
		return whsj;
	}

	public void setWhsj(String whsj) {
		this.whsj = whsj;
	}

	public String getDprtCode() {
		return dprtCode;
	}

	public void setDprtCode(String dprtCode) {
		this.dprtCode = dprtCode;
	}

	/**
	 * @author liub
	 * @description 比较工作内容
	 * @param w
	 * @return
	 * @develop date 2016.09.08
	 */
	public void CompareColumn(Affected w) {
		Affected w2 = w.getAffected();
		if (w2 != null) {
			if (cp(w.getZxfl(), w2.getZxfl())
					&& cp(w.getFjzch(), w2.getFjzch())
					&& cp(w.getBjh(), w2.getBjh())
					&& cp(w.getBjxlh(), w2.getBjxlh())) {
			} else {
				w.setIsUpd(w2.getId());
			}
		}
	}

	/**
	 * @author liub
	 * @description 比较字符串
	 * @param s1
	 *            ,s2
	 * @return boolean
	 * @develop date 2016.09.08
	 */
	private boolean cp(String s1, String s2) {
		s1 = s1 == null ? "" : s1;
		s2 = s2 == null ? "" : s2;
		return s1.equals(s2);
	}

	@Override
	public String toString() {
		return "Affected [id=" + id + ", mainid=" + mainid + ", zxfl=" + zxfl
				+ ", fjzch=" + fjzch + ", zjqdid=" + zjqdid + ", bjh=" + bjh
				+ ", bjxlh=" + bjxlh + ", zt=" + zt + ", whdwid=" + whdwid
				+ ", whrid=" + whrid + ", whsj=" + whsj + ", dprtCode="
				+ dprtCode + ", jhgsRs=" + jhgsRs + ", jhgsXss=" + jhgsXss
				+ ", affected=" + affected + ", isUpd=" + isUpd + "]";
	}
	
	

}
