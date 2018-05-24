package com.eray.thjw.productionplan.po;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import enu.MonitorItemEnum;

@SuppressWarnings("serial")
public class PlanTaskHistory extends PlanTask{
    
    /**
     * 计划
     */
    private String jh;
    
    /**
     * 实际
     */
    private String sj;
    
    /**
     * 实际总工时
     */
    private BigDecimal sjgs;
    
    
    /**
     * 快照列表
     */
    private PlanTaskSnap planTaskSnap;
    
    /**
     * 飞行记录单号
     */
    private String fxjldh;
    /**
     * 记录本页码
     */
    private String jlbym;
    /**
     * 完成日期开始
     */
    private Date wcrqStart; 
    /**
     * 完成日期结束
     */
    private Date wcrqEnd;
    /**
     * 打印时间开始
     */
    private Date dyrqStart;
    /**
     * 打印时间结束
     */
    private Date dyrqEnd;

	public String getJh() {
		if (planTaskSnap!=null) {
			List<String>jhs = new ArrayList<String>();
			if (StringUtils.isNotBlank(planTaskSnap.getJkxmbhF())) {
				jhs.add(MonitorItemEnum.getName(planTaskSnap.getJkxmbhF()).concat(":")
						.concat(StringUtils.isEmpty(planTaskSnap.getJkzJhF())?"":planTaskSnap.getJkzJhF()));
			}
			if (StringUtils.isNotBlank(planTaskSnap.getJkxmbhS())) {
				jhs.add(MonitorItemEnum.getName(planTaskSnap.getJkxmbhS()).concat(":")
						.concat(StringUtils.isEmpty(planTaskSnap.getJkzJhS())?"":planTaskSnap.getJkzJhS()));
			}
			if (StringUtils.isNotBlank(planTaskSnap.getJkxmbhT())) {
				jhs.add(MonitorItemEnum.getName(planTaskSnap.getJkxmbhT()).concat(":")
						.concat(StringUtils.isEmpty(planTaskSnap.getJkzJhT())?"":planTaskSnap.getJkzJhT()));
			}
			jh = list2Str(jhs);
		}
		return jh;
	}

	private String list2Str(List<String> list) {
		StringBuffer sb = new StringBuffer();
		if (list!=null && !list.isEmpty()) {
			for (String str : list) {
				if (sb.length()>0) {
					sb.append("</br>");
				}
				sb.append(str);
			}
		}
		return sb.toString();
	}

	public void setJh(String jh) {
		this.jh = jh;
	}

	public String getSj() { 
		if (planTaskSnap!=null) {
			List<String>sjs = new ArrayList<String>();
			if (StringUtils.isNotBlank(planTaskSnap.getJkxmbhF())) {
				sjs.add(MonitorItemEnum.getName(planTaskSnap.getJkxmbhF()).concat(":")
						.concat(StringUtils.isEmpty(planTaskSnap.getJkzSjF())?"":planTaskSnap.getJkzSjF()));
			}
			if (StringUtils.isNotBlank(planTaskSnap.getJkxmbhS())) {
				sjs.add(MonitorItemEnum.getName(planTaskSnap.getJkxmbhS()).concat(":")
						.concat(StringUtils.isEmpty(planTaskSnap.getJkzSjS())?"":planTaskSnap.getJkzSjS()));
			}
			if (StringUtils.isNotBlank(planTaskSnap.getJkxmbhT())) {
				sjs.add(MonitorItemEnum.getName(planTaskSnap.getJkxmbhT()).concat(":")
						.concat(StringUtils.isEmpty(planTaskSnap.getJkzSjT())?"":planTaskSnap.getJkzSjT()));
			}
			sj = list2Str(sjs);
		}
		
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public PlanTaskSnap getPlanTaskSnap() {
		return planTaskSnap;
	}

	public void setPlanTaskSnap(PlanTaskSnap planTaskSnap) {
		this.planTaskSnap = planTaskSnap;
	}

	public BigDecimal getSjgs() {
		sjgs = getSjgsXs().multiply(getSjgsRs());
		return sjgs ;
	}

	public void setSjgs(BigDecimal sjgs) {
		this.sjgs = sjgs;
	}

	public String getFxjldh() {
		return fxjldh;
	}

	public void setFxjldh(String fxjldh) {
		this.fxjldh = fxjldh;
	}
	
	public String getJlbym() {
		return jlbym;
	}

	public void setJlbym(String jlbym) {
		this.jlbym = jlbym;
	}

	public Date getWcrqStart() {
		return wcrqStart;
	}

	public void setWcrqStart(Date wcrqStart) {
		this.wcrqStart = wcrqStart;
	}

	public Date getWcrqEnd() {
		return wcrqEnd;
	}

	public void setWcrqEnd(Date wcrqEnd) {
		this.wcrqEnd = wcrqEnd;
	}

	public Date getDyrqStart() {
		return dyrqStart;
	}

	public void setDyrqStart(Date dyrqStart) {
		this.dyrqStart = dyrqStart;
	}

	public Date getDyrqEnd() {
		return dyrqEnd;
	}

	public void setDyrqEnd(Date dyrqEnd) {
		this.dyrqEnd = dyrqEnd;
	}
		
}