package com.eray.thjw.aerialmaterial.po;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;

/**
 * B_H_02101 销毁单明细
 * @author xu.yong
 *
 */
public class DestructionDetail extends BizEntity{
	
	private String id;

    private String mainid;

    private String kcllid;
    
    private Destruction destruction;
    
    private MaterialHistory materialHistory;

    private HCMainData hCMainData;
    
    private Department jg_dprt;
    
    
    //导出虚拟字段
    private Integer zt;
    
    private Integer hclx;
    
    private String xhrqBegin;
    
    private String xhrqEnd;
    
    
    
    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getHclx() {
		return hclx;
	}

	public void setHclx(Integer hclx) {
		this.hclx = hclx;
	}

	public String getXhrqBegin() {
		return xhrqBegin;
	}

	public void setXhrqBegin(String xhrqBegin) {
		this.xhrqBegin = xhrqBegin;
	}

	public String getXhrqEnd() {
		return xhrqEnd;
	}

	public void setXhrqEnd(String xhrqEnd) {
		this.xhrqEnd = xhrqEnd;
	}

	public Department getJg_dprt() {
		return jg_dprt;
	}

	public void setJg_dprt(Department jg_dprt) {
		this.jg_dprt = jg_dprt;
	}

	public Destruction getDestruction() {
		return destruction;
	}

	public void setDestruction(Destruction destruction) {
		this.destruction = destruction;
	}

	public MaterialHistory getMaterialHistory() {
		return materialHistory;
	}

	public void setMaterialHistory(MaterialHistory materialHistory) {
		this.materialHistory = materialHistory;
	}

	public HCMainData gethCMainData() {
		return hCMainData;
	}

	public void sethCMainData(HCMainData hCMainData) {
		this.hCMainData = hCMainData;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMainid() {
        return mainid;
    }

    public void setMainid(String mainid) {
        this.mainid = mainid == null ? null : mainid.trim();
    }

    public String getKcllid() {
        return kcllid;
    }

    public void setKcllid(String kcllid) {
        this.kcllid = kcllid == null ? null : kcllid.trim();
    }
}