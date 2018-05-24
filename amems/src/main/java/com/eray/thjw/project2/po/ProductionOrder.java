package com.eray.thjw.project2.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.FixChapter;
import com.eray.thjw.po.User;

/**
 * @Description 生产指令 b_g2_014
 * @CreateTime 2018年4月28日 上午9:58:52
 * @CreateBy 徐勇
 */
public class ProductionOrder extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String zlbh;

    private BigDecimal bb;

    private String zlms;

    private String jx;

    private String zjh;

    private String syx;

    private String gkbh;

    private Integer jsgs;

    private Integer isHdwz;

    private String jgms;

    private String bz;

    private Integer zt;

    private String fBbid;

    private String bBbid;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Date sxsj;
    
    private Integer wz;
    
    private Integer zxbs;
    
    private String gkid;
    
    private String gkbb;
    
    private String gkbt;
    
    private String shyj;
    
    private String spyj;
    
    /** 生产指令监控项 */
    private List<ProductionOrderMonitor> monitors;
    
    /** 生产指令飞机关系 */
    private List<ProductionOrderPlane> planes;
    
    /** 附件 */
    private List<Attachment> attachments;
    
    /** 维护人 */
    private User whr;
    
    /** 章节号 */
    private FixChapter fixChapter;
    
    /** 工卡 */
    private WorkCard workCard;

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

    public String getZlbh() {
        return zlbh;
    }

    public void setZlbh(String zlbh) {
        this.zlbh = zlbh == null ? null : zlbh.trim();
    }

    public BigDecimal getBb() {
        return bb;
    }

    public void setBb(BigDecimal bb) {
        this.bb = bb;
    }

    public String getZlms() {
        return zlms;
    }

    public void setZlms(String zlms) {
        this.zlms = zlms == null ? null : zlms.trim();
    }

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public String getSyx() {
        return syx;
    }

    public void setSyx(String syx) {
        this.syx = syx == null ? null : syx.trim();
    }

    public String getGkbh() {
        return gkbh;
    }

    public void setGkbh(String gkbh) {
        this.gkbh = gkbh == null ? null : gkbh.trim();
    }

    public Integer getJsgs() {
        return jsgs;
    }

    public void setJsgs(Integer jsgs) {
        this.jsgs = jsgs;
    }

    public Integer getIsHdwz() {
        return isHdwz;
    }

    public void setIsHdwz(Integer isHdwz) {
        this.isHdwz = isHdwz;
    }

    public String getJgms() {
        return jgms;
    }

    public void setJgms(String jgms) {
        this.jgms = jgms == null ? null : jgms.trim();
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

    public String getfBbid() {
        return fBbid;
    }

    public void setfBbid(String fBbid) {
        this.fBbid = fBbid == null ? null : fBbid.trim();
    }

    public String getbBbid() {
        return bBbid;
    }

    public void setbBbid(String bBbid) {
        this.bBbid = bBbid == null ? null : bBbid.trim();
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

    public Date getSxsj() {
        return sxsj;
    }

    public void setSxsj(Date sxsj) {
        this.sxsj = sxsj;
    }

	public Integer getWz() {
		return wz;
	}

	public void setWz(Integer wz) {
		this.wz = wz;
	}

	public Integer getZxbs() {
		return zxbs;
	}

	public void setZxbs(Integer zxbs) {
		this.zxbs = zxbs;
	}

	public List<ProductionOrderMonitor> getMonitors() {
		return monitors;
	}

	public void setMonitors(List<ProductionOrderMonitor> monitors) {
		this.monitors = monitors;
	}

	public List<ProductionOrderPlane> getPlanes() {
		return planes;
	}

	public void setPlanes(List<ProductionOrderPlane> planes) {
		this.planes = planes;
	}

	public List<Attachment> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	public String getGkid() {
		return gkid;
	}

	public void setGkid(String gkid) {
		this.gkid = gkid;
	}

	public String getGkbb() {
		return gkbb;
	}

	public void setGkbb(String gkbb) {
		this.gkbb = gkbb;
	}

	public String getGkbt() {
		return gkbt;
	}

	public void setGkbt(String gkbt) {
		this.gkbt = gkbt;
	}

	public String getShyj() {
		return shyj;
	}

	public void setShyj(String shyj) {
		this.shyj = shyj;
	}

	public String getSpyj() {
		return spyj;
	}

	public void setSpyj(String spyj) {
		this.spyj = spyj;
	}

	public WorkCard getWorkCard() {
		return workCard;
	}

	public void setWorkCard(WorkCard workCard) {
		this.workCard = workCard;
	}

}