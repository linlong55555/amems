package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;
/**
 * 用工可用工时实体 2016.07.25 21:56
 * @author ganqing
 *
 */
@Entity
@Table(name = "pbs_emp_available")
public class EmpAvailable extends BaseEntity {

	private String empId;			//员工编号
	private String empName;			//员工姓名
	private String empTrade;		//员工所在组
	private String deptNumber;		//部门编号
	private String department;		//部门
	private String markDate;		//打卡日期（格式：yyyyMMdd）
	private String containShift;	//是否有 in&on 的记录，用 Y&N 表示
	private String fullShift;		//是否 in&on 完整配对，用 Y&N 表示
	private String sysShift;		//系统是否自动处理过，用 Y&N 表示
	private String personShift;		//是否人为的处理过，用 Y&N 表示
	private String isAllOk;			//该数据是否处理完毕，用 Y&N 表示
	private String everWarn;		//该数据是否曾经是否出现问题
	private String total;			//Available 总有效工时
	private String isreportHandle; //是否做过report前的数据前处理 Y&N
	
	@Column(name = "isreporthandle_")
	public String getIsreportHandle() {
		return isreportHandle;
	}

	public void setIsreportHandle(String isreportHandle) {
		this.isreportHandle = isreportHandle;
	}

	@Column(name = "empid_")
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	@Column(name = "empname_")
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name = "emptrade_")
	public String getEmpTrade() {
		return empTrade;
	}

	public void setEmpTrade(String empTrade) {
		this.empTrade = empTrade;
	}

	@Column(name = "deptnumber_")
	public String getDeptNumber() {
		return deptNumber;
	}

	public void setDeptNumber(String deptNumber) {
		this.deptNumber = deptNumber;
	}

	@Column(name = "department_")
	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Column(name = "markdate_")
	public String getMarkDate() {
		return markDate;
	}

	public void setMarkDate(String markDate) {
		this.markDate = markDate;
	}

	@Column(name = "containshift_")
	public String getContainShift() {
		return containShift;
	}

	public void setContainShift(String containShift) {
		this.containShift = containShift;
	}

	@Column(name = "fullshift_")
	public String getFullShift() {
		return fullShift;
	}

	public void setFullShift(String fullShift) {
		this.fullShift = fullShift;
	}

	@Column(name = "sysshift_")
	public String getSysShift() {
		return sysShift;
	}

	public void setSysShift(String sysShift) {
		this.sysShift = sysShift;
	}

	@Column(name = "personshift_")
	public String getPersonShift() {
		return personShift;
	}

	public void setPersonShift(String personShift) {
		this.personShift = personShift;
	}

	@Column(name = "isallok_")
	public String getIsAllOk() {
		return isAllOk;
	}

	public void setIsAllOk(String isAllOk) {
		this.isAllOk = isAllOk;
	}

	@Column(name = "everwarn_")
	public String getEverWarn() {
		return everWarn;
	}

	public void setEverWarn(String everWarn) {
		this.everWarn = everWarn;
	}

	@Column(name = "total_")
	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "EmpAvailable [empId=" + empId + ", empName=" + empName
				+ ", empTrade=" + empTrade + ", deptNumber=" + deptNumber
				+ ", department=" + department + ", markDate=" + markDate
				+ ", containShift=" + containShift + ", fullShift=" + fullShift
				+ ", sysShift=" + sysShift + ", personShift=" + personShift
				+ ", isAllOk=" + isAllOk + ", everWarn=" + everWarn
				+ ", total=" + total + ", isreportHandle=" + isreportHandle
				+ "]";
	}

}
