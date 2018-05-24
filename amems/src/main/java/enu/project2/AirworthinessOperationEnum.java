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
 * @CreateTime 2017-8-15 下午4:12:08
 * @CreateBy 孙霁
 */
public enum AirworthinessOperationEnum {
	  	CHUSHI("初始", 1)
		, ADD("增加", 2)
		, UPDATE("修改", 3)
		, DELETE("删除", 4)
		;
	     
	    private String name;
	    private Integer id;

	    
	    private AirworthinessOperationEnum(String name, Integer id) {
	        this.name = name;
	        this.id = id;
	         
	    }

	    public static String getName(Integer id) {
	        for (AirworthinessOperationEnum c : AirworthinessOperationEnum.values()) {
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
	    	AirworthinessOperationEnum[] enums = AirworthinessOperationEnum.values();
	    	
	    	for (AirworthinessOperationEnum enumItem : enums) {
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
