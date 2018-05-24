package enu.aerialmaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hanwu
 * @description 报废状态枚举
 */
public enum ScrapStatusEnum {
	SAVE(1, "保存"),
	SUBMIT(2, "提交"),
	AUDITED(3, "审核通过"),
	AUDIT_REJEC(5, "审核驳回"),
	SCRAP(8, "作废"),
	CLOSED(9, "指定结束"),
	FINISHED(10, "完成"),
	REVOKE(11, "撤销")
	;
	
	
	private Integer id;
    private String name;
    
    private ScrapStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ScrapStatusEnum c : ScrapStatusEnum.values()) {
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
    	ScrapStatusEnum[] enums = ScrapStatusEnum.values();
    	
    	for (ScrapStatusEnum enumItem : enums) {
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
