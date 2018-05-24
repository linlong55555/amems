package com.eray.thjw.aerialmaterial.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;

/**
 * @author liub
 * @description 仓库D_009
 * @develop date 2016.09.09
 */
public class Store extends BizEntity{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

    private String ckh;

    private String dprtcode;

    private String ckmc;

    private String ckdz;

    private Integer cklb;

    private String kgyid;
    
    private String kgyrealname;
    
    private String kgyusername;
    
    private String displayName;
    
    private String jd;

    private String bz;

    private Integer zt;

    private String bmid;

    private String cjrid;
    
    private Date cjsj;
    
    private String selectId;
    
    private List<String> storageIdList;
    
    private List<Storage> storageList;
    
    private String dprtname;
    
    public String getJd() {
		return jd;
	}

	public void setJd(String jd) {
		this.jd = jd;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCkh() {
        return ckh;
    }

    public void setCkh(String ckh) {
        this.ckh = ckh == null ? null : ckh.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getCkmc() {
        return ckmc;
    }

    public void setCkmc(String ckmc) {
        this.ckmc = ckmc == null ? null : ckmc.trim();
    }

    public String getCkdz() {
        return ckdz;
    }

    public void setCkdz(String ckdz) {
        this.ckdz = ckdz == null ? null : ckdz.trim();
    }

    public Integer getCklb() {
        return cklb;
    }

    public void setCklb(Integer cklb) {
        this.cklb = cklb;
    }

    public String getKgyid() {
        return kgyid;
    }

    public void setKgyid(String kgyid) {
        this.kgyid = kgyid == null ? null : kgyid.trim();
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

    public String getBmid() {
        return bmid;
    }

    public void setBmid(String bmid) {
        this.bmid = bmid == null ? null : bmid.trim();
    }

    public String getCjrid() {
        return cjrid;
    }

    public void setCjrid(String cjrid) {
        this.cjrid = cjrid == null ? null : cjrid.trim();
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

	public String getSelectId() {
		return selectId;
	}

	public void setSelectId(String selectId) {
		this.selectId = selectId;
	}

	public List<Storage> getStorageList() {
		return storageList;
	}

	public void setStorageList(List<Storage> storageList) {
		this.storageList = storageList;
	}

	public List<String> getStorageIdList() {
		return storageIdList;
	}

	public void setStorageIdList(List<String> storageIdList) {
		this.storageIdList = storageIdList;
	}

	public String getKgyrealname() {
		return kgyrealname;
	}

	public void setKgyrealname(String kgyrealname) {
		this.kgyrealname = kgyrealname;
	}

	public String getKgyusername() {
		return kgyusername;
	}

	public void setKgyusername(String kgyusername) {
		this.kgyusername = kgyusername;
	}

	public String getDisplayName() {
		displayName = (kgyusername==null?"":kgyusername).concat(" ").concat(kgyrealname==null?"":kgyrealname);
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDprtname() {
		return dprtname;
	}

	public void setDprtname(String dprtname) {
		this.dprtname = dprtname;
	}

}