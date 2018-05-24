package enu.project2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TechnicalEnum {
	  CHUSHI("初始状态", -1)
	 	,NOT_EVALUATED("未评估", 0)
		, XIAFA("已评估", 1)
		, SHENHE("已审核", 2)
		, PIZHUN("已批准", 3)
		, ZHONGZI("中止（关闭）", 4)
		, SHENHEBOHUI("审核驳回", 5)
		, PIZHUNBOHUI("批准驳回", 6)
		, ZUOFEI("作废", 8)
		, CLOSE("关闭", 9)
		, WANCHENG("完成", 10)
		;
	    private String name;
	    private Integer id;

	    
	    private TechnicalEnum(String name, Integer id) {
	        this.name = name;
	        this.id = id;
	         
	    }

	    public static String getName(Integer id) {
	        for (TechnicalEnum c : TechnicalEnum.values()) {
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
	    	TechnicalEnum[] enums = TechnicalEnum.values();
	    	
	    	for (TechnicalEnum enumItem : enums) {
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
