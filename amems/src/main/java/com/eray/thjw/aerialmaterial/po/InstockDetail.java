package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BaseEntity;

/**
 * b_h_00801 入库单-航材表
 * @author xu.yong
 *
 */
public class InstockDetail extends BaseEntity {
    private String id;

    private String mainid;

    private String bjid;

    private String bjh;

    private BigDecimal sl;

    private String htid;

    private String htmxid;

    private String wpqdid;

    private String jdsqdid;

    private String jdsqmxid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    /** 虚拟字段 start */
    private Instock instock;//入库单
    
    private HCMainData hcMainData;//D_008航材主数据
    
    private ContractDetail contractDetail;//合同航材
    
    private Expatriate expatriate; //对应的外派清单
    
    private List<InstockDetailStock> detailStockList;//航材入库库存信息
    
    /** 虚拟字段 end */

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

    public BigDecimal getSl() {
    	if(sl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(sl));
    	}
        return sl;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public String getHtid() {
        return htid;
    }

    public void setHtid(String htid) {
        this.htid = htid == null ? null : htid.trim();
    }

    public String getHtmxid() {
        return htmxid;
    }

    public void setHtmxid(String htmxid) {
        this.htmxid = htmxid == null ? null : htmxid.trim();
    }

    public String getWpqdid() {
        return wpqdid;
    }

    public void setWpqdid(String wpqdid) {
        this.wpqdid = wpqdid == null ? null : wpqdid.trim();
    }

    public String getJdsqdid() {
        return jdsqdid;
    }

    public void setJdsqdid(String jdsqdid) {
        this.jdsqdid = jdsqdid == null ? null : jdsqdid.trim();
    }

    public String getJdsqmxid() {
        return jdsqmxid;
    }

    public void setJdsqmxid(String jdsqmxid) {
        this.jdsqmxid = jdsqmxid == null ? null : jdsqmxid.trim();
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

	public Instock getInstock() {
		return instock;
	}

	public void setInstock(Instock instock) {
		this.instock = instock;
	}

	public ContractDetail getContractDetail() {
		return contractDetail;
	}

	public void setContractDetail(ContractDetail contractDetail) {
		this.contractDetail = contractDetail;
	}

	public HCMainData getHcMainData() {
		return hcMainData;
	}

	public void setHcMainData(HCMainData hcMainData) {
		this.hcMainData = hcMainData;
	}

	public List<InstockDetailStock> getDetailStockList() {
		return detailStockList;
	}

	public void setDetailStockList(List<InstockDetailStock> detailStockList) {
		this.detailStockList = detailStockList;
	}

	public Expatriate getExpatriate() {
		return expatriate;
	}

	public void setExpatriate(Expatriate expatriate) {
		this.expatriate = expatriate;
	}
	
}