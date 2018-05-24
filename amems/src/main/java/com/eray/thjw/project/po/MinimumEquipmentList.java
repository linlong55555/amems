package com.eray.thjw.project.po;

import java.util.Date;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * @author liub
 * @description Mel清单B_G_008
 */
public class MinimumEquipmentList extends BizEntity{
	private String id;

    private String dprtcode;

    private String jx;

    private String bb;

    private String melbgdid;

    private Integer zt;

    private String melqdfjid;

    private String whbmid;

    private String whrid;

    private Date whsj;
    
    private MelChangeSheet melChangeSheet;
    
    private User shr;
    
    private User pyr;
    
    private Attachment attachment;

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

    public String getJx() {
        return jx;
    }

    public void setJx(String jx) {
        this.jx = jx == null ? null : jx.trim();
    }

    public String getBb() {
        return bb;
    }

    public void setBb(String bb) {
        this.bb = bb == null ? null : bb.trim();
    }

    public String getMelbgdid() {
        return melbgdid;
    }

    public void setMelbgdid(String melbgdid) {
        this.melbgdid = melbgdid == null ? null : melbgdid.trim();
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getMelqdfjid() {
        return melqdfjid;
    }

    public void setMelqdfjid(String melqdfjid) {
        this.melqdfjid = melqdfjid == null ? null : melqdfjid.trim();
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

	public MelChangeSheet getMelChangeSheet() {
		return melChangeSheet;
	}

	public void setMelChangeSheet(MelChangeSheet melChangeSheet) {
		this.melChangeSheet = melChangeSheet;
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

	public Attachment getAttachment() {
		return attachment;
	}

	public void setAttachment(Attachment attachment) {
		this.attachment = attachment;
	}
    
    
}
