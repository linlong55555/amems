package enu.aerialmaterial;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description 付款方式枚举
 * @develop date 2016.11.12
 */
public enum PayTypeEnum {
	PAY_OTHER(0, "其它"),
	PAY_CHEQUE(1, "支票"),
	PAY_CASH(2, "现金"),
	PAY_REMIT(3, "汇款")
	;
	
	
	private Integer id;
    private String name;
    
    private PayTypeEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (PayTypeEnum c : PayTypeEnum.values()) {
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
    	PayTypeEnum[] enums = PayTypeEnum.values();
    	
    	for (PayTypeEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	 Collections.sort(list, new Comparator<Map<String, Object>>() {
    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
    	            return o2.get("name").toString().compareTo(o1.get("name").toString()) ;
    	        }
    	    });
    	
    	return list;
	}
     
}
