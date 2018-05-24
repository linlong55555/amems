package com.eray.thjw.aerialmaterial.po;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import enu.PlanTaskType;

/**
 * 航材缺件-任务自身工单信息
 * @author zhuchao
 *
 */
public class HcTaskInfo {
	
	/**
	 * --部件id
	 */
    private String bjid;

    /**
     * --任务类型：1定检执行任务、2非例行工单任务、3EO工单任务
     */
    private String rwlx;

    /**
     * --任务子类型：0无、1时控件工单、2附加工单、3排故工单
     */
    private Short rwzlx;

    /**
     * --任务信息
     */
    private String rwxx;

    /**
     * --任务工单id
     */
    private String rwgdid;

    /**
     * --任务工单编号
     */
    private String rwgdbh;
    
    /**
     * --序号
     */
    private String xh;
    
    /**
	 * 工单id
	 */
	private String gdid ;
	
	private String gdjcid ;
	
    /**
     * 非例行/EO工单
     */
    private List<HcTaskRelOrder> hcTaskRelOrders ;
    
    /**
     * 任务/相关订单信息
     */
	private String taskRelOrdersStr ;
    
	public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
	}

	public String getRwlx() {
		return rwlx;
	}

	public void setRwlx(String rwlx) {
		this.rwlx = rwlx;
	}

	public Short getRwzlx() {
		return rwzlx;
	}

	public void setRwzlx(Short rwzlx) {
		this.rwzlx = rwzlx;
	}

	public String getRwxx() {
		int len = StringUtils.isNotBlank(rwxx)?rwxx.length():0;
		
		String rwxxTemp = StringUtils.isNotBlank(rwxx)?rwxx.substring(0,(len<=60?len:60)):"";
		rwxxTemp = rwxxTemp.equals(rwxx)?rwxx:rwxxTemp.concat("...");
		//任务信息：定检任务可以点击链接
		StringBuffer workorderSb = new StringBuffer();
		if (StringUtils.isNotBlank(this.getRwlx()) && PlanTaskType.isCheckBill(this.getRwlx())) {
			workorderSb.append("<a href='#'");
			workorderSb.append(" id=");
			workorderSb.append(this.getGdid());
			workorderSb.append(" esWorkOrder=true");
			workorderSb.append(" gddlx=4");
			workorderSb.append(" title='");
			workorderSb.append(rwxx);
			workorderSb.append("'>");
			workorderSb.append(rwxxTemp);
			workorderSb.append("</a>");
			
		}
		else{
			workorderSb.append("<a href='#'");
			workorderSb.append(" id=");
			workorderSb.append(this.getGdid());
			workorderSb.append(" esWorkOrder=true");
			workorderSb.append(" gddlx=");
			workorderSb.append(this.getRwlx());
			workorderSb.append(" title='");
			workorderSb.append(rwxx);
			workorderSb.append("'>");
			workorderSb.append(rwxxTemp);
			workorderSb.append("</a>");
		}
		
		return  workorderSb.toString();
	}

	public void setRwxx(String rwxx) {
		this.rwxx = rwxx;
	}

	public String getRwgdid() {
		return rwgdid;
	}
	
	public void setRwgdid(String rwgdid) {
		this.rwgdid = rwgdid;
	}

	public String getRwgdbh() {
		return rwgdbh;
	}

	public void setRwgdbh(String rwgdbh) {
		this.rwgdbh = rwgdbh;
	}

	public String getXh() {
		return xh==null?"":xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public void calHcTaskRelOrders() {
		if (hcTaskRelOrders!=null &&!hcTaskRelOrders.isEmpty()) {
			//是定检任务
			if (PlanTaskType.isCheckBill(rwlx)) {
				List<HcTaskRelOrder> hcTaskScheduleOrders = new ArrayList<HcTaskRelOrder>();
				String key = "";
				//组装定检单Map(定检单ID,定检单)
				Map<String, HcTaskRelOrder>masterBillMap = new HashMap<String, HcTaskRelOrder>();
				for (HcTaskRelOrder hcTaskRelOrder : hcTaskRelOrders) {
					if (hcTaskRelOrder.esMasterBill()) {
						hcTaskScheduleOrders.add(hcTaskRelOrder);
						key = hcTaskRelOrder.getBjid().concat(hcTaskRelOrder.getGdid()).concat(hcTaskRelOrder.getGdjcid());
						masterBillMap.put(key, hcTaskRelOrder);
					}
				}
				
				//将相关定检单组装到定检主单中
				for (HcTaskRelOrder hcTaskRelOrder : hcTaskRelOrders) {
					if (!hcTaskRelOrder.esMasterBill()) {
						key = hcTaskRelOrder.getBjid().concat(hcTaskRelOrder.getRwgdid()).concat(hcTaskRelOrder.getGdjcid());
						if (StringUtils.isNotBlank(hcTaskRelOrder.getRwgdid())
								&& masterBillMap.containsKey(key)) {
							HcTaskRelOrder masterOrder = masterBillMap.get(key);
							if (masterOrder.getHcTaskRelOrders()==null) {
								List<HcTaskRelOrder> hcTaskRelOrdersNew = new ArrayList<HcTaskRelOrder>();
								masterOrder.setHcTaskRelOrders(hcTaskRelOrdersNew);
								hcTaskRelOrdersNew.add(hcTaskRelOrder);
							}
							else{
								masterOrder.getHcTaskRelOrders().add(hcTaskRelOrder);
							}
						}
					}
				}
				this.setHcTaskRelOrders(hcTaskScheduleOrders) ;
			}
		}
	}
	
	public List<HcTaskRelOrder> getHcTaskRelOrders() {
		return hcTaskRelOrders!=null?hcTaskRelOrders:new ArrayList<HcTaskRelOrder>();
	}

	public void setHcTaskRelOrders(List<HcTaskRelOrder> hcTaskRelOrders) {
		this.hcTaskRelOrders = hcTaskRelOrders;
	}
 

	public String getTaskRelOrdersStr() {
		StringBuffer sb = new  StringBuffer();
		sb.append(this.getRwxx());
		if (this.getHcTaskRelOrders()!=null && !this.getHcTaskRelOrders().isEmpty() ) {
			int len = this.getHcTaskRelOrders().size();
			for (int i = 0;i<len ;i++) {
				sb.append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sb.append(i+1);
				sb.append(".");
				sb.append(this.getHcTaskRelOrders().get(i).getGdbhJoin());
			}
		}
		taskRelOrdersStr = sb.toString();
		return taskRelOrdersStr;
	}

	public void setTaskRelOrdersStr(String taskRelOrdersStr) {
		this.taskRelOrdersStr = taskRelOrdersStr;
	}

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

	public String getGdjcid() {
		return gdjcid;
	}

	public void setGdjcid(String gdjcid) {
		this.gdjcid = gdjcid;
	}
 
	
}