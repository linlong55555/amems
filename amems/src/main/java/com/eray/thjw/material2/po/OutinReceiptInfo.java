package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.eray.thjw.po.BaseEntity;

/**
 * @Description b_h2_02001 收货单-信息
 * @CreateTime 2018年3月12日 下午2:49:17
 * @CreateBy 韩武
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutinReceiptInfo extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String mainid;

    private String bjid;

    private String bjh;

    private String pch;

    private String sn;

    private BigDecimal sl;

    private String dw;

    private Integer isZj;

    private String cqid;

    private String lsckid;

    private String lskwid;

    private String lscfwz;

    private String wckcllid;
    
    private String rowid;
    
    /** 航材类型 */
    private Integer hclx;
    
    /** 部件名称 */
    private String bjmc;
    
    /** 产权编号 */
    private String cqbh;
    
    /** 来源明细id */
    private String lymxid;
    
    /** 来源明细类型 */
    private Integer lymxlx;
    
    /** 检验单id */
    private String jydid;
    
    /** 收货单-上架信息 */
    private List<OutinReceiptShelves> shelves;

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

    public String getPch() {
        return pch;
    }

    public void setPch(String pch) {
        this.pch = pch == null ? null : pch.trim();
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn == null ? null : sn.trim();
    }

    public BigDecimal getSl() {
        return sl;
    }

    public void setSl(BigDecimal sl) {
        this.sl = sl;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw == null ? null : dw.trim();
    }

    public Integer getIsZj() {
        return isZj;
    }

    public void setIsZj(Integer isZj) {
        this.isZj = isZj;
    }

    public String getCqid() {
        return cqid;
    }

    public void setCqid(String cqid) {
        this.cqid = cqid == null ? null : cqid.trim();
    }

    public String getLsckid() {
        return lsckid;
    }

    public void setLsckid(String lsckid) {
        this.lsckid = lsckid == null ? null : lsckid.trim();
    }

    public String getLskwid() {
        return lskwid;
    }

    public void setLskwid(String lskwid) {
        this.lskwid = lskwid == null ? null : lskwid.trim();
    }

    public String getLscfwz() {
        return lscfwz;
    }

    public void setLscfwz(String lscfwz) {
        this.lscfwz = lscfwz == null ? null : lscfwz.trim();
    }

    public String getWckcllid() {
        return wckcllid;
    }

    public void setWckcllid(String wckcllid) {
        this.wckcllid = wckcllid == null ? null : wckcllid.trim();
    }

	public List<OutinReceiptShelves> getShelves() {
		return shelves;
	}

	public void setShelves(List<OutinReceiptShelves> shelves) {
		this.shelves = shelves;
	}

	public String getLymxid() {
		return lymxid;
	}

	public void setLymxid(String lymxid) {
		this.lymxid = lymxid;
	}

	public Integer getLymxlx() {
		return lymxlx;
	}

	public void setLymxlx(Integer lymxlx) {
		this.lymxlx = lymxlx;
	}

	public Integer getHclx() {
		return hclx;
	}

	public void setHclx(Integer hclx) {
		this.hclx = hclx;
	}

	public String getBjmc() {
		return bjmc;
	}

	public void setBjmc(String bjmc) {
		this.bjmc = bjmc;
	}

	public String getCqbh() {
		return cqbh;
	}

	public void setCqbh(String cqbh) {
		this.cqbh = cqbh;
	}

	public String getJydid() {
		return jydid;
	}

	public void setJydid(String jydid) {
		this.jydid = jydid;
	}

	public String getRowid() {
		return rowid;
	}

	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
}