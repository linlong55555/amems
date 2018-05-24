package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 朱超
 * @description 计划任务状态
 */

public enum PlanTaskState {
//	状态：1保存、2提交、9关闭、10完成。保存状态保留；指定结束对应关闭状态
	TO_BE_EXECUTED(1, "待执行"),
	EXECUTING(2, "执行中"),
	CLOSE(9, "指定结束"),//指定结束
	COMPLETE(10, "完成")
    ;
	
	private Integer id;
    private String name;
    
    private PlanTaskState(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (PlanTaskState c : PlanTaskState.values()) {
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
	 * 是待执行状态
	 * @param val
	 * @return
	 */
	public static Boolean isToBeExecuted(Integer val) {
		return (val!=null&&val.equals(PlanTaskState.TO_BE_EXECUTED.getId()))?Boolean.TRUE:Boolean.FALSE;
	}
	
	public static Boolean canQuery(Integer val) {
		Boolean isTobeExecuted =  (val!=null&&val.equals(PlanTaskState.TO_BE_EXECUTED.getId()))?Boolean.TRUE:Boolean.FALSE;
		Boolean isExecuting =  (val!=null&&val.equals(PlanTaskState.EXECUTING.getId()))?Boolean.TRUE:Boolean.FALSE;
		return (isTobeExecuted || isExecuting);
	}
	
	
	 /**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	PlanTaskState[] enums = PlanTaskState.values();
    	
    	for (PlanTaskState enumItem : enums) {
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
