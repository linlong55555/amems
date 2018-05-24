package com.eray.pbs.po;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_dbtask")
public class DbTaskPbs extends BaseEntity
{
    private String cardNumber;
    private String aircraftType;
    private String stage;
    private BigDecimal planHours;
    private String lastModifier; //最后修改人
    private Timestamp lastModifyDate; //最后修改时间
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
  	public String toString() {
  		return "DbTaskPbs [cardNumber=" + cardNumber + ", aircraftType="
  				+ aircraftType + ", stage=" + stage + ", planHours="
  				+ planHours + ", lastModifier=" + lastModifier
  				+ ", lastModifyDate=" + lastModifyDate + "]";
  	}
   
    /**
     * @return the cardNumber
     */
    @Column(name = "cardnumber_")
    public String getCardNumber()
    {
        return cardNumber;
    }
  
	/**
     * @param cardNumber the cardNumber to set
     */
    public void setCardNumber(String cardNumber)
    {
        this.cardNumber = cardNumber;
    }
    /**
     * @return the aircraftType
     */
    @Column(name = "aircrafttype_")
    public String getAircraftType()
    {
        return aircraftType;
    }
    /**
     * @param aircraftType the aircraftType to set
     */
    public void setAircraftType(String aircraftType)
    {
        this.aircraftType = aircraftType;
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
     * @return the planHours
     */
    @Column(name = "planhours_")
    public BigDecimal getPlanHours()
    {
        return planHours;
    }
    /**
     * @param planHours the planHours to set
     */
    public void setPlanHours(BigDecimal planHours)
    {
        this.planHours = planHours;
    }
    
    @Column(name = "lastmodifier_")
	public String getLastModifier() {
		return lastModifier;
	}
	public void setLastModifier(String lastModifier) {
		this.lastModifier = lastModifier;
	}

	@Column(name = "lastmodifydate_")
	public Timestamp getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(Timestamp lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}
    
}
