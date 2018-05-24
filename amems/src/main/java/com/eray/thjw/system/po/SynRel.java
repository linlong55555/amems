package com.eray.thjw.system.po;

import com.eray.thjw.aerialmaterial.po.Firm;
import com.eray.thjw.baseinfo.po.Customer;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.Department;

/**
 * 
 * @Description T_SYN_REL 系统同步关系
 * @CreateTime 2017年9月25日 上午11:24:05
 * @CreateBy 朱超
 */
@SuppressWarnings("serial")
public class SynRel extends BaseEntity {
    private String id;

    private String dprtcode;

    private Integer gxlx;

    private String dxid;

    private String gljgdm;
    
    private Department gljg;
    
    /**客户*/
    private Customer customer;
    
    /**外委供应商*/
    private Firm firm;
    
	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Department getJg() {
		return jg;
	}

	public void setJg(Department jg) {
		this.jg = jg;
	}
	
	public Department getGljg() {
		return gljg;
	}

	public void setGljg(Department gljg) {
		this.gljg = gljg;
	}

	private Department jg;
    
    
    
    
    /**
     * 对象：编号+名称
     */
    private String objText;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public Integer getGxlx() {
        return gxlx;
    }

    public void setGxlx(Integer gxlx) {
        this.gxlx = gxlx;
    }

    public String getDxid() {
        return dxid;
    }

    public void setDxid(String dxid) {
        this.dxid = dxid == null ? null : dxid.trim();
    }

    public String getGljgdm() {
        return gljgdm;
    }

    public void setGljgdm(String gljgdm) {
        this.gljgdm = gljgdm == null ? null : gljgdm.trim();
    }

    /**
     * 
     * @Description 对象：编号+名称<br>
     * 关系类型 1：外围供应商，对象：外围供应商编号+外围供应商名称<br>
     * 关系类型 2：客户，对象：客户编号+客户名称<br>
     * @CreateTime 2017年9月25日 下午1:50:21
     * @CreateBy 朱超
     * @return
     */
	public String getObjText() {
		return objText;
	}

	public void setObjText(String objText) {
		this.objText = objText;
	}

	public Firm getFirm() {
		return firm;
	}

	public void setFirm(Firm firm) {
		this.firm = firm;
	}
}