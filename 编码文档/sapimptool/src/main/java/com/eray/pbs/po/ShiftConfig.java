package com.eray.pbs.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.eray.util.jpa.BaseEntity;

/**
 * 排班时间
 * 
 * @author ganqing
 * 
 */
@Entity
@Table(name = "pbs_shiftconfig")
public class ShiftConfig extends BaseEntity {

	private String shiftDesc; // 排班描述
	private String inStart; // in的开始时间
	private String inEnd; // in的结束时间
	private String actualInTime; // in的实际取值
	private String outStart; // out的开始时间
	private String outEnd; // out的结束时间
	private String actualOutTime; // out的实际取值(In匹配到out区间后的out取值)
	private String meetingStart; // meeting的开始时间
	private String meetingEnd; // meeting的结束时间

	@Column(name = "shiftdesc_")
	public String getShiftDesc() {
		return shiftDesc;
	}

	public void setShiftDesc(String shiftDesc) {
		this.shiftDesc = shiftDesc;
	}

	@Column(name = "instart_")
	public String getInStart() {
		return inStart;
	}

	public void setInStart(String inStart) {
		this.inStart = inStart;
	}

	@Column(name = "inend_")
	public String getInEnd() {
		return inEnd;
	}

	public void setInEnd(String inEnd) {
		this.inEnd = inEnd;
	}

	@Column(name = "actualintime_")
	public String getActualInTime() {
		return actualInTime;
	}

	public void setActualInTime(String actualInTime) {
		this.actualInTime = actualInTime;
	}

	@Column(name = "outstart_")
	public String getOutStart() {
		return outStart;
	}

	public void setOutStart(String outStart) {
		this.outStart = outStart;
	}

	@Column(name = "outend_")
	public String getOutEnd() {
		return outEnd;
	}

	public void setOutEnd(String outEnd) {
		this.outEnd = outEnd;
	}

	@Column(name = "actualouttime_")
	public String getActualOutTime() {
		return actualOutTime;
	}

	public void setActualOutTime(String actualOutTime) {
		this.actualOutTime = actualOutTime;
	}

	@Column(name = "meetingstart_")
	public String getMeetingStart() {
		return meetingStart;
	}

	public void setMeetingStart(String meetingStart) {
		this.meetingStart = meetingStart;
	}

	@Column(name = "meetingend_")
	public String getMeetingEnd() {
		return meetingEnd;
	}

	public void setMeetingEnd(String meetingEnd) {
		this.meetingEnd = meetingEnd;
	}

	public String toString() {
		return "ShiftConfig [shiftDesc=" + shiftDesc + ", inStart=" + inStart
				+ ", inEnd=" + inEnd + ", actualInTime=" + actualInTime
				+ ", outStart=" + outStart + ", outEnd=" + outEnd
				+ ", actualOutTime=" + actualOutTime + ", meetingStart="
				+ meetingStart + ", meetingEnd=" + meetingEnd + "]";
	}

}
