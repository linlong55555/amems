package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;

import com.eray.thjw.po.BizEntity;

/**
 * @Description b_g2_01001 EO-适用性
 * @CreateTime 2017-8-22 下午9:36:43
 * @CreateBy 雷伟
 */
public class EOApplicability extends BizEntity {
    private String id;

    private String mainid;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private BigDecimal xc;

    private String bh;

    private String xlh;

    private Integer qrzt;

    private String szwz;

    private BigDecimal sgbs;
    
    private BigDecimal gbzt;
    
    private String gbrid;
    
    private Date gbrq;
    
    private String gbyy;

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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
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

    public BigDecimal getXc() {
        return xc;
    }

    public void setXc(BigDecimal xc) {
        this.xc = xc;
    }

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh == null ? null : bh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public Integer getQrzt() {
        return qrzt;
    }

    public void setQrzt(Integer qrzt) {
        this.qrzt = qrzt;
    }

    public String getSzwz() {
        return szwz;
    }

    public void setSzwz(String szwz) {
        this.szwz = szwz == null ? null : szwz.trim();
    }

    public BigDecimal getSgbs() {
        return sgbs;
    }

    public void setSgbs(BigDecimal sgbs) {
        this.sgbs = sgbs;
    }

	public BigDecimal getGbzt() {
		return gbzt;
	}

	public void setGbzt(BigDecimal gbzt) {
		this.gbzt = gbzt;
	}

	public String getGbrid() {
		return gbrid;
	}

	public void setGbrid(String gbrid) {
		this.gbrid = gbrid;
	}

	public Date getGbrq() {
		return gbrq;
	}

	public void setGbrq(Date gbrq) {
		this.gbrq = gbrq;
	}

	public String getGbyy() {
		return gbyy;
	}

	public void setGbyy(String gbyy) {
		this.gbyy = gbyy;
	}

	@Override
	public String toString() {
		return "EOApplicability [id=" + id + ", mainid=" + mainid + ", zt="
				+ zt + ", whbmid=" + whbmid + ", whrid=" + whrid + ", whsj="
				+ whsj + ", xc=" + xc + ", bh=" + bh + ", xlh=" + xlh
				+ ", qrzt=" + qrzt + ", szwz=" + szwz + ", sgbs=" + sgbs
				+ ", gbzt=" + gbzt + ", gbrid=" + gbrid + ", gbrq=" + gbrq
				+ ", gbyy=" + gbyy + "]";
	}
}