package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排班状态枚举
 * @author hanwu
 *
 */
public enum ScheduleStatusEnum {
	COMMIT(1, "提交"),
	REVIEWE(2, "已审核"),
	APPROVE(3, "已批准"),
	CLOSE(4, "4中止（关闭）"),
	REVIEWE_REJECTED(5, "审核驳回"),
	APPROVAL_REJECTED(6, "审批驳回"),
	;
	
	private Integer id;
    private String name;
    
    private ScheduleStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ScheduleStatusEnum c : ScheduleStatusEnum.values()) {
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
    	ScheduleStatusEnum[] enums = ScheduleStatusEnum.values();
    	
    	for (ScheduleStatusEnum enumItem : enums) {
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
