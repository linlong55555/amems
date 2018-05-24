package com.eray.pbs.po;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_workhours")
public class WorkHoursPbs  extends BaseEntity
{
    private Long workId;
    private String stage;
    private String workCenter;
    private BigDecimal mechanicHours;
    private BigDecimal inspectionHours;
    private String modifyFlag; //Y:新增的  N:已有,MI没有改变的  X:被删除的
    private String wid; //工单编号 2016.12.17
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    
    
    @Column(name = "wid_")
	public String getWid() {
		return wid;
	}

	@Override
	public String toString() {
		return "WorkHoursPbs [workId=" + workId + ", stage=" + stage
				+ ", workCenter=" + workCenter + ", mechanicHours="
				+ mechanicHours + ", inspectionHours=" + inspectionHours
				+ ", modifyFlag=" + modifyFlag + ", wid=" + wid + "]";
	}

	public void setWid(String wid) {
		this.wid = wid;
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
    /**
     * @return the workId
     */
    @Column(name = "workid_")
    public Long getWorkId()
    {
        return workId;
    }
    /**
     * @param workId the workId to set
     */
    public void setWorkId(Long workId)
    {
        this.workId = workId;
    }
    
    @Column(name = "modifyflag_")
	public String getModifyFlag() {
		return modifyFlag;
	}
	public void setModifyFlag(String modifyFlag) {
		this.modifyFlag = modifyFlag;
	}
}
