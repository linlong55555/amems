package enu.project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 监控项单位枚举
 * @CreateTime 2017年10月10日 下午5:39:38
 * @CreateBy 徐勇
 */
public enum MonitorProjectUnitEnum {
	
	  CAL(10, "CAL")
	, DAY(11, "DAY")
	, MONTH(12, "MON")
	, HRS(20, "HRS")
	, CYC(30, "CYC")
	;
	
	private Integer code;
    private String name;

    
    private MonitorProjectUnitEnum(Integer code, String name) {
    	this.code = code;
        this.name = name;
    }

    public static String getName(String code) {
        for (MonitorProjectUnitEnum c : MonitorProjectUnitEnum.values()) {
            if (c.getCode().equals(code.toUpperCase())) {
                return c.name;
            }
        }
        return "";
    }
    
    /**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	MonitorProjectUnitEnum[] enums = MonitorProjectUnitEnum.values();
    	
    	for (MonitorProjectUnitEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("code", enumItem.getCode());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
	}
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
    
}