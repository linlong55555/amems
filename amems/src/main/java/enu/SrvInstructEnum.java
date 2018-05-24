package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuchao
 * @description 服务指令-指令类型
 */
public enum SrvInstructEnum {
	SCJHYP("SCJHYP", "生产计划预排") 
	;
	
	
	private String id;
    private String name;
    
    private SrvInstructEnum(String id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(String id) {
        for (SrvInstructEnum c : SrvInstructEnum.values()) {
            if (c.getId().equals( id)) {
                return c.name;
            }
        }
        return "";
    }
    
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
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
    	SrvInstructEnum[] enums = SrvInstructEnum.values();
    	
    	for (SrvInstructEnum enumItem : enums) {
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
