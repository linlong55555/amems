package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * mi配置表
 * 
 * @author liub
 * 
 */
@Entity
@Table(name = "pbs_miconfig")
public class MIConfigBase extends BaseEntity {

	private String workcenter; // 工作组
	private double mechanichours;// 机器工时
	private double inspectionhours; // 检查工时
	private Integer status; // 状态：1.有效2，无效
	
	@Column(name = "workcenter_")
	public String getWorkcenter() {
		return workcenter;
	}

	public void setWorkcenter(String workcenter) {
		this.workcenter = workcenter;
	}
	
	@Column(name = "mechanichours_")
	public double getMechanichours() {
		return mechanichours;
	}

	public void setMechanichours(double mechanichours) {
		this.mechanichours = mechanichours;
	}
	
	@Column(name = "inspectionhours_")
	public double getInspectionhours() {
		return inspectionhours;
	}

	public void setInspectionhours(double inspectionhours) {
		this.inspectionhours = inspectionhours;
	}

	@Column(name = "status_")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "MIconfigBase [id=" + id + ", workcenter=" + workcenter + ", mechanichours="
				+ mechanichours + ", inspectionhours=" + inspectionhours
				+ ", status=" + status + "]";
	}

	
}
