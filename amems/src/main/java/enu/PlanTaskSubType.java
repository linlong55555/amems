package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * @author 朱超
 * @description 计划任务子类型
 */

public enum PlanTaskSubType {
//	任务子类型：0无、1时控件工单、2附加工单、3排故工单
	NOTHING(0, "无"),
	TIME_CONTROL_PART(1, "时控件工单"),
	ATTACHED (2, "附加工单"),
	WORK_ORDER(3, "排故工单"),
    ;
	
	private Integer id;
    private String name;
    
    /**
     * 非例行工单
     * @param str
     * @return
     */
    public static Integer str2Id(String str) {
    	for (PlanTaskSubType subType : PlanTaskSubType.values()) {
    		if (subType.toString().equals(str)) {
    			return subType.getId();
			}
		}
		return null;
	}
    
    public static Boolean isNonRoutine(String str) {
    	for (PlanTaskSubType subType : PlanTaskSubType.values()) {
    		if (subType.toString().equals(str)) {
    			return true;
			}
		}
		return false;
	}

	private PlanTaskSubType(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (PlanTaskSubType c : PlanTaskSubType.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name;
            }
        }
        return "";
    }
    
    public static Boolean isTimeControlPart(String id) {
    	Boolean reuslt = false;
    	if (StringUtils.isNotBlank(id)) {
    		if (PlanTaskSubType.TIME_CONTROL_PART.getId().toString().equals(id)) {
            	reuslt = true;
            } 
		}
        return reuslt;
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
    	PlanTaskSubType[] enums = PlanTaskSubType.values();
    	
    	for (PlanTaskSubType enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	

   	    Collections.sort(list, new Comparator<Map<String, Object>>() {
   	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
   	            return o1.get("id").toString().compareTo(o2.get("id").toString()) ;
   	        }
   	    });
    	
    	return list;
	}
}
