package enu.aerialmaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工具使用状态枚举
 * @author zhuchao
 *
 */
public enum ToolUseStatusEnum {
	 
	APPLY(1, "提交申请"),
	CONFIRM(2, "借用确认"),
	RETURNED(3, "已归还"),
	CLOSE(9, "关闭"),
	COMPLETE(10, "完成"),
	;
	
	
	private Integer id;
    private String name;
    
    private ToolUseStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ToolUseStatusEnum c : ToolUseStatusEnum.values()) {
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
    	ToolUseStatusEnum[] enums = ToolUseStatusEnum.values();
    	
    	for (ToolUseStatusEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	Collections.sort(list, new Comparator<Map<String, Object>>() {
	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	            return Integer.valueOf(o1.get("id").toString()).compareTo(Integer.valueOf(o2.get("id").toString())) ;
	        }
	    });
    	
    	return list;
	}
}
