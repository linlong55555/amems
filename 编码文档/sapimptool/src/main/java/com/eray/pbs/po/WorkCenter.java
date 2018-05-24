package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;
@Entity
@Table(name = "pbs_workcenter")
public class WorkCenter extends BaseEntity
{
	private String workCenter;

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; workCenter:"+workCenter;
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
}
