package com.eray.thjw.project.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.OrderSource;
import com.eray.thjw.po.User;
import com.eray.thjw.project2.po.Instructionsource;
import com.eray.thjw.project2.po.Technical;

/**
 * @author liub
 * @description MEL变更单B_G_019
 */
public class MelChangeSheet extends BizEntity {
    private String id;

    private String zdbmid;

    private String zdrid;

    private Date zdsj;

    private Integer zt;

    private Integer dyzt;

    private String dprtcode;

    private String zdjsrid;

    private Date zdjsrq;

    private String zdjsyy;

    private String shbmid;

    private String shyj;

    private String shrid;

    private Date shsj;

    private String pfbmid;

    private String pfyj;

    private String pfrid;

    private Date pfsj;

    private String jx;

    private String ggdbh;

    private String xgqBb;

    private String xghBb;

    private String xmh;

    private String ssbf;

    private String sszj;

    private String zy;

    private String xgyj;

    private String xgbj;

    private String xdnr;

    private String xgyy;

    private String xdqx;

    private String melqdfjid;
    
    private Integer shjl;
    
    private Integer pfjl;
    
    private User shr;
    
    private User pyr;
    
    private User jsr;
    
    private Attachment attachment;
    
    private List<Instructionsource> isList;
    
    private List<Technical> technicalList;
    
    private List<MelChangeSheetAndBasis> melChangeSheetAndBasiList;//MEL更改单-修改依据
    
    private List<Attachment> AttachmentList;//上传文件列表
    
    private List<OrderSource> OrderSourceList;
    
    public Integer getShjl() {
		return shjl;
	}

	public void setShjl(Integer shjl) {
		this.shjl = shjl;
	}

	public Integer getPfjl() {
		return pfjl;
	}

	public void setPfjl(Integer pfjl) {
		this.pfjl = pfjl;
	}

	public List<OrderSource> getOrderSourceList() {
		return OrderSourceList;
	}

	public void setOrderSourceList(List<OrderSource> orderSourceList) {
		OrderSourceList = orderSourceList;
	}

	public List<Attachment> getAttachmentList() {
		return AttachmentList;
	}

	public void setAttachmentList(List<Attachment> attachmentList) {
		AttachmentList = attachmentList;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Integer getDyzt() {
        return dyzt;
    }

    public void setDyzt(Integer dyzt) {
        this.dyzt = dyzt;
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getZdjsrid() {
        return zdjsrid;
    }

    public void setZdjsrid(String zdjsrid) {
        this.zdjsrid = zdjsrid == null ? null : zdjsrid.trim();
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
        this.zdjsyy = zdjsyy == null ? null : zdjsyy.trim();
    }

    public String getShbmid() {
        return shbmid;
    }

    public void setShbmid(String shbmid) {
        this.shbmid = shbmid == null ? null : shbmid.trim();
    }

    public String getShyj() {
        return shyj;
    }

    public void setShyj(String shyj) {
        this.shyj = shyj == null ? null : shyj.trim();
    }

    public String getShrid() {
        return shrid;
    }

    public void setShrid(String shrid) {
        this.shrid = shrid == null ? null : shrid.trim();
    }

    public Date getShsj() {
        return shsj;
    }

    public void setShsj(Date shsj) {
        this.shsj = shsj;
    }

    public String getPfbmid() {
        return pfbmid;
    }

    public void setPfbmid(String pfbmid) {
        this.pfbmid = pfbmid == null ? null : pfbmid.trim();
    }

    public String getPfyj() {
        return pfyj;
    }

    public void setPfyj(String pfyj) {
        this.pfyj = pfyj == null ? null : pfyj.trim();
    }

    public String getPfrid() {
        return pfrid;
    }

    public void setPfrid(String pfrid) {
        this.pfrid = pfrid == null ? null : pfrid.trim();
    }

    public Date getPfsj() {
        return pfsj;
    }

    public void setPfsj(Date pfsj) {
        this.pfsj = pfsj;
    }

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public String getGgdbh() {
        return ggdbh;
    }

    public void setGgdbh(String ggdbh) {
        this.ggdbh = ggdbh == null ? null : ggdbh.trim();
    }

    public String getXgqBb() {
        return xgqBb;
    }

    public void setXgqBb(String xgqBb) {
        this.xgqBb = xgqBb == null ? null : xgqBb.trim();
    }

    public String getXghBb() {
        return xghBb;
    }

    public void setXghBb(String xghBb) {
        this.xghBb = xghBb == null ? null : xghBb.trim();
    }

    public String getXmh() {
        return xmh;
    }

    public void setXmh(String xmh) {
        this.xmh = xmh == null ? null : xmh.trim();
    }

    public String getSsbf() {
        return ssbf;
    }

    public void setSsbf(String ssbf) {
        this.ssbf = ssbf == null ? null : ssbf.trim();
    }

    public String getSszj() {
        return sszj;
    }

    public void setSszj(String sszj) {
        this.sszj = sszj == null ? null : sszj.trim();
    }

    public String getZy() {
        return zy;
    }

    public void setZy(String zy) {
        this.zy = zy == null ? null : zy.trim();
    }

    public String getXgyj() {
        return xgyj;
    }

    public void setXgyj(String xgyj) {
        this.xgyj = xgyj == null ? null : xgyj.trim();
    }

    public String getXgbj() {
        return xgbj;
    }

    public void setXgbj(String xgbj) {
        this.xgbj = xgbj == null ? null : xgbj.trim();
    }

    public String getXdnr() {
        return xdnr;
    }

    public void setXdnr(String xdnr) {
        this.xdnr = xdnr == null ? null : xdnr.trim();
    }

    public String getXgyy() {
        return xgyy;
    }

    public void setXgyy(String xgyy) {
        this.xgyy = xgyy == null ? null : xgyy.trim();
    }

    public String getXdqx() {
        return xdqx;
    }

    public void setXdqx(String xdqx) {
        this.xdqx = xdqx == null ? null : xdqx.trim();
    }

    public String getMelqdfjid() {
        return melqdfjid;
    }

    public void setMelqdfjid(String melqdfjid) {
        this.melqdfjid = melqdfjid == null ? null : melqdfjid.trim();
    }

	public List<MelChangeSheetAndBasis> getMelChangeSheetAndBasiList() {
		return melChangeSheetAndBasiList;
	}

	public void setMelChangeSheetAndBasiList(
			List<MelChangeSheetAndBasis> melChangeSheetAndBasiList) {
		this.melChangeSheetAndBasiList = melChangeSheetAndBasiList;
	}

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}

	public User getShr() {
		return shr;
	}

	public void setShr(User shr) {
		this.shr = shr;
	}

	public User getPyr() {
		return pyr;
	}

	public void setPyr(User pyr) {
		this.pyr = pyr;
	}

	public User getJsr() {
		return jsr;
	}

	public void setJsr(User jsr) {
		this.jsr = jsr;
	}

	public List<Instructionsource> getIsList() {
		return isList;
	}

	public void setIsList(List<Instructionsource> isList) {
		this.isList = isList;
	}

	public List<Technical> getTechnicalList() {
		return technicalList;
	}

	public void setTechnicalList(List<Technical> technicalList) {
		this.technicalList = technicalList;
	}
	
}