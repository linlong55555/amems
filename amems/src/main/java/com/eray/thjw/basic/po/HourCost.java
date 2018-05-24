package com.eray.thjw.basic.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
/**
 * 
 * @Description d_027 工时单价
 * @CreateTime 2018年4月2日 上午10:05:16
 * @CreateBy 岳彬彬
 */
public class HourCost extends BizEntity{
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String lx;

    private BigDecimal dj;

    private String biz;
    
    private List<PriceLadder> ladderList;

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

    public String getWhbmid() {
        return whbmid;
    }

    public void setWhbmid(String whbmid) {
        this.whbmid = whbmid == null ? null : whbmid.trim();
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

    public String getLx() {
        return lx;
    }

    public void setLx(String lx) {
        this.lx = lx == null ? null : lx.trim();
    }

    public BigDecimal getDj() {
        return dj;
    }

    public void setDj(BigDecimal dj) {
        this.dj = dj;
    }

    public String getBiz() {
        return biz;
    }

    public void setBiz(String biz) {
        this.biz = biz == null ? null : biz.trim();
    }

	public List<PriceLadder> getLadderList() {
		return ladderList;
	}

	public void setLadderList(List<PriceLadder> ladderList) {
		this.ladderList = ladderList;
	}
    
}