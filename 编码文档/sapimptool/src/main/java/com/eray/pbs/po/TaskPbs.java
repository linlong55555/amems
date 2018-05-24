package com.eray.pbs.po;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
@Entity
@Table(name = "pbs_task")
public class TaskPbs extends BaseEntity
{
    private String groupId;
    private String groupCounter;
    private String keyDate;
    private String description;
    private String planningPlant;
    private String cardNumber;
    private String workCenter;
    private String stage;
    private String aircraftType;
    private String sourceOfJobCard;
    private String sourceReference;
    private String zone;
    private String ataChapter;
    private String check;
    private String operationNumber;
    private String operationDescription;
    private String operationWorkCenter;
    private BigDecimal workHours;
    private String activityType;
    private String operationNumberRelation;
    private String relationType;
    private String customer;
    private String owner;
    private BigDecimal standardManhour;
    private String elapsedTime;
    private String stageDescription;
    private Integer workHourStatus;
    private String workhourNoReason;
    private BigDecimal dbWorkhours;
    private String lastModifier;
    private Timestamp lastModifyDate;
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return 
	    		"id:"+id+
	    		"; groupId:"+groupId+
	    		"; groupCounter:"+groupCounter+
	    		"; keyDate:"+keyDate+
	    		"; description:"+description+
	    		"; planningPlant:"+planningPlant+
	    		"; cardNumber:"+cardNumber+
	    		"; workCenter:"+workCenter+
	    		"; stage:"+stage+
	    		"; aircraftType:"+aircraftType+
	    		"; sourceOfJobCard:"+sourceOfJobCard+
	    		"; sourceReference:"+sourceReference+
	    		"; zone:"+zone+
	    		"; ataChapter:"+ataChapter+
	    		"; check:"+check+
	    		"; operationNumber:"+operationNumber+
	    		"; operationDescription:"+operationDescription+
	    		"; operationWorkCenter:"+operationWorkCenter+
	    		"; workHours:"+workHours+
	    		"; activityType:"+activityType+
	    		"; operationNumberRelation:"+operationNumberRelation+
	    		"; relationType:"+relationType+
	    		"; customer:"+customer+
	    		"; owner:"+owner+
	    		"; standardManhour:"+standardManhour+
	    		"; elapsedTime:"+elapsedTime+
	    		"; stageDescription:"+stageDescription+
	    		"; workHourStatus:"+workHourStatus+
	    		"; workhourNoReason:"+workhourNoReason+
	    		"; dbWorkhours:"+dbWorkhours+
	    		"; lastModifier:"+lastModifier+
	    		"; lastModifyDate:"+lastModifyDate;
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
    @Column(name = "groupcounter_")
    public String getGroupCounter()
    {
        return groupCounter;
    }
    public void setGroupCounter(String groupCounter)
    {
        this.groupCounter = groupCounter;
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
    @Column(name = "planningplant_")
    public String getPlanningPlant()
    {
        return planningPlant;
    }
    public void setPlanningPlant(String planningPlant)
    {
        this.planningPlant = planningPlant;
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
    @Column(name = "aircrafttype_")
    public String getAircraftType()
    {
        return aircraftType;
    }
    public void setAircraftType(String aircraftType)
    {
        this.aircraftType = aircraftType;
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
    @Column(name = "sourcereference_")
    public String getSourceReference()
    {
        return sourceReference;
    }
    public void setSourceReference(String sourceReference)
    {
        this.sourceReference = sourceReference;
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
    @Column(name = "atachapter_")
    public String getAtaChapter()
    {
        return ataChapter;
    }
    public void setAtaChapter(String ataChapter)
    {
        this.ataChapter = ataChapter;
    }
    @Column(name = "check_")
    public String getCheck()
    {
        return check;
    }
    public void setCheck(String check)
    {
        this.check = check;
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
    public BigDecimal getWorkHours()
    {
        return workHours;
    }
    public void setWorkHours(BigDecimal workHours)
    {
        this.workHours = workHours;
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
    @Column(name = "operationnumberrelation_")
    public String getOperationNumberRelation()
    {
        return operationNumberRelation;
    }
    public void setOperationNumberRelation(String operationNumberRelation)
    {
        this.operationNumberRelation = operationNumberRelation;
    }
    @Column(name = "relationtype_")
    public String getRelationType()
    {
        return relationType;
    }
    public void setRelationType(String relationType)
    {
        this.relationType = relationType;
    }
    @Column(name = "customer_")
    public String getCustomer()
    {
        return customer;
    }
    public void setCustomer(String customer)
    {
        this.customer = customer;
    }
    @Column(name = "owner_")
    public String getOwner()
    {
        return owner;
    }
    public void setOwner(String owner)
    {
        this.owner = owner;
    }
    @Column(name = "standardmanhour_")
    public BigDecimal getStandardManhour()
    {
        return standardManhour;
    }
    public void setStandardManhour(BigDecimal standardManhour)
    {
        this.standardManhour = standardManhour;
    }
    @Column(name = "keydate_")
    public String getKeyDate()
    {
        return keyDate;
    }
    public void setKeyDate(String keyDate)
    {
        this.keyDate = keyDate;
    }
    @Column(name = "groupid_")
    public String getGroupId()
    {
        return groupId;
    }
    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
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
    @Column(name = "operationworkcenter_")
    public String getOperationWorkCenter()
    {
        return operationWorkCenter;
    }
    public void setOperationWorkCenter(String operationWorkCenter)
    {
        this.operationWorkCenter = operationWorkCenter;
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
	 * @return the elapsedTime
	 */
    @Column(name = "elapsedtime_")
	public String getElapsedTime()
	{
		return elapsedTime;
	}
	/**
	 * @param elapsedTime the elapsedTime to set
	 */
	public void setElapsedTime(String elapsedTime)
	{
		this.elapsedTime = elapsedTime;
	}
	/**
	 * @return the workhourNoReason
	 */
	@Column(name = "workhournoreason_")
	public String getWorkhourNoReason()
	{
		return workhourNoReason;
	}
	/**
	 * @param workhourNoReason the workhourNoReason to set
	 */
	public void setWorkhourNoReason(String workhourNoReason)
	{
		this.workhourNoReason = workhourNoReason;
	}
	/**
	 * @return the dbWorkhours
	 */
	@Column(name = "dbworkhours_")
	public BigDecimal getDbWorkhours()
	{
		return dbWorkhours;
	}
	/**
	 * @param dbWorkhours the dbWorkhours to set
	 */
	public void setDbWorkhours(BigDecimal dbWorkhours)
	{
		this.dbWorkhours = dbWorkhours;
	}
	/**
	 * @return the lastModifier
	 */
	@Column(name = "lastmodifier_")
	public String getLastModifier()
	{
		return lastModifier;
	}
	/**
	 * @param lastModifier the lastModifier to set
	 */
	public void setLastModifier(String lastModifier)
	{
		this.lastModifier = lastModifier;
	}
	/**
	 * @return the lastModifyDate
	 */
	@Column(name = "lastmodifydate_")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	public Timestamp getLastModifyDate()
	{
		return lastModifyDate;
	}
	/**
	 * @param lastModifyDate the lastModifyDate to set
	 */
	public void setLastModifyDate(Timestamp lastModifyDate)
	{
		this.lastModifyDate = lastModifyDate;
	}
}
