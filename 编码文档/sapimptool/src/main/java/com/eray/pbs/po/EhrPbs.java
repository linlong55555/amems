package com.eray.pbs.po;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_ehr")
public class EhrPbs extends BaseEntity
{
	private String workOrder;
	private String controlNo;
	private String orignal;
	private String description;
	private BigDecimal workhours;
	private BigDecimal cap;
	private String defectType;
	private String manhourChargableStatus;
	private String billingRule;
	private String partialQuoted;
	private String specialCharges;
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; workOrder:"+workOrder+
    			"; controlNo:"+controlNo+
    			"; orignal:"+orignal+
    			"; description:"+description+
    			"; workhours:"+workhours+
    			"; cap:"+cap+
    			"; defectType:"+defectType+
    			"; manhourChargableStatus:"+manhourChargableStatus+
    			"; billingRule:"+billingRule+
    			"; partialQuoted:"+partialQuoted+
    			"; specialCharges:"+specialCharges;
    }
	/**
	 * @return the workOrder
	 */
	@Column(name = "workorder_")
	public String getWorkOrder()
	{
		return workOrder;
	}
	/**
	 * @param workOrder the workOrder to set
	 */
	public void setWorkOrder(String workOrder)
	{
		this.workOrder = workOrder;
	}
	/**
	 * @return the controlNo
	 */
	@Column(name = "controlno_")
	public String getControlNo()
	{
		return controlNo;
	}
	/**
	 * @param controlNo the controlNo to set
	 */
	public void setControlNo(String controlNo)
	{
		this.controlNo = controlNo;
	}
	/**
	 * @return the orignal
	 */
	@Column(name = "orignal_")
	public String getOrignal()
	{
		return orignal;
	}
	/**
	 * @param orignal the orignal to set
	 */
	public void setOrignal(String orignal)
	{
		this.orignal = orignal;
	}
	/**
	 * @return the description
	 */
	@Column(name = "description_")
	public String getDescription()
	{
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	/**
	 * @return the workhours
	 */
	@Column(name = "workhours_")
	public BigDecimal getWorkhours()
	{
		return workhours;
	}
	/**
	 * @param workhours the workhours to set
	 */
	public void setWorkhours(BigDecimal workhours)
	{
		this.workhours = workhours;
	}
	/**
	 * @return the cap
	 */
	@Column(name = "cap_")
	public BigDecimal getCap()
	{
		return cap;
	}
	/**
	 * @param cap the cap to set
	 */
	public void setCap(BigDecimal cap)
	{
		this.cap = cap;
	}
	/**
	 * @return the defectType
	 */
	@Column(name = "defecttype_")
	public String getDefectType()
	{
		return defectType;
	}
	/**
	 * @param defectType the defectType to set
	 */
	public void setDefectType(String defectType)
	{
		this.defectType = defectType;
	}
	/**
	 * @return the manhourChargableStatus
	 */
	@Column(name = "manhourchargablestatus_")
	public String getManhourChargableStatus()
	{
		return manhourChargableStatus;
	}
	/**
	 * @param manhourChargableStatus the manhourChargableStatus to set
	 */
	public void setManhourChargableStatus(String manhourChargableStatus)
	{
		this.manhourChargableStatus = manhourChargableStatus;
	}
	/**
	 * @return the billingRule
	 */
	@Column(name = "billingrule_")
	public String getBillingRule()
	{
		return billingRule;
	}
	/**
	 * @param billingRule the billingRule to set
	 */
	public void setBillingRule(String billingRule)
	{
		this.billingRule = billingRule;
	}
	/**
	 * @return the partialQuoted
	 */
	@Column(name = "partialquoted_")
	public String getPartialQuoted()
	{
		return partialQuoted;
	}
	/**
	 * @param partialQuoted the partialQuoted to set
	 */
	public void setPartialQuoted(String partialQuoted)
	{
		this.partialQuoted = partialQuoted;
	}
	/**
	 * @return the specialCharges
	 */
	@Column(name = "specialcharges_")
	public String getSpecialCharges()
	{
		return specialCharges;
	}
	/**
	 * @param specialCharges the specialCharges to set
	 */
	public void setSpecialCharges(String specialCharges)
	{
		this.specialCharges = specialCharges;
	}
}
