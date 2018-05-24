package com.eray.thjw.productionplan.po;

import java.util.Date;
import java.util.List;

import com.eray.thjw.flightdata.po.Attachment;
import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.WOJobEnclosure;
import com.eray.thjw.produce.po.CrewSchedule;

public class PlaneData extends BizEntity{
    
	private String fjzch;               // 飞机注册号

    private String dprtcode;       //组织机构

    private String fjjx;                   //飞机机型

    private String xlh;                   //序列号

    private String bzm;             //备注名

    private Date ccrq;                //出厂日期

    private String jd;                 //基地

    private String jsgzjl;            //机型改装记录

    private String bz;                      //备注
                
    private String bmid;              //部门id

    private String cjrid;              //创建人Id

    private Date cjsj;               //创建时间

    private Integer zt;
    
    private String jdms;			//基地描述
    
	private List<PlaneInitData> initDatas;	//飞机初始化数据
	
	private Integer isTsqk;			//是否有特殊情况
	
	private List<String> dprtcodes;
	
	private String gjdjzh;		//国际登记证号
	
	private String shzh;		//适航证号
	
	private String wxdtxkzh;	//无线电台许可证号
	
	private Date dtzzjkrq;	//电台执照监控日期
	
	private List<CrewSchedule> schedules;	// 飞机排班数据
	
	private List<Attachment> dtzzfjs;	// 电台执照附件
	
	//--适航状态：0适航、正整数表示不适航
	private String shzt;	
	
	//--停场日期
	private Date bshRq;
	
	//--适航日期
	private Date shRq; 
	
	private String syts;
	
	private String syts1;
	
	private String syts2;
	
	private String gjdjzfjid;//国籍登记证附件id
	
	private String shzfjid;//适航证附件id
	
	private String wxdtzzfjid;//无线电台执照附件id
	
	private Date gjdjzjkrq;	//国籍登记证有效期
	
	private Date shzjkrq;	//适航证有效期
	
	private Date wxdtxkzhrq;	//无线电台执照有效期
	
	private Date fxrq;
	
	private String fxsj;
	
	private String  qlxh;
	
	private String ssdsj;
	
	private String jcsj;
	
	private String jcxh;
	
	private String wdgxh;
	
	private String zN1;
	
	private String zN2;
	
	private String zN3;
	
	private String zN4;
	
	private String zN5;
	
	private String zN6;
	
	private String yN1;
	
	private String yN2;
	
	private String yN3;
	
	private String yN4;
	
	private String yN5;
	
	private String yN6;
	
	private String TS1;
	
	private String TS2;
	
    private List<WOJobEnclosure> woJobenclosure;   //附件
    
    private Integer isSync;	// 装机清单是否同步
	
    
    public String getSyts1() {
		return syts1;
	}
	public void setSyts1(String syts1) {
		this.syts1 = syts1;
	}
	public String getSyts2() {
		return syts2;
	}
	public void setSyts2(String syts2) {
		this.syts2 = syts2;
	}
	public void setGjdjzjkrq(Date gjdjzjkrq) {
		this.gjdjzjkrq = gjdjzjkrq;
	}
	public PlaneData() {
		// TODO Auto-generated constructor stub
	}
    public PlaneData(String fjzch,String dprtcode) {
    	this.fjzch = fjzch;
    	this.dprtcode = dprtcode;
	}
    public List<WOJobEnclosure> getWoJobenclosure() {
		return woJobenclosure;
	}

	public void setWoJobenclosure(List<WOJobEnclosure> woJobenclosure) {
		this.woJobenclosure = woJobenclosure;
	}
    
	public Date getFxrq() {
		return fxrq;
	}

	public void setFxrq(Date fxrq) {
		this.fxrq = fxrq;
	}

	public String getFxsj() {
		return fxsj;
	}

	public void setFxsj(String fxsj) {
		this.fxsj = fxsj;
	}
	
	//国籍登记证附件id
	
	public void setGjdjzfjid(String gjdjzfjid) {
		this.gjdjzfjid = gjdjzfjid;
	}
	
	public String getGjdjzfjid() {
		return gjdjzfjid;
	}
	
	//适航证附件id
	
	public void setShzfjid(String shzfjid) {
		this.shzfjid = shzfjid;
	}
	
	public String getShzfjid() {
		return shzfjid;
	}
	
	//无线电台执照附件id
	
	public void setWxdtzzfjid(String wxdtzzfjid) {
		this.wxdtzzfjid = wxdtzzfjid;
	}
	
	public String getWxdtzzfjid() {
		return wxdtzzfjid;
	}
	
	//国籍登记证有效期
	
	public void setGjdjzhrq(Date gjdjzjkrq) {
		this.gjdjzjkrq = gjdjzjkrq;
	}
	
	public Date getGjdjzjkrq() {
		return gjdjzjkrq;
	}
	
	//适航证有效期
	
	public void setShzjkrq(Date shzjkrq) {
		this.shzjkrq = shzjkrq;
	}
	
	public Date getShzjkrq() {
		return shzjkrq;
	}
	
	//无线电台执照有效期
	
	public void setWxdtxkzhrq(Date wxdtxkzhrq) {
		this.wxdtxkzhrq = wxdtxkzhrq;
	}
	
	public Date getWxdtxkzhrq() {
		return wxdtxkzhrq;
	}
	
	public String getQlxh() {
		return qlxh;
	}

	public void setQlxh(String qlxh) {
		this.qlxh = qlxh;
	}

	public String getSsdsj() {
		return ssdsj;
	}

	public void setSsdsj(String ssdsj) {
		this.ssdsj = ssdsj;
	}

