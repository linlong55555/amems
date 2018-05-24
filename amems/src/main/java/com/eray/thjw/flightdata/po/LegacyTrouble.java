package com.eray.thjw.flightdata.po;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.po.WorkOrder;

import enu.LegacyTroubleStatusEnum;
/**
 * 故障保留单
 * @author zhuchao
 *
 */
@SuppressWarnings("serial")
public class LegacyTrouble extends BizEntity{
	 
    private String id;

    private String gzbldh;

    private String fjzch;

    private String fxjldh;

    private String jlbym;

    private String isM;

    private String mel;

    private String zjh;

    private String scBlrid;

    private String scZzh;

    private Date scSqrq;

    private Date scDqrq;

    private String scPzrid;

    private String scNr;

    private String zcBlrid;

    private String zcZzh;

    private Date zcSqrq;

    private Date zcDqrq;

    private String zcPzrid;

    private String zcNr;

    private Date dqrq;

    private String gzms;

    private String bz;

    private String zt;

    private String whdwid;

    private String whrid;

    private Date whsj;

    private String gbrid;

    private Date gbrq;

    private String gbsm;
    
    //保留依据
    private String blyj;
    
    
    //扩展区域
    /**
     * 首次保留人
     */
    private String scBlrxx;
    
    /**
     * 再次保留人
     */
    private String zcBlrxx;
    
    /**
     * 首次批准人
     */
    private String scPzrxx;
    
    /**
     * 再次批准人
     */
    private String zcPzrxx;
    
    
    /**
     * 关闭人信息
     */
    private String gbrxx; 
    
    /**
     * 工单信息
     */
    private String gdxx;
    
    /**
     * 关闭开始日期
     */
    private Date gbrqStart;
    
    /**
     * 关闭结束日期
     */
    private Date gbrqEnd;
    
    /**
     * 申请开始日期
     */
    private Date sqrqStart;
    /**
     * 申请结束日期
     */
    private Date sqrqEnd;
    
    /**
     * 到期开始日期
     */
    private Date dqrqStart;
    
    /**
     * 到期结束日期
     */
    private Date dqrqEnd;
    
    /**
     * 机构代码
     */
    private String dprtName;
    
    /**
     * 能否编辑
     */
    private Boolean canEdit;
    /**
     * 能否关闭-指定结束
     */
    private Boolean canEnd;
    /**
     * 能否作废
     */
    private Boolean canCancel;
    
    /**
     * 能否重新保留
     */
    private Boolean canResave;
    
    /**
     * 能否生成工单
     */
    private Boolean canGenerateOrder;
    
    private List<WorkOrder> gdxxs;
    
    /**
     * 生成工单-类型
     */
    private String gdlx;
    
    private String bgcolor;
    
    private String zjhName;
    
    private User whr;
    
    private String outgdxx;//用于导出excel显示工单信息的虚拟字段
    
    private String ztStr;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getGzbldh() {
        return gzbldh;
    }

