package com.eray.thjw.flightdata.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;
import com.eray.thjw.productionplan.po.LoadingList;

/**
 * b_s_006 飞行记录单
 * @author hanwu
 *
 */
public class FlightRecordSheet extends BizEntity{
    private String id;

    private String fxjldh;

    private String fjzch;

    private String jlbym;

    private Date fxrq;
    
    private Date fxrqBegin;
    
    private Date fxrqEnd;

    private String zddwid;

    private String zdrid;

    private Date zdsj;
    
    private String xdrid;

    private Date xdsj;

    private Integer zt;

    private String dprtcode;

    private String bz;
    
    private User zjr;
    
    private User xdr;
    
    private List<FlightRecord> flightData;	// 飞行数据详情
    
    private List<String> dprtcodes;
    
    private List<FlightRecordSheetToPlan> finishedWork;	// 完成工作
    
    private List<InspectionRecord> jcr;	// 航间检查记录
    
    private Integer tbbs;	// 同步标识 
    
    private String zdjsyy;	// 指定结束原因
    
    private String valid;
    
    private Integer operation;	//操作  新增或修改
    
    private Integer isXdtx;	// 是否修订提醒
    
    private String hbh;		// 航班号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getFxjldh() {
        return fxjldh;
    }

    public void setFxjldh(String fxjldh) {
        this.fxjldh = fxjldh == null ? null : fxjldh.trim();
    }

    public String getFjzch() {
        return fjzch;
    }

    public void setFjzch(String fjzch) {
        this.fjzch = fjzch == null ? null : fjzch.trim();
    }

    public String getJlbym() {
        return jlbym;
    }

    public void setJlbym(String jlbym) {
        this.jlbym = jlbym == null ? null : jlbym.trim();
    }

    public Date getFxrq() {
        return fxrq;
    }

    public void setFxrq(Date fxrq) {
        this.fxrq = fxrq;
    }

    public String getZddwid() {
        return zddwid;
    }

    public void setZddwid(String zddwid) {
        this.zddwid = zddwid == null ? null : zddwid.trim();
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

    public String getDprtcode() {
        return dprtcode;
    }

    public void setDprtcode(String dprtcode) {
        this.dprtcode = dprtcode == null ? null : dprtcode.trim();
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz == null ? null : bz.trim();
    }

	public String getXdrid() {
		return xdrid;
	}

	public void setXdrid(String xdrid) {
		this.xdrid = xdrid;
	}

	public Date getXdsj() {
		return xdsj;
	}

	public void setXdsj(Date xdsj) {
		this.xdsj = xdsj;
	}

	public User getZjr() {
		return zjr;
	}

	public void setZjr(User zjr) {
		this.zjr = zjr;
	}

	public User getXdr() {
		return xdr;
	}

	public void setXdr(User xdr) {
		this.xdr = xdr;
	}

	public Date getFxrqBegin() {
		return fxrqBegin;
	}

	public void setFxrqBegin(Date fxrqBegin) {
		this.fxrqBegin = fxrqBegin;
	}

	public Date getFxrqEnd() {
		return fxrqEnd;
	}

	public void setFxrqEnd(Date fxrqEnd) {
		this.fxrqEnd = fxrqEnd;
	}

	public List<String> getDprtcodes() {
		return dprtcodes;
	}

	public void setDprtcodes(List<String> dprtcodes) {
		this.dprtcodes = dprtcodes;
	}

	public List<FlightRecord> getFlightData() {
		return flightData;
	}

	public void setFlightData(List<FlightRecord> flightData) {
		this.flightData = flightData;
	}

	public List<FlightRecordSheetToPlan> getFinishedWork() {
		return finishedWork;
	}

	public void setFinishedWork(List<FlightRecordSheetToPlan> finishedWork) {
		this.finishedWork = finishedWork;
	}

	public List<InspectionRecord> getJcr() {
		return jcr;
	}

	public void setJcr(List<InspectionRecord> jcr) {
		this.jcr = jcr;
	}

	public Integer getTbbs() {
		return tbbs;
	}

	public void setTbbs(Integer tbbs) {
		this.tbbs = tbbs;
	}

	public String getZdjsyy() {
		return zdjsyy;
	}

	public void setZdjsyy(String zdjsyy) {
		this.zdjsyy = zdjsyy;
	}

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}
	
	public Integer getOperation() {
		return operation;
	}

	public void setOperation(Integer operation) {
		this.operation = operation;
	}

	public Integer getIsXdtx() {
		return isXdtx;
	}

	public void setIsXdtx(Integer isXdtx) {
		this.isXdtx = isXdtx;
	}

	public String getHbh() {
		return hbh;
	}

	public void setHbh(String hbh) {
		this.hbh = hbh;
	}

	public static List<FlightRecordSheet> geJaspersoftBean(){
		List<FlightRecordSheet> list = new ArrayList<FlightRecordSheet>();
		FlightRecordSheet bean = new FlightRecordSheet();
		bean.setFjzch("PLANE01");
		bean.setJlbym("001");
		bean.setFxrq(new Date());
		bean.setFxjldh("FLB-7345-00005");
		// 飞行数据
		List<FlightRecord> flightDatas = new ArrayList<FlightRecord>();
		FlightRecord rec1 = new FlightRecord();
		rec1.setHc(2);
		rec1.setFxsj("1");
		rec1.setF1SjN16Z("2");
		rec1.setF2SjN16Z("3");
		rec1.setQljxh(new BigDecimal(4));
		rec1.setJcsj("5");
		rec1.setJcxh(new BigDecimal(6));
		rec1.setYlFxq(new BigDecimal(7));
		rec1.setYlFxh(new BigDecimal(8));
		rec1.setF1N1(new BigDecimal(9));
		rec1.setF1N2(new BigDecimal(10));
		rec1.setF1N3(new BigDecimal(11));
		rec1.setF1N4(new BigDecimal(12));
		rec1.setF1N5(new BigDecimal(13));
		rec1.setF1N6(new BigDecimal(14));
		rec1.setF1Hy(new BigDecimal(15));
		rec1.setF1Wdyd(new BigDecimal(16));
		rec1.setF1Glyd(new BigDecimal(17));
		rec1.setF2N1(new BigDecimal(18));
		rec1.setF2N2(new BigDecimal(19));
		rec1.setF2N3(new BigDecimal(20));
		rec1.setF2N4(new BigDecimal(21));
		rec1.setF2N5(new BigDecimal(22));
		rec1.setF2N6(new BigDecimal(23));
		rec1.setF2Hy(new BigDecimal(24));
		rec1.setF2Wdyd(new BigDecimal(25));
		rec1.setF2Glyd(new BigDecimal(26));
		rec1.setSsdsj("27");
		rec1.setDgxh(new BigDecimal(28));
		rec1.setTs1(new BigDecimal(29));
		rec1.setTs2(new BigDecimal(30));
		rec1.setTsqk("e3611870-c385-4e67-84c2-2b5acda921c8");
		rec1.setMgb(new BigDecimal(31));
		rec1.setIgb(new BigDecimal(32));
		rec1.setTgb(new BigDecimal(33));
		rec1.setAvFxr("AvFxr");
		rec1.setAvZzh("AvZzh");
		rec1.setMeFxr("MeFxr");
		rec1.setMeZzh("MeZzh");
		rec1.setJzshrk("同意");
		flightDatas.add(rec1);
		bean.setFlightData(flightDatas);
		
		// 检查人
		List<InspectionRecord> jcr = new ArrayList<InspectionRecord>();
		InspectionRecord jcr1 = new InspectionRecord();
		jcr1.setJcr("hanwu");
		jcr1.setHd(1);
		jcr.add(jcr1);
		InspectionRecord jcr2 = new InspectionRecord();
		jcr2.setJcr("admin");
		jcr2.setHd(3);
		jcr.add(jcr2);
		InspectionRecord jcr3 = new InspectionRecord();
		jcr3.setJcr("admin");
		jcr3.setHd(5);
		jcr.add(jcr3);
		InspectionRecord jcr4 = new InspectionRecord();
		jcr4.setJcr("admin");
		jcr4.setHd(7);
		jcr.add(jcr4);
		InspectionRecord jcr5 = new InspectionRecord();
		jcr5.setJcr("admin");
		jcr5.setHd(9);
		jcr.add(jcr5);
		bean.setJcr(jcr);
		
		// 完成工作
		List<FlightRecordSheetToPlan> finishedWork = new ArrayList<FlightRecordSheetToPlan>();
		FlightRecordSheetToPlan work1 = new FlightRecordSheetToPlan();
		work1.setRwlx(2);
		work1.setRwzlx(2);
		work1.setRwdh("NRC-2017-002");
		work1.setRwxx("rwxx-------test-!@#$%^&*(");
		work1.setHd(3);
		work1.setWhnr("维护内容维护内容维护内容维护内容维护内容维护内容维护内容维护内容维护内容");
		work1.setSjgsXss(new BigDecimal(6));
		work1.setSjgsRs(new BigDecimal(7));
		work1.setGzxmbldh("987654321");
		User zrr = new User();
		zrr.setUsername("hanwu");
		zrr.setRealname("韩武");
		work1.setZrr(zrr);
		
		// 拆解记录
		List<FlightRecordSheetToDisassembly> dismountRecord = new ArrayList<FlightRecordSheetToDisassembly>();
		FlightRecordSheetToDisassembly dismount = new FlightRecordSheetToDisassembly();
		LoadingList off = new LoadingList();
		off.setJh("LINGJ001");
		off.setXlh("XLH001");
		dismount.setOff(off);
		dismountRecord.add(dismount);
		work1.setDismountRecord(dismountRecord);
		finishedWork.add(work1);
		bean.setFinishedWork(finishedWork);
		list.add(bean);
		return list;
	}
}