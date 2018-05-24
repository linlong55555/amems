package enu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhuchao
 * @description 监控项枚举
 */
public enum MonitorItemEnum {
	  //日历类
	  CALENDAR("日历")
	  
	  //时间类
	  /**
	   * 机身飞行时间
	   */
	, FUSELAGE_FLIGHT_TIME("机身飞行时间")
	/**
	 * 搜索灯时间
	 */
	, SEARCH_LIGHT_TIME("搜索灯时间")
	/**
	 * 绞车时间
	 */
	, WINCH_TIME("绞车时间")
	
	  //循环类
	/**
	 * 起落架循环
	 */
	, LANDING_GEAR_CYCLE("起落循环")
	/**
	 * 绞车循环
	 */
	, WINCH_CYCLE("绞车循环")
	/**
	 * 外吊挂循环
	 */
	, EXT_SUSPENSION_LOOP("外吊挂循环")
	/**
	 * 搜索灯循环
	 */
	, SEARCH_LIGHT_CYCLE("搜索灯循环")
	/**
	 * 特殊监控1
	 */
	, SPECIAL_FIRST("特殊监控1")
	/**
	 * 特殊监控2
	 */
	, SPECIAL_SECOND("特殊监控2")
	
	, N1("N1")
	, N2("N2")
	, N3("N3")
	, N4("N4")
	, N5("N5")
	, N6("N6")
	
	
	;
	
    private String name;
    private Integer id;

    
    private MonitorItemEnum(String name) {
        this.name = name;
        this.id = name.hashCode();
         
    }

    public static String getName(String enuStr) {
        for (MonitorItemEnum c : MonitorItemEnum.values()) {
            if (c.toString().equals(enuStr.toUpperCase())) {
                return c.name;
            }
        }
        return "";
    }
    
    /**
     * 枚举转listmap
     * @return
     */
    public static List<Map<String, Object>> enumToListMap() {
    	
    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    	MonitorItemEnum[] enums = MonitorItemEnum.values();
    	
    	for (MonitorItemEnum enumItem : enums) {
    		Map<String, Object>map = new HashMap<String, Object>();
    		map.put("id", enumItem.getId());
    		map.put("name", enumItem.getName());
    		list.add(map);
		}
    	
    	return list;
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
	
	 public static boolean isCalendar(String monitorItem) {
    	if (monitorItem.toUpperCase().equals(CALENDAR.toString())) {
			return true;
		}
    	else{
    		return false;
    	}
	 }
	 
	 /**
	  * 是否时间类型监控项目</br>
	  * @param monitorItem
	  * @return
	  */
	 public static boolean isTime(String monitorItem) {
    	if (monitorItem.toUpperCase().equals(FUSELAGE_FLIGHT_TIME.toString())
    			||monitorItem.toUpperCase().equals(SEARCH_LIGHT_TIME.toString())
    			||monitorItem.toUpperCase().equals(WINCH_TIME.toString())) {
			return true;
		}
    	else{
    		return false;
    	}
	 }
	 
	 /**
	  * 是否循环类型监控项目</br>
	  * @param monitorItem
	  * @return
	  */
	 public static boolean isLoop(String monitorItem) {
    	if ( 
    			monitorItem.toUpperCase().equals(LANDING_GEAR_CYCLE.toString())
    			||monitorItem.toUpperCase().equals(EXT_SUSPENSION_LOOP.toString())
    			||monitorItem.toUpperCase().equals(WINCH_CYCLE.toString())
    			
    			||monitorItem.toUpperCase().equals(N1.toString())
    			||monitorItem.toUpperCase().equals(N2.toString())
    			||monitorItem.toUpperCase().equals(N3.toString())
    			||monitorItem.toUpperCase().equals(N4.toString())
    			||monitorItem.toUpperCase().equals(N5.toString())
    			||monitorItem.toUpperCase().equals(N6.toString())
    			
    			||monitorItem.toUpperCase().equals(SPECIAL_FIRST.toString())
    			||monitorItem.toUpperCase().equals(SPECIAL_SECOND.toString())) {
			return true;
		}
    	else{
    		return false;
    	}
	 }
	
	 /**
	  * 随飞机的监控项目</br>
	  * @param monitorItem
	  * @return
	  */
	 public static boolean isInvolvedAircraft(String monitorItem) {
    	if (monitorItem.toUpperCase().equals(LANDING_GEAR_CYCLE.toString())
    			||monitorItem.toUpperCase().equals(FUSELAGE_FLIGHT_TIME.toString())
    			||monitorItem.toUpperCase().equals(SPECIAL_FIRST.toString())
    			||monitorItem.toUpperCase().equals(SPECIAL_SECOND.toString())) {
			return true;
		}
    	else{
    		return false;
    	}
	 }
	 
	 
	 /**
	  * 获取监控项目单位
	  * @param monitorItem
	  * @return
	  */
	 public static String getUnit(String monitorItem) {
		 
		 String result = "";
		 if (isCalendar(monitorItem)) {
			 result = "D";
		 }
		 else if (isTime(monitorItem)) {
			 result = "H";
		 }
		 else if (isLoop(monitorItem)) {
			 result = "C";
		 }
		 return result;
	 }
	
    
}