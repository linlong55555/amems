package com.eray.thjw.productionplan.po;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.util.DateUtil;

import enu.ScheduleMonitorModelEnum;

/**
 * b_s_00303 生效区-定检件定检项目
 * 
 * @author zhuchao
 * 
 */
@SuppressWarnings("serial")
public class ScheduledCheckItem extends BizEntity {
	private String id;

	private String zjqdid;

	private String fjzch;

	private Integer bjlx;

	private String jh;

	private String xlh;

	private String djxmid;

	private String djbh;

	private String zwms;

	private Integer zt;

	private Date whsj;

	private Integer zyxs;

	private String zwmc; // 部件中文描述

	private String ywmc;// 部件英文描述

	private String jhc;// 计划

	private String sj;// 实际

	private String sy;// 剩余

	private String syts;// 剩余天数

	private String wz;

	private String rwdh;
	

	private String whsj1;

	private String whrid1;

	private String jhrw;

	private String jkbz;
	
	private Integer tbbs;
	
	private String nbsbm;
	
	private String whrid;
	
	private String whdwid;
	
	private BigDecimal bb;
	
	private String rwid;
	
	private List<ScheduledCheckMonitorItem> monitorItemList;
	
	private String keyword;//关键字
	
	private String fjXlh;       //飞机序列号 工单
	
	private String zfXlh;
	
	private String yfXlh;
	
	private String xggdid;
	
	private Boolean ypItem;
	
	private Boolean notCal;
	
	private String wxfabh;	//维修方案编号
	
	private int ypSeq;	//预排次数
	
	private String bjid; 
	
	public String getXggdid() {
		return xggdid;
	}
	public void setXggdid(String xggdid) {
		this.xggdid = xggdid;
	}
	public String getFjXlh() {
		return fjXlh;
	}
	public void setFjXlh(String fjXlh) {
		this.fjXlh = fjXlh;
	}
	public String getZfXlh() {
		return zfXlh;
	}
	public void setZfXlh(String zfXlh) {
		this.zfXlh = zfXlh;
	}
	public String getYfXlh() {
		return yfXlh;
	}
	public void setYfXlh(String yfXlh) {
		this.yfXlh = yfXlh;
	}

	//扩展字段
	private ScheduleMonitorModelEnum jkfs;
	
	private String rsyl;
	
	//截至日期
	private Date jzrq;
	
	private String jhzxrq;
	
	private String jhzxrqFir;
	
	private BigDecimal zqz;
	
	private String cursj;
	
	private String curjh;
	
	private Map<String,ScheduledCheckMonitorItem> scheduledCheckMonitorMap;
	 
	
	public ScheduledCheckItem() {
	}
	public ScheduledCheckItem(String syts, String rsyl,BigDecimal zqz,String cursj,String curjh) {
		 this.syts = syts;
		 this.rsyl = rsyl;
		 this.zqz = zqz;
		 this.cursj = cursj;
		 this.curjh = curjh;
	}

	private List<ScheduledCheckItem> scheduledCheckItems;//定检集合

	public List<ScheduledCheckItem> getScheduledCheckItems() {
		return scheduledCheckItems;
	}

	public void setScheduledCheckItems(List<ScheduledCheckItem> scheduledCheckItems) {
		this.scheduledCheckItems = scheduledCheckItems;
	}

	
	public String getRwid() {
		return rwid;
	}

	public void setRwid(String rwid) {
		this.rwid = rwid;
	}

	public Integer getZyxs() {
		return zyxs;
	}

