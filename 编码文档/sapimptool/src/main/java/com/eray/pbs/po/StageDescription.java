package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_stagedescription")
public class StageDescription extends BaseEntity
{
	private String stage;
    private String stageDescription;
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; stage:"+stage+
    			"; stageDescription:"+stageDescription;
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
	 * @return the stageDescription
	 */
	@Column(name = "stagedescription_")
	public String getStageDescription()
	{
		return stageDescription;
	}
	/**
	 * @param stageDescription the stageDescription to set
	 */
	public void setStageDescription(String stageDescription)
	{
		this.stageDescription = stageDescription;
	}
}
