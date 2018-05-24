package enu.produce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 维修类型
 * @CreateTime 2017-9-13 下午2:17:55
 * @CreateBy 刘兵
 */
public enum MaintenanceTypeEnum {
	PROJECT(1, "维修项目"),
	EO(2, "EO"),
	PRODUCTION_ORDER(3, "生产指令")
	;
	
	private Integer id;
    private String name;
    
    private MaintenanceTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (MaintenanceTypeEnum c : MaintenanceTypeEnum.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name;
            }
        }
        return "";
    }
    
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @Description 枚举转listmap
	 * @CreateTime 2017-9-13 下午2:19:21
	 * @CreateBy 刘兵
	 * @return
	 */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	MaintenanceTypeEnum[] enums = MaintenanceTypeEnum.values();
    	
    	for (MaintenanceTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	            return o2.get("id").toString().compareTo(o1.get("id").toString()) ;
    	        }
    	    });
    	
    	return list;
	}
     
}