	public void setZyxs(Integer zyxs) {
		this.zyxs = zyxs;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getJkbz() {
		return jkbz;
	}

	public void setJkbz(String jkbz) {
		this.jkbz = jkbz;
	}

	public String getJhrw() {
		return jhrw;
	}

	public void setJhrw(String jhrw) {
		this.jhrw = jhrw;
	}

	public String getRwdh() {
		return rwdh;
	}

	public void setRwdh(String rwdh) {
		this.rwdh = rwdh;
	}

	public String getWhsj1() {
		return whsj1;
	}

	public void setWhsj1(String whsj1) {
		this.whsj1 = whsj1;
	}

	public String getWhrid1() {
		return whrid1;
	}

	public void setWhrid1(String whrid1) {
		this.whrid1 = whrid1;
	}

	public String getWz() {
		return wz;
	}

	public void setWz(String wz) {
		this.wz = wz;
	}

	public String getJhc() {
		return jhc;
	}
	
	public void calJhAndSj() throws ParseException {
		if (monitorItemList!=null && !monitorItemList.isEmpty()) {
			 StringBuffer jhSb = new StringBuffer();
			 StringBuffer sySb = new StringBuffer();
			 for (ScheduledCheckMonitorItem monitorItem : monitorItemList) {
				 jhSb.append(monitorItem.getMs()+":"+(StringUtils.isNotBlank(monitorItem.getCurJh())?monitorItem.getCurJh():"") +",");
				 sySb.append(monitorItem.getMs()+":"+(StringUtils.isNotBlank(monitorItem.getSy())?monitorItem.getSy():"")+",");
			 }
			 this.setJhc(jhSb.toString());//计划
			 this.setSy(sySb.toString());//剩余
		}
	}
	
	

	public void setJhc(String jhc) {
		this.jhc = jhc;
	}

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getSy() {
		return sy;
	}

	public void setSy(String sy) {
		this.sy = sy;
	}

	public String getSyts() {
		return syts;
	}

	public void setSyts(String syts) {
		this.syts = syts;
	}

	public String getZwmc() {
		return zwmc;
	}

	public void setZwmc(String zwmc) {
		this.zwmc = zwmc;
	}

	public String getYwmc() {
		return ywmc;
	}

	public void setYwmc(String ywmc) {
		this.ywmc = ywmc;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
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

	public Integer getBjlx() {
		return bjlx;
	}

	public void setBjlx(Integer bjlx) {
		this.bjlx = bjlx;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Date getWhsj() {
		return whsj;
	}

	public void setWhsj(Date whsj) {
		this.whsj = whsj;
	}

	/*public String getDprtcode() {
		return dprtcode;
	}

	public void setDprtcode(String dprtcode) {
		this.dprtcode = dprtcode == null ? null : dprtcode.trim();
	}*/

	public Integer getTbbs() {
		return tbbs;
	}

	public void setTbbs(Integer tbbs) {
		this.tbbs = tbbs;
	}

	public String getNbsbm() {
		return nbsbm;
	}

	public void setNbsbm(String nbsbm) {
		this.nbsbm = nbsbm;
	}

	public String getWhrid() {
		return whrid;
	}

	public void setWhrid(String whrid) {
		this.whrid = whrid;
	}

	public String getWhdwid() {
		return whdwid;
	}

	public void setWhdwid(String whdwid) {
		this.whdwid = whdwid;
	}

	public List<ScheduledCheckMonitorItem> getMonitorItemList() {
		return monitorItemList;
	}

	public void setMonitorItemList(List<ScheduledCheckMonitorItem> monitorItemList) {
		this.monitorItemList = monitorItemList;
	}

	public BigDecimal getBb() {
		return bb;
	}

	public void setBb(BigDecimal bb) {
		this.bb = bb;
	}

	public ScheduleMonitorModelEnum getJkfs() {
		return jkfs;
	}

	public void setJkfs(ScheduleMonitorModelEnum jkfs) {
		this.jkfs = jkfs;
	}

	public Date getJzrq() {
		return jzrq;
	}

	public void setJzrq(Date jzrq) {
		this.jzrq = jzrq;
	}

	public String getRsyl() {
		return rsyl;
	}

	public void setRsyl(String rsyl) {
		this.rsyl = rsyl;
	}
	public String getJhzxrq() {
		return jhzxrq;
	}
	public void setJhzxrq(String jhzxrq) {
		this.jhzxrq = jhzxrq;
	}
	public BigDecimal getZqz() {
		return zqz;
	}
	public void setZqz(BigDecimal zqz) {
		this.zqz = zqz;
	}
	 
	public String getCursj() {
		return cursj;
	}
	public void setCursj(String cursj) {
		this.cursj = cursj;
	}
	public String getCurjh() {
		return curjh;
	}
	public void setCurjh(String curjh) {
		this.curjh = curjh;
	}
	public Map<String,ScheduledCheckMonitorItem> getScheduledCheckMonitorMap() {
		if (null == this.scheduledCheckMonitorMap ) {
			Map<String,ScheduledCheckMonitorItem> map = new HashMap<String,ScheduledCheckMonitorItem>();
			if (this.getMonitorItemList()!=null && !this.getMonitorItemList().isEmpty() ) {
				 for (ScheduledCheckMonitorItem monitorItem : this.getMonitorItemList()) {
					 map.put(monitorItem.getJklbh(), monitorItem);
				}
			}
			this.setScheduledCheckMonitorMap(map);
		}
		return scheduledCheckMonitorMap;
	}
	public void setScheduledCheckMonitorMap(Map<String,ScheduledCheckMonitorItem> scheduledCheckMonitorMap) {
		this.scheduledCheckMonitorMap = scheduledCheckMonitorMap;
	}
	public Boolean getNotCal() {
		return notCal;
	}
	public void setNotCal(Boolean notCal) {
		this.notCal = notCal;
	}
	public Boolean getYpItem() {
		return ypItem;
	}
	public void setYpItem(Boolean ypItem) {
		this.ypItem = ypItem;
	}
	public String getWxfabh() {
		return wxfabh;
	}
	public void setWxfabh(String wxfabh) {
		this.wxfabh = wxfabh;
	}
	
	/**
	 * 计算预排定检项目
	 * @param idx
	 * @return
	 * @throws ParseException 
	 */
	public ScheduledCheckItem getDjjhNext(int idx) throws ParseException {
			ScheduledCheckItem next = this.deepClone(ScheduledCheckItem.class);
			List<ScheduledCheckMonitorItem> monitorItemList = next.getMonitorItemList();
			if (monitorItemList!=null && !monitorItemList.isEmpty()) {
				 for (ScheduledCheckMonitorItem monitorItem : monitorItemList) {
					 monitorItem.genSyts(idx);
				 }
				 ScheduledCheckMonitorItem min = Collections.min(monitorItemList,new Comparator<ScheduledCheckMonitorItem>() {
						@Override
						public int compare(ScheduledCheckMonitorItem o1, ScheduledCheckMonitorItem o2) {
							return new BigDecimal(o1.getSyts()).compareTo(new BigDecimal(o2.getSyts()));
						}
					});
				 next.setSyts(min.getSyts());
				 next.setJhzxrq(DateUtil.getDatePlus(this.getJhzxrq(), new BigDecimal(min.getSyts()).intValue() ));
			}
			return next;
	}
	public String getJhzxrqFir() {
		return jhzxrqFir;
	}
	public void setJhzxrqFir(String jhzxrqFir) {
		this.jhzxrqFir = jhzxrqFir;
	}
	public int getYpSeq() {
		return ypSeq==0?1:ypSeq;
	}
	public void setYpSeq(int ypSeq) {
		this.ypSeq = ypSeq;
	}
	public String getBjid() {
		return bjid;
	}
	public void setBjid(String bjid) {
		this.bjid = bjid;
	}
	 
}