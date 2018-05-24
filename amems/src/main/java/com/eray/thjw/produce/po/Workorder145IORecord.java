package com.eray.thjw.produce.po;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.eray.thjw.mapper.CustomJsonDateDeserializer;
import com.eray.thjw.po.BaseEntity;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Workorder145IORecord extends BaseEntity{
    private String id;

    private String mainid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private Integer cxBs;

    private String cxZjqdid;
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date cxSj;

    private String cxBz;

    private String cxWckcid;

    private String cxKclvid;

    private Integer zsBs;

    private String zsZjqdid;
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date zsSj;

    private String zsBz;

    private String zsWckcid;

    private String zsKclvid;

    private String bz;
    
    private String cxBjh;
    
    private String cxXlh;
    
    private String cxPch;
    
    private Integer cxCjsl;
    
    private String cxBjzwmc;
    
    private String cxBjywmc;
    
    private Integer cxWz;
    
    private String zrrid;
    
    private String zrr;
    
    /** 装上件 */
    private InstallationListTemp zsj;
    
    /** 拆下件 */
    private InstallationListEditable cxj;

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

    public Integer getCxBs() {
        return cxBs;
    }

    public void setCxBs(Integer cxBs) {
        this.cxBs = cxBs;
    }

    public String getCxZjqdid() {
        return cxZjqdid;
    }

    public void setCxZjqdid(String cxZjqdid) {
        this.cxZjqdid = cxZjqdid == null ? null : cxZjqdid.trim();
    }

    public Date getCxSj() {
        return cxSj;
    }

    public void setCxSj(Date cxSj) {
        this.cxSj = cxSj;
    }

    public String getCxBz() {
        return cxBz;
    }

    public void setCxBz(String cxBz) {
        this.cxBz = cxBz == null ? null : cxBz.trim();
    }

    public String getCxWckcid() {
        return cxWckcid;
    }

    public void setCxWckcid(String cxWckcid) {
        this.cxWckcid = cxWckcid == null ? null : cxWckcid.trim();
    }

    public String getCxKclvid() {
        return cxKclvid;
    }

    public void setCxKclvid(String cxKclvid) {
        this.cxKclvid = cxKclvid == null ? null : cxKclvid.trim();
    }

    public Integer getZsBs() {
        return zsBs;
    }

    public void setZsBs(Integer zsBs) {
        this.zsBs = zsBs;
    }

    public String getZsZjqdid() {
        return zsZjqdid;
    }

    public void setZsZjqdid(String zsZjqdid) {
        this.zsZjqdid = zsZjqdid == null ? null : zsZjqdid.trim();
    }

    public Date getZsSj() {
        return zsSj;
    }

    public void setZsSj(Date zsSj) {
        this.zsSj = zsSj;
    }

    public String getZsBz() {
        return zsBz;
    }

    public void setZsBz(String zsBz) {
        this.zsBz = zsBz == null ? null : zsBz.trim();
    }

    public String getZsWckcid() {
        return zsWckcid;
    }

    public void setZsWckcid(String zsWckcid) {
        this.zsWckcid = zsWckcid == null ? null : zsWckcid.trim();
    }

    public String getZsKclvid() {
        return zsKclvid;
    }

    public void setZsKclvid(String zsKclvid) {
        this.zsKclvid = zsKclvid == null ? null : zsKclvid.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

	public InstallationListTemp getZsj() {
		return zsj;
	}

	public void setZsj(InstallationListTemp zsj) {
		this.zsj = zsj;
	}

	public InstallationListEditable getCxj() {
		return cxj;
	}

	public void setCxj(InstallationListEditable cxj) {
		this.cxj = cxj;
	}

	public String getCxBjh() {
		return cxBjh;
	}

	public void setCxBjh(String cxBjh) {
		this.cxBjh = cxBjh;
	}

	public String getCxXlh() {
		return cxXlh;
	}

	public void setCxXlh(String cxXlh) {
		this.cxXlh = cxXlh;
	}

	public String getCxPch() {
		return cxPch;
	}

	public void setCxPch(String cxPch) {
		this.cxPch = cxPch;
	}

	public Integer getCxCjsl() {
		return cxCjsl;
	}

	public void setCxCjsl(Integer cxCjsl) {
		this.cxCjsl = cxCjsl;
	}

	public String getCxBjzwmc() {
		return cxBjzwmc;
	}

	public void setCxBjzwmc(String cxBjzwmc) {
		this.cxBjzwmc = cxBjzwmc;
	}

	public String getCxBjywmc() {
		return cxBjywmc;
	}

	public void setCxBjywmc(String cxBjywmc) {
		this.cxBjywmc = cxBjywmc;
	}

	public Integer getCxWz() {
		return cxWz;
	}

	public void setCxWz(Integer cxWz) {
		this.cxWz = cxWz;
	}

	public String getZrrid() {
		return zrrid;
	}

	public void setZrrid(String zrrid) {
		this.zrrid = zrrid;
	}

	public String getZrr() {
		return zrr;
	}

	public void setZrr(String zrr) {
		this.zrr = zrr;
	}
    
}