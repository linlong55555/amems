package com.eray.thjw.quality.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BaseEntity;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.Department;
import com.eray.thjw.po.User;

/**
 * @Description b_z_013 审核问题清单
 * @CreateTime 2018年1月8日 下午1:45:42
 * @CreateBy 韩武
 */
public class AuditDiscoveryDetail extends BizEntity{
    private String id;

    private String dprtcode;

    private Integer zt;

    private String whbmid;

    private String whrid;

    private Date whsj;

    private String shwtdid;

    private String shwtbh;

    private String wtms;

    private String wtdj;

    private String wtfl;

    private String yyfx;

    private String jzcs;

    private String yfcs;

    private String zxrbmid;

    private String zxrid;

    private Date zxsj;

    private String wtfkrbmid;

    private String wtfkrid;

    private Date wtfksj;

    private String pgjg;

    private String pgrbmid;

    private String pgrid;

    private Date pgsj;

    private String yzjg;

    private String yzrbmid;

    private String yzrid;

    private Date yzsj;

    private String gbsm;

    private String gbrbmid;

    private String gbrid;

    private Date gbsj;

    private String bz;
    /** 审核问题单 */
    private AuditDiscovery auditDiscovery;
    /** 维护人*/
    private User detailWhr;
    /** 组织机构*/
    private Department department;
    /** 流程记录*/
    private List<ProcessRecord> processRecordList;
    /** 评估人*/
    private User pgr;
    /** 反馈人*/
    private User fkr;
    
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

    public String getShwtdid() {
        return shwtdid;
    }

    public void setShwtdid(String shwtdid) {
        this.shwtdid = shwtdid == null ? null : shwtdid.trim();
    }

    public String getShwtbh() {
        return shwtbh;
    }

    public void setShwtbh(String shwtbh) {
        this.shwtbh = shwtbh == null ? null : shwtbh.trim();
    }

    public String getWtms() {
        return wtms;
    }

    public void setWtms(String wtms) {
        this.wtms = wtms == null ? null : wtms.trim();
    }

    public String getWtdj() {
        return wtdj;
    }

    public void setWtdj(String wtdj) {
        this.wtdj = wtdj == null ? null : wtdj.trim();
    }

    public String getWtfl() {
        return wtfl;
    }

    public void setWtfl(String wtfl) {
        this.wtfl = wtfl == null ? null : wtfl.trim();
    }

    public String getYyfx() {
        return yyfx;
    }

    public void setYyfx(String yyfx) {
        this.yyfx = yyfx == null ? null : yyfx.trim();
    }

    public String getJzcs() {
        return jzcs;
    }

    public void setJzcs(String jzcs) {
        this.jzcs = jzcs == null ? null : jzcs.trim();
    }

    public String getYfcs() {
        return yfcs;
    }

    public void setYfcs(String yfcs) {
        this.yfcs = yfcs == null ? null : yfcs.trim();
    }

    public String getZxrbmid() {
        return zxrbmid;
    }

    public void setZxrbmid(String zxrbmid) {
        this.zxrbmid = zxrbmid == null ? null : zxrbmid.trim();
    }

    public String getZxrid() {
        return zxrid;
    }

    public void setZxrid(String zxrid) {
        this.zxrid = zxrid == null ? null : zxrid.trim();
    }

    public Date getZxsj() {
        return zxsj;
    }

    public void setZxsj(Date zxsj) {
        this.zxsj = zxsj;
    }

    public String getWtfkrbmid() {
        return wtfkrbmid;
    }

    public void setWtfkrbmid(String wtfkrbmid) {
        this.wtfkrbmid = wtfkrbmid == null ? null : wtfkrbmid.trim();
    }

    public String getWtfkrid() {
        return wtfkrid;
    }

    public void setWtfkrid(String wtfkrid) {
        this.wtfkrid = wtfkrid == null ? null : wtfkrid.trim();
    }

    public Date getWtfksj() {
        return wtfksj;
    }

    public void setWtfksj(Date wtfksj) {
        this.wtfksj = wtfksj;
    }

    public String getPgjg() {
        return pgjg;
    }

    public void setPgjg(String pgjg) {
        this.pgjg = pgjg == null ? null : pgjg.trim();
    }

    public String getPgrbmid() {
        return pgrbmid;
    }

    public void setPgrbmid(String pgrbmid) {
        this.pgrbmid = pgrbmid == null ? null : pgrbmid.trim();
    }

    public String getPgrid() {
        return pgrid;
    }

    public void setPgrid(String pgrid) {
        this.pgrid = pgrid == null ? null : pgrid.trim();
    }

    public Date getPgsj() {
        return pgsj;
    }

    public void setPgsj(Date pgsj) {
        this.pgsj = pgsj;
    }

    public String getYzjg() {
        return yzjg;
    }

    public void setYzjg(String yzjg) {
        this.yzjg = yzjg == null ? null : yzjg.trim();
    }

    public String getYzrbmid() {
        return yzrbmid;
    }

    public void setYzrbmid(String yzrbmid) {
        this.yzrbmid = yzrbmid == null ? null : yzrbmid.trim();
    }

    public String getYzrid() {
        return yzrid;
    }

    public void setYzrid(String yzrid) {
        this.yzrid = yzrid == null ? null : yzrid.trim();
    }

    public Date getYzsj() {
        return yzsj;
    }

    public void setYzsj(Date yzsj) {
        this.yzsj = yzsj;
    }

    public String getGbsm() {
        return gbsm;
    }

    public void setGbsm(String gbsm) {
        this.gbsm = gbsm == null ? null : gbsm.trim();
    }

    public String getGbrbmid() {
        return gbrbmid;
    }

    public void setGbrbmid(String gbrbmid) {
        this.gbrbmid = gbrbmid == null ? null : gbrbmid.trim();
    }

    public String getGbrid() {
        return gbrid;
    }

    public void setGbrid(String gbrid) {
        this.gbrid = gbrid == null ? null : gbrid.trim();
    }

    public Date getGbsj() {
        return gbsj;
    }

    public void setGbsj(Date gbsj) {
        this.gbsj = gbsj;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

	public AuditDiscovery getAuditDiscovery() {
		return auditDiscovery;
	}

	public void setAuditDiscovery(AuditDiscovery auditDiscovery) {
		this.auditDiscovery = auditDiscovery;
	}

	public User getDetailWhr() {
		return detailWhr;
	}

	public void setDetailWhr(User detailWhr) {
		this.detailWhr = detailWhr;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public List<ProcessRecord> getProcessRecordList() {
		return processRecordList;
	}

	public void setProcessRecordList(List<ProcessRecord> processRecordList) {
		this.processRecordList = processRecordList;
	}

	public User getPgr() {
		return pgr;
	}

	public void setPgr(User pgr) {
		this.pgr = pgr;
	}

	public User getFkr() {
		return fkr;
	}

	public void setFkr(User fkr) {
		this.fkr = fkr;
	}
    
}