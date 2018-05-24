package com.eray.thjw.system.po;

import java.util.Date;

import com.eray.thjw.po.BaseEntity;

/**
 * 
 * @Description 更新日志
 * @CreateTime 2018年4月24日 上午10:07:50
 * @CreateBy 林龙
 */
@SuppressWarnings("serial")
public class UpdateLog extends BaseEntity {
	
	private String id;
	
	private String version;
	
	private Date pubdate;
	
	private String apply;
	
	private String title;
	
	private String describe;
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Date getPubdate() {
		return pubdate;
	}

	public void setPubdate(Date pubdate) {
		this.pubdate = pubdate;
	}

	public String getApply() {
		return apply;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}


	
}