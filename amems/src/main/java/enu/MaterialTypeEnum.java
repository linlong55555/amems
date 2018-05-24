package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description 航材类型枚举
 * @develop date 2016.09.12
 */
public enum MaterialTypeEnum {
	MATERIAL(1, "航材"),
	APPOINTED(2, "工具设备"),
	//EQUIPMENT(3, "设备"),
	DANGEROUS_GOODS(4, "化工品"),
	LOW_PRICE(5, "低值易耗品"),
	SONGSHAN(6, "松散件"),
	OTHER(0, "其它");
	
	
	private Integer id;
    private String name;
    
    private MaterialTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (MaterialTypeEnum c : MaterialTypeEnum.values()) {
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
    	MaterialTypeEnum[] enums = MaterialTypeEnum.values();
    	
    	for (MaterialTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
//    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
//    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
//    	            return o1.get("name").toString().compareTo(o2.get("name").toString()) ;
//    	        }
//    	    });
    	
    	return list;
	}
     
}
