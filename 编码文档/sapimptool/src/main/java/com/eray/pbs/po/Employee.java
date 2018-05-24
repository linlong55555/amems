package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "sap_employee")
public class Employee extends BaseEntity
{
    private String employeeId;//员工号码
    private String qualificationId;
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
    			"; employeeId:"+employeeId+
    			"; qualificationId:"+qualificationId+
    			"; line:"+line+
    			"; imptFilename:"+imptFilename;
    }
    /**
     * @return the employeeId
     */
    @Column(name = "employeeid_")
    public String getEmployeeId()
    {
        return employeeId;
    }
    /**
     * @param employeeId the employeeId to set
     */
    public void setEmployeeId(String employeeId)
    {
        this.employeeId = employeeId;
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
     * @return the qualificationId
     */
    @Column(name = "qualificationid_")
    public String getQualificationId()
    {
        return qualificationId;
    }
    /**
     * @param qualificationId the qualificationId to set
     */
    public void setQualificationId(String qualificationId)
    {
        this.qualificationId = qualificationId;
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
