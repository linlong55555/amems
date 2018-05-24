package enu.quality;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description 检验单枚举
 * @CreateTime 2018年3月26日 下午4:18:20
 * @CreateBy 林龙
 */
public enum InspectionStatusEnum {
	INITIAL(-1, "初始"),
	SAVE(1, "保存"),
	SUBMIT(2, "提交"),
	INVALID(8, "作废"),
	CLOSED(9, "关闭"),
	COMPLETE(10, "完成"),
	;
	
	private Integer id;
    private String name;
    
    private InspectionStatusEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (InspectionStatusEnum c : InspectionStatusEnum.values()) {
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
    	InspectionStatusEnum[] enums = InspectionStatusEnum.values();
    	
    	for (InspectionStatusEnum enumItem : enums) {
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
