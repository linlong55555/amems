package com.eray.thjw.log.po;

import com.eray.thjw.po.BizEntity;

import enu.AlignEnum;
import enu.FieldTypeEnum;

/**
 * 登陆限制-T_IPMAC_LIMIT
 * @author ll
 *
 */
public class LoginLimit  extends BizEntity{
	 
	/**
	 * id
	 */
	private String id;
	
	private Integer type; //类型
	
	private String value1;//mac值
	
	private String value2;//备注

	
	public String getValue2() {
		return value2;
	}

	public void setValue2(String value2) {
		this.value2 = value2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getValue1() {
		return value1;
	}

	public void setValue1(String value1) {
		this.value1 = value1;
	}
	

}
