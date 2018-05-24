package com.eray.thjw.baseinfo.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/** 
 * @Description 客户基本信息实体,对应D_019表
 * @CreateTime 2017-9-15 下午5:01:23
 * @CreateBy 甘清	
 */
public class Customer extends BaseEntity {

	private static final long serialVersionUID = 1L;
	
	private String id; //数据库之间
	private String dprtcode; //组织机构码
	private String whbmid; //维护部门ID
	private String whrid; //维护人ID
	private Date whsj; //维护时间
	private Integer zt; //状态
	private String khbm; //客户编号
	private String khmc; //客户名称
	private String cs;  //城市
	private String gj;  //国家
	private String lxdh; //联系电话
	private String khfl; //客户分类
	private String bz; //备注
	private String yb; //邮编
	private String khjc; //客户简称
	
	private String keyword; //搜索key
    private String dprtname; //组织机构名
    private String whsjstr; //接收维护时间转义后的字符串
    private String whr; //维护人
	
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
	public String getKhbm() {
		return khbm;
	}
	public void setKhbm(String khbm) {
		this.khbm = khbm;
	}
	public String getKhmc() {
		return khmc;
	}
	public void setKhmc(String khmc) {
		this.khmc = khmc;
	}
	public String getCs() {
		return cs;
	}
	public void setCs(String cs) {
		this.cs = cs;
	}
	public String getGj() {
		return gj;
	}
	public void setGj(String gj) {
		this.gj = gj;
	}
	public String getLxdh() {
		return lxdh;
	}
	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}
	public String getKhfl() {
		return khfl;
	}
	public void setKhfl(String khfl) {
		this.khfl = khfl;
	}
	public String getBz() {
		return bz;
	}
	public void setBz(String bz) {
		this.bz = bz;
	}
	public String getYb() {
		return yb;
	}
	public void setYb(String yb) {
		this.yb = yb;
	}
	public String getKhjc() {
		return khjc;
	}
	public void setKhjc(String khjc) {
		this.khjc = khjc;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
	public String getWhr() {
		return whr;
	}
	public void setWhr(String whr) {
		this.whr = whr;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", dprtcode=" + dprtcode + ", whbmid="
				+ whbmid + ", whrid=" + whrid + ", whsj=" + whsj + ", zt=" + zt
				+ ", khbm=" + khbm + ", khmc=" + khmc + ", cs=" + cs + ", gj="
				+ gj + ", lxdh=" + lxdh + ", khfl=" + khfl + ", bz=" + bz
				+ ", yb=" + yb + ", khjc=" + khjc + "]";
	}
	
}
