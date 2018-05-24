package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuchao
 * @description 部件位置
 */
public enum PartsPositionEnum {
	/*位置：0机身、1.1#左发、2.2#左发、3绞车、4搜索灯、5外吊挂*/
	PLANE_BODY(0, "机身"),
	LEFT_ENGINE(1, "1#左发"),
	RIGHT_ENGINE(2, "2#右发"),
	WINCH(3, "绞车"),
	SEARCH_LIGHT(4, "搜索灯"),
	EXTERNAL_SUSPENSION(5, "外吊挂"),
	;
	
	private Integer id;
    private String name;
    
    private PartsPositionEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (PartsPositionEnum c : PartsPositionEnum.values()) {
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
    	PartsPositionEnum[] enums = PartsPositionEnum.values();
    	
    	for (PartsPositionEnum enumItem : enums) {
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
