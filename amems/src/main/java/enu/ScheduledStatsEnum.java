package enu;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ll
 * @description Mel枚举
 * @develop date 2016.09.12
 */
public enum ScheduledStatsEnum {
	NUM1(1, "日历"),
	NUM2(2,"机身飞行时间"),
	NUM3(3, "搜索灯时间"),
	NUM4(4, "绞车时间"),
	NUM5(5, "起落架循环"),
	NUM6(6, "绞车循环"),
	NUM7(7, "外吊挂循环"),
	NUM8(8, "搜索灯循环"),
	NUM9(9, "N1"),
	NUM10(10, "N2"),
	NUM11(11, "N3"),
	NUM12(12, "N4"),
	NUM13(13, "N5"),
	NUM14(14, "N6"),
	NUM15(15, "特殊循环1"),
	NUM16(16, "特殊循环2"),
	;
	
	private Integer id;
    private String name;
    
    private ScheduledStatsEnum(Integer id, String name) {
    	this.id = id;
        this.name = name;
    }
    
    public static String getName(Integer id) {
        for (ScheduledStatsEnum c : ScheduledStatsEnum.values()) {
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
    	ScheduledStatsEnum[] enums = ScheduledStatsEnum.values();
    	
    	for (ScheduledStatsEnum enumItem : enums) {
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
