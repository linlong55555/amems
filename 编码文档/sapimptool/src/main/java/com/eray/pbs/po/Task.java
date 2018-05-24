package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;
@Entity
@Table(name = "sap_task")
public class Task extends BaseEntity
{
    private String cardNumber;
    private String groupId;
    private String keyDate;
    private String aircraftType;
    private String operationNumber;
    private String line;
    private String imptFilename;
    

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; cardNumber:"+cardNumber+
    			"; groupId:"+groupId+
    			"; keyDate:"+keyDate+
    			"; aircraftType:"+aircraftType+
    			"; operationNumber:"+operationNumber+
    			"; line:"+line+
    			"; imptFilename:"+imptFilename;
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
     * @return the line
     */
    @Column(name = "line_")
    public String getLine()
    {
        return line;
    }
    /**
     * @param line the line to set
     */
    public void setLine(String line)
    {
        this.line = line;
    }
    /**
     * @return the imptFilename
     */
    @Column(name = "imptfilename_")
    public String getImptFilename()
    {
        return imptFilename;
    }
    /**
     * @param imptFilename the imptFilename to set
     */
    public void setImptFilename(String imptFilename)
    {
        this.imptFilename = imptFilename;
    }
    /**
     * @return the operationNumber
     */
    @Column(name = "operationnumber_")
    public String getOperationNumber()
    {
        return operationNumber;
    }
    /**
     * @param operationNumber the operationNumber to set
     */
    public void setOperationNumber(String operationNumber)
    {
        this.operationNumber = operationNumber;
    }
	/**
	 * @return the keyDate
	 */
    @Column(name = "keydate_")
	public String getKeyDate()
	{
		return keyDate;
	}
	/**
	 * @param keyDate the keyDate to set
	 */
	public void setKeyDate(String keyDate)
	{
		this.keyDate = keyDate;
	}
	/**
	 * @return the groupId
	 */
	@Column(name = "groupid_")
	public String getGroupId()
	{
		return groupId;
	}
	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

}
