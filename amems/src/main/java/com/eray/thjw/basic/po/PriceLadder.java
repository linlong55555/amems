package com.eray.thjw.basic.po;

import java.math.BigDecimal;

import com.eray.thjw.po.BizEntity;
/**
 * 
 * @Description d_02701 工时-阶梯单价
 * @CreateTime 2018年4月2日 上午10:07:59
 * @CreateBy 岳彬彬
 */
public class PriceLadder {
    private String id;

    private String mainid;

    private BigDecimal qsgs;

    private BigDecimal jzgs;

    private BigDecimal gsdj;

    private BigDecimal qdje;

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

    public BigDecimal getQsgs() {
        return qsgs;
    }

    public void setQsgs(BigDecimal qsgs) {
        this.qsgs = qsgs;
    }

    public BigDecimal getJzgs() {
        return jzgs;
    }

    public void setJzgs(BigDecimal jzgs) {
        this.jzgs = jzgs;
    }

    public BigDecimal getGsdj() {
        return gsdj;
    }

    public void setGsdj(BigDecimal gsdj) {
        this.gsdj = gsdj;
    }

    public BigDecimal getQdje() {
        return qdje;
    }

    public void setQdje(BigDecimal qdje) {
        this.qdje = qdje;
    }
}