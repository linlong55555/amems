package com.eray.thjw.produce.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.mapper.CustomJsonDateDeserializer;
import com.eray.thjw.po.BaseEntity;

/**
 * @Description b_s2_00604 航段信息-完成工作
 * @CreateTime 2017年10月24日 下午4:58:27
 * @CreateBy 韩武
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlightSheetFinishedWork extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String mainid;

    private Integer zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String hdid;

    private Integer xc;

    private Integer sgbs;

    private String gbid;

    private String gbbh;

    private String gdid;

    private String gdbh;

    private String gznr;

    private String gzbg;

    private String gzxx;

    private String clcs;

    private BigDecimal sjgs;

    private Integer sjrs;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date wcrq;

    private String zrrid;

    private String zrr;

    private String bz;

    private String gzzid;

    private String gzz;
    
    private String sjZd;
    
    private String rwxx;
    
    private String fxjldid;
    
    private Integer hsgs;
    
    /** 完成工作 */
    private List<FlightSheetDisassemblyRecord> disassemblies;
    
    /** 附件 */
    private List<Attachment> attachments;
    
    /** 工作者 */
    private List<FlightSheetWorker> workers;
    
    private Workorder workorder;
    
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

    public String getHdid() {
        return hdid;
    }

    public void setHdid(String hdid) {
        this.hdid = hdid == null ? null : hdid.trim();
    }

    public Integer getXc() {
        return xc;
    }

    public void setXc(Integer xc) {
        this.xc = xc;
    }

    public Integer getSgbs() {
        return sgbs;
    }

    public void setSgbs(Integer sgbs) {
        this.sgbs = sgbs;
    }

    public String getGbid() {
        return gbid;
    }

    public void setGbid(String gbid) {
        this.gbid = gbid == null ? null : gbid.trim();
    }

    public String getGbbh() {
        return gbbh;
    }

    public void setGbbh(String gbbh) {
        this.gbbh = gbbh == null ? null : gbbh.trim();
    }

    public String getGdid() {
        return gdid;
    }

    public void setGdid(String gdid) {
        this.gdid = gdid == null ? null : gdid.trim();
    }

    public String getGdbh() {
        return gdbh;
    }

    public void setGdbh(String gdbh) {
        this.gdbh = gdbh == null ? null : gdbh.trim();
    }

    public String getGznr() {
        return gznr;
    }

    public void setGznr(String gznr) {
        this.gznr = gznr == null ? null : gznr.trim();
    }

    public String getGzbg() {
        return gzbg;
    }

    public void setGzbg(String gzbg) {
        this.gzbg = gzbg == null ? null : gzbg.trim();
    }

    public String getGzxx() {
        return gzxx;
    }

    public void setGzxx(String gzxx) {
        this.gzxx = gzxx == null ? null : gzxx.trim();
    }

    public String getClcs() {
        return clcs;
    }

    public void setClcs(String clcs) {
        this.clcs = clcs == null ? null : clcs.trim();
    }

    public BigDecimal getSjgs() {
        return sjgs;
    }

    public void setSjgs(BigDecimal sjgs) {
        this.sjgs = sjgs;
    }

    public Integer getSjrs() {
        return sjrs;
    }

    public void setSjrs(Integer sjrs) {
        this.sjrs = sjrs;
    }

    public Date getWcrq() {
        return wcrq;
    }

    public void setWcrq(Date wcrq) {
        this.wcrq = wcrq;
    }

    public String getZrrid() {
        return zrrid;
    }

    public void setZrrid(String zrrid) {
        this.zrrid = zrrid == null ? null : zrrid.trim();
    }

    public String getZrr() {
        return zrr;
    }

    public void setZrr(String zrr) {
        this.zrr = zrr == null ? null : zrr.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getGzzid() {
        return gzzid;
    }

    public void setGzzid(String gzzid) {
        this.gzzid = gzzid == null ? null : gzzid.trim();
    }

    public String getGzz() {
        return gzz;
    }

    public void setGzz(String gzz) {
        this.gzz = gzz == null ? null : gzz.trim();
    }

	public List<FlightSheetDisassemblyRecord> getDisassemblies() {
		return disassemblies;
	}

	public void setDisassemblies(List<FlightSheetDisassemblyRecord> disassemblies) {
		this.disassemblies = disassemblies;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public Workorder getWorkorder() {
		return workorder;
	}

	public void setWorkorder(Workorder workorder) {
		this.workorder = workorder;
	}

	public String getSjZd() {
		return sjZd;
	}

	public void setSjZd(String sjZd) {
		this.sjZd = sjZd;
	}

	public String getRwxx() {
		return rwxx;
	}

	public void setRwxx(String rwxx) {
		this.rwxx = rwxx;
	}

	public String getFxjldid() {
		return fxjldid;
	}

	public void setFxjldid(String fxjldid) {
		this.fxjldid = fxjldid;
	}

	public List<FlightSheetWorker> getWorkers() {
		return workers;
	}

	public void setWorkers(List<FlightSheetWorker> workers) {
		this.workers = workers;
	}

	public Integer getHsgs() {
		return hsgs;
	}

	public void setHsgs(Integer hsgs) {
		this.hsgs = hsgs;
	}

}