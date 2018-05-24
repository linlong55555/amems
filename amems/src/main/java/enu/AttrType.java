package enu;

import java.util.Date;


/**
 * @author hanwu
 * @description 属性类型
 */
public enum AttrType {
	
	/**
	 * String
	 */
	STRING(1, String.class),
	
	/**
	 * Integer
	 */
	INTEGER(2, Integer.class),
	
	/**
	 * Double
	 */
	DOUBLE(3, Double.class),
	
	/**
	 * Date
	 */
	DATE(4, Date.class),
	;
	
	private final int code;
	
	private final Class<?> clazz;
	
	private AttrType(int code, Class<?> clazz) {
        this.code = code;
        this.clazz = clazz;
    }
	
	public int getCode() {
        return code;
    }
	
	public Class<?> getTypeClass(){
		return clazz;
	}
	
}
