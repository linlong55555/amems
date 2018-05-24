package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "sap_spenthours")
public class SpentHours extends BaseEntity
{
    private String workOrder;
    private String operation;
    private String workCenter;
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
    			"; workOrder:"+workOrder+
    			"; operation:"+operation+
    			"; workCenter:"+workCenter+
    			"; line:"+line+
    			"; imptFilename:"+imptFilename;
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
	 * @return the operation
	 */
    @Column(name = "operation_")
	public String getOperation()
	{
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	/**
	 * @return the workCenter
	 */
	@Column(name = "workcenter_")
	public String getWorkCenter()
	{
		return workCenter;
	}

	/**
	 * @param workCenter the workCenter to set
	 */
	public void setWorkCenter(String workCenter)
	{
		this.workCenter = workCenter;
	}

}
