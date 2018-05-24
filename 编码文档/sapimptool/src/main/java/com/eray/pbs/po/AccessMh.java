package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "sap_accessmh")
public class AccessMh extends BaseEntity
{
    private String accessNo;
    private String aircraftType;
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
	    		"; accessNo:"+accessNo+
	    		"; aircraftType:"+aircraftType+
	    		"; line:"+line+
	    		"; imptFilename:"+imptFilename;
    }
    
    /**
     * @return the accessNo
     */
    @Column(name = "accessno_")
    public String getAccessNo()
    {
        return accessNo;
    }
    /**
     * @param accessNo the accessNo to set
     */
    public void setAccessNo(String accessNo)
    {
        this.accessNo = accessNo;
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

}
