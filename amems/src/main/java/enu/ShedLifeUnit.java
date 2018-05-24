package enu;

import java.util.Calendar;

/**
 * @author 朱超
 * @description 预排范围单位
 */

public enum ShedLifeUnit {
//	天数-预警级别2：单位（11天、12月、13年）
	DATE(11, Calendar.DATE),
	MONTH(12, Calendar.MONTH),
	YEAR(13, Calendar.YEAR), 
    ;
	
	private Integer id;
    private Integer name;
    
    private ShedLifeUnit(Integer id, Integer name) {
    	this.id = id;
        this.name = name;
    }
    
    public static Integer getName(Integer id) {
        for (ShedLifeUnit c : ShedLifeUnit.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name;
            }
        }
        return null;
    }
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getName() {
		return name;
	}
	
	public void setName(Integer name) {
		this.name = name;
	}
  
}
