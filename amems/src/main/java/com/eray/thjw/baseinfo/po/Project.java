package com.eray.thjw.baseinfo.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BaseEntity;

/** 
 * @Description 项目信息,对应的表为D_020
 * @CreateTime 2017-9-15 下午5:30:27
 * @CreateBy 甘清	
 */
public class Project extends BaseEntity {
	
	private static final long serialVersionUID = 1L;
	private String id; //数据库主键
	private String dprtcode; //组织机构码
	private String whbmid; //维护部门ID
	private String whrid; //维护人ID
	private Date whsj; //维护时间
	private Integer zt; //状态
	private Integer kzxbs; //可执行标识
	private String xmbm; //项目编码
	private String xmmc; //项目名称
	private String xmzl; //项目种类
	private String fjzt; //飞机状态(数据字典)
	private String fjzch; //飞机注册号
	private String fjxlh; //飞机序列号
	private String fjbzm; //飞机备注名【对应界面上的飞机描述】
	private String fjjsh; //飞机机身号 MSN
	private Integer fxsj; //飞行时间
	private Integer fxxh; //飞行循环
	private String fjjx;  //飞机机型 2017.10.9新增
	
	private String hbh; //航班号 2017.12.7新增
	
	private String ipcyxxh; //IPC有效性号
	private String rhyzph;  //润滑油脂牌号
	private String yyyph;  //液压油牌号
	private Integer wbbs;   //外部标识 0否、1是
	private String khid;   //客户ID
	private Date jhksrq; //计划开始日期
	private Date jhjsrq; //计划结束日期
	private Date sjksrq; //实际开始日期
	private Date sjjsrq; //实际结束日期
	private String xsddh;  //销售订单号
	private String fstk; //附属条款
	private String xmjl; //项目经理
	private String jhzk; //计划主控
	private String kzs; //控制室
	private String kzsdh; //控制室电话
	private BigDecimal qsgs; //起算工时
	
	
	private String fxsjstr; // 飞行时间字符串---> 对应fxsj字段
	private String keyword; //搜索key
    private String dprtname; //组织机构名
    private String whsjstr; //接收维护时间转义后的字符串
    private String whr; //维护人
    private String khbm; //客户编号
	private String khmc; //客户名称
	private String ztstr; //接收zt字段的转义值
	private String kzxbsstr; //接收kzxbs字段的转义值
	
	private List<ProjectMainArea> projectMainAreas; //主部件信息
    
    private List<Attachment> attachments; //合同信息
    
    private String jhksrqstr; //计划开始日期 String 
	private String jhjsrqstr; //计划结束日期 String 
	private String sjksrqstr; //实际开始日期 String 
	private String sjjsrqstr; //实际结束日期 String
	
	private Integer attachCount; //附件个数
	
