package enu.project2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @Description 部件类型枚举 ，对应页面字段名为  涉及|适用类别|分类|等。。
 * @CreateTime 2017年8月17日 下午10:35:41
 * @CreateBy 林龙
 * @UpdateBy 徐勇
 * @UpdateTime 2017年8月19日 上午10:34:53
 */
public enum CompnentTypeEnum {
		  FUSELAGE("飞机", 1)
		, ENGINE("发动机", 2)
		, APU("APU", 3)
		, OTHER("其他部件", 99)
		;
	    private String name;
	    private Integer id;

	    
	    private CompnentTypeEnum(String name, Integer id) {
	        this.name = name;
	        this.id = id;
	         
	    }

	    public static String getName(Integer id) {
	        for (CompnentTypeEnum c : CompnentTypeEnum.values()) {
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
	    	CompnentTypeEnum[] enums = CompnentTypeEnum.values();
	    	
	    	for (CompnentTypeEnum enumItem : enums) {
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
