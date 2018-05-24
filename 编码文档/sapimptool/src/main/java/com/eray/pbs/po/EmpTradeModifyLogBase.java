package com.eray.pbs.po;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * 员工与trade之间关系 2016.10.27
 * @author ganqing
 *
 */
@Entity
@Table(name = "pbs_emptrademodifylog")
public class EmpTradeModifyLogBase  extends BaseEntity {

	private String empId;		   // 员工编号
	private String empName;		   // 员工名称
	private String deptNumber;     // 部门编号
	private String workCenter;     // 工作组
	private String factor;		   // 系数
	private String team;   	       // 团队 
	private Integer roleId;        // 角色编号
	private String status;         // 状态1.有效2.无效
	private String department;     // 部门名称
	private String deleteReason;	//删除原因 	2016.9.2 add
	private String active;	        //状态 Y：在职  N:离职
	private String modifyType;	    //修改类型 addMain:新增主数据   modifyMain:修改主数据  delMain:删除主数据  modifySub:修改主定点数据  delMain:删除定点数据
	
	
	private String newEmpId;		   // 员工编号
	private String newEmpName;		   // 员工名称
	private String newDeptNumber;     // 部门编号
	private String newWorkCenter;     // 工作组
	private String newFactor;		   // 系数
	private String newTeam;   	       // 团队 
	private Integer newRoleId;        // 角色编号
	private String newStatus;         // 状态1.有效2.无效
	private String newDepartment;     // 部门名称
	private String newDeleteReason;	//删除原因 	2016.9.2 add
	private String newActive;	        //状态 Y：在职  N:离职
	
	private Date modifyDate;       //修改时间
	private String modifyEid;	        //修改人ID
	private String modifyEname;	        //修改人名称
	
	private Long empTradeId;  //pbs_emptrade表主键

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
	@Column(name = "modifytype_")
	public String getModifyType() {
		return modifyType;
	}
	public void setModifyType(String modifyType) {
		this.modifyType = modifyType;
	}
	@Column(name = "newempid_")
	public String getNewEmpId() {
		return newEmpId;
	}
	public void setNewEmpId(String newEmpId) {
		this.newEmpId = newEmpId;
	}
	@Column(name = "newempname_")
	public String getNewEmpName() {
		return newEmpName;
	}
	public void setNewEmpName(String newEmpName) {
		this.newEmpName = newEmpName;
	}
	@Column(name = "newdeptnumber_")
	public String getNewDeptNumber() {
		return newDeptNumber;
	}
	public void setNewDeptNumber(String newDeptNumber) {
		this.newDeptNumber = newDeptNumber;
	}
	@Column(name = "newworkcenter_")
	public String getNewWorkCenter() {
		return newWorkCenter;
	}
	public void setNewWorkCenter(String newWorkCenter) {
		this.newWorkCenter = newWorkCenter;
	}
	@Column(name = "newfactor_")
	public String getNewFactor() {
		return newFactor;
	}
	public void setNewFactor(String newFactor) {
		this.newFactor = newFactor;
	}
	@Column(name = "newteam_")
	public String getNewTeam() {
		return newTeam;
	}
	public void setNewTeam(String newTeam) {
		this.newTeam = newTeam;
	}
	@Column(name = "newroleid_")
	public Integer getNewRoleId() {
		return newRoleId;
	}
	public void setNewRoleId(Integer newRoleId) {
		this.newRoleId = newRoleId;
	}
	@Column(name = "newstatus_")
	public String getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
	@Column(name = "newdepartment_")
	public String getNewDepartment() {
		return newDepartment;
	}
	public void setNewDepartment(String newDepartment) {
		this.newDepartment = newDepartment;
	}
	@Column(name = "newdeletereason_")
	public String getNewDeleteReason() {
		return newDeleteReason;
	}
	public void setNewDeleteReason(String newDeleteReason) {
		this.newDeleteReason = newDeleteReason;
	}
	@Column(name = "newactive_")
	public String getNewActive() {
		return newActive;
	}
	public void setNewActive(String newActive) {
		this.newActive = newActive;
	}
	@Column(name = "modifydate_")
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	@Column(name = "modifyeid_")
	public String getModifyEid() {
		return modifyEid;
	}
	public void setModifyEid(String modifyEid) {
		this.modifyEid = modifyEid;
	}
	@Column(name = "modifyename_")
	public String getModifyEname() {
		return modifyEname;
	}
	public void setModifyEname(String modifyEname) {
		this.modifyEname = modifyEname;
	}
	@Column(name = "emptradeid_")
	public Long getEmpTradeId() {
		return empTradeId;
	}
	public void setEmpTradeId(Long empTradeId) {
		this.empTradeId = empTradeId;
	}
	@Override
	public String toString() {
		return "EmpTradeModifyLogBase [empId=" + empId + ", empName=" + empName
				+ ", deptNumber=" + deptNumber + ", workCenter=" + workCenter
				+ ", factor=" + factor + ", team=" + team + ", roleId="
				+ roleId + ", status=" + status + ", department=" + department
				+ ", deleteReason=" + deleteReason + ", active=" + active
				+ ", modifyType=" + modifyType + ", newEmpId=" + newEmpId
				+ ", newEmpName=" + newEmpName + ", newDeptNumber="
				+ newDeptNumber + ", newWorkCenter=" + newWorkCenter
				+ ", newFactor=" + newFactor + ", newTeam=" + newTeam
				+ ", newRoleId=" + newRoleId + ", newStatus=" + newStatus
				+ ", newDepartment=" + newDepartment + ", newDeleteReason="
				+ newDeleteReason + ", newActive=" + newActive
				+ ", modifyDate=" + modifyDate + ", modifyEid=" + modifyEid
				+ ", modifyEname=" + modifyEname + ", empTradeId=" + empTradeId
				+ "]";
	}


}
