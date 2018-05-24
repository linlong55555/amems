package com.eray.thjw.po;

import java.util.Date;
import java.util.List;

public class FixedCheckWorkContent {
    private String id;

    private String djxmid;

    private String nbxh;

    private String zjh;

    private String wz;

    private String cksc;

    private String ckgk;

    private Integer isBj;

    private Integer isMi;

    private String bz;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String dprtcode;
    
    private String xmly;
    
    private String gzlx;
    
    private String scmsZw;
    
    private String scmsYw;
    
    private String jclx;
    
    private String fjzch;
    
    private String gzzw;
    
    private String djgk; 
    
    private String isUpd;
    
    private String normal;
    
    private String fjzchstr;
    
    private FixedCheckWorkContent fixedCheckWorkContent;
    
    private List<JobCard> jobCardList;
    
    private List<FixedCheckWorkPlane> fixedCheckWorkPlaneList;
    
    private FixChapter fixChapter;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getDjxmid() {
        return djxmid;
    }

    public void setDjxmid(String djxmid) {
        this.djxmid = djxmid == null ? null : djxmid.trim();
    }

    public String getNbxh() {
        return nbxh;
    }

    public void setNbxh(String nbxh) {
        this.nbxh = nbxh == null ? null : nbxh.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public String getWz() {
        return wz;
    }

    public void setWz(String wz) {
        this.wz = wz == null ? null : wz.trim();
    }

    public String getCksc() {
        return cksc;
    }

    public void setCksc(String cksc) {
        this.cksc = cksc == null ? null : cksc.trim();
    }

    public String getCkgk() {
        return ckgk;
    }

    public void setCkgk(String ckgk) {
        this.ckgk = ckgk == null ? null : ckgk.trim();
    }

    public Integer getIsBj() {
        return isBj;
    }

    public void setIsBj(Integer isBj) {
        this.isBj = isBj;
    }

    public Integer getIsMi() {
        return isMi;
    }

    public void setIsMi(Integer isMi) {
        this.isMi = isMi;
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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }
    
	public String getIsUpd() {
		return isUpd;
	}

	public void setIsUpd(String isUpd) {
		this.isUpd = isUpd;
	}
	
	public String getNormal() {
		return normal;
	}

	public void setNormal(String normal) {
		this.normal = normal;
	}

	public FixedCheckWorkContent getFixedCheckWorkContent() {
		return fixedCheckWorkContent;
	}

	public void setFixedCheckWorkContent(FixedCheckWorkContent fixedCheckWorkContent) {
		this.fixedCheckWorkContent = fixedCheckWorkContent;
	}
	
	public List<JobCard> getJobCardList() {
		return jobCardList;
	}

	public void setJobCardList(List<JobCard> jobCardList) {
		this.jobCardList = jobCardList;
	}

	public String getXmly() {
		return xmly;
	}

	public void setXmly(String xmly) {
		this.xmly = xmly;
	}

	public String getGzlx() {
		return gzlx;
	}

	public void setGzlx(String gzlx) {
		this.gzlx = gzlx;
	}

	public String getScmsZw() {
		return scmsZw;
	}

	public void setScmsZw(String scmsZw) {
		this.scmsZw = scmsZw;
	}

	public String getScmsYw() {
		return scmsYw;
	}

	public void setScmsYw(String scmsYw) {
		this.scmsYw = scmsYw;
	}

	public String getJclx() {
		return jclx;
	}

	public void setJclx(String jclx) {
		this.jclx = jclx;
	}

	public String getFjzch() {
		return fjzch;
	}

	public void setFjzch(String fjzch) {
		this.fjzch = fjzch;
	}

	public String getGzzw() {
		return gzzw;
	}

	public void setGzzw(String gzzw) {
		this.gzzw = gzzw;
	}
	
	public String getDjgk() {
		return djgk;
	}

	public void setDjgk(String djgk) {
		this.djgk = djgk;
	}
	
	public List<FixedCheckWorkPlane> getFixedCheckWorkPlaneList() {
		return fixedCheckWorkPlaneList;
	}

	public void setFixedCheckWorkPlaneList(
			List<FixedCheckWorkPlane> fixedCheckWorkPlaneList) {
		this.fixedCheckWorkPlaneList = fixedCheckWorkPlaneList;
	}

	public String getFjzchstr() {
		return fjzchstr;
	}

	public void setFjzchstr(String fjzchstr) {
		this.fjzchstr = fjzchstr;
	}

	public FixChapter getFixChapter() {
		return fixChapter;
	}

	public void setFixChapter(FixChapter fixChapter) {
		this.fixChapter = fixChapter;
	}

	/**
	 * @author liub
	 * @description 比较工作内容
	 * @param w
	 * @return
	 * @develop date 2016.09.08
	 */
	public void CompareColumn(FixedCheckWorkContent w){
		FixedCheckWorkContent w2 = w.getFixedCheckWorkContent();
		if(w2 != null){
			if(    cp(w.getZjh(),w2.getZjh())
				&& cp(w.getXmly(),w2.getXmly())
				&& cp(w.getGzlx(),w2.getGzlx())
				&& cp(w.getScmsZw(),w2.getScmsZw())
				&& cp(w.getScmsYw(),w2.getScmsYw())
				&& cp(w.getJclx(),w2.getJclx())
				&& cp(w.getFjzch(),w2.getFjzch())
				&& cp(w.getGzzw(),w2.getGzzw())
				&& cp(w.getCksc(),w2.getCksc())
				&& cp(w.getCkgk(),w2.getCkgk())
				&& w.getIsBj().intValue() == w2.getIsBj().intValue()
				&& w.getIsMi().intValue() == w2.getIsMi().intValue()
				&& cp(w.getBz(),w.getBz())
			){
				if(null == w.getFjzch() || "".equals(w.getFjzch()) || "-".equals(w.getFjzch())){
					if(!cp(w.getFjzchstr(),w2.getFjzchstr())){
						w.setIsUpd(w.getId());
					}
				}
			}else{
				w.setIsUpd(w.getId());
			}
			if(w.getZt() == 0 && w2.getZt() == 0){
				w.setNormal(w.getId());
			}
		}else{
			w.setIsUpd(w.getId());
		}
	}
	/**
	 * @author liub
	 * @description 比较字符串
	 * @param s1,s2
	 * @return boolean
	 * @develop date 2016.09.08
	 */
	private boolean cp(String s1, String s2){
		s1 = s1==null?"":s1;
		s2 = s2==null?"":s2;
		return s1.equals(s2);
	}
    
}