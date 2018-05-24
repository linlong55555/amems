package enu.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum PartSnValidationEnum {
	ACREG("飞机注册", 1)
	, INSTALL("装机清单", 2)
	, WO("工单装上件", 3)
	, FLB("飞行记录本装上件", 4)
	, PURCHASE("采购收货", 5)
	, REPAIR("送修收货", 6)
	, BORROW("借入收货", 7)
	, LENDRETURN("借出归还收货", 8)
	, OTHER("其他收货", 9)
	, MATERIAL("航材收货", 12)
	, TOOL("工具收货", 13)
	;
     
    private String name;
    private Integer id;

    
    private PartSnValidationEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (PartSnValidationEnum c : PartSnValidationEnum.values()) {
            if (c.getId().intValue() == id.intValue()) {
                return c.name;
            }
        }
        return "";
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	/**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	PartSnValidationEnum[] enums = PartSnValidationEnum.values();
    	
    	for (PartSnValidationEnum enumItem : enums) {
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