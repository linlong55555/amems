package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description 审批状态枚举
 * @develop date 2016.09.28
 */
public enum ApproveStatusEnum {
	NOT_AUDITED(1, "未审核"),
	AUDITED(2, "已审核"),
	APPROVED(3, "已批准"),
	STOPPED(4, "中止"),
	AUDIT_DISMISSED(5, "审核驳回"),
	APPROVED_DISMISSED(6, "批准驳回")
	;
	
	
	private Integer id;
    private String name;
    
    private ApproveStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ApproveStatusEnum c : ApproveStatusEnum.values()) {
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
    	ApproveStatusEnum[] enums = ApproveStatusEnum.values();
    	
    	for (ApproveStatusEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	 /*Collections.sort(list, new Comparator<Map<String, Object>>() {
    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	            return o1.get("name").toString().compareTo(o2.get("name").toString()) ;
    	        }
    	    });*/
    	
    	return list;
	}
     
}
