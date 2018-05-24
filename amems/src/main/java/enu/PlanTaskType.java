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
 * @description 计划任务类型
 */

public enum PlanTaskType {
//	任务类型：1定检执行任务、2非例行工单任务、3EO工单任务
	/**
	 * 定检执行任务
	 */
	CHECK_BILL(1, "定检执行任务"),
	NON_ROUTINE(2, "非例行"),
	EO_BILL(3, "EO工单")
    ;
	
	private Integer id;
    private String name;
    
    private PlanTaskType(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (PlanTaskType c : PlanTaskType.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name;
            }
        }
        return "";
    }
    
    public static Boolean isCheckBill(String id) {
    	Boolean reuslt = false;
    	if (StringUtils.isNotBlank(id)) {
    		if (PlanTaskType.CHECK_BILL.getId().toString().equals(id)) {
            	reuslt = true;
            } 
		}
        return reuslt;
    }
    
    public static Boolean isNonRoutine(String id) {
    	Boolean reuslt = false;
    	if (StringUtils.isNotBlank(id)) {
    		if (PlanTaskType.NON_ROUTINE.getId().toString().equals(id)) {
            	reuslt = true;
            } 
		}
        return reuslt;
    }
    
    public static Boolean isEoBill(String id) {
    	Boolean reuslt = false;
    	if (StringUtils.isNotBlank(id)) {
    		if (PlanTaskType.EO_BILL.getId().toString().equals(id)) {
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
    	PlanTaskType[] enums = PlanTaskType.values();
    	
    	for (PlanTaskType enumItem : enums) {
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
