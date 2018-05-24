package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;
@Entity
@Table(name = "sap_operation")
public class Operation extends BaseEntity
{
    private String operationId;
    private String shortText;
    private String workCenter;
    private String controlKey;
    private String activityType;
    private String workActivity;
    private String normalDuration;
    private String unitForWork;
    private String workId;
    private String scheduledStartDate;
    private String scheduledStartTime;
    private String scheduledFinishDate;
    private String scheduledFinishTime;
    private String imptFilename;
    

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; operationId:"+operationId+
    			"; shortText:"+shortText+
    			"; workCenter:"+workCenter+
    			"; controlKey:"+controlKey+
    			"; activityType:"+activityType+
    			"; workActivity:"+workActivity+
    			"; normalDuration:"+normalDuration+
    			"; unitForWork:"+unitForWork+
    			"; workId:"+workId+
    			"; scheduledStartDate:"+scheduledStartDate+
    			"; scheduledStartTime:"+scheduledStartTime+
    			"; scheduledFinishDate:"+scheduledFinishDate+
    			"; scheduledFinishTime:"+scheduledFinishTime+
    			"; imptFilename:"+imptFilename;
    }
    
    @Column(name = "operationid_")
    public String getOperationId()
    {
        return operationId;
    }
    public void setOperationId(String operationId)
    {
        this.operationId = operationId;
    }
    @Column(name = "shorttext_")
    public String getShortText()
    {
        return shortText;
    }
    public void setShortText(String shortText)
    {
        this.shortText = shortText;
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
    @Column(name = "controlkey_")
    public String getControlKey()
    {
        return controlKey;
    }
    public void setControlKey(String controlKey)
    {
        this.controlKey = controlKey;
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
    @Column(name = "normalduration_")
    public String getNormalDuration()
    {
        return normalDuration;
    }
    public void setNormalDuration(String normalDuration)
    {
        this.normalDuration = normalDuration;
    }
    @Column(name = "unitforwork_")
    public String getUnitForWork()
    {
        return unitForWork;
    }
    public void setUnitForWork(String unitForWork)
    {
        this.unitForWork = unitForWork;
    }
    @Column(name = "workid_")
    public String getWorkId()
    {
        return workId;
    }
    public void setWorkId(String workId)
    {
        this.workId = workId;
    }
    @Column(name = "scheduledstartdate_")
    public String getScheduledStartDate()
    {
        return scheduledStartDate;
    }
    public void setScheduledStartDate(String scheduledStartDate)
    {
        this.scheduledStartDate = scheduledStartDate;
    }
    @Column(name = "scheduledstarttime_")
    public String getScheduledStartTime()
    {
        return scheduledStartTime;
    }
    public void setScheduledStartTime(String scheduledStartTime)
    {
        this.scheduledStartTime = scheduledStartTime;
    }
    @Column(name = "scheduledfinishdate_")
    public String getScheduledFinishDate()
    {
        return scheduledFinishDate;
    }
    public void setScheduledFinishDate(String scheduledFinishDate)
    {
        this.scheduledFinishDate = scheduledFinishDate;
    }
    @Column(name = "scheduledfinishtime_")
    public String getScheduledFinishTime()
    {
        return scheduledFinishTime;
    }
    public void setScheduledFinishTime(String scheduledFinishTime)
    {
        this.scheduledFinishTime = scheduledFinishTime;
    }
    /**
     * @return the imptFilename
     */
    @Column(name = "imptfilename_")
    public String getImptFilename()
    {
        return imptFilename;
    }
    /**
     * @param imptFilename the imptFilename to set
     */
    public void setImptFilename(String imptFilename)
    {
        this.imptFilename = imptFilename;
    }
    /**
     * @return the workActivity
     */
    @Column(name = "workactivity_")
    public String getWorkActivity()
    {
        return workActivity;
    }
    /**
     * @param workActivity the workActivity to set
     */
    public void setWorkActivity(String workActivity)
    {
        this.workActivity = workActivity;
    }    
}
