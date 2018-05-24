package enu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import enu.aerialmaterial.ScrapStatusEnum;
import enu.ordersource.OrderSourceEnum;

/**
 * 
 * @Description 适航性文件操作枚举
 * @CreateTime 2017-8-15 下午4:11:48
 * @CreateBy 孙霁
 */
public enum AirworthinessStatusEnum {
	  NOT_EVALUATED("保存", 0)
		, SUBMIT("提交", 1)
		, ZUOFEI("作废", 8)
		, CLOSE("关闭", 9)
		;
	     
	    private String name;
	    private Integer id;

	    
	    private AirworthinessStatusEnum(String name, Integer id) {
	        this.name = name;
	        this.id = id;
	         
	    }

	    public static String getName(Integer id) {
	        for (AirworthinessStatusEnum c : AirworthinessStatusEnum.values()) {
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
	    	AirworthinessStatusEnum[] enums = AirworthinessStatusEnum.values();
	    	
	    	for (AirworthinessStatusEnum enumItem : enums) {
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
