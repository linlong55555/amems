package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "sap_work")
public class Work extends BaseEntity
{
    private String workId; //工单编号 7.18
    private String controlNo;
    private String taskId;
    private String funcLocation;
    private String orderType;
    private String workCenter;
    private String revisionId;
    private String companyCode;
    private String scheduledStartDate;
    private String scheduledFinishDate;
    private String scheduledStartTime;
    private String scheduledFinishTime;
    private String earliestStartDate;
    private String earliestStartTime;
    private String latestFinishDate;
    private String latestFinishTime;
    private String funcLocationStructureIndicator;
    private String description;
    private String plant;
    private String customerId;
    private String imptFilename;
    

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; workId:"+workId+
    			"; controlNo:"+controlNo+
    			"; taskId:"+taskId+
    			"; funcLocation:"+funcLocation+
    			"; orderType:"+orderType+
    			"; workCenter:"+workCenter+
    			"; revisionId:"+revisionId+
    			"; companyCode:"+companyCode+
    			"; scheduledStartDate:"+scheduledStartDate+
    			"; scheduledFinishDate:"+scheduledFinishDate+
    			"; scheduledStartTime:"+scheduledStartTime+
    			"; scheduledFinishTime:"+scheduledFinishTime+
    			"; earliestStartDate:"+earliestStartDate+
    			"; earliestStartTime:"+earliestStartTime+
    			"; latestFinishDate:"+latestFinishDate+
    			"; latestFinishTime:"+latestFinishTime+
    			"; funcLocationStructureIndicator:"+funcLocationStructureIndicator+
    			"; description:"+description+
    			"; plant:"+plant+
    			"; customerId:"+customerId+
    			"; imptFilename:"+imptFilename;
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
    @Column(name = "taskid_")
    public String getTaskId()
    {
        return taskId;
    }
    public void setTaskId(String taskId)
    {
        this.taskId = taskId;
    }
    @Column(name = "funclocation_")
    public String getFuncLocation()
    {
        return funcLocation;
    }
    public void setFuncLocation(String funcLocation)
    {
        this.funcLocation = funcLocation;
    }
    @Column(name = "ordertype_")
    public String getOrderType()
    {
        return orderType;
    }
    public void setOrderType(String orderType)
    {
        this.orderType = orderType;
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
    @Column(name = "revisionid_")
    public String getRevisionId()
    {
        return revisionId;
    }
    public void setRevisionId(String revisionId)
    {
        this.revisionId = revisionId;
    }
    @Column(name = "companycode_")
    public String getCompanyCode()
    {
        return companyCode;
    }
    public void setCompanyCode(String companyCode)
    {
        this.companyCode = companyCode;
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
    @Column(name = "scheduledfinishdate_")
    public String getScheduledFinishDate()
    {
        return scheduledFinishDate;
    }
    public void setScheduledFinishDate(String scheduledFinishDate)
    {
        this.scheduledFinishDate = scheduledFinishDate;
    }
    @Column(name = "funclocationstructureindicator_")
    public String getFuncLocationStructureIndicator()
    {
        return funcLocationStructureIndicator;
    }
    public void setFuncLocationStructureIndicator(String funcLocationStructureIndicator)
    {
        this.funcLocationStructureIndicator = funcLocationStructureIndicator;
    }
    @Column(name = "description_")
    public String getDescription()
    {
        return description;
    }
    public void setDescription(String description)
    {
        this.description = description;
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
    @Column(name = "controlno_")
    public String getControlNo()
    {
        return controlNo;
    }
    public void setControlNo(String controlNo)
    {
        this.controlNo = controlNo;
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
    @Column(name = "scheduledfinishtime_")
    public String getScheduledFinishTime()
    {
        return scheduledFinishTime;
    }
    public void setScheduledFinishTime(String scheduledFinishTime)
    {
        this.scheduledFinishTime = scheduledFinishTime;
    }
    @Column(name = "earlieststartdate_")
    public String getEarliestStartDate()
    {
        return earliestStartDate;
    }
    public void setEarliestStartDate(String earliestStartDate)
    {
        this.earliestStartDate = earliestStartDate;
    }
    @Column(name = "latestfinishdate_")
    public String getLatestFinishDate()
    {
        return latestFinishDate;
    }
    public void setLatestFinishDate(String latestFinishDate)
    {
        this.latestFinishDate = latestFinishDate;
    }
    @Column(name = "customerid_")
    public String getCustomerId()
    {
        return customerId;
    }
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    @Column(name = "earlieststarttime_")
    public String getEarliestStartTime()
    {
        return earliestStartTime;
    }
    public void setEarliestStartTime(String earliestStartTime)
    {
        this.earliestStartTime = earliestStartTime;
    }
    @Column(name = "latestfinishtime_")
    public String getLatestFinishTime()
    {
        return latestFinishTime;
    }
    public void setLatestFinishTime(String latestFinishTime)
    {
        this.latestFinishTime = latestFinishTime;
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
    
}