	public String getFjjx() {
		return fjjx;
	}
	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}
	public String getZtstr() {
		return ztstr;
	}
	public void setZtstr(String ztstr) {
		this.ztstr = ztstr;
	}
	public String getKzxbsstr() {
		return kzxbsstr;
	}
	public void setKzxbsstr(String kzxbsstr) {
		this.kzxbsstr = kzxbsstr;
	}
	public Integer getWbbs() {
		return wbbs;
	}
	public void setWbbs(Integer wbbs) {
		this.wbbs = wbbs;
	}
	public String getFxsjstr() {
		return fxsjstr;
	}
	public void setFxsjstr(String fxsjstr) {
		this.fxsjstr = fxsjstr;
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
	public Integer getKzxbs() {
		return kzxbs;
	}
	public void setKzxbs(Integer kzxbs) {
		this.kzxbs = kzxbs;
	}
	public String getXmbm() {
		return xmbm;
	}
	public void setXmbm(String xmbm) {
		this.xmbm = xmbm;
	}
	public String getXmmc() {
		return xmmc;
	}
	public void setXmmc(String xmmc) {
		this.xmmc = xmmc;
	}
	public String getXmzl() {
		return xmzl;
	}
	public void setXmzl(String xmzl) {
		this.xmzl = xmzl;
	}
	public String getFjzt() {
		return fjzt;
	}
	public void setFjzt(String fjzt) {
		this.fjzt = fjzt;
	}
	public String getFjzch() {
		return fjzch;
	}
	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}
	public String getFjxlh() {
		return fjxlh;
	}
	public void setFjxlh(String fjxlh) {
		this.fjxlh = fjxlh;
	}
	public String getFjbzm() {
		return fjbzm;
	}
	public void setFjbzm(String fjbzm) {
		this.fjbzm = fjbzm;
	}
	public String getFjjsh() {
		return fjjsh;
	}
	public void setFjjsh(String fjjsh) {
		this.fjjsh = fjjsh;
	}
	public String getIpcyxxh() {
		return ipcyxxh;
	}
	public void setIpcyxxh(String ipcyxxh) {
		this.ipcyxxh = ipcyxxh;
	}
	public String getRhyzph() {
		return rhyzph;
	}
	public void setRhyzph(String rhyzph) {
		this.rhyzph = rhyzph;
	}
	public String getYyyph() {
		return yyyph;
	}
	public void setYyyph(String yyyph) {
		this.yyyph = yyyph;
	}
	public String getKhid() {
		return khid;
	}
	public void setKhid(String khid) {
		this.khid = khid;
	}
	public Date getJhksrq() {
		return jhksrq;
	}
	public void setJhksrq(Date jhksrq) {
		this.jhksrq = jhksrq;
	}
	public Date getJhjsrq() {
		return jhjsrq;
	}
	public void setJhjsrq(Date jhjsrq) {
		this.jhjsrq = jhjsrq;
	}
	public Date getSjksrq() {
		return sjksrq;
	}
	public void setSjksrq(Date sjksrq) {
		this.sjksrq = sjksrq;
	}
	public Date getSjjsrq() {
		return sjjsrq;
	}
	public void setSjjsrq(Date sjjsrq) {
		this.sjjsrq = sjjsrq;
	}
	public String getXsddh() {
		return xsddh;
	}
	public void setXsddh(String xsddh) {
		this.xsddh = xsddh;
	}
	public String getFstk() {
		return fstk;
	}
	public void setFstk(String fstk) {
		this.fstk = fstk;
	}
	public String getXmjl() {
		return xmjl;
	}
	public void setXmjl(String xmjl) {
		this.xmjl = xmjl;
	}
	public String getJhzk() {
		return jhzk;
	}
	public void setJhzk(String jhzk) {
		this.jhzk = jhzk;
	}
	public String getKzs() {
		return kzs;
	}
	public void setKzs(String kzs) {
		this.kzs = kzs;
	}
	public String getKzsdh() {
		return kzsdh;
	}
	public void setKzsdh(String kzsdh) {
		this.kzsdh = kzsdh;
	}
	public BigDecimal getQsgs() {
		return qsgs;
	}
	public void setQsgs(BigDecimal qsgs) {
		this.qsgs = qsgs;
	}
	public Integer getFxsj() {
		return fxsj;
	}
	public void setFxsj(Integer fxsj) {
		this.fxsj = fxsj;
	}
	public Integer getFxxh() {
		return fxxh;
	}
	public void setFxxh(Integer fxxh) {
		this.fxxh = fxxh;
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
	public List<ProjectMainArea> getProjectMainAreas() {
		return projectMainAreas;
	}
	public void setProjectMainAreas(List<ProjectMainArea> projectMainAreas) {
		this.projectMainAreas = projectMainAreas;
	}
	public List<Attachment> getAttachments() {
		return attachments;
	}
	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}
	
	public String getJhksrqstr() {
		return jhksrqstr;
	}
	public void setJhksrqstr(String jhksrqstr) {
		this.jhksrqstr = jhksrqstr;
	}
	public String getJhjsrqstr() {
		return jhjsrqstr;
	}
	public void setJhjsrqstr(String jhjsrqstr) {
		this.jhjsrqstr = jhjsrqstr;
	}
	public String getSjksrqstr() {
		return sjksrqstr;
	}
	public void setSjksrqstr(String sjksrqstr) {
		this.sjksrqstr = sjksrqstr;
	}
	public String getSjjsrqstr() {
		return sjjsrqstr;
	}
	public void setSjjsrqstr(String sjjsrqstr) {
		this.sjjsrqstr = sjjsrqstr;
	}
	
	public Integer getAttachCount() {
		return attachCount;
	}
	public void setAttachCount(Integer attachCount) {
		this.attachCount = attachCount;
	}
	public String getHbh() {
		return hbh;
	}
	public void setHbh(String hbh) {
		this.hbh = hbh;
	}
	@Override
	public String toString() {
		return "Project [id=" + id + ", dprtcode=" + dprtcode + ", whbmid="
				+ whbmid + ", whrid=" + whrid + ", whsj=" + whsj + ", zt=" + zt
				+ ", kzxbs=" + kzxbs + ", xmbm=" + xmbm + ", xmmc=" + xmmc
				+ ", xmzl=" + xmzl + ", fjzt=" + fjzt + ", fjzch=" + fjzch
				+ ", fjxlh=" + fjxlh + ", fjbzm=" + fjbzm + ", fjjsh=" + fjjsh
				+ ", ipcyxxh=" + ipcyxxh + ", rhyzph=" + rhyzph + ", yyyph="
				+ yyyph + ", khid=" + khid + ", jhksrq=" + jhksrq + ", jhjsrq="
				+ jhjsrq + ", sjksrq=" + sjksrq + ", sjjsrq=" + sjjsrq
				+ ", xsddh=" + xsddh + ", fstk=" + fstk + ", xmjl=" + xmjl
				+ ", jhzk=" + jhzk + ", kzs=" + kzs + ", kzsdh=" + kzsdh
				+ ", qsgs=" + qsgs + ", fxsj=" + fxsj + ", fxxh=" + fxxh
				+ ", keyword=" + keyword + ", dprtname=" + dprtname
				+ ", whsjstr=" + whsjstr + ", whr=" + whr + "]";
	}
	
}
