package com.eray.thjw.flightdata.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;

public class PartsDisassemblyRecord extends BizEntity {
    private String id;

    private String mainid;

    private String dprtcode;

    private String jh;

    private String xlh;

    private Integer zt;

    private String fjzch;

    private String fxjldid;

    private String azJld;

    private Date azZxrq;

    private String azGzzid;

    private String azBz;

    private String cjJld;

    private Date cjZxrq;

    private String cjGzzid;

    private String cjBz;

    private String cjZsjjh;

    private String cjZsjxlh;

    private Date whsj;

    private Integer isCx;

    private Date azSj;

    private Date cjSj;

    private String azFxjldid;
    
    private String cjFxjldid;
    
	private String azGzzxm;
    
    private String cjGzzxm;
    
    private String aZxrq;
    
    private String cZxrq;
    
    private List<String> ids;
 
	private String gdsx;
	
	private String bjyy;
	
	private String zssy;
	
	private String cxyy;
	
	private String cxsy;
	
	private String zjsy;
	

	
	
	
	
	public String getAzGzzxm() {
		return azGzzxm;
	}

	public void setAzGzzxm(String azGzzxm) {
		this.azGzzxm = azGzzxm;
	}

	public String getGdsx() {
		return gdsx;
	}

	public void setGdsx(String gdsx) {
		this.gdsx = gdsx;
	}

	public String getBjyy() {
		return bjyy;
	}

	public void setBjyy(String bjyy) {
		this.bjyy = bjyy;
	}

	public String getZssy() {
		return zssy;
	}

	public void setZssy(String zssy) {
		this.zssy = zssy;
	}

	public String getCxyy() {
		return cxyy;
	}

	public void setCxyy(String cxyy) {
		this.cxyy = cxyy;
	}

	public String getCxsy() {
		return cxsy;
	}

	public void setCxsy(String cxsy) {
		this.cxsy = cxsy;
	}

	public String getZjsy() {
		return zjsy;
	}

	public void setZjsy(String zjsy) {
		this.zjsy = zjsy;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public String getaZxrq() {
		return aZxrq;
	}

	public void setaZxrq(String aZxrq) {
		this.aZxrq = aZxrq;
	}

	public String getcZxrq() {
		return cZxrq;
	}

	public void setcZxrq(String cZxrq) {
		this.cZxrq = cZxrq;
	}

	public String getAzFxjldid() {
		return azFxjldid;
	}

	public void setAzFxjldid(String azFxjldid) {
		this.azFxjldid = azFxjldid;
	}

	public String getCjFxjldid() {
		return cjFxjldid;
	}

	public void setCjFxjldid(String cjFxjldid) {
		this.cjFxjldid = cjFxjldid;
	}

	public String getCjGzzxm() {
		return cjGzzxm;
	}

	public void setCjGzzxm(String cjGzzxm) {
		this.cjGzzxm = cjGzzxm;
	}

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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh == null ? null : jh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getFxjldid() {
        return fxjldid;
    }

    public void setFxjldid(String fxjldid) {
        this.fxjldid = fxjldid == null ? null : fxjldid.trim();
    }

    public String getAzJld() {
        return azJld;
    }

    public void setAzJld(String azJld) {
        this.azJld = azJld == null ? null : azJld.trim();
    }

    public Date getAzZxrq() {
        return azZxrq;
    }

    public void setAzZxrq(Date azZxrq) {
        this.azZxrq = azZxrq;
    }

    public String getAzGzzid() {
        return azGzzid;
    }

    public void setAzGzzid(String azGzzid) {
        this.azGzzid = azGzzid == null ? null : azGzzid.trim();
    }

    public String getAzBz() {
        return azBz;
    }

    public void setAzBz(String azBz) {
        this.azBz = azBz == null ? null : azBz.trim();
    }

    public String getCjJld() {
        return cjJld;
    }

    public void setCjJld(String cjJld) {
        this.cjJld = cjJld == null ? null : cjJld.trim();
    }

    public Date getCjZxrq() {
        return cjZxrq;
    }

    public void setCjZxrq(Date cjZxrq) {
        this.cjZxrq = cjZxrq;
    }

    public String getCjGzzid() {
        return cjGzzid;
    }

    public void setCjGzzid(String cjGzzid) {
        this.cjGzzid = cjGzzid == null ? null : cjGzzid.trim();
    }

    public String getCjBz() {
        return cjBz;
    }

    public void setCjBz(String cjBz) {
        this.cjBz = cjBz == null ? null : cjBz.trim();
    }

    public String getCjZsjjh() {
        return cjZsjjh;
    }

    public void setCjZsjjh(String cjZsjjh) {
        this.cjZsjjh = cjZsjjh == null ? null : cjZsjjh.trim();
    }

    public String getCjZsjxlh() {
        return cjZsjxlh;
    }

    public void setCjZsjxlh(String cjZsjxlh) {
        this.cjZsjxlh = cjZsjxlh == null ? null : cjZsjxlh.trim();
    }

    public Date getWhsj() {
        return whsj;
    }

    public void setWhsj(Date whsj) {
        this.whsj = whsj;
    }

    public Integer getIsCx() {
        return isCx;
    }

    public void setIsCx(Integer isCx) {
        this.isCx = isCx;
    }

    public Date getAzSj() {
        return azSj;
    }

    public void setAzSj(Date azSj) {
        this.azSj = azSj;
    }

    public Date getCjSj() {
        return cjSj;
    }

    public void setCjSj(Date cjSj) {
        this.cjSj = cjSj;
    }

	@Override
	public String toString() {
		return "PartsDisassemblyRecord [id=" + id + ", mainid=" + mainid
				+ ", dprtcode=" + dprtcode + ", jh=" + jh + ", xlh=" + xlh
				+ ", zt=" + zt + ", fjzch=" + fjzch + ", fxjldid=" + fxjldid
				+ ", azJld=" + azJld + ", azZxrq=" + azZxrq + ", azGzzid="
				+ azGzzid + ", azBz=" + azBz + ", cjJld=" + cjJld + ", cjZxrq="
				+ cjZxrq + ", cjGzzid=" + cjGzzid + ", cjBz=" + cjBz
				+ ", cjZsjjh=" + cjZsjjh + ", cjZsjxlh=" + cjZsjxlh + ", whsj="
				+ whsj + ", isCx=" + isCx + ", azSj=" + azSj + ", cjSj=" + cjSj
				+ ", azFxjldid=" + azFxjldid + ", cjFxjldid=" + cjFxjldid
				+ ", cjGzzxm=" + cjGzzxm + ", aZxrq=" + aZxrq + ", cZxrq="
				+ cZxrq + ", ids=" + ids + ", gdsx=" + gdsx + ", bjyy=" + bjyy
				+ ", zssy=" + zssy + ", cxyy=" + cxyy + ", cxsy=" + cxsy
				+ ", zjsy=" + zjsy + "]";
	}




    
}