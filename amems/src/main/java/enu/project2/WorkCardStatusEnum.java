package enu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description 工卡状态枚举
 * @CreateTime 2017-8-15 下午5:34:46
 * @CreateBy 刘兵
 */
public enum WorkCardStatusEnum {
	SAVE(1, "保存"),
	SUBMIT(2, "提交"),
	AUDITED(3, "已审核"),
	APPROVED(4, "已批准"),
	AUDIT_REJEC(5, "审核驳回"),
	APPROVE_REJEC(6, "批准驳回"),
	EFFECTIVE(7, "生效"),
	INVALID(8, "失效"),
	;
	
	private Integer id;
    private String name;
    
    private WorkCardStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (WorkCardStatusEnum c : WorkCardStatusEnum.values()) {
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
	 * 
	 * @Description 枚举转listmap
	 * @CreateTime 2017-8-15 下午5:36:50
	 * @CreateBy 刘兵
	 * @return
	 */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	WorkCardStatusEnum[] enums = WorkCardStatusEnum.values();
    	
    	for (WorkCardStatusEnum enumItem : enums) {
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
