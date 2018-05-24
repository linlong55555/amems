package com.eray.thjw.material2.po;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.eray.thjw.po.BizEntity;
/**
 * 
 * @Description b_h_02401 需求明细
 * @CreateTime 2018年2月26日 上午10:01:56
 * @CreateBy 林龙
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DemandDetails extends BizEntity{
    private String id;

    private String mainid;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer wllb;

    private String bjid;

    private String bjh;

    private String bjmc;

    private String xingh;

    private String gg;

    private String jhly;

    private String zjh;
    
    private String zjhms;

    private String bzjh;

    private BigDecimal xqsl;

    private String dw;

    private String xlh;

    private String thj;

    private Integer xqbs;

    private Integer tbbs;

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

    public String getBjmc() {
        return bjmc;
    }

    public void setBjmc(String bjmc) {
        this.bjmc = bjmc == null ? null : bjmc.trim();
    }

    public String getXingh() {
        return xingh;
    }

    public void setXingh(String xingh) {
        this.xingh = xingh == null ? null : xingh.trim();
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg == null ? null : gg.trim();
    }

    public String getJhly() {
        return jhly;
    }

    public void setJhly(String jhly) {
        this.jhly = jhly == null ? null : jhly.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public String getBzjh() {
        return bzjh;
    }

    public void setBzjh(String bzjh) {
        this.bzjh = bzjh == null ? null : bzjh.trim();
    }

    public BigDecimal getXqsl() {
        return xqsl;
    }

    public void setXqsl(BigDecimal xqsl) {
        this.xqsl = xqsl;
    }

    public String getDw() {
        return dw;
    }

    public void setDw(String dw) {
        this.dw = dw == null ? null : dw.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public String getThj() {
        return thj;
    }

    public void setThj(String thj) {
        this.thj = thj == null ? null : thj.trim();
    }

	public Integer getWllb() {
		return wllb;
	}

	public void setWllb(Integer wllb) {
		this.wllb = wllb;
	}

	public Integer getXqbs() {
		return xqbs;
	}

	public void setXqbs(Integer xqbs) {
		this.xqbs = xqbs;
	}

	public Integer getTbbs() {
		return tbbs;
	}

	public void setTbbs(Integer tbbs) {
		this.tbbs = tbbs;
	}

	public String getZjhms() {
		return zjhms;
	}

	public void setZjhms(String zjhms) {
		this.zjhms = zjhms;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bjh == null) ? 0 : bjh.hashCode());
		result = prime * result + ((bjid == null) ? 0 : bjid.hashCode());
		result = prime * result + ((bjmc == null) ? 0 : bjmc.hashCode());
		result = prime * result + ((bzjh == null) ? 0 : bzjh.hashCode());
		result = prime * result + ((dw == null) ? 0 : dw.hashCode());
		result = prime * result + ((gg == null) ? 0 : gg.hashCode());
		result = prime * result + ((jhly == null) ? 0 : jhly.hashCode());
		result = prime * result + ((thj == null) ? 0 : thj.hashCode());
		result = prime * result + ((wllb == null) ? 0 : wllb.hashCode());
		result = prime * result + ((xingh == null) ? 0 : xingh.hashCode());
		result = prime * result + ((xlh == null) ? 0 : xlh.hashCode());
		result = prime * result + ((xqsl == null) ? 0 : xqsl.hashCode());
		result = prime * result + ((zjh == null) ? 0 : zjh.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DemandDetails other = (DemandDetails) obj;
		if (bjh == null) {
			if (other.bjh != null)
				return false;
		} else if (!bjh.equals(other.bjh))
			return false;
		if (bjid == null) {
			if (other.bjid != null)
				return false;
		} else if (!bjid.equals(other.bjid))
			return false;
		if (bjmc == null) {
			if (other.bjmc != null)
				return false;
		} else if (!bjmc.equals(other.bjmc))
			return false;
		if (bzjh == null) {
			if (other.bzjh != null)
				return false;
		} else if (!bzjh.equals(other.bzjh))
			return false;
		if (dw == null) {
			if (other.dw != null)
				return false;
		} else if (!dw.equals(other.dw))
			return false;
		if (gg == null) {
			if (other.gg != null)
				return false;
		} else if (!gg.equals(other.gg))
			return false;
		if (jhly == null) {
			if (other.jhly != null)
				return false;
		} else if (!jhly.equals(other.jhly))
			return false;
		if (thj == null) {
			if (other.thj != null)
				return false;
		} else if (!thj.equals(other.thj))
			return false;
		if (wllb == null) {
			if (other.wllb != null)
				return false;
		} else if (!wllb.equals(other.wllb))
			return false;
		if (xingh == null) {
			if (other.xingh != null)
				return false;
		} else if (!xingh.equals(other.xingh))
			return false;
		if (xlh == null) {
			if (other.xlh != null)
				return false;
		} else if (!xlh.equals(other.xlh))
			return false;
		if (xqsl == null) {
			if (other.xqsl != null)
				return false;
		} else if (!xqsl.equals(other.xqsl))
			return false;
		if (zjh == null) {
			if (other.zjh != null)
				return false;
		} else if (!zjh.equals(other.zjh))
			return false;
		return true;
	}

   
}