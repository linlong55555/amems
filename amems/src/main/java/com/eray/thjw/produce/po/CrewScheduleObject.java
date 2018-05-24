package com.eray.thjw.produce.po;

import com.eray.thjw.po.BizEntity;
import com.eray.thjw.po.User;

/**
 * 人员排班对象表
 * 
 * @author Administrator
 *
 */
public class CrewScheduleObject extends BizEntity {
	private String id;

	private String mainid;// 排班表id

	private Integer dxlx;// 对象类型：10飞机、20人员、21机械放行师、22电子放行师

	private String dxid;// 对象类型
	
	private User jxs;// 机械师

	private User dzs;// 电子师

	private User jxy;// 机械员

	private User dzy;// 电子员

	private User jxby;// 机械师(备)

	private User dzby;// 电子师(备)

	private User jxyby;// 机械员(备)

	private User dzyby;// 电子员(备)

	private User mccdd;// MCC调度

	private Integer zt;// 状态：0无效、1有效

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMainid() {
		return mainid;
	}

	public void setMainid(String mainid) {
		this.mainid = mainid;
	}

	public Integer getDxlx() {
		return dxlx;
	}

	public void setDxlx(Integer dxlx) {
		this.dxlx = dxlx;
	}

	public String getDxid() {
		return dxid;
	}

	public void setDxid(String dxid) {
		this.dxid = dxid;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public User getJxs() {
		return jxs;
	}

	public void setJxs(User jxs) {
		this.jxs = jxs;
	}

	public User getDzs() {
		return dzs;
	}

	public void setDzs(User dzs) {
		this.dzs = dzs;
	}

	public User getJxby() {
		return jxby;
	}

	public void setJxby(User jxby) {
		this.jxby = jxby;
	}

	public User getDzby() {
		return dzby;
	}

	public void setDzby(User dzby) {
		this.dzby = dzby;
	}

	public User getJxy() {
		return jxy;
	}

	public void setJxy(User jxy) {
		this.jxy = jxy;
	}

	public User getDzy() {
		return dzy;
	}

	public void setDzy(User dzy) {
		this.dzy = dzy;
	}

	public User getJxyby() {
		return jxyby;
	}

	public void setJxyby(User jxyby) {
		this.jxyby = jxyby;
	}

	public User getDzyby() {
		return dzyby;
	}

	public void setDzyby(User dzyby) {
		this.dzyby = dzyby;
	}

	public User getMccdd() {
		return mccdd;
	}

	public void setMccdd(User mccdd) {
		this.mccdd = mccdd;
	}

}
