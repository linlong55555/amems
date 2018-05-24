package com.eray.thjw.material2.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;
/**
 * 
 * @Description b_h_024 需求
 * @CreateTime 2018年2月26日 上午10:01:05
 * @CreateBy 林龙
 */
public class Demand extends BizEntity{
    private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String bh;

    private Integer zt;

    private Integer jjcd;

    private Integer isYxfx;

    private Integer isFjhtc;

    private String xqlb;

    private Date jhsyrq;

    private String sjbjh;

    private String sjbjmc;

    private String fjzch;

    private String xlh;

    private Integer fxsj;

    private Integer fxxh;

    private String xqyy;

    private String gmjy;

    private String sqrid;

    private Date sqsj;

    private String sqbmid;

    private String jhbmid;

    private String jhrid;

    private Date jhsj;

    private String jhyj;
    
    private String lyid;
    
    private String lybh;
    
    private Integer lylx;
    
    private Integer bs145;
    
    /** 申请人 */
    private User sqr;
    
    /** 检验人 */
    private User jhr;
    
    /** 需求详情 */
    private List<DemandDetails> details;

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

    public String getBh() {
        return bh;
    }

    public void setBh(String bh) {
        this.bh = bh == null ? null : bh.trim();
    }

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getJjcd() {
		return jjcd;
	}

	public void setJjcd(Integer jjcd) {
		this.jjcd = jjcd;
	}

	public Integer getIsYxfx() {
		return isYxfx;
	}

	public void setIsYxfx(Integer isYxfx) {
		this.isYxfx = isYxfx;
	}

	public Integer getIsFjhtc() {
		return isFjhtc;
	}

	public void setIsFjhtc(Integer isFjhtc) {
		this.isFjhtc = isFjhtc;
	}

	public String getXqlb() {
		return xqlb;
	}

	public void setXqlb(String xqlb) {
		this.xqlb = xqlb;
	}

	public Date getJhsyrq() {
		return jhsyrq;
	}

	public void setJhsyrq(Date jhsyrq) {
		this.jhsyrq = jhsyrq;
	}

	public String getSjbjh() {
		return sjbjh;
	}

	public void setSjbjh(String sjbjh) {
		this.sjbjh = sjbjh;
	}

	public String getSjbjmc() {
		return sjbjmc;
	}

