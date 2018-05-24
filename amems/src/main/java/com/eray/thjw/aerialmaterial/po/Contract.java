package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * 
 * b_h_004 合同表
 * @author xu.yong
 *
 */
public class Contract extends BizEntity{
    private String id;

    private String dprtcode;

    private Integer htlx;

    private String htlsh;

    private String hth;

    private Integer jjcd;

    private String gysid;

    private String gysbm;

    private String gysmc;

    private Date htqdrq;
    
    private BigDecimal fjfy;

    private String bz;

    private Integer zt;

    private Integer dhzt;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;
    
    private String zdjsrid;
    
    private Date zdjsrq;
    
    private String zdjsyy;
    
    private User zdjsr;
    
    private BigDecimal htfy;//虚拟字段 合同费用
    
    private List<String> contractDetailIdList;
    
    private List<ContractDetail> contractDetailList;
    
    private Message message;

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

    public Integer getHtlx() {
        return htlx;
    }

    public void setHtlx(Integer htlx) {
        this.htlx = htlx;
    }

    public String getHtlsh() {
        return htlsh;
    }

    public void setHtlsh(String htlsh) {
        this.htlsh = htlsh == null ? null : htlsh.trim();
    }

    public String getHth() {
        return hth;
    }

    public void setHth(String hth) {
        this.hth = hth == null ? null : hth.trim();
    }

    public Integer getJjcd() {
        return jjcd;
    }

    public void setJjcd(Integer jjcd) {
        this.jjcd = jjcd;
    }

    public String getGysid() {
        return gysid;
    }

    public void setGysid(String gysid) {
        this.gysid = gysid == null ? null : gysid.trim();
    }

    public String getGysbm() {
        return gysbm;
    }

    public void setGysbm(String gysbm) {
        this.gysbm = gysbm == null ? null : gysbm.trim();
    }

    public String getGysmc() {
        return gysmc;
    }

    public void setGysmc(String gysmc) {
        this.gysmc = gysmc == null ? null : gysmc.trim();
    }

    public Date getHtqdrq() {
        return htqdrq;
    }

    public void setHtqdrq(Date htqdrq) {
        this.htqdrq = htqdrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getDhzt() {
        return dhzt;
    }

    public void setDhzt(Integer dhzt) {
        this.dhzt = dhzt;
    }

    public String getZdbmid() {
        return zdbmid;
    }

    public void setZdbmid(String zdbmid) {
        this.zdbmid = zdbmid == null ? null : zdbmid.trim();
    }

    public String getZdrid() {
        return zdrid;
    }

    public void setZdrid(String zdrid) {
        this.zdrid = zdrid == null ? null : zdrid.trim();
    }

    public Date getZdsj() {
        return zdsj;
    }

    public void setZdsj(Date zdsj) {
        this.zdsj = zdsj;
    }

	public String getZdjsrid() {
		return zdjsrid;
	}

	public void setZdjsrid(String zdjsrid) {
		this.zdjsrid = zdjsrid;
	}

	public Date getZdjsrq() {
		return zdjsrq;
	}

	public void setZdjsrq(Date zdjsrq) {
		this.zdjsrq = zdjsrq;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}

	public List<ContractDetail> getContractDetailList() {
		return contractDetailList;
	}

	public void setContractDetailList(List<ContractDetail> contractDetailList) {
		this.contractDetailList = contractDetailList;
	}

	public User getZdjsr() {
		return zdjsr;
	}

	public void setZdjsr(User zdjsr) {
		this.zdjsr = zdjsr;
	}

	public List<String> getContractDetailIdList() {
		return contractDetailIdList;
	}

	public void setContractDetailIdList(List<String> contractDetailIdList) {
		this.contractDetailIdList = contractDetailIdList;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public BigDecimal getFjfy() {
		return fjfy;
	}

	public void setFjfy(BigDecimal fjfy) {
		this.fjfy = fjfy;
	}

	public BigDecimal getHtfy() {
		return htfy;
	}

	public void setHtfy(BigDecimal htfy) {
		this.htfy = htfy;
	}
    
}