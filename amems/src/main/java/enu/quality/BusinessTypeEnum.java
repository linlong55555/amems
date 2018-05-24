package enu.quality;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description 业务状态枚举
 * @CreateTime 2018年1月8日 上午10:02:26
 * @CreateBy 林龙
 */
public enum BusinessTypeEnum {
		SHNDJH("审核年度计划", 11)
		, SHTZD("审核通知单", 12)
		, SHXMD("审核项目单", 13)
		, FXWTTZD("发现问题通知单", 14)
		, ZLSHBG("质量审核报告", 15)
		;
	    private String name;
	    private Integer id;

	    
	    private BusinessTypeEnum(String name, Integer id) {
	        this.name = name;
	        this.id = id;
	         
	    }

	    public static String getName(Integer id) {
	        for (BusinessTypeEnum c : BusinessTypeEnum.values()) {
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
	    	BusinessTypeEnum[] enums = BusinessTypeEnum.values();
	    	
	    	for (BusinessTypeEnum enumItem : enums) {
	    		Map<String, Object>map = new HashMap<String, Object>();
	    		map.put("id", enumItem.getId());
	    		map.put("name", enumItem.getName());
	    		list.add(map);
			}
	    	
	    	/* Collections.sort(list, new Comparator<Map<String, Object>>() {
	    	        public int compare(Map<String, Object> o1, Map<String, Object> o2) {
	    	            return o1.get("name").toString().compareTo(o2.get("name").toString()) ;
	    	        }
	    	    });*/
	    	
	    	return list;
		}
}
