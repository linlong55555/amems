package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_project")
public class ProjectPbs extends BaseEntity
{
    private String rid;
    private String pid;
    private String salesOrder;
    private String cid;
    private String status;
    
    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; rid:"+rid+
    			"; pid:"+pid+
    			"; salesOrder:"+salesOrder+
    			"; cid:"+cid+
    			"; status:"+status;
    }
    
    @Column(name = "salesorder_")
    public String getSalesOrder()
    {
        return salesOrder;
    }
    public void setSalesOrder(String salesOrder)
    {
        this.salesOrder = salesOrder;
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
    @Column(name = "pid_")
    public String getPid()
    {
        return pid;
    }
    public void setPid(String pid)
    {
        this.pid = pid;
    }
    @Column(name = "cid_")
    public String getCid()
    {
        return cid;
    }
    public void setCid(String cid)
    {
        this.cid = cid;
    }
}
