package com.eray.thjw.po;

import java.util.Date;

import com.eray.thjw.project2.po.Todo;

/**
 * @Description t_batchtask 后台任务
 * @CreateTime 2018年4月11日 上午 11:35
 * @author hchu
 */
public class TaskInfo extends BizEntity {
	private String id;
	private Integer zt; // 状态:1待处理、2处理中、9失败、10完成
	private Integer rwlx; // 任务类型：1导出文档
	private String rwbm; // 任务编码
	private String rwms; // 任务描述
	private String sqrid; // 申请人id
	private Date sqsj; // 申请时间
	private String fksm; // 反馈说明
	private Date fksj; // 反馈时间
	private String nbsbm; // 内部识别码
	private String wjdz; // 文件地址
	private String rwdxid; // 任务对象id
	
	private Todo todo;//待办
	
	private User sqr;//申请人

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getZt() {
		return zt;
	}

	public void setZt(Integer zt) {
		this.zt = zt;
	}

	public Integer getRwlx() {
		return rwlx;
	}

	public void setRwlx(Integer rwlx) {
		this.rwlx = rwlx;
	}

	public String getRwbm() {
		return rwbm;
	}

	public void setRwbm(String rwbm) {
		this.rwbm = rwbm;
	}

	public String getRwms() {
		return rwms;
	}

	public void setRwms(String rwms) {
		this.rwms = rwms;
	}

	public String getSqrid() {
		return sqrid;
	}

	public void setSqrid(String sqrid) {
		this.sqrid = sqrid;
	}

	public Date getSqsj() {
		return sqsj;
	}

	public void setSqsj(Date sqsj) {
		this.sqsj = sqsj;
	}

	public String getFksm() {
		return fksm;
	}

	public void setFksm(String fksm) {
		this.fksm = fksm;
	}

	public Date getFksj() {
		return fksj;
	}

	public void setFksj(Date fksj) {
		this.fksj = fksj;
	}

	public String getNbsbm() {
		return nbsbm;
	}

	public void setNbsbm(String nbsbm) {
		this.nbsbm = nbsbm;
	}

	public String getWjdz() {
		return wjdz;
	}

	public void setWjdz(String wjdz) {
		this.wjdz = wjdz;
	}

	public String getRwdxid() {
		return rwdxid;
	}

	public void setRwdxid(String rwdxid) {
		this.rwdxid = rwdxid;
	}

	public User getSqr() {
		return sqr;
	}

	public void setSqr(User sqr) {
		this.sqr = sqr;
	}

	public Todo getTodo() {
		return todo;
	}

	public void setTodo(Todo todo) {
		this.todo = todo;
	}
	
}
