package com.eray.thjw.productionplan.po;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.po.BizEntity;

/**
 * 飞机装机清单
 * @author hanwu
 *
 */
public class LoadingList extends BizEntity{
    private String id;                                        //基础id

    private String fjzch;                                 //飞机注册号

    private Integer bjlx;                                   //部件类型         

    private String jh;                                   //件号

    private String xlh;								 //序列号
    
    private String nbsbm;								 //内部识别码

    private String zjh;                            //章节号

    private String cjjh;                             //厂家件号

    private String zwmc;                       //中文名称

    private String ywmc;                     //英文名称

    private String bjgzjl;                       //部件改装记录

    private Integer zjsl;                         //备注装机数量

    private Integer wz;                           //位置

    private Integer zt;                      //状态

    private Date scrq;                    //生产日期

    private String bz;                      //备注

    private Date azrq;                    //安装日期

    private String azjldh;                  //安装记录单号

    private Date ccrq;                          //拆除日期

    private String ccjldh;                     //拆除记录单号

    private Integer llklx;                         //履历类型

    private String llkbh;                      //履历编号

    private Integer kzlx;                         //控制类型

    private Integer isDj;                          //是否定检

    private String tsn;                        //TSN

    private String tso;                              //TSO

    private Integer cj;                          //层级

    private String fjdid;                        //父节点

    private Date sxrq;                       //生效日期

    private Integer sxzt;                      //生效状态

    private Date whsj;                      //维护时间

    private String dprtcode;               //机构代码
    
    private Integer tbbs;					//同步标识
    
    private String whdwid;					//维护单位id
    
    private String whrid;					//维护人id
    
    private String pId;						//父节点id
    
    private String name;					//名称
    
    private String fjjx;					//飞机机型
    
    private String kbs;						//块标识
    
    private String zjhms;					//章节号描述
    
    private Date beginScrq;					//开始生产日期
    
    private Date endScrq;					//结束生产日期
    
    private Date beginAzrq;					//开始安装日期
    
    private Date endAzrq;					//结束安装日期
    
    private Date beginCcrq;					//开始出厂日期
    
    private Date endCcrq;					//结束出厂日期
    
    private String notId;					//id不等于
    
    private Integer notCj;					//层级不等于
    
    private List<LoadingListToSpecialCondition> conditions;	//特殊飞行情况
    
    private List<TimeMonitorSetting> settings;			//时控件设置
    
    private Integer timeMonitorFlag;		//时控件设置标志
    
    private Integer fixMonitorFlag;			//定检件设置标志
    
    private String gdid; //工单ID
    
    private String rwid; //任务id	
    
    private String jkbz;//监控备注
    
    private String xkzh;	//试航证号
    
    private String shzh;	//许可证号
    
    private String pch;		//批次号
    
    private List<String> ids;
    
    private List<String> notIds;
    
    private Integer isSync;	// 是否已经同步生效区
    
    private String fxjldid;
    
    private String xgid;	// 相关id
    
    private String displayName;	// 显示名称
    
    private LoadingList parent; // 父节点
    
    private String bjid;	//部件id
    
