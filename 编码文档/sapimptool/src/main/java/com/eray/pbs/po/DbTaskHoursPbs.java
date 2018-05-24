package com.eray.pbs.po;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_dbtaskhours")
public class DbTaskHoursPbs  extends BaseEntity
{
    private Long taskId;
    private String stage;
    private String workCenter;
    private BigDecimal mechanicHours;
    private BigDecimal inspectionHours;
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; taskId:"+taskId+
    			"; stage:"+stage+
    			"; workCenter:"+workCenter+
    			"; mechanicHours:"+mechanicHours+
    			"; inspectionHours:"+inspectionHours;
    }
    /**
     * @return the taskId
     */
    @Column(name = "taskid_")
    public Long getTaskId()
    {
        return taskId;
    }
    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(Long taskId)
    {
        this.taskId = taskId;
    }
    /**
     * @return the stage
     */
    @Column(name = "stage_")
    public String getStage()
    {
        return stage;
    }
    /**
     * @param stage the stage to set
     */
    public void setStage(String stage)
    {
        this.stage = stage;
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
     * @return the mechanicHours
     */
    @Column(name = "mechanichours_")
    public BigDecimal getMechanicHours()
    {
        return mechanicHours;
    }
    /**
     * @param mechanicHours the mechanicHours to set
     */
    public void setMechanicHours(BigDecimal mechanicHours)
    {
        this.mechanicHours = mechanicHours;
    }
    /**
     * @return the inspectionHours
     */
    @Column(name = "inspectionhours_")
    public BigDecimal getInspectionHours()
    {
        return inspectionHours;
    }
    /**
     * @param inspectionHours the inspectionHours to set
     */
    public void setInspectionHours(BigDecimal inspectionHours)
    {
        this.inspectionHours = inspectionHours;
    }
}
