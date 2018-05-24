package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 飞机部件位置
 * @author hanwu
 *
 */
public enum PlaneComponentPositionEnum {
	
	JS("机身", 0) ,
	ZF("1#左发", 1) , 
	YF("2#右发", 2) ,
	JC("绞车", 3) ,
	SSD("搜索灯", 4) ,
	WDG("外吊挂", 5)
	; 
	
     
    private String name;
    private Integer code;

    
    private PlaneComponentPositionEnum(String name, Integer code) {
        this.name = name;
        this.setCode(code);
    }

    public static String getName(Integer code) {
        for (PlaneComponentPositionEnum c : PlaneComponentPositionEnum.values()) {
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
    	PlaneComponentPositionEnum[] enums = PlaneComponentPositionEnum.values();
    	
    	for (PlaneComponentPositionEnum enumItem : enums) {
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