package enu.material2;

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
 * @Description 需求标识枚举
 * @CreateTime 2018-2-27 下午5:22:57
 * @CreateBy 孙霁
 */
public enum DemandSafeguardStatusEnum {
	  save("新增", 1)
		, update("修订", 2)
		, QUXIAO("取消", 3)
		, CLOSE("关闭", 9)
		;
	     
	    private String name;
	    private Integer id;

	    
	    private DemandSafeguardStatusEnum(String name, Integer id) {
	        this.name = name;
	        this.id = id;
	         
	    }

	    public static String getName(Integer id) {
	        for (DemandSafeguardStatusEnum c : DemandSafeguardStatusEnum.values()) {
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
	    	DemandSafeguardStatusEnum[] enums = DemandSafeguardStatusEnum.values();
	    	
	    	for (DemandSafeguardStatusEnum enumItem : enums) {
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
