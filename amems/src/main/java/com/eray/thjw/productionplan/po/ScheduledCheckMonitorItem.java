package com.eray.thjw.productionplan.po;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.util.DateUtil;
import com.eray.thjw.util.StringAndDate_Util;

import enu.MonitorItemEnum;
import enu.ScheduledEnum;
import enu.TimeOperationEnum;
import enu.TimeSeparatorEnum;

/**
 * b_s_00304 生效区-定检监控项目
 * 
 * @author zhuchao
 * 
 */
@SuppressWarnings("serial")
public class ScheduledCheckMonitorItem extends BizEntity {
	private String id;

	private String mainid;

	private String zjqdid;

	private String fjzch;

	private Integer bjlx;

	private String jh;

	private String xlh;

	private String djxmid;

	private String djbh;

	private String zwms;

	private String jkflbh;

	private String jklbh;

	private Integer pxh;

	private String jkSz;

	private Integer jkDw;

	private Date whsj;

	private Integer zt;

	private String dprtcode;

	private String ms;

	private String jhz;

	private BigDecimal zqz;
	
	private BigDecimal monitorZqz;
	
	private Integer dw;

	private String sj;

	private String jshz; // 虚拟字段，计算后的计划值
	
	private String sy;//虚拟字段，剩余
	
	private String whdwid;
	
	private String whrid;
	
	private Integer tbbs;
	
	private String bjyy;
	
	private Integer bjyyDw;
	
	private String zjhyy;
	
	private Integer zjhyyDw;
	
	private String jklms;
	
	private String rsyl;
	
	private Integer csbj;
	//扩展区域
	//本次定检计划值-此计划值包含了周期值，所以是完整的计划值
	private String curJh;
	
	//当前发生实际值-此实际值包含了飞机初始值，所以是完整的实际值
	private String curSj;
	
	private String syts;
	
	private Boolean ypItem;
	/**
	 * 预排次数
	 */
	private int ypSeq;	
	
	private String jhzxrqFir;
	
	private String jhzxrq;
	
	public Integer getCsbj() {
		return csbj;
	}

	public void setCsbj(Integer csbj) {
		this.csbj = csbj;
	}

	public String getSy() {
		return sy;
	}

	public void setSy(String sy) {
		this.sy = sy;
	}

	public String getJshz() {
		return jshz;
	}

	public void setJshz(String jshz) {
		this.jshz = jshz;
	}

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getMs() {
		return ms;
	}

	public void setMs(String ms) {
		this.ms = ms;
	}

	public String getJhz() {
		return jhz;
	}

	public void setJhz(String jhz) {
		this.jhz = jhz;
	}

	public BigDecimal getZqz() {
		return zqz;
	}

	public void setZqz(BigDecimal zqz) {
		this.zqz = zqz;
	}

	public Integer getDw() {
		return dw;
	}