    public void setGzbldh(String gzbldh) {
        this.gzbldh = gzbldh == null ? null : gzbldh.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getFxjldh() {
        return fxjldh;
    }

    public void setFxjldh(String fxjldh) {
        this.fxjldh = fxjldh == null ? null : fxjldh.trim();
    }

    public String getJlbym() {
        return jlbym;
    }

    public void setJlbym(String jlbym) {
        this.jlbym = jlbym == null ? null : jlbym.trim();
    }

    public String getIsM() {
        return isM;
    }

    public void setIsM(String isM) {
        this.isM = isM;
    }

    public String getMel() {
        return mel;
    }

    public void setMel(String mel) {
        this.mel = mel == null ? null : mel.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public String getScBlrid() {
        return scBlrid;
    }

    public void setScBlrid(String scBlrid) {
        this.scBlrid = scBlrid == null ? null : scBlrid.trim();
    }

    public String getScZzh() {
        return scZzh;
    }

    public void setScZzh(String scZzh) {
        this.scZzh = scZzh == null ? null : scZzh.trim();
    }

    public Date getScSqrq() {
        return scSqrq;
    }

    public void setScSqrq(Date scSqrq) {
        this.scSqrq = scSqrq;
    }

    public Date getScDqrq() {
        return scDqrq;
    }

    public void setScDqrq(Date scDqrq) {
        this.scDqrq = scDqrq;
    }

    public String getScPzrid() {
        return scPzrid;
    }

    public void setScPzrid(String scPzrid) {
        this.scPzrid = scPzrid == null ? null : scPzrid.trim();
    }

    public String getScNr() {
        return scNr;
    }

    public void setScNr(String scNr) {
        this.scNr = scNr == null ? null : scNr.trim();
    }

    public String getZcBlrid() {
        return zcBlrid;
    }

    public void setZcBlrid(String zcBlrid) {
        this.zcBlrid = zcBlrid == null ? null : zcBlrid.trim();
    }

    public String getZcZzh() {
        return zcZzh;
    }

    public void setZcZzh(String zcZzh) {
        this.zcZzh = zcZzh;
    }

    public Date getZcSqrq() {
        return zcSqrq;
    }

    public void setZcSqrq(Date zcSqrq) {
        this.zcSqrq = zcSqrq;
    }

    public Date getZcDqrq() {
        return zcDqrq;
    }

    public void setZcDqrq(Date zcDqrq) {
        this.zcDqrq = zcDqrq;
    }

    public String getZcPzrid() {
        return zcPzrid;
    }

    public void setZcPzrid(String zcPzrid) {
        this.zcPzrid = zcPzrid == null ? null : zcPzrid.trim();
    }

    public String getZcNr() {
        return zcNr;
    }

    public void setZcNr(String zcNr) {
        this.zcNr = zcNr == null ? null : zcNr.trim();
    }

    public Date getDqrq() {
        return dqrq;
    }

    public void setDqrq(Date dqrq) {
        this.dqrq = dqrq;
    }

    public String getGzms() {
        return gzms;
    }

    public void setGzms(String gzms) {
        this.gzms = gzms == null ? null : gzms.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
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

    public String getGbrid() {
        return gbrid;
    }

    public void setGbrid(String gbrid) {
        this.gbrid = gbrid == null ? null : gbrid.trim();
    }

    public Date getGbrq() {
        return gbrq;
    }

    public void setGbrq(Date gbrq) {
        this.gbrq = gbrq;
    }

    public String getGbsm() {
        return gbsm;
    }

    public void setGbsm(String gbsm) {
        this.gbsm = gbsm == null ? null : gbsm.trim();
    }

	public String getGdxx() {
		return gdxx;
	}

	public void setGdxx(String gdxx) {
		this.gdxx = gdxx;
	}

	public String getGbrxx() {
		return gbrxx;
	}

	public void setGbrxx(String gbrxx) {
		this.gbrxx = gbrxx;
	}

	public String getScBlrxx() {
		return scBlrxx;
	}

	public void setScBlrxx(String scBlrxx) {
		this.scBlrxx = scBlrxx;
	}

	public String getZcBlrxx() {
		return zcBlrxx;
	}

	public void setZcBlrxx(String zcBlrxx) {
		this.zcBlrxx = zcBlrxx;
	}

	public Date getGbrqStart() {
		return gbrqStart;
	}

	public void setGbrqStart(Date gbrqStart) {
		this.gbrqStart = gbrqStart;
	}

	public Date getGbrqEnd() {
		return gbrqEnd;
	}

	public void setGbrqEnd(Date gbrqEnd) {
		this.gbrqEnd = gbrqEnd;
	}

	public Date getSqrqStart() {
		return sqrqStart;
	}

	public void setSqrqStart(Date sqrqStart) {
		this.sqrqStart = sqrqStart;
	}

	public Date getSqrqEnd() {
		return sqrqEnd;
	}

	public void setSqrqEnd(Date sqrqEnd) {
		this.sqrqEnd = sqrqEnd;
	}

	public Date getDqrqStart() {
		return dqrqStart;
	}

	public void setDqrqStart(Date dqrqStart) {
		this.dqrqStart = dqrqStart;
	}

	public Date getDqrqEnd() {
		return dqrqEnd;
	}

	public void setDqrqEnd(Date dqrqEnd) {
		this.dqrqEnd = dqrqEnd;
	}

	public String getDprtName() {
		return dprtName;
	}

	public void setDprtName(String dprtName) {
		this.dprtName = dprtName;
	}

	public Boolean getCanEdit() {
		if (LegacyTroubleStatusEnum.SAVED.getId().toString().equals(this.getZt())) {
			canEdit = Boolean.TRUE;
		}
		else {
			canEdit = Boolean.FALSE;
		}
		return canEdit;
	}

	public void setCanEdit(Boolean canEdit) {
		this.canEdit = canEdit;
	}

	public Boolean getCanEnd() {
		if (LegacyTroubleStatusEnum.COMMITTED.getId().toString().equals(this.getZt())) {
			canEnd = Boolean.TRUE;
		}
		else {
			canEnd = Boolean.FALSE;
		}
		return canEnd;
	}

	public void setCanEnd(Boolean canEnd) {
		this.canEnd = canEnd;
	}

	public Boolean getCanCancel() {
		if (LegacyTroubleStatusEnum.SAVED.getId().toString().equals(this.getZt())) {
			canCancel = Boolean.TRUE;
		}
		else {
			canCancel = Boolean.FALSE;
		}
		return canCancel;
	}

	public void setCanCancel(Boolean canCance) {
		this.canCancel = canCance;
	}

	public Boolean getCanResave() {
		if (LegacyTroubleStatusEnum.COMMITTED.getId().toString().equals(this.getZt())) {
			canResave = Boolean.TRUE;
		}
		else {
			canResave = Boolean.FALSE;
		}
		return canResave;
	}

	public void setCanResave(Boolean canResave) {
		this.canResave = canResave;
	}

	public Boolean getCanGenerateOrder() {
		if ( LegacyTroubleStatusEnum.COMMITTED.getId().toString().equals(this.getZt())) {
			canGenerateOrder = Boolean.TRUE;
		}
		else {
			canGenerateOrder = Boolean.FALSE;
		}
		return canGenerateOrder;
	}

	public void setCanGenerateOrder(Boolean canGenerateOrder) {
		this.canGenerateOrder = canGenerateOrder;
	}

	public List<WorkOrder> getGdxxs() {
			if (StringUtils.isNotBlank(gdxx)) {
				gdxxs = new ArrayList<WorkOrder>();
				String [] gdxxArray = gdxx.split(",");
				for (String gdLine : gdxxArray) {
					WorkOrder workOrder = new WorkOrder();
					String []gcCols = gdLine.split("##");
					 
					if (StringUtils.isNotBlank(gcCols[0])) {
						workOrder.setGdlx(Integer.valueOf(gcCols[0]));
					}
					if (StringUtils.isNotBlank(gcCols[1])) {
						workOrder.setGdlx2(gcCols[1]);
					}
					
					if (StringUtils.isNotBlank(gcCols[2])) {
						workOrder.setGdbh(gcCols[2]);
					}
					
					if (StringUtils.isNotBlank(gcCols[3])) {
						workOrder.setId(gcCols[3]);
					}
					if(gcCols.length>4){
						if (StringUtils.isNotBlank(gcCols[4])) {
							workOrder.setZt(Integer.valueOf(gcCols[4]));
						}
					}
					
					gdxxs.add(workOrder);
				}
			}
		return gdxxs;
	}

	public void setGdxxs(List<WorkOrder> gdxxs) {
		this.gdxxs = gdxxs;
	}

	public String getGdlx() {
		return gdlx;
	}

	public void setGdlx(String gdlx) {
		this.gdlx = gdlx;
	}

	public String getBgcolor() {
		
		return bgcolor;
	}

	public void setBgcolor(String bgcolor) {
		this.bgcolor = bgcolor;
	}

	public String getZjhName() {
		return zjhName;
	}

	public void setZjhName(String zjhName) {
		this.zjhName = zjhName;
	}

	public User getWhr() {
		return whr;
	}

	public void setWhr(User whr) {
		this.whr = whr;
	}

	public String getScPzrxx() {
		return scPzrxx;
	}

	public void setScPzrxx(String scPzrxx) {
		this.scPzrxx = scPzrxx;
	}

	public String getZcPzrxx() {
		return zcPzrxx;
	}

	public void setZcPzrxx(String zcPzrxx) {
		this.zcPzrxx = zcPzrxx;
	}

	public String getOutgdxx() {
		return outgdxx;
	}

	public void setOutgdxx(String outgdxx) {
		this.outgdxx = outgdxx;
	}

	public String getBlyj() {
		return blyj;
	}

	public void setBlyj(String blyj) {
		this.blyj = blyj;
	}

	public String getZtStr() {
		return ztStr;
	}

	public void setZtStr(String ztStr) {
		this.ztStr = ztStr;
	}
	
	
}