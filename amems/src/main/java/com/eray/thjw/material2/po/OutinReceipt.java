package com.eray.thjw.material2.po;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import com.eray.thjw.mapper.CustomJsonDateDeserializer;
import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.User;

/**
 * @Description b_h2_020 收货单
 * @CreateTime 2018年3月12日 下午2:49:04
 * @CreateBy 韩武
 */
public class OutinReceipt extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;

    private String dprtcode;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private Integer shlx;

    private String shdh;

    private String shbmid;

    private String shrid;

    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    private Date shrq;

    private String lydw;

    private String bz;

    private Integer zt;

    private String lyid;

    private String lybh;

    private String lymc;
    
    private String shck;
    
    /** 收货人id */
    private User shr;
    
    /** 收货单明细 */
    private List<OutinReceiptInfo> details;

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

    public Integer getShlx() {
        return shlx;
    }

    public void setShlx(Integer shlx) {
        this.shlx = shlx;
    }

    public String getShdh() {
        return shdh;
    }

    public void setShdh(String shdh) {
        this.shdh = shdh == null ? null : shdh.trim();
    }

    public String getShbmid() {
        return shbmid;
    }

    public void setShbmid(String shbmid) {
        this.shbmid = shbmid == null ? null : shbmid.trim();
    }

    public String getShrid() {
        return shrid;
    }

    public void setShrid(String shrid) {
        this.shrid = shrid == null ? null : shrid.trim();
    }

    public Date getShrq() {
        return shrq;
    }

    public void setShrq(Date shrq) {
        this.shrq = shrq;
    }

    public String getLydw() {
        return lydw;
    }

    public void setLydw(String lydw) {
        this.lydw = lydw == null ? null : lydw.trim();
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

    public String getLyid() {
        return lyid;
    }

    public void setLyid(String lyid) {
        this.lyid = lyid == null ? null : lyid.trim();
    }

    public String getLybh() {
        return lybh;
    }

    public void setLybh(String lybh) {
        this.lybh = lybh == null ? null : lybh.trim();
    }

    public String getLymc() {
        return lymc;
    }

    public void setLymc(String lymc) {
        this.lymc = lymc == null ? null : lymc.trim();
    }

	public List<OutinReceiptInfo> getDetails() {
		return details;
	}

	public void setDetails(List<OutinReceiptInfo> details) {
		this.details = details;
	}

	public User getShr() {
		return shr;
	}

	public void setShr(User shr) {
		this.shr = shr;
	}

	public String getShck() {
		return shck;
	}

	public void setShck(String shck) {
		this.shck = shck;
	}
}