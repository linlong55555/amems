package com.eray.thjw.log.po;

import enu.AlignEnum;
import enu.FieldTypeEnum;

/**
 * 日志列定义
 * @author zhuchao
 *
 */
public class Field {
	 
	/**
	 * 是否显示在差异中
	 */
	private Boolean showInDiff;
	
	/**
	 * 是否显示在列表中
	 */
	private Boolean showInList;
	
	/**
	 * DB字段
	 */
	private String field;
	
	/**
	 * 显示字段名
	 */
	private String fieldName;
	
	/**
	 * 字段类型(如果是类型是枚举或子典，需要使用枚举或子典渲染字段)
	 */
	private FieldTypeEnum type;
	
	/**
	 * 对齐方式
	 */
	private AlignEnum align;
	
	/**
	 * 枚举关键字：使用对应枚举渲染字段
	 */
	private String enumKey;
	
	/**
	 * 字典关键字：使用对应字典渲染字段
	 */
	private String dicKey;
	
	//排序
	private Integer order;
	
	//列头class
	private String headClass;
	
	//数据class
	private String dataClass;
	
	//英文名称
	private String fieldEname;
	
	private String enumClass;
	
	
	public Field() {
		 
	}
	
	/**
	 * 
	 * @param field  字段
	 * @param fieldName 字段名称
	 * @param showInDiff 在差异中显示
	 * @param showInList 在列表中显示
	 */
	public Field(String field, String fieldName,Boolean showInDiff,Boolean showInList) {
		super();
		this.showInDiff = showInDiff;
		this.showInList = showInList;
		this.field = field;
		this.fieldName = fieldName;
		this.align = AlignEnum.LEFT;
	}
	
	public Field(String field, String fieldName,AlignEnum align,Boolean showInDiff,Boolean showInList) {
		super();
		this.showInDiff = showInDiff;
		this.showInList = showInList;
		this.field = field;
		this.fieldName = fieldName;
		this.align = align;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	 
	public String getEnumKey() {
		return enumKey;
	}
	public void setEnumKey(String enumKey) {
		this.enumKey = enumKey;
	}
	public String getDicKey() {
		return dicKey;
	}
	public void setDicKey(String dicKey) {
		this.dicKey = dicKey;
	}
	public AlignEnum getAlign() {
		return align;
	}
	public void setAlign(AlignEnum align) {
		this.align = align;
	}
	public FieldTypeEnum getType() {
		return type;
	}
	public void setType(FieldTypeEnum type) {
		this.type = type;
	}

	public Boolean getShowInDiff() {
		return showInDiff;
	}

	public void setShowInDiff(Boolean showInDiff) {
		this.showInDiff = showInDiff;
	}

	public Boolean getShowInList() {
		return showInList;
	}

	public void setShowInList(Boolean showInList) {
		this.showInList = showInList;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public String getHeadClass() {
		return headClass;
	}

	public void setHeadClass(String headClass) {
		this.headClass = headClass;
	}

	public String getDataClass() {
		return dataClass;
	}

	public void setDataClass(String dataClass) {
		this.dataClass = dataClass;
	}

	public String getFieldEname() {
		return fieldEname;
	}

	public void setFieldEname(String fieldEname) {
		this.fieldEname = fieldEname;
	}

	public String getEnumClass() {
		return enumClass;
	}

	public void setEnumClass(String enumClass) {
		this.enumClass = enumClass;
	}

}
