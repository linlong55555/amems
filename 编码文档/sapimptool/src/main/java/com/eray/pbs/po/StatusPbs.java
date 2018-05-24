package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_status")
public class StatusPbs extends BaseEntity
{
    private String workOrder;
    private String operation;
    private String oldStatus;
    private String newStatus;
    private String changeBy;
    private String changeOn;
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; workOrder:"+workOrder+
    			"; operation:"+operation+
    			"; oldStatus:"+oldStatus+
    			"; newStatus:"+newStatus+
    			"; changeBy:"+changeBy+
    			"; changeOn:"+changeOn;
    }
    /**
     * @return the workOrder
     */
    @Column(name = "workorder_")
    public String getWorkOrder()
    {
        return workOrder;
    }
    public void setWorkOrder(String workOrder)
    {
        this.workOrder = workOrder;
    }
    
	/**
	 * @return the operation
	 */
    @Column(name = "operation_")
	public String getOperation()
	{
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}
	/**
	 * @return the oldStatus
	 */
	@Column(name = "oldstatus_")
	public String getOldStatus()
	{
		return oldStatus;
	}
	/**
	 * @param oldStatus the oldStatus to set
	 */
	public void setOldStatus(String oldStatus)
	{
		this.oldStatus = oldStatus;
	}
	/**
	 * @return the newStatus
	 */
	@Column(name = "newstatus_")
	public String getNewStatus()
	{
		return newStatus;
	}
	/**
	 * @param newStatus the newStatus to set
	 */
	public void setNewStatus(String newStatus)
	{
		this.newStatus = newStatus;
	}
	/**
	 * @return the changeBy
	 */
	@Column(name = "changeby_")
	public String getChangeBy()
	{
		return changeBy;
	}
	/**
	 * @param changeBy the changeBy to set
	 */
	public void setChangeBy(String changeBy)
	{
		this.changeBy = changeBy;
	}
	/**
	 * @return the changeOn
	 */
	@Column(name = "changeon_")
	public String getChangeOn()
	{
		return changeOn;
	}
	/**
	 * @param changeOn the changeOn to set
	 */
	public void setChangeOn(String changeOn)
	{
		this.changeOn = changeOn;
	}
    
}
