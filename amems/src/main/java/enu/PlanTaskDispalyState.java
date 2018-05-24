package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 朱超
 * @description 计划任务显示状态
 */

public enum PlanTaskDispalyState {
//	仅作用于计划任务看板功能，不涉及到流程。显示状态：1待执行、2执行中、3完工
	TO_BE_EXECUTED(1, "待执行"),
	EXECUTING(2, "执行中"),
	COMPLETE(3, "完工")
    ;
	
	private Integer id;
    private String name;
    
    private PlanTaskDispalyState(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (PlanTaskDispalyState c : PlanTaskDispalyState.values()) {
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
    	PlanTaskDispalyState[] enums = PlanTaskDispalyState.values();
    	
    	for (PlanTaskDispalyState enumItem : enums) {
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

	public static boolean waitComplete(Integer xszt) {
		Boolean waitComplete = xszt!=null && (EXECUTING.getId().equals(xszt) || TO_BE_EXECUTED.getId().equals(xszt));
		return waitComplete;
	}
	
	public static boolean isComplete(Integer xszt) {
		Boolean waitComplete = xszt!=null && (COMPLETE.getId().equals(xszt));
		return waitComplete;
	}
}
