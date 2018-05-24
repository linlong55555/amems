package com.eray.thjw.productionplan.po;

import java.util.Date;
import java.util.UUID;

public class PlanTaskSnap {
    private String id;

    private String mainid;

    private String zjqdid;

    private String djxmid;

    private String djxmbh;

    private String jkxmbhF;

    private String jkflbhF;

    private String jkzJhF;

    private String jkzSjF;

    private String jkxmbhS;

    private String jkflbhS;

    private String jkzJhS;

    private String jkzSjS;

    private String jkxmbhT;

    private String jkflbhT;

    private String jkzJhT;

    private String jkzSjT;

    private String whdwid;

    private String whrid;

    private Date whsj;
    
    private String zt;
    
    public PlanTaskSnap() {
	}
    public PlanTaskSnap(PlanTaskExt planTaskExt) {
    	this.setId(UUID.randomUUID().toString());
		this.setMainid(planTaskExt.getId());
		this.setZjqdid(planTaskExt.getZjqdid());
		
		//监控项目
		this.setJkxmbhF(planTaskExt.getJkxmbh_f());
		this.setJkxmbhS(planTaskExt.getJkxmbh_s());
		this.setJkxmbhT(planTaskExt.getJkxmbh_t());
		
		//监控类别
		this.setJkflbhF(planTaskExt.getJkflbh_f());
		this.setJkflbhS(planTaskExt.getJkflbh_s());
		this.setJkflbhT(planTaskExt.getJkflbh_t());
		
		//监控项目各个计划值
		this.setJkzJhF(planTaskExt.getJkz_f());
		this.setJkzJhS(planTaskExt.getJkz_s());
		this.setJkzJhT(planTaskExt.getJkz_t());
		 
		//监控项目各个实际值
		this.setJkzSjF(planTaskExt.getSj_f());
		this.setJkzSjS(planTaskExt.getSj_s());
		this.setJkzSjT(planTaskExt.getSj_t());
		
		//监控项目各个实际值
		this.setJkzSjF(planTaskExt.getSj_f());
		this.setJkzSjS(planTaskExt.getSj_s());
		this.setJkzSjT(planTaskExt.getSj_t());
		
		this.setWhdwid(planTaskExt.getWhdwid());
		this.setWhrid(planTaskExt.getWhrid());
		this.setWhsj(planTaskExt.getWhsj());
		
		this.setDjxmbh(planTaskExt.getDjxmbh());
		this.setDjxmid(planTaskExt.getDjxmid());
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

    public String getZjqdid() {
        return zjqdid;
    }

    public void setZjqdid(String zjqdid) {
        this.zjqdid = zjqdid == null ? null : zjqdid.trim();
    }

    public String getDjxmid() {
        return djxmid;
    }

    public void setDjxmid(String djxmid) {
        this.djxmid = djxmid == null ? null : djxmid.trim();
    }

    public String getDjxmbh() {
        return djxmbh;
    }

    public void setDjxmbh(String djxmbh) {
        this.djxmbh = djxmbh == null ? null : djxmbh.trim();
    }

    public String getJkxmbhF() {
        return jkxmbhF;
    }

    public void setJkxmbhF(String jkxmbhF) {
        this.jkxmbhF = jkxmbhF == null ? null : jkxmbhF.trim();
    }

    public String getJkflbhF() {
        return jkflbhF==null?"":jkflbhF;
    }

    public void setJkflbhF(String jkflbhF) {
        this.jkflbhF = jkflbhF == null ? null : jkflbhF.trim();
    }

    public String getJkzJhF() {
    	return jkzJhF==null?"":jkzJhF;
    }

    public void setJkzJhF(String jkzJhF) {
        this.jkzJhF = jkzJhF == null ? null : jkzJhF.trim();
    }

    public String getJkzSjF() {
    	return jkzSjF==null?"":jkzSjF;
    }

    public void setJkzSjF(String jkzSjF) {
        this.jkzSjF = jkzSjF == null ? null : jkzSjF.trim();
    }

    public String getJkxmbhS() {
        return jkxmbhS;
    }

    public void setJkxmbhS(String jkxmbhS) {
        this.jkxmbhS = jkxmbhS == null ? null : jkxmbhS.trim();
    }

    public String getJkflbhS() {
    	return jkflbhS==null?"":jkflbhS;
    }

    public void setJkflbhS(String jkflbhS) {
        this.jkflbhS = jkflbhS == null ? null : jkflbhS.trim();
    }

    public String getJkzJhS() {
    	return jkzJhS==null?"":jkzJhS;
    }

    public void setJkzJhS(String jkzJhS) {
        this.jkzJhS = jkzJhS == null ? null : jkzJhS.trim();
    }

    public String getJkzSjS() {
    	return jkzSjS==null?"":jkzSjS;
    }

    public void setJkzSjS(String jkzSjS) {
        this.jkzSjS = jkzSjS == null ? null : jkzSjS.trim();
    }

    public String getJkxmbhT() {
        return jkxmbhT;
    }

    public void setJkxmbhT(String jkxmbhT) {
        this.jkxmbhT = jkxmbhT == null ? null : jkxmbhT.trim();
    }

    public String getJkflbhT() {
    	return jkflbhT==null?"":jkflbhT;
    }

    public void setJkflbhT(String jkflbhT) {
        this.jkflbhT = jkflbhT == null ? null : jkflbhT.trim();
    }

    public String getJkzJhT() {
    	return jkzJhT==null?"":jkzJhT;
    }

    public void setJkzJhT(String jkzJhT) {
        this.jkzJhT = jkzJhT == null ? null : jkzJhT.trim();
    }

    public String getJkzSjT() {
    	return jkzSjT==null?"":jkzSjT;
    }

    public void setJkzSjT(String jkzSjT) {
        this.jkzSjT = jkzSjT == null ? null : jkzSjT.trim();
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

	public String getZt() {
		return zt;
	}

	public void setZt(String zt) {
		this.zt = zt;
	}
}