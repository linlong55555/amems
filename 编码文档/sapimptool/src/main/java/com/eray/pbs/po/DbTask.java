package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "sap_dbtask")
public class DbTask extends BaseEntity
{
    private String cardNumber;
    private String aircraftType;
    private String line;
    private String imptFilename;
    private String errorMsg; //错误信息，透明字段，只显示，不与数据库表结构做映射
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; cardNumber:"+cardNumber+
    			"; aircraftType:"+aircraftType+
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
    @Transient
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
    
}
