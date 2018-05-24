package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_dbtaskaccess")
public class DbTaskAccessPbs  extends BaseEntity
{
    private Long taskId;
    private String accessNo;
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; taskId:"+taskId+
    			"; accessNo:"+accessNo;
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
     * @return the accessNo
     */
    @Column(name = "accessno_")
    public String getAccessNo()
    {
        return accessNo;
    }
    /**
     * @param accessNo the accessNo to set
     */
    public void setAccessNo(String accessNo)
    {
        this.accessNo = accessNo;
    }
}