	public void setSjbjmc(String sjbjmc) {
		this.sjbjmc = sjbjmc;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getXlh() {
		return xlh;
	}

	public void setXlh(String xlh) {
		this.xlh = xlh;
	}

	public Integer getFxsj() {
		return fxsj;
	}

	public void setFxsj(Integer fxsj) {
		this.fxsj = fxsj;
	}

	public Integer getFxxh() {
		return fxxh;
	}

	public void setFxxh(Integer fxxh) {
		this.fxxh = fxxh;
	}

	public String getXqyy() {
		return xqyy;
	}

	public void setXqyy(String xqyy) {
		this.xqyy = xqyy;
	}

	public String getGmjy() {
		return gmjy;
	}

	public void setGmjy(String gmjy) {
		this.gmjy = gmjy;
	}

	public String getSqrid() {
		return sqrid;
	}

	public void setSqrid(String sqrid) {
		this.sqrid = sqrid;
	}

	public Date getSqsj() {
		return sqsj;
	}

	public void setSqsj(Date sqsj) {
		this.sqsj = sqsj;
	}

	public String getSqbmid() {
		return sqbmid;
	}

	public void setSqbmid(String sqbmid) {
		this.sqbmid = sqbmid;
	}

	public String getJhbmid() {
		return jhbmid;
	}

	public void setJhbmid(String jhbmid) {
		this.jhbmid = jhbmid;
	}

	public String getJhrid() {
		return jhrid;
	}

	public void setJhrid(String jhrid) {
		this.jhrid = jhrid;
	}

	public Date getJhsj() {
		return jhsj;
	}

	public void setJhsj(Date jhsj) {
		this.jhsj = jhsj;
	}

	public String getJhyj() {
		return jhyj;
	}

	public void setJhyj(String jhyj) {
		this.jhyj = jhyj;
	}

	public List<DemandDetails> getDetails() {
		return details;
	}

	public void setDetails(List<DemandDetails> details) {
		this.details = details;
	}

	public User getSqr() {
		return sqr;
	}

	public void setSqr(User sqr) {
		this.sqr = sqr;
	}

	public User getJhr() {
		return jhr;
	}

	public void setJhr(User jhr) {
		this.jhr = jhr;
	}

	public String getLyid() {
		return lyid;
	}

	public void setLyid(String lyid) {
		this.lyid = lyid;
	}

	public String getLybh() {
		return lybh;
	}

	public void setLybh(String lybh) {
		this.lybh = lybh;
	}

	public Integer getLylx() {
		return lylx;
	}

	public void setLylx(Integer lylx) {
		this.lylx = lylx;
	}

	public Integer getBs145() {
		return bs145;
	}

	public void setBs145(Integer bs145) {
		this.bs145 = bs145;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fjzch == null) ? 0 : fjzch.hashCode());
		result = prime * result + ((gmjy == null) ? 0 : gmjy.hashCode());
		result = prime * result + ((isFjhtc == null) ? 0 : isFjhtc.hashCode());
		result = prime * result + ((isYxfx == null) ? 0 : isYxfx.hashCode());
		result = prime * result + ((jjcd == null) ? 0 : jjcd.hashCode());
		result = prime * result + ((sjbjh == null) ? 0 : sjbjh.hashCode());
		result = prime * result + ((sjbjmc == null) ? 0 : sjbjmc.hashCode());
		result = prime * result + ((xqlb == null) ? 0 : xqlb.hashCode());
		result = prime * result + ((xqyy == null) ? 0 : xqyy.hashCode());
		result = prime * result + ((jhsyrq == null) ? 0 : jhsyrq.hashCode());
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
		Demand other = (Demand) obj;
		if (fjzch == null) {
			if (other.fjzch != null)
				return false;
		} else if (!fjzch.equals(other.fjzch))
			return false;
		if (fxsj == null) {
			if (other.fxsj != null)
				return false;
		} else if (!fxsj.equals(other.fxsj))
			return false;
		if (fxxh == null) {
			if (other.fxxh != null)
				return false;
		} else if (!fxxh.equals(other.fxxh))
			return false;
		if (gmjy == null) {
			if (other.gmjy != null)
				return false;
		} else if (!gmjy.equals(other.gmjy))
			return false;
		if (isFjhtc == null) {
			if (other.isFjhtc != null)
				return false;
		} else if (!isFjhtc.equals(other.isFjhtc))
			return false;
		if (isYxfx == null) {
			if (other.isYxfx != null)
				return false;
		} else if (!isYxfx.equals(other.isYxfx))
			return false;
		if (jjcd == null) {
			if (other.jjcd != null)
				return false;
		} else if (!jjcd.equals(other.jjcd))
			return false;
		if (sjbjh == null) {
			if (other.sjbjh != null)
				return false;
		} else if (!sjbjh.equals(other.sjbjh))
			return false;
		if (sjbjmc == null) {
			if (other.sjbjmc != null)
				return false;
		} else if (!sjbjmc.equals(other.sjbjmc))
			return false;
		if (xlh == null) {
			if (other.xlh != null)
				return false;
		} else if (!xlh.equals(other.xlh))
			return false;
		if (xqlb == null) {
			if (other.xqlb != null)
				return false;
		} else if (!xqlb.equals(other.xqlb))
			return false;
		if (xqyy == null) {
			if (other.xqyy != null)
				return false;
		} else if (!xqyy.equals(other.xqyy))
			return false;
		if (jhsyrq == null) {
			if (other.jhsyrq != null)
				return false;
		} else if (!jhsyrq.equals(other.jhsyrq))
			return false;
		return true;
	}
 
}