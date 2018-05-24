package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 朱超
 * @description 修订通知书类型枚举
 */

public enum RevisionNoticeBookTypeEnum {
	
	MAINTENANCE(1, "维修方案"),
	MEL(2, "MEL"),
	WORK_ORDER(3, "工单"),
    ;
	
	private Integer id;
    private String name;
    
    private RevisionNoticeBookTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (RevisionNoticeBookTypeEnum c : RevisionNoticeBookTypeEnum.values()) {
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
    	RevisionNoticeBookTypeEnum[] enums = RevisionNoticeBookTypeEnum.values();
    	
    	for (RevisionNoticeBookTypeEnum enumItem : enums) {
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
