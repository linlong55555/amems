package com.eray.thjw.produce.po;

import java.util.Date;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * 
 * @Description 人员排班
 * @CreateTime 2017年9月25日 下午1:46:09
 * @CreateBy 林龙
 */
public class CrewSchedule extends BizEntity {

	private static final long serialVersionUID = 1L;

	private String id;// id

	private Integer pblx;// 排班类型：1飞机排班、2人员排班

	private Date pbrq;// 排班日期。yyyymmdd

	private Date sbsj;// 上班时间。yyyymmddhh24

	private Date xbsj;// 下班时间。yyyymmddhh24

	private String whrid;// 维护人id

	private User whr;// 维护人

	private Date whsj;// 维护时间。yyyymmddhh24miss

	private Integer zt;// 状态：1提交、2已审核、3已批准、4中止（关闭）、5审核驳回、6审批驳回

	private String jd;// 基地


	private Department sh_department;// 审核部门

	private String shyj;// 审核意见

	private String shrid;// 审核人id

	private User shr;// 审核人

	private Date shsj;// 审核意见

	private Department pf_department;// 批复部门

	private String pfyj;// 批复意见

	private String prfid;// 批复人id

	private User pfr;// 批复人

	private Date pfsj;// 批复时间

	private CrewScheduleObject crewScheduleObject;// 人员排班对象表
	// 下面字段都是虚拟字段
	private String jxs;// 机械师

	private String dzs;// 电子师

	private String jxy;// 机械员

	private String dzy;// 电子员

	private String jxby;// 机械师(备)

	private String dzby;// 电子师(备)

	private String jxyby;// 机械员(备)

	private String dzyby;// 电子员(备)

	private String mccdd;// MCC调度

	private Date scheduleDateBegin;// 开始日期

	private Date scheduleDateEnd;// 结束日期

	private String wh;// 维护人

	private String jxsid;

	private String dzsid;

	private String jxyid;
	private String dzyid;
	private String jxbyid;
	private String jxybyid;
	private String dzbyid;
	private String dzybyid;
	private String mccddid;
	
	private Integer sbsjXs;
	
	private Integer xbsjXs;
	
	private Integer operation;
	
	private String dprtcode;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getPblx() {
		return pblx;
	}

	public void setPblx(Integer pblx) {
		this.pblx = pblx;
	}

	public Date getPbrq() {
		return pbrq;
	}

	public void setPbrq(Date pbrq) {
		this.pbrq = pbrq;
	}

	public Date getSbsj() {
		return sbsj;
	}

	public void setSbsj(Date sbsj) {
		this.sbsj = sbsj;
	}

	public Date getXbsj() {
		return xbsj;
	}

