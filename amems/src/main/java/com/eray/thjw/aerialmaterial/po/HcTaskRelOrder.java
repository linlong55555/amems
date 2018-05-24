package com.eray.thjw.aerialmaterial.po;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.eray.thjw.productionplan.po.Hyperlink;

import enu.BinaryEnum;

/**
 *  航材缺件--任务：工单-主工单
 * @author zhuchao
 *
 */
public class HcTaskRelOrder {
	
	private String id;
	/**
	 * --部件id
	 */
    private String bjid;

    /**
	 * --任务工单id
	 */
    private String rwgdid;

    /**
	 * --工单id（左）
	 */
    private String gdid;

    /**
	 * --工单编号（左）
	 */
    private String gdbh;

    /**
	 * --主工单id（右）
	 */
    private String zgdid;

    /**
	 * --主工单编号（右）
	 */
    private String zgdbh;
    
    /**
     * 1是主单，0是相关工单
     */
    private String scheduleCheck; 
    
    /**
     * 1是主单，0是相关工单
     */
    private String masterBill; 
    
    /**
     * 工单类型
     */
    private Integer gdlx; 
    
    
    /**
     * 相关编号-主工单编号
     */
    private String gdbhJoin;
    
    private String xh;
    
    private String gdjcid;
    
    /**
     * 主单下的相关工单
     */
    private List<HcTaskRelOrder> hcTaskRelOrders;
    

	public String getBjid() {
		return bjid;
	}

	public void setBjid(String bjid) {
		this.bjid = bjid;
	}

	public String getRwgdid() {
		return rwgdid;
	}

	public void setRwgdid(String rwgdid) {
		this.rwgdid = rwgdid;
	}

	public String getGdid() {
		return gdid;
	}

	public void setGdid(String gdid) {
		this.gdid = gdid;
	}

	public String getGdbh() {
		return gdbh;
	}

	public void setGdbh(String gdbh) {
		this.gdbh = gdbh;
	}

	public String getZgdid() {
		return zgdid;
	}

	public void setZgdid(String zgdid) {
		this.zgdid = zgdid;
	}

	public String getZgdbh() {
		return StringUtils.isNotBlank(zgdbh)?zgdbh:"主单";
	}

	public void setZgdbh(String zgdbh) {
		this.zgdbh = zgdbh;
	}

	/**
	 * 获取主工单，以及相关工单字符（带格式）
	 * @return
	 */
	public String getGdbhJoin() {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(this.getGdbh())) {
			this.setGdbh("");
		}
		sb.append(new Hyperlink(this.getGdid(),true,this.getGdlx(),this.getGdbh(),this.getZgdbh()).toHtml());
		if (this.hasRelOrders()) {
			int len = this.getHcTaskRelOrders().size();
			for (int i = 0;i<len ;i++) {
				sb.append("<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sb.append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
				sb.append(i+1);
				sb.append(".");
				sb.append(this.getHcTaskRelOrders().get(i).getGdbhJoin());
			}
		}
		
		gdbhJoin = sb.toString();
		return gdbhJoin;
	}

	public void setGdbhJoin(String gdbhJoin) {
		this.gdbhJoin = gdbhJoin;
	}

	/**
	 * 有相关工单
	 * @return
	 */
	public Boolean hasRelOrders() {
		Boolean result = false;
		if (esMasterBill() && this.getHcTaskRelOrders()!=null 
				&& !this.getHcTaskRelOrders().isEmpty()) {
			result = true;
		}
		return result;
	}
	
	/**
	 * 是主工单单
	 * @return
	 */
	public Boolean esMasterBill() {
		Boolean result = false;
		if (this.getMasterBill().equals(BinaryEnum.YES.getId().toString())) {
			result = true;
		}
		return result;
	}
	
	public Boolean esScheduleCheck() {
		Boolean result = false;
		if (this.getScheduleCheck().equals(BinaryEnum.YES.getId().toString())) {
			result = true;
		}
		return result;
	}
	
	public List<HcTaskRelOrder> getHcTaskRelOrders() {
		return hcTaskRelOrders!=null?hcTaskRelOrders:new ArrayList<HcTaskRelOrder>();
	}

	public void setHcTaskRelOrders(List<HcTaskRelOrder> hcTaskRelOrders) {
		this.hcTaskRelOrders = hcTaskRelOrders;
	}

	public String getScheduleCheck() {
		return scheduleCheck;
	}

	public void setScheduleCheck(String scheduleCheck) {
		this.scheduleCheck = scheduleCheck;
	}

	public String getMasterBill() {
		return StringUtils.isNotBlank(masterBill)?masterBill:"";
	}

	public void setMasterBill(String masterBill) {
		this.masterBill = masterBill;
	}

	public Integer getGdlx() {
		return gdlx;
	}

	public void setGdlx(Integer gdlx) {
		this.gdlx = gdlx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getXh() {
		return xh==null?"":xh;
	}

	public void setXh(String xh) {
		this.xh = xh;
	}

	public String getGdjcid() {
		return gdjcid;
	}

	public void setGdjcid(String gdjcid) {
		this.gdjcid = gdjcid;
	}
 
}