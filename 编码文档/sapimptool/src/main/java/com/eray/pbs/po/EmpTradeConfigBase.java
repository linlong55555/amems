package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * 员工与部门，工作组之间的关系 2016.07.26
 * @author ganqing
 *
 */
@Entity
@Table(name = "pbs_emptradeconfig")
public class EmpTradeConfigBase extends BaseEntity {
	private String empId;		   // 员工编号
	private String empName;		   // 员工名称
	private String deptNumber;     // 部门编号
	private String workCenter;     // 工作组
	private String factor;		   // 系数
	private String team;   	       // 团队 
	private Integer roleId;        // 角色编号
	private String status;         // 状态1.有效0.无效
	private String department;     // 部门名称
	private String deleteReason;	//删除原因 	2016.9.2 add
	private String active;	        //状态 Y：在职  N:离职
	
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
	
	@Column(name = "deptnumber_")
	public String getDeptNumber() {
		return deptNumber;
	}
	public void setDeptNumber(String deptNumber) {
		this.deptNumber = deptNumber;
	}
	
	@Column(name = "workcenter_")
	public String getWorkCenter() {
		return workCenter;
	}
	public void setWorkCenter(String workCenter) {
		this.workCenter = workCenter;
	}
	
	@Column(name = "factor_")
	public String getFactor() {
		return factor;
	}
	public void setFactor(String factor) {
		this.factor = factor;
	}
	
	@Column(name = "team_")
	public String getTeam() {
		return team;
	}
	public void setTeam(String team) {
		this.team = team;
	}
	
	@Column(name = "roleid_")
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	@Column(name = "status_")
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	@Column(name = "department_")
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	@Column(name = "deletereason_")
	public String getDeleteReason() {
		return deleteReason;
	}
	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}
	@Column(name = "active_")
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	@Override
	public String toString() {
		return "EmpTradeConfigBase [empId=" + empId + ", empName=" + empName
				+ ", deptNumber=" + deptNumber + ", workCenter=" + workCenter
				+ ", status=" + status + ", department=" + department + "]";
	}
	
	
}
