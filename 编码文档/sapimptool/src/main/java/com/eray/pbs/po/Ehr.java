package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "sap_ehr")
public class Ehr extends BaseEntity
{
	private String workOrder;
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
