package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 部件操作
 * @author hanwu
 *
 */
public enum ComponentOperationEnum {
	
	
	INSTALLED("装上", 1) , 
	DETACHED("拆下", 2) ,
	SCRAP("作废", 3)
	; 
	
     
    private String name;
    private Integer code;

    
    private ComponentOperationEnum(String name, Integer code) {
        this.name = name;
        this.setCode(code);
    }

    public static String getName(Integer code) {
        for (ComponentOperationEnum c : ComponentOperationEnum.values()) {
            if (c.getCode().intValue() == code.intValue()) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
 
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}
	
	 /**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	ComponentOperationEnum[] enums = ComponentOperationEnum.values();
    	
    	for (ComponentOperationEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getCode());
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