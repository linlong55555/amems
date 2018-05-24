package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;

/**
 * 
 * b_h_00701 领用申请单明细表
 * @author xu.yong
 *
 */
public class MaterialRequisitionDetail extends BizEntity {
    private String id;

    private String mainid;

    private String kcid;

    private String bjid;

    private String bjh;
    
    private Integer cklb;

    private BigDecimal sqlysl;

    private BigDecimal ylysl;

    private BigDecimal tksl;

    private Integer zt;

    private String whdwid;

    private String ckmc;
    
    public Integer getCklb() {
		return cklb;
	}

	public void setCklb(Integer cklb) {
		this.cklb = cklb;
	}

	public String getCkmc() {
		return ckmc;
	}

	public void setCkmc(String ckmc) {
		this.ckmc = ckmc;
	}

	private String whrid;

    private Date whsj;
    
    /** 虚拟字段属性 start */
    
    private Stock stock = new Stock();//库存
    
    private HCMainData hCMainData = new HCMainData();//航材主数据
    
    private List<MaterialRequisitionDetailWorkOrder> workOrderList;//相关工单
    
    private String zwms;//中文名称
    
    private String ywms;//英文名称
    
    private String cjjh;//厂家件号
    
    private String hclx;//航材类型
    
    private String sn;//序列号
    
    private String pch;//批次号
    
    private String shzh;//适航证号
    
    private String kcsl;//库存数量
    
    private String kwh;//库位号
    
    private String ckh;//库位号
    
    private String cksl;//出库数量
    
    private String grn;//grn
    
    /** 虚拟字段属性 end */
    
    
    public String getId() {
        return id;
    }

    public String getGrn() {
		return grn;
	}

	public void setGrn(String grn) {
		this.grn = grn;
	}

	public String getCkh() {
		return ckh;
	}

	public void setCkh(String ckh) {
		this.ckh = ckh;
	}

	public String getCksl() {
		return cksl;
	}

	public void setCksl(String cksl) {
		this.cksl = cksl;
	}

	public String getZwms() {
		return zwms;
	}

	public void setZwms(String zwms) {
		this.zwms = zwms;
	}

	public String getYwms() {
		return ywms;
	}

	public void setYwms(String ywms) {
		this.ywms = ywms;
	}

	public String getCjjh() {
		return cjjh;
	}

	public void setCjjh(String cjjh) {
		this.cjjh = cjjh;
	}

	public String getHclx() {
		return hclx;
	}

	public void setHclx(String hclx) {
		this.hclx = hclx;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getPch() {
		return pch;
	}

	public void setPch(String pch) {
		this.pch = pch;
	}

	public String getShzh() {
		return shzh;
	}

	public void setShzh(String shzh) {
		this.shzh = shzh;
	}

	public String getKcsl() {
		return kcsl;
	}

	public void setKcsl(String kcsl) {
		this.kcsl = kcsl;
	}

	public String getKwh() {
		return kwh;
	}

	public void setKwh(String kwh) {
		this.kwh = kwh;
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

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }

    public String getBjid() {
        return bjid;
    }

    public void setBjid(String bjid) {
        this.bjid = bjid == null ? null : bjid.trim();
    }

    public String getBjh() {
        return bjh;
    }

    public void setBjh(String bjh) {
        this.bjh = bjh == null ? null : bjh.trim();
    }

    public BigDecimal getSqlysl() {
        return sqlysl;
    }

    public void setSqlysl(BigDecimal sqlysl) {
        this.sqlysl = sqlysl;
    }

    public BigDecimal getYlysl() {
        return ylysl;
    }

    public void setYlysl(BigDecimal ylysl) {
        this.ylysl = ylysl;
    }

    public BigDecimal getTksl() {
        return tksl;
    }

    public void setTksl(BigDecimal tksl) {
        this.tksl = tksl;
    }

    public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public String getWhdwid() {
        return whdwid;
    }

    public void setWhdwid(String whdwid) {
        this.whdwid = whdwid == null ? null : whdwid.trim();
    }

    public String getWhrid() {
        return whrid;
    }

    public void setWhrid(String whrid) {
        this.whrid = whrid == null ? null : whrid.trim();
    }

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }

	public Stock getStock() {
		return stock;
	}

	public void setStock(Stock stock) {
		this.stock = stock;
	}

	public HCMainData gethCMainData() {
		return hCMainData;
	}

	public void sethCMainData(HCMainData hCMainData) {
		this.hCMainData = hCMainData;
	}

	public List<MaterialRequisitionDetailWorkOrder> getWorkOrderList() {
		return workOrderList;
	}

	public void setWorkOrderList(List<MaterialRequisitionDetailWorkOrder> workOrderList) {
		this.workOrderList = workOrderList;
	}
	
}