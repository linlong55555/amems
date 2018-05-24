package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor.YELLOW;

/**
 * @author zhuchao
 * @description 二进制枚举
 */
public enum BinaryEnum {
	YES(1, "是"),
	NO(0, "否")
	;
	
	
	private Integer id;
    private String name;
    
    private BinaryEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (BinaryEnum c : BinaryEnum.values()) {
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
    	BinaryEnum[] enums = BinaryEnum.values();
    	
    	for (BinaryEnum enumItem : enums) {
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
    
    public static Boolean isYes(Integer id) {
    	return id!=null && YES.getId().equals(id)?Boolean.TRUE:Boolean.FALSE;
	}
    public static Boolean isYes4Str(String id) {
    	
    	if (id!=null) {
    		Integer idInt = Integer.valueOf(id);
    		if (idInt.equals(YES.getId())) {
    			return Boolean.TRUE;
			}
		}
    	return Boolean.FALSE;
	}
     
}
