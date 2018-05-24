package com.eray.pbs.vo;

/**
 * 员工的先关信息 
 * @author ganqing
 *
 */
public class EmpTrade {
	private String empId;			//员工编号
	private String empName;			//员工姓名
	private String empTrade;		//员工所在组
	private String deptNumber;		//部门编号
	private String department;		//部门
	
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpTrade() {
		return empTrade;
	}
	public void setEmpTrade(String empTrade) {
		this.empTrade = empTrade;
	}
	public String getDeptNumber() {
		return deptNumber;
	}
	public void setDeptNumber(String deptNumber) {
		this.deptNumber = deptNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	
	
	
}
