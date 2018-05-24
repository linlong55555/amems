package com.eray.pbs.po;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_spenthours")
public class SpentHoursPbs extends BaseEntity
{
    private String workOrder;
    private String operation;
    private String confText;
    private String workCenter;
    private BigDecimal spentHours;
    private String spentHoursUnit;
    private String startDate;
    private String startTime;
    private String finishDate;
    private String finishTime;
    private String employeeId;
    private String activityType;
    
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
    			"; confText:"+confText+
    			"; workCenter:"+workCenter+
    			"; spentHours:"+spentHours+
    			"; spentHoursUnit:"+spentHoursUnit+
    			"; startDate:"+startDate+
    			"; startTime:"+startTime+
    			"; finishDate:"+finishDate+
    			"; finishTime:"+finishTime+
    			"; employeeId:"+employeeId+
    			"; activityType:"+activityType;
    }

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
	 * @return the confText
	 */
	@Column(name = "conftext_")
	public String getConfText()
	{
		return confText;
	}

	/**
	 * @param confText the confText to set
	 */
	public void setConfText(String confText)
	{
		this.confText = confText;
	}

	/**
	 * @return the workCenter
	 */
	@Column(name = "workcenter_")
	public String getWorkCenter()
	{
		return workCenter;
	}

	/**
	 * @param workCenter the workCenter to set
	 */
	public void setWorkCenter(String workCenter)
	{
		this.workCenter = workCenter;
	}

	/**
	 * @return the spentHours
	 */
	@Column(name = "spenthours_")
	public BigDecimal getSpentHours()
	{
		return spentHours;
	}

	/**
	 * @param spentHours the spentHours to set
	 */
	public void setSpentHours(BigDecimal spentHours)
	{
		this.spentHours = spentHours;
	}

	/**
	 * @return the spentHoursUnit
	 */
	@Column(name = "spenthoursunit_")
	public String getSpentHoursUnit()
	{
		return spentHoursUnit;
	}

	/**
	 * @param spentHoursUnit the spentHoursUnit to set
	 */
	public void setSpentHoursUnit(String spentHoursUnit)
	{
		this.spentHoursUnit = spentHoursUnit;
	}

	/**
	 * @return the startDate
	 */
	@Column(name = "startdate_")
	public String getStartDate()
	{
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return the startTime
	 */
	@Column(name = "starttime_")
	public String getStartTime()
	{
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime)
	{
		this.startTime = startTime;
	}

	/**
	 * @return the finishDate
	 */
	@Column(name = "finishdate_")
	public String getFinishDate()
	{
		return finishDate;
	}

	/**
	 * @param finishDate the finishDate to set
	 */
	public void setFinishDate(String finishDate)
	{
		this.finishDate = finishDate;
	}

	/**
	 * @return the finishTime
	 */
	@Column(name = "finishtime_")
	public String getFinishTime()
	{
		return finishTime;
	}

	/**
	 * @param finishTime the finishTime to set
	 */
	public void setFinishTime(String finishTime)
	{
		this.finishTime = finishTime;
	}

	/**
	 * @return the employeeId
	 */
	@Column(name = "employeeid_")
	public String getEmployeeId()
	{
		return employeeId;
	}

	/**
	 * @param employeeId the employeeId to set
	 */
	public void setEmployeeId(String employeeId)
	{
		this.employeeId = employeeId;
	}

	/**
	 * @return the activityType
	 */
	@Column(name = "activitytype_")
	public String getActivityType()
	{
		return activityType;
	}

	/**
	 * @param activityType the activityType to set
	 */
	public void setActivityType(String activityType)
	{
		this.activityType = activityType;
	}

}
