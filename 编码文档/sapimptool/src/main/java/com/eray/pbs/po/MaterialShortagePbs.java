package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_materialshortage")
public class MaterialShortagePbs extends BaseEntity
{
    private String cardNumber;
    private String workOrder;
    private String workOrderDescription;
    private String materialNumber;
    private String materialDescription;
    private String requiredQuantity;
    private String mrNumber;
    private String mrCreationDate;
    private String priority;
    private String requiredDeliveryDate;
    private String status;
    private String estimatedGrDate;
    private String workCenter;
    private String createdBy;
    private String remark;
    private String materialGroup;
    private String cfmFlag;
    private String specialStock;
    private String poNumber;
    private String incotermsDescription;
    private String poDepartureDate;
    private String awb;
    private String etdDepartureDate;
    private String etaArrivalDate;
    private Integer isLast;
    

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; cardNumber:"+cardNumber+
    			"; workOrder:"+workOrder+
    			"; workOrderDescription:"+workOrderDescription+
    			"; materialNumber:"+materialNumber+
    			"; materialDescription:"+materialDescription+
    			"; requiredQuantity:"+requiredQuantity+
    			"; mrNumber:"+mrNumber+
    			"; mrCreationDate:"+mrCreationDate+
    			"; priority:"+priority+
    			"; requiredDeliveryDate:"+requiredDeliveryDate+
    			"; status:"+status+
    			"; estimatedGrDate:"+estimatedGrDate+
    			"; workCenter:"+workCenter+
    			"; createdBy:"+createdBy+
    			"; remark:"+remark+
    			"; materialGroup:"+materialGroup+
    			"; cfmFlag:"+cfmFlag+
    			"; specialStock:"+specialStock+
    			"; poNumber:"+poNumber+
    			"; incotermsDescription:"+incotermsDescription+
    			"; poDepartureDate:"+poDepartureDate+
    			"; awb:"+awb+
    			"; etdDepartureDate:"+etdDepartureDate+
    			"; etaArrivalDate:"+etaArrivalDate+
    			"; isLast:"+isLast;
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
    @Column(name = "workorder_")
    public String getWorkOrder()
    {
        return workOrder;
    }
    public void setWorkOrder(String workOrder)
    {
        this.workOrder = workOrder;
    }
    @Column(name = "workorderdescription_")
    public String getWorkOrderDescription()
    {
        return workOrderDescription;
    }
    public void setWorkOrderDescription(String workOrderDescription)
    {
        this.workOrderDescription = workOrderDescription;
    }
    @Column(name = "materialnumber_")
    public String getMaterialNumber()
    {
        return materialNumber;
    }
    public void setMaterialNumber(String materialNumber)
    {
        this.materialNumber = materialNumber;
    }
    @Column(name = "materialdescription_")
    public String getMaterialDescription()
    {
        return materialDescription;
    }
    public void setMaterialDescription(String materialDescription)
    {
        this.materialDescription = materialDescription;
    }
    @Column(name = "requiredquantity_")
    public String getRequiredQuantity()
    {
        return requiredQuantity;
    }
    public void setRequiredQuantity(String requiredQuantity)
    {
        this.requiredQuantity = requiredQuantity;
    }
    @Column(name = "mrnumber_")
    public String getMrNumber()
    {
        return mrNumber;
    }
    public void setMrNumber(String mrNumber)
    {
        this.mrNumber = mrNumber;
    }
    @Column(name = "mrcreationdate_")
    public String getMrCreationDate()
    {
        return mrCreationDate;
    }
    public void setMrCreationDate(String mrCreationDate)
    {
        this.mrCreationDate = mrCreationDate;
    }
    @Column(name = "priority_")
    public String getPriority()
    {
        return priority;
    }
    public void setPriority(String priority)
    {
        this.priority = priority;
    }
    @Column(name = "requireddeliverydate_")
    public String getRequiredDeliveryDate()
    {
        return requiredDeliveryDate;
    }
    public void setRequiredDeliveryDate(String requiredDeliveryDate)
    {
        this.requiredDeliveryDate = requiredDeliveryDate;
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
    @Column(name = "estimatedgrdate_")
    public String getEstimatedGrDate()
    {
        return estimatedGrDate;
    }
    public void setEstimatedGrDate(String estimatedGrDate)
    {
        this.estimatedGrDate = estimatedGrDate;
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
    @Column(name = "createdby_")
    public String getCreatedBy()
    {
        return createdBy;
    }
    public void setCreatedBy(String createdBy)
    {
        this.createdBy = createdBy;
    }
    @Column(name = "remark_")
    public String getRemark()
    {
        return remark;
    }
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    @Column(name = "materialgroup_")
    public String getMaterialGroup()
    {
        return materialGroup;
    }
    public void setMaterialGroup(String materialGroup)
    {
        this.materialGroup = materialGroup;
    }
    @Column(name = "cfmflag_")
    public String getCfmFlag()
    {
        return cfmFlag;
    }
    public void setCfmFlag(String cfmFlag)
    {
        this.cfmFlag = cfmFlag;
    }
    @Column(name = "specialstock_")
    public String getSpecialStock()
    {
        return specialStock;
    }
    public void setSpecialStock(String specialStock)
    {
        this.specialStock = specialStock;
    }
    @Column(name = "ponumber_")
    public String getPoNumber()
    {
        return poNumber;
    }
    public void setPoNumber(String poNumber)
    {
        this.poNumber = poNumber;
    }
    @Column(name = "incotermsdescription_")
    public String getIncotermsDescription()
    {
        return incotermsDescription;
    }
    public void setIncotermsDescription(String incotermsDescription)
    {
        this.incotermsDescription = incotermsDescription;
    }
    @Column(name = "podeparturedate_")
    public String getPoDepartureDate()
    {
        return poDepartureDate;
    }
    public void setPoDepartureDate(String poDepartureDate)
    {
        this.poDepartureDate = poDepartureDate;
    }
    @Column(name = "awb_")
    public String getAwb()
    {
        return awb;
    }
    public void setAwb(String awb)
    {
        this.awb = awb;
    }
    /**
     * @return the etdDepartureDate
     */
    @Column(name = "etddeparturedate_")
    public String getEtdDepartureDate()
    {
        return etdDepartureDate;
    }
    /**
     * @param etdDepartureDate the etdDepartureDate to set
     */
    public void setEtdDepartureDate(String etdDepartureDate)
    {
        this.etdDepartureDate = etdDepartureDate;
    }
    /**
     * @return the etoArrivalDate
     */
    @Column(name = "etaarrivaldate_")
    public String getEtaArrivalDate()
    {
        return etaArrivalDate;
    }
    /**
     * @param etoArrivalDate the etoArrivalDate to set
     */
    public void setEtaArrivalDate(String etaArrivalDate)
    {
        this.etaArrivalDate = etaArrivalDate;
    }
	/**
	 * @return the isLast
	 */
    @Column(name = "islast_")
	public Integer getIsLast()
	{
		return isLast;
	}
	/**
	 * @param isLast the isLast to set
	 */
	public void setIsLast(Integer isLast)
	{
		this.isLast = isLast;
	}
    
}
