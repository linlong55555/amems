package com.eray.rest.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/** 
 * @Description 飞机停场数据传递对象
 * @CreateTime 2018年2月2日 上午11:59:55
 * @CreateBy 韩武	
 */
public class PlaneParking {
	
	/** 飞机注册号 */
	private String regNum;
	
	/** 开始时间(yyyy-MM-dd HHmm) */
	@JSONField (format="yyyy-MM-dd HHmm")
	private Date startTime;
	
	/** 结束时间(yyyy-MM-dd HHmm) */
	@JSONField (format="yyyy-MM-dd HHmm")
	private Date endTime;
	
	/** 停场种类(1: 计划停场,2: 非计划停场) */
	private Integer parkingType;

	/** 停场原因 */
	private String reason;
	
	/** 详细内容 */
	private String detailContent;
	
	/** 更新人 */
	private String updateBy;
	
	/** 更新时间(yyyy-MM-dd HH:mm:ss) */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")
	private Date updateDate;
	
	/** 是否删除 */
	private Boolean isDelete;
	
	/** 同步Code(主键id) */
	private String syncCode;

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getParkingType() {
		return parkingType;
	}

	public void setParkingType(Integer parkingType) {
		this.parkingType = parkingType;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDetailContent() {
		return detailContent;
	}

	public void setDetailContent(String detailContent) {
		this.detailContent = detailContent;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getSyncCode() {
		return syncCode;
	}

	public void setSyncCode(String syncCode) {
		this.syncCode = syncCode;
	}

	@Override
	public String toString() {
		return "PlaneParking [regNum=" + regNum + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", parkingType=" + parkingType
				+ ", reason=" + reason + ", detailContent=" + detailContent
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ ", isDelete=" + isDelete + ", syncCode=" + syncCode + "]";
	}

}
