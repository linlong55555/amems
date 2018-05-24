package enu.aerialmaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收货类型枚举
 * @author hanwu
 *
 */
public enum ReceivingTypeEnum {
	PURCHASE(1, "采购"),
	REPAIR(2, "送修"),
	BORROW(3, "借入"),
	RETURN(4, "借出归还"),
	OTHER(99, "其它");
	
	
	private Integer id;
    private String name;
    
    private ReceivingTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ReceivingTypeEnum c : ReceivingTypeEnum.values()) {
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
    	ReceivingTypeEnum[] enums = ReceivingTypeEnum.values();
    	
    	for (ReceivingTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	Collections.sort(list, new Comparator<Map<String, Object>>() {
	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	            return Integer.valueOf(o1.get("id").toString()).compareTo(Integer.valueOf(o2.get("id").toString())) ;
	        }
	    });
    	
    	return list;
	}
}
