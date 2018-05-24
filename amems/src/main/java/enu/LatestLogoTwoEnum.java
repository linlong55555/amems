package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liub
 * @description 最新标识枚举(页面显示)
 * @develop date 2016.09.21
 */
public enum LatestLogoTwoEnum {
	
	NO_EFFECT("未生效", 0)
	, EFFECT("生效", 1)
	, LOSE_EFFECT("失效", 2)
	 
	;
     
    private String name;
    private Integer id;

    
    private LatestLogoTwoEnum(String name, Integer id) {
        this.name = name;
        this.id = id;
         
    }

    public static String getName(Integer id) {
        for (LatestLogoTwoEnum c : LatestLogoTwoEnum.values()) {
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
    	LatestLogoTwoEnum[] enums = LatestLogoTwoEnum.values();
    	
    	for (LatestLogoTwoEnum enumItem : enums) {
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