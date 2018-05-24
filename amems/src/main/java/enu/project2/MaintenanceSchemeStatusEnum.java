package enu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 维修方案状态枚举
 * @author hanwu
 *
 */
public enum MaintenanceSchemeStatusEnum {
	SAVE(1, "保存"),
	SUBMIT(2, "提交"),
	AUDITED(3, "已审核"),
	APPROVED(4, "已批准"),
	AUDIT_REJEC(5, "审核驳回"),
	APPROVE_REJEC(6, "审批驳回"),
	TO_BE_EFFECT(7, "待生效"),
	EFFECTIVE(10, "生效"),
	;
	
	private Integer id;
    private String name;
    
    private MaintenanceSchemeStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (MaintenanceSchemeStatusEnum c : MaintenanceSchemeStatusEnum.values()) {
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
    	MaintenanceSchemeStatusEnum[] enums = MaintenanceSchemeStatusEnum.values();
    	
    	for (MaintenanceSchemeStatusEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	            return o2.get("id").toString().compareTo(o1.get("id").toString()) ;
    	        }
    	    });
    	
    	return list;
	}
     
}
