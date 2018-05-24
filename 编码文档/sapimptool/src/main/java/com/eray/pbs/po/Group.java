package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * 组的定义
 * 
 * @author ganqing
 * 
 */
@Entity
@Table(name = "pbs_group")
public class Group extends BaseEntity {
	private String name; // 组名称
	private String status; // 组状态
	private Long workCenterId; 
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; name:"+name+
    			"; status:"+status+
    			"; workCenterId:"+workCenterId;
    }

	@Column(name = "name_")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "status_")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the workCenterId
	 */
	@Column(name = "workcenterid_")
	public Long getWorkCenterId()
	{
		return workCenterId;
	}

	/**
	 * @param workCenterId the workCenterId to set
	 */
	public void setWorkCenterId(Long workCenterId)
	{
		this.workCenterId = workCenterId;
	}

}
