package enu.material2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 交货期单位枚举
 * @CreateTime 2018-3-28 下午1:31:07
 * @CreateBy 刘兵
 */
public enum DeliveryEnum {
	CALENDAR(10, "日历"),
	DAY(11, "D"),
	MONTH(12, "M")
	;
	
	
	private Integer id;
    private String name;
    
    private DeliveryEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (DeliveryEnum c : DeliveryEnum.values()) {
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
    	DeliveryEnum[] enums = DeliveryEnum.values();
    	
    	for (DeliveryEnum enumItem : enums) {
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