	public String getJcsj() {
		return jcsj;
	}

	public void setJcsj(String jcsj) {
		this.jcsj = jcsj;
	}

	public String getJcxh() {
		return jcxh;
	}

	public void setJcxh(String jcxh) {
		this.jcxh = jcxh;
	}

	public String getWdgxh() {
		return wdgxh;
	}

	public void setWdgxh(String wdgxh) {
		this.wdgxh = wdgxh;
	}

	public String getzN1() {
		return zN1;
	}

	public void setzN1(String zN1) {
		this.zN1 = zN1;
	}

	public String getzN2() {
		return zN2;
	}

	public void setzN2(String zN2) {
		this.zN2 = zN2;
	}

	public String getzN3() {
		return zN3;
	}

	public void setzN3(String zN3) {
		this.zN3 = zN3;
	}

	public String getzN4() {
		return zN4;
	}

	public void setzN4(String zN4) {
		this.zN4 = zN4;
	}

	public String getzN5() {
		return zN5;
	}

	public void setzN5(String zN5) {
		this.zN5 = zN5;
	}

	public String getzN6() {
		return zN6;
	}

	public void setzN6(String zN6) {
		this.zN6 = zN6;
	}

	public String getyN1() {
		return yN1;
	}

	public void setyN1(String yN1) {
		this.yN1 = yN1;
	}

	public String getyN2() {
		return yN2;
	}

	public void setyN2(String yN2) {
		this.yN2 = yN2;
	}

	public String getyN3() {
		return yN3;
	}

	public void setyN3(String yN3) {
		this.yN3 = yN3;
	}

	public String getyN4() {
		return yN4;
	}

	public void setyN4(String yN4) {
		this.yN4 = yN4;
	}

	public String getyN5() {
		return yN5;
	}

	public void setyN5(String yN5) {
		this.yN5 = yN5;
	}

	public String getyN6() {
		return yN6;
	}

	public void setyN6(String yN6) {
		this.yN6 = yN6;
	}

	public String getTS1() {
		return TS1;
	}

	public void setTS1(String tS1) {
		TS1 = tS1;
	}

	public String getTS2() {
		return TS2;
	}

	public void setTS2(String tS2) {
		TS2 = tS2;
	}

	public String getSyts() {
		return syts;
	}

	public void setSyts(String syts) {
		this.syts = syts;
	}

	public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getFjjx() {
        return fjjx;
    }

    public void setFjjx(String fjjx) {
        this.fjjx = fjjx == null ? null : fjjx.trim();
    }

    public String getXlh() {
        return xlh;
    }

    public void setXlh(String xlh) {
        this.xlh = xlh == null ? null : xlh.trim();
    }

    public String getBzm() {
        return bzm;
    }

    public void setBzm(String bzm) {
        this.bzm = bzm == null ? null : bzm.trim();
    }

    public Date getCcrq() {
        return ccrq;
    }

    public void setCcrq(Date ccrq) {
        this.ccrq = ccrq;
    }

    public String getJd() {
        return jd;
    }

    public void setJd(String jd) {
        this.jd = jd == null ? null : jd.trim();
    }

    public String getJsgzjl() {
        return jsgzjl;
    }

    public void setJsgzjl(String jsgzjl) {
        this.jsgzjl = jsgzjl == null ? null : jsgzjl.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
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

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

	public List<PlaneInitData> getInitDatas() {
		return initDatas;
	}

	public void setInitDatas(List<PlaneInitData> initDatas) {
		this.initDatas = initDatas;
	}

	public String getJdms() {
		return jdms;
	}

	public void setJdms(String jdms) {
		this.jdms = jdms;
	}

	public Integer getIsTsqk() {
		return isTsqk;
	}

	public void setIsTsqk(Integer isTsqk) {
		this.isTsqk = isTsqk;
	}

	public List<String> getDprtcodes() {
		return dprtcodes;
	}

	public void setDprtcodes(List<String> dprtcodes) {
		this.dprtcodes = dprtcodes;
	}

	public String getGjdjzh() {
		return gjdjzh;
	}

	public void setGjdjzh(String gjdjzh) {
		this.gjdjzh = gjdjzh;
	}

	public String getShzh() {
		return shzh;
	}

	public void setShzh(String shzh) {
		this.shzh = shzh;
	}

	public String getWxdtxkzh() {
		return wxdtxkzh;
	}

	public void setWxdtxkzh(String wxdtxkzh) {
		this.wxdtxkzh = wxdtxkzh;
	}

	public String getShzt() {
		return shzt;
	}

	public void setShzt(String shzt) {
		this.shzt = shzt;
	}

	public Date getBshRq() {
		return bshRq;
	}

	public void setBshRq(Date bshRq) {
		this.bshRq = bshRq;
	}

	public Date getShRq() {
		return shRq;
	}

	public void setShRq(Date shRq) {
		this.shRq = shRq;
	}

	public Date getDtzzjkrq() {
		return dtzzjkrq;
	}

	public void setDtzzjkrq(Date dtzzjkrq) {
		this.dtzzjkrq = dtzzjkrq;
	}

	public List<CrewSchedule> getSchedules() {
		return schedules;
	}

	public void setSchedules(List<CrewSchedule> schedules) {
		this.schedules = schedules;
	}

	public List<Attachment> getDtzzfjs() {
		return dtzzfjs;
	}

	public void setDtzzfjs(List<Attachment> dtzzfjs) {
		this.dtzzfjs = dtzzfjs;
	}
	public Integer getIsSync() {
		return isSync;
	}
	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}
	
}