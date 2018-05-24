package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

@Entity
@Table(name = "pbs_employee")
public class EmployeePbs extends BaseEntity
{
    private String eid;// 员工号码
    private String ename;// 员工名称
    private String group;// 员工组
    private String status;// 雇员状态
    private String did;// 部门号码
    private String email;
    

    /* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
    @Override
    public String toString()
    {
    	return
    	    	": id"+id+
    			"; eid:"+eid+
    			"; ename:"+ename+
    			"; group:"+group+
    			"; status:"+status+
    			"; did:"+did+
    			"; email:"+email;
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

    @Column(name = "eid_")
    public String getEid()
    {
        return eid;
    }

    public void setEid(String eid)
    {
        this.eid = eid;
    }

    @Column(name = "ename_")
    public String getEname()
    {
        return ename;
    }

    public void setEname(String ename)
    {
        this.ename = ename;
    }

    @Column(name = "group_")
    public String getGroup()
    {
        return group;
    }

    public void setGroup(String group)
    {
        this.group = group;
    }

    @Column(name = "did_")
    public String getDid()
    {
        return did;
    }

    public void setDid(String did)
    {
        this.did = did;
    }

    @Column(name = "email_")
    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}
