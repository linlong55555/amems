package com.eray.pbs.po;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_revision")
public class RevisionPbs extends BaseEntity
{
    private String rid;
    private String description;
    private String status;
    private String aircraftTailNumber;
    private String aircraftDescription;
    private String hangar;
    private String aircraftModel;
    private String style;
    private Timestamp plannedStartDate;
    private Timestamp plannedFinishDate;
    private String network;
    private String aircraftType;
    private String customer;
    private Timestamp actualStartDate;
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    	    	"; rid:"+rid+
    			"; description:"+description+
    			"; status:"+status+
    			"; aircraftTailNumber:"+aircraftTailNumber+
    			"; aircraftDescription:"+aircraftDescription+
    			"; hangar:"+hangar+
    			"; aircraftModel:"+aircraftModel+
    			"; style:"+style+
    			"; plannedStartDate:"+plannedStartDate+
    			"; plannedFinishDate:"+plannedFinishDate+
    			"; network:"+network+
    			"; aircraftType:"+aircraftType+
    			"; customer:"+customer+
    			"; actualStartDate:"+actualStartDate;
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

    @Column(name = "status_")
    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
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

    @Column(name = "aircrafttailnumber_")
    public String getAircraftTailNumber()
    {
        return aircraftTailNumber;
    }

    public void setAircraftTailNumber(String aircraftTailNumber)
    {
        this.aircraftTailNumber = aircraftTailNumber;
    }

    @Column(name = "aircraftdescription_")
    public String getAircraftDescription()
    {
        return aircraftDescription;
    }

    public void setAircraftDescription(String aircraftDescription)
    {
        this.aircraftDescription = aircraftDescription;
    }

    @Column(name = "plannedstartdate_")
    public Timestamp getPlannedStartDate()
    {
        return plannedStartDate;
    }

    public void setPlannedStartDate(Timestamp plannedStartDate)
    {
        this.plannedStartDate = plannedStartDate;
    }

    @Column(name = "plannedfinishdate_")
    public Timestamp getPlannedFinishDate()
    {
        return plannedFinishDate;
    }

    public void setPlannedFinishDate(Timestamp plannedFinishDate)
    {
        this.plannedFinishDate = plannedFinishDate;
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

    /**
     * @return the hangar
     */
    @Column(name = "hangar_")
    public String getHangar()
    {
        return hangar;
    }

    /**
     * @param hangar the hangar to set
     */
    public void setHangar(String hangar)
    {
        this.hangar = hangar;
    }

    /**
     * @return the aircraftModel
     */
    @Column(name = "aircraftmodel_")
    public String getAircraftModel()
    {
        return aircraftModel;
    }

    /**
     * @param aircraftModel the aircraftModel to set
     */
    public void setAircraftModel(String aircraftModel)
    {
        this.aircraftModel = aircraftModel;
    }

    /**
     * @return the style
     */
    @Column(name = "style_")
    public String getStyle()
    {
        return style;
    }

    /**
     * @param style the style to set
     */
    public void setStyle(String style)
    {
        this.style = style;
    }

    /**
     * @return the network
     */
    @Column(name = "network_")
    public String getNetwork()
    {
        return network;
    }

    /**
     * @param network the network to set
     */
    public void setNetwork(String network)
    {
        this.network = network;
    }

	/**
	 * @return the customer
	 */
    @Column(name = "customer_")
	public String getCustomer()
	{
		return customer;
	}

	/**
	 * @param customer the customer to set
	 */
	public void setCustomer(String customer)
	{
		this.customer = customer;
	}

	/**
	 * @return the actualStartDate
	 */
	@Column(name = "actualstartdate_")
	public Timestamp getActualStartDate()
	{
		return actualStartDate;
	}

	/**
	 * @param actualStartDate the actualStartDate to set
	 */
	public void setActualStartDate(Timestamp actualStartDate)
	{
		this.actualStartDate = actualStartDate;
	}
}
