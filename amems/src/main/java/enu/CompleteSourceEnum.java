package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.org.apache.xpath.internal.operations.Bool;

/**
 * @author zhuchao
 * @description 完成来源
 */
 
public enum CompleteSourceEnum {
	CHECK_BILL(1, "定检工单"),
	NON_ROUTINE(2, "非例行工单任务"),
	EO_BILL(3, "EO工单任务"),
	CHECK_BILL_TASK(4, "定检执行任务"),
	PLAN_TASK(5, "计划任务")
	;
	
	
	private Integer id;
    private String name;
    
    private CompleteSourceEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (CompleteSourceEnum c : CompleteSourceEnum.values()) {
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
    	CompleteSourceEnum[] enums = CompleteSourceEnum.values();
    	
    	for (CompleteSourceEnum enumItem : enums) {
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
    
    /**
     * 是定检单
     * @param id
     * @return
     */
    public static Boolean isCheckBill(Integer id) {
        return id!=null && CHECK_BILL.getId().equals(id)?Boolean.TRUE:Boolean.FALSE;
    }
    
    public static Boolean isCheckBill4Str(String id) {
        return id!=null && CHECK_BILL.getId().equals(Integer.valueOf(id))?Boolean.TRUE:Boolean.FALSE;
    }
    
    public static Boolean isNonRoutine4Str(String id) {
        return id!=null && NON_ROUTINE.getId().equals(Integer.valueOf(id))?Boolean.TRUE:Boolean.FALSE;
    }
    
    public static Boolean isEoBill4Str(String id) {
        return id!=null && EO_BILL.getId().equals(Integer.valueOf(id))?Boolean.TRUE:Boolean.FALSE;
    }
    
    public static Boolean isCheckbillTask4Str(String id) {
        return id!=null && CHECK_BILL_TASK.getId().equals(Integer.valueOf(id))?Boolean.TRUE:Boolean.FALSE;
    }
    
    public static Boolean isPlanTask4Str(String id) {
        return id!=null && PLAN_TASK.getId().equals(Integer.valueOf(id))?Boolean.TRUE:Boolean.FALSE;
    }
     
     
}
