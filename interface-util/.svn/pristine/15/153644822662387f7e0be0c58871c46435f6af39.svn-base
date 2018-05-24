package com.eray.rest.vo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

/** 
 * @Description 故障保留单数据传递对象
 * @CreateTime 2018年2月2日 上午11:57:50
 * @CreateBy 韩武	
 */
public class DeferredDefect {

	/** DD单号 */
	private String ddCode;
	
	/** 飞机注册号 */
	private String regNum;
	
	/** 类型（1：DD） */
	private Integer type;
	
	/** 到期日期(yyyy-MM-dd) */
    @JSONField (format="yyyy-MM-dd")    
	private Date expDate;
	
	/** 故障报告 */
	private String description;
	
	/** 保留依据 */
	private String document;
	
	/** 保留原因 */
	private String reason;
	
	/** 修复期限 */
	private String deadline;
	
	/** 办理日期(yyyy-MM-dd) */
	@JSONField (format="yyyy-MM-dd")  
	private Date occuDate;
	
	/** 运行限制内容 */
	private String runLtdContent;
	
	/** 运行限制开始日期(yyyy-MM-dd) */
	@JSONField (format="yyyy-MM-dd")  
	private Date runLtdStart;
	
	/** 运行限制结束日期(yyyy-MM-dd) */
	@JSONField (format="yyyy-MM-dd")  
	private Date runLtdEnd;
	
	/** 状态（70：已审批，20：已关闭） */
	private Integer state;
	
	/** 更新人 */
	private String updateBy;
	
	/** 更新时间(yyyy-MM-dd HH:mm:ss) */
	@JSONField (format="yyyy-MM-dd HH:mm:ss")  
	private Date updateDate;
	
	/** 同步Code(主键id) */
	private String syncCode;
	
	public String getDdCode() {
		return ddCode;
	}

	public void setDdCode(String ddCode) {
		this.ddCode = ddCode;
	}

	public String getRegNum() {
		return regNum;
	}

	public void setRegNum(String regNum) {
		this.regNum = regNum;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getExpDate() {
		return expDate;
	}

	public void setExpDate(Date expDate) {
		this.expDate = expDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	public Date getOccuDate() {
		return occuDate;
	}

	public void setOccuDate(Date occuDate) {
		this.occuDate = occuDate;
	}

	public String getRunLtdContent() {
		return runLtdContent;
	}

	public void setRunLtdContent(String runLtdContent) {
		this.runLtdContent = runLtdContent;
	}

	public Date getRunLtdStart() {
		return runLtdStart;
	}

	public void setRunLtdStart(Date runLtdStart) {
		this.runLtdStart = runLtdStart;
	}

	public Date getRunLtdEnd() {
		return runLtdEnd;
	}

	public void setRunLtdEnd(Date runLtdEnd) {
		this.runLtdEnd = runLtdEnd;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
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

	public String getSyncCode() {
		return syncCode;
	}

	public void setSyncCode(String syncCode) {
		this.syncCode = syncCode;
	}

	@Override
	public String toString() {
		return "DeferredDefect [ddCode=" + ddCode + ", regNum=" + regNum
				+ ", type=" + type + ", expDate=" + expDate + ", description="
				+ description + ", document=" + document + ", reason=" + reason
				+ ", deadline=" + deadline + ", occuDate=" + occuDate
				+ ", runLtdContent=" + runLtdContent + ", runLtdStart="
				+ runLtdStart + ", runLtdEnd=" + runLtdEnd + ", state=" + state
				+ ", updateBy=" + updateBy + ", updateDate=" + updateDate
				+ ", syncCode=" + syncCode + "]";
	}
	
}
