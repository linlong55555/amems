package com.eray.thjw.aerialmaterial.po;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BaseEntity;

/**
 * b_h_006 检验单
 * @author xu.yong
 *
 */
@SuppressWarnings("serial")
public class QualityCheck extends BaseEntity{
    private String id;

    private String dprtcode;

    private String jydh;

    private String htid;

    private String hth;

    private String rkdid;

    private String rkdh;

    private String tddid;

    private String tddh;

    private String kcid;

    private Integer cklb;

    private String ckid;

    private String ckh;

    private String ckmc;

    private String kwid;

    private String kwh;

    private String bjid;

    private String bjh;

    private String pch;

    private String sn;

    private String zwms;

    private String ywms;

    private String jldw;

    private BigDecimal kcsl;
    
    private String hcly;

    private String cfyq;

    private String xkzh;

    private String shzh;

    private String shzwz;

    private String tsn;

    private String tso;

    private Date jyrq;
    
    private Integer jyjg;

    private String jgsm;

    private String bz;

    private Date scrq;

    private String xh;

    private String gg;

    private String zzcs;

    private Integer zt;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;
    
    private String jyr;

    private String jyrid;
    
    //补充字段
	private String username;
	
	private String realname;
	
	private String dprtname;
	
	private String displayname;

	private String cjjh;
	
	private Integer hclx;
	
	private Integer gljb;
	
	private Date hjsm;
	
	private List<String> del_ids;
	
	
	
   public List<String> getDel_ids() {
		return del_ids;
	}

	public void setDel_ids(List<String> del_ids) {
		this.del_ids = del_ids;
	}

public String getDisplayname() {
		return displayname;
	}

	public void setDisplayname(String displayname) {
		this.displayname = displayname;
	}

private List<Attachment>	 attachmentList;
   
   public QualityCheck(){}
   
   public QualityCheck(OutFieldStock outFieldStock){
   	this.dprtcode = outFieldStock.getDprtcode();
   	this.cklb = outFieldStock.getCklb();
   	this.ckid = outFieldStock.getCkid();
   	this.ckh = outFieldStock.getCkh();
   	this.ckmc = outFieldStock.getCkmc();
   	this.kwh = outFieldStock.getKwh();
   	this.bjid = outFieldStock.getBjid();
   	this.bjh = outFieldStock.getBjh();
   	this.pch = outFieldStock.getPch();
   	this.sn = outFieldStock.getSn();
   	this.zwms = outFieldStock.getZwms();
   	this.ywms = outFieldStock.getYwms();
   	this.jldw = outFieldStock.getJldw();
   	this.tddid = outFieldStock.getTddid();
   	this.tddh = outFieldStock.getTddh();
   	this.shzh = outFieldStock.getShzh();
   	this.shzwz = outFieldStock.getShzwz();
   	this.zt = outFieldStock.getZt();
   	this.bz = outFieldStock.getBz();
   	this.hjsm = outFieldStock.getHjsm();
   	this.scrq = outFieldStock.getScrq();
   	this.xh = outFieldStock.getXh();
   	this.gg = outFieldStock.getGg();
   	this.zzcs = outFieldStock.getZzcs();
   	this.tsn = outFieldStock.getTsn();
   	this.tso = outFieldStock.getTso();
   	this.hcly = outFieldStock.getHcly();
   	this.cfyq = outFieldStock.getCfyq();
   	this.xkzh = outFieldStock.getXkzh();
   }
   
	public String getJyr() {
	return jyr;
}

public void setJyr(String jyr) {
	this.jyr = jyr;
}


	public String getJyrid() {
	return jyrid;
}

public void setJyrid(String jyrid) {
	this.jyrid = jyrid;
}

	public List<Attachment> getAttachmentList() {
	return attachmentList;
}

public void setAttachmentList(List<Attachment> attachmentList) {
	this.attachmentList = attachmentList;
}

	public Date getHjsm() {
		return hjsm;
	}

	public void setHjsm(Date hjsm) {
		this.hjsm = hjsm;
	}

	public String getCjjh() {
		return cjjh;
	}

	public void setCjjh(String cjjh) {
		this.cjjh = cjjh;
	}

	public Integer getHclx() {
		return hclx;
	}

	public void setHclx(Integer hclx) {
		this.hclx = hclx;
	}

	public Integer getGljb() {
		return gljb;
	}

