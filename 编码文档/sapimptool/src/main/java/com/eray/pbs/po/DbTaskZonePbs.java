package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_dbtaskzone")
public class DbTaskZonePbs  extends BaseEntity
{
    private Long taskId;
    private String zone;
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; taskId:"+taskId+
    			"; zone:"+zone;
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
     * @return the zone
     */
    @Column(name = "zone_")
    public String getZone()
    {
        return zone;
    }
    /**
     * @param zone the zone to set
     */
    public void setZone(String zone)
    {
        this.zone = zone;
    }
    
}