	public void setXbsj(Date xbsj) {
		this.xbsj = xbsj;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
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

	public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public Department getSh_department() {
		return sh_department;
	}

	public void setSh_department(Department sh_department) {
		this.sh_department = sh_department;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public User getShr() {
		return shr;
	}

	public void setShr(User shr) {
		this.shr = shr;
	}

	public Date getShsj() {
		return shsj;
	}

	public void setShsj(Date shsj) {
		this.shsj = shsj;
	}

	public Department getPf_department() {
		return pf_department;
	}

	public void setPf_department(Department pf_department) {
		this.pf_department = pf_department;
	}

	public String getPfyj() {
		return pfyj;
	}

	public void setPfyj(String pfyj) {
		this.pfyj = pfyj;
	}

	public User getPfr() {
		return pfr;
	}

	public void setPfr(User pfr) {
		this.pfr = pfr;
	}

	public Date getPfsj() {
		return pfsj;
	}

	public void setPfsj(Date pfsj) {
		this.pfsj = pfsj;
	}

	public CrewScheduleObject getCrewScheduleObject() {
		return crewScheduleObject;
	}

	public void setCrewScheduleObject(CrewScheduleObject crewScheduleObject) {
		this.crewScheduleObject = crewScheduleObject;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public String getShrid() {
		return shrid;
	}

	public void setShrid(String shrid) {
		this.shrid = shrid;
	}

	public String getPrfid() {
		return prfid;
	}

	public void setPrfid(String prfid) {
		this.prfid = prfid;
	}

	public String getJxs() {
		return jxs;
	}

	public void setJxs(String jxs) {
		this.jxs = jxs;
	}

	public String getDzs() {
		return dzs;
	}

	public void setDzs(String dzs) {
		this.dzs = dzs;
	}

	public String getJxy() {
		return jxy;
	}

	public void setJxy(String jxy) {
		this.jxy = jxy;
	}

	public String getDzy() {
		return dzy;
	}

	public void setDzy(String dzy) {
		this.dzy = dzy;
	}

	public String getJxby() {
		return jxby;
	}

	public void setJxby(String jxby) {
		this.jxby = jxby;
	}

	public String getDzby() {
		return dzby;
	}

	public void setDzby(String dzby) {
		this.dzby = dzby;
	}

	public String getJxyby() {
		return jxyby;
	}

	public void setJxyby(String jxyby) {
		this.jxyby = jxyby;
	}

	public String getDzyby() {
		return dzyby;
	}

	public void setDzyby(String dzyby) {
		this.dzyby = dzyby;
	}

	public String getMccdd() {
		return mccdd;
	}

	public void setMccdd(String mccdd) {
		this.mccdd = mccdd;
	}

	public Date getScheduleDateBegin() {
		return scheduleDateBegin;
	}

	public void setScheduleDateBegin(Date scheduleDateBegin) {
		this.scheduleDateBegin = scheduleDateBegin;
	}

	public Date getScheduleDateEnd() {
		return scheduleDateEnd;
	}

	public void setScheduleDateEnd(Date scheduleDateEnd) {
		this.scheduleDateEnd = scheduleDateEnd;
	}

	public String getWh() {
		return wh;
	}

	public void setWh(String wh) {
		this.wh = wh;
	}

	public String getJxsid() {
		return jxsid;
	}

	public void setJxsid(String jxsid) {
		this.jxsid = jxsid;
	}

	public String getDzsid() {
		return dzsid;
	}

	public void setDzsid(String dzsid) {
		this.dzsid = dzsid;
	}

	public String getJxyid() {
		return jxyid;
	}

	public void setJxyid(String jxyid) {
		this.jxyid = jxyid;
	}

	public String getDzyid() {
		return dzyid;
	}

	public void setDzyid(String dzyid) {
		this.dzyid = dzyid;
	}

	public String getJxbyid() {
		return jxbyid;
	}

	public void setJxbyid(String jxbyid) {
		this.jxbyid = jxbyid;
	}

	public String getJxybyid() {
		return jxybyid;
	}

	public void setJxybyid(String jxybyid) {
		this.jxybyid = jxybyid;
	}

	public String getDzbyid() {
		return dzbyid;
	}

	public void setDzbyid(String dzbyid) {
		this.dzbyid = dzbyid;
	}

	public String getDzybyid() {
		return dzybyid;
	}

	public void setDzybyid(String dzybyid) {
		this.dzybyid = dzybyid;
	}

	public String getMccddid() {
		return mccddid;
	}

	public void setMccddid(String mccddid) {
		this.mccddid = mccddid;
	}

	public Integer getSbsjXs() {
		return sbsjXs;
	}

	public void setSbsjXs(Integer sbsjXs) {
		this.sbsjXs = sbsjXs;
	}

	public Integer getXbsjXs() {
		return xbsjXs;
	}

	public void setXbsjXs(Integer xbsjXs) {
		this.xbsjXs = xbsjXs;
	}

	public Integer getOperation() {
		return operation;
	}

	public void setOperation(Integer operation) {
		this.operation = operation;
	}

	public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode;
	}

}
