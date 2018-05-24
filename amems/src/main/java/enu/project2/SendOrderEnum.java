package enu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description 技术评估单下达指令 枚举
 * @CreateTime 2017年8月19日 上午10:46:19
 * @CreateBy 徐勇
 */
public enum SendOrderEnum {
		 JSTG("维护提示", 1)
		, JSZL("技术指令", 2)
		, XDWXFA("修订维修方案", 3)
		, NRC("下达工单(维修指令)", 4)
		, EO("工程指令EO", 6)
		, MEL("MEL更改", 7)
		, XDGK("修订工卡", 8)
		, JSPGD("技术评估单", 9)
		, QITA("其他", 99)
		;

	    private String name;
	    private Integer id;

	    
	    private SendOrderEnum(String name, Integer id) {
	        this.name = name;
	        this.id = id;
	         
	    }

	    public static String getName(Integer id) {
	        for (SendOrderEnum c : SendOrderEnum.values()) {
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
	    	SendOrderEnum[] enums = SendOrderEnum.values();
	    	
	    	for (SendOrderEnum enumItem : enums) {
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
