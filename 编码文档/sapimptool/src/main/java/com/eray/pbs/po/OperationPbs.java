package com.eray.pbs.po;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;
@Entity
@Table(name = "pbs_operation")
public class OperationPbs extends BaseEntity
{
    private String wid;
    private String operationNumber;
    private String plant;
    private String workCenter;
    private String operationDescription;
    private BigDecimal workActivity;
    private BigDecimal workhours;
    private String timeUnit;
    private Integer duration;
    private String activityType;
    private Timestamp basicStartDate;
    private Timestamp basicFinishDate;
    private Timestamp scheduledStartDate;
    private Timestamp scheduledFinishDate;
    private String sortField;
    

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    	    	"; wid:"+wid+
    			"; operationNumber:"+operationNumber+
    			"; plant:"+plant+
    			"; workCenter:"+workCenter+
    			"; operationDescription:"+operationDescription+
    			"; workActivity:"+workActivity+
    			"; workhours:"+workhours+
    			"; timeUnit:"+timeUnit+
    			"; duration:"+duration+
    			"; activityType:"+activityType+
    			"; basicStartDate:"+basicStartDate+
    			"; basicFinishDate:"+basicFinishDate+
    			"; scheduledStartDate:"+scheduledStartDate+
    			"; scheduledFinishDate:"+scheduledFinishDate+
    			"; sortField:"+sortField;
    }
    
    @Column(name = "workcenter_")
    public String getWorkCenter()
    {
        return workCenter;
    }
    public void setWorkCenter(String workCenter)
    {
        this.workCenter = workCenter;
    }
    @Column(name = "activitytype_")
    public String getActivityType()
    {
        return activityType;
    }
    public void setActivityType(String activityType)
    {
        this.activityType = activityType;
    }
    @Column(name = "wid_")
    public String getWid()
    {
        return wid;
    }
    public void setWid(String wid)
    {
        this.wid = wid;
    }
    @Column(name = "operationnumber_")
    public String getOperationNumber()
    {
        return operationNumber;
    }
    public void setOperationNumber(String operationNumber)
    {
        this.operationNumber = operationNumber;
    }
    @Column(name = "plant_")
    public String getPlant()
    {
        return plant;
    }
    public void setPlant(String plant)
    {
        this.plant = plant;
    }
    @Column(name = "operationdescription_")
    public String getOperationDescription()
    {
        return operationDescription;
    }
    public void setOperationDescription(String operationDescription)
    {
        this.operationDescription = operationDescription;
    }
    @Column(name = "workhours_")
    public BigDecimal getWorkhours()
    {
        return workhours;
    }
    public void setWorkhours(BigDecimal workhours)
    {
        this.workhours = workhours;
    }
    @Column(name = "timeunit_")
    public String getTimeUnit()
    {
        return timeUnit;
    }
    public void setTimeUnit(String timeUnit)
    {
        this.timeUnit = timeUnit;
    }
    @Column(name = "duration_")
    public Integer getDuration()
    {
        return duration;
    }
    public void setDuration(Integer duration)
    {
        this.duration = duration;
    }
    @Column(name = "basicstartdate_")
    public Timestamp getBasicStartDate()
    {
        return basicStartDate;
    }
    public void setBasicStartDate(Timestamp basicStartDate)
    {
        this.basicStartDate = basicStartDate;
    }
    @Column(name = "basicfinishdate_")
    public Timestamp getBasicFinishDate()
    {
        return basicFinishDate;
    }
    public void setBasicFinishDate(Timestamp basicFinishDate)
    {
        this.basicFinishDate = basicFinishDate;
    }
    @Column(name = "scheduledstartdate_")
    public Timestamp getScheduledStartDate()
    {
        return scheduledStartDate;
    }
    public void setScheduledStartDate(Timestamp scheduledStartDate)
    {
        this.scheduledStartDate = scheduledStartDate;
    }
    @Column(name = "scheduledfinishdate_")
    public Timestamp getScheduledFinishDate()
    {
        return scheduledFinishDate;
    }
    public void setScheduledFinishDate(Timestamp scheduledFinishDate)
    {
        this.scheduledFinishDate = scheduledFinishDate;
    }

    @Column(name = "sortfield_")
    public String getSortField()
    {
        return sortField;
    }
    public void setSortField(String sortField)
    {
        this.sortField = sortField;
    }
    /**
     * @return the workActivity
     */
    @Column(name = "workactivity_")
    public BigDecimal getWorkActivity()
    {
        return workActivity;
    }
    /**
     * @param workActivity the workActivity to set
     */
    public void setWorkActivity(BigDecimal workActivity)
    {
        this.workActivity = workActivity;
    }
}