    public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
	}

	public LoadingList() {
		super();
	}

	public LoadingList(String id, String fjzch) {
		super();
		this.id = id;
		this.fjzch = fjzch;
	}

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

	public String getRwid() {
		return rwid;
	}

	public void setRwid(String rwid) {
		this.rwid = rwid;
	}

	public String getJkbz() {
		return jkbz;
	}

	public void setJkbz(String jkbz) {
		this.jkbz = jkbz;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public Integer getBjlx() {
        return bjlx;
    }

    public void setBjlx(Integer bjlx) {
        this.bjlx = bjlx;
    }

    public String getJh() {
        return jh;
    }

    public void setJh(String jh) {
        this.jh = jh == null ? null : jh.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public String getZjh() {
        return zjh;
    }

    public void setZjh(String zjh) {
        this.zjh = zjh == null ? null : zjh.trim();
    }

    public String getCjjh() {
        return cjjh;
    }

    public void setCjjh(String cjjh) {
        this.cjjh = cjjh == null ? null : cjjh.trim();
    }

    public String getZwmc() {
        return zwmc;
    }

    public void setZwmc(String zwmc) {
        this.zwmc = zwmc == null ? null : zwmc.trim();
    }

    public String getYwmc() {
        return ywmc;
    }

    public void setYwmc(String ywmc) {
        this.ywmc = ywmc == null ? null : ywmc.trim();
    }

    public String getBjgzjl() {
        return bjgzjl;
    }

    public void setBjgzjl(String bjgzjl) {
        this.bjgzjl = bjgzjl == null ? null : bjgzjl.trim();
    }

    public Integer getZjsl() {
        return zjsl;
    }

    public void setZjsl(Integer zjsl) {
        this.zjsl = zjsl;
    }

    public Integer getWz() {
        return wz;
    }

    public void setWz(Integer wz) {
        this.wz = wz;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public Date getScrq() {
        return scrq;
    }

    public void setScrq(Date scrq) {
        this.scrq = scrq;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

    public Date getAzrq() {
        return azrq;
    }

    public void setAzrq(Date azrq) {
        this.azrq = azrq;
    }

    public String getAzjldh() {
        return azjldh;
    }

    public void setAzjldh(String azjldh) {
        this.azjldh = azjldh == null ? null : azjldh.trim();
    }

    public Date getCcrq() {
        return ccrq;
    }

    public void setCcrq(Date ccrq) {
        this.ccrq = ccrq;
    }

    public String getCcjldh() {
        return ccjldh;
    }

    public void setCcjldh(String ccjldh) {
        this.ccjldh = ccjldh == null ? null : ccjldh.trim();
    }

    public Integer getLlklx() {
        return llklx;
    }

    public void setLlklx(Integer llklx) {
        this.llklx = llklx;
    }

    public String getLlkbh() {
        return llkbh;
    }

    public void setLlkbh(String llkbh) {
        this.llkbh = llkbh == null ? null : llkbh.trim();
    }

    public Integer getKzlx() {
        return kzlx;
    }

    public void setKzlx(Integer kzlx) {
        this.kzlx = kzlx;
    }

    public Integer getIsDj() {
        return isDj;
    }

    public void setIsDj(Integer isDj) {
        this.isDj = isDj;
    }

    public String getTsn() {
        return tsn;
    }

    public void setTsn(String tsn) {
        this.tsn = tsn == null ? null : tsn.trim();
    }

    public String getTso() {
        return tso;
    }

    public void setTso(String tso) {
        this.tso = tso == null ? null : tso.trim();
    }

    public Integer getCj() {
        return cj;
    }

    public void setCj(Integer cj) {
        this.cj = cj;
    }

    public String getFjdid() {
        return fjdid;
    }

    public void setFjdid(String fjdid) {
        this.fjdid = fjdid;
    }

    public Date getSxrq() {
        return sxrq;
    }

    public void setSxrq(Date sxrq) {
        this.sxrq = sxrq;
    }

    public Integer getSxzt() {
        return sxzt;
    }

    public void setSxzt(Integer sxzt) {
        this.sxzt = sxzt;
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

	public String getNbsbm() {
		return nbsbm;
	}

	public void setNbsbm(String nbsbm) {
		this.nbsbm = nbsbm;
	}

	public Integer getTbbs() {
		return tbbs;
	}

	public void setTbbs(Integer tbbs) {
		this.tbbs = tbbs;
	}

	public String getWhdwid() {
		return whdwid;
	}

	public void setWhdwid(String whdwid) {
		this.whdwid = whdwid;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public String getpId() {
		return pId;
	}

	public void setpId(String pId) {
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFjjx() {
		return fjjx;
	}

	public void setFjjx(String fjjx) {
		this.fjjx = fjjx;
	}

	public String getKbs() {
		return kbs;
	}

	public void setKbs(String kbs) {
		this.kbs = kbs;
	}

	public String getZjhms() {
		return zjhms;
	}

	public void setZjhms(String zjhms) {
		this.zjhms = zjhms;
	}

	public Date getBeginScrq() {
		return beginScrq;
	}

	public void setBeginScrq(Date beginScrq) {
		this.beginScrq = beginScrq;
	}

	public Date getEndScrq() {
		return endScrq;
	}

	public void setEndScrq(Date endScrq) {
		this.endScrq = endScrq;
	}

	public Date getBeginAzrq() {
		return beginAzrq;
	}

	public void setBeginAzrq(Date beginAzrq) {
		this.beginAzrq = beginAzrq;
	}

	public Date getEndAzrq() {
		return endAzrq;
	}

	public void setEndAzrq(Date endAzrq) {
		this.endAzrq = endAzrq;
	}

	public Date getBeginCcrq() {
		return beginCcrq;
	}

	public void setBeginCcrq(Date beginCcrq) {
		this.beginCcrq = beginCcrq;
	}

	public Date getEndCcrq() {
		return endCcrq;
	}

	public void setEndCcrq(Date endCcrq) {
		this.endCcrq = endCcrq;
	}

	public String getNotId() {
		return notId;
	}

	public void setNotId(String notId) {
		this.notId = notId;
	}

	public List<LoadingListToSpecialCondition> getConditions() {
		return conditions;
	}

	public void setConditions(List<LoadingListToSpecialCondition> conditions) {
		this.conditions = conditions;
	}

	public List<TimeMonitorSetting> getSettings() {
		return settings;
	}

	public void setSettings(List<TimeMonitorSetting> settings) {
		this.settings = settings;
	}

	public Integer getTimeMonitorFlag() {
		return timeMonitorFlag;
	}

	public void setTimeMonitorFlag(Integer timeMonitorFlag) {
		this.timeMonitorFlag = timeMonitorFlag;
	}

	public Integer getFixMonitorFlag() {
		return fixMonitorFlag;
	}

	public void setFixMonitorFlag(Integer fixMonitorFlag) {
		this.fixMonitorFlag = fixMonitorFlag;
	}

	public String getXkzh() {
		return xkzh;
	}

	public void setXkzh(String xkzh) {
		this.xkzh = xkzh;
	}

	public String getShzh() {
		return shzh;
	}

	public void setShzh(String shzh) {
		this.shzh = shzh;
	}

	public String getPch() {
		return pch;
	}

	public void setPch(String pch) {
		this.pch = pch;
	}

	public Integer getNotCj() {
		return notCj;
	}

	public void setNotCj(Integer notCj) {
		this.notCj = notCj;
	}

	public List<String> getNotIds() {
		return notIds;
	}

	public void setNotIds(List<String> notIds) {
		this.notIds = notIds;
	}

	public List<String> getIds() {
		return ids;
	}

	public void setIds(List<String> ids) {
		this.ids = ids;
	}

	public Integer getIsSync() {
		return isSync;
	}

	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}

	public String getFxjldid() {
		return fxjldid;
	}

	public void setFxjldid(String fxjldid) {
		this.fxjldid = fxjldid;
	}

	public String getXgid() {
		return xgid;
	}

	public void setXgid(String xgid) {
		this.xgid = xgid;
	}

	public LoadingList getParent() {
		return parent;
	}

	public void setParent(LoadingList parent) {
		this.parent = parent;
	}

	public String getDisplayName() {
		/*String firstName = null;
		String lastName = null;
		if (!Utils.Str.isEmpty(getYwmc())) {
			firstName = getYwmc();
		} else if (!Utils.Str.isEmpty(getJh())) {
			firstName = getJh();
		}

		if (!Utils.Str.isEmpty(getXlh())) {
			lastName = getXlh();
		} else if (!Utils.Str.isEmpty(getNbsbm())) {
			lastName = getNbsbm();
		}
		if (!Utils.Str.isEmpty(firstName) && !Utils.Str.isEmpty(lastName)) {
			displayName = firstName + "-" + lastName;
		} else if (!Utils.Str.isEmpty(firstName)) {
			displayName = firstName;
		} else if (!Utils.Str.isEmpty(lastName)) {
			displayName = lastName;
		}*/
		String jh = StringUtils.isNotBlank(getJh()) ? getJh() : " ";
		String xlh = StringUtils.isNotBlank(getXlh()) ? getXlh() : " ";
		String ywmc = StringUtils.isNotBlank(getYwmc()) ? getYwmc() : " ";
		String zwmc = StringUtils.isNotBlank(getZwmc()) ? getZwmc() : " ";
		String zjh = StringUtils.isNotBlank(getZjh()) ? getZjh() : " ";
		String zjhms = StringUtils.isNotBlank(getZjhms()) ? getZjhms() : " ";
		if(getCj() != null && getCj() == 0){
			displayName = jh + " " + xlh;
		}else{
			displayName = zjh+ " " + zjhms + " " + jh + " " + ywmc + " " + zwmc + " " +  xlh;
		}
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}