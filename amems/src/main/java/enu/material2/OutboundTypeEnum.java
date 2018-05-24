package enu.material2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description 出库类型枚举
 * @CreateTime 2018年3月15日 下午3:29:38
 * @CreateBy 林龙
 */
public enum OutboundTypeEnum {
		PURCHASE("采购", 10,27)
		, REPAIR("修理", 20,21)
		, RENTINTO("租进", 31,22)
		, RENTOUT("租出", 32,23)
		, EXCHANGE("交换", 40,24)
		, SELL("出售", 50,25)
		, ISSUE("发料", 70,26)
		, OTHER("其他", 90,28)
		;

	    private String name;
	    private Integer id;
	    private Integer uid;

	    
	    private OutboundTypeEnum(String name, Integer id,Integer uid) {
	        this.name = name;
	        this.id = id;
	        this.uid = uid;
	         
	    }

	    public static Integer getName(Integer id) {
	        for (OutboundTypeEnum c : OutboundTypeEnum.values()) {
	            if (c.getId().intValue() == id.intValue()) {
	                return c.uid;
	            }
	        }
	        return 0;
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
		
		 public Integer getUid() {
			return uid;
		}

		public void setUid(Integer uid) {
			this.uid = uid;
		}

		/**
	     * 枚举转listmap
	     * @return
	     */
	    public static List<Map<String, Object>> enumToListMap() {
	    	List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
	    	OutboundTypeEnum[] enums = OutboundTypeEnum.values();
	    	
	    	for (OutboundTypeEnum enumItem : enums) {
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
