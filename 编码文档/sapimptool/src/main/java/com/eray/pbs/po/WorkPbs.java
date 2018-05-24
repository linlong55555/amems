package com.eray.pbs.po;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_work")
public class WorkPbs extends BaseEntity
{
    private String description;
    private String type;
    private String status;
    private String plant;
    private String mainWorkCenter;
    private String currentWorkCenter;
    private Timestamp basicStartDate;
    private Timestamp basicFinishDate;
    private Timestamp scheduledStartDate;
    private Timestamp scheduledFinishDate;
    private Timestamp actualStartDate;
    private Timestamp actualFinishDate;
    private String rid;
    private String stage;
    private String wid;
    private BigDecimal workhours;
    private Integer relativeDays;
    private BigDecimal duration;
    private String controlNumber;
    private String cardNumber;
    private String customerId;
    private String stageDescription;
    private String zone;
    private String sourceOfJobCard;
    private Integer workHourStatus;
    private BigDecimal actualHours;
    private Integer msStatus;
    private String durationChanged;
    private String actualHourChanged;

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id:"+id+
    			"; description:"+description+
    			"; type:"+type+
    			"; status:"+status+
    			"; plant:"+plant+
    			"; mainWorkCenter:"+mainWorkCenter+
    			"; currentWorkCenter:"+currentWorkCenter+
    			"; basicStartDate:"+basicStartDate+
    			"; basicFinishDate:"+basicFinishDate+
    			"; scheduledStartDate:"+scheduledStartDate+
    			"; scheduledFinishDate:"+scheduledFinishDate+
    			"; actualStartDate:"+actualStartDate+
    			"; actualFinishDate:"+actualFinishDate+
    			"; rid:"+rid+
    			"; stage:"+stage+
    			"; wid:"+wid+
    			"; workhours:"+workhours+
    			"; relativeDays:"+relativeDays+
    			"; duration:"+duration+
    			"; controlNumber:"+controlNumber+
    			"; cardNumber:"+cardNumber+
    			"; customerId:"+customerId+
    			"; stageDescription:"+stageDescription+
    			"; zone:"+zone+
    			"; sourceOfJobCard:"+sourceOfJobCard+
    			"; workHourStatus:"+workHourStatus+
    			"; actualHours:"+actualHours+
    			"; msStatus:"+msStatus+
    			"; durationChanged:"+durationChanged+
    			"; actualHourChanged:"+actualHourChanged;
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
    @Column(name = "type_")
    public String getType()
    {
        return type;
    }
    public void setType(String type)
    {
        this.type = type;
    }
    @Column(name = "mainworkcenter_")
    public String getMainWorkCenter()
    {
        return mainWorkCenter;
    }
    public void setMainWorkCenter(String mainWorkCenter)
    {
        this.mainWorkCenter = mainWorkCenter;
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
    public Date getScheduledStartDate()
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
    @Column(name = "rid_")
    public String getRid()
    {
        return rid;
    }
    public void setRid(String rid)
    {
        this.rid = rid;
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
    @Column(name = "controlnumber_")
    public String getControlNumber()
    {
        return controlNumber;
    }
    public void setControlNumber(String controlNumber)
    {
        this.controlNumber = controlNumber;
    }
    @Column(name = "cardnumber_")
    public String getCardNumber()
    {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
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
    @Column(name = "status_")
    public String getStatus()
    {
        return status;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }
    @Column(name = "stage_")
    public String getStage()
    {
        return stage;
    }
    public void setStage(String stage)
    {
        this.stage = stage;
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
    @Column(name = "stagedescription_")
    public String getStageDescription()
    {
        return stageDescription;
    }
    public void setStageDescription(String stageDescription)
    {
        this.stageDescription = stageDescription;
    }
    @Column(name = "actualstartdate_")
    public Timestamp getActualStartDate()
    {
        return actualStartDate;
    }
    public void setActualStartDate(Timestamp actualStartDate)
    {
        this.actualStartDate = actualStartDate;
    }
    @Column(name = "actualfinishdate_")
    public Timestamp getActualFinishDate()
    {
        return actualFinishDate;
    }
    public void setActualFinishDate(Timestamp actualFinishDate)
    {
        this.actualFinishDate = actualFinishDate;
    }
    @Column(name = "currentworkcenter_")
    public String getCurrentWorkCenter()
    {
        return currentWorkCenter;
    }
    public void setCurrentWorkCenter(String currentWorkCenter)
    {
        this.currentWorkCenter = currentWorkCenter;
    }
    @Column(name = "zone_")
    public String getZone()
    {
        return zone;
    }
    public void setZone(String zone)
    {
        this.zone = zone;
    }
    @Column(name = "sourceofjobcard_")
    public String getSourceOfJobCard()
    {
        return sourceOfJobCard;
    }
    public void setSourceOfJobCard(String sourceOfJobCard)
    {
        this.sourceOfJobCard = sourceOfJobCard;
    }
    /**
     * @return the relativeDays
     */
    @Column(name = "relativedays_")
    public Integer getRelativeDays()
    {
        return relativeDays;
    }
    /**
     * @param relativeDays the relativeDays to set
     */
    public void setRelativeDays(Integer relativeDays)
    {
        this.relativeDays = relativeDays;
    }
    /**
     * @return the duration
     */
    @Column(name = "duration_")
    public BigDecimal getDuration()
    {
        return duration;
    }
    /**
     * @param duration the duration to set
     */
    public void setDuration(BigDecimal duration)
    {
        this.duration = duration;
    }
    /**
     * @return the workHourStatus
     */
    @Column(name = "workhourstatus_")
    public Integer getWorkHourStatus()
    {
        return workHourStatus;
    }
    /**
     * @param workHourStatus the workHourStatus to set
     */
    public void setWorkHourStatus(Integer workHourStatus)
    {
        this.workHourStatus = workHourStatus;
    }
	/**
	 * @return the actualHours
	 */
    @Column(name = "actualhours_")
	public BigDecimal getActualHours()
	{
		return actualHours;
	}
	/**
	 * @param actualHours the actualHours to set
	 */
	public void setActualHours(BigDecimal actualHours)
	{
		this.actualHours = actualHours;
	}
	/**
	 * @return the msStatus
	 */
	@Column(name = "msstatus_")
	public Integer getMsStatus()
	{
		return msStatus;
	}
	/**
	 * @param msStatus the msStatus to set
	 */
	public void setMsStatus(Integer msStatus)
	{
		this.msStatus = msStatus;
	}

	/**
	 * @return the durationChanged
	 */
	@Column(name = "durationchanged_")
	public String getDurationChanged()
	{
		return durationChanged;
	}

	/**
	 * @param durationChanged the durationChanged to set
	 */
	public void setDurationChanged(String durationChanged)
	{
		this.durationChanged = durationChanged;
	}

	/**
	 * @return the actualHourChanged
	 */
	@Column(name = "actualhourchanged_")
	public String getActualHourChanged()
	{
		return actualHourChanged;
	}

	/**
	 * @param actualHourChanged the actualHourChanged to set
	 */
	public void setActualHourChanged(String actualHourChanged)
	{
		this.actualHourChanged = actualHourChanged;
	}
}
