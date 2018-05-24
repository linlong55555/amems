package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "sap_materialshortage")
public class MaterialShortage extends BaseEntity
{
    private String workOrder;
    private String materialNumber;
    private String mrCreationDate;
    private String line;
    private String imptFilename;
    private Long pbsMaterialShortageId; //pbs_materialshortage 表ID
    

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; workOrder:"+workOrder+
    			"; materialNumber:"+materialNumber+
    			"; mrCreationDate:"+mrCreationDate+
    			"; line:"+line+
    			"; imptFilename:"+imptFilename;
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
     * @return the materialNumber
     */
    @Column(name = "materialnumber_")
    public String getMaterialNumber()
    {
        return materialNumber;
    }
    /**
     * @param materialNumber the materialNumber to set
     */
    public void setMaterialNumber(String materialNumber)
    {
        this.materialNumber = materialNumber;
    }
    /**
     * @return the mrCreationDate
     */
    @Column(name = "mrcreationdate_")
    public String getMrCreationDate()
    {
        return mrCreationDate;
    }
    /**
     * @param mrCreationDate the mrCreationDate to set
     */
    public void setMrCreationDate(String mrCreationDate)
    {
        this.mrCreationDate = mrCreationDate;
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
	public Long getPbsMaterialShortageId() {
		return pbsMaterialShortageId;
	}
	public void setPbsMaterialShortageId(Long pbsMaterialShortageId) {
		this.pbsMaterialShortageId = pbsMaterialShortageId;
	}
}
