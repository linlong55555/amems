package enu.workorder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author meizhiliang
 * @description 工单操作所需要用到的枚举
 * @develop date 2016.09.12
 */
public enum WorkOrderEnum {
	DJRW_TYPE(4, "定检任务单"),
	NON_TYPE(2, "非例行工单"),
	EO_TYPE(3, "EO工单"),
	DJ_TYPE(1, "定检工单"),
	ENGINEER_EO_TYPE(6, "工程指令EO"),
	;
	
	private Integer id;
    private String name;
    
    private WorkOrderEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (WorkOrderEnum c : WorkOrderEnum.values()) {
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
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	WorkOrderEnum[] enums = WorkOrderEnum.values();
    	
    	for (WorkOrderEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	            return o1.get("name").toString().compareTo(o2.get("name").toString()) ;
    	        }
    	    });
    	
    	return list;
	}
     
}
