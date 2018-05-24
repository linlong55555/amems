package com.eray.thjw.po;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import enu.LogOperationEnum;

/**
 * @author zhuchao
 * @description 所有实体的基类
 */
@SuppressWarnings("serial")
public class BaseEntity extends AbstractEntity {
	/**
	 * 分页参数对象
	 */
	public  Pagination pagination; 
	/**
	 * 操作流水号：用于日志
	 */
	private String czls;
	/**
	 * 主表ID：用于日志
	 */
	private String zbid;
	 
	
	/**
	 * 操作：用于日志
	 */
	private LogOperationEnum logOperationEnum;
	
	/**
	 * 页面需要扩展的查询条件
	 */
    private Map<String, Object> paramsMap;
    
	public Pagination getPagination() {
		if(pagination == null){
			pagination = new Pagination();
		}
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public Map<String, Object> getParamsMap() {
		if(paramsMap == null){
			paramsMap = new HashMap<String, Object>();
		}
		return paramsMap;
	}

	public void setParamsMap(Map<String, Object> paramsMap) {
		this.paramsMap = paramsMap;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T deepClone(Class<T> clazz) {
		T obj = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			obj = (T) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public Object getId() {
		return null;
	}

	public String getCzls() {
		return czls;
	}

	public void setCzls(String czls) {
		this.czls = czls;
	}

	public LogOperationEnum getLogOperationEnum() {
		return logOperationEnum;
	}

	public void setLogOperationEnum(LogOperationEnum logOperationEnum) {
		this.logOperationEnum = logOperationEnum;
	}

	public String getZbid() {
		return zbid;
	}

	public void setZbid(String zbid) {
		this.zbid = zbid;
	}
}