	public void setDw(Integer dw) {
		this.dw = dw;
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

	public String getDjxmid() {
		return djxmid;
	}

	public void setDjxmid(String djxmid) {
		this.djxmid = djxmid == null ? null : djxmid.trim();
	}

	public String getDjbh() {
		return djbh;
	}

	public void setDjbh(String djbh) {
		this.djbh = djbh == null ? null : djbh.trim();
	}

	public String getZwms() {
		return zwms;
	}

	public void setZwms(String zwms) {
		this.zwms = zwms == null ? null : zwms.trim();
	}

	public String getJkflbh() {
		return jkflbh;
	}

	public void setJkflbh(String jkflbh) {
		this.jkflbh = jkflbh == null ? null : jkflbh.trim();
	}

	public String getJklbh() {
		return jklbh;
	}

	public void setJklbh(String jklbh) {
		this.jklbh = jklbh == null ? null : jklbh.trim();
	}

	public Integer getPxh() {
		return pxh;
	}

	public void setPxh(Integer pxh) {
		this.pxh = pxh;
	}


	public String getJkSz() {
		return jkSz;
	}

	public void setJkSz(String jkSz) {
		this.jkSz = jkSz;
	}

	public Integer getJkDw() {
		return jkDw;
	}

	public void setJkDw(Integer jkDw) {
		this.jkDw = jkDw;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
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

	public Integer getTbbs() {
		return tbbs;
	}

	public void setTbbs(Integer tbbs) {
		this.tbbs = tbbs;
	}

	public String getBjyy() {
		return bjyy;
	}

	public void setBjyy(String bjyy) {
		this.bjyy = bjyy;
	}

	public Integer getBjyyDw() {
		return bjyyDw;
	}

	public void setBjyyDw(Integer bjyyDw) {
		this.bjyyDw = bjyyDw;
	}

	public String getZjhyy() {
		return zjhyy;
	}

	public void setZjhyy(String zjhyy) {
		this.zjhyy = zjhyy;
	}

	public Integer getZjhyyDw() {
		return zjhyyDw;
	}

	public void setZjhyyDw(Integer zjhyyDw) {
		this.zjhyyDw = zjhyyDw;
	}

	public String getJklms() {
		return jklms;
	}

	public void setJklms(String jklms) {
		this.jklms = jklms;
	}

	public String getRsyl() {
		return rsyl;
	}

	public void setRsyl(String rsyl) {
		this.rsyl = rsyl;
	}

	public String getSyts() {
		return syts;
	}

	public void setSyts(String syts) {
		this.syts = syts;
	}

	public String getCurJh() {
		return curJh;
	}

	public void setCurJh(String curJh) {
		this.curJh = curJh;
	}

	public String getCurSj() {
		return curSj;
	}

	public void setCurSj(String curSj) {
		this.curSj = curSj;
	}
	
	/**
	 * 根据当前前实际数据计算下期计划值
	 * @throws ParseException 
	 */
	public void calJhNext() throws ParseException {
		String jh=null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(this.getDw().equals(ScheduledEnum.calendar.getId())){
			Date date = sdf.parse(this.getCurJh());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int b=this.getYpZqz().intValue(); 
			cal.add(Calendar.DAY_OF_MONTH, b);
			date =cal.getTime();
			jh=new SimpleDateFormat("yyyy-MM-dd").format(date);
			
		}else if(this.getDw().equals(ScheduledEnum.DAY.getId())){
			Date date = sdf.parse(this.getCurJh());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int b=this.getYpZqz().intValue(); 
			cal.add(Calendar.DATE, b);
			date =cal.getTime();
			jh=new SimpleDateFormat("yyyy-MM-dd").format(date);
			
		}else if(this.getDw().equals(ScheduledEnum.MOON.getId())){
			Date date = sdf.parse(this.getCurJh());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int b=this.getYpZqz().intValue(); 
			cal.add(Calendar.MONTH, b);
			date =cal.getTime();
			jh=new SimpleDateFormat("yyyy-MM-dd").format(date);
		}else if(this.getDw().equals(ScheduledEnum.YEAR.getId())){
			Date date = sdf.parse(this.getCurJh());
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			int b=this.getYpZqz().intValue();  
			cal.add(Calendar.YEAR, b);
			date =cal.getTime();
			jh=new SimpleDateFormat("yyyy-MM-dd").format(date);
		}else if(this.getDw().equals( ScheduledEnum.TIME.getId())){
			int b=this.getYpZqz().intValue(); 
			jh=StringAndDate_Util.operateTime(this.getCurJh(),String.valueOf(b), TimeSeparatorEnum.COLON, TimeOperationEnum.ADD);
		}else if(this.getDw().equals(ScheduledEnum.LOOP.getId())){
			BigDecimal ss=new BigDecimal(this.getCurJh());
			BigDecimal sss=ss.add( this.getYpZqz());
			jh=sss.toString();
		}
		this.setJshz(jh);
		//更新各个定检项，周期值，剩余天数，日使用量
		this.setCurJh(jh);
		
//		jh=this.getMs()+":"+jh;
	}
	
	/**
	 * 获取周期值
	 * @return
	 */
	private BigDecimal getYpZqz() {
		return this.getMonitorZqz();
	}

	/**
	 * 根据当前前实际数据计算下期剩余值
	 * @throws ParseException 
	 */
	public void calSyNext() throws ParseException {
		this.calJhNext();
		String sy=null;
		if(this.getDw().equals(ScheduledEnum.calendar.getId())){
			sy=DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE,this.getCurSj(),this.getCurJh())+"";
		}else if(this.getDw().equals(ScheduledEnum.DAY.getId())
				||this.getDw().equals(ScheduledEnum.MOON.getId())
				||this.getDw().equals(ScheduledEnum.YEAR.getId())){
			sy=DateUtil.getConsumeDay(DateUtil.DEFAULT_FORMAT_DATE,this.getCurSj(), this.getCurJh())+"";
		}else if(this.getDw().equals(ScheduledEnum.TIME.getId()) ){
			sy=StringAndDate_Util.operateTime(this.getCurJh(),this.getCurSj(), TimeSeparatorEnum.COLON, TimeOperationEnum.SUBTRACT);
		}else if(this.getDw().equals(ScheduledEnum.LOOP.getId())){
			
			//起落循环+需要累加飞机注册表.起落循环
			if(this.getJklbh().equals(MonitorItemEnum.LANDING_GEAR_CYCLE.toString().toLowerCase())){
				DecimalFormat df = new DecimalFormat("0.00");   
				sy=String.valueOf(df.format(Double.parseDouble(this.getCurJh()) -Double.parseDouble(this.getCurSj())));
			}else if(this.getJklbh().equals(MonitorItemEnum.SPECIAL_FIRST.toString().toLowerCase())){
				 
				DecimalFormat df = new DecimalFormat("0.00");   
				sy=df.format(Double.valueOf(this.getCurJh()) -Double.valueOf(this.getCurSj()))+"";
			}else if (this.getJklbh().equals(MonitorItemEnum.SPECIAL_SECOND.toString().toLowerCase())){
				DecimalFormat df = new DecimalFormat("0.00");   
				sy=df.format(Double.valueOf(this.getCurJh())-Double.valueOf(this.getCurSj()))+"";
			}else{
				DecimalFormat df = new DecimalFormat("0.00");  
				sy=df.format(Double.valueOf(this.getCurJh())-Double.valueOf(this.getCurSj()))+"";
			}
		}
		this.setSy(sy);
	}

	/**
	 * 根据当前实际数据计算下期剩余天数
	 * @throws ParseException 
	 */
	/*public void calSytsNext() throws ParseException {
		this.calSyNext();
		 String rsylCur = StringUtils.isEmpty(this.getRsyl())|| this.getRsyl().equals("0")?"1":this.getRsyl();
		 if(this.getJklbh().equals(MonitorItemEnum.CALENDAR.toString().toLowerCase())){
			 this.setSyts(this.getSy());
		 }else if(this.getJklbh().equals(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.toString().toLowerCase())){
			 int syminute = getMiniter(this.getSy());
			 int rsylminute = getMiniter(rsylCur);
			 this.setSyts(String.valueOf(syminute/rsylminute));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.SEARCH_LIGHT_TIME.toString().toLowerCase())){
			 int syminute = getMiniter(this.getSy());
			 int rsylminute = getMiniter(rsylCur);
			 this.setSyts(String.valueOf(syminute/rsylminute));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.WINCH_TIME.toString().toLowerCase())){
			 int syminute = getMiniter(this.getSy());
			 int rsylminute = getMiniter(rsylCur);
			 this.setSyts(String.valueOf(syminute/rsylminute));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.LANDING_GEAR_CYCLE.toString().toLowerCase())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.WINCH_CYCLE.toString().toLowerCase())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.EXT_SUSPENSION_LOOP.toString().toLowerCase())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.SPECIAL_FIRST.toString().toLowerCase())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.SPECIAL_SECOND.toString().toLowerCase())){ 
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
		 }else if(this.getJklbh().equals(MonitorItemEnum.N1.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.N2.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.N3.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.N4.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.N5.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.N6.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
		 }
	}*/
	
	
	 
	/*定检项目第一次计划日期
	     定检项目周期 （天数）= 所有监控项 周期/日使用 取小
                预排循环 
	     第一次计划到截止 累加 定检项目周期(0-1?1:1,向下取整)
     */
	
	/**
	 * 计算剩余天数</br>
	 * 1.日历，(根据单位（年，月，日） 周期*n+计划执行日期)</br>
	 * 2.时间，(剩余天数（格式：（11.33 33:44 ）  周期(换算成分钟)*(n)/(日使用量-换算成分钟))+计划执行日期)</br>
	 * 3.循环，(剩余天数（周期*(n)/(日使用量))+计划执行日期)</br>
	 * 4.2,3先向下取整，结果小于1当1处理</br>
	 * 
	 * @throws ParseException
	 */
	public void calSytsNext() throws ParseException {
		 int syts = 0;
		 String rsylCur = StringUtils.isEmpty(this.getRsyl())|| this.getRsyl().equals("0")?"1":this.getRsyl();
		 //日历类
		 if(MonitorItemEnum.isCalendar(this.getJklbh())){
			 	int calBit = Calendar.DAY_OF_MONTH ;
			    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			    Calendar cal = Calendar.getInstance();
//			    Date start = sdf.parse(this.getJhzxrqFir());
			    Date start = sdf.parse(this.getJhzxrq());
			    Date end = null;
			    cal.setTime(start);
				if(this.getDw().equals(ScheduledEnum.calendar.getId())){
					calBit = Calendar.DAY_OF_MONTH ;
				}else if(this.getDw().equals(ScheduledEnum.DAY.getId())){
					calBit = Calendar.DAY_OF_MONTH ;
				}else if(this.getDw().equals(ScheduledEnum.MOON.getId())){
					calBit = Calendar.MONTH ;
				}else if(this.getDw().equals(ScheduledEnum.YEAR.getId())){
					calBit = Calendar.YEAR ;
				}

//				int amount = this.getMonitorZqz().multiply(BigDecimal.valueOf(ypSeq)).intValue();
				int amount = this.getMonitorZqz().intValue(); 
				cal.add(calBit, amount);
				end =cal.getTime();
				syts = DateUtil.getConsumeDay(start, end);
//				syts = this.getMonitorZqz().intValue();
				
		 }else if(MonitorItemEnum.isTime(this.getJklbh())){
			 //时间类
			int rsylminute = getMiniter(rsylCur);
			syts = this.getMonitorZqz()
					.multiply(BigDecimal.valueOf(60))
//					.multiply(BigDecimal.valueOf(ypSeq))
					.divide(BigDecimal.valueOf(rsylminute), 1, RoundingMode.FLOOR).intValue();
			 
		 }else if(MonitorItemEnum.isLoop(this.getJklbh())){
			 //循环类
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 syts = this.getMonitorZqz()
//					 .multiply(BigDecimal.valueOf(ypSeq))
					 .divide(rsyl, 3, RoundingMode.FLOOR).intValue();
		 }
		this.setSyts(syts < 1 ? "1" : String.valueOf(syts));
	}
	

	/**
	 * 小时：分钟
	 * @param str
	 * @return
	 */
	private static int getMiniter(String str) {
		int ret = 0 ;
		if (StringUtils.isNotBlank(str)) {
			String []arr = null;
			if(str.contains(":")){
				arr = str.split(":");
				ret = arr.length>1?new BigDecimal(arr[0]).intValue()*60+new BigDecimal(arr[1]).intValue()
						:new BigDecimal(arr[0]).intValue()*60;
			}
			else if(str.contains(".")){
				arr = str.split("\\.");
				ret = arr.length>1?new BigDecimal(arr[0]).intValue()*60+new BigDecimal(arr[1]).intValue()
						:new BigDecimal(arr[0]).intValue()*60;
			}
			else{
				ret = new BigDecimal(str).intValue()*60;
			}
		}
		return ret;
	}
	
	public BigDecimal getMonitorZqz() {
		return monitorZqz;
	}

	public void setMonitorZqz(BigDecimal monitorZqz) {
		this.monitorZqz = monitorZqz;
	}

	public Boolean getYpItem() {
		return ypItem;
	}

	public void setYpItem(Boolean ypItem) {
		this.ypItem = ypItem;
	}

	/**
	 * 生成监控项的第n次预判周期值（n*监控周期值/日使用量）
	 * @param n
	 */
	public void genSyts(int n) {
		String rsylCur = StringUtils.isEmpty(this.getRsyl())|| this.getRsyl().equals("0")?"1":this.getRsyl();
		 if(this.getJklbh().equals(MonitorItemEnum.CALENDAR.toString().toLowerCase())){
			 this.setSyts(String.valueOf(this.getMonitorZqz().intValue()));
		 }else if(this.getJklbh().equals(MonitorItemEnum.FUSELAGE_FLIGHT_TIME.toString().toLowerCase())){
			 int syminute = getMiniter(this.getSy());
			 int rsylminute = getMiniter(rsylCur);
			 this.setSyts(String.valueOf(syminute/rsylminute));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.SEARCH_LIGHT_TIME.toString().toLowerCase())){
			 int syminute = getMiniter(this.getSy());
			 int rsylminute = getMiniter(rsylCur);
			 this.setSyts(String.valueOf(syminute/rsylminute));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.WINCH_TIME.toString().toLowerCase())){
			 int syminute = getMiniter(this.getSy());
			 int rsylminute = getMiniter(rsylCur);
			 this.setSyts(String.valueOf(syminute/rsylminute));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.LANDING_GEAR_CYCLE.toString().toLowerCase())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.WINCH_CYCLE.toString().toLowerCase())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.EXT_SUSPENSION_LOOP.toString().toLowerCase())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.SPECIAL_FIRST.toString().toLowerCase())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.SPECIAL_SECOND.toString().toLowerCase())){ 
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
		 }else if(this.getJklbh().equals(MonitorItemEnum.N1.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.N2.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.N3.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.N4.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.N5.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
			 
		 }else if(this.getJklbh().equals(MonitorItemEnum.N6.getName())){
			 BigDecimal sy = new BigDecimal(this.getSy());
			 BigDecimal rsyl = new BigDecimal(rsylCur);
			 this.setSyts(String.valueOf(sy.divide(rsyl, 0, RoundingMode.FLOOR).intValue()));
		 }
		
	}

	public int getYpSeq() {
		return ypSeq;
	}

	public void setYpSeq(int ypSeq) {
		this.ypSeq = ypSeq;
	}

	public String getJhzxrqFir() {
		return jhzxrqFir;
	}

	public void setJhzxrqFir(String jhzxrqFir) {
		this.jhzxrqFir = jhzxrqFir;
	}

	public String getJhzxrq() {
		return jhzxrq;
	}

	public void setJhzxrq(String jhzxrq) {
		this.jhzxrq = jhzxrq;
	}

}