	public void setGljb(Integer gljb) {
		this.gljb = gljb;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

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

    public String getJydh() {
        return jydh;
    }

    public void setJydh(String jydh) {
        this.jydh = jydh == null ? null : jydh.trim();
    }

    public String getHtid() {
        return htid;
    }

    public void setHtid(String htid) {
        this.htid = htid == null ? null : htid.trim();
    }

    public String getHth() {
        return hth;
    }

    public void setHth(String hth) {
        this.hth = hth == null ? null : hth.trim();
    }

    public String getRkdid() {
        return rkdid;
    }

    public void setRkdid(String rkdid) {
        this.rkdid = rkdid == null ? null : rkdid.trim();
    }

    public String getRkdh() {
        return rkdh;
    }

    public void setRkdh(String rkdh) {
        this.rkdh = rkdh == null ? null : rkdh.trim();
    }

    public String getTddid() {
        return tddid;
    }

    public void setTddid(String tddid) {
        this.tddid = tddid == null ? null : tddid.trim();
    }

    public String getTddh() {
        return tddh;
    }

    public void setTddh(String tddh) {
        this.tddh = tddh == null ? null : tddh.trim();
    }

    public String getKcid() {
        return kcid;
    }

    public void setKcid(String kcid) {
        this.kcid = kcid == null ? null : kcid.trim();
    }

    public Integer getCklb() {
        return cklb;
    }

    public void setCklb(Integer cklb) {
        this.cklb = cklb;
    }

    public String getCkid() {
        return ckid;
    }

    public void setCkid(String ckid) {
        this.ckid = ckid == null ? null : ckid.trim();
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh == null ? null : ckh.trim();
    }

    public String getCkmc() {
        return ckmc;
    }

    public void setCkmc(String ckmc) {
        this.ckmc = ckmc == null ? null : ckmc.trim();
    }

    public String getKwid() {
        return kwid;
    }

    public void setKwid(String kwid) {
        this.kwid = kwid == null ? null : kwid.trim();
    }

    public String getKwh() {
        return kwh;
    }

    public void setKwh(String kwh) {
        this.kwh = kwh == null ? null : kwh.trim();
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

    public String getZwms() {
        return zwms;
    }

    public void setZwms(String zwms) {
        this.zwms = zwms == null ? null : zwms.trim();
    }

    public String getYwms() {
        return ywms;
    }

    public void setYwms(String ywms) {
        this.ywms = ywms == null ? null : ywms.trim();
    }

    public String getJldw() {
        return jldw;
    }

    public void setJldw(String jldw) {
        this.jldw = jldw == null ? null : jldw.trim();
    }

    public BigDecimal getKcsl() {
    	if(kcsl!=null){
    		NumberFormat nf = NumberFormat.getInstance();
    		return new BigDecimal(nf.format(kcsl));
    	}
        return kcsl;
    }

    public void setKcsl(BigDecimal kcsl) {
        this.kcsl = kcsl;
    }


    public String getHcly() {
		return hcly;
	}

	public void setHcly(String hcly) {
		this.hcly = hcly;
	}

	public String getCfyq() {
        return cfyq;
    }

    public void setCfyq(String cfyq) {
        this.cfyq = cfyq == null ? null : cfyq.trim();
    }

    public String getXkzh() {
        return xkzh;
    }

    public void setXkzh(String xkzh) {
        this.xkzh = xkzh == null ? null : xkzh.trim();
    }

    public String getShzh() {
        return shzh;
    }

    public void setShzh(String shzh) {
        this.shzh = shzh == null ? null : shzh.trim();
    }

    public String getShzwz() {
        return shzwz;
    }

    public void setShzwz(String shzwz) {
        this.shzwz = shzwz == null ? null : shzwz.trim();
    }

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn == null ? null : tsn.trim();
    }

    public String getTso() {
        return tso;
    }

    public void setTso(String tso) {
        this.tso = tso == null ? null : tso.trim();
    }

    public Date getJyrq() {
        return jyrq;
    }

    public void setJyrq(Date jyrq) {
        this.jyrq = jyrq;
    }

    public Integer getJyjg() {
        return jyjg;
    }

    public void setJyjg(Integer jyjg) {
        this.jyjg = jyjg;
    }

    public String getJgsm() {
        return jgsm;
    }

    public void setJgsm(String jgsm) {
        this.jgsm = jgsm == null ? null : jgsm.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Date getScrq() {
        return scrq;
    }

    public void setScrq(Date scrq) {
        this.scrq = scrq;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh == null ? null : xh.trim();
    }

    public String getGg() {
        return gg;
    }

    public void setGg(String gg) {
        this.gg = gg == null ? null : gg.trim();
    }

    public String getZzcs() {
        return zzcs;
    }

    public void setZzcs(String zzcs) {
        this.zzcs = zzcs == null ? null : zzcs.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
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

	@Override
	public String toString() {
		return "QualityCheck [id=" + id + ", dprtcode=" + dprtcode + ", jydh="
				+ jydh + ", htid=" + htid + ", hth=" + hth + ", rkdid=" + rkdid
				+ ", rkdh=" + rkdh + ", tddid=" + tddid + ", tddh=" + tddh
				+ ", kcid=" + kcid + ", cklb=" + cklb + ", ckid=" + ckid
				+ ", ckh=" + ckh + ", ckmc=" + ckmc + ", kwid=" + kwid
				+ ", kwh=" + kwh + ", bjid=" + bjid + ", bjh=" + bjh + ", pch="
				+ pch + ", sn=" + sn + ", zwms=" + zwms + ", ywms=" + ywms
				+ ", jldw=" + jldw + ", kcsl=" + kcsl + ", hcly=" + hcly
				+ ", cfyq=" + cfyq + ", xkzh=" + xkzh + ", shzh=" + shzh
				+ ", shzwz=" + shzwz + ", tsn=" + tsn + ", tso=" + tso
				+ ", jyrq=" + jyrq + ", jyjg=" + jyjg + ", jgsm=" + jgsm
				+ ", bz=" + bz + ", scrq=" + scrq + ", xh=" + xh + ", gg=" + gg
				+ ", zzcs=" + zzcs + ", zt=" + zt + ", zdbmid=" + zdbmid
				+ ", zdrid=" + zdrid + ", zdsj=" + zdsj + ", jyr=" + jyr
				+ ", jyrid=" + jyrid + ", username=" + username + ", realname="
				+ realname + ", dprtname=" + dprtname + ", cjjh=" + cjjh
				+ ", hclx=" + hclx + ", gljb=" + gljb + ", hjsm=" + hjsm
				+ ", attachmentList=" + attachmentList + "]";
	}
    
}